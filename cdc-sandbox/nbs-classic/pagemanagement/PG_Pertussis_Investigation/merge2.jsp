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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","Case Information","General Comments","Pregnancy and Birth","Symptoms","Complications","Treatment","Laboratory","Vaccination Information","Epidemiology","Contact Investigation"};
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
<html:select name="PageForm" property="pageClientVO2.answer(DEM113)" styleId="DEM113_2" title="Patient's current sex." onchange="ruleEnDisDEM1138596();ruleHideUnhINV1788603();ruleEnDisINV1788611();;ruleEnDisNBS3648616();;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');;ruleEnDisNBS3648616();;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(DEM127)" styleId="DEM127_2" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1278593();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_UI_45_2" name="Occupation and Industry Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_45_2errorMessages">
<b> <a name="NBS_UI_45_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_45_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_45_2">
<tr id="patternNBS_UI_45_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_45_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_45_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_45_2">
<tr id="nopatternNBS_UI_45_2" class="odd" style="display:">
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
<span class="NBS_UI_45 InputFieldLabel" id="85659_1_2L" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation.">
Current Occupation Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85659_1)" styleId="85659_1_2" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation." onchange="unhideBatchImg('NBS_UI_45');">
<nedss:optionsCollection property="codedValue(PHVS_OCCUPATION_CDC_CENSUS2010)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="85658_3_2L" title="This data element is used to capture the narrative text of a subjects current occupation.">
Current Occupation:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(85658_3)" styleId ="85658_3_2" onkeyup="checkTextAreaLength(this, 200)" title="This data element is used to capture the narrative text of a subjects current occupation."/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_45 InputFieldLabel" id="85657_5_2L" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry.">
Current  Industry Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85657_5)" styleId="85657_5_2" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry." onchange="unhideBatchImg('NBS_UI_45');">
<nedss:optionsCollection property="codedValue(PHVS_INDUSTRY_CDC_CENSUS2010)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="85078_4_2L" title="This data element is used to capture the narrative text of subjects current industry.">
Current Industry:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(85078_4)" styleId ="85078_4_2" onkeyup="checkTextAreaLength(this, 200)" title="This data element is used to capture the narrative text of subjects current industry."/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_45">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_45BatchAddFunction()) writeQuestion('NBS_UI_45_2','patternNBS_UI_45_2','questionbodyNBS_UI_45_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_45_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_45BatchAddFunction()) writeQuestion('NBS_UI_45_2','patternNBS_UI_45_2','questionbodyNBS_UI_45_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_45_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_45BatchAddFunction()) writeQuestion('NBS_UI_45_2','patternNBS_UI_45_2','questionbodyNBS_UI_45_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_45"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_45_2')"/>&nbsp;	&nbsp;&nbsp;
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
<html:text name="PageForm" property="pageClientVO2.answer(INV200)" maxlength="25" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." styleId="INV200"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS336_2L" title="The Medical Record Number as reported by health care provider or facility">
Medical (Hospital) Record Number:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS336)" maxlength="25" title="The Medical Record Number as reported by health care provider or facility" styleId="NBS336"/>
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
<nedss:container id="NBS_INV_GENV2_UI_1_2" name="Reporting County" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS_INV_GENV2_UI_3_2" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128_2L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV128)" styleId="INV128_2" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288591();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328597();pgSelectNextFocus(this);">
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
<span class=" InputFieldLabel" id="INV178_2L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV178)" styleId="INV178_2" title="Assesses whether or not the patient is pregnant." onchange="ruleEnDisINV1788611();ruleHideUnhINV1788603();;ruleEnDisNBS3648616();;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');pgSelectNextFocus(this);">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV145_2L" title="Did the patient die from pertussis or complications (including secondary infection) associated with pertussis?">
Did the patient die from pertussis/complications (incl. secondary infection) associated w/pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV145)" styleId="INV145_2" title="Did the patient die from pertussis or complications (including secondary infection) associated with pertussis?" onchange="ruleEnDisINV1458592();pgSelectNextFocus(this);">
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

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="http://PUTLINKHERE" TARGET="_blank">Download and complete the Petussis Death Worksheet</a></td></tr>
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
<span class=" InputFieldLabel" id="INV926_2L" title="Was case patient a healthcare provider (HCP) at illness onset?">
Was the patient a healthcare provider (HCP) at illness onset?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV926)" styleId="INV926_2" title="Was case patient a healthcare provider (HCP) at illness onset?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV150_2L" title="Denotes whether the reported case was associated with an identified outbreak.">
Is this case part of a cluster or outbreak (e.g. total is 2 or more cases)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV150)" styleId="INV150_2" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1508594();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV152)" styleId="INV152_2" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1528595();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_INV_GENV2_UI_4_2" name="Exposure Location" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_GENV2_UI_4_2errorMessages">
<b> <a name="NBS_INV_GENV2_UI_4_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_GENV2_UI_4_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_GENV2_UI_4_2">
<tr id="patternNBS_INV_GENV2_UI_4_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_GENV2_UI_4_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_GENV2_UI_4_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_GENV2_UI_4_2">
<tr id="nopatternNBS_INV_GENV2_UI_4_2" class="odd" style="display:">
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
<span class="NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV502_2L" title="Indicates the country in which the disease was potentially acquired.">
Country of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV502)" styleId="INV502_2" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_INV_GENV2_UI_4');ruleEnDisINV5028601();getDWRStatesByCountry(this, 'INV503');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV503_2L" title="Indicates the state in which the disease was potentially acquired.">
State or Province of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV503)" styleId="INV503_2" title="Indicates the state in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_INV_GENV2_UI_4');getDWRCounties(this, 'INV505');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_STATEPROVINCEOFEXPOSURE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV504_2L" title="Indicates the city in which the disease was potentially acquired.">
City of Exposure:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV504)"  maxlength="100" title="Indicates the city in which the disease was potentially acquired." styleId="INV504_2" onkeyup="unhideBatchImg('NBS_INV_GENV2_UI_4');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV505_2L" title="Indicates the county in which the disease was potentially acquired.">
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
<tr id="AddButtonToggleNBS_INV_GENV2_UI_4">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_INV_GENV2_UI_4_2','patternNBS_INV_GENV2_UI_4_2','questionbodyNBS_INV_GENV2_UI_4_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_GENV2_UI_4_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_INV_GENV2_UI_4_2','patternNBS_INV_GENV2_UI_4_2','questionbodyNBS_INV_GENV2_UI_4_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_GENV2_UI_4_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_INV_GENV2_UI_4_2','patternNBS_INV_GENV2_UI_4_2','questionbodyNBS_INV_GENV2_UI_4_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_GENV2_UI_4"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_GENV2_UI_4_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_GENV2_UI_5_2" name="Binational Reporting" isHidden="F" classType="subSect" >

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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
<html:select name="PageForm" property="pageClientVO2.answer(NOT120)" styleId="NOT120_2" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1208602();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_UI_56_2" name="Pregnancy and Birth Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS364_2L" title="Was the patient &lt; 12 months old at illness onset?">
Was the patient &lt; 12 months old at illness onset?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS364)" styleId="NBS364_2" title="Was the patient &lt; 12 months old at illness onset?" onchange="ruleEnDisNBS3648616();;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="MTH111_2L" title="Mothers age at infants birth (used only if patient &lt; 1 year of age)">
Mothers Age at Infants Birth (in Years):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(MTH111)" size="10" maxlength="10"  title="Mothers age at infants birth (used only if patient &lt; 1 year of age)" styleId="MTH111_2" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS347_2L" title="Birth Weight in Grams">
Birth Weight (in grams):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS347)" size="20" maxlength="20"  title="Birth Weight in Grams" styleId="NBS347_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr style="display:none">
<td class="fieldName">
<span class="InputFieldLabel" id="NBS345_2L" title="Birth Weight in Pounds">
Pounds:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS345)" size="20" maxlength="20"  title="Birth Weight in Pounds" styleId="NBS345_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr style="display:none">
<td class="fieldName">
<span class="InputFieldLabel" id="NBS346_2L" title="Birth Weight in Ounces">
Ounces:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS346)" size="20" maxlength="20"  title="Birth Weight in Ounces" styleId="NBS346_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM228_2L" title="Indicate the patient's gestational age (in weeks) if case-patient was &lt; 1 year of age at illness onset.">
Patient's Gestational Age (in weeks):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(DEM228)" size="3" maxlength="3"  title="Indicate the patient's gestational age (in weeks) if case-patient was &lt; 1 year of age at illness onset." styleId="DEM228_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,50)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH172_2L" title="Did mother receive Tdap (if case-patient &lt; 1 year of age at illness onset)?">
Did the mother receive Tdap?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(MTH172)" styleId="MTH172_2" title="Did mother receive Tdap (if case-patient &lt; 1 year of age at illness onset)?" onchange="ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH173_2L" title="If mother received Tdap, when was it administered in relation to the pregnancy?">
If mother received Tdap, when was it administered in relation to the pregnancy?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(MTH173)" styleId="MTH173_2" title="If mother received Tdap, when was it administered in relation to the pregnancy?" onchange="enableOrDisableOther('MTH173');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TIMINGOFMATERNALTREATMENT_NND)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If mother received Tdap, when was it administered in relation to the pregnancy?" id="MTH173_2OthL">Other If mother received Tdap, when was it administered in relation to the pregnancy?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(MTH173Oth)" size="40" maxlength="40" title="Other If mother received Tdap, when was it administered in relation to the pregnancy?" styleId="MTH173_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="MTH174_2L" title="If mother received Tdap, what date was it administered?*(if available)">
If mother received Tdap, what date was it administered? (if available):</span>
</td>
<td>
<html:text name="PageForm" title="If mother received Tdap, what date was it administered?*(if available)"  property="pageClientVO2.answer(MTH174)" maxlength="10" size="10" styleId="MTH174_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_32_2" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV576_2L" title="Indicate whether the patient is/was symptomatic for pertussis?">
Did the patient experience any symptoms related to pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV576)" styleId="INV576_2" title="Indicate whether the patient is/was symptomatic for pertussis?" onchange="ruleEnDisINV5768614();ruleHideUnh497270028615();ruleHideUnhNBS3388619();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49727002_2L" title="Did the patient's illness include the symptom of cough?">
Did the patient have any cough?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(49727002)" styleId="49727002_2" title="Did the patient's illness include the symptom of cough?" onchange="ruleHideUnh497270028615();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV550_2L" title="Cough onset date">
Cough Onset Date:</span>
</td>
<td>
<html:text name="PageForm" title="Cough onset date"  property="pageClientVO2.answer(INV550)" maxlength="10" size="10" styleId="INV550_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV902_2L" title="Patients age at cough onset">
Age at Cough Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV902)" size="3" maxlength="3"  title="Patients age at cough onset" styleId="INV902_2" onkeyup="isNumericCharacterEntered(this)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO2.answer(INV902Unit)" styleId="INV902UNIT_2">
<nedss:optionsCollection property="codedValue(PHVS_AGEUNIT_NND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV557_2L" title="What was the duration (in days) of the patients cough?">
Total Cough Duration (in days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV557)" size="3" maxlength="3"  title="What was the duration (in days) of the patients cough?" styleId="INV557_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV555_2L" title="Date of the patients final interview">
Date of Final Interview:</span>
</td>
<td>
<html:text name="PageForm" title="Date of the patients final interview"  property="pageClientVO2.answer(INV555)" maxlength="10" size="10" styleId="INV555_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS349_2L" title="Was there a cough at the patients final interview?">
Cough at Final Interview:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS349)" styleId="NBS349_2" title="Was there a cough at the patients final interview?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="43025008_2L" title="Did the patients illness include the symptom of paroxysmal cough?">
Did the patient have paroxysmal cough?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(43025008)" styleId="43025008_2" title="Did the patients illness include the symptom of paroxysmal cough?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="60537006_2L" title="Did the patients illness include the symptom of whoop?">
Did the patient have whoop?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(60537006)" styleId="60537006_2" title="Did the patients illness include the symptom of whoop?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="424580008_2L" title="Did the patients illness include the symptom of post-tussive vomiting?">
Did the patient have post-tussive vomiting?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(424580008)" styleId="424580008_2" title="Did the patients illness include the symptom of post-tussive vomiting?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="1023001_2L" title="Did the patients illness include the symptom of apnea?">
Did the patient have apnea?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(1023001)" styleId="1023001_2" title="Did the patients illness include the symptom of apnea?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="3415004_2L" title="Did the patient's illness include the symptom of cyanosis?">
Did the patient have cyanosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(3415004)" styleId="3415004_2" title="Did the patient's illness include the symptom of cyanosis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338_2L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other symptom(s)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS338)" styleId="NBS338_2" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleHideUnhNBS3388619();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS338_OTH_2L" title="Specify other signs and symptoms.">
Specify Other Symptom(s):</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS338_OTH)" styleId ="NBS338_OTH_2" onkeyup="checkTextAreaLength(this, 100)" title="Specify other signs and symptoms."/>
</td> </tr>

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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_64_2" name="Signs and Symptoms Repeating Block" isHidden="T" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_64_2errorMessages">
<b> <a name="NBS_UI_64_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_64_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_64_2">
<tr id="patternNBS_UI_64_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_64_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_64_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_64_2">
<tr id="nopatternNBS_UI_64_2" class="odd" style="display:">
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
<span class="NBS_UI_64 InputFieldLabel" id="INV272_2L" title="Select all the signs and symptoms that are associated with the patient">
Signs and Symptoms Associated with Patient:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV272)" styleId="INV272_2" title="Select all the signs and symptoms that are associated with the patient" onchange="unhideBatchImg('NBS_UI_64');enableOrDisableOther('INV272');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SIGNSSYMPTOMS_PERTUSSIS_NND)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Select all the signs and symptoms that are associated with the patient" id="INV272_2OthL">Other Signs and Symptoms Associated with Patient:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV272Oth)" size="40" maxlength="40" title="Other Select all the signs and symptoms that are associated with the patient" onkeyup="unhideBatchImg('NBS_UI_64')" styleId="INV272_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_64 InputFieldLabel" id="INV919_2L" title="Indicator for associated signs and symptoms">
Signs and Symptoms Indicator:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV919)" styleId="INV919_2" title="Indicator for associated signs and symptoms" onchange="unhideBatchImg('NBS_UI_64');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_64">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_64BatchAddFunction()) writeQuestion('NBS_UI_64_2','patternNBS_UI_64_2','questionbodyNBS_UI_64_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_64_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_64BatchAddFunction()) writeQuestion('NBS_UI_64_2','patternNBS_UI_64_2','questionbodyNBS_UI_64_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_64_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_64BatchAddFunction()) writeQuestion('NBS_UI_64_2','patternNBS_UI_64_2','questionbodyNBS_UI_64_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_64"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_64_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_33_2" name="Complications" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS340_2L" title="Indicate whether the patient experienced any complications related to pertussis.">
Did the patient experience any complications related to pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS340)" styleId="NBS340_2" title="Indicate whether the patient experienced any complications related to pertussis." onchange="ruleEnDisNBS3408613();ruleHideUnhNBS3438618();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV923_2L" title="Result of chest x-ray for pneumonia">
Result of Chest X-Ray for Pneumonia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV923)" styleId="INV923_2" title="Result of chest x-ray for pneumonia">
<nedss:optionsCollection property="codedValue(PHVS_PNUND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="91175000_2L" title="Did the patient have generalized or focal seizures due to pertussis?">
Did the patient have generalized or focal seizures due to pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(91175000)" styleId="91175000_2" title="Did the patient have generalized or focal seizures due to pertussis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="81308009_2L" title="Did the patient have acute encephalopathy due to pertussis?">
Did the patient have acute encephalopathy due to pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(81308009)" styleId="81308009_2" title="Did the patient have acute encephalopathy due to pertussis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS343_2L" title="Other Complications">
Other Complication(s):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS343)" styleId="NBS343_2" title="Other Complications" onchange="ruleHideUnhNBS3438618();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS343_OTH_2L" title="Specify Other Complication">
Specify Other Complication:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS343_OTH)" maxlength="100" title="Specify Other Complication" styleId="NBS343_OTH"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_65_2" name="Complications Repeating Block" isHidden="T" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_65_2errorMessages">
<b> <a name="NBS_UI_65_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_65_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_65_2">
<tr id="patternNBS_UI_65_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_65_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_65_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_65_2">
<tr id="nopatternNBS_UI_65_2" class="odd" style="display:">
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
<span class="NBS_UI_65 InputFieldLabel" id="67187_5_2L" title="Complications associated with the illness being reported">
Type of Complications:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(67187_5)" styleId="67187_5_2" title="Complications associated with the illness being reported" onchange="unhideBatchImg('NBS_UI_65');enableOrDisableOther('67187_5');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_COMPLICATIONS_PERTUSSIS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Complications associated with the illness being reported" id="67187_5_2OthL">Other Type of Complications:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(67187_5Oth)" size="40" maxlength="40" title="Other Complications associated with the illness being reported" onkeyup="unhideBatchImg('NBS_UI_65')" styleId="67187_5_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_65 InputFieldLabel" id="INV920_2L" title="Indicator for associated complication">
Type of Complications Indicator:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV920)" styleId="INV920_2" title="Indicator for associated complication" onchange="unhideBatchImg('NBS_UI_65');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_65">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_65BatchAddFunction()) writeQuestion('NBS_UI_65_2','patternNBS_UI_65_2','questionbodyNBS_UI_65_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_65_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_65BatchAddFunction()) writeQuestion('NBS_UI_65_2','patternNBS_UI_65_2','questionbodyNBS_UI_65_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_65_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_65BatchAddFunction()) writeQuestion('NBS_UI_65_2','patternNBS_UI_65_2','questionbodyNBS_UI_65_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_65"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_65_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_53_2" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV559_2L" title="Were antibiotics given to the patient?">
Were antibiotics given to the patient?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV559)" styleId="INV559_2" title="Were antibiotics given to the patient?" onchange="ruleHideUnhINV5598607();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_54_2" name="Please list all of the antibiotics the patient received in the order the antibiotics were taken." isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_54_2errorMessages">
<b> <a name="NBS_UI_54_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_54_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_54_2">
<tr id="patternNBS_UI_54_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_54_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_54_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_54_2">
<tr id="nopatternNBS_UI_54_2" class="odd" style="display:">
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
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_54_2 InputFieldLabel" id="29303_5_2L" title="Indicate the antibiotic the patient received.">
Medication (Antibiotic) Administered:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(29303_5)" styleId="29303_5_2" title="Indicate the antibiotic the patient received." onchange="unhideBatchImg('NBS_UI_54');">
<nedss:optionsCollection property="codedValue(PHVS_ANTIBIOTICRECEIVED_PERTUSSIS)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV924_2L" title="Indicate the date the treatment was initiated.">
Treatment Start Date:</span>
</td>
<td>
<html:text name="PageForm" title="Indicate the date the treatment was initiated."  property="pageClientVO2.answer(INV924)" maxlength="10" size="10" styleId="INV924_2" onkeyup="unhideBatchImg('NBS_UI_54');DateMask(this,null,event)" styleClass="NBS_UI_54"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="67453_1_2L" title="Indicate the number of days the patient actually took the antibiotic referenced.">
Number of Days Actually Taken:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(67453_1)" size="3" maxlength="3"  title="Indicate the number of days the patient actually took the antibiotic referenced." styleId="67453_1_2" onkeyup="unhideBatchImg('NBS_UI_54');isNumericCharacterEntered(this)"/>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_54">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_54BatchAddFunction()) writeQuestion('NBS_UI_54_2','patternNBS_UI_54_2','questionbodyNBS_UI_54_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_54_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_54BatchAddFunction()) writeQuestion('NBS_UI_54_2','patternNBS_UI_54_2','questionbodyNBS_UI_54_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_54_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_54BatchAddFunction()) writeQuestion('NBS_UI_54_2','patternNBS_UI_54_2','questionbodyNBS_UI_54_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_54"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_54_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_47_2" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740_2L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done for pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV740)" styleId="INV740_2" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408606();ruleEnDisINV7408605();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV164_2L" title="Was the case laboratory confirmed?">
Was the case laboratory confirmed?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV164)" styleId="INV164_2" title="Was the case laboratory confirmed?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB515_2L" title="Was a specimen sent to CDC for testing?">
Specimen Sent to CDC:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB515)" styleId="LAB515_2" title="Was a specimen sent to CDC for testing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="Date sent for genotyping" id="NBS380_2L">
LEGACY Date sent for genotyping:</span>
</td>
<td>
<html:text name="PageForm" title="Date sent for genotyping" property="pageClientVO2.answer(NBS380)" maxlength="10" size="10" styleId="NBS380_2"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="Legacy Specimen Type" id="NBS381_2L">
LEGACY Specimen Type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS381)" maxlength="25" title="Legacy Specimen Type" styleId="NBS381"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44_2" name="Interpretive Lab Data Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_44_2errorMessages">
<b> <a name="NBS_UI_44_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_44_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_44_2">
<tr id="patternNBS_UI_44_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_44_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_44_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_44_2">
<tr id="nopatternNBS_UI_44_2" class="odd" style="display:">
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
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_44_2 InputFieldLabel" id="INV290_2L" title="Type of test(s) performed for this case.">
Lab Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV290)" styleId="INV290_2" title="Type of test(s) performed for this case." onchange="unhideBatchImg('NBS_UI_44');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_PERTUSSIS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Type of test(s) performed for this case." id="INV290_2OthL">Other Lab Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV290Oth)" size="40" maxlength="40" title="Other Type of test(s) performed for this case." onkeyup="unhideBatchImg('NBS_UI_44')" styleId="INV290_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_44 InputFieldLabel" id="INV291_2L" title="Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative).">
Lab Test Result Qualitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV291)" styleId="INV291_2" title="Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative)." onchange="unhideBatchImg('NBS_UI_44');enableOrDisableOther('INV291');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTINTERPRETATION_PERTUSSIS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative)." id="INV291_2OthL">Other Lab Test Result Qualitative:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV291Oth)" size="40" maxlength="40" title="Other Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative)." onkeyup="unhideBatchImg('NBS_UI_44')" styleId="INV291_2Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628_2L" title="Indicate the quantitative test result value for the lab test performed.">
Lab Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB628)"  maxlength="10" title="Indicate the quantitative test result value for the lab test performed." styleId="LAB628_2" onkeyup="unhideBatchImg('NBS_UI_44');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_44 InputFieldLabel" id="LAB115_2L" title="Units of measure for the Quantitative Test Result Value">
Quantitative Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB115)" styleId="LAB115_2" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('NBS_UI_44');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163_2L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date/Time:</span>
</td>
<td>
<html:text name="PageForm" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date."  property="pageClientVO2.answer(LAB163)" maxlength="10" size="10" styleId="LAB163_2" onkeyup="unhideBatchImg('NBS_UI_44');DateMask(this,null,event)" styleClass="NBS_UI_44"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_44 InputFieldLabel" id="LAB165_2L" title="Anatomic site or specimen type from which positive lab specimen was collected.">
Specimen Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB165)" styleId="LAB165_2" title="Anatomic site or specimen type from which positive lab specimen was collected." onchange="unhideBatchImg('NBS_UI_44');enableOrDisableOther('LAB165');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SPECIMENTYPEVPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165_2OthL">Other Specimen Source:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB165Oth)" size="40" maxlength="40" title="Other Anatomic site or specimen type from which positive lab specimen was collected." onkeyup="unhideBatchImg('NBS_UI_44')" styleId="LAB165_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB516_2L" title="Date specimen sent to CDC">
Date Specimen Sent to CDC:</span>
</td>
<td>
<html:text name="PageForm" title="Date specimen sent to CDC"  property="pageClientVO2.answer(LAB516)" maxlength="10" size="10" styleId="LAB516_2" onkeyup="unhideBatchImg('NBS_UI_44');DateMask(this,null,event)" styleClass="NBS_UI_44"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_44 InputFieldLabel" id="LAB606_2L" title="Enter the performing laboratory type">
Performing Lab Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB606)" styleId="LAB606_2" title="Enter the performing laboratory type" onchange="unhideBatchImg('NBS_UI_44');enableOrDisableOther('LAB606');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_VPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Enter the performing laboratory type" id="LAB606_2OthL">Other Performing Lab Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB606Oth)" size="40" maxlength="40" title="Other Enter the performing laboratory type" onkeyup="unhideBatchImg('NBS_UI_44')" styleId="LAB606_2Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_44">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44_2','patternNBS_UI_44_2','questionbodyNBS_UI_44_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_44_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44_2','patternNBS_UI_44_2','questionbodyNBS_UI_44_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_44_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44_2','patternNBS_UI_44_2','questionbodyNBS_UI_44_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_44"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_44_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42_2" name="Vaccine Preventable Disease (VPD) Lab Message Linkage" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_42_2errorMessages">
<b> <a name="NBS_UI_42_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_42_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_42_2">
<tr id="patternNBS_UI_42_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_42_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_42_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_42_2">
<tr id="nopatternNBS_UI_42_2" class="odd" style="display:">
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
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_42_2 InputFieldLabel" id="LAB143_2L" title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message">
VPD Lab Message Reference Laboratory:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB143)" styleId="LAB143_2" title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message" onchange="unhideBatchImg('NBS_UI_42');">
<nedss:optionsCollection property="codedValue(VPD_LAB_REFERENCE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB598_2L" title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message">
VPD Lab Message Patient Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB598)"  maxlength="25" title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message" styleId="LAB598_2" onkeyup="unhideBatchImg('NBS_UI_42');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB125_2L" title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message">
VPD Lab Message Specimen Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB125)"  maxlength="25" title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message" styleId="LAB125_2" onkeyup="unhideBatchImg('NBS_UI_42');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_42">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_42BatchAddFunction()) writeQuestion('NBS_UI_42_2','patternNBS_UI_42_2','questionbodyNBS_UI_42_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_42_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_42BatchAddFunction()) writeQuestion('NBS_UI_42_2','patternNBS_UI_42_2','questionbodyNBS_UI_42_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_42_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_42BatchAddFunction()) writeQuestion('NBS_UI_42_2','patternNBS_UI_42_2','questionbodyNBS_UI_42_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_42"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_42_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37_2" name="Vaccination Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126_2L" title="Did the patient ever receive pertussis-containing vaccine?">
Did the patient ever receive pertussis-containing vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC126)" styleId="VAC126_2" title="Did the patient ever receive pertussis-containing vaccine?" onchange="ruleEnDisVAC1268612();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;For the next 2 questions, to indicate that the number of vaccine doses is unknown, enter 99.</span></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC132_2L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine). To indicate that the number of doses is unknown, enter 99.">
If yes, how many doses?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(VAC132)" size="2" maxlength="2"  title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine). To indicate that the number of doses is unknown, enter 99." styleId="VAC132_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC140_2L" title="Number of vaccine doses against this disease prior to illness onset. To indicate that the number of doses is unknown, enter 99.">
Vaccination Doses Prior to Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(VAC140)" size="2" maxlength="2"  title="Number of vaccine doses against this disease prior to illness onset. To indicate that the number of doses is unknown, enter 99." styleId="VAC140_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC142_2L" title="Indicate the date the patient received the last vaccine dose against mumps prior to illness onset.">
Date of last dose prior to illness onset:</span>
</td>
<td>
<html:text name="PageForm" title="Indicate the date the patient received the last vaccine dose against mumps prior to illness onset."  property="pageClientVO2.answer(VAC142)" maxlength="10" size="10" styleId="VAC142_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC148_2L" title="This data element is used for all cases. For example, a case might not have received a vaccine because they were too young per ACIP schedules.">
Was the patient vaccinated per ACIP recommendations?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC148)" styleId="VAC148_2" title="This data element is used for all cases. For example, a case might not have received a vaccine because they were too young per ACIP schedules." onchange="ruleEnDisVAC1488604();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC149_2L" title="Indicate the reason the patient was not vaccinated as recommended by ACIP.">
Reason patient not vaccinated per ACIP recommendations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC149)" styleId="VAC149_2" title="Indicate the reason the patient was not vaccinated as recommended by ACIP.">
<nedss:optionsCollection property="codedValue(PHVS_VAC_NOTG_RSN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC133_2L" title="Indicate any pertinent notes regarding the patient's vaccination history that may not have already been communicated via the standard vaccination questions on this form.">
Notes pertaining to the patient's vaccination history:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(VAC133)" styleId ="VAC133_2" onkeyup="checkTextAreaLength(this, 2000)" title="Indicate any pertinent notes regarding the patient's vaccination history that may not have already been communicated via the standard vaccination questions on this form."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_36_2" name="Disease Transmission" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV907_2L" title="Is this case epi-linked to a laboratory-confirmed case?">
Is this case epi-linked to a laboratory-confirmed case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV907)" styleId="INV907_2" title="Is this case epi-linked to a laboratory-confirmed case?" onchange="ruleEnDisINV9078608();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS350_2L" title="If epi-linked to a laboratory-confirmed case, Case ID of epi-linked case.">
Case ID of epi-linked case:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS350)" maxlength="25" title="If epi-linked to a laboratory-confirmed case, Case ID of epi-linked case." styleId="NBS350"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV224_2L" title="What was the transmission setting where the condition was acquired?">
Transmission Setting (Where did the case acquire pertusis?):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV224)" styleId="INV224_2" title="What was the transmission setting where the condition was acquired?" onchange="enableOrDisableOther('INV224');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TRAN_SETNG)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What was the transmission setting where the condition was acquired?" id="INV224_2OthL">Other Transmission Setting (Where did the case acquire pertusis?):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV224Oth)" size="40" maxlength="40" title="Other What was the transmission setting where the condition was acquired?" styleId="INV224_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS351_2L" title="Was there documented transmission from this case of pertussis to a new setting (outside of the household)?">
Was there documented transmission from this case to a new setting (outside of the household)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS351)" styleId="NBS351_2" title="Was there documented transmission from this case of pertussis to a new setting (outside of the household)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV561_2L" title="What was the new setting (outside of the household) for transmission of pertussis from this case?">
What was the new setting (outside of the household) for transmission of pertussis from this case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV561)" styleId="INV561_2" title="What was the new setting (outside of the household) for transmission of pertussis from this case?" onchange="enableOrDisableOther('INV561');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SETTINGOFFURTHERSPREAD_CDC)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What was the new setting (outside of the household) for transmission of pertussis from this case?" id="INV561_2OthL">Other What was the new setting (outside of the household) for transmission of pertussis from this case?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV561Oth)" size="40" maxlength="40" title="Other What was the new setting (outside of the household) for transmission of pertussis from this case?" styleId="INV561_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS354_2L" title="Were there one or more suspected sources of infection (A suspected source is another person with a cough who was in contact with  the case 7-20 days before the cases cough).">
Were there one or more suspected sources of infection?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS354)" styleId="NBS354_2" title="Were there one or more suspected sources of infection (A suspected source is another person with a cough who was in contact with  the case 7-20 days before the cases cough)." onchange="ruleHideUnhNBS3548610();ruleEnDisNBS3548609();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS355_2L" title="Indicate the number of suspected sources of infection.">
Number of suspected sources of infection:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS355)" size="3" maxlength="3"  title="Indicate the number of suspected sources of infection." styleId="NBS355_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,999)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV562_2L" title="Indicate the number of contacts of this case recommended to receive antibiotic prophylaxis.">
Number of contacts of this case recommended to receive antibiotic prophylaxis:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV562)" size="3" maxlength="3"  title="Indicate the number of contacts of this case recommended to receive antibiotic prophylaxis." styleId="INV562_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57_2" name="For each suspected source of infection, indicate the following:" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_57_2errorMessages">
<b> <a name="NBS_UI_57_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_57_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_57_2">
<tr id="patternNBS_UI_57_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_57_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_57_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_57_2">
<tr id="nopatternNBS_UI_57_2" class="odd" style="display:">
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
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS356_2L" title="Suspected source age">
Age:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS356)" size="3" maxlength="3"  title="Suspected source age" styleId="NBS356_2" onkeyup="unhideBatchImg('NBS_UI_57');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_57"/>
<html:select name="PageForm" property="pageClientVO2.answer(NBS356Unit)" styleId="NBS356UNIT_2" onchange="unhideBatchImg('NBS_UI_57')">
<nedss:optionsCollection property="codedValue(AGE_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_57 InputFieldLabel" id="NBS358_2L" title="Suspected source sex">
Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS358)" styleId="NBS358_2" title="Suspected source sex" onchange="unhideBatchImg('NBS_UI_57');">
<nedss:optionsCollection property="codedValue(SEX)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS363_2L" title="Suspected source cough onset date">
Cough Onset Date:</span>
</td>
<td>
<html:text name="PageForm" title="Suspected source cough onset date"  property="pageClientVO2.answer(NBS363)" maxlength="10" size="10" styleId="NBS363_2" onkeyup="unhideBatchImg('NBS_UI_57');DateMask(this,null,event)" styleClass="NBS_UI_57"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_57 InputFieldLabel" id="NBS359_2L" title="Suspected source relationship to case">
Relationship to Case:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS359)" styleId="NBS359_2" title="Suspected source relationship to case" onchange="unhideBatchImg('NBS_UI_57');enableOrDisableOther('NBS359');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_RELATIONSHIP_VPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Suspected source relationship to case" id="NBS359_2OthL">Other Relationship to Case:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS359Oth)" size="40" maxlength="40" title="Other Suspected source relationship to case" onkeyup="unhideBatchImg('NBS_UI_57')" styleId="NBS359_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_57 InputFieldLabel" id="NBS362_2L" title="How many doses of pertussis-containing vaccine has the suspected source received?">
How many doses of pertussis-containing vaccine has the suspected source received?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS362)" styleId="NBS362_2" title="How many doses of pertussis-containing vaccine has the suspected source received?" onchange="unhideBatchImg('NBS_UI_57');">
<nedss:optionsCollection property="codedValue(NBS_VAC_DOSE_NUM)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_57">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57_2','patternNBS_UI_57_2','questionbodyNBS_UI_57_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_57_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57_2','patternNBS_UI_57_2','questionbodyNBS_UI_57_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_57_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57_2','patternNBS_UI_57_2','questionbodyNBS_UI_57_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_57"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_57_2')"/>&nbsp;	&nbsp;&nbsp;
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

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV169_2L" title="Condition_Cd should always be a hidden or read-only field.">
Hidden Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV169)" styleId="INV169_2" title="Condition_Cd should always be a hidden or read-only field." disabled="true">
<nedss:optionsCollection property="codedValue(PHC_TYPE)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
