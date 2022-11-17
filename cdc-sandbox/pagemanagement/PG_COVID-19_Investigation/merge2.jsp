<?xml version="1.0" encoding="UTF-8"?>
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
<!--##Investigation Business Object##-->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<%@ page import="gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%
Map map2 = new HashMap();
if(request.getAttribute("SubSecStructureMap2") != null){
map2 =(Map)request.getAttribute("SubSecStructureMap2");
}
%>
<%
String tabId = "edit";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Patient Information","Occupation and Industry","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Place of Residence","Healthcare Worker Information","Exposure Information","Information Source","Signs &amp; Symptoms","Medical History","Vaccination","Respiratory Diagnostic Testing","COVID-19 Laboratory Findings","Contact Investigation","Retired Questions"};
;
%>
<tr><td>
<div style="float:left;width:100% ">    <%@ include file="/pagemanagement/patient/PatientSummaryCompare2.jsp" %> </div>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_6_2" name="General Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="
requiredInputField
InputFieldLabel" id="NBS104_2L" title="As of Date is the last known date for which the information is valid.">
Information As of Date:</span>
</td>
<td>
<html:text  name="PageForm" title="As of Date is the last known date for which the information is valid." styleClass="requiredInputField_2" property="pageClientVO2.answer(NBS104)" maxlength="10" size="10" styleId="NBS104_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM196_2L" title="General comments pertaining to the patient.">
Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(DEM196)" styleId ="DEM196_2" onkeyup="checkTextAreaLength(this, 2000)" title="General comments pertaining to the patient."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_7_2" name="Name Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS095_2L" title="As of Date is the last known date for which the information is valid.">
Name Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm" title="As of Date is the last known date for which the information is valid."  property="pageClientVO2.answer(NBS095)" maxlength="10" size="10" styleId="NBS095_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM104_2L" title="The patient's first name.">
First Name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM104)" maxlength="50" title="The patient's first name." styleId="DEM104"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM105_2L" title="The patient's middle name or initial.">
Middle Name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM105)" maxlength="50" title="The patient's middle name or initial." styleId="DEM105"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM102_2L" title="The patient's last name.">
Last Name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM102)" maxlength="50" title="The patient's last name." styleId="DEM102"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM107_2L" title="The patient's name suffix">
Suffix:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM107)" styleId="DEM107_2" title="The patient's name suffix">
<nedss:optionsCollection property="codedValue(P_NM_SFX)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_8_2" name="Other Personal Details" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS096_2L" title="As of Date is the last known date for which the information is valid.">
Other Personal Details As Of Date:</span>
</td>
<td>
<html:text name="PageForm" title="As of Date is the last known date for which the information is valid."  property="pageClientVO2.answer(NBS096)" maxlength="10" size="10" styleId="NBS096_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="DEM115_2L" title="Reported date of birth of patient.">
Date of Birth:</span>
</td>
<td>
<html:text name="PageForm" title="Reported date of birth of patient."  property="pageClientVO2.answer(DEM115)" maxlength="10" size="10" styleId="DEM115_2" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateReportedAge('DEM115','INV2001','INV2002','NBS096','NBS104')" onchange="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateReportedAge('DEM115','INV2001','INV2002','NBS096','NBS104')"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV2001_2L" title="The patient's age reported at the time of interview.">
Reported Age:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV2001)" size="3" maxlength="3"  title="The patient's age reported at the time of interview." styleId="INV2001_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,150)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV2002_2L" title="Patient's age units">
Reported Age Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV2002)" styleId="INV2002_2" title="Patient's age units">
<nedss:optionsCollection property="codedValue(AGE_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM126_2L" title="Country of Birth">
Country of Birth:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM126)" styleId="DEM126_2" title="Country of Birth">
<nedss:optionsCollection property="codedValue(PHVS_BIRTHCOUNTRY_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM113_2L" title="Patient's current sex.">
Current Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM113)" styleId="DEM113_2" title="Patient's current sex." onchange="ruleEnDisDEM1138837();ruleEnDisINV1788876();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SEX)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS097_2L" title="As of Date is the last known date for which the information is valid.">
Mortality Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm" title="As of Date is the last known date for which the information is valid."  property="pageClientVO2.answer(NBS097)" maxlength="10" size="10" styleId="NBS097_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM127_2L" title="Indicator of whether or not a patient is alive or dead.">
Is the patient deceased?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM127)" styleId="DEM127_2" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1278834();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="DEM128_2L" title="Date on which the individual died.">
Deceased Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date on which the individual died."  property="pageClientVO2.answer(DEM128)" maxlength="10" size="10" styleId="DEM128_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS098_2L" title="As of Date is the last known date for which the information is valid.">
Marital Status As Of Date:</span>
</td>
<td>
<html:text name="PageForm" title="As of Date is the last known date for which the information is valid."  property="pageClientVO2.answer(NBS098)" maxlength="10" size="10" styleId="NBS098_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM140_2L" title="A code indicating the married or similar partnership status of a patient.">
Marital Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM140)" styleId="DEM140_2" title="A code indicating the married or similar partnership status of a patient.">
<nedss:optionsCollection property="codedValue(P_MARITAL)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_15_2" name="Reporting Address for Case Counting" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS102_2L" title="As of Date is the last known date for which the information is valid.">
Address Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm" title="As of Date is the last known date for which the information is valid."  property="pageClientVO2.answer(NBS102)" maxlength="10" size="10" styleId="NBS102_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM159_2L" title="Line one of the address label.">
Street Address 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM159)" maxlength="50" title="Line one of the address label." styleId="DEM159"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM160_2L" title="Line two of the address label.">
Street Address 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM160)" maxlength="50" title="Line two of the address label." styleId="DEM160"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM161_2L" title="The city for a postal location.">
City:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM161)" maxlength="50" title="The city for a postal location." styleId="DEM161"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM162_2L" title="The state code for a postal location.">
State:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO2.answer(DEM162)" styleId="DEM162_2" title="The state code for a postal location." onchange="getDWRCounties(this, 'DEM165');getDWRCitites(this)">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM163_2L" title="The zip code of a residence of the case patient or entity.">
Zip:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM163)" maxlength="10" title="The zip code of a residence of the case patient or entity." styleId="DEM163_2" onkeyup="ZipMask(this,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM165_2L" title="The county of residence of the case patient or entity.">
County:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO2.answer(DEM165)" styleId="DEM165_2" title="The county of residence of the case patient or entity.">
<html:optionsCollection property="dwrCounties" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM167_2L" title="The country code for a postal location.">
Country:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM167)" styleId="DEM167_2" title="The country code for a postal location.">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_16_2" name="Telephone Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS103_2L" title="As of Date is the last known date for which the information is valid.">
Telephone Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm" title="As of Date is the last known date for which the information is valid."  property="pageClientVO2.answer(NBS103)" maxlength="10" size="10" styleId="NBS103_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM177_2L" title="The patient's home phone number.">
Home Phone:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM177)" maxlength="13" title="The patient's home phone number." styleId="DEM177_2" onkeyup="TeleMask(this, event)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS002_2L" title="The patient's work phone number.">
Work Phone:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS002)" maxlength="13" title="The patient's work phone number." styleId="NBS002_2" onkeyup="TeleMask(this, event)"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS003_2L" title="The patient's work phone number extension.">
Ext.:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS003)" size="8" maxlength="8"  title="The patient's work phone number extension." styleId="NBS003_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS006_2L" title="The patient's cellular phone number.">
Cell Phone:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS006)" maxlength="13" title="The patient's cellular phone number." styleId="NBS006_2" onkeyup="TeleMask(this, event)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM182_2L" title="The patient's email address.">
Email:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM182)" maxlength="50" title="The patient's email address." styleId="DEM182" onblur="checkEmail(this)" styleClass="emailField"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_9_2" name="Ethnicity and Race Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS100_2L" title="As of Date is the last known date for which the information is valid.">
Ethnicity Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm" title="As of Date is the last known date for which the information is valid."  property="pageClientVO2.answer(NBS100)" maxlength="10" size="10" styleId="NBS100_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM155_2L" title="Indicates if the patient is hispanic or not.">
Ethnicity:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM155)" styleId="DEM155_2" title="Indicates if the patient is hispanic or not.">
<nedss:optionsCollection property="codedValue(PHVS_ETHNICITYGROUP_CDC_UNK)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS101_2L" title="As of Date is the last known date for which the information is valid.">
Race Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm" title="As of Date is the last known date for which the information is valid."  property="pageClientVO2.answer(NBS101)" maxlength="10" size="10" styleId="NBS101_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Reported race; supports collection of multiple race categories.  This field could repeat." id="DEM152_2L">
Race:</span>
</td>
<td>
<html:checkbox styleId="DEM152_2_0"  name="PageForm" property="pageClientVO2.americanIndianAlskanRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox> <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2_1"  name="PageForm" property="pageClientVO2.asianRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.asian"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2_2"  name="PageForm" property="pageClientVO2.africanAmericanRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>   <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2_3"  name="PageForm" property="pageClientVO2.hawaiianRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2_4"  name="PageForm" property="pageClientVO2.whiteRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.white"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2_5"  name="PageForm" property="pageClientVO2.otherRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.otherRace"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2_6"  name="PageForm" property="pageClientVO2.refusedToAnswer" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2_7"  name="PageForm" property="pageClientVO2.notAsked" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.notAsked"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2_8"  name="PageForm" property="pageClientVO2.unKnownRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.unknown"/>
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35007_2" name="Language" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM142_2L" title="What is the patient primary language? Please indicate for both hospitalized and not hospitalized cases.">
Primary Language:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM142)" styleId="DEM142_2" title="What is the patient primary language? Please indicate for both hospitalized and not hospitalized cases.">
<nedss:optionsCollection property="codedValue(LANGUAGE)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA24000_2" name="Tribal Affiliation" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS681_2L" title="Does the patient have any indian tribal affiliation?">
Does this case have any tribal affiliation?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS681)" styleId="NBS681_2" title="Does the patient have any indian tribal affiliation?" onchange="ruleEnDisNBS6818858();enableOrDisableOther('95370_3');ruleEnDisNBS6828869();null;enableOrDisableOther('NBS779');null;enableOrDisableOther('NBS779');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95370_3_2L" title="If tribal affiliation, which tribe(s)?">
Tribal Name:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(95370_3)" styleId="95370_3_2" title="If tribal affiliation, which tribe(s)?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, '95370_3_2-selectedValues');enableOrDisableOther('95370_3')" >
<nedss:optionsCollection property="codedValue(PHVS_TRIBENAME_NND_COVID19)" value="key" label="value" /> </html:select>
<div id="95370_3_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If tribal affiliation, which tribe(s)?" id="95370_3_2OthL">Other Tribal Name:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(95370_3Oth)" size="40" maxlength="40" title="Other If tribal affiliation, which tribe(s)?" styleId="95370_3_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS682_2L" title="Is the patient an enrolled tribal member?">
Enrolled Tribal Member?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS682)" styleId="NBS682_2" title="Is the patient an enrolled tribal member?" onchange="ruleEnDisNBS6828869();null;enableOrDisableOther('NBS779');enableOrDisableOther('NBS779');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS779_2L" title="List enrolled tribe(s).">
Enrolled Tribe Name:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS779)" styleId="NBS779_2" title="List enrolled tribe(s)."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS779_2-selectedValues');enableOrDisableOther('NBS779')" >
<nedss:optionsCollection property="codedValue(PHVS_TRIBENAME_NND_COVID19)" value="key" label="value" /> </html:select>
<div id="NBS779_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="List enrolled tribe(s)." id="NBS779_2OthL">Other Enrolled Tribe Name:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS779Oth)" size="40" maxlength="40" title="Other List enrolled tribe(s)." styleId="NBS779_2Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35001_2" name="Occupation and Industry Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_GA35001_2errorMessages">
<b> <a name="NBS_UI_GA35001_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA35001_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
while(mapIt .hasNext())
{
Map.Entry mappairs = (Map.Entry)mapIt .next();
if(mappairs.getKey().toString().equals(subSecNm)){
batchrec =(String[][]) mappairs.getValue();
break;
}
}%>
<%int wid =100/11; %>
<td style="background-color: #EFEFEF; border:1px solid #666666"  width="9%" colspan="1"> &nbsp;</td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<th width="<%=aInt%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_GA35001_2">
<tr id="patternNBS_UI_GA35001_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_GA35001_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA35001_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][4] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<td width="<%=aInt%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="questionbodyNBS_UI_GA35001_2">
<tr id="nopatternNBS_UI_GA35001_2" class="odd" style="display:">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35001 InputFieldLabel" id="85659_1_2L" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation.">
Current Occupation Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85659_1)" styleId="85659_1_2" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation." onchange="unhideBatchImg('NBS_UI_GA35001');">
<nedss:optionsCollection property="codedValue(PHVS_OCCUPATION_CDC_CENSUS2010)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="85658_3_2L" title="This data element is used to capture the narrative text of a subjects current occupation.">
Current Occupation:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(85658_3)" styleId ="85658_3_2" onkeyup="checkTextAreaLength(this, 199)" title="This data element is used to capture the narrative text of a subjects current occupation."/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35001 InputFieldLabel" id="85657_5_2L" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry.">
Current  Industry Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85657_5)" styleId="85657_5_2" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry." onchange="unhideBatchImg('NBS_UI_GA35001');">
<nedss:optionsCollection property="codedValue(PHVS_INDUSTRY_CDC_CENSUS2010)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="85078_4_2L" title="This data element is used to capture the narrative text of subjects current industry.">
Current Industry:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(85078_4)" styleId ="85078_4_2" onkeyup="checkTextAreaLength(this, 199)" title="This data element is used to capture the narrative text of subjects current industry."/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_GA35001">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_GA35001BatchAddFunction()) writeQuestion('NBS_UI_GA35001_2','patternNBS_UI_GA35001_2','questionbodyNBS_UI_GA35001_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_GA35001_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_GA35001BatchAddFunction()) writeQuestion('NBS_UI_GA35001_2','patternNBS_UI_GA35001_2','questionbodyNBS_UI_GA35001_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_GA35001_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_GA35001BatchAddFunction()) writeQuestion('NBS_UI_GA35001_2','patternNBS_UI_GA35001_2','questionbodyNBS_UI_GA35001_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_GA35001"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_GA35001_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_19_2" name="Investigation Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField_2 InputFieldLabel" id="INV107_2L" title="The jurisdiction of the investigation.">
Jurisdiction:</span>
</td>
<td>

<!--processing Jurisdistion Coded Question-->
<logic:empty name="PageForm" property="attributeMap2.ReadOnlyJursdiction"><html:select name="PageForm" property="pageClientVO2.answer(INV107)" styleId="INV107_2" title="The jurisdiction of the investigation.">
<html:optionsCollection property="jurisdictionList" value="key" label="value" /> </html:select></logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.ReadOnlyJursdiction"><nedss:view name="PageForm" property="pageClientVO2.answer(INV107)" codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/> <html:hidden name="PageForm" property="pageClientVO2.answer(INV107)"/></logic:notEmpty>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField_2 InputFieldLabel" id="INV108_2L" title="The program area associated with the investigaiton condition.">
Program Area:</span>
</td>
<td>

<!--processing Program Area Coded Question - read only-->
<nedss:view name="PageForm" property="pageClientVO2.answer(INV108)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/><html:hidden name="PageForm" property="pageClientVO2.answer(INV108)" />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV147_2L" title="The date the investigation was started or initiated.">
Investigation Start Date:</span>
</td>
<td>
<html:text name="PageForm" title="The date the investigation was started or initiated."  property="pageClientVO2.answer(INV147)" maxlength="10" size="10" styleId="INV147_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField_2 InputFieldLabel" id="INV109_2L" title="The status of the investigation.">
Investigation Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV109)" styleId="INV109_2" title="The status of the investigation.">
<nedss:optionsCollection property="codedValue(PHC_IN_STS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Should this record be shared with guests with program area and jurisdiction rights?" id="NBS012_2L">
Shared Indicator:</span>
</td>
<td>
<html:checkbox styleClass="requiredInputField_2" name="PageForm" property="pageClientVO2.answer(NBS012)" value="1"
title="Should this record be shared with guests with program area and jurisdiction rights?"></html:checkbox>
</td>
</tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV173_2L" title="The State ID associated with the case.">
State Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV173)" maxlength="15" title="The State ID associated with the case." styleId="INV173_2" onkeyup="isAlphaNumericCharacterEntered(this);UpperCaseMask(this);chkMaxLength(this,15)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV200_2L" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here.">
NNDSS Local Record ID (NETSS):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV200)" maxlength="50" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." styleId="INV200"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21015_2" name="COVID-19 Case Details" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS547_2L" title="CDC-Assigned Case ID">
CDC 2019-nCoV ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS547)" maxlength="50" title="CDC-Assigned Case ID" styleId="NBS547"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS548_2L" title="What is the current processing status of this person?">
What is the current status of this person?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS548)" styleId="NBS548_2" title="What is the current processing status of this person?" onchange="ruleEnDisNBS5488859();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PATIENT_STATUS_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS678_2L" title="If probable, select reason for case classification">
If probable, select reason for case classification:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS678)" styleId="NBS678_2" title="If probable, select reason for case classification">
<nedss:optionsCollection property="codedValue(CASE_CLASS_REASON_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS551_2L" title="Under what process was the PUI or case first identified?">
Under what process was the case first identified?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS551)" styleId="NBS551_2" title="Under what process was the PUI or case first identified?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS551_2-selectedValues');ruleEnDisNBS5518847();enableOrDisableOther('NBS551')" >
<nedss:optionsCollection property="codedValue(CASE_IDENTIFY_PROCESS_COVID)" value="key" label="value" /> </html:select>
<div id="NBS551_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Under what process was the PUI or case first identified?" id="NBS551_2OthL">Other Under what process was the case first identified?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS551Oth)" size="40" maxlength="40" title="Other Under what process was the PUI or case first identified?" styleId="NBS551_2Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS552_2L" title="If the PUI or case was first identified through and EpiX notification of travelers, enter the Division of Global Migration and Quarantine ID.">
DGMQ ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS552)" maxlength="50" title="If the PUI or case was first identified through and EpiX notification of travelers, enter the Division of Global Migration and Quarantine ID." styleId="NBS552"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS550_2L" title="Date of first positive specimen collection">
Date of first positive specimen collection:</span>
</td>
<td>
<html:text name="PageForm" title="Date of first positive specimen collection"  property="pageClientVO2.answer(NBS550)" maxlength="10" size="10" styleId="NBS550_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20_2" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180_2L" title="The Public Health Investigator assigned to the Investigation.">
Investigator:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.INV180Uid">
<span id="clearINV180_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV180Uid">
<span id="clearINV180_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="INV180CodeClearButton_2" onclick="clearProvider('INV180')"/>
</span>
<span id="INV180_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="INV180_2_Icon" onclick="getProvider('INV180');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(INV180)" styleId="INV180_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV180Text','INV180_qec_list')"
title="The Public Health Investigator assigned to the Investigation."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="INV180CodeLookupButton" onclick="getDWRProvider('INV180')"
<logic:notEmpty name="PageForm" property="attributeMap2.INV180Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV180_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV180_2S">Investigator Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.INV180Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV180Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.INV180Uid"/>
<span id="INV180_2">${PageForm.attributeMap2.INV180SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV180_2Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV110_2L" title="The date the investigation was assigned/started.">
Date Assigned to Investigation:</span>
</td>
<td>
<html:text name="PageForm" title="The date the investigation was assigned/started."  property="pageClientVO2.answer(INV110)" maxlength="10" size="10" styleId="INV110_2" onkeyup="DateMask(this,null,event)"/>
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
<span class="
InputFieldLabel" id="INV111_2L" title="The date of report of the condition to the public health department.">
Date of Report:</span>
</td>
<td>
<html:text name="PageForm" title="The date of report of the condition to the public health department."  property="pageClientVO2.answer(INV111)" maxlength="10" size="10" styleId="INV111_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="Date the report was first sent to the public health department (local, county or state) by reporter (physician, lab, etc.)." id="INV177_2L">
Date First Reported to PHD:</span>
</td>
<td>
<html:text name="PageForm" title="Date the report was first sent to the public health department (local, county or state) by reporter (physician, lab, etc.)." property="pageClientVO2.answer(INV177)" maxlength="10" size="10" styleId="INV177_2"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV120_2L" title="Earliest date reported to county public health system.">
Earliest Date Reported to County:</span>
</td>
<td>
<html:text name="PageForm" title="Earliest date reported to county public health system."  property="pageClientVO2.answer(INV120)" maxlength="10" size="10" styleId="INV120_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV121_2L" title="Earliest date reported to state public health system.">
Earliest Date Reported to State:</span>
</td>
<td>
<html:text name="PageForm" title="Earliest date reported to state public health system."  property="pageClientVO2.answer(INV121)" maxlength="10" size="10" styleId="INV121_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_23_2" name="Reporting Organization" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV112_2L" title="Type of facility or provider associated with the source of information sent to Public Health.">
Reporting Source Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV112)" styleId="INV112_2" title="Type of facility or provider associated with the source of information sent to Public Health.">
<nedss:optionsCollection property="codedValue(PHVS_REPORTINGSOURCETYPE_NND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV183_2L" title="The organization that reported the case.">
Reporting Organization:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.INV183Uid">
<span id="clearINV183" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV183Uid">
<span id="clearINV183">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="INV183CodeClearButton" onclick="clearOrganization('INV183')"/>
</span>
<span id="INV183_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="INV183Icon" onclick="getReportingOrg('INV183');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(INV183)" styleId="INV183Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV183Text','INV183_qec_list')"
title="The organization that reported the case."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="INV183CodeLookupButton" onclick="getDWROrganization('INV183')"
<logic:notEmpty name="PageForm" property="attributeMap2.INV183Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV183_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV183_2S">Reporting Organization Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.INV183Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV183Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.INV183Uid"/>
<span id="INV183_2">${PageForm.attributeMap2.INV183SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV183_2Error"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_10_2" name="Reporting Provider" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV181_2L" title="The provider that reported the case.">
Reporting Provider:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.INV181Uid">
<span id="clearINV181_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV181Uid">
<span id="clearINV181_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="INV181CodeClearButton_2" onclick="clearProvider('INV181')"/>
</span>
<span id="INV181_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="INV181_2_Icon" onclick="getProvider('INV181');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(INV181)" styleId="INV181_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV181Text','INV181_qec_list')"
title="The provider that reported the case."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="INV181CodeLookupButton" onclick="getDWRProvider('INV181')"
<logic:notEmpty name="PageForm" property="attributeMap2.INV181Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV181_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV181_2S">Reporting Provider Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.INV181Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV181Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.INV181Uid"/>
<span id="INV181_2">${PageForm.attributeMap2.INV181SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV181_2Error"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_1_2" name="Reporting County" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NOT113_2L" title="County reporting the notification.">
Reporting County:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO2.answer(NOT113)" styleId="NOT113_2" title="County reporting the notification.">
<html:optionsCollection property="dwrDefaultStateCounties" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_12_2" name="Physician" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV182_2L" title="The physician associated with this case.">
Physician:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.INV182Uid">
<span id="clearINV182_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV182Uid">
<span id="clearINV182_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="INV182CodeClearButton_2" onclick="clearProvider('INV182')"/>
</span>
<span id="INV182_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="INV182_2_Icon" onclick="getProvider('INV182');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(INV182)" styleId="INV182_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV182Text','INV182_qec_list')"
title="The physician associated with this case."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="INV182CodeLookupButton" onclick="getDWRProvider('INV182')"
<logic:notEmpty name="PageForm" property="attributeMap2.INV182Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV182_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV182_2S">Physician Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.INV182Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV182Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.INV182Uid"/>
<span id="INV182_2">${PageForm.attributeMap2.INV182SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV182_2Error"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3_2" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128_2L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV128)" styleId="INV128_2" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288832();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328838();ruleEnDis3099040018860();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV184_2L" title="The hospital associated with the investigation.">
Hospital:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.INV184Uid">
<span id="clearINV184" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV184Uid">
<span id="clearINV184">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="INV184CodeClearButton" onclick="clearOrganization('INV184')"/>
</span>
<span id="INV184_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="INV184Icon" onclick="getReportingOrg('INV184');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(INV184)" styleId="INV184Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV184Text','INV184_qec_list')"
title="The hospital associated with the investigation."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="INV184CodeLookupButton" onclick="getDWROrganization('INV184')"
<logic:notEmpty name="PageForm" property="attributeMap2.INV184Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV184_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV184_2S">Hospital Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.INV184Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.INV184Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.INV184Uid"/>
<span id="INV184_2">${PageForm.attributeMap2.INV184SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV184_2Error"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV132_2L" title="Subject's admission date to the hospital for the condition covered by the investigation.">
Admission Date:</span>
</td>
<td>
<html:text name="PageForm" title="Subject's admission date to the hospital for the condition covered by the investigation."  property="pageClientVO2.answer(INV132)" maxlength="10" size="10" styleId="INV132_2" onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV133_2L" title="Subject's discharge date from the hospital for the condition covered by the investigation.">
Discharge Date:</span>
</td>
<td>
<html:text name="PageForm" title="Subject's discharge date from the hospital for the condition covered by the investigation."  property="pageClientVO2.answer(INV133)" maxlength="10" size="10" styleId="INV133_2" onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV134_2L" title="Subject's duration of stay at the hospital for the condition covered by the investigation.">
Total Duration of Stay in the Hospital (in days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV134)" size="3" maxlength="3"  title="Subject's duration of stay at the hospital for the condition covered by the investigation." styleId="INV134_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="54588_9_2L" title="Does the patient need or want an interpreter to communicate with doctor or healthcare staff?">
If hospitalized, was a translator required?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(54588_9)" styleId="54588_9_2" title="Does the patient need or want an interpreter to communicate with doctor or healthcare staff?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="309904001_2L" title="Was the patient admitted to an intensive care unit (ICU)?">
Was the patient admitted to an intensive care unit (ICU)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(309904001)" styleId="309904001_2" title="Was the patient admitted to an intensive care unit (ICU)?" onchange="ruleEnDis3099040018860();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS679_2L" title="Enter the date of ICU admission.">
ICU Admission Date:</span>
</td>
<td>
<html:text name="PageForm" title="Enter the date of ICU admission."  property="pageClientVO2.answer(NBS679)" maxlength="10" size="10" styleId="NBS679_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS680_2L" title="Enter the date of ICU discharge.">
ICU Discharge Date:</span>
</td>
<td>
<html:text name="PageForm" title="Enter the date of ICU discharge."  property="pageClientVO2.answer(NBS680)" maxlength="10" size="10" styleId="NBS680_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV145_2L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV145)" styleId="INV145_2" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1458833();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV146_2L" title="The date the subject’s death occurred.">
Date of Death:</span>
</td>
<td>
<html:text name="PageForm" title="The date the subject’s death occurred."  property="pageClientVO2.answer(INV146)" maxlength="10" size="10" styleId="INV146_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS545_2L" title="If the date of death is unknown, select yes.">
Unknown Date of Death:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS545)" styleId="NBS545_2" title="If the date of death is unknown, select yes.">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14_2" name="Condition" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV136_2L" title="Date of diagnosis of condition being reported to public health system.">
Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date of diagnosis of condition being reported to public health system."  property="pageClientVO2.answer(INV136)" maxlength="10" size="10" styleId="INV136_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25_2" name="Epi-Link" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV148_2L" title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility.">
Is this person associated with a day care facility?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV148)" styleId="INV148_2" title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV149_2L" title="Indicates whether the subject of the investigation was food handler.">
Is this person a food handler?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV149)" styleId="INV149_2" title="Indicates whether the subject of the investigation was food handler.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV150_2L" title="Denotes whether the reported case was associated with an identified outbreak.">
Is this case part of an outbreak?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV150)" styleId="INV150_2" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1508835();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV151_2L" title="A name assigned to an individual outbreak.   State assigned in SRT.  Should show only those outbreaks for the program area of the investigation.">
Outbreak Name:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV151)" styleId="INV151_2" title="A name assigned to an individual outbreak.   State assigned in SRT.  Should show only those outbreaks for the program area of the investigation.">
<nedss:optionsCollection property="codedValue(OUTBREAK_NM)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_1_2" name="Disease Acquisition" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV152_2L" title="Indication of where the disease/condition was likely acquired.">
Where was the disease acquired?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV152)" styleId="INV152_2" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1528836();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_DISEASEACQUIREDJURISDICTION_NND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV153_2L" title="If the disease or condition was imported, indicate the country in which the disease was likely acquired.">
Imported Country:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV153)" styleId="INV153_2" title="If the disease or condition was imported, indicate the country in which the disease was likely acquired.">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV154_2L" title="If the disease or condition was imported, indicate the state in which the disease was likely acquired.">
Imported State:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO2.answer(INV154)" styleId="INV154_2" title="If the disease or condition was imported, indicate the state in which the disease was likely acquired." onchange="getDWRCounties(this, 'INV156')">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV155_2L" title="If the disease or condition was imported, indicate the city in which the disease was likely acquired.">
Imported City:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV155)" maxlength="50" title="If the disease or condition was imported, indicate the city in which the disease was likely acquired." styleId="INV155"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV156_2L" title="If the disease or condition was imported, this field will contain the county of origin of the disease or condition.">
Imported County:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO2.answer(INV156)" styleId="INV156_2" title="If the disease or condition was imported, this field will contain the county of origin of the disease or condition.">
<html:optionsCollection property="dwrImportedCounties2" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV501_2L" title="Where does the person usually live (defined as their residence).">
Country of Usual Residence:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV501)" styleId="INV501_2" title="Where does the person usually live (defined as their residence).">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_4_2" name="Exposure Location" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_NBS_INV_GENV2_UI_4_2errorMessages">
<b> <a name="NBS_UI_NBS_INV_GENV2_UI_4_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<td style="background-color: #EFEFEF; border:1px solid #666666"  width="9%" colspan="1"> &nbsp;</td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<th width="<%=aInt%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_NBS_INV_GENV2_UI_4_2">
<tr id="patternNBS_UI_NBS_INV_GENV2_UI_4_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_NBS_INV_GENV2_UI_4_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_NBS_INV_GENV2_UI_4_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][4] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<td width="<%=aInt%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="questionbodyNBS_UI_NBS_INV_GENV2_UI_4_2">
<tr id="nopatternNBS_UI_NBS_INV_GENV2_UI_4_2" class="odd" style="display:">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV502_2L" title="Indicates the country in which the disease was potentially acquired.">
Country of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV502)" styleId="INV502_2" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');ruleEnDisINV5028842();getDWRStatesByCountry(this, 'INV503');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV503_2L" title="Indicates the state in which the disease was potentially acquired.">
State or Province of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV503)" styleId="INV503_2" title="Indicates the state in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');getDWRCounties(this, 'INV505');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_STATEPROVINCEOFEXPOSURE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV504_2L" title="Indicates the city in which the disease was potentially acquired.">
City of Exposure:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV504)"  maxlength="100" title="Indicates the city in which the disease was potentially acquired." styleId="INV504_2" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV505_2L" title="Indicates the county in which the disease was potentially acquired.">
County of Exposure:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO2.answer(INV505)" styleId="INV505_2" title="Indicates the county in which the disease was potentially acquired.">
<html:optionsCollection property="dwrImportedCounties2" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_NBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_GENV2_UI_4_2','patternNBS_UI_NBS_INV_GENV2_UI_4_2','questionbodyNBS_UI_NBS_INV_GENV2_UI_4_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_NBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_GENV2_UI_4_2','patternNBS_UI_NBS_INV_GENV2_UI_4_2','questionbodyNBS_UI_NBS_INV_GENV2_UI_4_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_NBS_INV_GENV2_UI_4_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_NBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_GENV2_UI_4_2','patternNBS_UI_NBS_INV_GENV2_UI_4_2','questionbodyNBS_UI_NBS_INV_GENV2_UI_4_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_NBS_INV_GENV2_UI_4"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_NBS_INV_GENV2_UI_4_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_5_2" name="Binational Reporting" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV515_2L" title="For cases meeting the binational criteria, select all the criteria which are met.">
Binational Reporting Criteria:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(INV515)" styleId="INV515_2" title="For cases meeting the binational criteria, select all the criteria which are met."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV515_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVS_BINATIONALREPORTINGCRITERIA_CDC)" value="key" label="value" /> </html:select>
<div id="INV515_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2_2" name="Case Status" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV157_2L" title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate.">
Transmission Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV157)" styleId="INV157_2" title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate.">
<nedss:optionsCollection property="codedValue(PHC_TRAN_M)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="INV159_2L" title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc.">
Detection Method:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV159)" styleId="INV159_2" title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc.">
<nedss:optionsCollection property="codedValue(PHC_DET_MT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV161_2L" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived.">
Confirmation Method:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(INV161)" styleId="INV161_2" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV161_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHC_CONF_M)" value="key" label="value" /> </html:select>
<div id="INV161_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV162_2L" title="If an investigation is confirmed as a case, then the confirmation date is entered.">
Confirmation Date:</span>
</td>
<td>
<html:text name="PageForm" title="If an investigation is confirmed as a case, then the confirmation date is entered."  property="pageClientVO2.answer(INV162)" maxlength="10" size="10" styleId="INV162_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV163_2L" title="The current status of the investigation/case.">
Case Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV163)" styleId="INV163_2" title="The current status of the investigation/case.">
<nedss:optionsCollection property="codedValue(PHC_CLASS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV165_2L" title="The MMWR week in which the case should be counted.">
MMWR Week:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV165)" maxlength="2" title="The MMWR week in which the case should be counted." styleId="INV165_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,53)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV166_2L" title="The MMWR year in which the case should be counted.">
MMWR Year:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV166)" maxlength="4" title="The MMWR year in which the case should be counted." styleId="INV166_2" onkeyup="YearMask(this, event)" onblur="pgCheckFullYear(this)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NOT120_2L" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?">
Immediate National Notifiable Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NOT120)" styleId="NOT120_2" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1208843();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NOT120SPEC_2L" title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition.">
If yes, describe:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NOT120SPEC)" maxlength="100" title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." styleId="NOT120SPEC"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV176_2L" title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition.">
Date CDC Was First Verbally Notified of This Case:</span>
</td>
<td>
<html:text name="PageForm" title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition."  property="pageClientVO2.answer(INV176)" maxlength="10" size="10" styleId="INV176_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV886_2L" title="Do not send personally identifiable information to CDC in this field. Use this field, if needed, to communicate anything unusual about this case, which is not already covered with the other data elements.  Alternatively, use this field to communicate information to the CDC NNDSS staff processing the data.">
Notification Comments to CDC:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(INV886)" styleId ="INV886_2" onkeyup="checkTextAreaLength(this, 2000)" title="Do not send personally identifiable information to CDC in this field. Use this field, if needed, to communicate anything unusual about this case, which is not already covered with the other data elements.  Alternatively, use this field to communicate information to the CDC NNDSS staff processing the data."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_27_2" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV167_2L" title="Field which contains general comments for the investigation.">
General Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(INV167)" styleId ="INV167_2" onkeyup="checkTextAreaLength(this, 2000)" title="Field which contains general comments for the investigation."/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA24002_2" name="Place of Residence" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95421_4_2L" title="Is the patient a resident in a congregate care/living setting? This can include nursing homes, residential care for people with intellectual and developmental disabilities, psychiatric treatment facilities, group homes, board and care homes, homeless shelter, foster care, etc.">
Is the patient a resident in a congregate care/living setting?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(95421_4)" styleId="95421_4_2" title="Is the patient a resident in a congregate care/living setting? This can include nursing homes, residential care for people with intellectual and developmental disabilities, psychiatric treatment facilities, group homes, board and care homes, homeless shelter, foster care, etc.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS202_2L" title="Which would best describe where the patient was staying at the time of illness onset?">
Which would best describe where the patient was staying at the time of illness onset?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS202)" styleId="NBS202_2" title="Which would best describe where the patient was staying at the time of illness onset?" onchange="enableOrDisableOther('NBS202');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(RESIDENCE_TYPE_COVID)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Which would best describe where the patient was staying at the time of illness onset?" id="NBS202_2OthL">Other Which would best describe where the patient was staying at the time of illness onset?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS202Oth)" size="40" maxlength="40" title="Other Which would best describe where the patient was staying at the time of illness onset?" styleId="NBS202_2Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25005_2" name="Healthcare Worker Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS540_2L" title="Is the patient a health care worker in the United States?">
Was patient healthcare personnel (HCP) at illness onset?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS540)" styleId="NBS540_2" title="Is the patient a health care worker in the United States?" onchange="ruleEnDisNBS5408861();enableOrDisableOther('NBS683');enableOrDisableOther('14679004');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="14679004_2L" title="If the patient is a healthcare worker, specify occupation (type of job)">
If yes, what is their occupation (type of job)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(14679004)" styleId="14679004_2" title="If the patient is a healthcare worker, specify occupation (type of job)" onchange="enableOrDisableOther('14679004');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(HCW_OCCUPATION_COVID)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If the patient is a healthcare worker, specify occupation (type of job)" id="14679004_2OthL">Other If yes, what is their occupation (type of job)?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(14679004Oth)" size="40" maxlength="40" title="Other If the patient is a healthcare worker, specify occupation (type of job)" styleId="14679004_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS683_2L" title="If the patient is a healthcare worker, what is their job setting?">
If yes, what is their job setting?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS683)" styleId="NBS683_2" title="If the patient is a healthcare worker, what is their job setting?" onchange="enableOrDisableOther('NBS683');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(HCW_SETTING_COVID)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If the patient is a healthcare worker, what is their job setting?" id="NBS683_2OthL">Other If yes, what is their job setting?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS683Oth)" size="40" maxlength="40" title="Other If the patient is a healthcare worker, what is their job setting?" styleId="NBS683_2Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25007_2" name="Travel Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV664_2L" title="Did the case patient travel domestically within program specific time frame?">
Domestic travel (outside normal state of residence):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV664)" styleId="INV664_2" title="Did the case patient travel domestically within program specific time frame?" onchange="ruleEnDisINV6648862();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL38_2L" title="Did the case patient travel internationally?">
International Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(TRAVEL38)" styleId="TRAVEL38_2" title="Did the case patient travel internationally?" onchange="ruleEnDisTRAVEL388863();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="473085002_2L" title="Prior to illness onset, did the patient travel by cruise ship or vessel, either as a passenger or crew member?">
Cruise ship or vessel travel as passenger or crew member:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(473085002)" styleId="473085002_2" title="Prior to illness onset, did the patient travel by cruise ship or vessel, either as a passenger or crew member?" onchange="ruleEnDis4730850028864();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS690_2L" title="Name of cruise ship or vessel.">
Specify Name of Ship or Vessel:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS690)" maxlength="50" title="Name of cruise ship or vessel." styleId="NBS690"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35002_2" name="Travel Events Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_GA35002_2errorMessages">
<b> <a name="NBS_UI_GA35002_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA35002_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
while(mapIt .hasNext())
{
Map.Entry mappairs = (Map.Entry)mapIt .next();
if(mappairs.getKey().toString().equals(subSecNm)){
batchrec =(String[][]) mappairs.getValue();
break;
}
}%>
<%int wid =100/11; %>
<td style="background-color: #EFEFEF; border:1px solid #666666"  width="9%" colspan="1"> &nbsp;</td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<th width="<%=aInt%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_GA35002_2">
<tr id="patternNBS_UI_GA35002_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_GA35002_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA35002_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][4] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<td width="<%=aInt%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="questionbodyNBS_UI_GA35002_2">
<tr id="nopatternNBS_UI_GA35002_2" class="odd" style="display:">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35002 InputFieldLabel" id="TRAVEL05_2L" title="Indicate all countries of travel in the last 14 days.">
Country of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(TRAVEL05)" styleId="TRAVEL05_2" title="Indicate all countries of travel in the last 14 days." onchange="unhideBatchImg('NBS_UI_GA35002');">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35002 InputFieldLabel" id="82754_3_2L" title="Domestic destination, states traveled">
State of Travel:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO2.answer(82754_3)" styleId="82754_3_2" title="Domestic destination, states traveled">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35002 InputFieldLabel" id="NBS453_2L" title="Choose the mode of travel">
Travel Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS453)" styleId="NBS453_2" title="Choose the mode of travel" onchange="unhideBatchImg('NBS_UI_GA35002');">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELMODE_CDC_COVID19)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA35002 InputFieldLabel" id="TRAVEL16_2L" title="Principal Reason for Travel">
Principal Reason for Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(TRAVEL16)" styleId="TRAVEL16_2" title="Principal Reason for Travel" onchange="unhideBatchImg('NBS_UI_GA35002');enableOrDisableOther('TRAVEL16');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELREASON_MALARIA)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Principal Reason for Travel" id="TRAVEL16_2OthL">Other Principal Reason for Travel:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(TRAVEL16Oth)" size="40" maxlength="40" title="Other Principal Reason for Travel" onkeyup="unhideBatchImg('NBS_UI_GA35002')" styleId="TRAVEL16_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL06_2L" title="If the subject traveled, when did they arrive to their travel destination?">
Date of Arrival at Destination:</span>
</td>
<td>
<html:text name="PageForm" title="If the subject traveled, when did they arrive to their travel destination?"  property="pageClientVO2.answer(TRAVEL06)" maxlength="10" size="10" styleId="TRAVEL06_2" onkeyup="unhideBatchImg('NBS_UI_GA35002');DateMask(this,null,event)" styleClass="NBS_UI_GA35002"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL07_2L" title="If the subject traveled, when did they depart from their travel destination?">
Date of Departure from Destination:</span>
</td>
<td>
<html:text name="PageForm" title="If the subject traveled, when did they depart from their travel destination?"  property="pageClientVO2.answer(TRAVEL07)" maxlength="10" size="10" styleId="TRAVEL07_2" onkeyup="unhideBatchImg('NBS_UI_GA35002');DateMask(this,null,event)" styleClass="NBS_UI_GA35002"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82310_4_2L" title="Duration of stay in country outside the US">
Duration of Stay:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(82310_4)" size="3" maxlength="3"  title="Duration of stay in country outside the US" styleId="82310_4_2" onkeyup="unhideBatchImg('NBS_UI_GA35002');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_GA35002"/>
<html:select name="PageForm" property="pageClientVO2.answer(82310_4Unit)" styleId="82310_4UNIT_2" onchange="unhideBatchImg('NBS_UI_GA35002')">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC_1)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="TRAVEL23_2L" title="Additional Travel Information">
Additional Travel Information:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(TRAVEL23)" styleId ="TRAVEL23_2" onkeyup="checkTextAreaLength(this, 199)" title="Additional Travel Information"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_GA35002">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_GA35002BatchAddFunction()) writeQuestion('NBS_UI_GA35002_2','patternNBS_UI_GA35002_2','questionbodyNBS_UI_GA35002_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_GA35002_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_GA35002BatchAddFunction()) writeQuestion('NBS_UI_GA35002_2','patternNBS_UI_GA35002_2','questionbodyNBS_UI_GA35002_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_GA35002_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_GA35002BatchAddFunction()) writeQuestion('NBS_UI_GA35002_2','patternNBS_UI_GA35002_2','questionbodyNBS_UI_GA35002_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_GA35002"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_GA35002_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21014_2" name="Other Exposure Events" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS684_2L" title="Prior to illness onset, did the patient have exposure in the workplace?">
Workplace:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS684)" styleId="NBS684_2" title="Prior to illness onset, did the patient have exposure in the workplace?" onchange="ruleEnDisNBS6848865();ruleEnDisNBS6858870();null;enableOrDisableOther('NBS686_CD');null;enableOrDisableOther('NBS686_CD');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS685_2L" title="If patient experienced a workplace exposure, is the workplace considered critical infrastructure (e.g. healthcare setting, grocery store, etc.)?">
If yes, is the workplace critical infrastructure (e.g. healthcare setting, grocery store)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS685)" styleId="NBS685_2" title="If patient experienced a workplace exposure, is the workplace considered critical infrastructure (e.g. healthcare setting, grocery store, etc.)?" onchange="ruleEnDisNBS6858870();null;enableOrDisableOther('NBS686_CD');enableOrDisableOther('NBS686_CD');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS686_CD_2L" title="Specify the workplace setting">
If yes, specify workplace setting:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS686_CD)" styleId="NBS686_CD_2" title="Specify the workplace setting"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS686_CD_2-selectedValues');enableOrDisableOther('NBS686_CD')" >
<nedss:optionsCollection property="codedValue(PHVS_CRITICALINFRASTRUCTURESECTOR_NND_COVID19)" value="key" label="value" /> </html:select>
<div id="NBS686_CD_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Specify the workplace setting" id="NBS686_CD_2OthL">Other If yes, specify workplace setting:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS686_CDOth)" size="40" maxlength="40" title="Other Specify the workplace setting" styleId="NBS686_CD_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="445000002_2L" title="Prior to illness onset, did the patient travel by air?">
Airport/Airplane:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(445000002)" styleId="445000002_2" title="Prior to illness onset, did the patient travel by air?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS687_2L" title="Prior to illness onset, did the patient have an exposure related to living in an adult congregate living facility (e.g. nursing, assisted living, or long term care facility).">
Adult Congregate Living Facility (nursing, assisted living, or LTC facility):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS687)" styleId="NBS687_2" title="Prior to illness onset, did the patient have an exposure related to living in an adult congregate living facility (e.g. nursing, assisted living, or long term care facility).">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS689_2L" title="Prior to illness onset, was patient exposed to a correctional facility?">
Correctional Facility:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS689)" styleId="NBS689_2" title="Prior to illness onset, was patient exposed to a correctional facility?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="257698009_2L" title="Prior to illness onset, was the patient exposed to a school oruniversity ?">
School/University Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(257698009)" styleId="257698009_2" title="Prior to illness onset, was the patient exposed to a school oruniversity ?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413817003_2L" title="Prior to illness onset, was the patient exposed to a child care facility?">
Child Care Facility Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(413817003)" styleId="413817003_2" title="Prior to illness onset, was the patient exposed to a child care facility?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_184_2L" title="Did subject attend any events or large gatherings prior to onset of illness?">
Community Event/Mass Gathering:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(FDD_Q_184)" styleId="FDD_Q_184_2" title="Did subject attend any events or large gatherings prior to onset of illness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS559_2L" title="Did the patient have exposure to an animal with confirmed or suspected COVID-19?">
Animal with confirmed or suspected COVID-19:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS559)" styleId="NBS559_2" title="Did the patient have exposure to an animal with confirmed or suspected COVID-19?" onchange="ruleEnDisNBS5598866();enableOrDisableOther('FDD_Q_32');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_32_2L" title="Animal contact by type of animal">
Animal Type:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(FDD_Q_32)" styleId="FDD_Q_32_2" title="Animal contact by type of animal"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_32_2-selectedValues');enableOrDisableOther('FDD_Q_32')" >
<nedss:optionsCollection property="codedValue(PHVS_ANIMALTYPE_COVID19)" value="key" label="value" /> </html:select>
<div id="FDD_Q_32_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Animal contact by type of animal" id="FDD_Q_32_2OthL">Other Animal Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(FDD_Q_32Oth)" size="40" maxlength="40" title="Other Animal contact by type of animal" styleId="FDD_Q_32_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS560_2L" title="Did the patient have any other exposure type?">
Other Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS560)" styleId="NBS560_2" title="Did the patient have any other exposure type?" onchange="ruleEnDisNBS5608846();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS561_2L" title="Specify other exposure.">
Other Exposure Specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS561)" maxlength="100" title="Specify other exposure." styleId="NBS561"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS667_2L" title="Unknown exposures in the 14 days prior to illness onset">
Unknown exposures in the 14 days prior to illness onset:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS667)" styleId="NBS667_2" title="Unknown exposures in the 14 days prior to illness onset">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25008_2" name="Exposure to Known Case" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 14 days prior to illness onset, did the patient have any of the following exposures:</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS557_2L" title="Did the patient have contact with another confirmed or probable case? This can include household, community, or healthcare contact.">
Did the patient have contact with another COVID-19 case (probable or confirmed)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS557)" styleId="NBS557_2" title="Did the patient have contact with another confirmed or probable case? This can include household, community, or healthcare contact." onchange="ruleEnDisNBS5578844();ruleEnDisNBS5438845();ruleEnDisINV603_68871();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS664_2L" title="Did the patient have household contact with another lab-confirmed COVID-19 case-patient?">
Household contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS664)" styleId="NBS664_2" title="Did the patient have household contact with another lab-confirmed COVID-19 case-patient?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS665_2L" title="Did the patient have community contact with another lab-confirmed COVID-19 case-patient?">
Community contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS665)" styleId="NBS665_2" title="Did the patient have community contact with another lab-confirmed COVID-19 case-patient?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS666_2L" title="Did the patient have healthcare contact with another lab-confirmed COVID-19 case-patient?">
Healthcare contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS666)" styleId="NBS666_2" title="Did the patient have healthcare contact with another lab-confirmed COVID-19 case-patient?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV603_6_2L" title="If other contact with a known COVID-19 case, indicate other type of contact.">
Other Contact Type?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV603_6)" styleId="INV603_6_2" title="If other contact with a known COVID-19 case, indicate other type of contact." onchange="ruleEnDisINV603_68871();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV897_2L" title="Specify other contact type">
Other Contact Type Specify:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(INV897)" styleId ="INV897_2" onkeyup="checkTextAreaLength(this, 100)" title="Specify other contact type"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS543_2L" title="If the patient had contact with another COVID-19 case, was this person a U.S. case?">
If the patient had contact with another COVID-19 case, was this person a U.S. case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS543)" styleId="NBS543_2" title="If the patient had contact with another COVID-19 case, was this person a U.S. case?" onchange="ruleEnDisNBS5438845();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS350_2L" title="If patient had contact with another US COVID-19 case, enter the 2019-nCoV ID of the source case.">
nCoV ID of source case 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS350)" maxlength="25" title="If patient had contact with another US COVID-19 case, enter the 2019-nCoV ID of the source case." styleId="NBS350"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS350_2_2L" title="If epi-linked to a known case, Case ID of the epi-linked case.">
nCoV ID of source case 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS350_2)" maxlength="25" title="If epi-linked to a known case, Case ID of the epi-linked case." styleId="NBS350_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS350_3_2L" title="If epi-linked to a known case, Case ID of the epi-linked case.">
nCoV ID of source case 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS350_3)" maxlength="25" title="If epi-linked to a known case, Case ID of the epi-linked case." styleId="NBS350_3"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25014_2" name="Information Source Details" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS553_2L" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.).">
Information Source for Clinical Information (check all that apply):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS553)" styleId="NBS553_2" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS553_2-selectedValues');enableOrDisableOther('NBS553')" >
<nedss:optionsCollection property="codedValue(INFO_SOURCE_COVID)" value="key" label="value" /> </html:select>
<div id="NBS553_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)." id="NBS553_2OthL">Other Information Source for Clinical Information (check all that apply):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS553Oth)" size="40" maxlength="40" title="Other Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)." styleId="NBS553_2Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21003_2" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV576_2L" title="Were symptoms present during course of illness?">
Symptoms present during course of illness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV576)" styleId="INV576_2" title="Were symptoms present during course of illness?" onchange="ruleEnDisINV5768852();ruleEnDisINV5768848();ruleDCompINV1378839();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV137_2L" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system.">
Date of Symptom Onset:</span>
</td>
<td>
<html:text name="PageForm" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system."  property="pageClientVO2.answer(INV137)" maxlength="10" size="10" styleId="INV137_2" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV138_2L" title="The time at which the disease or condition ends.">
Date of Symptom Resolution:</span>
</td>
<td>
<html:text name="PageForm" title="The time at which the disease or condition ends."  property="pageClientVO2.answer(INV138)" maxlength="10" size="10" styleId="INV138_2" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS555_2L" title="Status of reported symptom(s).">
If symptomatic, symptom status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS555)" styleId="NBS555_2" title="Status of reported symptom(s).">
<nedss:optionsCollection property="codedValue(SYMPTOM_STATUS_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV139_2L" title="The length of time this person had this disease or condition.">
Illness Duration:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV139)" size="3" maxlength="3"  title="The length of time this person had this disease or condition." styleId="INV139_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV140_2L" title="Unit of time used to describe the length of the illness or condition.">
Illness Duration Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV140)" styleId="INV140_2" title="Unit of time used to describe the length of the illness or condition.">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV143_2L" title="Subject's age at the onset of the disease or condition.">
Age at Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV143)" size="3" maxlength="3"  title="Subject's age at the onset of the disease or condition." styleId="INV143_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,150)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV144_2L" title="The age units for an age.">
Age at Onset Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV144)" styleId="INV144_2" title="The age units for an age.">
<nedss:optionsCollection property="codedValue(AGE_UNIT)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21016_2" name="Clinical Findings" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233604007_2L" title="Did the patient have pneumonia?">
Did the patient develop pneumonia?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(233604007)" styleId="233604007_2" title="Did the patient have pneumonia?">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="67782005_2L" title="Did the patient have acute respiratory distress syndrome?">
Did the patient have acute respiratory distress syndrome?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(67782005)" styleId="67782005_2" title="Did the patient have acute respiratory distress syndrome?">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="168734001_2L" title="Did the patient have an abnormal chest X-ray?">
Did the patient have an abnormal chest X-ray?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(168734001)" styleId="168734001_2" title="Did the patient have an abnormal chest X-ray?">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="102594003_2L" title="Did the patient have an abnormal EKG?">
Did the patient have an abnormal EKG?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(102594003)" styleId="102594003_2" title="Did the patient have an abnormal EKG?">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS546_2L" title="Did the patient have another diagnosis/etiology for their illness?">
Did the patient have another diagnosis/etiology for their illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS546)" styleId="NBS546_2" title="Did the patient have another diagnosis/etiology for their illness?" onchange="ruleEnDisNBS5468872();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="81885_6_2L" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology">
Secondary Diagnosis Description 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(81885_6)" maxlength="30" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" styleId="81885_6"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="81885_6_2_2L" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology">
Secondary Diagnosis Description 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(81885_6_2)" maxlength="30" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" styleId="81885_6_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="81885_6_3_2L" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology">
Secondary Diagnosis Description 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(81885_6_3)" maxlength="30" title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" styleId="81885_6_3"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS776_2L" title="Indication of whether the patient had other clinical findings associated with the illness being reported">
Other Clinical Finding:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS776)" styleId="NBS776_2" title="Indication of whether the patient had other clinical findings associated with the illness being reported" onchange="ruleEnDisNBS7768873();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YES_NO_UNKNOWN_NA)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS776_OTH_2L" title="Specify other clinical finding">
Other Clinical Finding Specify:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS776_OTH)" styleId ="NBS776_OTH_2" onkeyup="checkTextAreaLength(this, 100)" title="Specify other clinical finding"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35003_2" name="Clinical Treatments" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS673_2L" title="Did the patient receive mechanical ventilation (MV)/intubation?">
Did the patient receive mechanical ventilation (MV)/intubation?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS673)" styleId="NBS673_2" title="Did the patient receive mechanical ventilation (MV)/intubation?" onchange="ruleEnDisNBS6738857();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV547_2L" title="Total days with mechanical ventilation.">
Total days with Mechanical Ventilation:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV547)" size="3" maxlength="3"  title="Total days with mechanical ventilation." styleId="INV547_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233573008_2L" title="Did the patient receive ECMO?">
Did the patient receive ECMO?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(233573008)" styleId="233573008_2" title="Did the patient receive ECMO?" onchange="ruleEnDis2335730088874();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS780_2L" title="Total days with Extracorporeal Membrane Oxygenation">
Total days with ECMO:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS780)" size="3" maxlength="3"  title="Total days with Extracorporeal Membrane Oxygenation" styleId="NBS780_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS781_2L" title="Indicator for other treatment type specify indicator">
Other Treatment Type?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS781)" styleId="NBS781_2" title="Indicator for other treatment type specify indicator" onchange="ruleEnDisNBS7818875();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS389_2L" title="If the treatment type is Other, specify the treatment.">
Other Treatment Specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS389)" maxlength="100" title="If the treatment type is Other, specify the treatment." styleId="NBS389"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS782_2L" title="Total days with Other Treatment Type">
Total days with Other Treatment Type:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS782)" size="3" maxlength="3"  title="Total days with Other Treatment Type" styleId="NBS782_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21002_2" name="Symptom Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006_2L" title="Did the patient have fever &gt;100.4F (38C)c?">
Fever &gt;100.4F (38C):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(386661006)" styleId="386661006_2" title="Did the patient have fever &gt;100.4F (38C)c?" onchange="ruleEnDis3866610068849();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202_2L" title="What was the patients highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV202)" size="10" maxlength="10"  title="What was the patients highest measured temperature during this illness, in degress Celsius?" styleId="INV202_2" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO2.answer(INV202Unit)" styleId="INV202UNIT_2">
<nedss:optionsCollection property="codedValue(PHVS_TEMP_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="103001002_2L" title="Did the patient have subjective fever (felt feverish)?">
Subjective fever (felt feverish):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(103001002)" styleId="103001002_2" title="Did the patient have subjective fever (felt feverish)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="28376_2_2L" title="Did the patient have chills?">
Chills:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(28376_2)" styleId="28376_2_2" title="Did the patient have chills?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="38880002_2L" title="Did the patient have rigors?">
Rigors:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(38880002)" styleId="38880002_2" title="Did the patient have rigors?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001_2L" title="Did the patient have muscle aches (myalgia)?">
Muscle aches (myalgia):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(68962001)" styleId="68962001_2" title="Did the patient have muscle aches (myalgia)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="82272006_2L" title="Did the patient  have runny nose (rhinorrhea)?">
Runny nose (rhinorrhea):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(82272006)" styleId="82272006_2" title="Did the patient  have runny nose (rhinorrhea)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267102003_2L" title="Did the patient have a sore throat?">
Sore Throat:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(267102003)" styleId="267102003_2" title="Did the patient have a sore throat?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44169009_2L" title="Did the patient experience new olfactory disorder?">
New Olfactory Disorder:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(44169009)" styleId="44169009_2" title="Did the patient experience new olfactory disorder?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="36955009_2L" title="Did the patient experience new taste disorder?">
New Taste Disorder:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(36955009)" styleId="36955009_2" title="Did the patient experience new taste disorder?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="25064002_2L" title="Did the patient have headache?">
Headache:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(25064002)" styleId="25064002_2" title="Did the patient have headache?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="419284004_2L" title="Did the patient have new confusion or change in mental status?">
New confusion or change in mental status?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(419284004)" styleId="419284004_2" title="Did the patient have new confusion or change in mental status?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271795006_2L" title="Did the patient have fatigue or malaise?">
Fatigue or malaise:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(271795006)" styleId="271795006_2" title="Did the patient have fatigue or malaise?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS793_2L" title="Inability to Wake or Stay Awake">
Inability to Wake or Stay Awake:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS793)" styleId="NBS793_2" title="Inability to Wake or Stay Awake">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49727002_2L" title="Did the patient have a Cough (new onset or worsening of chronic cough)?">
Cough (new onset or worsening of chronic cough):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(49727002)" styleId="49727002_2" title="Did the patient have a Cough (new onset or worsening of chronic cough)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="56018004_2L" title="Did the patient experience wheezing?">
Wheezing:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(56018004)" styleId="56018004_2" title="Did the patient experience wheezing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267036007_2L" title="Did the patient have shortness of breath (dyspnea)?">
Shortness of Breath (dyspnea):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(267036007)" styleId="267036007_2" title="Did the patient have shortness of breath (dyspnea)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="230145002_2L" title="Did the patient experience difficulty breathing?">
Difficulty Breathing:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(230145002)" styleId="230145002_2" title="Did the patient experience difficulty breathing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="3415004_2L" title="Did the patient have pale, gray, or blue colored skin, lips, or nail beds, depending on skin tone?">
Pale/gray/blue skin/lips/nail beds?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(3415004)" styleId="3415004_2" title="Did the patient have pale, gray, or blue colored skin, lips, or nail beds, depending on skin tone?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="29857009_2L" title="Did the patient experience chest pain?">
Chest Pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(29857009)" styleId="29857009_2" title="Did the patient experience chest pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS794_2L" title="Persistent Pain or Pressure in Chest">
Persistent Pain or Pressure in Chest:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS794)" styleId="NBS794_2" title="Persistent Pain or Pressure in Chest">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="16932000_2L" title="Did the patient have nausea?">
Nausea:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(16932000)" styleId="16932000_2" title="Did the patient have nausea?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="422400008_2L" title="Did the patient experience vomiting?">
Vomiting:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(422400008)" styleId="422400008_2" title="Did the patient experience vomiting?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="21522001_2L" title="Did the patient have abdominal pain or tenderness?">
Abdominal Pain or Tenderness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(21522001)" styleId="21522001_2" title="Did the patient have abdominal pain or tenderness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="62315008_2L" title="Did the patient have diarrhea (=3 loose/looser than normal stools/24hr period)?">
Diarrhea (=3 loose/looser than normal stools/24hr period):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(62315008)" styleId="62315008_2" title="Did the patient have diarrhea (=3 loose/looser than normal stools/24hr period)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="79890006_2L" title="Did patient have loss of appetite?">
Loss of appetite:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(79890006)" styleId="79890006_2" title="Did patient have loss of appetite?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338_2L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other symptom(s)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS338)" styleId="NBS338_2" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleEnDisNBS3388850();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS338_OTH_2L" title="Specify other signs and symptoms.">
Other Symptoms:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS338_OTH)" styleId ="NBS338_OTH_2" onkeyup="checkTextAreaLength(this, 100)" title="Specify other signs and symptoms."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21004_2" name="Symptom Notes" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS337_2L" title="Notes pertaining to the symptoms indicated for this case.">
Symptom Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS337)" styleId ="NBS337_2" onkeyup="checkTextAreaLength(this, 2000)" title="Notes pertaining to the symptoms indicated for this case."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21009_2" name="Pre-Existing Conditions" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="102478008_2L" title="Does the patient have a history of pre-existing medical conditions?">
Pre-existing medical conditions?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(102478008)" styleId="102478008_2" title="Does the patient have a history of pre-existing medical conditions?" onchange="ruleEnDis1024780088879();ruleEnDis1024780088851();ruleEnDisNBS6628855();ruleEnDisNBS6778868();ruleEnDis1103590098854();ruleEnDis1103590098854();ruleEnDis747320098867();ruleEnDis747320098867();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21008_2" name="Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="73211009_2L" title="Does the patient have diabetes mellitus?">
Diabetes Mellitus:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(73211009)" styleId="73211009_2" title="Does the patient have diabetes mellitus?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB017_2L" title="Before your infection, did a health care provider ever tell you that you had high blood pressure (hypertension)?">
Hypertension:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(ARB017)" styleId="ARB017_2" title="Before your infection, did a health care provider ever tell you that you had high blood pressure (hypertension)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="414916001_2L" title="Is the patient severely obese (BMI &gt;= 40)?">
Severe Obesity (BMI &gt;=40):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(414916001)" styleId="414916001_2" title="Is the patient severely obese (BMI &gt;= 40)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="128487001_2L" title="Does the patient have a history of cardiovascular disease?">
Cardiovascular disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(128487001)" styleId="128487001_2" title="Does the patient have a history of cardiovascular disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="709044004_2L" title="Does the patient have a history of chronic renal disease?">
Chronic Renal disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(709044004)" styleId="709044004_2" title="Does the patient have a history of chronic renal disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="328383001_2L" title="Does the patient have a history of chronic liver disease?">
Chronic Liver disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(328383001)" styleId="328383001_2" title="Does the patient have a history of chronic liver disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413839001_2L" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
Chronic Lung Disease (asthma/emphysema/COPD):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(413839001)" styleId="413839001_2" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS662_2L" title="Did the patient have any history of other chronic disease?">
Other Chronic Diseases:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS662)" styleId="NBS662_2" title="Did the patient have any history of other chronic disease?" onchange="ruleEnDisNBS6628855();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS662_OTH_2L" title="Specify the other chronic disease(s).">
Specify Other Chronic Diseases:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS662_OTH)" maxlength="100" title="Specify the other chronic disease(s)." styleId="NBS662_OTH"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS677_2L" title="Did the patient have any other underlying conditions or risk behaviors?">
Other Underlying Condition or Risk Behavior:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS677)" styleId="NBS677_2" title="Did the patient have any other underlying conditions or risk behaviors?" onchange="ruleEnDisNBS6778868();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS677_OTH_2L" title="If patient had an other underlying condition or risk behavior, specify the condition or behavior.">
Specify Other Underlying Condition or Risk Behavior:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS677_OTH)" maxlength="50" title="If patient had an other underlying condition or risk behavior, specify the condition or behavior." styleId="NBS677_OTH"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="370388006_2L" title="Does the patient have a history of an immunocompromised/immunosuppressive condition?">
Immunosuppressive Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(370388006)" styleId="370388006_2" title="Does the patient have a history of an immunocompromised/immunosuppressive condition?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="85828009_2L" title="Did the patient have an autoimmune disease or condition?">
Autoimmune Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85828009)" styleId="85828009_2" title="Did the patient have an autoimmune disease or condition?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="65568007_2L" title="Is the patient a current smoker?">
Current smoker:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(65568007)" styleId="65568007_2" title="Is the patient a current smoker?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="8517006_2L" title="Is the patient a former smoker?">
Former smoker:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(8517006)" styleId="8517006_2" title="Is the patient a former smoker?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS676_2L" title="Did the patient engage in substance abuse or misuse?">
Substance Abuse or Misuse:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS676)" styleId="NBS676_2" title="Did the patient engage in substance abuse or misuse?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="110359009_2L" title="Does the patient have a history of neurologic/neurodevelopmental/intellectual disability, physical, vision or hearing impairment?">
Disability:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(110359009)" styleId="110359009_2" title="Does the patient have a history of neurologic/neurodevelopmental/intellectual disability, physical, vision or hearing impairment?" onchange="ruleEnDis1103590098854();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS671_2L" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)">
Specify Disability 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS671)" maxlength="100" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" styleId="NBS671"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS671_2_2L" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)">
Specify Disability 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS671_2)" maxlength="100" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" styleId="NBS671_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS671_3_2L" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)">
Specify Disability 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS671_3)" maxlength="100" title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" styleId="NBS671_3"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="74732009_2L" title="Did the patient have a psychological or psychiatric condition?">
Psychological or Psychiatric Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(74732009)" styleId="74732009_2" title="Did the patient have a psychological or psychiatric condition?" onchange="ruleEnDis747320098867();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS691_2L" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition.">
Specify Psychological or Psychiatric Condition 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS691)" maxlength="50" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." styleId="NBS691"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS691_2_2L" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition.">
Specify Psychological or Psychiatric Condition 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS691_2)" maxlength="50" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." styleId="NBS691_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS691_3_2L" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition.">
Specify Psychological or Psychiatric Condition 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS691_3)" maxlength="50" title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." styleId="NBS691_3"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV178_2L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV178)" styleId="INV178_2" title="Assesses whether or not the patient is pregnant." onchange="ruleEnDisINV1788876();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV579_2L" title="If the subject in pregnant, please enter the due date.">
Due Date:</span>
</td>
<td>
<html:text name="PageForm" title="If the subject in pregnant, please enter the due date."  property="pageClientVO2.answer(INV579)" maxlength="10" size="10" styleId="INV579_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAR159_2L" title="If the case-patient was pregnant at time of illness onset, specify the number of weeks gestation at onset of illness (1-45 weeks).">
Number of Weeks Gestation at Onset of Illness:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(VAR159)" size="20" maxlength="20"  title="If the case-patient was pregnant at time of illness onset, specify the number of weeks gestation at onset of illness (1-45 weeks)." styleId="VAR159_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAR160_2L" title="If the case-patient was pregnant at time of illness onset, indicate trimester of gestation at time of disease.">
Trimester at Onset of Illness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAR160)" styleId="VAR160_2" title="If the case-patient was pregnant at time of illness onset, indicate trimester of gestation at time of disease.">
<nedss:optionsCollection property="codedValue(PHVS_PREG_TRIMESTER)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35004_2" name="Vaccination Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126_2L" title="Did subject ever receive a COVID-containing vaccine?">
Did the patient ever received a COVID-containing vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC126)" styleId="VAC126_2" title="Did subject ever receive a COVID-containing vaccine?" onchange="ruleEnDisVAC1268877();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC140_2L" title="Number of vaccine doses against this disease prior to illness onset">
Vaccination Doses Prior to Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(VAC140)" size="2" maxlength="2"  title="Number of vaccine doses against this disease prior to illness onset" styleId="VAC140_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC142_2L" title="Date of last vaccine dose against this disease prior to illness onset">
Date of Last Dose Prior to Illness Onset:</span>
</td>
<td>
<html:text name="PageForm" title="Date of last vaccine dose against this disease prior to illness onset"  property="pageClientVO2.answer(VAC142)" maxlength="10" size="10" styleId="VAC142_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC148_2L" title="Was subject vaccinated as recommended by the Advisory Committee on Immunization Practices (ACIP)?">
Vaccinated per ACIP Recommendations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC148)" styleId="VAC148_2" title="Was subject vaccinated as recommended by the Advisory Committee on Immunization Practices (ACIP)?" onchange="ruleEnDisVAC1488878();enableOrDisableOther('VAC149');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC149_2L" title="Reason subject not vaccinated as recommended by ACIP">
Reason Not Vaccinated Per ACIP Recommendations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC149)" styleId="VAC149_2" title="Reason subject not vaccinated as recommended by ACIP" onchange="enableOrDisableOther('VAC149');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(VACCINE_NOT_GIVEN_REASON_COVID19)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Reason subject not vaccinated as recommended by ACIP" id="VAC149_2OthL">Other Reason Not Vaccinated Per ACIP Recommendations:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(VAC149Oth)" size="40" maxlength="40" title="Other Reason subject not vaccinated as recommended by ACIP" styleId="VAC149_2Oth"/></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC133_2L" title="Comments about the subjects vaccination history">
Vaccine History Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(VAC133)" styleId ="VAC133_2" onkeyup="checkTextAreaLength(this, 200)" title="Comments about the subjects vaccination history"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21012_2" name="Laboratory Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740_2L" title="Was laboratory testing done to confirm the diagnosis?">
Laboratory Testing Performed:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV740)" styleId="INV740_2" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408880();ruleEnDisINV7408853();enableOrDisableOther('INV575');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV575_2L" title="Listing of the reason(s) the subject was tested for the condition">
Reason for Testing (check all that apply):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(INV575)" styleId="INV575_2" title="Listing of the reason(s) the subject was tested for the condition"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV575_2-selectedValues');enableOrDisableOther('INV575')" >
<nedss:optionsCollection property="codedValue(PHVS_REASONFORTEST_COVID19)" value="key" label="value" /> </html:select>
<div id="INV575_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Listing of the reason(s) the subject was tested for the condition" id="INV575_2OthL">Other Reason for Testing (check all that apply):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV575Oth)" size="40" maxlength="40" title="Other Listing of the reason(s) the subject was tested for the condition" styleId="INV575_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS786_2L" title="COVID-19 Variant">
COVID-19 Variant:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS786)" styleId="NBS786_2" title="COVID-19 Variant" onchange="enableOrDisableOther('NBS786');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(COVID_19_VARIANTS)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="COVID-19 Variant" id="NBS786_2OthL">Other COVID-19 Variant:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS786Oth)" size="40" maxlength="40" title="Other COVID-19 Variant" styleId="NBS786_2Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21011_2" name="COVID-19 Testing" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_GA21011_2errorMessages">
<b> <a name="NBS_UI_GA21011_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA21011_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
while(mapIt .hasNext())
{
Map.Entry mappairs = (Map.Entry)mapIt .next();
if(mappairs.getKey().toString().equals(subSecNm)){
batchrec =(String[][]) mappairs.getValue();
break;
}
}%>
<%int wid =100/11; %>
<td style="background-color: #EFEFEF; border:1px solid #666666"  width="9%" colspan="1"> &nbsp;</td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<th width="<%=aInt%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_GA21011_2">
<tr id="patternNBS_UI_GA21011_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_GA21011_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA21011_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][4] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<td width="<%=aInt%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="questionbodyNBS_UI_GA21011_2">
<tr id="nopatternNBS_UI_GA21011_2" class="odd" style="display:">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA21011 InputFieldLabel" id="LAB606_2L" title="Enter the performing laboratory type">
Performing Lab Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB606)" styleId="LAB606_2" title="Enter the performing laboratory type" onchange="unhideBatchImg('NBS_UI_GA21011');enableOrDisableOther('LAB606');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_VPD_COVID19)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Enter the performing laboratory type" id="LAB606_2OthL">Other Performing Lab Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB606Oth)" size="40" maxlength="40" title="Other Enter the performing laboratory type" onkeyup="unhideBatchImg('NBS_UI_GA21011')" styleId="LAB606_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163_2L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date."  property="pageClientVO2.answer(LAB163)" maxlength="10" size="10" styleId="LAB163_2" onkeyup="unhideBatchImg('NBS_UI_GA21011');DateMask(this,null,event)" styleClass="NBS_UI_GA21011"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS674_2L" title="Please enter the performing lab specimen ID number for this lab test.">
Specimen ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS674)"  maxlength="25" title="Please enter the performing lab specimen ID number for this lab test." styleId="NBS674_2" onkeyup="unhideBatchImg('NBS_UI_GA21011');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA21011 InputFieldLabel" id="LAB165_2L" title="Specimen type from which positive lab specimen was collected.">
Specimen Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB165)" styleId="LAB165_2" title="Specimen type from which positive lab specimen was collected." onchange="unhideBatchImg('NBS_UI_GA21011');enableOrDisableOther('LAB165');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SPECIMEN_TYPE_COVID)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Specimen type from which positive lab specimen was collected." id="LAB165_2OthL">Other Specimen Source:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB165Oth)" size="40" maxlength="40" title="Other Specimen type from which positive lab specimen was collected." onkeyup="unhideBatchImg('NBS_UI_GA21011')" styleId="LAB165_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_GA21011_2 InputFieldLabel" id="INV290_2L" title="Lab Test Type">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV290)" styleId="INV290_2" title="Lab Test Type" onchange="unhideBatchImg('NBS_UI_GA21011');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_TYPE_COVID)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Lab Test Type" id="INV290_2OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV290Oth)" size="40" maxlength="40" title="Other Lab Test Type" onkeyup="unhideBatchImg('NBS_UI_GA21011')" styleId="INV290_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA21011 InputFieldLabel" id="INV291_2L" title="Lab test coded result">
Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV291)" styleId="INV291_2" title="Lab test coded result" onchange="unhideBatchImg('NBS_UI_GA21011');enableOrDisableOther('INV291');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTINTERPRETATION_VPD_COVID19)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Lab test coded result" id="INV291_2OthL">Other Test Result:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV291Oth)" size="40" maxlength="40" title="Other Lab test coded result" onkeyup="unhideBatchImg('NBS_UI_GA21011')" styleId="INV291_2Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628_2L" title="Quantitative Test Result Value">
Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB628)"  maxlength="10" title="Quantitative Test Result Value" styleId="LAB628_2" onkeyup="unhideBatchImg('NBS_UI_GA21011');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA21011 InputFieldLabel" id="LAB115_2L" title="Units of measure for the Quantitative Test Result Value">
Quantitative Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB115)" styleId="LAB115_2" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('NBS_UI_GA21011');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="8251_1_2L" title="Comments having to do specifically with the lab result test. These are the comments from the NTE segment if the result was originally an Electronic Laboratory Report.">
Test Result Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(8251_1)" styleId ="8251_1_2" onkeyup="checkTextAreaLength(this, 199)" title="Comments having to do specifically with the lab result test. These are the comments from the NTE segment if the result was originally an Electronic Laboratory Report."/>
</td> </tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="LAB331_2L" title="Was the isolate sent to a state public health laboratory? (Answer Yes if it was sent to any state lab, even if it was sent to a lab outside of the cases state of residence)">
Specimen Sent to State Public Health Lab?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB331)" styleId="LAB331_2" title="Was the isolate sent to a state public health laboratory? (Answer Yes if it was sent to any state lab, even if it was sent to a lab outside of the cases state of residence)" onchange="unhideBatchImg('NBS_UI_GA21011');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="Date Specimen Sent to State Public Health Lab" id="NBS564_2L">
Date Specimen Sent to State Public Health Lab:</span>
</td>
<td>
<html:text name="PageForm" title="Date Specimen Sent to State Public Health Lab" property="pageClientVO2.answer(NBS564)" maxlength="10" size="10" styleId="NBS564_2"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="State Lab Isolate ID Number(s)" id="FDD_Q_1141_2L">
State Lab Specimen ID Number:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_1141)" maxlength="25" title="State Lab Isolate ID Number(s)" styleId="FDD_Q_1141_2" onkeyup="unhideBatchImg('NBS_UI_GA21011');"/>
</td> </tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="LAB515_2L" title="Was a specimen sent to CDC for testing?">
Specimen Sent to CDC?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB515)" styleId="LAB515_2" title="Was a specimen sent to CDC for testing?" onchange="unhideBatchImg('NBS_UI_GA21011');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="Date specimen sent to CDC" id="LAB516_2L">
Date Specimen Sent to CDC:</span>
</td>
<td>
<html:text name="PageForm" title="Date specimen sent to CDC" property="pageClientVO2.answer(LAB516)" maxlength="10" size="10" styleId="LAB516_2"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="CDC specimen ID number from the 50.34 submission form. Example format (10-digit number): 3000123456." id="INV965_2L">
CDC Specimen ID Number:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV965)" maxlength="25" title="CDC specimen ID number from the 50.34 submission form. Example format (10-digit number): 3000123456." styleId="INV965_2" onkeyup="unhideBatchImg('NBS_UI_GA21011');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_GA21011">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_GA21011BatchAddFunction()) writeQuestion('NBS_UI_GA21011_2','patternNBS_UI_GA21011_2','questionbodyNBS_UI_GA21011_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_GA21011_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_GA21011BatchAddFunction()) writeQuestion('NBS_UI_GA21011_2','patternNBS_UI_GA21011_2','questionbodyNBS_UI_GA21011_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_GA21011_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_GA21011BatchAddFunction()) writeQuestion('NBS_UI_GA21011_2','patternNBS_UI_GA21011_2','questionbodyNBS_UI_GA21011_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_GA21011"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_GA21011_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA23001_2" name="Additional State or Local Specimen IDs" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_GA23001_2errorMessages">
<b> <a name="NBS_UI_GA23001_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA23001_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
while(mapIt .hasNext())
{
Map.Entry mappairs = (Map.Entry)mapIt .next();
if(mappairs.getKey().toString().equals(subSecNm)){
batchrec =(String[][]) mappairs.getValue();
break;
}
}%>
<%int wid =100/11; %>
<td style="background-color: #EFEFEF; border:1px solid #666666"  width="9%" colspan="1"> &nbsp;</td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<th width="<%=aInt%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_GA23001_2">
<tr id="patternNBS_UI_GA23001_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_GA23001_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA23001_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][4] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<td width="<%=aInt%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="questionbodyNBS_UI_GA23001_2">
<tr id="nopatternNBS_UI_GA23001_2" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS670_2L" title="Please provide any additional specimen ID of interest.">
Additional Specimen ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS670)"  maxlength="50" title="Please provide any additional specimen ID of interest." styleId="NBS670_2" onkeyup="unhideBatchImg('NBS_UI_GA23001');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_GA23001">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_GA23001BatchAddFunction()) writeQuestion('NBS_UI_GA23001_2','patternNBS_UI_GA23001_2','questionbodyNBS_UI_GA23001_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_GA23001_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_GA23001BatchAddFunction()) writeQuestion('NBS_UI_GA23001_2','patternNBS_UI_GA23001_2','questionbodyNBS_UI_GA23001_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_GA23001_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_GA23001BatchAddFunction()) writeQuestion('NBS_UI_GA23001_2','patternNBS_UI_GA23001_2','questionbodyNBS_UI_GA23001_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_GA23001"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_GA23001_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_28_2" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS055_2L" title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc.">
Contact Investigation Priority:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS055)" styleId="NBS055_2" title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc.">
<nedss:optionsCollection property="codedValue(NBS_PRIORITY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS056_2L" title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period.">
Infectious Period From:</span>
</td>
<td>
<html:text name="PageForm" title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period."  property="pageClientVO2.answer(NBS056)" maxlength="10" size="10" styleId="NBS056_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS057_2L" title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period.">
Infectious Period To:</span>
</td>
<td>
<html:text name="PageForm" title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period."  property="pageClientVO2.answer(NBS057)" maxlength="10" size="10" styleId="NBS057_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29_2" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS058_2L" title="The status of the contact investigation.">
Contact Investigation Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS058)" styleId="NBS058_2" title="The status of the contact investigation.">
<nedss:optionsCollection property="codedValue(PHC_IN_STS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS059_2L" title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation.">
Contact Investigation Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS059)" styleId ="NBS059_2" onkeyup="checkTextAreaLength(this, 2000)" title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA25002_2" name="Retired Questions" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS549_2L" title="Report Date of Person Under Investigation (PUI) to CDC">
Report Date of PUI to CDC:</span>
</td>
<td>
<html:text name="PageForm" title="Report Date of Person Under Investigation (PUI) to CDC"  property="pageClientVO2.answer(NBS549)" maxlength="10" size="10" styleId="NBS549_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS541_2L" title="Does the patient have a history of being in a healthcare facility (as a patient, worker or visitor) in China?">
Patient history of being in a healthcare facility (as a patient, worker or visitor) in China?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS541)" styleId="NBS541_2" title="Does the patient have a history of being in a healthcare facility (as a patient, worker or visitor) in China?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS544_2L" title="Type of healthcare contact with another lab-confirmed case-patient.">
Type of healthcare contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS544)" styleId="NBS544_2" title="Type of healthcare contact with another lab-confirmed case-patient.">
<nedss:optionsCollection property="codedValue(HC_CONTACT_TYPE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS558_2L" title="Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology.">
Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS558)" styleId="NBS558_2" title="Exposure to a cluster of patients with severe acute lower respiratory distress of unknown etiology.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS556_2L" title="Did the patient travel to any high-risk locations?">
Did the patient travel to any high-risk locations:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS556)" styleId="NBS556_2" title="Did the patient travel to any high-risk locations?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS556_2-selectedValues');enableOrDisableOther('NBS556')" >
<nedss:optionsCollection property="codedValue(HIGH_RISK_TRAVEL_LOC_COVID)" value="key" label="value" /> </html:select>
<div id="NBS556_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Did the patient travel to any high-risk locations?" id="NBS556_2OthL">Other Did the patient travel to any high-risk locations:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS556Oth)" size="40" maxlength="40" title="Other Did the patient travel to any high-risk locations?" styleId="NBS556_2Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS554_2L" title="Only complete if case-patient is a known contact of prior source case-patient. Assign Contact ID using CDC 2019-nCoV ID and sequential contact ID, e.g., Confirmed case CA102034567 has contacts CA102034567 -01 and CA102034567 -02.">
Source patient case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS554)" maxlength="50" title="Only complete if case-patient is a known contact of prior source case-patient. Assign Contact ID using CDC 2019-nCoV ID and sequential contact ID, e.g., Confirmed case CA102034567 has contacts CA102034567 -01 and CA102034567 -02." styleId="NBS554"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS663_2L" title="Indicate the date the case was reported to CDC.">
Report Date of Case to CDC:</span>
</td>
<td>
<html:text name="PageForm" title="Indicate the date the case was reported to CDC."  property="pageClientVO2.answer(NBS663)" maxlength="10" size="10" styleId="NBS663_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA36000_2" name="Retired Questions Do Not Use" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Questions in this section have been replaced by other standard questions. These versions of the questions should not be used going forward, but are on the page to maintain any data previously collected.</span></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="67884_7_2L" title="If the patient has any tribal affiliation, enter the Indian Tribe name.">
Tribe Name(s) (Retired):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(67884_7)" maxlength="50" title="If the patient has any tribal affiliation, enter the Indian Tribe name." styleId="67884_7"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="54899_0_2L" title="If patient needs or wants an interpreter to communicate with a doctor or healthcare staff, what is the preferred language?">
If yes, specify which language - Retired:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(54899_0)" maxlength="25" title="If patient needs or wants an interpreter to communicate with a doctor or healthcare staff, what is the preferred language?" styleId="54899_0"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS686_2L" title="Specify the type of workplace setting">
If yes, specify workplace setting (Retired):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS686)" maxlength="50" title="Specify the type of workplace setting" styleId="NBS686"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_32_TXT_2L" title="Specify the type of animal with which the patient had contact.">
Specify Type of Animal (Retired):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_32_TXT)" maxlength="30" title="Specify the type of animal with which the patient had contact." styleId="FDD_Q_32_TXT"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS688_2L" title="Prior to illness onset, was the patient exposed to a school, university, or childcare center?">
School/University/Childcare Center Exposure - Retired:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS688)" styleId="NBS688_2" title="Prior to illness onset, was the patient exposed to a school, university, or childcare center?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="43724002_2L" title="Did the patient have chills or rigors?">
Chills or Rigors - Retired:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(43724002)" styleId="43724002_2" title="Did the patient have chills or rigors?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS675_2L" title="Did the patient experience loss of taste and/or smell or other new olfactory and taste disorder?">
New Olfactory and Taste Disorder - Retired:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS675)" styleId="NBS675_2" title="Did the patient experience loss of taste and/or smell or other new olfactory and taste disorder?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS777_2L" title="Indicate all the countries of travel in the last 14 days (Multi-Select)">
Travel Country (Multi Select):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS777)" styleId="NBS777_2" title="Indicate all the countries of travel in the last 14 days (Multi-Select)"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS777_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
<div id="NBS777_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS778_2L" title="Indicate all the states of travel in the last 14 days (Multi-Select)">
Travel State (Multi-Select):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS778)" styleId="NBS778_2" title="Indicate all the states of travel in the last 14 days (Multi-Select)"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS778_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(STATE_CCD)" value="key" label="value" /> </html:select>
<div id="NBS778_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA22003_2" name="Respiratory Diagnostic Testing Retired" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="80382_5_2L" title="Influenza A Rapid Ag Result">
Influenza A Rapid Ag:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(80382_5)" styleId="80382_5_2" title="Influenza A Rapid Ag Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="80383_3_2L" title="Influenza B Rapid Ag Result">
Influenza B Rapid Ag:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(80383_3)" styleId="80383_3_2" title="Influenza B Rapid Ag Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="34487_9_2L" title="Influenza A PCR Result">
Influenza A PCR:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(34487_9)" styleId="34487_9_2" title="Influenza A PCR Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="40982_1_2L" title="Influenza B PCR Result">
Influenza B PCR:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(40982_1)" styleId="40982_1_2" title="Influenza B PCR Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="6415009_2L" title="Respiratory Syncytial Virus (RSV) Result">
RSV:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(6415009)" styleId="6415009_2" title="Respiratory Syncytial Virus (RSV) Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="416730002_2L" title="H. metapneumovirus Result">
H. metapneumovirus:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(416730002)" styleId="416730002_2" title="H. metapneumovirus Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="41505_9_2L" title="Parainfluenza (1-4) Result">
Parainfluenza (1-4):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(41505_9)" styleId="41505_9_2" title="Parainfluenza (1-4) Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="74871001_2L" title="Adenovirus Result">
Adenovirus:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(74871001)" styleId="74871001_2" title="Adenovirus Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="69239002_2L" title="Rhinovirus/Enterovirus Result">
Rhinovirus/enterovirus:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(69239002)" styleId="69239002_2" title="Rhinovirus/Enterovirus Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="84101006_2L" title="Coronavirus (OC43, 229E, HKU1, NL63) Result">
Coronavirus (OC43, 229E, HKU1, NL63):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(84101006)" styleId="84101006_2" title="Coronavirus (OC43, 229E, HKU1, NL63) Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="58720004_2L" title="M. pneumoniae Result">
M. pneumoniae:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(58720004)" styleId="58720004_2" title="M. pneumoniae Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="103514009_2L" title="C. pneumoniae Result">
C. pneumoniae:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(103514009)" styleId="103514009_2" title="C. pneumoniae Result">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS672_2L" title="Indicates whether additional pathogen testing was completed for this patient.">
Were Other Pathogen(s) Tested?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS672)" styleId="NBS672_2" title="Indicates whether additional pathogen testing was completed for this patient." onchange="ruleEnDisNBS6728856();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA23000_2" name="Other Pathogens Tested Retired" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_GA23000_2errorMessages">
<b> <a name="NBS_UI_GA23000_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_GA23000_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
while(mapIt .hasNext())
{
Map.Entry mappairs = (Map.Entry)mapIt .next();
if(mappairs.getKey().toString().equals(subSecNm)){
batchrec =(String[][]) mappairs.getValue();
break;
}
}%>
<%int wid =100/11; %>
<td style="background-color: #EFEFEF; border:1px solid #666666"  width="9%" colspan="1"> &nbsp;</td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<th width="<%=aInt%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_UI_GA23000_2">
<tr id="patternNBS_UI_GA23000_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_GA23000_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_GA23000_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][4] != null && batchrec[i][2].equals("Y"))  {%>
<% String per = batchrec[i][4];
int aInt = (Integer.parseInt(per)) *91/100;
%>
<td width="<%=aInt%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="questionbodyNBS_UI_GA23000_2">
<tr id="nopatternNBS_UI_GA23000_2" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS669_2L" title="Other Pathogen Tested">
Specify Other Pathogen Tested:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS669)"  maxlength="100" title="Other Pathogen Tested" styleId="NBS669_2" onkeyup="unhideBatchImg('NBS_UI_GA23000');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_GA23000 InputFieldLabel" id="NBS668_2L" title="Other Pathogen Tested Result">
Other Pathogens Tested:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS668)" styleId="NBS668_2" title="Other Pathogen Tested Result" onchange="unhideBatchImg('NBS_UI_GA23000');">
<nedss:optionsCollection property="codedValue(TEST_RESULT_RDT_COVID)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_GA23000">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_GA23000BatchAddFunction()) writeQuestion('NBS_UI_GA23000_2','patternNBS_UI_GA23000_2','questionbodyNBS_UI_GA23000_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_GA23000_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_GA23000BatchAddFunction()) writeQuestion('NBS_UI_GA23000_2','patternNBS_UI_GA23000_2','questionbodyNBS_UI_GA23000_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_GA23000_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_GA23000BatchAddFunction()) writeQuestion('NBS_UI_GA23000_2','patternNBS_UI_GA23000_2','questionbodyNBS_UI_GA23000_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_GA23000"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_GA23000_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
