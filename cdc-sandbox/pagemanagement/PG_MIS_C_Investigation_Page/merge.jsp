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
Map map = new HashMap();
if(request.getAttribute("SubSecStructureMap") != null){
map =(Map)request.getAttribute("SubSecStructureMap");
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
<div style="float:left;width:100% ">    <%@ include file="/pagemanagement/patient/PatientSummaryCompare1.jsp" %> </div>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_6" name="General Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="
requiredInputField
InputFieldLabel" id="NBS104L" title="As of Date is the last known date for which the information is valid.">
Information As of Date:</span>
</td>
<td>
<html:text  name="PageForm" styleClass="requiredInputField" property="pageClientVO.answer(NBS104)" maxlength="10" size="10" styleId="NBS104" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS104','NBS104Icon'); return false;" styleId="NBS104Icon" onkeypress="showCalendarEnterKey('NBS104','NBS104Icon',event)"></html:img>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM196L" title="General comments pertaining to the patient.">
Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(DEM196)" styleId ="DEM196" onkeyup="checkTextAreaLength(this, 2000)" title="General comments pertaining to the patient."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_7" name="Name Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS095L" title="As of Date is the last known date for which the information is valid.">
Name Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS095)" maxlength="10" size="10" styleId="NBS095" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS095','NBS095Icon'); return false;" styleId="NBS095Icon" onkeypress="showCalendarEnterKey('NBS095','NBS095Icon',event)"></html:img>
</td> </tr>
</logic:notEqual>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM104L" title="The patient's first name.">
First Name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM104)" maxlength="50" title="The patient's first name." styleId="DEM104"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM105L" title="The patient's middle name or initial.">
Middle Name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM105)" maxlength="50" title="The patient's middle name or initial." styleId="DEM105"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM102L" title="The patient's last name.">
Last Name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM102)" maxlength="50" title="The patient's last name." styleId="DEM102"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM107L" title="The patient's name suffix">
Suffix:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM107)" styleId="DEM107" title="The patient's name suffix">
<nedss:optionsCollection property="codedValue(P_NM_SFX)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_8" name="Other Personal Details" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS096L" title="As of Date is the last known date for which the information is valid.">
Other Personal Details As Of Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS096)" maxlength="10" size="10" styleId="NBS096" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS096','NBS096Icon'); return false;" styleId="NBS096Icon" onkeypress="showCalendarEnterKey('NBS096','NBS096Icon',event)"></html:img>
</td> </tr>
</logic:notEqual>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="DEM115L" title="Reported date of birth of patient.">
Date of Birth:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(DEM115)" maxlength="10" size="10" styleId="DEM115" title="Reported date of birth of patient." onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateReportedAge('DEM115','INV2001','INV2002','NBS096','NBS104')" onchange="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateReportedAge('DEM115','INV2001','INV2002','NBS096','NBS104')"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM115','DEM115Icon'); return false;" styleId="DEM115Icon" onkeypress="showCalendarEnterKey('DEM115','DEM115Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV2001L" title="The patient's age reported at the time of interview.">
Reported Age:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV2001)" size="3" maxlength="3"  title="The patient's age reported at the time of interview." styleId="INV2001" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,150)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV2002L" title="Patient's age units">
Reported Age Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV2002)" styleId="INV2002" title="Patient's age units">
<nedss:optionsCollection property="codedValue(AGE_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM126L" title="Country of Birth">
Country of Birth:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM126)" styleId="DEM126" title="Country of Birth">
<nedss:optionsCollection property="codedValue(PHVS_BIRTHCOUNTRY_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM113L" title="Patient's current sex.">
Current Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM113)" styleId="DEM113" title="Patient's current sex." onchange="ruleEnDisDEM1138738();ruleHideUnhINV1788745();">
<nedss:optionsCollection property="codedValue(SEX)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS097L" title="As of Date is the last known date for which the information is valid.">
Mortality Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS097)" maxlength="10" size="10" styleId="NBS097" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS097','NBS097Icon'); return false;" styleId="NBS097Icon" onkeypress="showCalendarEnterKey('NBS097','NBS097Icon',event)"></html:img>
</td> </tr>
</logic:notEqual>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM127L" title="Indicator of whether or not a patient is alive or dead.">
Is the patient deceased?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM127)" styleId="DEM127" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1278735();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="DEM128L" title="Date on which the individual died.">
Deceased Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(DEM128)" maxlength="10" size="10" styleId="DEM128" title="Date on which the individual died." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('DEM128','DEM128Icon'); return false;" styleId="DEM128Icon" onkeypress="showCalendarEnterKey('DEM128','DEM128Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS098L" title="As of Date is the last known date for which the information is valid.">
Marital Status As Of Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS098)" maxlength="10" size="10" styleId="NBS098" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS098','NBS098Icon'); return false;" styleId="NBS098Icon" onkeypress="showCalendarEnterKey('NBS098','NBS098Icon',event)"></html:img>
</td> </tr>
</logic:notEqual>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM140L" title="A code indicating the married or similar partnership status of a patient.">
Marital Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM140)" styleId="DEM140" title="A code indicating the married or similar partnership status of a patient.">
<nedss:optionsCollection property="codedValue(P_MARITAL)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_15" name="Reporting Address for Case Counting" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS102L" title="As of Date is the last known date for which the information is valid.">
Address Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS102)" maxlength="10" size="10" styleId="NBS102" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS102','NBS102Icon'); return false;" styleId="NBS102Icon" onkeypress="showCalendarEnterKey('NBS102','NBS102Icon',event)"></html:img>
</td> </tr>
</logic:notEqual>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM159L" title="Line one of the address label.">
Street Address 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM159)" maxlength="50" title="Line one of the address label." styleId="DEM159"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM160L" title="Line two of the address label.">
Street Address 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM160)" maxlength="50" title="Line two of the address label." styleId="DEM160"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM161L" title="The city for a postal location.">
City:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM161)" maxlength="50" title="The city for a postal location." styleId="DEM161"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM162L" title="The state code for a postal location.">
State:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(DEM162)" styleId="DEM162" title="The state code for a postal location." onchange="getDWRCounties(this, 'DEM165');getDWRCitites(this)">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM163L" title="The zip code of a residence of the case patient or entity.">
Zip:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM163)" maxlength="10" title="The zip code of a residence of the case patient or entity." styleId="DEM163" onkeyup="ZipMask(this,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM165L" title="The county of residence of the case patient or entity.">
County:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(DEM165)" styleId="DEM165" title="The county of residence of the case patient or entity.">
<html:optionsCollection property="dwrCounties" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM167L" title="The country code for a postal location.">
Country:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM167)" styleId="DEM167" title="The country code for a postal location.">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_16" name="Telephone Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS103L" title="As of Date is the last known date for which the information is valid.">
Telephone Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS103)" maxlength="10" size="10" styleId="NBS103" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS103','NBS103Icon'); return false;" styleId="NBS103Icon" onkeypress="showCalendarEnterKey('NBS103','NBS103Icon',event)"></html:img>
</td> </tr>
</logic:notEqual>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM177L" title="The patient's home phone number.">
Home Phone:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM177)" maxlength="13" title="The patient's home phone number." styleId="DEM177" onkeyup="TeleMask(this, event)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS002L" title="The patient's work phone number.">
Work Phone:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS002)" maxlength="13" title="The patient's work phone number." styleId="NBS002" onkeyup="TeleMask(this, event)"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS003L" title="The patient's work phone number extension.">
Ext.:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS003)" size="8" maxlength="8"  title="The patient's work phone number extension." styleId="NBS003" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS006L" title="The patient's cellular phone number.">
Cell Phone:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS006)" maxlength="13" title="The patient's cellular phone number." styleId="NBS006" onkeyup="TeleMask(this, event)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM182L" title="The patient's email address.">
Email:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM182)" maxlength="50" title="The patient's email address." styleId="DEM182" onblur="checkEmail(this)" styleClass="emailField"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_9" name="Ethnicity and Race Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS100L" title="As of Date is the last known date for which the information is valid.">
Ethnicity Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS100)" maxlength="10" size="10" styleId="NBS100" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS100','NBS100Icon'); return false;" styleId="NBS100Icon" onkeypress="showCalendarEnterKey('NBS100','NBS100Icon',event)"></html:img>
</td> </tr>
</logic:notEqual>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM155L" title="Indicates if the patient is hispanic or not.">
Ethnicity:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM155)" styleId="DEM155" title="Indicates if the patient is hispanic or not.">
<nedss:optionsCollection property="codedValue(PHVS_ETHNICITYGROUP_CDC_UNK)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS101L" title="As of Date is the last known date for which the information is valid.">
Race Information As Of Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS101)" maxlength="10" size="10" styleId="NBS101" title="As of Date is the last known date for which the information is valid." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS101','NBS101Icon'); return false;" styleId="NBS101Icon" onkeypress="showCalendarEnterKey('NBS101','NBS101Icon',event)"></html:img>
</td> </tr>
</logic:notEqual>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Reported race; supports collection of multiple race categories.  This field could repeat." id="DEM152L">
Race:</span>
</td>
<td>
<html:checkbox styleId="DEM152_0"  name="PageForm" property="pageClientVO.americanIndianAlskanRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox> <bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_1"  name="PageForm" property="pageClientVO.asianRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.asian"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_2"  name="PageForm" property="pageClientVO.africanAmericanRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>   <bean:message bundle="RVCT" key="rvct.black.or.african.american"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_3"  name="PageForm" property="pageClientVO.hawaiianRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_4"  name="PageForm" property="pageClientVO.whiteRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.white"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_5"  name="PageForm" property="pageClientVO.otherRace" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.otherRace"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_6"  name="PageForm" property="pageClientVO.refusedToAnswer" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_7"  name="PageForm" property="pageClientVO.notAsked" value="1"
title="Reported race; supports collection of multiple race categories.  This field could repeat."></html:checkbox>  <bean:message bundle="RVCT" key="rvct.notAsked"/>
</td>
</tr>
<tr>
<td class="fieldName">
&nbsp;
</td>
<td>
<html:checkbox styleId="DEM152_8"  name="PageForm" property="pageClientVO.unKnownRace" value="1"
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
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_19" name="Investigation Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="INV107L" title="The jurisdiction of the investigation.">
Jurisdiction:</span>
</td>
<td>

