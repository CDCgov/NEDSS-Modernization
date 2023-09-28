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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Epidemiologic","General Comments","Inclusion Criteria","Comorbidities","Hospitalization","Clinical Signs and Symptoms","Complications","Treatments","Vaccination","Studies Details","SARS-COV-2 Testing","Contact Investigation"};
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
<html:select name="PageForm" property="pageClientVO2.answer(DEM113)" styleId="DEM113_2" title="Patient's current sex." onchange="ruleEnDisDEM1138738();ruleHideUnhINV1788745();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(DEM127)" styleId="DEM127_2" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1278735();pgSelectNextFocus(this);">
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
Legacy Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV200)" maxlength="50" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." styleId="INV200"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS547_2L" title="If the MIS case also had a COVID-19 case, enter the nCoV ID from the COVID-19 case here.">
nCoV ID (If Available):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS547)" maxlength="50" title="If the MIS case also had a COVID-19 case, enter the nCoV ID from the COVID-19 case here." styleId="NBS547"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="67153_7_2L" title="Name of the person who abstracted the medical record.">
Abstractor Name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(67153_7)" maxlength="30" title="Name of the person who abstracted the medical record." styleId="67153_7"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS692_2L" title="The date the information was abstracted from the medical record.">
Date of Abstraction:</span>
</td>
<td>
<html:text name="PageForm" title="The date the information was abstracted from the medical record."  property="pageClientVO2.answer(NBS692)" maxlength="10" size="10" styleId="NBS692_2" onkeyup="DateMask(this,null,event)"/>
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14_2" name="Pregnancy" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV178_2L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV178)" styleId="INV178_2" title="Assesses whether or not the patient is pregnant." onchange="ruleHideUnhINV1788745();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV150)" styleId="INV150_2" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1508736();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV152)" styleId="INV152_2" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1528737();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV502)" styleId="INV502_2" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');ruleEnDisINV5028743();getDWRStatesByCountry(this, 'INV503');pgSelectNextFocus(this);">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV159_2L" title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc.">
Detection Method:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV159)" styleId="INV159_2" title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc.">
<nedss:optionsCollection property="codedValue(PHC_DET_MT)" value="key" label="value" /></html:select>
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
<html:select name="PageForm" property="pageClientVO2.answer(NOT120)" styleId="NOT120_2" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1208744();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_UI_GA28002_2" name="Inclusion Criteria Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS694_2L" title="Was the patient less than 21 years old at illness onset?">
Age &lt; 21:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS694)" styleId="NBS694_2" title="Was the patient less than 21 years old at illness onset?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="426000000_2L" title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more">
Fever For At Least 24 Hrs:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(426000000)" styleId="426000000_2" title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS695_2L" title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin.">
Laboratory Markers of Inflammation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS695)" styleId="NBS695_2" title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="434081000124108_2L" title="Evidence of clinically severe illness requiring hospitalization.">
Evidence of clinically severe illness requiring hospitalization with multisystem organ involvement:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(434081000124108)" styleId="434081000124108_2" title="Evidence of clinically severe illness requiring hospitalization." onchange="ruleEnDis4340810001241088748();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="56265001_2L" title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?">
Cardiac:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(56265001)" styleId="56265001_2" title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="90708001_2L" title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?">
Renal:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(90708001)" styleId="90708001_2" title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="50043002_2L" title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
Respiratory:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(50043002)" styleId="50043002_2" title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="128480004_2L" title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?">
Hematologic:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(128480004)" styleId="128480004_2" title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="119292006_2L" title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea).">
Gastrointestinal:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(119292006)" styleId="119292006_2" title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea).">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95320005_2L" title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
Dermatologic:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(95320005)" styleId="95320005_2" title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="118940003_2L" title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?">
Neurological:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(118940003)" styleId="118940003_2" title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS696_2L" title="Indicates whether there is no alternative plausible diagnosis.">
No alternative plausible diagnosis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS696)" styleId="NBS696_2" title="Indicates whether there is no alternative plausible diagnosis.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS697_2L" title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?">
Positive for current or recent SARS-COV-2 infection by lab testing?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS697)" styleId="NBS697_2" title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?" onchange="ruleEnDisNBS6978749();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS709_2L" title="Was RT-PCR testing completed for this disease?">
RT-PCR:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS709)" styleId="NBS709_2" title="Was RT-PCR testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS710_2L" title="Was serology testing completed for this disease?">
Serology:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS710)" styleId="NBS710_2" title="Was serology testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS711_2L" title="Was antigen testing completed for this disease?">
Antigen:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS711)" styleId="NBS711_2" title="Was antigen testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="840546002_2L" title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?">
COVID-19 exposure within the 4 weeks prior to the onset of symptoms?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(840546002)" styleId="840546002_2" title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?" onchange="ruleEnDis8405460028746();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP248166_3_2L" title="Date of first exposure to individual with confirmed illness within the 4 weeks prior.">
Date of first exposure within the 4 weeks prior:</span>
</td>
<td>
<html:text name="PageForm" title="Date of first exposure to individual with confirmed illness within the 4 weeks prior."  property="pageClientVO2.answer(LP248166_3)" maxlength="10" size="10" styleId="LP248166_3_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS698_2L" title="Exposure date unknown.">
Exposure Date Unknown:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS698)" styleId="NBS698_2" title="Exposure date unknown.">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28004_2" name="Weight and BMI" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS434_2L" title="Enter the height of the patient in inches.">
Height (in inches):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS434)" size="10" maxlength="10"  title="Enter the height of the patient in inches." styleId="NBS434_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM255_2L" title="Enter the patients weight at diagnosis in pounds (lbs).">
Weight (in lbs):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(DEM255)" size="5" maxlength="5"  title="Enter the patients weight at diagnosis in pounds (lbs)." styleId="DEM255_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="39156_5_2L" title="What is the patient's body mass index?">
BMI:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(39156_5)" size="10" maxlength="10"  title="What is the patient's body mass index?" styleId="39156_5_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28003_2" name="Comorbidities Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="370388006_2L" title="Does the patient have a history of an immunocompromised condition?">
Immunosuppressive disorder or malignancy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(370388006)" styleId="370388006_2" title="Does the patient have a history of an immunocompromised condition?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="414916001_2L" title="Is the patient obese?">
Obesity:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(414916001)" styleId="414916001_2" title="Is the patient obese?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="46635009_2L" title="Does the patient have type 1 diabetes?">
Type 1 diabetes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(46635009)" styleId="46635009_2" title="Does the patient have type 1 diabetes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44054006_2L" title="Does the patient have type 2 diabetes?">
Type 2 diabetes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(44054006)" styleId="44054006_2" title="Does the patient have type 2 diabetes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="91175000_2L" title="Did the patient have seizures?">
Seizures:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(91175000)" styleId="91175000_2" title="Did the patient have seizures?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="13213009_2L" title="Congenital heart disease">
Congenital heart disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(13213009)" styleId="13213009_2" title="Congenital heart disease">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="417357006_2L" title="Does the patient have sickle cell disease?">
Sickle cell disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(417357006)" styleId="417357006_2" title="Does the patient have sickle cell disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413839001_2L" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
Chronic Lung Disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(413839001)" styleId="413839001_2" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="276654001_2L" title="Does the patient have any other congenital malformations?">
Other congenital malformations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(276654001)" styleId="276654001_2" title="Does the patient have any other congenital malformations?" onchange="ruleEnDis2766540018747();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="276654001_OTH_2L" title="Specify other congenital malformations.">
Specify other congenital malformations:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(276654001_OTH)" maxlength="100" title="Specify other congenital malformations." styleId="276654001_OTH"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3_2" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128_2L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV128)" styleId="INV128_2" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288733();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328739();ruleEnDis3099040018750();pgSelectNextFocus(this);">
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
Hospital Admission Date:</span>
</td>
<td>
<html:text name="PageForm" title="Subject's admission date to the hospital for the condition covered by the investigation."  property="pageClientVO2.answer(INV132)" maxlength="10" size="10" styleId="INV132_2" onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV133_2L" title="Subject's discharge date from the hospital for the condition covered by the investigation.">
Hospital Discharge:</span>
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
<span class=" InputFieldLabel" id="309904001_2L" title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?">
Was patient admitted to ICU?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(309904001)" styleId="309904001_2" title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?" onchange="ruleEnDis3099040018750();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS679_2L" title="Enter the date of admission.">
ICU Admission Date:</span>
</td>
<td>
<html:text name="PageForm" title="Enter the date of admission."  property="pageClientVO2.answer(NBS679)" maxlength="10" size="10" styleId="NBS679_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="74200_7_2L" title="Indicate the number of days the patient was in intensive care.">
Number of days in the ICU:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(74200_7)" size="3" maxlength="3"  title="Indicate the number of days the patient was in intensive care." styleId="74200_7_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,364)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_1038_2L" title="Patients Outcome">
Patients Outcome:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(FDD_Q_1038)" styleId="FDD_Q_1038_2" title="Patients Outcome">
<nedss:optionsCollection property="codedValue(PATIENT_HOSP_STATUS_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV145_2L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV145)" styleId="INV145_2" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1458734();pgSelectNextFocus(this);">
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
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29007_2" name="Previous COVID Like Illness" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS707_2L" title="Did the patient have preceding COVID-like illness?">
Did patient have preceding COVID-like illness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS707)" styleId="NBS707_2" title="Did the patient have preceding COVID-like illness?" onchange="ruleEnDisNBS7078759();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS708_2L" title="Date of COVID-like illness symptom onset.">
Date of COVID-like illness symptom onset:</span>
</td>
<td>
<html:text name="PageForm" title="Date of COVID-like illness symptom onset."  property="pageClientVO2.answer(NBS708)" maxlength="10" size="10" styleId="NBS708_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27016_2" name="Symptoms Onset and Fever" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Onset information below relates to onset of Multisystem Inflammatory Syndrome Associated with COVID-19.</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV136_2L" title="Date of diagnosis of MIS-C.">
Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date of diagnosis of MIS-C."  property="pageClientVO2.answer(INV136)" maxlength="10" size="10" styleId="INV136_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV137_2L" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system.">
Illness Onset Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system."  property="pageClientVO2.answer(INV137)" maxlength="10" size="10" styleId="INV137_2" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV138_2L" title="The time at which the disease or condition ends.">
Illness End Date:</span>
</td>
<td>
<html:text name="PageForm" title="The time at which the disease or condition ends."  property="pageClientVO2.answer(INV138)" maxlength="10" size="10" styleId="INV138_2" onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')"/>
</td> </tr>

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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006_2L" title="Did the patient have fever?">
Fever Greater Than 38.0 C or 100.4 F:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(386661006)" styleId="386661006_2" title="Did the patient have fever?" onchange="ruleEnDis3866610068751();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV203_2L" title="Indicates the date of fever onset">
Date of Fever Onset:</span>
</td>
<td>
<html:text name="PageForm" title="Indicates the date of fever onset"  property="pageClientVO2.answer(INV203)" maxlength="10" size="10" styleId="INV203_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202_2L" title="What was the subjects highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature (in degrees C):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV202)" size="10" maxlength="10"  title="What was the subjects highest measured temperature during this illness, in degress Celsius?" styleId="INV202_2" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAR125_2L" title="Total number of days fever lasted">
Number of Days Febrile:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(VAR125)" size="20" maxlength="20"  title="Total number of days fever lasted" styleId="VAR125_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27017_2" name="Cardiac" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Shock is captured in the Complications section of the page.</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="444931001_2L" title="Did the patient have elevated troponin?">
Elevated troponin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(444931001)" styleId="444931001_2" title="Did the patient have elevated troponin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="414798009_2L" title="Does the patient have elevated BNP or NT-proBNP?">
Elevated BNP or NT-proBNP:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(414798009)" styleId="414798009_2" title="Does the patient have elevated BNP or NT-proBNP?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27018_2" name="Renal" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="14350001000004108_2L" title="Did the patient have acute kidney injury?">
Acute kidney injury:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(14350001000004108)" styleId="14350001000004108_2" title="Did the patient have acute kidney injury?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="42399005_2L" title="Did the patient have renal failure?">
Renal failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(42399005)" styleId="42399005_2" title="Did the patient have renal failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27019_2" name="Respiratory" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49727002_2L" title="Did the patients illness include the symptom of cough?">
Cough:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(49727002)" styleId="49727002_2" title="Did the patients illness include the symptom of cough?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267036007_2L" title="Did the patient have shortness of breath (dyspnea)?">
Shortness of breath (dyspnea):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(267036007)" styleId="267036007_2" title="Did the patient have shortness of breath (dyspnea)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="29857009_2L" title="Did the patient experience chest pain?">
Chest pain or tightness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(29857009)" styleId="29857009_2" title="Did the patient experience chest pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Pneumonia and ARDS are captured in Complications section of the page.</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="59282003_2L" title="Did the patient have pulmonary embolism?">
Pulmonary embolism:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(59282003)" styleId="59282003_2" title="Did the patient have pulmonary embolism?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27020_2" name="Hematologic" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="449830004_2L" title="Did the patient have elevated D-dimers?">
Elevated D-dimers:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(449830004)" styleId="449830004_2" title="Did the patient have elevated D-dimers?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="234467004_2L" title="Did the patient have thrombophilia?">
Thrombophilia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(234467004)" styleId="234467004_2" title="Did the patient have thrombophilia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="302215000_2L" title="Did the subject have thrombocytopenia?">
Thrombocytopenia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(302215000)" styleId="302215000_2" title="Did the subject have thrombocytopenia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27021_2" name="Gastrointestinal" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="21522001_2L" title="Did the patient have abdominal pain or tenderness?">
Abdominal pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(21522001)" styleId="21522001_2" title="Did the patient have abdominal pain or tenderness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="422400008_2L" title="Did the subject experience vomiting?">
Vomiting:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(422400008)" styleId="422400008_2" title="Did the subject experience vomiting?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="62315008_2L" title="Did the patient have diarrhea?">
Diarrhea:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(62315008)" styleId="62315008_2" title="Did the patient have diarrhea?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="26165005_2L" title="Did the patient have elevated bilirubin?">
Elevated bilirubin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(26165005)" styleId="26165005_2" title="Did the patient have elevated bilirubin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="707724006_2L" title="Did the patient have elevated liver enzymes?">
Elevated liver enzymes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(707724006)" styleId="707724006_2" title="Did the patient have elevated liver enzymes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27022_2" name="Dermatologic" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271807003_2L" title="Did the patient have rash?">
Rash:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(271807003)" styleId="271807003_2" title="Did the patient have rash?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95346009_2L" title="Did the patient have mucocutaneous lesions?">
Mucocutaneous lesions:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(95346009)" styleId="95346009_2" title="Did the patient have mucocutaneous lesions?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27023_2" name="Neurological" isHidden="F" classType="subSect" >

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
<span class=" InputFieldLabel" id="419284004_2L" title="Did the patient have altered mental status?">
Altered Mental Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(419284004)" styleId="419284004_2" title="Did the patient have altered mental status?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS712_2L" title="Did the patient have syncope/near syncope?">
Syncope/near syncope:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS712)" styleId="NBS712_2" title="Did the patient have syncope/near syncope?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44201003_2L" title="Did the patient have meningitis?">
Meningitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(44201003)" styleId="44201003_2" title="Did the patient have meningitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="81308009_2L" title="Did the patient have encephalopathy?">
Encephalopathy?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(81308009)" styleId="81308009_2" title="Did the patient have encephalopathy?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27024_2" name="Other Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="81680005_2L" title="Did the patient have neck pain?">
Neck pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(81680005)" styleId="81680005_2" title="Did the patient have neck pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001_2L" title="Did the patient have myalgia?">
Myalgia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(68962001)" styleId="68962001_2" title="Did the patient have myalgia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="193894004_2L" title="Did the patient have conjunctival injection?">
Conjunctival injection:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(193894004)" styleId="193894004_2" title="Did the patient have conjunctival injection?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49563000_2L" title="Did the patient have periorbital edema?">
Periorbital edema:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(49563000)" styleId="49563000_2" title="Did the patient have periorbital edema?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="127086001_2L" title="Did the patient have cervical lymphadenopathy?">
Cervical lymphadenopathy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(127086001)" styleId="127086001_2" title="Did the patient have cervical lymphadenopathy?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29002_2" name="Complications Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="698247007_2L" title="Did the patient have arrhythmia?">
Arrhythmia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(698247007)" styleId="698247007_2" title="Did the patient have arrhythmia?" onchange="ruleEnDis6982470078752();enableOrDisableOther('76281_5');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="76281_5_2L" title="Indicate the type of arrhythmia.">
Type of arrhythmia:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(76281_5)" styleId="76281_5_2" title="Indicate the type of arrhythmia."
multiple="true" size="4"
onchange="displaySelectedOptions(this, '76281_5_2-selectedValues');enableOrDisableOther('76281_5')" >
<nedss:optionsCollection property="codedValue(CARDIAC_ARRHYTHMIA_TYPE)" value="key" label="value" /> </html:select>
<div id="76281_5_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the type of arrhythmia." id="76281_5_2OthL">Other Type of arrhythmia:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(76281_5Oth)" size="40" maxlength="40" title="Other Indicate the type of arrhythmia." styleId="76281_5_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB020_2L" title="Did the patient have congestive heart failure?">
Congestive Heart Failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(ARB020)" styleId="ARB020_2" title="Did the patient have congestive heart failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="50920009_2L" title="Did the patient have myocarditis?">
Myocarditis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(50920009)" styleId="50920009_2" title="Did the patient have myocarditis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="3238004_2L" title="Did the patient have pericarditis?">
Pericarditis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(3238004)" styleId="3238004_2" title="Did the patient have pericarditis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="59927004_2L" title="Did the patient have liver failure?">
Liver failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(59927004)" styleId="59927004_2" title="Did the patient have liver failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS713_2L" title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?">
Deep vein thrombosis (DVT) or pulmonary embolism (PE):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS713)" styleId="NBS713_2" title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="67782005_2L" title="Did the patient have acute respiratory distress syndrome?">
ARDS:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(67782005)" styleId="67782005_2" title="Did the patient have acute respiratory distress syndrome?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233604007_2L" title="Did the patient have pneumonia?">
Pneumonia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(233604007)" styleId="233604007_2" title="Did the patient have pneumonia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB021_2L" title="Did patient develop CVA or stroke?">
CVA or Stroke:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(ARB021)" styleId="ARB021_2" title="Did patient develop CVA or stroke?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="31646008_2L" title="Did the patient have encephalitis?">
Encephalitis or aseptic meningitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(31646008)" styleId="31646008_2" title="Did the patient have encephalitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="27942005_2L" title="Did the subject have septic shock?">
Shock:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(27942005)" styleId="27942005_2" title="Did the subject have septic shock?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="45007003_2L" title="Did the patient have hypotension?">
Hypotension:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(45007003)" styleId="45007003_2" title="Did the patient have hypotension?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29004_2" name="Treatments Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="466713001_2L" title="Did the patient receive low flow nasal cannula?">
Low flow nasal cannula:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(466713001)" styleId="466713001_2" title="Did the patient receive low flow nasal cannula?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="426854004_2L" title="Did the patient receive high flow nasal cannula?">
High flow nasal cannula:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(426854004)" styleId="426854004_2" title="Did the patient receive high flow nasal cannula?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="428311008_2L" title="Did the patient receive non-invasive ventilation?">
Non-invasive ventilation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(428311008)" styleId="428311008_2" title="Did the patient receive non-invasive ventilation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="52765003_2L" title="Did the patient receive intubation?">
Intubation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(52765003)" styleId="52765003_2" title="Did the patient receive intubation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS673_2L" title="Did the patient receive mechanical ventilation?">
Mechanical ventilation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS673)" styleId="NBS673_2" title="Did the patient receive mechanical ventilation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233573008_2L" title="Did the patient receive ECMO?">
ECMO:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(233573008)" styleId="233573008_2" title="Did the patient receive ECMO?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS699_2L" title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?">
Vasoactive medications:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS699)" styleId="NBS699_2" title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?" onchange="ruleEnDisNBS6998755();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS700_2L" title="Specify the vasoactive medications the patient received.">
Specify vasoactive medications:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS700)" maxlength="100" title="Specify the vasoactive medications the patient received." styleId="NBS700"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB040_2L" title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?">
Steroids:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(ARB040)" styleId="ARB040_2" title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB045_2L" title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?">
Immune modulators:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(ARB045)" styleId="ARB045_2" title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?" onchange="ruleEnDisARB0458756();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="373244000_2L" title="Specify immune modulators the patient received.">
Specify immune modulators:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(373244000)" maxlength="100" title="Specify immune modulators the patient received." styleId="373244000"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="372560006_2L" title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?">
Antiplatelets:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(372560006)" styleId="372560006_2" title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?" onchange="ruleEnDis3725600068753();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="372560006_OTH_2L" title="Specify antiplatelets the patient received.">
Specify antiplatelets:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(372560006_OTH)" maxlength="100" title="Specify antiplatelets the patient received." styleId="372560006_OTH"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="372862008_2L" title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?">
Anticoagulation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(372862008)" styleId="372862008_2" title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?" onchange="ruleEnDis3728620088754();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="372862008_OTH_2L" title="Specify anticoagulation the patient received?">
Specify anticoagulation:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(372862008_OTH)" maxlength="100" title="Specify anticoagulation the patient received?" styleId="372862008_OTH"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="302497006_2L" title="Did the patient receive hemodialysis?">
Dialysis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(302497006)" styleId="302497006_2" title="Did the patient receive hemodialysis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS701_2L" title="Did the patient receive first intravenous immunoglobulin?">
First IVIG:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS701)" styleId="NBS701_2" title="Did the patient receive first intravenous immunoglobulin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS702_2L" title="Did the patient receive second intravenous immunoglobulin?">
Second IVIG:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS702)" styleId="NBS702_2" title="Did the patient receive second intravenous immunoglobulin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA40001_2" name="Vaccine Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126_2L" title="Has the patient received a COVID-19 vaccine?">
Has the patient received a COVID-19 vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC126)" styleId="VAC126_2" title="Has the patient received a COVID-19 vaccine?" onchange="ruleEnDisVAC1268769();enableOrDisableOther('VAC107');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC132_CD_2L" title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
If yes, how many doses?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC132_CD)" styleId="VAC132_CD_2" title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
<nedss:optionsCollection property="codedValue(VAC_DOSE_NUM_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS791_2L" title="Date the first vaccine dose was received.">
Vaccine Dose 1 Received Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date the first vaccine dose was received."  property="pageClientVO2.answer(NBS791)" maxlength="10" size="10" styleId="NBS791_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS792_2L" title="Date the second vaccine dose was received.">
Vaccine Dose 2 Received Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date the second vaccine dose was received."  property="pageClientVO2.answer(NBS792)" maxlength="10" size="10" styleId="NBS792_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC107_2L" title="COVID-19 vaccine manufacturer">
COVID-19 vaccine manufacturer:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC107)" styleId="VAC107_2" title="COVID-19 vaccine manufacturer" onchange="enableOrDisableOther('VAC107');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(MIS_VAC_MFGR)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="COVID-19 vaccine manufacturer" id="VAC107_2OthL">Other COVID-19 vaccine manufacturer:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(VAC107Oth)" size="40" maxlength="40" title="Other COVID-19 vaccine manufacturer" styleId="VAC107_2Oth"/></td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27008_2" name="Blood Test Results" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS714_2L" title="If the patient had a fibrinogen result, enter the highest value.">
Fibrinogen Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS714)" size="10" maxlength="10"  title="If the patient had a fibrinogen result, enter the highest value." styleId="NBS714_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS715_2L" title="If the patient had a fibrinogen test, enter the unit of measure associated with the value.">
Fibrinogen Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS715)" styleId="NBS715_2" title="If the patient had a fibrinogen test, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(FIBRINOGEN_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS716_2L" title="If the patient had a fibrinogen result, indicate the interpretation.">
Fibrinogen Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS716)" styleId="NBS716_2" title="If the patient had a fibrinogen result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS717_2L" title="If the patient had a CRP result, enter the highest value.">
CRP Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS717)" size="10" maxlength="10"  title="If the patient had a CRP result, enter the highest value." styleId="NBS717_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS728_2L" title="If the patient had a CRP result enter the unit of measure associated with the value.">
CRP Unit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS728)" styleId="NBS728_2" title="If the patient had a CRP result enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(CRP_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS729_2L" title="If the patient had a CRP result, indicate the interpretation.">
CRP Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS729)" styleId="NBS729_2" title="If the patient had a CRP result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS730_2L" title="If the patient had a ferritin result, enter the highest value.">
Ferritin Test Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS730)" size="10" maxlength="10"  title="If the patient had a ferritin result, enter the highest value." styleId="NBS730_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS731_2L" title="If the patient had a ferritin result enter the unit of measure associated with the value.">
Ferritin Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS731)" styleId="NBS731_2" title="If the patient had a ferritin result enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(FERRITIN_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS732_2L" title="If the patient had a ferritin result, indicate the interpretation.">
Ferritin Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS732)" styleId="NBS732_2" title="If the patient had a ferritin result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS733_2L" title="If the patient had a troponin result, enter the highest value.">
Troponin Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS733)" size="10" maxlength="10"  title="If the patient had a troponin result, enter the highest value." styleId="NBS733_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS734_2L" title="If the patient had a troponin result, enter the unit of measure associated with the value.">
Troponin Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS734)" styleId="NBS734_2" title="If the patient had a troponin result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(TROPONIN_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS735_2L" title="If the patient had a troponin result, indicate the interpretation.">
Troponin Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS735)" styleId="NBS735_2" title="If the patient had a troponin result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS736_2L" title="If the patient had a BNP result, enter the highest value.">
BNP Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS736)" size="10" maxlength="10"  title="If the patient had a BNP result, enter the highest value." styleId="NBS736_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS737_2L" title="If the patient had a BNP result, enter the unit of measure associated with the value.">
BNP Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS737)" styleId="NBS737_2" title="If the patient had a BNP result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BNP_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS738_2L" title="If the patient had a BNP result, indicate the interpretation.">
BNP Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS738)" styleId="NBS738_2" title="If the patient had a BNP result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS739_2L" title="If the patient had a NT-proBNP result, enter the highest value.">
NT-proBNP Test Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS739)" size="10" maxlength="10"  title="If the patient had a NT-proBNP result, enter the highest value." styleId="NBS739_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS740_2L" title="If the patient had a NT-proBNP result, enter the unit of measure associated with the value.">
NT-proBNP Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS740)" styleId="NBS740_2" title="If the patient had a NT-proBNP result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BNP_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS741_2L" title="If the patient had a NT-proBNP result, indicate the interpretation.">
NT-proBNP Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS741)" styleId="NBS741_2" title="If the patient had a NT-proBNP result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS742_2L" title="If the patient had a D-dimer result, enter the highest value.">
D-dimer Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS742)" size="10" maxlength="10"  title="If the patient had a D-dimer result, enter the highest value." styleId="NBS742_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS743_2L" title="If the patient had a D-dimer result, enter the unit of measure associated with the value.">
D-dimer Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS743)" styleId="NBS743_2" title="If the patient had a D-dimer result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(D_DIMER_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS744_2L" title="If the patient had a D-dimer result, indicate the interpretation.">
D-dimer Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS744)" styleId="NBS744_2" title="If the patient had a D-dimer result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS745_2L" title="If the patient had a IL-6 result, enter the highest value.">
IL-6 Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS745)" size="10" maxlength="10"  title="If the patient had a IL-6 result, enter the highest value." styleId="NBS745_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS746_2L" title="If the patient had a IL-6 result, enter the unit of measure associated with the value.">
IL-6 Unit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS746)" styleId="NBS746_2" title="If the patient had a IL-6 result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(IL_6_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS747_2L" title="If the patient had a IL-6 result, indicate the interpretation.">
IL-6 Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS747)" styleId="NBS747_2" title="If the patient had a IL-6 result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS748_2L" title="If the patient had a serum white blood count result, enter the test interpretation.">
Serum White Blood Count Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS748)" size="10" maxlength="10"  title="If the patient had a serum white blood count result, enter the test interpretation." styleId="NBS748_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS749_2L" title="If the patient had a serum white blood count result, enter the lowest value.">
Serum White Blood Count Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS749)" size="10" maxlength="10"  title="If the patient had a serum white blood count result, enter the lowest value." styleId="NBS749_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS750_2L" title="If the patient had a serum white blood count result, enter the unit of measure associated with the value.">
Serum White Blood Count Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS750)" styleId="NBS750_2" title="If the patient had a serum white blood count result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BLOOD_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS751_2L" title="If the patient had a platelets result, enter the highest value.">
Platelets Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS751)" size="10" maxlength="10"  title="If the patient had a platelets result, enter the highest value." styleId="NBS751_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS752_2L" title="If the patient had a platelets result, enter the lowest value.">
Platelets Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS752)" size="10" maxlength="10"  title="If the patient had a platelets result, enter the lowest value." styleId="NBS752_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS753_2L" title="If the patient had a platelets result, enter the unit of measure associated with the value.">
Platelets Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS753)" styleId="NBS753_2" title="If the patient had a platelets result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BLOOD_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS754_2L" title="If the patient had a neutrophils result, enter the highest value.">
Neutrophils Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS754)" size="10" maxlength="10"  title="If the patient had a neutrophils result, enter the highest value." styleId="NBS754_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS755_2L" title="If the patient had a neutrophils result, enter the lowest value.">
Neutrophils Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS755)" size="10" maxlength="10"  title="If the patient had a neutrophils result, enter the lowest value." styleId="NBS755_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS756_2L" title="If the patient had a neutrophils result, enter the unit of measure associated with the value.">
Neutrophils Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS756)" styleId="NBS756_2" title="If the patient had a neutrophils result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BLOOD_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS757_2L" title="If the patient had a lymphocytes result, enter the highest value.">
Lymphocytes Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS757)" size="10" maxlength="10"  title="If the patient had a lymphocytes result, enter the highest value." styleId="NBS757_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS758_2L" title="If the patient had a lymphocytes result, enter the lowest value.">
Lymphocytes Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS758)" size="10" maxlength="10"  title="If the patient had a lymphocytes result, enter the lowest value." styleId="NBS758_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS759_2L" title="If the patient had a lymphocytes result, enter the unit of measure associated with the value.">
Lymphocytes Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS759)" styleId="NBS759_2" title="If the patient had a lymphocytes result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BLOOD_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS760_2L" title="If the patient had a bands test result, enter the highest value.">
Bands Test Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS760)" size="10" maxlength="10"  title="If the patient had a bands test result, enter the highest value." styleId="NBS760_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS761_2L" title="If the patient had a band test result, enter the lowest value.">
Bands Test Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS761)" size="10" maxlength="10"  title="If the patient had a band test result, enter the lowest value." styleId="NBS761_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS762_2L" title="If the patient had a bands test result, enter the unit of measure associated with the value.">
Bands Test Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS762)" styleId="NBS762_2" title="If the patient had a bands test result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BANDS_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27009_2" name="CSF Studies" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS763_2L" title="If the patient had a white blood count result, enter the highest value.">
White Blood Count Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS763)" size="10" maxlength="10"  title="If the patient had a white blood count result, enter the highest value." styleId="NBS763_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS764_2L" title="If the patient had a white blood count result, enter the lowest value.">
White Blood Count Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS764)" size="10" maxlength="10"  title="If the patient had a white blood count result, enter the lowest value." styleId="NBS764_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS765_2L" title="If the patient had a white blood count result, enter the unit of measure associated with the value.">
White Blood Count Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS765)" styleId="NBS765_2" title="If the patient had a white blood count result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(WBC_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS766_2L" title="If the patient had a protein test result, enter the highest value.">
Protein Test Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS766)" size="10" maxlength="10"  title="If the patient had a protein test result, enter the highest value." styleId="NBS766_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS767_2L" title="If the patient had a protein test result, enter the lowest value.">
Protein Test Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS767)" size="10" maxlength="10"  title="If the patient had a protein test result, enter the lowest value." styleId="NBS767_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS768_2L" title="If the patient had a protein test result, enter the unit of measure associated with the value.">
Protein Test Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS768)" styleId="NBS768_2" title="If the patient had a protein test result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(PROTEIN_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS769_2L" title="If the patient had a glucose result, enter the highest value.">
Glucose Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS769)" size="10" maxlength="10"  title="If the patient had a glucose result, enter the highest value." styleId="NBS769_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS770_2L" title="If the patient had a glucose test result, enter the lowest value.">
Glucose Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS770)" size="10" maxlength="10"  title="If the patient had a glucose test result, enter the lowest value." styleId="NBS770_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS771_2L" title="If the patient had a glucose result, enter the unit of measure associated with the value.">
Glucose Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS771)" styleId="NBS771_2" title="If the patient had a glucose result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(GLUCOSE_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27010_2" name="Urinalysis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS772_2L" title="If the patient had a urine while blood count result, enter the highest value.">
Urine White Blood Count Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS772)" size="10" maxlength="10"  title="If the patient had a urine while blood count result, enter the highest value." styleId="NBS772_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS773_2L" title="If the patient had a urine white blood count result, enter the lowest value.">
Urine White Blood Count Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS773)" size="10" maxlength="10"  title="If the patient had a urine white blood count result, enter the lowest value." styleId="NBS773_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS774_2L" title="If the patient had a urine white blood count result, enter the unit of measure associated with the value.">
Urine White Blood Count Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS774)" styleId="NBS774_2" title="If the patient had a urine white blood count result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(URINE_WBC_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27011_2" name="Echocardiogram" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="40701008_2L" title="Indicate echocardiogram result.">
Echocardiogram result:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(40701008)" styleId="40701008_2" title="Indicate echocardiogram result."
multiple="true" size="4"
onchange="displaySelectedOptions(this, '40701008_2-selectedValues');ruleEnDis407010088763();ruleEnDis407010088762();ruleEnDis407010088761();ruleEnDis407010088760();enableOrDisableOther('40701008')" >
<nedss:optionsCollection property="codedValue(ECHOCARDIOGRAM_RESULT_MIS)" value="key" label="value" /> </html:select>
<div id="40701008_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate echocardiogram result." id="40701008_2OthL">Other Echocardiogram result:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(40701008Oth)" size="40" maxlength="40" title="Other Indicate echocardiogram result." styleId="40701008_2Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="373065002_2L" title="Indicate max coronary artery Z-score.">
Max coronary artery Z-score:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(373065002)" maxlength="10" title="Indicate max coronary artery Z-score." styleId="373065002"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS703_2L" title="Indicate cardiac dysfunction type.">
Cardiac dysfunction type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS703)" styleId="NBS703_2" title="Indicate cardiac dysfunction type.">
<nedss:optionsCollection property="codedValue(CARDIAC_DYSFUNCTION_TYPE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS704_2L" title="Indicate the date of first test showing coronary artery aneurysm or dilatation.">
Date of first test showing coronary artery aneurysm or dilatation:</span>
</td>
<td>
<html:text name="PageForm" title="Indicate the date of first test showing coronary artery aneurysm or dilatation."  property="pageClientVO2.answer(NBS704)" maxlength="10" size="10" styleId="NBS704_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="18113_1_2L" title="Indicate mitral regurgitation type.">
Mitral regurgitation type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(18113_1)" styleId="18113_1_2" title="Indicate mitral regurgitation type.">
<nedss:optionsCollection property="codedValue(MITRAL_REGURG_TYPE)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27012_2" name="Abdominal Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="441987005_2L" title="Was abdominal imaging was completed for this patient?">
Was abdominal imaging done?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(441987005)" styleId="441987005_2" title="Was abdominal imaging was completed for this patient?" onchange="ruleEnDis4419870058757();enableOrDisableOther('NBS706');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS705_2L" title="Indicate abdominal imaging type.">
Abdominal imaging type:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS705)" styleId="NBS705_2" title="Indicate abdominal imaging type."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS705_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(ABD_IMAGING_TYPE_MIS)" value="key" label="value" /> </html:select>
<div id="NBS705_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS706_2L" title="Indicate abdominal imaging results.">
Abdominal imaging results:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS706)" styleId="NBS706_2" title="Indicate abdominal imaging results."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS706_2-selectedValues');enableOrDisableOther('NBS706')" >
<nedss:optionsCollection property="codedValue(ABD_IMAGING_RSLT_MIS)" value="key" label="value" /> </html:select>
<div id="NBS706_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate abdominal imaging results." id="NBS706_2OthL">Other Abdominal imaging results:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS706Oth)" size="40" maxlength="40" title="Other Indicate abdominal imaging results." styleId="NBS706_2Oth"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27013_2" name="Chest Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413815006_2L" title="Was chest imaging completed for this patient?">
Was chest imaging done?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(413815006)" styleId="413815006_2" title="Was chest imaging completed for this patient?" onchange="ruleEnDis4138150068758();enableOrDisableOther('LAB678');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB677_2L" title="Indicate the type of chest study performed. Please provide a response for each of the main test types (plain chest radiograph, chest CT Scan) and if test was not done please indicate so.">
Type of Chest Study:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(LAB677)" styleId="LAB677_2" title="Indicate the type of chest study performed. Please provide a response for each of the main test types (plain chest radiograph, chest CT Scan) and if test was not done please indicate so."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'LAB677_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(CHEST_IMAGING_TYPE_MIS)" value="key" label="value" /> </html:select>
<div id="LAB677_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB678_2L" title="Result of chest diagnostic testing">
Result of Chest Study:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(LAB678)" styleId="LAB678_2" title="Result of chest diagnostic testing"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'LAB678_2-selectedValues');enableOrDisableOther('LAB678')" >
<nedss:optionsCollection property="codedValue(CHEST_IMAGING_RSLT_MIS)" value="key" label="value" /> </html:select>
<div id="LAB678_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Result of chest diagnostic testing" id="LAB678_2OthL">Other Result of Chest Study:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB678Oth)" size="40" maxlength="40" title="Other Result of chest diagnostic testing" styleId="LAB678_2Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27015_2" name="SARS-COV-2 Testing Details" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;If multiple tests were performed for a given test type, enter the date for the first positive.</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS718_2L" title="RT-PCR Test Result">
RT-PCR Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS718)" styleId="NBS718_2" title="RT-PCR Test Result" onchange="ruleEnDisNBS7188764();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS719_2L" title="RT-PCR Test Date">
RT-PCR Test Date:</span>
</td>
<td>
<html:text name="PageForm" title="RT-PCR Test Date"  property="pageClientVO2.answer(NBS719)" maxlength="10" size="10" styleId="NBS719_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS720_2L" title="Antigen Test Result">
Antigen Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS720)" styleId="NBS720_2" title="Antigen Test Result" onchange="ruleEnDisNBS7208765();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS721_2L" title="Antigen Test Date">
Antigen Test Date:</span>
</td>
<td>
<html:text name="PageForm" title="Antigen Test Date"  property="pageClientVO2.answer(NBS721)" maxlength="10" size="10" styleId="NBS721_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS722_2L" title="IgG Test Result">
IgG Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS722)" styleId="NBS722_2" title="IgG Test Result" onchange="ruleEnDisNBS7228766();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS723_2L" title="IgG Test Date">
IgG Test Date:</span>
</td>
<td>
<html:text name="PageForm" title="IgG Test Date"  property="pageClientVO2.answer(NBS723)" maxlength="10" size="10" styleId="NBS723_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS724_2L" title="IgM Test Result">
IgM Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS724)" styleId="NBS724_2" title="IgM Test Result" onchange="ruleEnDisNBS7248767();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS725_2L" title="IgM Test Date">
IgM Test Date:</span>
</td>
<td>
<html:text name="PageForm" title="IgM Test Date"  property="pageClientVO2.answer(NBS725)" maxlength="10" size="10" styleId="NBS725_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS726_2L" title="IgA Test Result">
IgA Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS726)" styleId="NBS726_2" title="IgA Test Result" onchange="ruleEnDisNBS7268768();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS727_2L" title="IgA Test Date">
IgA Test Date:</span>
</td>
<td>
<html:text name="PageForm" title="IgA Test Date"  property="pageClientVO2.answer(NBS727)" maxlength="10" size="10" styleId="NBS727_2" onkeyup="DateMask(this,null,event)"/>
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
</div> </td></tr>
