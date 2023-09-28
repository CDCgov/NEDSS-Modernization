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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Signs and Symptoms","Laboratory","Exposure","Contact Investigation","Retired Data Elements"};
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
<html:select name="PageForm" property="pageClientVO2.answer(DEM113)" styleId="DEM113_2" title="Patient's current sex." onchange="ruleEnDisDEM1138545();ruleHideUnhINV1788552();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(DEM127)" styleId="DEM127_2" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1278542();pgSelectNextFocus(this);">
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
<nedss:container id="NBS_UI_32_2" name="Occupation and Industry Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_32_2errorMessages">
<b> <a name="NBS_UI_32_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_32_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_32_2">
<tr id="patternNBS_UI_32_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_32_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_32_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_32_2">
<tr id="nopatternNBS_UI_32_2" class="odd" style="display:">
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
<span class="NBS_UI_32 InputFieldLabel" id="85659_1_2L" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation.">
Current Occupation Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85659_1)" styleId="85659_1_2" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation." onchange="unhideBatchImg('NBS_UI_32');">
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
<span class="NBS_UI_32 InputFieldLabel" id="85657_5_2L" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry.">
Current  Industry Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(85657_5)" styleId="85657_5_2" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry." onchange="unhideBatchImg('NBS_UI_32');">
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
<tr id="AddButtonToggleNBS_UI_32">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_32BatchAddFunction()) writeQuestion('NBS_UI_32_2','patternNBS_UI_32_2','questionbodyNBS_UI_32_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_32_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_32BatchAddFunction()) writeQuestion('NBS_UI_32_2','patternNBS_UI_32_2','questionbodyNBS_UI_32_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_32_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_32BatchAddFunction()) writeQuestion('NBS_UI_32_2','patternNBS_UI_32_2','questionbodyNBS_UI_32_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_32"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_32_2')"/>&nbsp;	&nbsp;&nbsp;
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
<html:select name="PageForm" property="pageClientVO2.answer(INV128)" styleId="INV128_2" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288540();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328546();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV178)" styleId="INV178_2" title="Assesses whether or not the patient is pregnant." onchange="ruleHideUnhINV1788552();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV145)" styleId="INV145_2" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1458541();pgSelectNextFocus(this);">
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
<span class=" InputFieldLabel" id="INV217_2L" title="Is this case epi-linked to another confirmed or probable case?">
Is this case epi-linked to another confirmed or probable case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV217)" styleId="INV217_2" title="Is this case epi-linked to another confirmed or probable case?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

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
<html:select name="PageForm" property="pageClientVO2.answer(INV150)" styleId="INV150_2" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1508543();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV152)" styleId="INV152_2" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1528544();pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(INV502)" styleId="INV502_2" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');ruleEnDisINV5028550();getDWRStatesByCountry(this, 'INV503');pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO2.answer(NOT120)" styleId="NOT120_2" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1208551();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NOT120SPEC_2L" title="This field is for local use to describe any phone contact with CDC regarding this Immediate National Notifiable Condition.">
If yes, describe:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NOT120SPEC)" maxlength="100" title="This field is for local use to describe any phone contact with CDC regarding this Immediate National Notifiable Condition." styleId="NOT120SPEC"/>
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
<nedss:container id="NBS_UI_35_2" name="Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386789004_2L" title="Did the patient have eosinophilia?">
Eosinophilia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(386789004)" styleId="386789004_2" title="Did the patient have eosinophilia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006_2L" title="Did the patient have a fever?">
Fever:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(386661006)" styleId="386661006_2" title="Did the patient have a fever?" onchange="ruleEnDis3866610068560();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202_2L" title="What was the patient's highest measured temperature during this illness?">
Highest Measured Temperature:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(INV202)" size="10" maxlength="10"  title="What was the patient's highest measured temperature during this illness?" styleId="INV202_2" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this);pgCheckFieldMinMax(this,30,110)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO2.answer(INV202Unit)" styleId="INV202UNIT_2">
<nedss:optionsCollection property="codedValue(PHVS_TEMP_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001_2L" title="Did the patient have myalgia or muscle pain?">
Myalgia/Muscle Pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(68962001)" styleId="68962001_2" title="Did the patient have myalgia or muscle pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49563000_2L" title="Did the patient have periorbital edema?">
Periorbital Edema:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(49563000)" styleId="49563000_2" title="Did the patient have periorbital edema?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338_2L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other Symptom(s):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS338)" styleId="NBS338_2" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleEnDisNBS3388559();pgSelectNextFocus(this);">
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
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42_2" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740_2L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV740)" styleId="INV740_2" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408554();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_43_2" name="Interpretative Lab Data Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_43_2errorMessages">
<b> <a name="NBS_UI_43_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_43_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_43_2">
<tr id="patternNBS_UI_43_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_43_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_43_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_43_2">
<tr id="nopatternNBS_UI_43_2" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_43_2 InputFieldLabel" id="INV290_2L" title="Epidemiologic interpretation of the type of test(s) performed for this case">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV290)" styleId="INV290_2" title="Epidemiologic interpretation of the type of test(s) performed for this case" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290_2OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV290Oth)" size="40" maxlength="40" title="Other Epidemiologic interpretation of the type of test(s) performed for this case" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="INV290_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="INV291_2L" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g, positive, detected, negative)">
Test Result Qualitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV291)" styleId="INV291_2" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g, positive, detected, negative)" onchange="unhideBatchImg('NBS_UI_43');">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTINTERPRETATION_TRICH)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628_2L" title="Quantitative Test Result Value">
Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB628)"  maxlength="10" title="Quantitative Test Result Value" styleId="LAB628_2" onkeyup="unhideBatchImg('NBS_UI_43');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="667469_2L" title="This indicates the type of specimen tested">
Specimen Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(667469)" styleId="667469_2" title="This indicates the type of specimen tested" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('667469');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="This indicates the type of specimen tested" id="667469_2OthL">Other Specimen Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(667469Oth)" size="40" maxlength="40" title="Other This indicates the type of specimen tested" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="667469_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163_2L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date"  property="pageClientVO2.answer(LAB163)" maxlength="10" size="10" styleId="LAB163_2" onkeyup="unhideBatchImg('NBS_UI_43');DateMask(this,null,event)" styleClass="NBS_UI_43"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB217_2L" title="The date the specimen/isolate was tested. Time of analysis is an optional addition to date.">
Specimen Analyzed Date:</span>
</td>
<td>
<html:text name="PageForm" title="The date the specimen/isolate was tested. Time of analysis is an optional addition to date."  property="pageClientVO2.answer(LAB217)" maxlength="10" size="10" styleId="LAB217_2" onkeyup="unhideBatchImg('NBS_UI_43');DateMask(this,null,event)" styleClass="NBS_UI_43"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="LAB606_2L" title="Enter the performing laboratory type">
Performing Laboratory Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB606)" styleId="LAB606_2" title="Enter the performing laboratory type" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('LAB606');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Enter the performing laboratory type" id="LAB606_2OthL">Other Performing Laboratory Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB606Oth)" size="40" maxlength="40" title="Other Enter the performing laboratory type" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="LAB606_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="NBS212_2L" title="If the specimen was sent for strain identification, indicate the strain">
Strain Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS212)" styleId="NBS212_2" title="If the specimen was sent for strain identification, indicate the strain" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('NBS212');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_STRAINTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If the specimen was sent for strain identification, indicate the strain" id="NBS212_2OthL">Other Strain Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(NBS212Oth)" size="40" maxlength="40" title="Other If the specimen was sent for strain identification, indicate the strain" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="NBS212_2Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_43">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43_2','patternNBS_UI_43_2','questionbodyNBS_UI_43_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_43_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43_2','patternNBS_UI_43_2','questionbodyNBS_UI_43_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_43_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43_2','patternNBS_UI_43_2','questionbodyNBS_UI_43_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_43"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_43_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44_2" name="Eosinophilia Lab" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB711_2L" title="Was testing performed for eosinophilia?">
Was testing performed for eosinophilia?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB711)" styleId="LAB711_2" title="Was testing performed for eosinophilia?" onchange="ruleEnDisLAB7118555();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="LAB665_2L" title="Eosinophil value (absolute number or percentage)">
Eosinophil value (absolute number or percentage):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(LAB665)" size="10" maxlength="10"  title="Eosinophil value (absolute number or percentage)" styleId="LAB665_2" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO2.answer(LAB665Unit)" styleId="LAB665UNIT_2">
<nedss:optionsCollection property="codedValue(PHVS_EOSINOPHILUNITS_CDC_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37_2" name="Food Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 8 WEEKS before symptom onset/diagnosis:</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS406_2L" title="Did the patient consume meat suspected of making the patient ill?">
Did the patient consume meat item(s) suspected of making the patient ill?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS406)" styleId="NBS406_2" title="Did the patient consume meat suspected of making the patient ill?" onchange="ruleEnDisNBS4068553();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_38_2" name="Food Exposure History" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_38_2errorMessages">
<b> <a name="NBS_UI_38_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_38_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_38_2">
<tr id="patternNBS_UI_38_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_38_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_38_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_38_2">
<tr id="nopatternNBS_UI_38_2" class="odd" style="display:">
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

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Specify all the information for suspected meat consumed:</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV1009_2L" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill">
Meat Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV1009)" styleId="INV1009_2" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV1009');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_MEATCONSUMEDTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" id="INV1009_2OthL">Other Meat Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV1009Oth)" size="40" maxlength="40" title="Other Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV1009_2Oth"/></td></tr>

