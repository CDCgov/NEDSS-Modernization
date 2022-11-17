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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Epidemiologic","General Comments","Clinical Data","Diagnostic Tests","Hepatitis D Infection","Contact with Case","Sexual and Drug Exposures","Exposures Prior to Onset","Hepatitis Treatment","Vaccination History","Contact Investigation"};
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
<span title="Country of Birth" id="DEM126L" >
Country of Birth:</span></td><td>
<span id="DEM126" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM126)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

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
<nedss:container id="NBS_INV_HEP_UI_1" name="Reporting County" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_INV_HEP_UI_2" name="Exposure Location" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_HEP_UI_2"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_HEP_UI_2">
<tr id="patternNBS_INV_HEP_UI_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_HEP_UI_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_HEP_UI_2">
<tr id="nopatternNBS_INV_HEP_UI_2" class="odd" style="display:none">
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
<nedss:container id="NBS_INV_HEP_UI_3" name="Binational Reporting" isHidden="F" classType="subSect" >

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
<span title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition." id="INV176L">Date CDC was First Verbally Notified of This Case:</span>
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
<nedss:container id="NBS_INV_HEP_UI_5" name="Reason for Testing" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV575L" title="Listing of the reason(s) the subject was tested for the condition">
Reason for Testing (check all that apply):</span></td><td>
<span id="INV575" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV575)"
codeSetNm="PHVS_REASONFORTEST_HEPATITIS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Other reason(s) the patient was tested for hepatitis." id="INV901L">
Other Reason for Testing:</span>
</td>
<td>
<span id="INV901"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV901)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Clinical Data" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of diagnosis of condition being reported to public health system." id="INV136L">Diagnosis Date:</span>
</td><td>
<span id="INV136"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV136)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient symptomatic for the illness of interest?" id="INV576L" >
Is patient symptomatic?:</span></td><td>
<span id="INV576" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

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
<span title="At the time of diagnosis, was the patient jaundiced?" id="INV578L" >
Was the patient jaundiced?:</span></td><td>
<span id="INV578" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV578)"
codeSetNm="YNU"/>
</td> </tr>

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient aware s/he had hepatitis prior to lab testing?" id="INV650L" >
Was the patient aware s/he had hepatitis prior to lab testing?:</span></td><td>
<span id="INV650" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV650)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the subject have a provider of care for the condition?  This is any healthcare provider that monitors or treats the patient for the condition." id="INV651L" >
Does the patient have a provider of care for hepatitis?:</span></td><td>
<span id="INV651" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV651)"
codeSetNm="YNU"/>
</td> </tr>

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate whether the patient has diabetes" id="INV887L" >
Does the patient have diabetes?:</span></td><td>
<span id="INV887" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV887)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If subject has diabetes, date of diabetes diagnosis." id="INV842L">Diabetes Diagnosis Date:</span>
</td><td>
<span id="INV842"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV842)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEP_UI_6" name="Liver Enzyme Levels at Time of Diagnosis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="1742_6L" title="Enter the ALT/SGPT result">
ALT [SGPT] Result:</span>
</td><td>
<span id="1742_6"/>
<nedss:view name="PageForm" property="pageClientVO.answer(1742_6)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of specimen collection of the ALT liver enzyme lab test result." id="INV826L">Specimen Collection Date (ALT):</span>
</td><td>
<span id="INV826"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV826)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV827L" title="Enter the upper limit normal value (numeric) for the ALT liver enzyme test result.">
Test Result Upper Limit Normal (ALT):</span>
</td><td>
<span id="INV827"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV827)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="11920_8L" title="Enter patient's AST/SGOT result">
AST [SGOT] Result:</span>
</td><td>
<span id="11920_8"/>
<nedss:view name="PageForm" property="pageClientVO.answer(11920_8)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of specimen collection of the AST liver enzyme lab test result." id="INV826bL">Specimen Collection Date (AST):</span>
</td><td>
<span id="INV826b"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV826b)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV827bL" title="Enter the upper limit normal value (numeric) for the AST liver enzyme test result.">
Test Result Upper Limit Normal (AST):</span>
</td><td>
<span id="INV827b"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV827b)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEP_UI_8" name="Diagnostic Test Results" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of specimen collection for total anti-HAV test result" id="LP38316_3_DTL">Specimen Collection Date (anti-HAV):</span>
</td><td>
<span id="LP38316_3_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38316_3_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Total antibody to Hepatitis A virus result" id="LP38316_3L" >
total anti-HAV Result:</span></td><td>
<span id="LP38316_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38316_3)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of IgM anti-HAV test result" id="LP38318_9_DTL">Specimen Collection Date (IgM anti-HAV):</span>
</td><td>
<span id="LP38318_9_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38318_9_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgM antibody to Hepatitis A virus result" id="LP38318_9L" >
IgM anti-HAV Result:</span></td><td>
<span id="LP38318_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38318_9)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen Collection Date for HBsAg test" id="LP38331_2_DTL">Specimen Collection Date (HBsAg):</span>
</td><td>
<span id="LP38331_2_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38331_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis B virus surface antigen result" id="LP38331_2L" >
HBsAg Result:</span></td><td>
<span id="LP38331_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38331_2)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of total anti-HBc test result" id="LP38323_9_DTL">Specimen Collection Date (total anti-HBc):</span>
</td><td>
<span id="LP38323_9_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38323_9_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="total antibody to Hepatitis B core antigen result" id="LP38323_9L" >
total anti-HBc Result:</span></td><td>
<span id="LP38323_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38323_9)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of IgM anti-HBc test result" id="LP38325_4_DTL">Specimen Collection Date (IgM anti-HBc):</span>
</td><td>
<span id="LP38325_4_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38325_4_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="IgM antibody to hepatitis B core antigen result" id="LP38325_4L" >
IgM anti-HBc Result:</span></td><td>
<span id="LP38325_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38325_4)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of HBV DNA/NAT test" id="LP38320_5_DTL">Specimen Collection Date (HEP B DNA/NAT):</span>
</td><td>
<span id="LP38320_5_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38320_5_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis B DNA-containing test (Nucleic Acid Test (NAT)) result" id="LP38320_5L" >
HEP B DNA/NAT Result:</span></td><td>
<span id="LP38320_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38320_5)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of HBeAg test" id="LP38329_6_DTL">Specimen Collection Date (HBeAg):</span>
</td><td>
<span id="LP38329_6_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38329_6_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis B virus e Antigen result" id="LP38329_6L" >
HBeAg Result:</span></td><td>
<span id="LP38329_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38329_6)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date of total anti-HCV" id="LP38332_0_DTL">Specimen Collection Date (total anti-HCV):</span>
</td><td>
<span id="LP38332_0_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38332_0_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis C virus Ab result" id="LP38332_0L" >
total anti-HCV Result:</span></td><td>
<span id="LP38332_0" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38332_0)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the antibody test to Hepatitis C Virus (anti-HCV) was positive or negative, enter the signal to cut-off ratio." id="INV841L">
anti-HCV signal to cut-off ratio:</span>
</td>
<td>
<span id="INV841"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV841)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date for supplemental anti-HCV assay" id="5199_5_DTL">Specimen Collection Date (supplemental anti-HCV assay):</span>
</td><td>
<span id="5199_5_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(5199_5_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="supplemental anti-HCV assay (e.g. RIBA) result" id="5199_5L" >
Supplemental anti-HCV Assay Result:</span></td><td>
<span id="5199_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(5199_5)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date for HCV RNA test" id="LP38335_3_DTL">Specimen Collection Date (HCV RNA):</span>
</td><td>
<span id="LP38335_3_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38335_3_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Hepatitis C virus RNA result" id="LP38335_3L" >
HCV RNA Result:</span></td><td>
<span id="LP38335_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38335_3)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date for total anti-HDV test" id="LP38345_2_DTL">Specimen Collection Date (total anti-HDV):</span>
</td><td>
<span id="LP38345_2_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38345_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Total Hepatitis D virus Ab result" id="LP38345_2L" >
anti-HDV Result:</span></td><td>
<span id="LP38345_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38345_2)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Specimen collection date for total anti-HEV test" id="LP38350_2_DTL">Specimen Collection Date (total anti-HEV):</span>
</td><td>
<span id="LP38350_2_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38350_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Total Hepatitis E virus Ab result" id="LP38350_2L" >
anti-HEV Result:</span></td><td>
<span id="LP38350_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38350_2)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Note: The question regarding a negative hepatitis-related tests refers to the condition being reported. If this is an acute hepatitis B case, indicate if the patient had a negative hepatitis B-related test in the previous 6 months.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a negative hepatitis-related test in the previous 6 months? For Hep B: Did patient have a negative HBsAg test in the previous 6 months? For Hep C: Did patient have a negative HCV antibody test in the previous 6 months?" id="INV832L" >
Did the patient have a negative hepatitis-related test in the previous 6 months?:</span></td><td>
<span id="INV832" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV832)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If the patient had a previous negative hepatitis test within 6 months, enter the date of the test." id="INV843L">Verified Test Date:</span>
</td><td>
<span id="INV843"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV843)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_2" name="Hepatitis D Infection" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient tested for Hepatitis D" id="INV840L" >
Was the patient tested for hepatitis D?:</span></td><td>
<span id="INV840" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV840)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have a co-infection with Hepatitis D?" id="INV831L" >
Did the patient have a co-infection with hepatitis D?:</span></td><td>
<span id="INV831" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV831)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_5" name="Contact with a Case" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;The time period of interest differs for Acute Hepatitis B and C. For Hepatitis B, the time period is 6 weeks - 6 months prior to onset of of symptoms. For Hepatitis C, the time period is 2 weeks - 6 months prior to onset of symptoms.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During the time period prior to the onset of symptoms, was the subject a contact of a person with confirmed or suspected hepatitis virus infection? For hepatitis B, the time period is 6 weeks to 6 months. For hepatitis C, it is 2 weeks to 6 months." id="INV602L" >
During the time period prior to onset, was patient a contact of a case?:</span></td><td>
<span id="INV602" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV602)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_7" name="Types of Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact type sexual?" id="INV603_5L" >
Sexual (Contact Type):</span></td><td>
<span id="INV603_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_5)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient is a contact of a confirmed or suspected case, was the contact type  household non-sexual)?" id="INV603_3L" >
Household (Non-Sexual) (Contact Type):</span></td><td>
<span id="INV603_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_3)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact type other?" id="INV603_6L" >
Other (Contact Type):</span></td><td>
<span id="INV603_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If contact type with a confirmed or suspected case was 'other', specify other contact type." id="INV897L">
Other Contact Type (Specify):</span>
</td>
<td>
<span id="INV897"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV897)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_9" name="Sexual Exposures in Prior 6 Months" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Please enter the sexual preference of the patient." id="INV592L" >
What is the sexual preference of the patient?:</span></td><td>
<span id="INV592" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV592)"
codeSetNm="PHVS_SEXUALPREFERENCE_NETSS"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Note: If 0 is selected on the form, enter 0; if 1 is selected on the form, enter 1; if 2-5 is selected on the form, enter 2; if &gt;5 is selected on the form, enter 6.</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 6 months before symptom onset, how many:</span></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV605L" title="During the 6 months prior to the onset of symptoms, number of male sex partners the person had.">
Male Sex Partners Did the Patient Have:</span>
</td><td>
<span id="INV605"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV605)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV606L" title="During the 6 months prior to the onset of symptoms, number of female sex partners the person had.">
Female Sex Partners Did the Patient Have:</span>
</td><td>
<span id="INV606"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV606)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient ever treated for a sexually transmitted disease?" id="INV653bL" >
Was the patient ever treated for a sexually transmitted disease?:</span></td><td>
<span id="INV653b" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV653b)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV654L" title="If the subject was ever treated for a sexually-transmitted diases, in what year was the most recent treatment?">
If yes, in what year was the most recent treatment?:</span>
</td><td>
<span id="INV654"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV654)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_12" name="Blood Exposures Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;The time period of interest differs for Acute Hepatitis B and C. For Hepatitis B, the time period is 6 weeks - 6 months prior to onset of of symptoms. For Hepatitis C, the time period is 2 weeks - 6 months prior to onset of symptoms.</span></td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;During the time period prior to onset, did the patient:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient undergo hemodialysis? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV583L" >
Undergo Hemodialysis:</span></td><td>
<span id="INV583" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV583)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient have an accidental stick or puncture with a needle or other object contaminated with blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV655L" >
Have an Accidental Stick or Puncture With a Needle or Other Object Contaminated With Blood:</span></td><td>
<span id="INV655" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV655)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient receive blood or blood products (transfusion)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV580L" >
Receive Blood or Blood Products (Transfusion):</span></td><td>
<span id="INV580" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV580)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date the subject began receiving blood or blood products (transfusion) prior to symptom onset. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV614L">If Yes, Date of Transfusion:</span>
</td><td>
<span id="INV614"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV614)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient receive any IV infusions and/or injections in an outpatient setting? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV620L" >
Receive Any IV Infusions and/or Injections in the Outpatient Setting:</span></td><td>
<span id="INV620" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV620)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient have other exposure to someone else's blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV617L" >
Have Other Exposure to Someone Else's Blood:</span></td><td>
<span id="INV617" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV617)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="If patient had exposure to someone else's blood prior to symptom onset, specify the other blood exposure." id="INV898L">
Other Blood Exposure (Specify):</span>
</td>
<td>
<span id="INV898"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV898)"  />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the patient employed in a medical or dental field involving direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV590L" >
Was the patient employed in a medical or dental field involving contact with human blood?:</span></td><td>
<span id="INV590" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV590)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Subject's frequency of blood contact as an employee in a medical or dental field involving direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months" id="INV594L" >
If Yes, Frequency of Direct Blood Contact:</span></td><td>
<span id="INV594" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV594)"
codeSetNm="PHVS_BLOODCONTACTFREQUENCY_HEPATITIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the subject employed as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months.  For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV595L" >
Was the patient employed as a public safety worker having direct contact with human blood?:</span></td><td>
<span id="INV595" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV595)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Subject's frequency of blood contact as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV596L" >
If Yes, Frequency of Direct Blood Contact:</span></td><td>
<span id="INV596" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV596)"
codeSetNm="PHVS_BLOODCONTACTFREQUENCY_HEPATITIS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_16" name="Tattooing/Drugs/Piercing" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the time period prior to onset:</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient receive a tattoo? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV597L" >
Did the patient receive a tattoo?:</span></td><td>
<span id="INV597" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV597)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV598L" title="Where was the tattooing performed (check all that apply)">
Where was the tattooing performed (check all that apply)?:</span></td><td>
<span id="INV598" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV598)"
codeSetNm="PHVS_TATTOOOBTAINEDFROM_HEPATITIS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="specify other location(s) where the patient received a tattoo" id="INV900L">
Other Location(s) Tattoo Received:</span>
</td>
<td>
<span id="INV900"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV900)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During the timeperiod prior to the onset of symptoms, did the subject inject drugs?" id="INV607L" >
Inject Drugs Not Prescribed By a Doctor:</span></td><td>
<span id="INV607" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV607)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During the time period prior to the onset of symptoms, did the subject use street drugs but not inject?" id="INV608L" >
Use Street Drugs But Not Inject:</span></td><td>
<span id="INV608" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV608)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient have any part of their body pierced (other than ear)?" id="INV622L" >
Did the patient have any part of their body pierced (other than ear)?:</span></td><td>
<span id="INV622" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV622)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV623L" title="Where was the piercing performed (check all that apply)">
Where was the piercing performed (check all that apply)?:</span></td><td>
<span id="INV623" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV623)"
codeSetNm="PHVS_TATTOOOBTAINEDFROM_HEPATITIS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Specify other location(s) where the patient received a piercing" id="INV899L">
Other Location(s) Piercing Received:</span>
</td>
<td>
<span id="INV899"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV899)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_18" name="Other Healthcare Exposure" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient have dental work or oral surgery? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV633L" >
Did the patient have dental work or oral surgery?:</span></td><td>
<span id="INV633" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV633)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, did the patient have surgery (other than oral surgery)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV634L" >
Did the patient have surgery (other than oral surgery)?:</span></td><td>
<span id="INV634" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV634)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the patient hospitalized? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV635L" >
Was the patient hospitalized?:</span></td><td>
<span id="INV635" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV635)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the patient a resident of a long-term care facility? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV636L" >
Was the patient a resident of a long-term care facility?:</span></td><td>
<span id="INV636" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV636)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Prior to the onset of symptoms, was the patient incarcerated for longer than 24 hours? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV637L" >
Was the patient incarcerated for longer than 24 hours?:</span></td><td>
<span id="INV637" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV637)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_19" name="Incarceration Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If patient was incarcerated, indicate if the incarceration type was prison." id="INV638_3L" >
Prison:</span></td><td>
<span id="INV638_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV638_3)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If patient was incarcerated, what type of facility?" id="INV638_1L" >
Jail:</span></td><td>
<span id="INV638_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV638_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If patient was incarcerated, indicate the type of facility?" id="INV638_2L" >
Juvenile Facility:</span></td><td>
<span id="INV638_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV638_2)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_20" name="Incarceration More than 6 Months" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During his/her lifetime, was the patient EVER incarcerated for more than 6 months?" id="INV639L" >
Was the patient ever incarcerated for longer than 6 months?:</span></td><td>
<span id="INV639" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV639)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV640L" title="Enter the 4-digit year the patient was most recently incarcerated for longer than 6 months.">
If yes, what year was the most recent incarceration?:</span>
</td><td>
<span id="INV640"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV640)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV641L" title="If the patient was incarcerated for longer than 6 months, enter the length of time of incarceration in months">
If yes, for how long (answer in months)?:</span>
</td><td>
<span id="INV641"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV641)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_22" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Has the subject ever received medication for the type of Hepatitis being reported?" id="INV652L" >
Has the patient received medication for the type of hepatitis being reported?:</span></td><td>
<span id="INV652" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV652)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_24" name="Hepatitis B Vaccination" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the patient ever receive hepatitis B vaccine?" id="VAC126L" >
Did the patient ever receive hepatitis B vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC132L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
If yes, how many doses?:</span>
</td><td>
<span id="VAC132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC132)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC142bL" title="Enter the 4 digit year of when the last dose of vaccine for the condition being investigated was received.">
In what year was the last dose received?:</span>
</td><td>
<span id="VAC142b"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC142b)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient tested for antibody to HBsAg (anti-HBS) within 1-2 months after the last does of vaccine?" id="HEP190L" >
Was patient tested for antibody to HBsAg (anti-HBs) within 1-2 mos after last dose?:</span></td><td>
<span id="HEP190" />
<nedss:view name="PageForm" property="pageClientVO.answer(HEP190)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the serum anti-HBs &gt;= 10ml U/ml?  (Answer 'Yes' if lab result reported as positive or reactive.)" id="HEP191L" >
Was the serum anti-HBs &gt;= 10ml U/ml?:</span></td><td>
<span id="HEP191" />
<nedss:view name="PageForm" property="pageClientVO.answer(HEP191)"
codeSetNm="YNU"/>
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
