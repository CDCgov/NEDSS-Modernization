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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Epidemiologic","General Comments","Inclusion Criteria","Comorbidities","Hospitalization","Clinical Signs and Symptoms","Complications","Treatments","Vaccination","Studies Details","SARS-COV-2 Testing","Contact Investigation"};
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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the MIS case also had a COVID-19 case, enter the nCoV ID from the COVID-19 case here." id="NBS547L">
nCoV ID (If Available):</span>
</td>
<td>
<span id="NBS547"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS547)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Name of the person who abstracted the medical record." id="67153_7L">
Abstractor Name:</span>
</td>
<td>
<span id="67153_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(67153_7)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the information was abstracted from the medical record." id="NBS692L">Date of Abstraction:</span>
</td><td>
<span id="NBS692"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS692)"  />
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Pregnancy" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_UI_GA28002" name="Inclusion Criteria Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient less than 21 years old at illness onset?" id="NBS694L" >
Age &lt; 21:</span></td><td>
<span id="NBS694" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS694)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more" id="426000000L" >
Fever For At Least 24 Hrs:</span></td><td>
<span id="426000000" />
<nedss:view name="PageForm" property="pageClientVO.answer(426000000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin." id="NBS695L" >
Laboratory Markers of Inflammation:</span></td><td>
<span id="NBS695" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS695)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Evidence of clinically severe illness requiring hospitalization." id="434081000124108L" >
Evidence of clinically severe illness requiring hospitalization with multisystem organ involvement:</span></td><td>
<span id="434081000124108" />
<nedss:view name="PageForm" property="pageClientVO.answer(434081000124108)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?" id="56265001L" >
Cardiac:</span></td><td>
<span id="56265001" />
<nedss:view name="PageForm" property="pageClientVO.answer(56265001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?" id="90708001L" >
Renal:</span></td><td>
<span id="90708001" />
<nedss:view name="PageForm" property="pageClientVO.answer(90708001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?" id="50043002L" >
Respiratory:</span></td><td>
<span id="50043002" />
<nedss:view name="PageForm" property="pageClientVO.answer(50043002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?" id="128480004L" >
Hematologic:</span></td><td>
<span id="128480004" />
<nedss:view name="PageForm" property="pageClientVO.answer(128480004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea)." id="119292006L" >
Gastrointestinal:</span></td><td>
<span id="119292006" />
<nedss:view name="PageForm" property="pageClientVO.answer(119292006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?" id="95320005L" >
Dermatologic:</span></td><td>
<span id="95320005" />
<nedss:view name="PageForm" property="pageClientVO.answer(95320005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?" id="118940003L" >
Neurological:</span></td><td>
<span id="118940003" />
<nedss:view name="PageForm" property="pageClientVO.answer(118940003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether there is no alternative plausible diagnosis." id="NBS696L" >
No alternative plausible diagnosis:</span></td><td>
<span id="NBS696" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS696)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?" id="NBS697L" >
Positive for current or recent SARS-COV-2 infection by lab testing?:</span></td><td>
<span id="NBS697" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS697)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was RT-PCR testing completed for this disease?" id="NBS709L" >
RT-PCR:</span></td><td>
<span id="NBS709" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS709)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was serology testing completed for this disease?" id="NBS710L" >
Serology:</span></td><td>
<span id="NBS710" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS710)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was antigen testing completed for this disease?" id="NBS711L" >
Antigen:</span></td><td>
<span id="NBS711" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS711)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?" id="840546002L" >
COVID-19 exposure within the 4 weeks prior to the onset of symptoms?:</span></td><td>
<span id="840546002" />
<nedss:view name="PageForm" property="pageClientVO.answer(840546002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of first exposure to individual with confirmed illness within the 4 weeks prior." id="LP248166_3L">Date of first exposure within the 4 weeks prior:</span>
</td><td>
<span id="LP248166_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP248166_3)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Exposure date unknown." id="NBS698L" >
Exposure Date Unknown:</span></td><td>
<span id="NBS698" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS698)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28004" name="Weight and BMI" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS434L" title="Enter the height of the patient in inches.">
Height (in inches):</span>
</td><td>
<span id="NBS434"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS434)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="DEM255L" title="Enter the patients weight at diagnosis in pounds (lbs).">
Weight (in lbs):</span>
</td><td>
<span id="DEM255"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM255)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="39156_5L" title="What is the patient's body mass index?">
BMI:</span>
</td><td>
<span id="39156_5"/>
<nedss:view name="PageForm" property="pageClientVO.answer(39156_5)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28003" name="Comorbidities Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of an immunocompromised condition?" id="370388006L" >
Immunosuppressive disorder or malignancy:</span></td><td>
<span id="370388006" />
<nedss:view name="PageForm" property="pageClientVO.answer(370388006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient obese?" id="414916001L" >
Obesity:</span></td><td>
<span id="414916001" />
<nedss:view name="PageForm" property="pageClientVO.answer(414916001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have type 1 diabetes?" id="46635009L" >
Type 1 diabetes:</span></td><td>
<span id="46635009" />
<nedss:view name="PageForm" property="pageClientVO.answer(46635009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have type 2 diabetes?" id="44054006L" >
Type 2 diabetes:</span></td><td>
<span id="44054006" />
<nedss:view name="PageForm" property="pageClientVO.answer(44054006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have seizures?" id="91175000L" >
Seizures:</span></td><td>
<span id="91175000" />
<nedss:view name="PageForm" property="pageClientVO.answer(91175000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Congenital heart disease" id="13213009L" >
Congenital heart disease:</span></td><td>
<span id="13213009" />
<nedss:view name="PageForm" property="pageClientVO.answer(13213009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have sickle cell disease?" id="417357006L" >
Sickle cell disease:</span></td><td>
<span id="417357006" />
<nedss:view name="PageForm" property="pageClientVO.answer(417357006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?" id="413839001L" >
Chronic Lung Disease:</span></td><td>
<span id="413839001" />
<nedss:view name="PageForm" property="pageClientVO.answer(413839001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have any other congenital malformations?" id="276654001L" >
Other congenital malformations:</span></td><td>
<span id="276654001" />
<nedss:view name="PageForm" property="pageClientVO.answer(276654001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify other congenital malformations." id="276654001_OTHL">
Specify other congenital malformations:</span>
</td>
<td>
<span id="276654001_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(276654001_OTH)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
<span title="Subject's admission date to the hospital for the condition covered by the investigation." id="INV132L">Hospital Admission Date:</span>
</td><td>
<span id="INV132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV132)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's discharge date from the hospital for the condition covered by the investigation." id="INV133L">Hospital Discharge:</span>
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
<span title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?" id="309904001L" >
Was patient admitted to ICU?:</span></td><td>
<span id="309904001" />
<nedss:view name="PageForm" property="pageClientVO.answer(309904001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of admission." id="NBS679L">ICU Admission Date:</span>
</td><td>
<span id="NBS679"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS679)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="74200_7L" title="Indicate the number of days the patient was in intensive care.">
Number of days in the ICU:</span>
</td><td>
<span id="74200_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(74200_7)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patients Outcome" id="FDD_Q_1038L" >
Patients Outcome:</span></td><td>
<span id="FDD_Q_1038" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_1038)"
codeSetNm="PATIENT_HOSP_STATUS_MIS"/>
</td> </tr>

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
<nedss:container id="NBS_UI_GA29007" name="Previous COVID Like Illness" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have preceding COVID-like illness?" id="NBS707L" >
Did patient have preceding COVID-like illness:</span></td><td>
<span id="NBS707" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS707)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of COVID-like illness symptom onset." id="NBS708L">Date of COVID-like illness symptom onset:</span>
</td><td>
<span id="NBS708"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS708)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27016" name="Symptoms Onset and Fever" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Onset information below relates to onset of Multisystem Inflammatory Syndrome Associated with COVID-19.</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of diagnosis of MIS-C." id="INV136L">Diagnosis Date:</span>
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
<span title="Did the patient have fever?" id="386661006L" >
Fever Greater Than 38.0 C or 100.4 F:</span></td><td>
<span id="386661006" />
<nedss:view name="PageForm" property="pageClientVO.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicates the date of fever onset" id="INV203L">Date of Fever Onset:</span>
</td><td>
<span id="INV203"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV203)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV202L" title="What was the subjects highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature (in degrees C):</span>
</td><td>
<span id="INV202"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV202)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAR125L" title="Total number of days fever lasted">
Number of Days Febrile:</span>
</td><td>
<span id="VAR125"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAR125)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27017" name="Cardiac" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Shock is captured in the Complications section of the page.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have elevated troponin?" id="444931001L" >
Elevated troponin:</span></td><td>
<span id="444931001" />
<nedss:view name="PageForm" property="pageClientVO.answer(444931001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have elevated BNP or NT-proBNP?" id="414798009L" >
Elevated BNP or NT-proBNP:</span></td><td>
<span id="414798009" />
<nedss:view name="PageForm" property="pageClientVO.answer(414798009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27018" name="Renal" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have acute kidney injury?" id="14350001000004108L" >
Acute kidney injury:</span></td><td>
<span id="14350001000004108" />
<nedss:view name="PageForm" property="pageClientVO.answer(14350001000004108)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have renal failure?" id="42399005L" >
Renal failure:</span></td><td>
<span id="42399005" />
<nedss:view name="PageForm" property="pageClientVO.answer(42399005)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27019" name="Respiratory" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patients illness include the symptom of cough?" id="49727002L" >
Cough:</span></td><td>
<span id="49727002" />
<nedss:view name="PageForm" property="pageClientVO.answer(49727002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have shortness of breath (dyspnea)?" id="267036007L" >
Shortness of breath (dyspnea):</span></td><td>
<span id="267036007" />
<nedss:view name="PageForm" property="pageClientVO.answer(267036007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient experience chest pain?" id="29857009L" >
Chest pain or tightness:</span></td><td>
<span id="29857009" />
<nedss:view name="PageForm" property="pageClientVO.answer(29857009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Pneumonia and ARDS are captured in Complications section of the page.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pulmonary embolism?" id="59282003L" >
Pulmonary embolism:</span></td><td>
<span id="59282003" />
<nedss:view name="PageForm" property="pageClientVO.answer(59282003)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27020" name="Hematologic" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have elevated D-dimers?" id="449830004L" >
Elevated D-dimers:</span></td><td>
<span id="449830004" />
<nedss:view name="PageForm" property="pageClientVO.answer(449830004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have thrombophilia?" id="234467004L" >
Thrombophilia:</span></td><td>
<span id="234467004" />
<nedss:view name="PageForm" property="pageClientVO.answer(234467004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have thrombocytopenia?" id="302215000L" >
Thrombocytopenia:</span></td><td>
<span id="302215000" />
<nedss:view name="PageForm" property="pageClientVO.answer(302215000)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27021" name="Gastrointestinal" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have abdominal pain or tenderness?" id="21522001L" >
Abdominal pain:</span></td><td>
<span id="21522001" />
<nedss:view name="PageForm" property="pageClientVO.answer(21522001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject experience vomiting?" id="422400008L" >
Vomiting:</span></td><td>
<span id="422400008" />
<nedss:view name="PageForm" property="pageClientVO.answer(422400008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have diarrhea?" id="62315008L" >
Diarrhea:</span></td><td>
<span id="62315008" />
<nedss:view name="PageForm" property="pageClientVO.answer(62315008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have elevated bilirubin?" id="26165005L" >
Elevated bilirubin:</span></td><td>
<span id="26165005" />
<nedss:view name="PageForm" property="pageClientVO.answer(26165005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have elevated liver enzymes?" id="707724006L" >
Elevated liver enzymes:</span></td><td>
<span id="707724006" />
<nedss:view name="PageForm" property="pageClientVO.answer(707724006)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27022" name="Dermatologic" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have rash?" id="271807003L" >
Rash:</span></td><td>
<span id="271807003" />
<nedss:view name="PageForm" property="pageClientVO.answer(271807003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have mucocutaneous lesions?" id="95346009L" >
Mucocutaneous lesions:</span></td><td>
<span id="95346009" />
<nedss:view name="PageForm" property="pageClientVO.answer(95346009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27023" name="Neurological" isHidden="F" classType="subSect" >

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
<span title="Did the patient have altered mental status?" id="419284004L" >
Altered Mental Status:</span></td><td>
<span id="419284004" />
<nedss:view name="PageForm" property="pageClientVO.answer(419284004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have syncope/near syncope?" id="NBS712L" >
Syncope/near syncope:</span></td><td>
<span id="NBS712" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS712)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have meningitis?" id="44201003L" >
Meningitis:</span></td><td>
<span id="44201003" />
<nedss:view name="PageForm" property="pageClientVO.answer(44201003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have encephalopathy?" id="81308009L" >
Encephalopathy?:</span></td><td>
<span id="81308009" />
<nedss:view name="PageForm" property="pageClientVO.answer(81308009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27024" name="Other Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have neck pain?" id="81680005L" >
Neck pain:</span></td><td>
<span id="81680005" />
<nedss:view name="PageForm" property="pageClientVO.answer(81680005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have myalgia?" id="68962001L" >
Myalgia:</span></td><td>
<span id="68962001" />
<nedss:view name="PageForm" property="pageClientVO.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have conjunctival injection?" id="193894004L" >
Conjunctival injection:</span></td><td>
<span id="193894004" />
<nedss:view name="PageForm" property="pageClientVO.answer(193894004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have periorbital edema?" id="49563000L" >
Periorbital edema:</span></td><td>
<span id="49563000" />
<nedss:view name="PageForm" property="pageClientVO.answer(49563000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have cervical lymphadenopathy?" id="127086001L" >
Cervical lymphadenopathy:</span></td><td>
<span id="127086001" />
<nedss:view name="PageForm" property="pageClientVO.answer(127086001)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29002" name="Complications Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have arrhythmia?" id="698247007L" >
Arrhythmia:</span></td><td>
<span id="698247007" />
<nedss:view name="PageForm" property="pageClientVO.answer(698247007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="76281_5L" title="Indicate the type of arrhythmia.">
Type of arrhythmia:</span></td><td>
<span id="76281_5" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(76281_5)"
codeSetNm="CARDIAC_ARRHYTHMIA_TYPE"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Indicate the type of arrhythmia." id="76281_5OthL">Other Type of arrhythmia:</span></td>
<td> <span id="76281_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(76281_5Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have congestive heart failure?" id="ARB020L" >
Congestive Heart Failure:</span></td><td>
<span id="ARB020" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB020)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have myocarditis?" id="50920009L" >
Myocarditis:</span></td><td>
<span id="50920009" />
<nedss:view name="PageForm" property="pageClientVO.answer(50920009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pericarditis?" id="3238004L" >
Pericarditis:</span></td><td>
<span id="3238004" />
<nedss:view name="PageForm" property="pageClientVO.answer(3238004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have liver failure?" id="59927004L" >
Liver failure:</span></td><td>
<span id="59927004" />
<nedss:view name="PageForm" property="pageClientVO.answer(59927004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?" id="NBS713L" >
Deep vein thrombosis (DVT) or pulmonary embolism (PE):</span></td><td>
<span id="NBS713" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS713)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have acute respiratory distress syndrome?" id="67782005L" >
ARDS:</span></td><td>
<span id="67782005" />
<nedss:view name="PageForm" property="pageClientVO.answer(67782005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have pneumonia?" id="233604007L" >
Pneumonia:</span></td><td>
<span id="233604007" />
<nedss:view name="PageForm" property="pageClientVO.answer(233604007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did patient develop CVA or stroke?" id="ARB021L" >
CVA or Stroke:</span></td><td>
<span id="ARB021" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB021)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have encephalitis?" id="31646008L" >
Encephalitis or aseptic meningitis:</span></td><td>
<span id="31646008" />
<nedss:view name="PageForm" property="pageClientVO.answer(31646008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have septic shock?" id="27942005L" >
Shock:</span></td><td>
<span id="27942005" />
<nedss:view name="PageForm" property="pageClientVO.answer(27942005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have hypotension?" id="45007003L" >
Hypotension:</span></td><td>
<span id="45007003" />
<nedss:view name="PageForm" property="pageClientVO.answer(45007003)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29004" name="Treatments Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive low flow nasal cannula?" id="466713001L" >
Low flow nasal cannula:</span></td><td>
<span id="466713001" />
<nedss:view name="PageForm" property="pageClientVO.answer(466713001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive high flow nasal cannula?" id="426854004L" >
High flow nasal cannula:</span></td><td>
<span id="426854004" />
<nedss:view name="PageForm" property="pageClientVO.answer(426854004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive non-invasive ventilation?" id="428311008L" >
Non-invasive ventilation:</span></td><td>
<span id="428311008" />
<nedss:view name="PageForm" property="pageClientVO.answer(428311008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive intubation?" id="52765003L" >
Intubation:</span></td><td>
<span id="52765003" />
<nedss:view name="PageForm" property="pageClientVO.answer(52765003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive mechanical ventilation?" id="NBS673L" >
Mechanical ventilation:</span></td><td>
<span id="NBS673" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS673)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive ECMO?" id="233573008L" >
ECMO:</span></td><td>
<span id="233573008" />
<nedss:view name="PageForm" property="pageClientVO.answer(233573008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?" id="NBS699L" >
Vasoactive medications:</span></td><td>
<span id="NBS699" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS699)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify the vasoactive medications the patient received." id="NBS700L">
Specify vasoactive medications:</span>
</td>
<td>
<span id="NBS700"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS700)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?" id="ARB040L" >
Steroids:</span></td><td>
<span id="ARB040" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB040)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?" id="ARB045L" >
Immune modulators:</span></td><td>
<span id="ARB045" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB045)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify immune modulators the patient received." id="373244000L">
Specify immune modulators:</span>
</td>
<td>
<span id="373244000"/>
<nedss:view name="PageForm" property="pageClientVO.answer(373244000)" />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?" id="372560006L" >
Antiplatelets:</span></td><td>
<span id="372560006" />
<nedss:view name="PageForm" property="pageClientVO.answer(372560006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify antiplatelets the patient received." id="372560006_OTHL">
Specify antiplatelets:</span>
</td>
<td>
<span id="372560006_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(372560006_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?" id="372862008L" >
Anticoagulation:</span></td><td>
<span id="372862008" />
<nedss:view name="PageForm" property="pageClientVO.answer(372862008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Specify anticoagulation the patient received?" id="372862008_OTHL">
Specify anticoagulation:</span>
</td>
<td>
<span id="372862008_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(372862008_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive hemodialysis?" id="302497006L" >
Dialysis:</span></td><td>
<span id="302497006" />
<nedss:view name="PageForm" property="pageClientVO.answer(302497006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive first intravenous immunoglobulin?" id="NBS701L" >
First IVIG:</span></td><td>
<span id="NBS701" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS701)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient receive second intravenous immunoglobulin?" id="NBS702L" >
Second IVIG:</span></td><td>
<span id="NBS702" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS702)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA40001" name="Vaccine Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Has the patient received a COVID-19 vaccine?" id="VAC126L" >
Has the patient received a COVID-19 vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine)." id="VAC132_CDL" >
If yes, how many doses?:</span></td><td>
<span id="VAC132_CD" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC132_CD)"
codeSetNm="VAC_DOSE_NUM_MIS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the first vaccine dose was received." id="NBS791L">Vaccine Dose 1 Received Date:</span>
</td><td>
<span id="NBS791"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS791)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the second vaccine dose was received." id="NBS792L">Vaccine Dose 2 Received Date:</span>
</td><td>
<span id="NBS792"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS792)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="COVID-19 vaccine manufacturer" id="VAC107L" >
COVID-19 vaccine manufacturer:</span></td><td>
<span id="VAC107" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC107)"
codeSetNm="MIS_VAC_MFGR"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="COVID-19 vaccine manufacturer" id="VAC107OthL">Other COVID-19 vaccine manufacturer:</span></td>
<td> <span id="VAC107Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(VAC107Oth)"/></td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27008" name="Blood Test Results" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS714L" title="If the patient had a fibrinogen result, enter the highest value.">
Fibrinogen Highest Value:</span>
</td><td>
<span id="NBS714"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS714)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a fibrinogen test, enter the unit of measure associated with the value." id="NBS715L" >
Fibrinogen Units:</span></td><td>
<span id="NBS715" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS715)"
codeSetNm="FIBRINOGEN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a fibrinogen result, indicate the interpretation." id="NBS716L" >
Fibrinogen Interpretation:</span></td><td>
<span id="NBS716" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS716)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS717L" title="If the patient had a CRP result, enter the highest value.">
CRP Highest Value:</span>
</td><td>
<span id="NBS717"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS717)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a CRP result enter the unit of measure associated with the value." id="NBS728L" >
CRP Unit:</span></td><td>
<span id="NBS728" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS728)"
codeSetNm="CRP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a CRP result, indicate the interpretation." id="NBS729L" >
CRP Interpretation:</span></td><td>
<span id="NBS729" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS729)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS730L" title="If the patient had a ferritin result, enter the highest value.">
Ferritin Test Highest Value:</span>
</td><td>
<span id="NBS730"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS730)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a ferritin result enter the unit of measure associated with the value." id="NBS731L" >
Ferritin Units:</span></td><td>
<span id="NBS731" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS731)"
codeSetNm="FERRITIN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a ferritin result, indicate the interpretation." id="NBS732L" >
Ferritin Interpretation:</span></td><td>
<span id="NBS732" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS732)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS733L" title="If the patient had a troponin result, enter the highest value.">
Troponin Highest Value:</span>
</td><td>
<span id="NBS733"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS733)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a troponin result, enter the unit of measure associated with the value." id="NBS734L" >
Troponin Units:</span></td><td>
<span id="NBS734" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS734)"
codeSetNm="TROPONIN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a troponin result, indicate the interpretation." id="NBS735L" >
Troponin Interpretation:</span></td><td>
<span id="NBS735" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS735)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS736L" title="If the patient had a BNP result, enter the highest value.">
BNP Highest Value:</span>
</td><td>
<span id="NBS736"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS736)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a BNP result, enter the unit of measure associated with the value." id="NBS737L" >
BNP Units:</span></td><td>
<span id="NBS737" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS737)"
codeSetNm="BNP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a BNP result, indicate the interpretation." id="NBS738L" >
BNP Interpretation:</span></td><td>
<span id="NBS738" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS738)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS739L" title="If the patient had a NT-proBNP result, enter the highest value.">
NT-proBNP Test Highest Value:</span>
</td><td>
<span id="NBS739"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS739)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a NT-proBNP result, enter the unit of measure associated with the value." id="NBS740L" >
NT-proBNP Units:</span></td><td>
<span id="NBS740" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS740)"
codeSetNm="BNP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a NT-proBNP result, indicate the interpretation." id="NBS741L" >
NT-proBNP Interpretation:</span></td><td>
<span id="NBS741" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS741)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS742L" title="If the patient had a D-dimer result, enter the highest value.">
D-dimer Highest Value:</span>
</td><td>
<span id="NBS742"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS742)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a D-dimer result, enter the unit of measure associated with the value." id="NBS743L" >
D-dimer Units:</span></td><td>
<span id="NBS743" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS743)"
codeSetNm="D_DIMER_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a D-dimer result, indicate the interpretation." id="NBS744L" >
D-dimer Interpretation:</span></td><td>
<span id="NBS744" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS744)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS745L" title="If the patient had a IL-6 result, enter the highest value.">
IL-6 Highest Value:</span>
</td><td>
<span id="NBS745"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS745)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a IL-6 result, enter the unit of measure associated with the value." id="NBS746L" >
IL-6 Unit:</span></td><td>
<span id="NBS746" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS746)"
codeSetNm="IL_6_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a IL-6 result, indicate the interpretation." id="NBS747L" >
IL-6 Interpretation:</span></td><td>
<span id="NBS747" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS747)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS748L" title="If the patient had a serum white blood count result, enter the test interpretation.">
Serum White Blood Count Highest Value:</span>
</td><td>
<span id="NBS748"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS748)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS749L" title="If the patient had a serum white blood count result, enter the lowest value.">
Serum White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS749"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS749)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a serum white blood count result, enter the unit of measure associated with the value." id="NBS750L" >
Serum White Blood Count Units:</span></td><td>
<span id="NBS750" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS750)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS751L" title="If the patient had a platelets result, enter the highest value.">
Platelets Highest Value:</span>
</td><td>
<span id="NBS751"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS751)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS752L" title="If the patient had a platelets result, enter the lowest value.">
Platelets Lowest Value:</span>
</td><td>
<span id="NBS752"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS752)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a platelets result, enter the unit of measure associated with the value." id="NBS753L" >
Platelets Units:</span></td><td>
<span id="NBS753" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS753)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS754L" title="If the patient had a neutrophils result, enter the highest value.">
Neutrophils Highest Value:</span>
</td><td>
<span id="NBS754"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS754)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS755L" title="If the patient had a neutrophils result, enter the lowest value.">
Neutrophils Lowest Value:</span>
</td><td>
<span id="NBS755"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS755)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a neutrophils result, enter the unit of measure associated with the value." id="NBS756L" >
Neutrophils Units:</span></td><td>
<span id="NBS756" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS756)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS757L" title="If the patient had a lymphocytes result, enter the highest value.">
Lymphocytes Highest Value:</span>
</td><td>
<span id="NBS757"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS757)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS758L" title="If the patient had a lymphocytes result, enter the lowest value.">
Lymphocytes Lowest Value:</span>
</td><td>
<span id="NBS758"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS758)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a lymphocytes result, enter the unit of measure associated with the value." id="NBS759L" >
Lymphocytes Units:</span></td><td>
<span id="NBS759" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS759)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS760L" title="If the patient had a bands test result, enter the highest value.">
Bands Test Highest Value:</span>
</td><td>
<span id="NBS760"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS760)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS761L" title="If the patient had a band test result, enter the lowest value.">
Bands Test Lowest Value:</span>
</td><td>
<span id="NBS761"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS761)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a bands test result, enter the unit of measure associated with the value." id="NBS762L" >
Bands Test Units:</span></td><td>
<span id="NBS762" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS762)"
codeSetNm="BANDS_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27009" name="CSF Studies" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS763L" title="If the patient had a white blood count result, enter the highest value.">
White Blood Count Highest Value:</span>
</td><td>
<span id="NBS763"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS763)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS764L" title="If the patient had a white blood count result, enter the lowest value.">
White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS764"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS764)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a white blood count result, enter the unit of measure associated with the value." id="NBS765L" >
White Blood Count Units:</span></td><td>
<span id="NBS765" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS765)"
codeSetNm="WBC_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS766L" title="If the patient had a protein test result, enter the highest value.">
Protein Test Highest Value:</span>
</td><td>
<span id="NBS766"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS766)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS767L" title="If the patient had a protein test result, enter the lowest value.">
Protein Test Lowest Value:</span>
</td><td>
<span id="NBS767"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS767)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a protein test result, enter the unit of measure associated with the value." id="NBS768L" >
Protein Test Units:</span></td><td>
<span id="NBS768" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS768)"
codeSetNm="PROTEIN_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS769L" title="If the patient had a glucose result, enter the highest value.">
Glucose Highest Value:</span>
</td><td>
<span id="NBS769"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS769)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS770L" title="If the patient had a glucose test result, enter the lowest value.">
Glucose Lowest Value:</span>
</td><td>
<span id="NBS770"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS770)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a glucose result, enter the unit of measure associated with the value." id="NBS771L" >
Glucose Units:</span></td><td>
<span id="NBS771" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS771)"
codeSetNm="GLUCOSE_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27010" name="Urinalysis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS772L" title="If the patient had a urine while blood count result, enter the highest value.">
Urine White Blood Count Highest Value:</span>
</td><td>
<span id="NBS772"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS772)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS773L" title="If the patient had a urine white blood count result, enter the lowest value.">
Urine White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS773"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS773)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient had a urine white blood count result, enter the unit of measure associated with the value." id="NBS774L" >
Urine White Blood Count Units:</span></td><td>
<span id="NBS774" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS774)"
codeSetNm="URINE_WBC_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27011" name="Echocardiogram" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="40701008L" title="Indicate echocardiogram result.">
Echocardiogram result:</span></td><td>
<span id="40701008" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(40701008)"
codeSetNm="ECHOCARDIOGRAM_RESULT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Indicate echocardiogram result." id="40701008OthL">Other Echocardiogram result:</span></td>
<td> <span id="40701008Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(40701008Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Indicate max coronary artery Z-score." id="373065002L">
Max coronary artery Z-score:</span>
</td>
<td>
<span id="373065002"/>
<nedss:view name="PageForm" property="pageClientVO.answer(373065002)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate cardiac dysfunction type." id="NBS703L" >
Cardiac dysfunction type:</span></td><td>
<span id="NBS703" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS703)"
codeSetNm="CARDIAC_DYSFUNCTION_TYPE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date of first test showing coronary artery aneurysm or dilatation." id="NBS704L">Date of first test showing coronary artery aneurysm or dilatation:</span>
</td><td>
<span id="NBS704"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS704)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate mitral regurgitation type." id="18113_1L" >
Mitral regurgitation type:</span></td><td>
<span id="18113_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(18113_1)"
codeSetNm="MITRAL_REGURG_TYPE"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27012" name="Abdominal Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was abdominal imaging was completed for this patient?" id="441987005L" >
Was abdominal imaging done?:</span></td><td>
<span id="441987005" />
<nedss:view name="PageForm" property="pageClientVO.answer(441987005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS705L" title="Indicate abdominal imaging type.">
Abdominal imaging type:</span></td><td>
<span id="NBS705" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS705)"
codeSetNm="ABD_IMAGING_TYPE_MIS"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS706L" title="Indicate abdominal imaging results.">
Abdominal imaging results:</span></td><td>
<span id="NBS706" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS706)"
codeSetNm="ABD_IMAGING_RSLT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Indicate abdominal imaging results." id="NBS706OthL">Other Abdominal imaging results:</span></td>
<td> <span id="NBS706Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS706Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27013" name="Chest Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was chest imaging completed for this patient?" id="413815006L" >
Was chest imaging done?:</span></td><td>
<span id="413815006" />
<nedss:view name="PageForm" property="pageClientVO.answer(413815006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="LAB677L" title="Indicate the type of chest study performed. Please provide a response for each of the main test types (plain chest radiograph, chest CT Scan) and if test was not done please indicate so.">
Type of Chest Study:</span></td><td>
<span id="LAB677" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(LAB677)"
codeSetNm="CHEST_IMAGING_TYPE_MIS"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="LAB678L" title="Result of chest diagnostic testing">
Result of Chest Study:</span></td><td>
<span id="LAB678" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(LAB678)"
codeSetNm="CHEST_IMAGING_RSLT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Result of chest diagnostic testing" id="LAB678OthL">Other Result of Chest Study:</span></td>
<td> <span id="LAB678Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB678Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27015" name="SARS-COV-2 Testing Details" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;If multiple tests were performed for a given test type, enter the date for the first positive.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="RT-PCR Test Result" id="NBS718L" >
RT-PCR Test Result:</span></td><td>
<span id="NBS718" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS718)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="RT-PCR Test Date" id="NBS719L">RT-PCR Test Date:</span>
</td><td>
<span id="NBS719"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS719)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antigen Test Result" id="NBS720L" >
Antigen Test Result:</span></td><td>
<span id="NBS720" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS720)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Antigen Test Date" id="NBS721L">Antigen Test Date:</span>
</td><td>
<span id="NBS721"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS721)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgG Test Result" id="NBS722L" >
IgG Test Result:</span></td><td>
<span id="NBS722" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS722)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="IgG Test Date" id="NBS723L">IgG Test Date:</span>
</td><td>
<span id="NBS723"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS723)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgM Test Result" id="NBS724L" >
IgM Test Result:</span></td><td>
<span id="NBS724" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS724)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="IgM Test Date" id="NBS725L">IgM Test Date:</span>
</td><td>
<span id="NBS725"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS725)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgA Test Result" id="NBS726L" >
IgA Test Result:</span></td><td>
<span id="NBS726" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS726)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="IgA Test Date" id="NBS727L">IgA Test Date:</span>
</td><td>
<span id="NBS727"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS727)"  />
</td></tr>
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
