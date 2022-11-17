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
String [] sectionNames  = {"Patient Information","Address Information","Telephone and Email Contact Information","Race and Ethnicity Information","Other Identifying Information","Investigation Information","OOJ Initiating Agency Information","Reporting Information","Clinical","Epidemiologic","Comments","Case Numbers","Initial Follow-up","Surveillance","Field Follow-up Information","Case Closure","Congenital Syphilis Information","Maternal Information","Infant Information","Contact Investigation"};
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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's alias or nickname." id="DEM250L">
Alias/Nickname:</span>
</td>
<td>
<span id="DEM250"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM250)" />
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's current sex if identified as unknown (i.e., not male or female)." id="NBS272L" >
Unknown Reason:</span></td><td>
<span id="NBS272" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS272)"
codeSetNm="SEX_UNK_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's transgender identity." id="NBS274L" >
Gender Identity/Transgender Info:</span></td><td>
<span id="NBS274" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS274)"
codeSetNm="NBS_STD_GENDER_PARPT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The specific gender information of the index patient if other selections do not apply (i.e. intersex, two-spirited, etc.)." id="NBS213L">
Additional Gender:</span>
</td>
<td>
<span id="NBS213"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS213)" />
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
<!--skipping Hidden Date Question-->
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="DEM140L" title="A code indicating the married or similar partnership status of a patient.">Marital Status:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(DEM140)" styleId="DEM140"><html:optionsCollection property="codedValue(P_MARITAL)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV178L" title="Assesses whether or not the patient is pregnant.">Is the patient pregnant?:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Numeric Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts." id="DEM168L">
Census Tract:</span>
</td>
<td>
<span id="DEM168"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM168)" />
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
<nedss:container id="NBS_INV_STD_UI_2" name="Additional Residence Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The RELATIONSHIP (such as spouse, parents, sibling, partner, roommate, etc., not the name) of those living with the patient." id="NBS201L">
Living With:</span>
</td>
<td>
<span id="NBS201"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS201)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The type of residence in which the patient currenlty resides." id="NBS202L" >
Type of Residence:</span></td><td>
<span id="NBS202" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS202)"
codeSetNm="RESIDENCE_TYPE_STD"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS203L" title="The length of time the patient has lived at the current address.">
Time at Address:</span>
</td><td>
<span id="NBS203"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS203)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time at address." id="NBS204L" >
Units:</span></td><td>
<span id="NBS204" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS204)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS205L" title="The length of time the patient has lived in this state/territory.">
Time in State:</span>
</td><td>
<span id="NBS205"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS205)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time in state." id="NBS206L" >
Units:</span></td><td>
<span id="NBS206" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS206)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS207L" title="The length of time the patient has lived in the country.">
Time in Country:</span>
</td><td>
<span id="NBS207"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS207)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time in country." id="NBS208L" >
Units:</span></td><td>
<span id="NBS208" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS208)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the patient is institutionalized (i.e., in jail, in a group home, in a mental health facility, etc.)" id="NBS209L" >
Currently institutionalized:</span></td><td>
<span id="NBS209" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS209)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Name of Institutition" id="NBS210L">
If institutionalized, document the name of the facility.:</span>
</td>
<td>
<span id="NBS210"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS210)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of Institutition" id="NBS211L" >
Type of Institutition:</span></td><td>
<span id="NBS211" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS211)"
codeSetNm="INSTITUTION_TYPE_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Specify reason the patient's ethnicity is unknown." id="NBS273L" >
Reason Unknown:</span></td><td>
<span id="NBS273" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS273)"
codeSetNm="P_ETHN_UNK_REASON"/>
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

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_6" name="Other Identifying Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific height of the patient." id="NBS155L">
Height:</span>
</td>
<td>
<span id="NBS155"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS155)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific weight or body type of the patient." id="NBS156L">
Size/Build:</span>
</td>
<td>
<span id="NBS156"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS156)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The description of the patients hair, including color, length, and/or style." id="NBS157L">
Hair:</span>
</td>
<td>
<span id="NBS157"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS157)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific skin tone/hue of the patient." id="NBS158L">
Complexion:</span>
</td>
<td>
<span id="NBS158"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS158)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Any additional demographic information (e.g., tattoos, etc)." id="NBS159L">
Other Identifying Info:</span>
</td>
<td>
<span id="NBS159"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS159)"  />
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Document the reason (referral basis) why the investigation was initiated." id="NBS110L" >
Referral Basis:</span></td><td>
<span id="NBS110" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS110)"
codeSetNm="REFERRAL_BASIS"/>
</td> </tr>

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)" id="NBS115L" >
Current Process Stage:</span></td><td>
<span id="NBS115" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS115)"
codeSetNm="CM_PROCESS_STAGE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation was started or initiated." id="INV147L">Investigation Start Date:</span>
</td><td>
<span id="INV147"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV147)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation is closed." id="INV2006L">Investigation Close Date:</span>
</td><td>
<span id="INV2006"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV2006)"  />
</td></tr>

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
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="NBS270L" title="Referral Basis - OOJ">Referral Basis - OOJ:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(NBS270)" styleId="NBS270"><html:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180L" title="The Public Health Investigator assigned to the Investigation.">
Current Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV180)"/>
<span id="INV180">${PageForm.attributeMap.INV180SearchResult}</span>
</td> </tr>
<!--skipping Hidden Date Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_8" name="OOJ Agency Initiating Report" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The Initiating Agency which sent the OOJ Contact Report." id="NBS111L" >
Initiating Agency:</span></td><td>
<span id="NBS111" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS111)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the OOJ Contact report was received from the Initiating Agency." id="NBS112L">Date Received from Init. Agency:</span>
</td><td>
<span id="NBS112"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS112)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date OOJ outcome is due back to the Initiating Agency." id="NBS113L">Date OOJ Due to Init. Agency:</span>
</td><td>
<span id="NBS113"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS113)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date OOJ outcome was sent back to the Initiating Agency." id="NBS114L">Date OOJ Info Sent:</span>
</td><td>
<span id="NBS114"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS114)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_9" name="Reported as OOJ Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The cluster relationship reported by OOJ Contact." id="NBS124L" >
Relationship:</span></td><td>
<span id="NBS124" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS124)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>
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
<!--skipping Hidden Date Question-->
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
<nedss:container id="NBS_UI_32" name="Physician Clinic" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS291L" title="The clinic with which the physician associated with this case is affiliated.">
Physician Ordering Clinic:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS291)"/>
<span id="NBS291">${PageForm.attributeMap.NBS291SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_13" name="Hospital" isHidden="F" classType="subSect" >

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
Total duration of stay in the hospital (in days):</span>
</td><td>
<span id="INV134"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV134)"  />
</td></tr>
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date treatment initiated for the condition that is the subject of this case report." id="STD105L">Treatment Start Date:</span>
</td><td>
<span id="STD105"/>
<nedss:view name="PageForm" property="pageClientVO.answer(STD105)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date." id="STD099L">Date of Initial Health Exam:</span>
</td><td>
<span id="STD099"/>
<nedss:view name="PageForm" property="pageClientVO.answer(STD099)"  />
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
Where was the disease acquired:</span></td><td>
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
<span title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient." id="NBS135L" >
Source/Spread:</span></td><td>
<span id="NBS135" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS135)"
codeSetNm="SOURCE_SPREAD"/>
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
<nedss:container id="NBS11001" name="Reporting County" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS11002" name="Exposure Location" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS11002"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS11002">
<tr id="patternNBS11002" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS11002');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS11002">
<tr id="nopatternNBS11002" class="odd" style="display:none">
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
<nedss:container id="NBS11003" name="Binational Reporting" isHidden="F" classType="subSect" >

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
codeSetNm="PHVS_DETECTIONMETHOD_STD"/>
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
codeSetNm="PHVS_PHC_CLASS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The disease diagnosis of the patient." id="NBS136L" >
Diagnosis Reported to CDC:</span></td><td>
<span id="NBS136" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS136)"
codeSetNm="CASE_DIAGNOSIS_CS"/>
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition." id="INV176L">Date CDC was first verbally notified of this case:</span>
</td><td>
<span id="INV176"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV176)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." id="NOT120SPECL">
If Yes, describe:</span>
</td>
<td>
<span id="NOT120SPEC"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NOT120SPEC)" />
</td> </tr>

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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_73" name="Syphilis Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Neurological Involvement?" id="STD102L" >
Neurological Manifestations:</span></td><td>
<span id="STD102" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD102)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="102957003L" title="What neurologic manifestations of syphilis are present?">
Neurologic Signs/Symptoms:</span></td><td>
<span id="102957003" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(102957003)"
codeSetNm="PHVS_NEUROLOGICALMANIFESTATION_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="What neurologic manifestations of syphilis are present?" id="102957003OthL">Other Neurologic Signs/Symptoms:</span></td>
<td> <span id="102957003Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(102957003Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis." id="410478005L" >
Ocular Manifestations:</span></td><td>
<span id="410478005" />
<nedss:view name="PageForm" property="pageClientVO.answer(410478005)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo." id="PHC1472L" >
Otic Manifestations:</span></td><td>
<span id="PHC1472" />
<nedss:view name="PageForm" property="pageClientVO.answer(PHC1472)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Late clinical manifestations of syphilis (tertiary syphilis) may include inflammatory lesions of the cardiovascular system, skin, bone, or other tissue. Certain neurologic manifestations (e.g., general paresis and tabes dorsalis) are late clinical manifestations of syphilis." id="72083004L" >
Late Clinical Manifestations:</span></td><td>
<span id="72083004" />
<nedss:view name="PageForm" property="pageClientVO.answer(72083004)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
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
<nedss:container id="NBS_INV_STD_UI_12" name="Case Numbers" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Unique field record identifier." id="NBS160L">
Field Record Number:</span>
</td>
<td>
<span id="NBS160"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191L">
Lot Number:</span>
</td>
<td>
<span id="NBS191"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS191)" />
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
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_14" name="Initial Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS139L" title="The investigator assigning the initial follow-up.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS139)"/>
<span id="NBS139">${PageForm.attributeMap.NBS139SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Initial Follow-up action." id="NBS140L" >
Initial Follow-Up:</span></td><td>
<span id="NBS140" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS140)"
codeSetNm="STD_NBS_PROCESSING_DECISION_ALL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the inital follow-up was identified as closed." id="NBS141L">Date Closed:</span>
</td><td>
<span id="NBS141"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS141)"  />
</td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="NBS142L" title="Initiate for Internet follow-up?">Internet Follow-Up:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(NBS142)" styleId="NBS142"><html:optionsCollection property="codedValue(YN)" value="key" label="value" /> </html:select> </td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If applicable, enter the specific clinic code identifying the initiating clinic." id="NBS144L">
Clinic Code:</span>
</td>
<td>
<span id="NBS144"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS144)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_16" name="Surveillance Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS145L" title="The investigator assigned for surveillance follow-up.">
Assigned To:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS145)"/>
<span id="NBS145">${PageForm.attributeMap.NBS145SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date surveillance follow-up is assigned." id="NBS146L">Date Assigned:</span>
</td><td>
<span id="NBS146"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS146)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date surveillance follow-up is completed." id="NBS147L">Date Closed:</span>
</td><td>
<span id="NBS147"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS147)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_17" name="Surveillance Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the contact with the provider was successful or not." id="NBS148L" >
Provider Contact:</span></td><td>
<span id="NBS148" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS148)"
codeSetNm="PRVDR_CONTACT_OUTCOME"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's reason for examing the patient." id="NBS149L" >
Exam Reason:</span></td><td>
<span id="NBS149" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS149)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's diagnosis." id="NBS150L" >
Reporting Provider Diagnosis (Surveillance):</span></td><td>
<span id="NBS150" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS150)"
codeSetNm="PRVDR_DIAGNOSIS_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason." id="NBS151L" >
Patient Follow-Up:</span></td><td>
<span id="NBS151" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS151)"
codeSetNm="SURVEILLANCE_PATIENT_FOLLOWUP"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_18" name="Surveillance Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_18"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_18">
<tr id="patternNBS_INV_STD_UI_18" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_18');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_18">
<tr id="nopatternNBS_INV_STD_UI_18" class="odd" style="display:none">
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
<span title="Notes for surveillance activities (e.g., type of information needed, additional comments.)" id="NBS152L">
Surveillance Notes:</span>
</td>
<td>
<span id="NBS152"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS152)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_22" name="Field Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS161L" title="The investigator assigned to field follow-up activities.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS161)"/>
<span id="NBS161">${PageForm.attributeMap.NBS161SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigator is assigned to field follow-up activities." id="NBS162L">Date Assigned:</span>
</td><td>
<span id="NBS162"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS162)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS163L" title="The investigator originally assigned to field follow-up activities.">
Initially Assigned:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS163)"/>
<span id="NBS163">${PageForm.attributeMap.NBS163SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of initial assignment for field follow-up." id="NBS164L">Initial Assignment Date:</span>
</td><td>
<span id="NBS164"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS164)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_23" name="Field Follow-up Exam Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's reason for examing the patient." id="NBS165L" >
Exam Reason:</span></td><td>
<span id="NBS165" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS165)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's diagnosis." id="NBS166L" >
Reporting Provider Diagnosis (Field Follow-up):</span></td><td>
<span id="NBS166" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS166)"
codeSetNm="PRVDR_DIAGNOSIS_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Do you expect the patient to come in for examination?" id="NBS168L" >
Expected In:</span></td><td>
<span id="NBS168" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS168)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the patient is expected to come in for examination." id="NBS169L">Expected In Date:</span>
</td><td>
<span id="NBS169"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS169)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the patient was examined as a result of field activities." id="NBS170L">Exam Date:</span>
</td><td>
<span id="NBS170"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS170)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS171L" title="The provider who performed the exam.">
Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS171)"/>
<span id="NBS171">${PageForm.attributeMap.NBS171SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS172L" title="The facility at which the exam was performed.">
Facility:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS172)"/>
<span id="NBS172">${PageForm.attributeMap.NBS172SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_24" name="Case Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The disposition of the field follow-up activities." id="NBS173L" >
Disposition:</span></td><td>
<span id="NBS173" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS173)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="When the disposition was determined as relates to exam or treatment situation." id="NBS174L">Disposition Date:</span>
</td><td>
<span id="NBS174"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS174)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS175L" title="The person who brought the field record/activities to final disposition.">
Dispositioned By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS175)"/>
<span id="NBS175">${PageForm.attributeMap.NBS175SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS176L" title="The supervisor who should review the field record disposition.">
Supervisor:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS176)"/>
<span id="NBS176">${PageForm.attributeMap.NBS176SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the investigator is unable to disposition the case" id="NBS392L" >
Reason Unable to Disposition Case:</span></td><td>
<span id="NBS392" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS392)"
codeSetNm="REASON_NOT_DISPO_CS"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="NBS178L" title="The outcome of internet based activities.">Internet Outcome:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(NBS178)" styleId="NBS178"><html:optionsCollection property="codedValue(INTERNET_FOLLOWUP_OUTCOME)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_25" name="OOJ Field Record Sent To Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The name of the area where the out-of-jurisdiction Field Follow-up is sent." id="NBS179L" >
OOJ Agency FR Sent To:</span></td><td>
<span id="NBS179" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS179)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Field record number from initiating or receiving jurisdiction." id="NBS180L">
OOJ FR Number In Receiving Area:</span>
</td>
<td>
<span id="NBS180"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS180)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)" id="NBS181L">OOJ Due Date from Receiving Area:</span>
</td><td>
<span id="NBS181"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS181)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The outcome of the OOJ jurisdiction field activities." id="NBS182L" >
OOJ Outcome from Receiving Area:</span></td><td>
<span id="NBS182" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS182)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_26" name="Field Follow-Up Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_26"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_26">
<tr id="patternNBS_INV_STD_UI_26" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_26');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_26">
<tr id="nopatternNBS_INV_STD_UI_26" class="odd" style="display:none">
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
<span title="Note text." id="NBS185L">
Note:</span>
</td>
<td>
<span id="NBS185"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS185)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_27" name="Field Supervisory Review and Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_27"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_27">
<tr id="patternNBS_INV_STD_UI_27" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_27');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_27">
<tr id="nopatternNBS_INV_STD_UI_27" class="odd" style="display:none">
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
<span title="Note text" id="NBS268L">
Note:</span>
</td>
<td>
<span id="NBS268"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS268)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_32" name="Case Closure" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the case follow-up is closed." id="NBS196L">Date Closed:</span>
</td><td>
<span id="NBS196"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS196)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS197L" title="The investigator who closed out the case follow-up.">
Closed By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS197)"/>
<span id="NBS197">${PageForm.attributeMap.NBS197SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_34" name="Supervisory Review/Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_34"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_34">
<tr id="patternNBS_INV_STD_UI_34" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_34');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_34">
<tr id="nopatternNBS_INV_STD_UI_34" class="odd" style="display:none">
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
<span title="Note text." id="NBS200L">
Note:</span>
</td>
<td>
<span id="NBS200"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS200)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_77" name="Congenital Syphilis Report" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the expected delivery date of the infant" id="NBS388L">Expected Delivery Date:</span>
</td><td>
<span id="NBS388"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS388)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS383L" title="The mother's OB/GYN provider.">
Mother OB/GYN:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS383)"/>
<span id="NBS383">${PageForm.attributeMap.NBS383SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS384L" title="Who will be the Delivering Phyisician?">
Delivering MD:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS384)"/>
<span id="NBS384">${PageForm.attributeMap.NBS384SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS386L" title="The name of the hospital where the infant was born.">
Delivering Hospital:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS386)"/>
<span id="NBS386">${PageForm.attributeMap.NBS386SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS385L" title="Who is the attending Pediatrician?">
Pediatrician:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS385)"/>
<span id="NBS385">${PageForm.attributeMap.NBS385SearchResult}</span>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The Medical Record Number as reported by health care provider or facility" id="NBS336L">
Infant's Medical Record Number:</span>
</td>
<td>
<span id="NBS336"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS336)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_79" name="Mother Administrative Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Please complete this section for the mother's information. Though this information is available on the contact record, information entered into this section will be sent in the case notification to CDC.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page." id="NBS387L" >
Next of Kin Relationship:</span></td><td>
<span id="NBS387" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS387)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the state of residence of the mother of the case patient" id="MTH166L" >
Mother's Residence State:</span></td><td>
<span id="MTH166" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH166)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Enter the zip code of the case patient's mother" id="MTH169L">
Mother's Residence Zip Code:</span>
</td>
<td>
<span id="MTH169"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH169)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The county of residence of the case patient's mother" id="MTH168L" >
Mother's County of Residence:</span></td><td>
<span id="MTH168" />
<logic:notEmpty name="PageForm" property="pageClientVO.answer(MTH168)">
<logic:notEmpty name="PageForm" property="pageClientVO.answer(MTH166)">
<bean:define id="value" name="PageForm" property="pageClientVO.answer(MTH166)"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH168)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.MTH168_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the country of residence of the case patient's mother" id="MTH167L" >
Mother's Country of Residence:</span></td><td>
<span id="MTH167" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH167)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of birth of the case patient's mother" id="MTH153L">Mother's Date of Birth:</span>
</td><td>
<span id="MTH153"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH153)"  />
</td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="MTH157L" title="Enter the race of the patient's mother">
Race of Mother:</span></td><td>
<span id="MTH157" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(MTH157)"
codeSetNm="PHVS_RACECATEGORY_CDC_NULLFLAVOR"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Ethnicity of the patient's mother" id="MTH159L" >
Ethnicity of Mother:</span></td><td>
<span id="MTH159" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH159)"
codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the marital status of the case patient's mother" id="MTH165L" >
Mother's Marital Status:</span></td><td>
<span id="MTH165" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH165)"
codeSetNm="P_MARITAL"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_80" name="Mother Medical History" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="75201_4L" title="Enter the number of pregnancies, include current and previous pregnancies (G)">
Number of Pregnancies:</span>
</td><td>
<span id="75201_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(75201_4)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="75202_2L" title="Enter the total number of live births">
Number of Live Births:</span>
</td><td>
<span id="75202_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(75202_2)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Mother Last menstrual period (LMP) start date before delivery." id="752030L">Mother's Last Menstrual Period Before Delivery:</span>
</td><td>
<span id="752030"/>
<nedss:view name="PageForm" property="pageClientVO.answer(752030)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if there was a prenatal visit" id="75204_8L" >
Was There a Prenatal Visit:</span></td><td>
<span id="75204_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(75204_8)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS390L" title="Enter the total number of prenatal visits the mother had related to the pregnancy">
Total Number of Prenatal Visits:</span>
</td><td>
<span id="NBS390"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS390)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date of the first prenatal visit" id="75200_6L">Indicate Date of First Prenatal Visit:</span>
</td><td>
<span id="75200_6"/>
<nedss:view name="PageForm" property="pageClientVO.answer(75200_6)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the trimester of the first prenatal visit" id="75163_6L" >
Indicate Trimester of First Prenatal Visit:</span></td><td>
<span id="75163_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(75163_6)"
codeSetNm="PHVS_PREG_TRIMESTER"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have a Non-treponemal Test or Treponemal Test at First Prenatal Visit" id="75164_4L" >
Did Mother Have Non-Treponemal or Treponemal Test at First Prenatal Visit:</span></td><td>
<span id="75164_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(75164_4)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have a  Non-treponemal Test or Treponemal Test at 28-32 Weeks Gestation" id="75165_1L" >
Did Mother Have Non-Treponemal or Treponemal Test at 28-32 Weeks Gestation?:</span></td><td>
<span id="75165_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(75165_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have a Non-treponemal Test or Treponemal Test at Delivery" id="75166_9L" >
Did Mother Have Non-Treponemal or Treponemal Tests at Delivery:</span></td><td>
<span id="75166_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(75166_9)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_81" name="Mother Interpretive Lab Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_81"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_81">
<tr id="patternNBS_INV_STD_UI_81" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_81');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_81">
<tr id="nopatternNBS_INV_STD_UI_81" class="odd" style="display:none">
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
<span title="Select the appropriate value to indicate timing and subject of the test being entered." id="LAB588_MTHL" >
Lab Test Performed Modifier (Mother):</span></td><td>
<span id="LAB588_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB588_MTH)"
codeSetNm="LAB_TST_MODIFIER_MTH"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the type of lab test performed" id="INV290_MTHL" >
Test Type (Mother):</span></td><td>
<span id="INV290_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290_MTH)"
codeSetNm="PHVS_TESTTYPE_SYPHILIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the result for the lab test being reported." id="INV291_MTHL" >
Test Result (Mother):</span></td><td>
<span id="INV291_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291_MTH)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the quantitative test result for the non-treponemal serologic test of the mother of the case. If the test performed provides a quantifiable result, provide quantitative results as a coded value. For example, if the titer is 1:64, choose the corresponding value from the drop down." id="STD123_MTHL" >
Non-Treponemal Serologic Test Result (Quantitative) (Mother):</span></td><td>
<span id="STD123_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD123_MTH)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the lab result date for the lab test being reported." id="LAB167_MTHL">Lab Result Date (Mother):</span>
</td><td>
<span id="LAB167_MTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB167_MTH)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_82" name="Mother Clinical Information" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the HIV status of the mother of case." id="NBS153_MTHL" >
Mother HIV Status:</span></td><td>
<span id="NBS153_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS153_MTH)"
codeSetNm="PHVS_HIVSTATUS_STD"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the clinical stage of syphilis the mother had during pregnancy" id="75180_0L" >
What CLINICAL Stage of Syphilis Did Mother Have During Pregnancy:</span></td><td>
<span id="75180_0" />
<nedss:view name="PageForm" property="pageClientVO.answer(75180_0)"
codeSetNm="PHVS_SYPHILISCLINICALSTAGE_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the mother's surveillance stage of syphilis during pregnancy" id="75181_8L" >
What SURVEILLANCE Stage of Syphilis Did Mother Have During Pregnancy:</span></td><td>
<span id="75181_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(75181_8)"
codeSetNm="PHVS_SYPHILISSURVEILLANCESTAGE_CS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date the patient received the first dose of benzathine penicillin" id="75182_6L">When Did Mother Receive Her First Dose of Benzathine Penicillin:</span>
</td><td>
<span id="75182_6"/>
<nedss:view name="PageForm" property="pageClientVO.answer(75182_6)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the trimester the patient received the first dose of benzathine penicillin" id="75183_4L" >
Which Trimester Did Mother Receive Her First Dose of Benzathine Penicillin:</span></td><td>
<span id="75183_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(75183_4)"
codeSetNm="PHVS_PREGNANCYTREATMENTSTAGE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the mother was treated at least 30 days prior to delivery" id="NBS391L" >
Was the Mother Treated At Least 30 Days Prior to Delivery:</span></td><td>
<span id="NBS391" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS391)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the mother's treatment" id="75184_2L" >
What Was the Mother's Treatment:</span></td><td>
<span id="75184_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(75184_2)"
codeSetNm="PHVS_SYPHILISTREATMENTMOTHER_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have an appropriate serologic response to treatment" id="75185_9L" >
Did Mother Have an Appropriate Serologic Response:</span></td><td>
<span id="75185_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(75185_9)"
codeSetNm="PHVS_SEROLOGICRESPONSE_CS"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_84" name="Infant Delivery Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the vital status of the infant" id="75186_7L" >
Vital Status:</span></td><td>
<span id="75186_7" />
<nedss:view name="PageForm" property="pageClientVO.answer(75186_7)"
codeSetNm="PHVS_BIRTHSTATUS_NND"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="DEM229L" title="Infants birth weight in grams">
Birth Weight in Grams:</span>
</td><td>
<span id="DEM229"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM229)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="DEM228L" title="Gestational age in weeks">
Estimated Gestational Age in Weeks:</span>
</td><td>
<span id="DEM228"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM228)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_85" name="Infant Clinical Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the infant/child have long bone x-rays" id="75194_1L" >
Did the Infant/Child Have Long Bone X-rays:</span></td><td>
<span id="75194_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(75194_1)"
codeSetNm="PHVS_XRAYRESULT_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did infant/child placenta or cord have Darkfield exam, DFA, or special stain?" id="75192_5L" >
Did the Infant/Child, Placenta, or Cord Have Darkfield Exam, DFA, or Special Stains:</span></td><td>
<span id="75192_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(75192_5)"
codeSetNm="LAB_RSLT_INTERP_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the interpretation of the CSF WBC count testing results" id="LP48341_9L" >
CSF WBC Count Interpretation:</span></td><td>
<span id="LP48341_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP48341_9)"
codeSetNm="LAB_RSLT_CSF_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the interpretation of the CSF Protein level testing results" id="LP69956_8L" >
CSF Protein Level Interpretation:</span></td><td>
<span id="LP69956_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP69956_8)"
codeSetNm="LAB_RSLT_PRT_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the CSF VDRL Test Finding" id="75195_8L" >
Did the Infant/Child Have CSF-VDRL:</span></td><td>
<span id="75195_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(75195_8)"
codeSetNm="LAB_RSLT_VDRL_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is this case classified as a syphilitic stillbirth?" id="75207_1L" >
Stillbirth Indicator:</span></td><td>
<span id="75207_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(75207_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV272L" title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click.">
Did the Infant/Child Have Any Signs of CS (Select All that Apply):</span></td><td>
<span id="INV272" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV272)"
codeSetNm="PHVS_SIGNSSYMPTOMS_CS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click." id="INV272OthL">Other Did the Infant/Child Have Any Signs of CS (Select All that Apply):</span></td>
<td> <span id="INV272Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV272Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the infant/child treated for congenital syphilis?" id="75197_4L" >
Was the Infant/Child Treated?:</span></td><td>
<span id="75197_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(75197_4)"
codeSetNm="PHVS_SYPHILISTREATMENTINFANT_CS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the treatment type is 'Other', specify the treatment." id="NBS389L">
Other Treatment Specify:</span>
</td>
<td>
<span id="NBS389"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS389)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_86" name="Infant Interpretive Lab Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_86"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_86">
<tr id="patternNBS_INV_STD_UI_86" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_86');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_86">
<tr id="nopatternNBS_INV_STD_UI_86" class="odd" style="display:none">
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
<span title="Select the appropriate value to indicate timing and subject of the test being entered." id="LAB588L" >
Lab Test Performed Modifier (Infant):</span></td><td>
<span id="LAB588" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB588)"
codeSetNm="LAB_TST_MODIFIER_INFANT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="PHVS_TESTTYPE_SYPHILIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024." id="STD123_1L" >
Nontreponemal Serologic Syphilis Test Result (Quantitative):</span></td><td>
<span id="STD123_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD123_1)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date result sent from Reporting Laboratory" id="LAB167L">Lab Result Date:</span>
</td><td>
<span id="LAB167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB167)"  />
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
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV169L" title="Condition_Cd should always be a hidden or read-only field.">Hidden Condition:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(INV169)" styleId="INV169"><html:optionsCollection property="codedValue(PHC_TYPE)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
