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
String tabId = "viewPatient";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Patient Information"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_6" name="General Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span style="color:#CC0000">*</span>
<span title="As of Date is the last known date for which the information is valid." id="NBS104L">Information As of Date:</span>
</td><td>
<span id="NBS104"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS104)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
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
<tr><td valign="top" class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS095L">Name Information As Of Date:</span>
</td><td>
<span id="NBS095"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS095)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The patient's first name." id="DEM104L">
First Name:</span>
</td>
<td>
<span id="DEM104"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM104)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The patient's middle name or initial." id="DEM105L">
Middle Name:</span>
</td>
<td>
<span id="DEM105"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM105)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The patient's last name." id="DEM102L">
Last Name:</span>
</td>
<td>
<span id="DEM102"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM102)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
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
<tr><td valign="top" class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS096L">Other Personal Details As Of Date:</span>
</td><td>
<span id="NBS096"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS096)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Reported date of birth of patient." id="DEM115L">Date of Birth:</span>
</td><td>
<span id="DEM115"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM115)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV2001L" title="The patient's age reported at the time of interview.">
Reported Age:</span>
</td><td>
<span id="INV2001"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV2001)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Patient's age units" id="INV2002L" >
Reported Age Units:</span></td><td>
<span id="INV2002" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV2002)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Patient's current sex." id="DEM113L" >
Current Sex:</span></td><td>
<span id="DEM113" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM113)"
codeSetNm="SEX"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Country of Birth" id="DEM126L" >
Country of Birth:</span></td><td>
<span id="DEM126" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM126)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS097L">Mortality Information As Of Date:</span>
</td><td>
<span id="NBS097"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS097)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicator of whether or not a patient is alive or dead." id="DEM127L" >
Is the patient deceased?:</span></td><td>
<span id="DEM127" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM127)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date on which the individual died." id="DEM128L">Deceased Date:</span>
</td><td>
<span id="DEM128"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM128)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS098L">Marital Status As Of Date:</span>
</td><td>
<span id="NBS098"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS098)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
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
<tr><td valign="top" class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS102L">Address Information As Of Date:</span>
</td><td>
<span id="NBS102"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS102)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Line one of the address label." id="DEM159L">
Street Address 1:</span>
</td>
<td>
<span id="DEM159"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM159)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Line two of the address label." id="DEM160L">
Street Address 2:</span>
</td>
<td>
<span id="DEM160"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The city for a postal location." id="DEM161L">
City:</span>
</td>
<td>
<span id="DEM161"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM161)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The state code for a postal location." id="DEM162L" >
State:</span></td><td>
<span id="DEM162" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM162)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The zip code of a residence of the case patient or entity." id="DEM163L">
Zip:</span>
</td>
<td>
<span id="DEM163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM163)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
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
<td class="fieldName" valign="top">
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
<tr><td valign="top" class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS103L">Telephone Information As Of Date:</span>
</td><td>
<span id="NBS103"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS103)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The patient's home phone number." id="DEM177L">
Home Phone:</span>
</td>
<td>
<span id="DEM177"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM177)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The patient's work phone number." id="NBS002L">
Work Phone:</span>
</td>
<td>
<span id="NBS002"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS002)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS003L" title="The patient's work phone number extension.">
Ext.:</span>
</td><td>
<span id="NBS003"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS003)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The patient's cellular phone number." id="NBS006L">
Cell Phone:</span>
</td>
<td>
<span id="NBS006"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS006)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
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
<tr><td valign="top" class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS100L">Ethnicity Information As Of Date:</span>
</td><td>
<span id="NBS100"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS100)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if the patient is hispanic or not." id="DEM155L" >
Ethnicity:</span></td><td>
<span id="DEM155" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM155)"
codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS101L">Race Information As Of Date:</span>
</td><td>
<span id="NBS101"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS101)"  />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td valign="top" class="fieldName">
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
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