<!--processing Jurisdistion Coded Question-->
<logic:empty name="PageForm" property="attributeMap.ReadOnlyJursdiction"><html:select name="PageForm" property="pageClientVO.answer(INV107)" styleId="INV107" title="The jurisdiction of the investigation.">
<html:optionsCollection property="jurisdictionList" value="key" label="value" /> </html:select></logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.ReadOnlyJursdiction"><nedss:view name="PageForm" property="pageClientVO.answer(INV107)" codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/> <html:hidden name="PageForm" property="pageClientVO.answer(INV107)"/></logic:notEmpty>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="INV108L" title="The program area associated with the investigaiton condition.">
Program Area:</span>
</td>
<td>

<!--processing Program Area Coded Question - read only-->
<nedss:view name="PageForm" property="pageClientVO.answer(INV108)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/><html:hidden name="PageForm" property="pageClientVO.answer(INV108)" />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV147L" title="The date the investigation was started or initiated.">
Investigation Start Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV147)" maxlength="10" size="10" styleId="INV147" title="The date the investigation was started or initiated." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV147','INV147Icon'); return false;" styleId="INV147Icon" onkeypress="showCalendarEnterKey('INV147','INV147Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputField InputFieldLabel" id="INV109L" title="The status of the investigation.">
Investigation Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV109)" styleId="INV109" title="The status of the investigation.">
<nedss:optionsCollection property="codedValue(PHC_IN_STS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Should this record be shared with guests with program area and jurisdiction rights?" id="NBS012L">
Shared Indicator:</span>
</td>
<td>
<html:checkbox styleClass="requiredInputField" name="PageForm" property="pageClientVO.answer(NBS012)" value="1"
title="Should this record be shared with guests with program area and jurisdiction rights?"></html:checkbox>
</td>
</tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV173L" title="The State ID associated with the case.">
State Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV173)" maxlength="15" title="The State ID associated with the case." styleId="INV173" onkeyup="isAlphaNumericCharacterEntered(this);UpperCaseMask(this);chkMaxLength(this,15)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV200L" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here.">
Legacy Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV200)" maxlength="50" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." styleId="INV200"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS547L" title="If the MIS case also had a COVID-19 case, enter the nCoV ID from the COVID-19 case here.">
nCoV ID (If Available):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS547)" maxlength="50" title="If the MIS case also had a COVID-19 case, enter the nCoV ID from the COVID-19 case here." styleId="NBS547"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="67153_7L" title="Name of the person who abstracted the medical record.">
Abstractor Name:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(67153_7)" maxlength="30" title="Name of the person who abstracted the medical record." styleId="67153_7"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS692L" title="The date the information was abstracted from the medical record.">
Date of Abstraction:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS692)" maxlength="10" size="10" styleId="NBS692" title="The date the information was abstracted from the medical record." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS692','NBS692Icon'); return false;" styleId="NBS692Icon" onkeypress="showCalendarEnterKey('NBS692','NBS692Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180L" title="The Public Health Investigator assigned to the Investigation.">
Investigator:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.INV180Uid">
<span id="clearINV180" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV180Uid">
<span id="clearINV180">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="INV180CodeClearButton" onclick="clearProvider('INV180')"/>
</span>
<span id="INV180SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.INV180Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="INV180Icon" onclick="getProvider('INV180');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(INV180)" styleId="INV180Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV180Text','INV180_qec_list')"
title="The Public Health Investigator assigned to the Investigation."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="INV180CodeLookupButton" onclick="getDWRProvider('INV180')"
<logic:notEmpty name="PageForm" property="attributeMap.INV180Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV180_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV180S">Investigator Selected: </td>
<logic:empty name="PageForm" property="attributeMap.INV180Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV180Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.INV180Uid"/>
<span id="INV180">${PageForm.attributeMap.INV180SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV180Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV110L" title="The date the investigation was assigned/started.">
Date Assigned to Investigation:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV110)" maxlength="10" size="10" styleId="INV110" title="The date the investigation was assigned/started." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV110','INV110Icon'); return false;" styleId="INV110Icon" onkeypress="showCalendarEnterKey('INV110','INV110Icon',event)"></html:img>
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
<span class="
InputFieldLabel" id="INV111L" title="The date of report of the condition to the public health department.">
Date of Report:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV111)" maxlength="10" size="10" styleId="INV111" title="The date of report of the condition to the public health department." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV111','INV111Icon'); return false;" styleId="INV111Icon" onkeypress="showCalendarEnterKey('INV111','INV111Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="Date the report was first sent to the public health department (local, county or state) by reporter (physician, lab, etc.)." id="INV177L">
Date First Reported to PHD:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV177)" maxlength="10" size="10" styleId="INV177" title="Date the report was first sent to the public health department (local, county or state) by reporter (physician, lab, etc.)."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV177','INV177Icon');return false;" styleId="INV177Icon" onkeypress="showCalendarEnterKey('INV177','INV177Icon',event);" ></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV120L" title="Earliest date reported to county public health system.">
Earliest Date Reported to County:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV120)" maxlength="10" size="10" styleId="INV120" title="Earliest date reported to county public health system." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV120','INV120Icon'); return false;" styleId="INV120Icon" onkeypress="showCalendarEnterKey('INV120','INV120Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV121L" title="Earliest date reported to state public health system.">
Earliest Date Reported to State:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV121)" maxlength="10" size="10" styleId="INV121" title="Earliest date reported to state public health system." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV121','INV121Icon'); return false;" styleId="INV121Icon" onkeypress="showCalendarEnterKey('INV121','INV121Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_23" name="Reporting Organization" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV112L" title="Type of facility or provider associated with the source of information sent to Public Health.">
Reporting Source Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV112)" styleId="INV112" title="Type of facility or provider associated with the source of information sent to Public Health.">
<nedss:optionsCollection property="codedValue(PHVS_REPORTINGSOURCETYPE_NND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV183L" title="The organization that reported the case.">
Reporting Organization:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.INV183Uid">
<span id="clearINV183" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV183Uid">
<span id="clearINV183">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="INV183CodeClearButton" onclick="clearOrganization('INV183')"/>
</span>
<span id="INV183SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.INV183Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="INV183Icon" onclick="getReportingOrg('INV183');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(INV183)" styleId="INV183Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV183Text','INV183_qec_list')"
title="The organization that reported the case."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="INV183CodeLookupButton" onclick="getDWROrganization('INV183')"
<logic:notEmpty name="PageForm" property="attributeMap.INV183Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV183_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV183S">Reporting Organization Selected: </td>
<logic:empty name="PageForm" property="attributeMap.INV183Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV183Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.INV183Uid"/>
<span id="INV183">${PageForm.attributeMap.INV183SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV183Error"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_10" name="Reporting Provider" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV181L" title="The provider that reported the case.">
Reporting Provider:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.INV181Uid">
<span id="clearINV181" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV181Uid">
<span id="clearINV181">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="INV181CodeClearButton" onclick="clearProvider('INV181')"/>
</span>
<span id="INV181SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.INV181Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="INV181Icon" onclick="getProvider('INV181');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(INV181)" styleId="INV181Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV181Text','INV181_qec_list')"
title="The provider that reported the case."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="INV181CodeLookupButton" onclick="getDWRProvider('INV181')"
<logic:notEmpty name="PageForm" property="attributeMap.INV181Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV181_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV181S">Reporting Provider Selected: </td>
<logic:empty name="PageForm" property="attributeMap.INV181Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV181Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.INV181Uid"/>
<span id="INV181">${PageForm.attributeMap.INV181SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV181Error"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_12" name="Physician" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV182L" title="The physician associated with this case.">
Physician:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.INV182Uid">
<span id="clearINV182" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV182Uid">
<span id="clearINV182">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="INV182CodeClearButton" onclick="clearProvider('INV182')"/>
</span>
<span id="INV182SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.INV182Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="INV182Icon" onclick="getProvider('INV182');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(INV182)" styleId="INV182Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV182Text','INV182_qec_list')"
title="The physician associated with this case."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="INV182CodeLookupButton" onclick="getDWRProvider('INV182')"
<logic:notEmpty name="PageForm" property="attributeMap.INV182Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV182_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV182S">Physician Selected: </td>
<logic:empty name="PageForm" property="attributeMap.INV182Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV182Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.INV182Uid"/>
<span id="INV182">${PageForm.attributeMap.INV182SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV182Error"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_1" name="Reporting County" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NOT113L" title="County reporting the notification.">
Reporting County:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(NOT113)" styleId="NOT113" title="County reporting the notification.">
<html:optionsCollection property="dwrDefaultStateCounties" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Pregnancy" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV178L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178" title="Assesses whether or not the patient is pregnant." onchange="ruleHideUnhINV1788745();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV579L" title="If the subject in pregnant, please enter the due date.">
Due Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV579)" maxlength="10" size="10" styleId="INV579" title="If the subject in pregnant, please enter the due date." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('INV579','INV579Icon'); return false;" styleId="INV579Icon" onkeypress ="showCalendarFutureEnterKey('INV579','INV579Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25" name="Epi-Link" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV148L" title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility.">
Is this person associated with a day care facility?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV148)" styleId="INV148" title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV149L" title="Indicates whether the subject of the investigation was food handler.">
Is this person a food handler?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV149)" styleId="INV149" title="Indicates whether the subject of the investigation was food handler.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV150L" title="Denotes whether the reported case was associated with an identified outbreak.">
Is this case part of an outbreak?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV150)" styleId="INV150" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1508736();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV151L" title="A name assigned to an individual outbreak.   State assigned in SRT.  Should show only those outbreaks for the program area of the investigation.">
Outbreak Name:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV151)" styleId="INV151" title="A name assigned to an individual outbreak.   State assigned in SRT.  Should show only those outbreaks for the program area of the investigation.">
<nedss:optionsCollection property="codedValue(OUTBREAK_NM)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_1" name="Disease Acquisition" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV152L" title="Indication of where the disease/condition was likely acquired.">
Where was the disease acquired?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV152)" styleId="INV152" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1528737();">
<nedss:optionsCollection property="codedValue(PHVS_DISEASEACQUIREDJURISDICTION_NND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV153L" title="If the disease or condition was imported, indicate the country in which the disease was likely acquired.">
Imported Country:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV153)" styleId="INV153" title="If the disease or condition was imported, indicate the country in which the disease was likely acquired.">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV154L" title="If the disease or condition was imported, indicate the state in which the disease was likely acquired.">
Imported State:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(INV154)" styleId="INV154" title="If the disease or condition was imported, indicate the state in which the disease was likely acquired." onchange="getDWRCounties(this, 'INV156')">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV155L" title="If the disease or condition was imported, indicate the city in which the disease was likely acquired.">
Imported City:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV155)" maxlength="50" title="If the disease or condition was imported, indicate the city in which the disease was likely acquired." styleId="INV155"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV156L" title="If the disease or condition was imported, this field will contain the county of origin of the disease or condition.">
Imported County:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(INV156)" styleId="INV156" title="If the disease or condition was imported, this field will contain the county of origin of the disease or condition.">
<html:optionsCollection property="dwrImportedCounties" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV501L" title="Where does the person usually live (defined as their residence).">
Country of Usual Residence:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV501)" styleId="INV501" title="Where does the person usually live (defined as their residence).">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_4" name="Exposure Location" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_NBS_INV_GENV2_UI_4errorMessages">
<b> <a name="NBS_UI_NBS_INV_GENV2_UI_4errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<td style="background-color: #EFEFEF; border:1px solid #666666" width="9%" colspan="3"> &nbsp;</td>
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
<tbody id="questionbodyNBS_UI_NBS_INV_GENV2_UI_4">
<tr id="patternNBS_UI_NBS_INV_GENV2_UI_4" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_NBS_INV_GENV2_UI_4" onkeypress="viewClicked(this.id,'NBS_UI_NBS_INV_GENV2_UI_4');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_NBS_INV_GENV2_UI_4');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_NBS_INV_GENV2_UI_4" onkeypress="editClicked(this.id,'NBS_UI_NBS_INV_GENV2_UI_4');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_NBS_INV_GENV2_UI_4');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_NBS_INV_GENV2_UI_4" onkeypress="deleteClicked(this.id,'NBS_UI_NBS_INV_GENV2_UI_4','patternNBS_UI_NBS_INV_GENV2_UI_4','questionbodyNBS_UI_NBS_INV_GENV2_UI_4');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_NBS_INV_GENV2_UI_4','patternNBS_UI_NBS_INV_GENV2_UI_4','questionbodyNBS_UI_NBS_INV_GENV2_UI_4');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_NBS_INV_GENV2_UI_4">
<tr id="nopatternNBS_UI_NBS_INV_GENV2_UI_4" class="odd" style="display:">
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
<span class="NBS_UI_NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV502L" title="Indicates the country in which the disease was potentially acquired.">
Country of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV502)" styleId="INV502" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');ruleEnDisINV5028743();getDWRStatesByCountry(this, 'INV503');">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV503L" title="Indicates the state in which the disease was potentially acquired.">
State or Province of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV503)" styleId="INV503" title="Indicates the state in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');getDWRCounties(this, 'INV505');">
<nedss:optionsCollection property="codedValue(PHVS_STATEPROVINCEOFEXPOSURE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV504L" title="Indicates the city in which the disease was potentially acquired.">
City of Exposure:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV504)" maxlength="100" title="Indicates the city in which the disease was potentially acquired." styleId="INV504" onkeyup="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_NBS_INV_GENV2_UI_4 InputFieldLabel" id="INV505L" title="Indicates the county in which the disease was potentially acquired.">
County of Exposure:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(INV505)" styleId="INV505" title="Indicates the county in which the disease was potentially acquired.">
<html:optionsCollection property="dwrImportedCounties" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_NBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_GENV2_UI_4','patternNBS_UI_NBS_INV_GENV2_UI_4','questionbodyNBS_UI_NBS_INV_GENV2_UI_4')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_NBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_GENV2_UI_4','patternNBS_UI_NBS_INV_GENV2_UI_4','questionbodyNBS_UI_NBS_INV_GENV2_UI_4')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_NBS_INV_GENV2_UI_4"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_NBS_INV_GENV2_UI_4BatchAddFunction()) writeQuestion('NBS_UI_NBS_INV_GENV2_UI_4','patternNBS_UI_NBS_INV_GENV2_UI_4','questionbodyNBS_UI_NBS_INV_GENV2_UI_4')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_NBS_INV_GENV2_UI_4"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_NBS_INV_GENV2_UI_4')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_5" name="Binational Reporting" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV515L" title="For cases meeting the binational criteria, select all the criteria which are met.">
Binational Reporting Criteria:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV515)" styleId="INV515" title="For cases meeting the binational criteria, select all the criteria which are met."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV515-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVS_BINATIONALREPORTINGCRITERIA_CDC)" value="key" label="value" /> </html:select>
<div id="INV515-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2" name="Case Status" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV157L" title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate.">
Transmission Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV157)" styleId="INV157" title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate.">
<nedss:optionsCollection property="codedValue(PHC_TRAN_M)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV159L" title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc.">
Detection Method:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV159)" styleId="INV159" title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc.">
<nedss:optionsCollection property="codedValue(PHC_DET_MT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV161L" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived.">
Confirmation Method:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV161)" styleId="INV161" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV161-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHC_CONF_M)" value="key" label="value" /> </html:select>
<div id="INV161-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV162L" title="If an investigation is confirmed as a case, then the confirmation date is entered.">
Confirmation Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV162)" maxlength="10" size="10" styleId="INV162" title="If an investigation is confirmed as a case, then the confirmation date is entered." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV162','INV162Icon'); return false;" styleId="INV162Icon" onkeypress="showCalendarEnterKey('INV162','INV162Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV163L" title="The current status of the investigation/case.">
Case Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV163)" styleId="INV163" title="The current status of the investigation/case.">
<nedss:optionsCollection property="codedValue(PHC_CLASS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV165L" title="The MMWR week in which the case should be counted.">
MMWR Week:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV165)" maxlength="2" title="The MMWR week in which the case should be counted." styleId="INV165" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,53)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV166L" title="The MMWR year in which the case should be counted.">
MMWR Year:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV166)" maxlength="4" title="The MMWR year in which the case should be counted." styleId="INV166" onkeyup="YearMask(this, event)" onblur="pgCheckFullYear(this)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NOT120L" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?">
Immediate National Notifiable Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NOT120)" styleId="NOT120" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1208744();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NOT120SPECL" title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition.">
If yes, describe:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NOT120SPEC)" maxlength="100" title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." styleId="NOT120SPEC"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV176L" title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition.">
Date CDC Was First Verbally Notified of This Case:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV176)" maxlength="10" size="10" styleId="INV176" title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV176','INV176Icon'); return false;" styleId="INV176Icon" onkeypress="showCalendarEnterKey('INV176','INV176Icon',event)"></html:img>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV886L" title="Do not send personally identifiable information to CDC in this field. Use this field, if needed, to communicate anything unusual about this case, which is not already covered with the other data elements.  Alternatively, use this field to communicate information to the CDC NNDSS staff processing the data.">
Notification Comments to CDC:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV886)" styleId ="INV886" onkeyup="checkTextAreaLength(this, 2000)" title="Do not send personally identifiable information to CDC in this field. Use this field, if needed, to communicate anything unusual about this case, which is not already covered with the other data elements.  Alternatively, use this field to communicate information to the CDC NNDSS staff processing the data."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_27" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV167L" title="Field which contains general comments for the investigation.">
General Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV167)" styleId ="INV167" onkeyup="checkTextAreaLength(this, 2000)" title="Field which contains general comments for the investigation."/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28002" name="Inclusion Criteria Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS694L" title="Was the patient less than 21 years old at illness onset?">
Age &lt; 21:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS694)" styleId="NBS694" title="Was the patient less than 21 years old at illness onset?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="426000000L" title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more">
Fever For At Least 24 Hrs:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(426000000)" styleId="426000000" title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS695L" title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin.">
Laboratory Markers of Inflammation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS695)" styleId="NBS695" title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="434081000124108L" title="Evidence of clinically severe illness requiring hospitalization.">
Evidence of clinically severe illness requiring hospitalization with multisystem organ involvement:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(434081000124108)" styleId="434081000124108" title="Evidence of clinically severe illness requiring hospitalization." onchange="ruleEnDis4340810001241088748();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="56265001L" title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?">
Cardiac:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(56265001)" styleId="56265001" title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="90708001L" title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?">
Renal:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(90708001)" styleId="90708001" title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="50043002L" title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
Respiratory:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(50043002)" styleId="50043002" title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="128480004L" title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?">
Hematologic:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(128480004)" styleId="128480004" title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="119292006L" title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea).">
Gastrointestinal:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(119292006)" styleId="119292006" title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea).">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95320005L" title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
Dermatologic:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(95320005)" styleId="95320005" title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="118940003L" title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?">
Neurological:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(118940003)" styleId="118940003" title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS696L" title="Indicates whether there is no alternative plausible diagnosis.">
No alternative plausible diagnosis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS696)" styleId="NBS696" title="Indicates whether there is no alternative plausible diagnosis.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS697L" title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?">
Positive for current or recent SARS-COV-2 infection by lab testing?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS697)" styleId="NBS697" title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?" onchange="ruleEnDisNBS6978749();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS709L" title="Was RT-PCR testing completed for this disease?">
RT-PCR:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS709)" styleId="NBS709" title="Was RT-PCR testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS710L" title="Was serology testing completed for this disease?">
Serology:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS710)" styleId="NBS710" title="Was serology testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS711L" title="Was antigen testing completed for this disease?">
Antigen:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS711)" styleId="NBS711" title="Was antigen testing completed for this disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="840546002L" title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?">
COVID-19 exposure within the 4 weeks prior to the onset of symptoms?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(840546002)" styleId="840546002" title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?" onchange="ruleEnDis8405460028746();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LP248166_3L" title="Date of first exposure to individual with confirmed illness within the 4 weeks prior.">
Date of first exposure within the 4 weeks prior:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LP248166_3)" maxlength="10" size="10" styleId="LP248166_3" title="Date of first exposure to individual with confirmed illness within the 4 weeks prior." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LP248166_3','LP248166_3Icon'); return false;" styleId="LP248166_3Icon" onkeypress="showCalendarEnterKey('LP248166_3','LP248166_3Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS698L" title="Exposure date unknown.">
Exposure Date Unknown:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS698)" styleId="NBS698" title="Exposure date unknown.">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28004" name="Weight and BMI" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS434L" title="Enter the height of the patient in inches.">
Height (in inches):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS434)" size="10" maxlength="10"  title="Enter the height of the patient in inches." styleId="NBS434" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM255L" title="Enter the patients weight at diagnosis in pounds (lbs).">
Weight (in lbs):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(DEM255)" size="5" maxlength="5"  title="Enter the patients weight at diagnosis in pounds (lbs)." styleId="DEM255" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="39156_5L" title="What is the patient's body mass index?">
BMI:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(39156_5)" size="10" maxlength="10"  title="What is the patient's body mass index?" styleId="39156_5" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28003" name="Comorbidities Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="370388006L" title="Does the patient have a history of an immunocompromised condition?">
Immunosuppressive disorder or malignancy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(370388006)" styleId="370388006" title="Does the patient have a history of an immunocompromised condition?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="414916001L" title="Is the patient obese?">
Obesity:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(414916001)" styleId="414916001" title="Is the patient obese?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="46635009L" title="Does the patient have type 1 diabetes?">
Type 1 diabetes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(46635009)" styleId="46635009" title="Does the patient have type 1 diabetes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44054006L" title="Does the patient have type 2 diabetes?">
Type 2 diabetes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(44054006)" styleId="44054006" title="Does the patient have type 2 diabetes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="91175000L" title="Did the patient have seizures?">
Seizures:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(91175000)" styleId="91175000" title="Did the patient have seizures?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="13213009L" title="Congenital heart disease">
Congenital heart disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(13213009)" styleId="13213009" title="Congenital heart disease">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="417357006L" title="Does the patient have sickle cell disease?">
Sickle cell disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(417357006)" styleId="417357006" title="Does the patient have sickle cell disease?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413839001L" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
Chronic Lung Disease:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(413839001)" styleId="413839001" title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="276654001L" title="Does the patient have any other congenital malformations?">
Other congenital malformations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(276654001)" styleId="276654001" title="Does the patient have any other congenital malformations?" onchange="ruleEnDis2766540018747();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="276654001_OTHL" title="Specify other congenital malformations.">
Specify other congenital malformations:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(276654001_OTH)" maxlength="100" title="Specify other congenital malformations." styleId="276654001_OTH"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV128)" styleId="INV128" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288733();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328739();ruleEnDis3099040018750();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV184L" title="The hospital associated with the investigation.">
Hospital:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.INV184Uid">
<span id="clearINV184" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV184Uid">
<span id="clearINV184">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="INV184CodeClearButton" onclick="clearOrganization('INV184')"/>
</span>
<span id="INV184SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.INV184Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="INV184Icon" onclick="getReportingOrg('INV184');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(INV184)" styleId="INV184Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('INV184Text','INV184_qec_list')"
title="The hospital associated with the investigation."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="INV184CodeLookupButton" onclick="getDWROrganization('INV184')"
<logic:notEmpty name="PageForm" property="attributeMap.INV184Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="INV184_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="INV184S">Hospital Selected: </td>
<logic:empty name="PageForm" property="attributeMap.INV184Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.INV184Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.INV184Uid"/>
<span id="INV184">${PageForm.attributeMap.INV184SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="INV184Error"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV132L" title="Subject's admission date to the hospital for the condition covered by the investigation.">
Hospital Admission Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV132)" maxlength="10" size="10" styleId="INV132" title="Subject's admission date to the hospital for the condition covered by the investigation." onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV132','INV132Icon'); return false;" styleId="INV132Icon" onkeypress="showCalendarEnterKey('INV132','INV132Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV133L" title="Subject's discharge date from the hospital for the condition covered by the investigation.">
Hospital Discharge:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV133)" maxlength="10" size="10" styleId="INV133" title="Subject's discharge date from the hospital for the condition covered by the investigation." onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV133','INV133Icon'); return false;" styleId="INV133Icon" onkeypress="showCalendarEnterKey('INV133','INV133Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV134L" title="Subject's duration of stay at the hospital for the condition covered by the investigation.">
Total Duration of Stay in the Hospital (in days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV134)" size="3" maxlength="3"  title="Subject's duration of stay at the hospital for the condition covered by the investigation." styleId="INV134" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="309904001L" title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?">
Was patient admitted to ICU?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(309904001)" styleId="309904001" title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?" onchange="ruleEnDis3099040018750();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS679L" title="Enter the date of admission.">
ICU Admission Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS679)" maxlength="10" size="10" styleId="NBS679" title="Enter the date of admission." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS679','NBS679Icon'); return false;" styleId="NBS679Icon" onkeypress="showCalendarEnterKey('NBS679','NBS679Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="74200_7L" title="Indicate the number of days the patient was in intensive care.">
Number of days in the ICU:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(74200_7)" size="3" maxlength="3"  title="Indicate the number of days the patient was in intensive care." styleId="74200_7" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,364)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_1038L" title="Patients Outcome">
Patients Outcome:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_1038)" styleId="FDD_Q_1038" title="Patients Outcome">
<nedss:optionsCollection property="codedValue(PATIENT_HOSP_STATUS_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV145L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV145)" styleId="INV145" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1458734();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV146L" title="The date the subject’s death occurred.">
Date of Death:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV146)" maxlength="10" size="10" styleId="INV146" title="The date the subject’s death occurred." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV146','INV146Icon'); return false;" styleId="INV146Icon" onkeypress="showCalendarEnterKey('INV146','INV146Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29007" name="Previous COVID Like Illness" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS707L" title="Did the patient have preceding COVID-like illness?">
Did patient have preceding COVID-like illness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS707)" styleId="NBS707" title="Did the patient have preceding COVID-like illness?" onchange="ruleEnDisNBS7078759();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS708L" title="Date of COVID-like illness symptom onset.">
Date of COVID-like illness symptom onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS708)" maxlength="10" size="10" styleId="NBS708" title="Date of COVID-like illness symptom onset." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS708','NBS708Icon'); return false;" styleId="NBS708Icon" onkeypress="showCalendarEnterKey('NBS708','NBS708Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27016" name="Symptoms Onset and Fever" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Onset information below relates to onset of Multisystem Inflammatory Syndrome Associated with COVID-19.</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV136L" title="Date of diagnosis of MIS-C.">
Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV136)" maxlength="10" size="10" styleId="INV136" title="Date of diagnosis of MIS-C." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV136','INV136Icon'); return false;" styleId="INV136Icon" onkeypress="showCalendarEnterKey('INV136','INV136Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV137L" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system.">
Illness Onset Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV137)" maxlength="10" size="10" styleId="INV137" title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessOnsetAge('DEM115','INV137','INV143','INV144');pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV137','INV137Icon'); return false;" styleId="INV137Icon" onkeypress="showCalendarEnterKey('INV137','INV137Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV138L" title="The time at which the disease or condition ends.">
Illness End Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV138)" maxlength="10" size="10" styleId="INV138" title="The time at which the disease or condition ends." onkeyup="DateMask(this,null,event)" onblur="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')" onchange="pgCalculateIllnessDuration('INV139','INV140','INV137','INV138')"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV138','INV138Icon'); return false;" styleId="INV138Icon" onkeypress="showCalendarEnterKey('INV138','INV138Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV139L" title="The length of time this person had this disease or condition.">
Illness Duration:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV139)" size="3" maxlength="3"  title="The length of time this person had this disease or condition." styleId="INV139" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV140L" title="Unit of time used to describe the length of the illness or condition.">
Illness Duration Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV140)" styleId="INV140" title="Unit of time used to describe the length of the illness or condition.">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV143L" title="Subject's age at the onset of the disease or condition.">
Age at Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV143)" size="3" maxlength="3"  title="Subject's age at the onset of the disease or condition." styleId="INV143" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,150)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV144L" title="The age units for an age.">
Age at Onset Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV144)" styleId="INV144" title="The age units for an age.">
<nedss:optionsCollection property="codedValue(AGE_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006L" title="Did the patient have fever?">
Fever Greater Than 38.0 C or 100.4 F:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386661006)" styleId="386661006" title="Did the patient have fever?" onchange="ruleEnDis3866610068751();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV203L" title="Indicates the date of fever onset">
Date of Fever Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV203)" maxlength="10" size="10" styleId="INV203" title="Indicates the date of fever onset" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV203','INV203Icon'); return false;" styleId="INV203Icon" onkeypress="showCalendarEnterKey('INV203','INV203Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202L" title="What was the subjects highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature (in degrees C):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV202)" size="10" maxlength="10"  title="What was the subjects highest measured temperature during this illness, in degress Celsius?" styleId="INV202" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAR125L" title="Total number of days fever lasted">
Number of Days Febrile:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAR125)" size="20" maxlength="20"  title="Total number of days fever lasted" styleId="VAR125" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27017" name="Cardiac" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Shock is captured in the Complications section of the page.</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="444931001L" title="Did the patient have elevated troponin?">
Elevated troponin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(444931001)" styleId="444931001" title="Did the patient have elevated troponin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="414798009L" title="Does the patient have elevated BNP or NT-proBNP?">
Elevated BNP or NT-proBNP:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(414798009)" styleId="414798009" title="Does the patient have elevated BNP or NT-proBNP?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27018" name="Renal" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="14350001000004108L" title="Did the patient have acute kidney injury?">
Acute kidney injury:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(14350001000004108)" styleId="14350001000004108" title="Did the patient have acute kidney injury?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="42399005L" title="Did the patient have renal failure?">
Renal failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(42399005)" styleId="42399005" title="Did the patient have renal failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27019" name="Respiratory" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49727002L" title="Did the patients illness include the symptom of cough?">
Cough:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(49727002)" styleId="49727002" title="Did the patients illness include the symptom of cough?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="267036007L" title="Did the patient have shortness of breath (dyspnea)?">
Shortness of breath (dyspnea):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(267036007)" styleId="267036007" title="Did the patient have shortness of breath (dyspnea)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="29857009L" title="Did the patient experience chest pain?">
Chest pain or tightness:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(29857009)" styleId="29857009" title="Did the patient experience chest pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Pneumonia and ARDS are captured in Complications section of the page.</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="59282003L" title="Did the patient have pulmonary embolism?">
Pulmonary embolism:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(59282003)" styleId="59282003" title="Did the patient have pulmonary embolism?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27020" name="Hematologic" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="449830004L" title="Did the patient have elevated D-dimers?">
Elevated D-dimers:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(449830004)" styleId="449830004" title="Did the patient have elevated D-dimers?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="234467004L" title="Did the patient have thrombophilia?">
Thrombophilia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(234467004)" styleId="234467004" title="Did the patient have thrombophilia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="302215000L" title="Did the subject have thrombocytopenia?">
Thrombocytopenia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(302215000)" styleId="302215000" title="Did the subject have thrombocytopenia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27021" name="Gastrointestinal" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="21522001L" title="Did the patient have abdominal pain or tenderness?">
Abdominal pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(21522001)" styleId="21522001" title="Did the patient have abdominal pain or tenderness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="422400008L" title="Did the subject experience vomiting?">
Vomiting:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(422400008)" styleId="422400008" title="Did the subject experience vomiting?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="62315008L" title="Did the patient have diarrhea?">
Diarrhea:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(62315008)" styleId="62315008" title="Did the patient have diarrhea?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="26165005L" title="Did the patient have elevated bilirubin?">
Elevated bilirubin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(26165005)" styleId="26165005" title="Did the patient have elevated bilirubin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="707724006L" title="Did the patient have elevated liver enzymes?">
Elevated liver enzymes:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(707724006)" styleId="707724006" title="Did the patient have elevated liver enzymes?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27022" name="Dermatologic" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="271807003L" title="Did the patient have rash?">
Rash:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(271807003)" styleId="271807003" title="Did the patient have rash?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="95346009L" title="Did the patient have mucocutaneous lesions?">
Mucocutaneous lesions:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(95346009)" styleId="95346009" title="Did the patient have mucocutaneous lesions?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27023" name="Neurological" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="25064002L" title="Did the patient have headache?">
Headache:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(25064002)" styleId="25064002" title="Did the patient have headache?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="419284004L" title="Did the patient have altered mental status?">
Altered Mental Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(419284004)" styleId="419284004" title="Did the patient have altered mental status?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS712L" title="Did the patient have syncope/near syncope?">
Syncope/near syncope:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS712)" styleId="NBS712" title="Did the patient have syncope/near syncope?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="44201003L" title="Did the patient have meningitis?">
Meningitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(44201003)" styleId="44201003" title="Did the patient have meningitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="81308009L" title="Did the patient have encephalopathy?">
Encephalopathy?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(81308009)" styleId="81308009" title="Did the patient have encephalopathy?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27024" name="Other Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="81680005L" title="Did the patient have neck pain?">
Neck pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(81680005)" styleId="81680005" title="Did the patient have neck pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001L" title="Did the patient have myalgia?">
Myalgia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(68962001)" styleId="68962001" title="Did the patient have myalgia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="193894004L" title="Did the patient have conjunctival injection?">
Conjunctival injection:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(193894004)" styleId="193894004" title="Did the patient have conjunctival injection?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49563000L" title="Did the patient have periorbital edema?">
Periorbital edema:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(49563000)" styleId="49563000" title="Did the patient have periorbital edema?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="127086001L" title="Did the patient have cervical lymphadenopathy?">
Cervical lymphadenopathy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(127086001)" styleId="127086001" title="Did the patient have cervical lymphadenopathy?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29002" name="Complications Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="698247007L" title="Did the patient have arrhythmia?">
Arrhythmia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(698247007)" styleId="698247007" title="Did the patient have arrhythmia?" onchange="ruleEnDis6982470078752();enableOrDisableOther('76281_5');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="76281_5L" title="Indicate the type of arrhythmia.">
Type of arrhythmia:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(76281_5)" styleId="76281_5" title="Indicate the type of arrhythmia."
multiple="true" size="4"
onchange="displaySelectedOptions(this, '76281_5-selectedValues');enableOrDisableOther('76281_5')" >
<nedss:optionsCollection property="codedValue(CARDIAC_ARRHYTHMIA_TYPE)" value="key" label="value" /> </html:select>
<div id="76281_5-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the type of arrhythmia." id="76281_5OthL">Other Type of arrhythmia:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(76281_5Oth)" size="40" maxlength="40" title="Other Indicate the type of arrhythmia." styleId="76281_5Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB020L" title="Did the patient have congestive heart failure?">
Congestive Heart Failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB020)" styleId="ARB020" title="Did the patient have congestive heart failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="50920009L" title="Did the patient have myocarditis?">
Myocarditis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(50920009)" styleId="50920009" title="Did the patient have myocarditis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="3238004L" title="Did the patient have pericarditis?">
Pericarditis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(3238004)" styleId="3238004" title="Did the patient have pericarditis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="59927004L" title="Did the patient have liver failure?">
Liver failure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(59927004)" styleId="59927004" title="Did the patient have liver failure?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS713L" title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?">
Deep vein thrombosis (DVT) or pulmonary embolism (PE):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS713)" styleId="NBS713" title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="67782005L" title="Did the patient have acute respiratory distress syndrome?">
ARDS:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(67782005)" styleId="67782005" title="Did the patient have acute respiratory distress syndrome?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233604007L" title="Did the patient have pneumonia?">
Pneumonia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(233604007)" styleId="233604007" title="Did the patient have pneumonia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB021L" title="Did patient develop CVA or stroke?">
CVA or Stroke:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB021)" styleId="ARB021" title="Did patient develop CVA or stroke?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="31646008L" title="Did the patient have encephalitis?">
Encephalitis or aseptic meningitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(31646008)" styleId="31646008" title="Did the patient have encephalitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="27942005L" title="Did the subject have septic shock?">
Shock:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(27942005)" styleId="27942005" title="Did the subject have septic shock?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="45007003L" title="Did the patient have hypotension?">
Hypotension:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(45007003)" styleId="45007003" title="Did the patient have hypotension?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29004" name="Treatments Details" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="466713001L" title="Did the patient receive low flow nasal cannula?">
Low flow nasal cannula:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(466713001)" styleId="466713001" title="Did the patient receive low flow nasal cannula?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="426854004L" title="Did the patient receive high flow nasal cannula?">
High flow nasal cannula:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(426854004)" styleId="426854004" title="Did the patient receive high flow nasal cannula?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="428311008L" title="Did the patient receive non-invasive ventilation?">
Non-invasive ventilation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(428311008)" styleId="428311008" title="Did the patient receive non-invasive ventilation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="52765003L" title="Did the patient receive intubation?">
Intubation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(52765003)" styleId="52765003" title="Did the patient receive intubation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS673L" title="Did the patient receive mechanical ventilation?">
Mechanical ventilation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS673)" styleId="NBS673" title="Did the patient receive mechanical ventilation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="233573008L" title="Did the patient receive ECMO?">
ECMO:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(233573008)" styleId="233573008" title="Did the patient receive ECMO?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS699L" title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?">
Vasoactive medications:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS699)" styleId="NBS699" title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?" onchange="ruleEnDisNBS6998755();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS700L" title="Specify the vasoactive medications the patient received.">
Specify vasoactive medications:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS700)" maxlength="100" title="Specify the vasoactive medications the patient received." styleId="NBS700"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB040L" title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?">
Steroids:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB040)" styleId="ARB040" title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="ARB045L" title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?">
Immune modulators:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(ARB045)" styleId="ARB045" title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?" onchange="ruleEnDisARB0458756();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="373244000L" title="Specify immune modulators the patient received.">
Specify immune modulators:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(373244000)" maxlength="100" title="Specify immune modulators the patient received." styleId="373244000"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="372560006L" title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?">
Antiplatelets:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(372560006)" styleId="372560006" title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?" onchange="ruleEnDis3725600068753();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="372560006_OTHL" title="Specify antiplatelets the patient received.">
Specify antiplatelets:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(372560006_OTH)" maxlength="100" title="Specify antiplatelets the patient received." styleId="372560006_OTH"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="372862008L" title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?">
Anticoagulation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(372862008)" styleId="372862008" title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?" onchange="ruleEnDis3728620088754();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="372862008_OTHL" title="Specify anticoagulation the patient received?">
Specify anticoagulation:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(372862008_OTH)" maxlength="100" title="Specify anticoagulation the patient received?" styleId="372862008_OTH"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="302497006L" title="Did the patient receive hemodialysis?">
Dialysis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(302497006)" styleId="302497006" title="Did the patient receive hemodialysis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS701L" title="Did the patient receive first intravenous immunoglobulin?">
First IVIG:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS701)" styleId="NBS701" title="Did the patient receive first intravenous immunoglobulin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS702L" title="Did the patient receive second intravenous immunoglobulin?">
Second IVIG:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS702)" styleId="NBS702" title="Did the patient receive second intravenous immunoglobulin?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA40001" name="Vaccine Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126L" title="Has the patient received a COVID-19 vaccine?">
Has the patient received a COVID-19 vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC126)" styleId="VAC126" title="Has the patient received a COVID-19 vaccine?" onchange="ruleEnDisVAC1268769();enableOrDisableOther('VAC107');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC132_CDL" title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
If yes, how many doses?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC132_CD)" styleId="VAC132_CD" title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
<nedss:optionsCollection property="codedValue(VAC_DOSE_NUM_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS791L" title="Date the first vaccine dose was received.">
Vaccine Dose 1 Received Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS791)" maxlength="10" size="10" styleId="NBS791" title="Date the first vaccine dose was received." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS791','NBS791Icon'); return false;" styleId="NBS791Icon" onkeypress="showCalendarEnterKey('NBS791','NBS791Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS792L" title="Date the second vaccine dose was received.">
Vaccine Dose 2 Received Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS792)" maxlength="10" size="10" styleId="NBS792" title="Date the second vaccine dose was received." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS792','NBS792Icon'); return false;" styleId="NBS792Icon" onkeypress="showCalendarEnterKey('NBS792','NBS792Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC107L" title="COVID-19 vaccine manufacturer">
COVID-19 vaccine manufacturer:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC107)" styleId="VAC107" title="COVID-19 vaccine manufacturer" onchange="enableOrDisableOther('VAC107');">
<nedss:optionsCollection property="codedValue(MIS_VAC_MFGR)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="COVID-19 vaccine manufacturer" id="VAC107OthL">Other COVID-19 vaccine manufacturer:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(VAC107Oth)" size="40" maxlength="40" title="Other COVID-19 vaccine manufacturer" styleId="VAC107Oth"/></td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27008" name="Blood Test Results" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS714L" title="If the patient had a fibrinogen result, enter the highest value.">
Fibrinogen Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS714)" size="10" maxlength="10"  title="If the patient had a fibrinogen result, enter the highest value." styleId="NBS714" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS715L" title="If the patient had a fibrinogen test, enter the unit of measure associated with the value.">
Fibrinogen Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS715)" styleId="NBS715" title="If the patient had a fibrinogen test, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(FIBRINOGEN_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS716L" title="If the patient had a fibrinogen result, indicate the interpretation.">
Fibrinogen Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS716)" styleId="NBS716" title="If the patient had a fibrinogen result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS717L" title="If the patient had a CRP result, enter the highest value.">
CRP Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS717)" size="10" maxlength="10"  title="If the patient had a CRP result, enter the highest value." styleId="NBS717" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS728L" title="If the patient had a CRP result enter the unit of measure associated with the value.">
CRP Unit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS728)" styleId="NBS728" title="If the patient had a CRP result enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(CRP_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS729L" title="If the patient had a CRP result, indicate the interpretation.">
CRP Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS729)" styleId="NBS729" title="If the patient had a CRP result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS730L" title="If the patient had a ferritin result, enter the highest value.">
Ferritin Test Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS730)" size="10" maxlength="10"  title="If the patient had a ferritin result, enter the highest value." styleId="NBS730" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS731L" title="If the patient had a ferritin result enter the unit of measure associated with the value.">
Ferritin Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS731)" styleId="NBS731" title="If the patient had a ferritin result enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(FERRITIN_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS732L" title="If the patient had a ferritin result, indicate the interpretation.">
Ferritin Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS732)" styleId="NBS732" title="If the patient had a ferritin result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS733L" title="If the patient had a troponin result, enter the highest value.">
Troponin Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS733)" size="10" maxlength="10"  title="If the patient had a troponin result, enter the highest value." styleId="NBS733" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS734L" title="If the patient had a troponin result, enter the unit of measure associated with the value.">
Troponin Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS734)" styleId="NBS734" title="If the patient had a troponin result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(TROPONIN_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS735L" title="If the patient had a troponin result, indicate the interpretation.">
Troponin Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS735)" styleId="NBS735" title="If the patient had a troponin result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS736L" title="If the patient had a BNP result, enter the highest value.">
BNP Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS736)" size="10" maxlength="10"  title="If the patient had a BNP result, enter the highest value." styleId="NBS736" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS737L" title="If the patient had a BNP result, enter the unit of measure associated with the value.">
BNP Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS737)" styleId="NBS737" title="If the patient had a BNP result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BNP_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS738L" title="If the patient had a BNP result, indicate the interpretation.">
BNP Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS738)" styleId="NBS738" title="If the patient had a BNP result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS739L" title="If the patient had a NT-proBNP result, enter the highest value.">
NT-proBNP Test Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS739)" size="10" maxlength="10"  title="If the patient had a NT-proBNP result, enter the highest value." styleId="NBS739" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS740L" title="If the patient had a NT-proBNP result, enter the unit of measure associated with the value.">
NT-proBNP Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS740)" styleId="NBS740" title="If the patient had a NT-proBNP result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BNP_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS741L" title="If the patient had a NT-proBNP result, indicate the interpretation.">
NT-proBNP Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS741)" styleId="NBS741" title="If the patient had a NT-proBNP result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS742L" title="If the patient had a D-dimer result, enter the highest value.">
D-dimer Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS742)" size="10" maxlength="10"  title="If the patient had a D-dimer result, enter the highest value." styleId="NBS742" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS743L" title="If the patient had a D-dimer result, enter the unit of measure associated with the value.">
D-dimer Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS743)" styleId="NBS743" title="If the patient had a D-dimer result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(D_DIMER_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS744L" title="If the patient had a D-dimer result, indicate the interpretation.">
D-dimer Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS744)" styleId="NBS744" title="If the patient had a D-dimer result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS745L" title="If the patient had a IL-6 result, enter the highest value.">
IL-6 Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS745)" size="10" maxlength="10"  title="If the patient had a IL-6 result, enter the highest value." styleId="NBS745" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS746L" title="If the patient had a IL-6 result, enter the unit of measure associated with the value.">
IL-6 Unit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS746)" styleId="NBS746" title="If the patient had a IL-6 result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(IL_6_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS747L" title="If the patient had a IL-6 result, indicate the interpretation.">
IL-6 Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS747)" styleId="NBS747" title="If the patient had a IL-6 result, indicate the interpretation.">
<nedss:optionsCollection property="codedValue(LAB_TST_INTERP_MIS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS748L" title="If the patient had a serum white blood count result, enter the test interpretation.">
Serum White Blood Count Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS748)" size="10" maxlength="10"  title="If the patient had a serum white blood count result, enter the test interpretation." styleId="NBS748" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS749L" title="If the patient had a serum white blood count result, enter the lowest value.">
Serum White Blood Count Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS749)" size="10" maxlength="10"  title="If the patient had a serum white blood count result, enter the lowest value." styleId="NBS749" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS750L" title="If the patient had a serum white blood count result, enter the unit of measure associated with the value.">
Serum White Blood Count Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS750)" styleId="NBS750" title="If the patient had a serum white blood count result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BLOOD_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS751L" title="If the patient had a platelets result, enter the highest value.">
Platelets Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS751)" size="10" maxlength="10"  title="If the patient had a platelets result, enter the highest value." styleId="NBS751" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS752L" title="If the patient had a platelets result, enter the lowest value.">
Platelets Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS752)" size="10" maxlength="10"  title="If the patient had a platelets result, enter the lowest value." styleId="NBS752" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS753L" title="If the patient had a platelets result, enter the unit of measure associated with the value.">
Platelets Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS753)" styleId="NBS753" title="If the patient had a platelets result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BLOOD_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS754L" title="If the patient had a neutrophils result, enter the highest value.">
Neutrophils Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS754)" size="10" maxlength="10"  title="If the patient had a neutrophils result, enter the highest value." styleId="NBS754" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS755L" title="If the patient had a neutrophils result, enter the lowest value.">
Neutrophils Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS755)" size="10" maxlength="10"  title="If the patient had a neutrophils result, enter the lowest value." styleId="NBS755" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS756L" title="If the patient had a neutrophils result, enter the unit of measure associated with the value.">
Neutrophils Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS756)" styleId="NBS756" title="If the patient had a neutrophils result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BLOOD_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS757L" title="If the patient had a lymphocytes result, enter the highest value.">
Lymphocytes Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS757)" size="10" maxlength="10"  title="If the patient had a lymphocytes result, enter the highest value." styleId="NBS757" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS758L" title="If the patient had a lymphocytes result, enter the lowest value.">
Lymphocytes Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS758)" size="10" maxlength="10"  title="If the patient had a lymphocytes result, enter the lowest value." styleId="NBS758" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS759L" title="If the patient had a lymphocytes result, enter the unit of measure associated with the value.">
Lymphocytes Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS759)" styleId="NBS759" title="If the patient had a lymphocytes result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BLOOD_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS760L" title="If the patient had a bands test result, enter the highest value.">
Bands Test Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS760)" size="10" maxlength="10"  title="If the patient had a bands test result, enter the highest value." styleId="NBS760" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS761L" title="If the patient had a band test result, enter the lowest value.">
Bands Test Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS761)" size="10" maxlength="10"  title="If the patient had a band test result, enter the lowest value." styleId="NBS761" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS762L" title="If the patient had a bands test result, enter the unit of measure associated with the value.">
Bands Test Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS762)" styleId="NBS762" title="If the patient had a bands test result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(BANDS_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27009" name="CSF Studies" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS763L" title="If the patient had a white blood count result, enter the highest value.">
White Blood Count Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS763)" size="10" maxlength="10"  title="If the patient had a white blood count result, enter the highest value." styleId="NBS763" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS764L" title="If the patient had a white blood count result, enter the lowest value.">
White Blood Count Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS764)" size="10" maxlength="10"  title="If the patient had a white blood count result, enter the lowest value." styleId="NBS764" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS765L" title="If the patient had a white blood count result, enter the unit of measure associated with the value.">
White Blood Count Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS765)" styleId="NBS765" title="If the patient had a white blood count result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(WBC_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS766L" title="If the patient had a protein test result, enter the highest value.">
Protein Test Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS766)" size="10" maxlength="10"  title="If the patient had a protein test result, enter the highest value." styleId="NBS766" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS767L" title="If the patient had a protein test result, enter the lowest value.">
Protein Test Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS767)" size="10" maxlength="10"  title="If the patient had a protein test result, enter the lowest value." styleId="NBS767" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS768L" title="If the patient had a protein test result, enter the unit of measure associated with the value.">
Protein Test Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS768)" styleId="NBS768" title="If the patient had a protein test result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(PROTEIN_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS769L" title="If the patient had a glucose result, enter the highest value.">
Glucose Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS769)" size="10" maxlength="10"  title="If the patient had a glucose result, enter the highest value." styleId="NBS769" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS770L" title="If the patient had a glucose test result, enter the lowest value.">
Glucose Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS770)" size="10" maxlength="10"  title="If the patient had a glucose test result, enter the lowest value." styleId="NBS770" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS771L" title="If the patient had a glucose result, enter the unit of measure associated with the value.">
Glucose Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS771)" styleId="NBS771" title="If the patient had a glucose result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(GLUCOSE_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27010" name="Urinalysis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS772L" title="If the patient had a urine while blood count result, enter the highest value.">
Urine White Blood Count Highest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS772)" size="10" maxlength="10"  title="If the patient had a urine while blood count result, enter the highest value." styleId="NBS772" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS773L" title="If the patient had a urine white blood count result, enter the lowest value.">
Urine White Blood Count Lowest Value:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS773)" size="10" maxlength="10"  title="If the patient had a urine white blood count result, enter the lowest value." styleId="NBS773" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS774L" title="If the patient had a urine white blood count result, enter the unit of measure associated with the value.">
Urine White Blood Count Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS774)" styleId="NBS774" title="If the patient had a urine white blood count result, enter the unit of measure associated with the value.">
<nedss:optionsCollection property="codedValue(URINE_WBC_TEST_UNIT)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27011" name="Echocardiogram" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="40701008L" title="Indicate echocardiogram result.">
Echocardiogram result:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(40701008)" styleId="40701008" title="Indicate echocardiogram result."
multiple="true" size="4"
onchange="displaySelectedOptions(this, '40701008-selectedValues');ruleEnDis407010088763();ruleEnDis407010088762();ruleEnDis407010088761();ruleEnDis407010088760();enableOrDisableOther('40701008')" >
<nedss:optionsCollection property="codedValue(ECHOCARDIOGRAM_RESULT_MIS)" value="key" label="value" /> </html:select>
<div id="40701008-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate echocardiogram result." id="40701008OthL">Other Echocardiogram result:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(40701008Oth)" size="40" maxlength="40" title="Other Indicate echocardiogram result." styleId="40701008Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="373065002L" title="Indicate max coronary artery Z-score.">
Max coronary artery Z-score:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(373065002)" maxlength="10" title="Indicate max coronary artery Z-score." styleId="373065002"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS703L" title="Indicate cardiac dysfunction type.">
Cardiac dysfunction type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS703)" styleId="NBS703" title="Indicate cardiac dysfunction type.">
<nedss:optionsCollection property="codedValue(CARDIAC_DYSFUNCTION_TYPE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS704L" title="Indicate the date of first test showing coronary artery aneurysm or dilatation.">
Date of first test showing coronary artery aneurysm or dilatation:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS704)" maxlength="10" size="10" styleId="NBS704" title="Indicate the date of first test showing coronary artery aneurysm or dilatation." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS704','NBS704Icon'); return false;" styleId="NBS704Icon" onkeypress="showCalendarEnterKey('NBS704','NBS704Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="18113_1L" title="Indicate mitral regurgitation type.">
Mitral regurgitation type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(18113_1)" styleId="18113_1" title="Indicate mitral regurgitation type.">
<nedss:optionsCollection property="codedValue(MITRAL_REGURG_TYPE)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27012" name="Abdominal Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="441987005L" title="Was abdominal imaging was completed for this patient?">
Was abdominal imaging done?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(441987005)" styleId="441987005" title="Was abdominal imaging was completed for this patient?" onchange="ruleEnDis4419870058757();enableOrDisableOther('NBS706');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS705L" title="Indicate abdominal imaging type.">
Abdominal imaging type:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(NBS705)" styleId="NBS705" title="Indicate abdominal imaging type."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS705-selectedValues')" >
<nedss:optionsCollection property="codedValue(ABD_IMAGING_TYPE_MIS)" value="key" label="value" /> </html:select>
<div id="NBS705-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS706L" title="Indicate abdominal imaging results.">
Abdominal imaging results:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(NBS706)" styleId="NBS706" title="Indicate abdominal imaging results."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS706-selectedValues');enableOrDisableOther('NBS706')" >
<nedss:optionsCollection property="codedValue(ABD_IMAGING_RSLT_MIS)" value="key" label="value" /> </html:select>
<div id="NBS706-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate abdominal imaging results." id="NBS706OthL">Other Abdominal imaging results:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS706Oth)" size="40" maxlength="40" title="Other Indicate abdominal imaging results." styleId="NBS706Oth"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27013" name="Chest Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="413815006L" title="Was chest imaging completed for this patient?">
Was chest imaging done?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(413815006)" styleId="413815006" title="Was chest imaging completed for this patient?" onchange="ruleEnDis4138150068758();enableOrDisableOther('LAB678');">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB677L" title="Indicate the type of chest study performed. Please provide a response for each of the main test types (plain chest radiograph, chest CT Scan) and if test was not done please indicate so.">
Type of Chest Study:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(LAB677)" styleId="LAB677" title="Indicate the type of chest study performed. Please provide a response for each of the main test types (plain chest radiograph, chest CT Scan) and if test was not done please indicate so."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'LAB677-selectedValues')" >
<nedss:optionsCollection property="codedValue(CHEST_IMAGING_TYPE_MIS)" value="key" label="value" /> </html:select>
<div id="LAB677-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB678L" title="Result of chest diagnostic testing">
Result of Chest Study:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(LAB678)" styleId="LAB678" title="Result of chest diagnostic testing"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'LAB678-selectedValues');enableOrDisableOther('LAB678')" >
<nedss:optionsCollection property="codedValue(CHEST_IMAGING_RSLT_MIS)" value="key" label="value" /> </html:select>
<div id="LAB678-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Result of chest diagnostic testing" id="LAB678OthL">Other Result of Chest Study:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB678Oth)" size="40" maxlength="40" title="Other Result of chest diagnostic testing" styleId="LAB678Oth"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27015" name="SARS-COV-2 Testing Details" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;If multiple tests were performed for a given test type, enter the date for the first positive.</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS718L" title="RT-PCR Test Result">
RT-PCR Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS718)" styleId="NBS718" title="RT-PCR Test Result" onchange="ruleEnDisNBS7188764();">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS719L" title="RT-PCR Test Date">
RT-PCR Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS719)" maxlength="10" size="10" styleId="NBS719" title="RT-PCR Test Date" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS719','NBS719Icon'); return false;" styleId="NBS719Icon" onkeypress="showCalendarEnterKey('NBS719','NBS719Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS720L" title="Antigen Test Result">
Antigen Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS720)" styleId="NBS720" title="Antigen Test Result" onchange="ruleEnDisNBS7208765();">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS721L" title="Antigen Test Date">
Antigen Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS721)" maxlength="10" size="10" styleId="NBS721" title="Antigen Test Date" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS721','NBS721Icon'); return false;" styleId="NBS721Icon" onkeypress="showCalendarEnterKey('NBS721','NBS721Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS722L" title="IgG Test Result">
IgG Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS722)" styleId="NBS722" title="IgG Test Result" onchange="ruleEnDisNBS7228766();">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS723L" title="IgG Test Date">
IgG Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS723)" maxlength="10" size="10" styleId="NBS723" title="IgG Test Date" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS723','NBS723Icon'); return false;" styleId="NBS723Icon" onkeypress="showCalendarEnterKey('NBS723','NBS723Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS724L" title="IgM Test Result">
IgM Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS724)" styleId="NBS724" title="IgM Test Result" onchange="ruleEnDisNBS7248767();">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS725L" title="IgM Test Date">
IgM Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS725)" maxlength="10" size="10" styleId="NBS725" title="IgM Test Date" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS725','NBS725Icon'); return false;" styleId="NBS725Icon" onkeypress="showCalendarEnterKey('NBS725','NBS725Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS726L" title="IgA Test Result">
IgA Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS726)" styleId="NBS726" title="IgA Test Result" onchange="ruleEnDisNBS7268768();">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS727L" title="IgA Test Date">
IgA Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS727)" maxlength="10" size="10" styleId="NBS727" title="IgA Test Date" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS727','NBS727Icon'); return false;" styleId="NBS727Icon" onkeypress="showCalendarEnterKey('NBS727','NBS727Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->

<!-- ################### A PAGE TAB ###################### - - -->

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_28" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS055L" title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc.">
Contact Investigation Priority:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS055)" styleId="NBS055" title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc.">
<nedss:optionsCollection property="codedValue(NBS_PRIORITY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS056L" title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period.">
Infectious Period From:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS056)" maxlength="10" size="10" styleId="NBS056" title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS056','NBS056Icon'); return false;" styleId="NBS056Icon" onkeypress ="showCalendarFutureEnterKey('NBS056','NBS056Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS057L" title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period.">
Infectious Period To:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS057)" maxlength="10" size="10" styleId="NBS057" title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS057','NBS057Icon'); return false;" styleId="NBS057Icon" onkeypress ="showCalendarFutureEnterKey('NBS057','NBS057Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS058L" title="The status of the contact investigation.">
Contact Investigation Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS058)" styleId="NBS058" title="The status of the contact investigation.">
<nedss:optionsCollection property="codedValue(PHC_IN_STS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS059L" title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation.">
Contact Investigation Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS059)" styleId ="NBS059" onkeyup="checkTextAreaLength(this, 2000)" title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation."/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
