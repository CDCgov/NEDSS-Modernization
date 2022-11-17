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
String [] sectionNames  = {"Patient Information","Address Information","Telephone and Email Contact Information","Race and Ethnicity Information","Other Identifying Information","Investigation Information","OOJ Initiating Agency Information","Reporting Information","Clinical","Epidemiologic","Comments","Case Numbers","Initial Follow-up","Surveillance","Field Follow-up Information","Case Closure","Congenital Syphilis Information","Maternal Information","Infant Information","Contact Investigation"};
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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's alias or nickname." id="DEM250_2L">
Alias/Nickname:</span>
</td>
<td>
<span id="DEM250_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM250)" />
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's current sex if identified as unknown (i.e., not male or female)." id="NBS272_2L" >
Unknown Reason:</span></td><td>
<span id="NBS272_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS272)"
codeSetNm="SEX_UNK_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's transgender identity." id="NBS274_2L" >
Gender Identity/Transgender Info:</span></td><td>
<span id="NBS274_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS274)"
codeSetNm="NBS_STD_GENDER_PARPT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The specific gender information of the index patient if other selections do not apply (i.e. intersex, two-spirited, etc.)." id="NBS213_2L">
Additional Gender:</span>
</td>
<td>
<span id="NBS213_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS213)" />
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
<!--skipping Hidden Date Question-->
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="DEM140_2L" title="A code indicating the married or similar partnership status of a patient.">Marital Status:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(DEM140)" styleId="DEM140_2"><html:optionsCollection property="codedValue(P_MARITAL)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV178_2L" title="Assesses whether or not the patient is pregnant.">Is the patient pregnant?:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(INV178)" styleId="INV178_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Numeric Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts." id="DEM168_2L">
Census Tract:</span>
</td>
<td>
<span id="DEM168_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM168)" />
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
<nedss:container id="NBS_INV_STD_UI_2_2" name="Additional Residence Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The RELATIONSHIP (such as spouse, parents, sibling, partner, roommate, etc., not the name) of those living with the patient." id="NBS201_2L">
Living With:</span>
</td>
<td>
<span id="NBS201_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS201)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The type of residence in which the patient currenlty resides." id="NBS202_2L" >
Type of Residence:</span></td><td>
<span id="NBS202_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS202)"
codeSetNm="RESIDENCE_TYPE_STD"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS203_2L" title="The length of time the patient has lived at the current address.">
Time at Address:</span>
</td><td>
<span id="NBS203_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS203)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time at address." id="NBS204_2L" >
Units:</span></td><td>
<span id="NBS204_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS204)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS205_2L" title="The length of time the patient has lived in this state/territory.">
Time in State:</span>
</td><td>
<span id="NBS205_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS205)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time in state." id="NBS206_2L" >
Units:</span></td><td>
<span id="NBS206_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS206)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS207_2L" title="The length of time the patient has lived in the country.">
Time in Country:</span>
</td><td>
<span id="NBS207_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS207)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time in country." id="NBS208_2L" >
Units:</span></td><td>
<span id="NBS208_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS208)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the patient is institutionalized (i.e., in jail, in a group home, in a mental health facility, etc.)" id="NBS209_2L" >
Currently institutionalized:</span></td><td>
<span id="NBS209_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS209)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Name of Institutition" id="NBS210_2L">
If institutionalized, document the name of the facility.:</span>
</td>
<td>
<span id="NBS210_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS210)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of Institutition" id="NBS211_2L" >
Type of Institutition:</span></td><td>
<span id="NBS211_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS211)"
codeSetNm="INSTITUTION_TYPE_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Specify reason the patient's ethnicity is unknown." id="NBS273_2L" >
Reason Unknown:</span></td><td>
<span id="NBS273_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS273)"
codeSetNm="P_ETHN_UNK_REASON"/>
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

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_6_2" name="Other Identifying Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific height of the patient." id="NBS155_2L">
Height:</span>
</td>
<td>
<span id="NBS155_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS155)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific weight or body type of the patient." id="NBS156_2L">
Size/Build:</span>
</td>
<td>
<span id="NBS156_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS156)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The description of the patients hair, including color, length, and/or style." id="NBS157_2L">
Hair:</span>
</td>
<td>
<span id="NBS157_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS157)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific skin tone/hue of the patient." id="NBS158_2L">
Complexion:</span>
</td>
<td>
<span id="NBS158_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS158)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Any additional demographic information (e.g., tattoos, etc)." id="NBS159_2L">
Other Identifying Info:</span>
</td>
<td>
<span id="NBS159_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS159)"  />
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Document the reason (referral basis) why the investigation was initiated." id="NBS110_2L" >
Referral Basis:</span></td><td>
<span id="NBS110_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS110)"
codeSetNm="REFERRAL_BASIS"/>
</td> </tr>

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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)" id="NBS115_2L" >
Current Process Stage:</span></td><td>
<span id="NBS115_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS115)"
codeSetNm="CM_PROCESS_STAGE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation was started or initiated." id="INV147_2L">Investigation Start Date:</span>
</td><td>
<span id="INV147_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV147)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation is closed." id="INV2006_2L">Investigation Close Date:</span>
</td><td>
<span id="INV2006_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV2006)"  />
</td></tr>

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
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="NBS270_2L" title="Referral Basis - OOJ">Referral Basis - OOJ:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(NBS270)" styleId="NBS270_2"><html:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20_2" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180_2L" title="The Public Health Investigator assigned to the Investigation.">
Current Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV180)"/>
<span id="INV180_2">${PageForm.attributeMap2.INV180SearchResult}</span>
</td> </tr>
<!--skipping Hidden Date Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_8_2" name="OOJ Agency Initiating Report" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The Initiating Agency which sent the OOJ Contact Report." id="NBS111_2L" >
Initiating Agency:</span></td><td>
<span id="NBS111_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS111)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the OOJ Contact report was received from the Initiating Agency." id="NBS112_2L">Date Received from Init. Agency:</span>
</td><td>
<span id="NBS112_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS112)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date OOJ outcome is due back to the Initiating Agency." id="NBS113_2L">Date OOJ Due to Init. Agency:</span>
</td><td>
<span id="NBS113_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS113)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date OOJ outcome was sent back to the Initiating Agency." id="NBS114_2L">Date OOJ Info Sent:</span>
</td><td>
<span id="NBS114_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS114)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_9_2" name="Reported as OOJ Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The cluster relationship reported by OOJ Contact." id="NBS124_2L" >
Relationship:</span></td><td>
<span id="NBS124_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS124)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>
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
<!--skipping Hidden Date Question-->
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
<nedss:container id="NBS_UI_32_2" name="Physician Clinic" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS291_2L" title="The clinic with which the physician associated with this case is affiliated.">
Physician Ordering Clinic:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS291)"/>
<span id="NBS291_2">${PageForm.attributeMap2.NBS291SearchResult}</span>
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date treatment initiated for the condition that is the subject of this case report." id="STD105_2L">Treatment Start Date:</span>
</td><td>
<span id="STD105_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(STD105)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date." id="STD099_2L">Date of Initial Health Exam:</span>
</td><td>
<span id="STD099_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(STD099)"  />
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
<span title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient." id="NBS135_2L" >
Source/Spread:</span></td><td>
<span id="NBS135_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS135)"
codeSetNm="SOURCE_SPREAD"/>
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
<nedss:container id="NBS11001_2" name="Reporting County" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS11002_2" name="Exposure Location" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS11002_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS11002_2">
<tr id="patternNBS11002_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS11002_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS11002_2">
<tr id="nopatternNBS11002_2" class="odd" style="display:none">
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
<nedss:container id="NBS11003_2" name="Binational Reporting" isHidden="F" classType="subSect" >

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
codeSetNm="PHVS_DETECTIONMETHOD_STD"/>
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
codeSetNm="PHVS_PHC_CLASS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The disease diagnosis of the patient." id="NBS136_2L" >
Diagnosis Reported to CDC:</span></td><td>
<span id="NBS136_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS136)"
codeSetNm="CASE_DIAGNOSIS_CS"/>
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition." id="INV176_2L">Date CDC was first verbally notified of this case:</span>
</td><td>
<span id="INV176_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV176)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." id="NOT120SPEC_2L">
If Yes, describe:</span>
</td>
<td>
<span id="NOT120SPEC_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NOT120SPEC)" />
</td> </tr>

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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_73_2" name="Syphilis Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Neurological Involvement?" id="STD102_2L" >
Neurological Manifestations:</span></td><td>
<span id="STD102_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD102)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="102957003_2L" title="What neurologic manifestations of syphilis are present?">
Neurologic Signs/Symptoms:</span></td><td>
<span id="102957003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(102957003)"
codeSetNm="PHVS_NEUROLOGICALMANIFESTATION_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="What neurologic manifestations of syphilis are present?" id="102957003_2_OthL">Other Neurologic Signs/Symptoms:</span></td>
<td> <span id="102957003_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(102957003Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis." id="410478005_2L" >
Ocular Manifestations:</span></td><td>
<span id="410478005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(410478005)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo." id="PHC1472_2L" >
Otic Manifestations:</span></td><td>
<span id="PHC1472_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1472)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Late clinical manifestations of syphilis (tertiary syphilis) may include inflammatory lesions of the cardiovascular system, skin, bone, or other tissue. Certain neurologic manifestations (e.g., general paresis and tabes dorsalis) are late clinical manifestations of syphilis." id="72083004_2L" >
Late Clinical Manifestations:</span></td><td>
<span id="72083004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(72083004)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
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
<nedss:container id="NBS_INV_STD_UI_12_2" name="Case Numbers" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Unique field record identifier." id="NBS160_2L">
Field Record Number:</span>
</td>
<td>
<span id="NBS160_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191_2L">
Lot Number:</span>
</td>
<td>
<span id="NBS191_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS191)" />
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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_14_2" name="Initial Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS139_2L" title="The investigator assigning the initial follow-up.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS139)"/>
<span id="NBS139_2">${PageForm.attributeMap2.NBS139SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Initial Follow-up action." id="NBS140_2L" >
Initial Follow-Up:</span></td><td>
<span id="NBS140_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS140)"
codeSetNm="STD_NBS_PROCESSING_DECISION_ALL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the inital follow-up was identified as closed." id="NBS141_2L">Date Closed:</span>
</td><td>
<span id="NBS141_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS141)"  />
</td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="NBS142_2L" title="Initiate for Internet follow-up?">Internet Follow-Up:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(NBS142)" styleId="NBS142_2"><html:optionsCollection property="codedValue(YN)" value="key" label="value" /> </html:select> </td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If applicable, enter the specific clinic code identifying the initiating clinic." id="NBS144_2L">
Clinic Code:</span>
</td>
<td>
<span id="NBS144_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS144)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_16_2" name="Surveillance Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS145_2L" title="The investigator assigned for surveillance follow-up.">
Assigned To:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS145)"/>
<span id="NBS145_2">${PageForm.attributeMap2.NBS145SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date surveillance follow-up is assigned." id="NBS146_2L">Date Assigned:</span>
</td><td>
<span id="NBS146_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS146)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date surveillance follow-up is completed." id="NBS147_2L">Date Closed:</span>
</td><td>
<span id="NBS147_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS147)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_17_2" name="Surveillance Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the contact with the provider was successful or not." id="NBS148_2L" >
Provider Contact:</span></td><td>
<span id="NBS148_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS148)"
codeSetNm="PRVDR_CONTACT_OUTCOME"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's reason for examing the patient." id="NBS149_2L" >
Exam Reason:</span></td><td>
<span id="NBS149_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS149)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's diagnosis." id="NBS150_2L" >
Reporting Provider Diagnosis (Surveillance):</span></td><td>
<span id="NBS150_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS150)"
codeSetNm="PRVDR_DIAGNOSIS_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason." id="NBS151_2L" >
Patient Follow-Up:</span></td><td>
<span id="NBS151_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS151)"
codeSetNm="SURVEILLANCE_PATIENT_FOLLOWUP"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_18_2" name="Surveillance Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_18_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_18_2">
<tr id="patternNBS_INV_STD_UI_18_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_18_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_18_2">
<tr id="nopatternNBS_INV_STD_UI_18_2" class="odd" style="display:none">
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
<span title="Notes for surveillance activities (e.g., type of information needed, additional comments.)" id="NBS152_2L">
Surveillance Notes:</span>
</td>
<td>
<span id="NBS152_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS152)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_22_2" name="Field Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS161_2L" title="The investigator assigned to field follow-up activities.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS161)"/>
<span id="NBS161_2">${PageForm.attributeMap2.NBS161SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigator is assigned to field follow-up activities." id="NBS162_2L">Date Assigned:</span>
</td><td>
<span id="NBS162_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS162)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS163_2L" title="The investigator originally assigned to field follow-up activities.">
Initially Assigned:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS163)"/>
<span id="NBS163_2">${PageForm.attributeMap2.NBS163SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of initial assignment for field follow-up." id="NBS164_2L">Initial Assignment Date:</span>
</td><td>
<span id="NBS164_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS164)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_23_2" name="Field Follow-up Exam Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's reason for examing the patient." id="NBS165_2L" >
Exam Reason:</span></td><td>
<span id="NBS165_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS165)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's diagnosis." id="NBS166_2L" >
Reporting Provider Diagnosis (Field Follow-up):</span></td><td>
<span id="NBS166_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS166)"
codeSetNm="PRVDR_DIAGNOSIS_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Do you expect the patient to come in for examination?" id="NBS168_2L" >
Expected In:</span></td><td>
<span id="NBS168_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS168)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the patient is expected to come in for examination." id="NBS169_2L">Expected In Date:</span>
</td><td>
<span id="NBS169_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS169)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the patient was examined as a result of field activities." id="NBS170_2L">Exam Date:</span>
</td><td>
<span id="NBS170_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS170)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS171_2L" title="The provider who performed the exam.">
Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS171)"/>
<span id="NBS171_2">${PageForm.attributeMap2.NBS171SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS172_2L" title="The facility at which the exam was performed.">
Facility:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS172)"/>
<span id="NBS172_2">${PageForm.attributeMap2.NBS172SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_24_2" name="Case Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The disposition of the field follow-up activities." id="NBS173_2L" >
Disposition:</span></td><td>
<span id="NBS173_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS173)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="When the disposition was determined as relates to exam or treatment situation." id="NBS174_2L">Disposition Date:</span>
</td><td>
<span id="NBS174_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS174)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS175_2L" title="The person who brought the field record/activities to final disposition.">
Dispositioned By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS175)"/>
<span id="NBS175_2">${PageForm.attributeMap2.NBS175SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS176_2L" title="The supervisor who should review the field record disposition.">
Supervisor:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS176)"/>
<span id="NBS176_2">${PageForm.attributeMap2.NBS176SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the investigator is unable to disposition the case" id="NBS392_2L" >
Reason Unable to Disposition Case:</span></td><td>
<span id="NBS392_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS392)"
codeSetNm="REASON_NOT_DISPO_CS"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="NBS178_2L" title="The outcome of internet based activities.">Internet Outcome:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(NBS178)" styleId="NBS178_2"><html:optionsCollection property="codedValue(INTERNET_FOLLOWUP_OUTCOME)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_25_2" name="OOJ Field Record Sent To Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The name of the area where the out-of-jurisdiction Field Follow-up is sent." id="NBS179_2L" >
OOJ Agency FR Sent To:</span></td><td>
<span id="NBS179_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS179)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Field record number from initiating or receiving jurisdiction." id="NBS180_2L">
OOJ FR Number In Receiving Area:</span>
</td>
<td>
<span id="NBS180_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS180)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)" id="NBS181_2L">OOJ Due Date from Receiving Area:</span>
</td><td>
<span id="NBS181_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS181)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The outcome of the OOJ jurisdiction field activities." id="NBS182_2L" >
OOJ Outcome from Receiving Area:</span></td><td>
<span id="NBS182_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS182)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_26_2" name="Field Follow-Up Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_26_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_26_2">
<tr id="patternNBS_INV_STD_UI_26_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_26_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_26_2">
<tr id="nopatternNBS_INV_STD_UI_26_2" class="odd" style="display:none">
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
<span title="Note text." id="NBS185_2L">
Note:</span>
</td>
<td>
<span id="NBS185_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS185)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_27_2" name="Field Supervisory Review and Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_27_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_27_2">
<tr id="patternNBS_INV_STD_UI_27_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_27_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_27_2">
<tr id="nopatternNBS_INV_STD_UI_27_2" class="odd" style="display:none">
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
<span title="Note text" id="NBS268_2L">
Note:</span>
</td>
<td>
<span id="NBS268_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS268)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_32_2" name="Case Closure" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the case follow-up is closed." id="NBS196_2L">Date Closed:</span>
</td><td>
<span id="NBS196_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS196)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS197_2L" title="The investigator who closed out the case follow-up.">
Closed By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS197)"/>
<span id="NBS197_2">${PageForm.attributeMap2.NBS197SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_34_2" name="Supervisory Review/Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_34_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_34_2">
<tr id="patternNBS_INV_STD_UI_34_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_34_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_34_2">
<tr id="nopatternNBS_INV_STD_UI_34_2" class="odd" style="display:none">
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
<span title="Note text." id="NBS200_2L">
Note:</span>
</td>
<td>
<span id="NBS200_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS200)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_77_2" name="Congenital Syphilis Report" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the expected delivery date of the infant" id="NBS388_2L">Expected Delivery Date:</span>
</td><td>
<span id="NBS388_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS388)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS383_2L" title="The mother's OB/GYN provider.">
Mother OB/GYN:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS383)"/>
<span id="NBS383_2">${PageForm.attributeMap2.NBS383SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS384_2L" title="Who will be the Delivering Phyisician?">
Delivering MD:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS384)"/>
<span id="NBS384_2">${PageForm.attributeMap2.NBS384SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS386_2L" title="The name of the hospital where the infant was born.">
Delivering Hospital:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS386)"/>
<span id="NBS386_2">${PageForm.attributeMap2.NBS386SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS385_2L" title="Who is the attending Pediatrician?">
Pediatrician:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS385)"/>
<span id="NBS385_2">${PageForm.attributeMap2.NBS385SearchResult}</span>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The Medical Record Number as reported by health care provider or facility" id="NBS336_2L">
Infant's Medical Record Number:</span>
</td>
<td>
<span id="NBS336_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS336)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_79_2" name="Mother Administrative Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Please complete this section for the mother's information. Though this information is available on the contact record, information entered into this section will be sent in the case notification to CDC.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page." id="NBS387_2L" >
Next of Kin Relationship:</span></td><td>
<span id="NBS387_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS387)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the state of residence of the mother of the case patient" id="MTH166_2L" >
Mother's Residence State:</span></td><td>
<span id="MTH166_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH166)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Enter the zip code of the case patient's mother" id="MTH169_2L">
Mother's Residence Zip Code:</span>
</td>
<td>
<span id="MTH169_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH169)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The county of residence of the case patient's mother" id="MTH168_2L" >
Mother's County of Residence:</span></td><td>
<span id="MTH168_2" />
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(MTH168)">
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(MTH166)">
<bean:define id="value" name="PageForm" property="pageClientVO2.answer(MTH166)"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH168)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.MTH168_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the country of residence of the case patient's mother" id="MTH167_2L" >
Mother's Country of Residence:</span></td><td>
<span id="MTH167_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH167)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date of birth of the case patient's mother" id="MTH153_2L">Mother's Date of Birth:</span>
</td><td>
<span id="MTH153_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH153)"  />
</td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="MTH157_2L" title="Enter the race of the patient's mother">
Race of Mother:</span></td><td>
<span id="MTH157_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(MTH157)"
codeSetNm="PHVS_RACECATEGORY_CDC_NULLFLAVOR"/>
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

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the marital status of the case patient's mother" id="MTH165_2L" >
Mother's Marital Status:</span></td><td>
<span id="MTH165_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(MTH165)"
codeSetNm="P_MARITAL"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_80_2" name="Mother Medical History" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="75201_4_2L" title="Enter the number of pregnancies, include current and previous pregnancies (G)">
Number of Pregnancies:</span>
</td><td>
<span id="75201_4_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(75201_4)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="75202_2_2L" title="Enter the total number of live births">
Number of Live Births:</span>
</td><td>
<span id="75202_2_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(75202_2)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Mother Last menstrual period (LMP) start date before delivery." id="752030_2L">Mother's Last Menstrual Period Before Delivery:</span>
</td><td>
<span id="752030_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(752030)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if there was a prenatal visit" id="75204_8_2L" >
Was There a Prenatal Visit:</span></td><td>
<span id="75204_8_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75204_8)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS390_2L" title="Enter the total number of prenatal visits the mother had related to the pregnancy">
Total Number of Prenatal Visits:</span>
</td><td>
<span id="NBS390_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS390)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date of the first prenatal visit" id="75200_6_2L">Indicate Date of First Prenatal Visit:</span>
</td><td>
<span id="75200_6_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(75200_6)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the trimester of the first prenatal visit" id="75163_6_2L" >
Indicate Trimester of First Prenatal Visit:</span></td><td>
<span id="75163_6_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75163_6)"
codeSetNm="PHVS_PREG_TRIMESTER"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have a Non-treponemal Test or Treponemal Test at First Prenatal Visit" id="75164_4_2L" >
Did Mother Have Non-Treponemal or Treponemal Test at First Prenatal Visit:</span></td><td>
<span id="75164_4_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75164_4)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have a  Non-treponemal Test or Treponemal Test at 28-32 Weeks Gestation" id="75165_1_2L" >
Did Mother Have Non-Treponemal or Treponemal Test at 28-32 Weeks Gestation?:</span></td><td>
<span id="75165_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75165_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have a Non-treponemal Test or Treponemal Test at Delivery" id="75166_9_2L" >
Did Mother Have Non-Treponemal or Treponemal Tests at Delivery:</span></td><td>
<span id="75166_9_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75166_9)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_81_2" name="Mother Interpretive Lab Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_81_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_81_2">
<tr id="patternNBS_INV_STD_UI_81_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_81_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_81_2">
<tr id="nopatternNBS_INV_STD_UI_81_2" class="odd" style="display:none">
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
<span title="Select the appropriate value to indicate timing and subject of the test being entered." id="LAB588_MTH_2L" >
Lab Test Performed Modifier (Mother):</span></td><td>
<span id="LAB588_MTH_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB588_MTH)"
codeSetNm="LAB_TST_MODIFIER_MTH"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the type of lab test performed" id="INV290_MTH_2L" >
Test Type (Mother):</span></td><td>
<span id="INV290_MTH_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV290_MTH)"
codeSetNm="PHVS_TESTTYPE_SYPHILIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the result for the lab test being reported." id="INV291_MTH_2L" >
Test Result (Mother):</span></td><td>
<span id="INV291_MTH_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV291_MTH)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the quantitative test result for the non-treponemal serologic test of the mother of the case. If the test performed provides a quantifiable result, provide quantitative results as a coded value. For example, if the titer is 1:64, choose the corresponding value from the drop down." id="STD123_MTH_2L" >
Non-Treponemal Serologic Test Result (Quantitative) (Mother):</span></td><td>
<span id="STD123_MTH_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD123_MTH)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the lab result date for the lab test being reported." id="LAB167_MTH_2L">Lab Result Date (Mother):</span>
</td><td>
<span id="LAB167_MTH_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB167_MTH)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_82_2" name="Mother Clinical Information" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the HIV status of the mother of case." id="NBS153_MTH_2L" >
Mother HIV Status:</span></td><td>
<span id="NBS153_MTH_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS153_MTH)"
codeSetNm="PHVS_HIVSTATUS_STD"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the clinical stage of syphilis the mother had during pregnancy" id="75180_0_2L" >
What CLINICAL Stage of Syphilis Did Mother Have During Pregnancy:</span></td><td>
<span id="75180_0_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75180_0)"
codeSetNm="PHVS_SYPHILISCLINICALSTAGE_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the mother's surveillance stage of syphilis during pregnancy" id="75181_8_2L" >
What SURVEILLANCE Stage of Syphilis Did Mother Have During Pregnancy:</span></td><td>
<span id="75181_8_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75181_8)"
codeSetNm="PHVS_SYPHILISSURVEILLANCESTAGE_CS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date the patient received the first dose of benzathine penicillin" id="75182_6_2L">When Did Mother Receive Her First Dose of Benzathine Penicillin:</span>
</td><td>
<span id="75182_6_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(75182_6)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the trimester the patient received the first dose of benzathine penicillin" id="75183_4_2L" >
Which Trimester Did Mother Receive Her First Dose of Benzathine Penicillin:</span></td><td>
<span id="75183_4_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75183_4)"
codeSetNm="PHVS_PREGNANCYTREATMENTSTAGE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the mother was treated at least 30 days prior to delivery" id="NBS391_2L" >
Was the Mother Treated At Least 30 Days Prior to Delivery:</span></td><td>
<span id="NBS391_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS391)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the mother's treatment" id="75184_2_2L" >
What Was the Mother's Treatment:</span></td><td>
<span id="75184_2_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75184_2)"
codeSetNm="PHVS_SYPHILISTREATMENTMOTHER_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the subject have an appropriate serologic response to treatment" id="75185_9_2L" >
Did Mother Have an Appropriate Serologic Response:</span></td><td>
<span id="75185_9_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75185_9)"
codeSetNm="PHVS_SEROLOGICRESPONSE_CS"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_84_2" name="Infant Delivery Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the vital status of the infant" id="75186_7_2L" >
Vital Status:</span></td><td>
<span id="75186_7_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75186_7)"
codeSetNm="PHVS_BIRTHSTATUS_NND"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="DEM229_2L" title="Infants birth weight in grams">
Birth Weight in Grams:</span>
</td><td>
<span id="DEM229_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM229)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="DEM228_2L" title="Gestational age in weeks">
Estimated Gestational Age in Weeks:</span>
</td><td>
<span id="DEM228_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM228)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_85_2" name="Infant Clinical Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did the infant/child have long bone x-rays" id="75194_1_2L" >
Did the Infant/Child Have Long Bone X-rays:</span></td><td>
<span id="75194_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75194_1)"
codeSetNm="PHVS_XRAYRESULT_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Did infant/child placenta or cord have Darkfield exam, DFA, or special stain?" id="75192_5_2L" >
Did the Infant/Child, Placenta, or Cord Have Darkfield Exam, DFA, or Special Stains:</span></td><td>
<span id="75192_5_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75192_5)"
codeSetNm="LAB_RSLT_INTERP_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the interpretation of the CSF WBC count testing results" id="LP48341_9_2L" >
CSF WBC Count Interpretation:</span></td><td>
<span id="LP48341_9_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP48341_9)"
codeSetNm="LAB_RSLT_CSF_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the interpretation of the CSF Protein level testing results" id="LP69956_8_2L" >
CSF Protein Level Interpretation:</span></td><td>
<span id="LP69956_8_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LP69956_8)"
codeSetNm="LAB_RSLT_PRT_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the CSF VDRL Test Finding" id="75195_8_2L" >
Did the Infant/Child Have CSF-VDRL:</span></td><td>
<span id="75195_8_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75195_8)"
codeSetNm="LAB_RSLT_VDRL_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is this case classified as a syphilitic stillbirth?" id="75207_1_2L" >
Stillbirth Indicator:</span></td><td>
<span id="75207_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75207_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV272_2L" title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click.">
Did the Infant/Child Have Any Signs of CS (Select All that Apply):</span></td><td>
<span id="INV272_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV272)"
codeSetNm="PHVS_SIGNSSYMPTOMS_CS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click." id="INV272_2_OthL">Other Did the Infant/Child Have Any Signs of CS (Select All that Apply):</span></td>
<td> <span id="INV272_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV272Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the infant/child treated for congenital syphilis?" id="75197_4_2L" >
Was the Infant/Child Treated?:</span></td><td>
<span id="75197_4_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(75197_4)"
codeSetNm="PHVS_SYPHILISTREATMENTINFANT_CS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the treatment type is 'Other', specify the treatment." id="NBS389_2L">
Other Treatment Specify:</span>
</td>
<td>
<span id="NBS389_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS389)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_86_2" name="Infant Interpretive Lab Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_86_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_86_2">
<tr id="patternNBS_INV_STD_UI_86_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_86_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_86_2">
<tr id="nopatternNBS_INV_STD_UI_86_2" class="odd" style="display:none">
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
<span title="Select the appropriate value to indicate timing and subject of the test being entered." id="LAB588_2L" >
Lab Test Performed Modifier (Infant):</span></td><td>
<span id="LAB588_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB588)"
codeSetNm="LAB_TST_MODIFIER_INFANT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290_2L" >
Test Type:</span></td><td>
<span id="INV290_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV290)"
codeSetNm="PHVS_TESTTYPE_SYPHILIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." id="INV291_2L" >
Test Result:</span></td><td>
<span id="INV291_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV291)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024." id="STD123_1_2L" >
Nontreponemal Serologic Syphilis Test Result (Quantitative):</span></td><td>
<span id="STD123_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD123_1)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date result sent from Reporting Laboratory" id="LAB167_2L">Lab Result Date:</span>
</td><td>
<span id="LAB167_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB167)"  />
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
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV169_2L" title="Condition_Cd should always be a hidden or read-only field.">Hidden Condition:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(INV169)" styleId="INV169_2"><html:optionsCollection property="codedValue(PHC_TYPE)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