<!--processing ReadOnly Textbox Text Question-->
<tr> <td class="fieldName">
<span title="Other Meat Type" id="NBS528_2L">
(Legacy) Other Meat Type:</span>
</td>
<td>
<span id="NBS528S_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS528)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO2.answer(NBS528)" styleId="NBS528_2" />
</td>
</tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV967_2L" title="Date suspected meat was eaten">
Consumed Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date suspected meat was eaten"  property="pageClientVO2.answer(INV967)" maxlength="10" size="10" styleId="INV967_2" onkeyup="unhideBatchImg('NBS_UI_38');DateMask(this,null,event)" styleClass="NBS_UI_38"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV969_2L" title="Where was the suspected meat obtained?">
Meat Obtained:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV969)" styleId="INV969_2" title="Where was the suspected meat obtained?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV969');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_MEATPURCHASEINFO_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspected meat obtained?" id="INV969_2OthL">Other Meat Obtained:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV969Oth)" size="40" maxlength="40" title="Other Where was the suspected meat obtained?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV969_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV968_2L" title="Meat preparation (further food processing)">
Method of Meat Preparation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV968)" styleId="INV968_2" title="Meat preparation (further food processing)" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV968');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_FOODPROCESSINGMETHOD_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Meat preparation (further food processing)" id="INV968_2OthL">Other Method of Meat Preparation:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV968Oth)" size="40" maxlength="40" title="Other Meat preparation (further food processing)" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV968_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV970_2L" title="What method of cooking was used on suspected meat?">
Method of Cooking:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV970)" styleId="INV970_2" title="What method of cooking was used on suspected meat?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV970');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_FOODCOOKINGMETHOD_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What method of cooking was used on suspected meat?" id="INV970_2OthL">Other Method of Cooking:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV970Oth)" size="40" maxlength="40" title="Other What method of cooking was used on suspected meat?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV970_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV971_2L" title="Was larva observed in suspected meat by microscopy?">
Larva Present in Meat:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV971)" styleId="INV971_2" title="Was larva observed in suspected meat by microscopy?" onchange="unhideBatchImg('NBS_UI_38');ruleEnDisINV9718561();enableOrDisableOther('INV972');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PRESENTABSENTUNKNOTEXAMINED_CDC_TRICH)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV972_2L" title="Where was the suspected meat tested?">
Where was the Meat Tested:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV972)" styleId="INV972_2" title="Where was the suspected meat tested?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV972');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspected meat tested?" id="INV972_2OthL">Other Where was the Meat Tested:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV972Oth)" size="40" maxlength="40" title="Other Where was the suspected meat tested?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV972_2Oth"/></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV973_2L" title="Use this field, if needed, to communicate anything unusual about the suspect meat, which is not already covered with the other data elements (e.g., additional details about where eaten, if consumed while traveling outside of the U.S., where wild game was hunted, if meat was stored frozen and/or if leftovers are available for testing, etc.)">
Suspect Meat Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(INV973)" styleId ="INV973_2" onkeyup="checkTextAreaLength(this, 199)" title="Use this field, if needed, to communicate anything unusual about the suspect meat, which is not already covered with the other data elements (e.g., additional details about where eaten, if consumed while traveling outside of the U.S., where wild game was hunted, if meat was stored frozen and/or if leftovers are available for testing, etc.)"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_38">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38_2','patternNBS_UI_38_2','questionbodyNBS_UI_38_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_38_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38_2','patternNBS_UI_38_2','questionbodyNBS_UI_38_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_38_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38_2','patternNBS_UI_38_2','questionbodyNBS_UI_38_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_38"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_38_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39_2" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL02_2L" title="In the 8 weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their state or country of residence?">
In 8 weeks prior to illness, did patient travel outside his/her county/state/country of residence?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(TRAVEL02)" styleId="TRAVEL02_2" title="In the 8 weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their state or country of residence?" onchange="ruleEnDisTRAVEL028558();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40_2" name="Travel History Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_40_2errorMessages">
<b> <a name="NBS_UI_40_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_40_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_40_2">
<tr id="patternNBS_UI_40_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_40_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_UI_40_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_UI_40_2">
<tr id="nopatternNBS_UI_40_2" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_40_2 InputFieldLabel" id="NBS454_2L" title="Domestic vs International Travel">
Was the travel domestic or international?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS454)" styleId="NBS454_2" title="Domestic vs International Travel" onchange="unhideBatchImg('NBS_UI_40');ruleEnDisNBS4548557();ruleEnDisNBS4548556();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVSFB_DOMINTNL)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="TRAVEL05_2L" title="List any international destinations of recent travel">
Country of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(TRAVEL05)" styleId="TRAVEL05_2" title="List any international destinations of recent travel" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="82754_3_2L" title="Domestic destination, states traveled">
State of Travel:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO2.answer(82754_3)" styleId="82754_3_2" title="Domestic destination, states traveled">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="82753_5_2L" title="Intrastate destination, counties traveled">
County of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(82753_5)" styleId="82753_5_2" title="Intrastate destination, counties traveled" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PHVS_COUNTY_FIPS_6-4)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="NBS453_2L" title="Choose the mode of travel">
Travel Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS453)" styleId="NBS453_2" title="Choose the mode of travel" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELMODE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="TRAVEL16_2L" title="Principal Reason for Travel">
Principal Reason for Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(TRAVEL16)" styleId="TRAVEL16_2" title="Principal Reason for Travel" onchange="unhideBatchImg('NBS_UI_40');enableOrDisableOther('TRAVEL16');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELREASON_MALARIA)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Principal Reason for Travel" id="TRAVEL16_2OthL">Other Principal Reason for Travel:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(TRAVEL16Oth)" size="40" maxlength="40" title="Other Principal Reason for Travel" onkeyup="unhideBatchImg('NBS_UI_40')" styleId="TRAVEL16_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL06_2L" title="If the patient traveled, when did they arrive to their travel destination?">
Date of Arrival at Destination:</span>
</td>
<td>
<html:text name="PageForm" title="If the patient traveled, when did they arrive to their travel destination?"  property="pageClientVO2.answer(TRAVEL06)" maxlength="10" size="10" styleId="TRAVEL06_2" onkeyup="unhideBatchImg('NBS_UI_40');DateMask(this,null,event)" styleClass="NBS_UI_40"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL07_2L" title="If the patient traveled, when did they depart from their travel destination?">
Date of Departure from Destination:</span>
</td>
<td>
<html:text name="PageForm" title="If the patient traveled, when did they depart from their travel destination?"  property="pageClientVO2.answer(TRAVEL07)" maxlength="10" size="10" styleId="TRAVEL07_2" onkeyup="unhideBatchImg('NBS_UI_40');DateMask(this,null,event)" styleClass="NBS_UI_40"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82310_4_2L" title="Duration of Stay">
Duration of Stay:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(82310_4)" size="3" maxlength="3"  title="Duration of Stay" styleId="82310_4_2" onkeyup="unhideBatchImg('NBS_UI_40');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_40"/>
<html:select name="PageForm" property="pageClientVO2.answer(82310_4Unit)" styleId="82310_4UNIT_2" onchange="unhideBatchImg('NBS_UI_40')">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC_1)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="TRAVEL23_2L" title="Additional Travel Information">
Additional Travel Information:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(TRAVEL23)" styleId ="TRAVEL23_2" onkeyup="checkTextAreaLength(this, 2000)" title="Additional Travel Information"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_40">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40_2','patternNBS_UI_40_2','questionbodyNBS_UI_40_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_40_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40_2','patternNBS_UI_40_2','questionbodyNBS_UI_40_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_40_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40_2','patternNBS_UI_40_2','questionbodyNBS_UI_40_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_40"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_UI_40_2')"/>&nbsp;	&nbsp;&nbsp;
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
<nedss:container id="NBS_UI_GA101000_2" name="Legacy Animal Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV667_2L" title="Did the case have contact with an animal?">
(Legacy) Did the case have contact with an animal?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV667)" styleId="INV667_2" title="Did the case have contact with an animal?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_32_2L" title="Animal contact by type of animal">
(Legacy) Animal Type:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(FDD_Q_32)" styleId="FDD_Q_32_2" title="Animal contact by type of animal"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_32_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_ANIMALST)" value="key" label="value" /> </html:select>
<div id="FDD_Q_32_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_243_2L" title="If Other, please specify other type of animal">
(Legacy) If Other, please specify other type of animal:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_243)" maxlength="50" title="If Other, please specify other type of animal" styleId="FDD_Q_243"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_295_2L" title="If Other Amphibian, please specify other type of amphibian">
(Legacy) If Other Amphibian, please specify other type of amphibian:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_295)" maxlength="50" title="If Other Amphibian, please specify other type of amphibian" styleId="FDD_Q_295"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_296_2L" title="If Other Reptile, please specify other type of reptile">
(Legacy) If Other Reptile, please specify other type of reptile:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_296)" maxlength="50" title="If Other Reptile, please specify other type of reptile" styleId="FDD_Q_296"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_374_2L" title="If Other Mammal, please specify other type of mammal">
(Legacy) If Other Mammal, please specify other type of mammal:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_374)" maxlength="50" title="If Other Mammal, please specify other type of mammal" styleId="FDD_Q_374"/>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_33_2L" title="What was the name of the farm, ranch, petting zoo, or other setting that has farm animals?">
(Legacy) Animal Contact Location:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(FDD_Q_33)" styleId ="FDD_Q_33_2" onkeyup="checkTextAreaLength(this, 200)" title="What was the name of the farm, ranch, petting zoo, or other setting that has farm animals?"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_34_2L" title="Did the patient acquire a pet prior to onset of illness?">
(Legacy) Did the patient acquire a pet prior to onset of illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(FDD_Q_34)" styleId="FDD_Q_34_2" title="Did the patient acquire a pet prior to onset of illness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_244_2L" title="Applicable incubation period for this illness">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_244)" maxlength="50" title="Applicable incubation period for this illness" styleId="FDD_Q_244"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101001_2" name="Legacy Underlying Conditions" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_233_2L" title="Did patient have any of the following underlying conditions?">
(Legacy) Did patient have any of the following underlying conditions?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(FDD_Q_233)" styleId="FDD_Q_233_2" title="Did patient have any of the following underlying conditions?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_233_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_DISEASES)" value="key" label="value" /> </html:select>
<div id="FDD_Q_233_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_234_2L" title="If Other Prior Illness, please specify">
(Legacy) If Other Prior Illness, please specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_234)" maxlength="50" title="If Other Prior Illness, please specify" styleId="FDD_Q_234"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_235_2L" title="If Diabetes Mellitus, specify whether on insulin">
(Legacy) If Diabetes Mellitus, specify whether on insulin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(FDD_Q_235)" styleId="FDD_Q_235_2" title="If Diabetes Mellitus, specify whether on insulin">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_237_2L" title="If Gastric Surgery, please specify type">
(Legacy) If Gastric Surgery, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_237)" maxlength="50" title="If Gastric Surgery, please specify type" styleId="FDD_Q_237"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_238_2L" title="If Hematologic Disease, please specify type">
(Legacy) If Hematologic Disease, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_238)" maxlength="50" title="If Hematologic Disease, please specify type" styleId="FDD_Q_238"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_239_2L" title="If Immunodeficiency, please specify type:">
(Legacy) If Immunodeficiency, please specify type::</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_239)" maxlength="50" title="If Immunodeficiency, please specify type:" styleId="FDD_Q_239"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_240_2L" title="If Other Liver Disease, please specify type">
(Legacy) If Other Liver Disease, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_240)" maxlength="50" title="If Other Liver Disease, please specify type" styleId="FDD_Q_240"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_241_2L" title="If Other Malignancy, please specify type">
(Legacy) If Other Malignancy, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_241)" maxlength="50" title="If Other Malignancy, please specify type" styleId="FDD_Q_241"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_242_2L" title="If Other Renal Disease, please specify">
(Legacy) If Other Renal Disease, please specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_242)" maxlength="50" title="If Other Renal Disease, please specify" styleId="FDD_Q_242"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_236_2L" title="If Organ Transplant, please specify organ">
(Legacy) If Organ Transplant, please specify organ:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_236)" maxlength="50" title="If Organ Transplant, please specify organ" styleId="FDD_Q_236"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101002_2" name="Legacy Related Cases" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_77_2L" title="Does the patient know of other similarly ill persons?">
(Legacy) Does the patient know of other similarly ill persons?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(FDD_Q_77)" styleId="FDD_Q_77_2" title="Does the patient know of other similarly ill persons?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_78_2L" title="If Yes, did the health department collect contact information about other similarly ill persons and investigate further?">
(Legacy) If Yes, did the health department collect contact information about other similarly ill persons and:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(FDD_Q_78)" styleId="FDD_Q_78_2" title="If Yes, did the health department collect contact information about other similarly ill persons and investigate further?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_79_2L" title="Are there other cases related to this one?">
(Legacy) Are there other cases related to this one?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(FDD_Q_79)" styleId="FDD_Q_79_2" title="Are there other cases related to this one?">
<nedss:optionsCollection property="codedValue(PHVSFB_EPIDEMGY)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102002_2" name="Legacy Suspect Food" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_139_2L" title="What suspect foods did the patient eat?">
(Legacy) What suspect foods did the patient eat?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(FDD_Q_139)" styleId="FDD_Q_139_2" title="What suspect foods did the patient eat?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_139_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_PORKONOT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_139_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_144_2L" title="Where was the suspect (Pork) meat obtained?">
(Legacy) Where was the suspect (Pork) meat obtained?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(FDD_Q_144)" styleId="FDD_Q_144_2" title="Where was the suspect (Pork) meat obtained?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_144_2-selectedValues');enableOrDisableOther('FDD_Q_144')" >
<nedss:optionsCollection property="codedValue(PHVSFB_SOURCEMT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_144_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspect (Pork) meat obtained?" id="FDD_Q_144_2OthL">Other (Legacy) Where was the suspect (Pork) meat obtained?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(FDD_Q_144Oth)" size="40" maxlength="40" title="Other Where was the suspect (Pork) meat obtained?" styleId="FDD_Q_144_2Oth"/></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_154_2L" title="Where was the suspect (Non-Pork) meat obtained?">
(Legacy) Where was the suspect (Non-Pork) meat obtained?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(FDD_Q_154)" styleId="FDD_Q_154_2" title="Where was the suspect (Non-Pork) meat obtained?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_154_2-selectedValues');enableOrDisableOther('FDD_Q_154')" >
<nedss:optionsCollection property="codedValue(PHVSFB_SOURCEMT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_154_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspect (Non-Pork) meat obtained?" id="FDD_Q_154_2OthL">Other (Legacy) Where was the suspect (Non-Pork) meat obtained?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(FDD_Q_154Oth)" size="40" maxlength="40" title="Other Where was the suspect (Non-Pork) meat obtained?" styleId="FDD_Q_154_2Oth"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102000_2" name="Legacy Food Handler" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_8_2L" title="Did patient work as a food handler after onset of illness?">
(Legacy) Did patient work as a food handler after onset of illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(FDD_Q_8)" styleId="FDD_Q_8_2" title="Did patient work as a food handler after onset of illness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="FDD_Q_9_2L" title="What was last date worked as a food handler after onset of illness?">
(Legacy) What was last date worked as a food handler after onset of illness?:</span>
</td>
<td>
<html:text name="PageForm" title="What was last date worked as a food handler after onset of illness?"  property="pageClientVO2.answer(FDD_Q_9)" maxlength="10" size="10" styleId="FDD_Q_9_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_10_2L" title="Where was patient a food handler?">
(Legacy) Where was patient a food handler?:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_10)" maxlength="50" title="Where was patient a food handler?" styleId="FDD_Q_10"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102001_2" name="Legacy Travel History" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_12_2L" title="Applicable incubation period for this illness">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_12)" maxlength="50" title="Applicable incubation period for this illness" styleId="FDD_Q_12"/>
</td> </tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_13_2L" title="What was the purpose of the travel?">
(Legacy) What was the purpose of the travel?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(FDD_Q_13)" styleId="FDD_Q_13_2" title="What was the purpose of the travel?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_13_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_TRAVELTT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_13_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_14_2L" title="If Other, please specify other purpose of travel">
(Legacy) If Other, please specify other purpose of travel:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_14)" maxlength="50" title="If Other, please specify other purpose of travel" styleId="FDD_Q_14"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_20_2L" title="If more than 3 destinations, specify details here">
(Legacy) If more than 3 destinations, specify details here:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(FDD_Q_20)" maxlength="50" title="If more than 3 destinations, specify details here" styleId="FDD_Q_20"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
