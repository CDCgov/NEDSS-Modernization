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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","Case Information","General Comments","Symptoms","Complications","Laboratory","Epidemiology","Vaccination Information","Contact Investigation"};
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
<html:select name="PageForm" property="pageClientVO2.answer(DEM113)" styleId="DEM113_2" title="Patient's current sex." onchange="ruleEnDisDEM1138625();ruleHideUnhINV1788632();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(DEM127)" styleId="DEM127_2" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1278622();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_1_2" name="Occupation and Industry Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_NBS_INV_MUM_UI_1_2errorMessages">
<b> <a name="NBS_UI_NBS_INV_MUM_UI_1_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_MUM_UI_1_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_1_2">
<tr id="patternNBS_UI_NBS_INV_MUM_UI_1_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_NBS_INV_MUM_UI_1_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_NBS_INV_MUM_UI_1_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_1_2">
<tr id="nopatternNBS_UI_NBS_INV_MUM_UI_1_2" class="odd" style="display:">
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
<span class="NBS_UI_NBS_INV_MUM_UI_1 InputFieldLabel" id="85659_1_2L" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation.">
Current Occupation Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85659_1)" styleId="85659_1_2" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation." onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_1');">
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
<span class="NBS_UI_NBS_INV_MUM_UI_1 InputFieldLabel" id="85657_5_2L" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry.">
Current  Industry Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85657_5)" styleId="85657_5_2" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry." onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_1');">
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
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_1">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_NBS_INV_MUM_UI_1BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_1_2','patternNBS_UI_NBS_INV_MUM_UI_1_2','questionbodyNBS_UI_NBS_INV_MUM_UI_1_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_1_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_NBS_INV_MUM_UI_1BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_1_2','patternNBS_UI_NBS_INV_MUM_UI_1_2','questionbodyNBS_UI_NBS_INV_MUM_UI_1_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_NBS_INV_MUM_UI_1_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_NBS_INV_MUM_UI_1BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_1_2','patternNBS_UI_NBS_INV_MUM_UI_1_2','questionbodyNBS_UI_NBS_INV_MUM_UI_1_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_NBS_INV_MUM_UI_1"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_NBS_INV_MUM_UI_1_2')"/>&nbsp;	&nbsp;&nbsp;
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
<html:select name="PageForm" property="pageClientVO2.answer(INV128)" styleId="INV128_2" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288620();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328626();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV178)" styleId="INV178_2" title="Assesses whether or not the patient is pregnant." onchange="ruleHideUnhINV1788632();pgSelectNextFocus(this);">
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
<span class=" InputFieldLabel" id="INV145_2L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV145)" styleId="INV145_2" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1458621();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV150)" styleId="INV150_2" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1508623();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV152)" styleId="INV152_2" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1528624();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV502)" styleId="INV502_2" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');ruleEnDisINV5028630();getDWRStatesByCountry(this, 'INV503');pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(NOT120)" styleId="NOT120_2" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1208631();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_5_2" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV576_2L" title="Indicate whether the patient is/was symptomatic for mumps?">
Did the patient experience any symptoms related to mumps?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV576)" styleId="INV576_2" title="Indicate whether the patient is/was symptomatic for mumps?" onchange="ruleEnDisINV5768636();ruleHideUnh3866610068635();ruleHideUnh3866610068635();ruleHideUnhNBS3388640();ruleHideUnhNBS3428638();null;enableOrDisableOther('INV301');null;enableOrDisableOther('INV301');enableOrDisableOther('INV301');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS342_2L" title="Did the patient have Parotitis?">
Parotitis (opposite 2nd molars)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS342)" styleId="NBS342_2" title="Did the patient have Parotitis?" onchange="ruleHideUnhNBS3428638();null;enableOrDisableOther('INV301');enableOrDisableOther('INV301');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV301_2L" title="Indicates if the parotitis is unilateral or bilateral">
Parotitis Laterality:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV301)" styleId="INV301_2" title="Indicates if the parotitis is unilateral or bilateral" onchange="enableOrDisableOther('INV301');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PAROTITISLATERALITY_MUMPS)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicates if the parotitis is unilateral or bilateral" id="INV301_2OthL">Other Parotitis Laterality:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV301Oth)" size="40" maxlength="40" title="Other Indicates if the parotitis is unilateral or bilateral" styleId="INV301_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="274667000_2L" title="Indication of whether patient had jaw pain.">
Jaw Pain?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(274667000)" styleId="274667000_2" title="Indication of whether patient had jaw pain.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS339_2L" title="Indicate whether the patient had salivary gland swelling.">
Salivary Gland Swelling?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS339)" styleId="NBS339_2" title="Indicate whether the patient had salivary gland swelling.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271614004_2L" title="Indication of whether patient had submandibular salivary gland swelling.">
Submandibular salivary gland swelling?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(271614004)" styleId="271614004_2" title="Indication of whether patient had submandibular salivary gland swelling.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271615003_2L" title="Indication of whether the patient had sublingual salivary gland swelling.">
Sublingual salivary gland swelling?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(271615003)" styleId="271615003_2" title="Indication of whether the patient had sublingual salivary gland swelling.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV288_2L" title="Date of subjects salivary gland swelling (including parotitis) onset">
Salivary Gland Swelling Onset Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date of subjects salivary gland swelling (including parotitis) onset"  property="pageClientVO2.answer(INV288)" maxlength="10" size="10" styleId="INV288_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV303_2L" title="The length of time (days) that the subject exhibited swelling of the salivary gland">
Salivary Gland Swelling Duration in Days:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV303)" size="3" maxlength="3"  title="The length of time (days) that the subject exhibited swelling of the salivary gland" styleId="INV303_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006_2L" title="Did the patient have fever?">
Fever?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(386661006)" styleId="386661006_2" title="Did the patient have fever?" onchange="ruleHideUnh3866610068635();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202_2L" title="What was the subjects highest measured temperature during this illness?">
Highest Measured Temperature:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV202)" size="10" maxlength="10"  title="What was the subjects highest measured temperature during this illness?" styleId="INV202_2" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this);pgCheckFieldMinMax(this,30,110)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO2.answer(INV202Unit)" styleId="INV202UNIT_2">
<nedss:optionsCollection property="codedValue(PHVS_TEMP_UNIT)" value="key" label="value" /> </html:select>
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="79890006_2L" title="Did patient have loss of appetite?">
Loss of Appetite?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(79890006)" styleId="79890006_2" title="Did patient have loss of appetite?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267031002_2L" title="Did the patient have tiredness?">
Tiredness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(267031002)" styleId="267031002_2" title="Did the patient have tiredness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="25064002_2L" title="Did the patient have headache?">
Headache?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(25064002)" styleId="25064002_2" title="Did the patient have headache?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001_2L" title="Did the patient have myalgia (muscle pain)?">
Myalgia/Muscle Pain?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(68962001)" styleId="68962001_2" title="Did the patient have myalgia (muscle pain)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338_2L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other symptom(s)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS338)" styleId="NBS338_2" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleHideUnhNBS3388640();pgSelectNextFocus(this);">
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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_13_2" name="Complications" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS340_2L" title="Indicate whether the patient experienced any complications related to mumps.">
Did the patient experience any complications related to mumps?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS340)" styleId="NBS340_2" title="Indicate whether the patient experienced any complications related to mumps." onchange="ruleEnDisNBS3408633();ruleHideUnh2720330078639();null;enableOrDisableOther('INV307');null;enableOrDisableOther('INV307');ruleHideUnhNBS3438641();enableOrDisableOther('INV307');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44201003_2L" title="Meningitis">
Meningitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(44201003)" styleId="44201003_2" title="Meningitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="272033007_2L" title="Deafness">
Deafness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(272033007)" styleId="272033007_2" title="Deafness" onchange="ruleHideUnh2720330078639();null;enableOrDisableOther('INV307');enableOrDisableOther('INV307');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV307_2L" title="Was the type of deafness permanent or temporary?">
Type of Deafness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV307)" styleId="INV307_2" title="Was the type of deafness permanent or temporary?" onchange="enableOrDisableOther('INV307');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_DEAFNESSTYPE_MUMPS)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Was the type of deafness permanent or temporary?" id="INV307_2OthL">Other Type of Deafness:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV307Oth)" size="40" maxlength="40" title="Other Was the type of deafness permanent or temporary?" styleId="INV307_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="78580004_2L" title="Orchitis">
Orchitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(78580004)" styleId="78580004_2" title="Orchitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="31646008_2L" title="Encephalitis">
Encephalitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(31646008)" styleId="31646008_2" title="Encephalitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="237443002_2L" title="Mastitis">
Mastitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(237443002)" styleId="237443002_2" title="Mastitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75548002_2L" title="Oophoritis">
Oophoritis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(75548002)" styleId="75548002_2" title="Oophoritis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="10665004_2L" title="Pancreatitis">
Pancreatitis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(10665004)" styleId="10665004_2" title="Pancreatitis">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS343_2L" title="Other Complications">
Other Complication(s)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS343)" styleId="NBS343_2" title="Other Complications" onchange="ruleHideUnhNBS3438641();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS343_OTH_2L" title="Specify other complicationsh.">
Specify Other Complication(s):</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS343_OTH)" styleId ="NBS343_OTH_2" onkeyup="checkTextAreaLength(this, 100)" title="Specify other complicationsh."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_15_2" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740_2L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV740)" styleId="INV740_2" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408643();ruleEnDisINV7408637();pgSelectNextFocus(this);">
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
Was a specimen sent to CDC for testing?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB515)" styleId="LAB515_2" title="Was a specimen sent to CDC for testing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="LEGACY Date Sent for Genotyping" id="NBS380_2L">
LEGACY Date Sent for Genotyping:</span>
</td>
<td>
<html:text name="PageForm" title="LEGACY Date Sent for Genotyping" property="pageClientVO2.answer(NBS380)" maxlength="10" size="10" styleId="NBS380_2"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_16_2" name="Lab Interpretive Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_NBS_INV_MUM_UI_16_2errorMessages">
<b> <a name="NBS_UI_NBS_INV_MUM_UI_16_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_MUM_UI_16_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_16_2">
<tr id="patternNBS_UI_NBS_INV_MUM_UI_16_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_NBS_INV_MUM_UI_16_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_NBS_INV_MUM_UI_16_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_16_2">
<tr id="nopatternNBS_UI_NBS_INV_MUM_UI_16_2" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_NBS_INV_MUM_UI_16_2 InputFieldLabel" id="INV290_2L" title="Test Type">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV290)" styleId="INV290_2" title="Test Type" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_MUMPS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Test Type" id="INV290_2OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV290Oth)" size="40" maxlength="40" title="Other Test Type" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16')" styleId="INV290_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="INV291_2L" title="Test Result (Qualitative)">
Test Result (Qualitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV291)" styleId="INV291_2" title="Test Result (Qualitative)" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');enableOrDisableOther('INV291');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTINTERPRETATION_VPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Test Result (Qualitative)" id="INV291_2OthL">Other Test Result (Qualitative):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV291Oth)" size="40" maxlength="40" title="Other Test Result (Qualitative)" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16')" styleId="INV291_2Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628_2L" title="Quantitative Test Result Value.">
Test Result (Quantitative):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB628)"  maxlength="10" title="Quantitative Test Result Value." styleId="LAB628_2" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="LAB115_2L" title="Units of measure for the Quantitative Test Result Value">
Quantitative Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB115)" styleId="LAB115_2" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163_2L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date."  property="pageClientVO2.answer(LAB163)" maxlength="10" size="10" styleId="LAB163_2" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');DateMask(this,null,event)" styleClass="NBS_UI_NBS_INV_MUM_UI_16"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="LAB165_2L" title="Anatomic site or specimen type from which positive lab specimen was collected.">
Specimen Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB165)" styleId="LAB165_2" title="Anatomic site or specimen type from which positive lab specimen was collected." onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');enableOrDisableOther('LAB165');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SPECIMENTYPEVPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165_2OthL">Other Specimen Source:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB165Oth)" size="40" maxlength="40" title="Other Anatomic site or specimen type from which positive lab specimen was collected." onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16')" styleId="LAB165_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB516_2L" title="Date specimen sent to CDC">
Date Specimen Sent to CDC:</span>
</td>
<td>
<html:text name="PageForm" title="Date specimen sent to CDC"  property="pageClientVO2.answer(LAB516)" maxlength="10" size="10" styleId="LAB516_2" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');DateMask(this,null,event)" styleClass="NBS_UI_NBS_INV_MUM_UI_16"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_MUM_UI_16 InputFieldLabel" id="LAB606_2L" title="Enter the performing laboratory type">
Performing Lab Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB606)" styleId="LAB606_2" title="Enter the performing laboratory type" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16');enableOrDisableOther('LAB606');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_VPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Enter the performing laboratory type" id="LAB606_2OthL">Other Performing Lab Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB606Oth)" size="40" maxlength="40" title="Other Enter the performing laboratory type" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_16')" styleId="LAB606_2Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_16">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_NBS_INV_MUM_UI_16BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_16_2','patternNBS_UI_NBS_INV_MUM_UI_16_2','questionbodyNBS_UI_NBS_INV_MUM_UI_16_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_16_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_NBS_INV_MUM_UI_16BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_16_2','patternNBS_UI_NBS_INV_MUM_UI_16_2','questionbodyNBS_UI_NBS_INV_MUM_UI_16_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_NBS_INV_MUM_UI_16_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_NBS_INV_MUM_UI_16BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_16_2','patternNBS_UI_NBS_INV_MUM_UI_16_2','questionbodyNBS_UI_NBS_INV_MUM_UI_16_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_NBS_INV_MUM_UI_16"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_NBS_INV_MUM_UI_16_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_17_2" name="Vaccine Preventable Disease (VPD) Lab Message Linkage" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_NBS_INV_MUM_UI_17_2errorMessages">
<b> <a name="NBS_UI_NBS_INV_MUM_UI_17_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_NBS_INV_MUM_UI_17_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_17_2">
<tr id="patternNBS_UI_NBS_INV_MUM_UI_17_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_NBS_INV_MUM_UI_17_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_NBS_INV_MUM_UI_17_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_NBS_INV_MUM_UI_17_2">
<tr id="nopatternNBS_UI_NBS_INV_MUM_UI_17_2" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_NBS_INV_MUM_UI_17_2 InputFieldLabel" id="LAB143_2L" title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message">
VPD Lab Message Reference Laboratory:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB143)" styleId="LAB143_2" title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message" onchange="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_17');">
<nedss:optionsCollection property="codedValue(VPD_LAB_REFERENCE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB598_2L" title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message">
VPD Lab Message Patient Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB598)"  maxlength="25" title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message" styleId="LAB598_2" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_17');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB125_2L" title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message">
VPD Lab Message Specimen Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB125)"  maxlength="25" title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message" styleId="LAB125_2" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_MUM_UI_17');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_17">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_NBS_INV_MUM_UI_17BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_17_2','patternNBS_UI_NBS_INV_MUM_UI_17_2','questionbodyNBS_UI_NBS_INV_MUM_UI_17_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_MUM_UI_17_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_NBS_INV_MUM_UI_17BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_17_2','patternNBS_UI_NBS_INV_MUM_UI_17_2','questionbodyNBS_UI_NBS_INV_MUM_UI_17_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_NBS_INV_MUM_UI_17_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_NBS_INV_MUM_UI_17BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_MUM_UI_17_2','patternNBS_UI_NBS_INV_MUM_UI_17_2','questionbodyNBS_UI_NBS_INV_MUM_UI_17_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_NBS_INV_MUM_UI_17"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_NBS_INV_MUM_UI_17_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_19_2" name="Disease Transmission" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM225_2L" title="Indicate the patients length of time in the U.S. since the last travel">
Length of time in the U.S. since last travel:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(DEM225)" size="3" maxlength="3"  title="Indicate the patients length of time in the U.S. since the last travel" styleId="DEM225_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,364)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO2.answer(DEM225Unit)" styleId="DEM225UNIT_2">
<nedss:optionsCollection property="codedValue(DUR_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL05_2L" title="List any international destinations of recent travel">
International Destination(s) of Recent Travel:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(TRAVEL05)" styleId="TRAVEL05_2" title="List any international destinations of recent travel"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'TRAVEL05_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
<div id="TRAVEL05_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL08_2L" title="Date of return from most recent travel">
Date of Return from Travel:</span>
</td>
<td>
<html:text name="PageForm" title="Date of return from most recent travel"  property="pageClientVO2.answer(TRAVEL08)" maxlength="10" size="10" styleId="TRAVEL08_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV224_2L" title="What was the transmission setting where the condition was acquired?">
Transmission Setting:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV224)" styleId="INV224_2" title="What was the transmission setting where the condition was acquired?" onchange="enableOrDisableOther('INV224');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TRAN_SETNG)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What was the transmission setting where the condition was acquired?" id="INV224_2OthL">Other Transmission Setting:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV224Oth)" size="40" maxlength="40" title="Other What was the transmission setting where the condition was acquired?" styleId="INV224_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV216_2L" title="Does the age of the case match or make sense for the transmission setting listed?">
Were age and setting verified?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV216)" styleId="INV216_2" title="Does the age of the case match or make sense for the transmission setting listed?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="Enter State ID if source was an in-state case; enter Country if source was out of U.S.; enter State if source was out-of-sate." id="NBS341_2L">
LEGACY Source of Infection (Exposure) for Current Case:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS341)" maxlength="100" title="Enter State ID if source was an in-state case; enter Country if source was out of U.S.; enter State if source was out-of-sate." styleId="NBS341"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV293_2L" title="Did parotitis or other mumps-associated complication onset occur within 12-25 days of entering the U.S., following any travel or living outside U.S.? (Import Status)">
Symptom onset 12-25 days of entering U.S., following travel/living outside U.S.? (Import Status):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV293)" styleId="INV293_2" title="Did parotitis or other mumps-associated complication onset occur within 12-25 days of entering the U.S., following any travel or living outside U.S.? (Import Status)">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV516_2L" title="If this is a U.S.-acquired case, how should the case be classified by source?">
If this is a U.S.-acquired case, how should the case be classified by source? (Import Status):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV516)" styleId="INV516_2" title="If this is a U.S.-acquired case, how should the case be classified by source?" onchange="enableOrDisableOther('INV516');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_CASECLASSIFICATIONEXPOSURESOURCE_NND)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If this is a U.S.-acquired case, how should the case be classified by source?" id="INV516_2OthL">Other If this is a U.S.-acquired case, how should the case be classified by source? (Import Status):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV516Oth)" size="40" maxlength="40" title="Other If this is a U.S.-acquired case, how should the case be classified by source?" styleId="INV516_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV217_2L" title="Is this case epi-linked to another confirmed or probable case?">
Is this case epi-linked to another confirmed or probable case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV217)" styleId="INV217_2" title="Is this case epi-linked to another confirmed or probable case?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_MUM_UI_22_2" name="Vaccination Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126_2L" title="Did patient ever receive mumps-containing vaccine?">
Did patient ever receive mumps-containing vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC126)" styleId="VAC126_2" title="Did patient ever receive mumps-containing vaccine?" onchange="ruleEnDisVAC1268634();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;For the next 2 questions, to indicate that the number of vaccine doses is unknown, enter 99.</span></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC129_2L" title="Indicate the number of vaccine doses against mumps the patient received on or after his or her first birthday. To indicate that the number of doses is unknown, enter 99.">
Number of doses received ON or AFTER first birthday:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(VAC129)" size="2" maxlength="2"  title="Indicate the number of vaccine doses against mumps the patient received on or after his or her first birthday. To indicate that the number of doses is unknown, enter 99." styleId="VAC129_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,99)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC140_2L" title="Indicate the number of vaccine doses against mumps the patient received prior to illness onset. To indicate that the number of doses is unknown, enter 99.">
Number of vaccine doses against mumps received prior to illness onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(VAC140)" size="2" maxlength="2"  title="Indicate the number of vaccine doses against mumps the patient received prior to illness onset. To indicate that the number of doses is unknown, enter 99." styleId="VAC140_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,99)"/>
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
<html:select name="PageForm" property="pageClientVO2.answer(VAC148)" styleId="VAC148_2" title="This data element is used for all cases. For example, a case might not have received a vaccine because they were too young per ACIP schedules." onchange="ruleHideUnhVAC1488642();enableOrDisableOther('VAC149');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC149_2L" title="Indicate the reason the patient was not vaccinated as recommended by ACIP.">
Reason patient not vaccinated per ACIP recommendations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(VAC149)" styleId="VAC149_2" title="Indicate the reason the patient was not vaccinated as recommended by ACIP." onchange="enableOrDisableOther('VAC149');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_VAC_NOTG_RSN)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the reason the patient was not vaccinated as recommended by ACIP." id="VAC149_2OthL">Other Reason patient not vaccinated per ACIP recommendations:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(VAC149Oth)" size="40" maxlength="40" title="Other Indicate the reason the patient was not vaccinated as recommended by ACIP." styleId="VAC149_2Oth"/></td></tr>

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
