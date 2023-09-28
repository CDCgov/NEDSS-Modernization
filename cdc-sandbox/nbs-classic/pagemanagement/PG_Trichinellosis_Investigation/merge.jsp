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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Signs and Symptoms","Laboratory","Exposure","Contact Investigation","Retired Data Elements"};
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
<html:select name="PageForm" property="pageClientVO.answer(DEM113)" styleId="DEM113" title="Patient's current sex." onchange="ruleEnDisDEM1138545();ruleHideUnhINV1788552();">
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
<html:select name="PageForm" property="pageClientVO.answer(DEM127)" styleId="DEM127" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1278542();">
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_32" name="Occupation and Industry Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_32errorMessages">
<b> <a name="NBS_UI_32errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_32"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_32">
<tr id="patternNBS_UI_32" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_32" onkeypress="viewClicked(this.id,'NBS_UI_32');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_32');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_32" onkeypress="editClicked(this.id,'NBS_UI_32');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_32');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_32" onkeypress="deleteClicked(this.id,'NBS_UI_32','patternNBS_UI_32','questionbodyNBS_UI_32');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_32','patternNBS_UI_32','questionbodyNBS_UI_32');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_32">
<tr id="nopatternNBS_UI_32" class="odd" style="display:">
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
<span class="NBS_UI_32 InputFieldLabel" id="85659_1L" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation.">
Current Occupation Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(85659_1)" styleId="85659_1" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation." onchange="unhideBatchImg('NBS_UI_32');">
<nedss:optionsCollection property="codedValue(PHVS_OCCUPATION_CDC_CENSUS2010)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="85658_3L" title="This data element is used to capture the narrative text of a subjects current occupation.">
Current Occupation:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(85658_3)" styleId ="85658_3" onkeyup="checkTextAreaLength(this, 199)" title="This data element is used to capture the narrative text of a subjects current occupation."/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_32 InputFieldLabel" id="85657_5L" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry.">
Current  Industry Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(85657_5)" styleId="85657_5" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry." onchange="unhideBatchImg('NBS_UI_32');">
<nedss:optionsCollection property="codedValue(PHVS_INDUSTRY_CDC_CENSUS2010)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="85078_4L" title="This data element is used to capture the narrative text of subjects current industry.">
Current Industry:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(85078_4)" styleId ="85078_4" onkeyup="checkTextAreaLength(this, 199)" title="This data element is used to capture the narrative text of subjects current industry."/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_32">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_32BatchAddFunction()) writeQuestion('NBS_UI_32','patternNBS_UI_32','questionbodyNBS_UI_32')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_32">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_32BatchAddFunction()) writeQuestion('NBS_UI_32','patternNBS_UI_32','questionbodyNBS_UI_32')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_32"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_32BatchAddFunction()) writeQuestion('NBS_UI_32','patternNBS_UI_32','questionbodyNBS_UI_32')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_32"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_32')"/>&nbsp;	&nbsp;&nbsp;
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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV128)" styleId="INV128" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288540();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328546();">
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
Admission Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV132)" maxlength="10" size="10" styleId="INV132" title="Subject's admission date to the hospital for the condition covered by the investigation." onkeyup="DateMask(this,null,event)" onblur="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')" onchange="pgCalcDaysInHosp('INV132', 'INV133', 'INV134')"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV132','INV132Icon'); return false;" styleId="INV132Icon" onkeypress="showCalendarEnterKey('INV132','INV132Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV133L" title="Subject's discharge date from the hospital for the condition covered by the investigation.">
Discharge Date:</span>
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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Condition" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV136L" title="Date of diagnosis of condition being reported to public health system.">
Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV136)" maxlength="10" size="10" styleId="INV136" title="Date of diagnosis of condition being reported to public health system." onkeyup="DateMask(this,null,event)"/>
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
<span class=" InputFieldLabel" id="INV178L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178" title="Assesses whether or not the patient is pregnant." onchange="ruleHideUnhINV1788552();">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV145L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV145)" styleId="INV145" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1458541();">
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
<nedss:container id="NBS_UI_25" name="Epi-Link" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV217L" title="Is this case epi-linked to another confirmed or probable case?">
Is this case epi-linked to another confirmed or probable case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV217)" styleId="INV217" title="Is this case epi-linked to another confirmed or probable case?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

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
<html:select name="PageForm" property="pageClientVO.answer(INV150)" styleId="INV150" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1508543();">
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
<html:select name="PageForm" property="pageClientVO.answer(INV152)" styleId="INV152" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1528544();">
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
<html:select name="PageForm" property="pageClientVO.answer(INV502)" styleId="INV502" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');ruleEnDisINV5028550();getDWRStatesByCountry(this, 'INV503');">
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
<html:select name="PageForm" property="pageClientVO.answer(NOT120)" styleId="NOT120" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1208551();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NOT120SPECL" title="This field is for local use to describe any phone contact with CDC regarding this Immediate National Notifiable Condition.">
If yes, describe:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NOT120SPEC)" maxlength="100" title="This field is for local use to describe any phone contact with CDC regarding this Immediate National Notifiable Condition." styleId="NOT120SPEC"/>
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
<nedss:container id="NBS_UI_35" name="Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386789004L" title="Did the patient have eosinophilia?">
Eosinophilia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386789004)" styleId="386789004" title="Did the patient have eosinophilia?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="386661006L" title="Did the patient have a fever?">
Fever:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(386661006)" styleId="386661006" title="Did the patient have a fever?" onchange="ruleEnDis3866610068560();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV202L" title="What was the patient's highest measured temperature during this illness?">
Highest Measured Temperature:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV202)" size="10" maxlength="10"  title="What was the patient's highest measured temperature during this illness?" styleId="INV202" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this);pgCheckFieldMinMax(this,30,110)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(INV202Unit)" styleId="INV202UNIT">
<nedss:optionsCollection property="codedValue(PHVS_TEMP_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68962001L" title="Did the patient have myalgia or muscle pain?">
Myalgia/Muscle Pain:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(68962001)" styleId="68962001" title="Did the patient have myalgia or muscle pain?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49563000L" title="Did the patient have periorbital edema?">
Periorbital Edema:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(49563000)" styleId="49563000" title="Did the patient have periorbital edema?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other Symptom(s):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS338)" styleId="NBS338" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleEnDisNBS3388559();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS338_OTHL" title="Specify other signs and symptoms.">
Specify Other Symptom(s):</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS338_OTH)" styleId ="NBS338_OTH" onkeyup="checkTextAreaLength(this, 100)" title="Specify other signs and symptoms."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408554();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_43" name="Interpretative Lab Data Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_43errorMessages">
<b> <a name="NBS_UI_43errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_43"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_43">
<tr id="patternNBS_UI_43" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_43" onkeypress="viewClicked(this.id,'NBS_UI_43');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_43');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_43" onkeypress="editClicked(this.id,'NBS_UI_43');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_43');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_43" onkeypress="deleteClicked(this.id,'NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_43">
<tr id="nopatternNBS_UI_43" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_43 InputFieldLabel" id="INV290L" title="Epidemiologic interpretation of the type of test(s) performed for this case">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Epidemiologic interpretation of the type of test(s) performed for this case" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('INV290');">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Epidemiologic interpretation of the type of test(s) performed for this case" id="INV290OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Epidemiologic interpretation of the type of test(s) performed for this case" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="INV291L" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g, positive, detected, negative)">
Test Result Qualitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  (e.g, positive, detected, negative)" onchange="unhideBatchImg('NBS_UI_43');">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTINTERPRETATION_TRICH)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628L" title="Quantitative Test Result Value">
Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB628)" maxlength="10" title="Quantitative Test Result Value" styleId="LAB628" onkeyup="unhideBatchImg('NBS_UI_43');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="667469L" title="This indicates the type of specimen tested">
Specimen Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(667469)" styleId="667469" title="This indicates the type of specimen tested" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('667469');">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="This indicates the type of specimen tested" id="667469OthL">Other Specimen Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(667469Oth)" size="40" maxlength="40" title="Other This indicates the type of specimen tested" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="667469Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date" onkeyup="unhideBatchImg('NBS_UI_43');DateMask(this,null,event)" styleClass="NBS_UI_43"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_43');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB217L" title="The date the specimen/isolate was tested. Time of analysis is an optional addition to date.">
Specimen Analyzed Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB217)" maxlength="10" size="10" styleId="LAB217" title="The date the specimen/isolate was tested. Time of analysis is an optional addition to date." onkeyup="unhideBatchImg('NBS_UI_43');DateMask(this,null,event)" styleClass="NBS_UI_43"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB217','LAB217Icon'); unhideBatchImg('NBS_UI_43');return false;" styleId="LAB217Icon" onkeypress="showCalendarEnterKey('LAB217','LAB217Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="LAB606L" title="Enter the performing laboratory type">
Performing Laboratory Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB606)" styleId="LAB606" title="Enter the performing laboratory type" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('LAB606');">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Enter the performing laboratory type" id="LAB606OthL">Other Performing Laboratory Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB606Oth)" size="40" maxlength="40" title="Other Enter the performing laboratory type" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="LAB606Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_43 InputFieldLabel" id="NBS212L" title="If the specimen was sent for strain identification, indicate the strain">
Strain Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS212)" styleId="NBS212" title="If the specimen was sent for strain identification, indicate the strain" onchange="unhideBatchImg('NBS_UI_43');enableOrDisableOther('NBS212');">
<nedss:optionsCollection property="codedValue(PHVS_STRAINTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If the specimen was sent for strain identification, indicate the strain" id="NBS212OthL">Other Strain Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS212Oth)" size="40" maxlength="40" title="Other If the specimen was sent for strain identification, indicate the strain" onkeyup="unhideBatchImg('NBS_UI_43')" styleId="NBS212Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_43">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_43">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_43"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_43BatchAddFunction()) writeQuestion('NBS_UI_43','patternNBS_UI_43','questionbodyNBS_UI_43')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_43"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_43')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Eosinophilia Lab" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB711L" title="Was testing performed for eosinophilia?">
Was testing performed for eosinophilia?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB711)" styleId="LAB711" title="Was testing performed for eosinophilia?" onchange="ruleEnDisLAB7118555();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="LAB665L" title="Eosinophil value (absolute number or percentage)">
Eosinophil value (absolute number or percentage):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB665)" size="10" maxlength="10"  title="Eosinophil value (absolute number or percentage)" styleId="LAB665" onkeyup="isTemperatureCharEntered(this)" onblur="isTemperatureEntered(this)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(LAB665Unit)" styleId="LAB665UNIT">
<nedss:optionsCollection property="codedValue(PHVS_EOSINOPHILUNITS_CDC_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37" name="Food Exposure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the 8 WEEKS before symptom onset/diagnosis:</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS406L" title="Did the patient consume meat suspected of making the patient ill?">
Did the patient consume meat item(s) suspected of making the patient ill?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS406)" styleId="NBS406" title="Did the patient consume meat suspected of making the patient ill?" onchange="ruleEnDisNBS4068553();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_38" name="Food Exposure History" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_38errorMessages">
<b> <a name="NBS_UI_38errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_38"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_38">
<tr id="patternNBS_UI_38" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_38" onkeypress="viewClicked(this.id,'NBS_UI_38');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_38');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_38" onkeypress="editClicked(this.id,'NBS_UI_38');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_38');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_38" onkeypress="deleteClicked(this.id,'NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_38">
<tr id="nopatternNBS_UI_38" class="odd" style="display:">
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
<span class="NBS_UI_38 InputFieldLabel" id="INV1009L" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill">
Meat Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV1009)" styleId="INV1009" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV1009');">
<nedss:optionsCollection property="codedValue(PHVS_MEATCONSUMEDTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" id="INV1009OthL">Other Meat Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV1009Oth)" size="40" maxlength="40" title="Other Specify type of suspect meat consumed i.e., meat items consumed in the eight weeks before symptom onset or diagnosis (use earlier date) suspected of making the person ill" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV1009Oth"/></td></tr>

<!--processing ReadOnly Textbox Text Question-->
<tr> <td class="fieldName">
<span title="Other Meat Type" id="NBS528L">
(Legacy) Other Meat Type:</span>
</td>
<td>
<span id="NBS528S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS528)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS528)" styleId="NBS528" />
</td>
</tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV967L" title="Date suspected meat was eaten">
Consumed Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV967)" maxlength="10" size="10" styleId="INV967" title="Date suspected meat was eaten" onkeyup="unhideBatchImg('NBS_UI_38');DateMask(this,null,event)" styleClass="NBS_UI_38"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV967','INV967Icon'); unhideBatchImg('NBS_UI_38');return false;" styleId="INV967Icon" onkeypress="showCalendarEnterKey('INV967','INV967Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV969L" title="Where was the suspected meat obtained?">
Meat Obtained:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV969)" styleId="INV969" title="Where was the suspected meat obtained?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV969');">
<nedss:optionsCollection property="codedValue(PHVS_MEATPURCHASEINFO_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspected meat obtained?" id="INV969OthL">Other Meat Obtained:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV969Oth)" size="40" maxlength="40" title="Other Where was the suspected meat obtained?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV969Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV968L" title="Meat preparation (further food processing)">
Method of Meat Preparation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV968)" styleId="INV968" title="Meat preparation (further food processing)" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV968');">
<nedss:optionsCollection property="codedValue(PHVS_FOODPROCESSINGMETHOD_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Meat preparation (further food processing)" id="INV968OthL">Other Method of Meat Preparation:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV968Oth)" size="40" maxlength="40" title="Other Meat preparation (further food processing)" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV968Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV970L" title="What method of cooking was used on suspected meat?">
Method of Cooking:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV970)" styleId="INV970" title="What method of cooking was used on suspected meat?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV970');">
<nedss:optionsCollection property="codedValue(PHVS_FOODCOOKINGMETHOD_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What method of cooking was used on suspected meat?" id="INV970OthL">Other Method of Cooking:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV970Oth)" size="40" maxlength="40" title="Other What method of cooking was used on suspected meat?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV970Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV971L" title="Was larva observed in suspected meat by microscopy?">
Larva Present in Meat:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV971)" styleId="INV971" title="Was larva observed in suspected meat by microscopy?" onchange="unhideBatchImg('NBS_UI_38');ruleEnDisINV9718561();enableOrDisableOther('INV972');">
<nedss:optionsCollection property="codedValue(PHVS_PRESENTABSENTUNKNOTEXAMINED_CDC_TRICH)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="INV972L" title="Where was the suspected meat tested?">
Where was the Meat Tested:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV972)" styleId="INV972" title="Where was the suspected meat tested?" onchange="unhideBatchImg('NBS_UI_38');enableOrDisableOther('INV972');">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_TRICH)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspected meat tested?" id="INV972OthL">Other Where was the Meat Tested:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV972Oth)" size="40" maxlength="40" title="Other Where was the suspected meat tested?" onkeyup="unhideBatchImg('NBS_UI_38')" styleId="INV972Oth"/></td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV973L" title="Use this field, if needed, to communicate anything unusual about the suspect meat, which is not already covered with the other data elements (e.g., additional details about where eaten, if consumed while traveling outside of the U.S., where wild game was hunted, if meat was stored frozen and/or if leftovers are available for testing, etc.)">
Suspect Meat Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(INV973)" styleId ="INV973" onkeyup="checkTextAreaLength(this, 199)" title="Use this field, if needed, to communicate anything unusual about the suspect meat, which is not already covered with the other data elements (e.g., additional details about where eaten, if consumed while traveling outside of the U.S., where wild game was hunted, if meat was stored frozen and/or if leftovers are available for testing, etc.)"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_38">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_38">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_38"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_38BatchAddFunction()) writeQuestion('NBS_UI_38','patternNBS_UI_38','questionbodyNBS_UI_38')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_38"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_38')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Travel History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL02L" title="In the 8 weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their state or country of residence?">
In 8 weeks prior to illness, did patient travel outside his/her county/state/country of residence?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL02)" styleId="TRAVEL02" title="In the 8 weeks before symptom onset or diagnosis (use earlier date), did the patient travel out of their state or country of residence?" onchange="ruleEnDisTRAVEL028558();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_40" name="Travel History Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_40errorMessages">
<b> <a name="NBS_UI_40errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_40"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_40">
<tr id="patternNBS_UI_40" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_40" onkeypress="viewClicked(this.id,'NBS_UI_40');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_40');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_40" onkeypress="editClicked(this.id,'NBS_UI_40');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_40');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_40" onkeypress="deleteClicked(this.id,'NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_40">
<tr id="nopatternNBS_UI_40" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_40 InputFieldLabel" id="NBS454L" title="Domestic vs International Travel">
Was the travel domestic or international?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS454)" styleId="NBS454" title="Domestic vs International Travel" onchange="unhideBatchImg('NBS_UI_40');ruleEnDisNBS4548557();ruleEnDisNBS4548556();">
<nedss:optionsCollection property="codedValue(PHVSFB_DOMINTNL)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="TRAVEL05L" title="List any international destinations of recent travel">
Country of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL05)" styleId="TRAVEL05" title="List any international destinations of recent travel" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="82754_3L" title="Domestic destination, states traveled">
State of Travel:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(82754_3)" styleId="82754_3" title="Domestic destination, states traveled">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="82753_5L" title="Intrastate destination, counties traveled">
County of Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(82753_5)" styleId="82753_5" title="Intrastate destination, counties traveled" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PHVS_COUNTY_FIPS_6-4)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="NBS453L" title="Choose the mode of travel">
Travel Mode:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS453)" styleId="NBS453" title="Choose the mode of travel" onchange="unhideBatchImg('NBS_UI_40');">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELMODE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_40 InputFieldLabel" id="TRAVEL16L" title="Principal Reason for Travel">
Principal Reason for Travel:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL16)" styleId="TRAVEL16" title="Principal Reason for Travel" onchange="unhideBatchImg('NBS_UI_40');enableOrDisableOther('TRAVEL16');">
<nedss:optionsCollection property="codedValue(PHVS_TRAVELREASON_MALARIA)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Principal Reason for Travel" id="TRAVEL16OthL">Other Principal Reason for Travel:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(TRAVEL16Oth)" size="40" maxlength="40" title="Other Principal Reason for Travel" onkeyup="unhideBatchImg('NBS_UI_40')" styleId="TRAVEL16Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL06L" title="If the patient traveled, when did they arrive to their travel destination?">
Date of Arrival at Destination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL06)" maxlength="10" size="10" styleId="TRAVEL06" title="If the patient traveled, when did they arrive to their travel destination?" onkeyup="unhideBatchImg('NBS_UI_40');DateMask(this,null,event)" styleClass="NBS_UI_40"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL06','TRAVEL06Icon'); unhideBatchImg('NBS_UI_40');return false;" styleId="TRAVEL06Icon" onkeypress="showCalendarEnterKey('TRAVEL06','TRAVEL06Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="TRAVEL07L" title="If the patient traveled, when did they depart from their travel destination?">
Date of Departure from Destination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(TRAVEL07)" maxlength="10" size="10" styleId="TRAVEL07" title="If the patient traveled, when did they depart from their travel destination?" onkeyup="unhideBatchImg('NBS_UI_40');DateMask(this,null,event)" styleClass="NBS_UI_40"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('TRAVEL07','TRAVEL07Icon'); unhideBatchImg('NBS_UI_40');return false;" styleId="TRAVEL07Icon" onkeypress="showCalendarEnterKey('TRAVEL07','TRAVEL07Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="82310_4L" title="Duration of Stay">
Duration of Stay:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(82310_4)" size="3" maxlength="3"  title="Duration of Stay" styleId="82310_4" onkeyup="unhideBatchImg('NBS_UI_40');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_40"/>
<html:select name="PageForm" property="pageClientVO.answer(82310_4Unit)" styleId="82310_4UNIT" onchange="unhideBatchImg('NBS_UI_40')">
<nedss:optionsCollection property="codedValue(PHVS_DURATIONUNIT_CDC_1)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="TRAVEL23L" title="Additional Travel Information">
Additional Travel Information:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(TRAVEL23)" styleId ="TRAVEL23" onkeyup="checkTextAreaLength(this, 2000)" title="Additional Travel Information"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_40">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_40">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_40"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_40BatchAddFunction()) writeQuestion('NBS_UI_40','patternNBS_UI_40','questionbodyNBS_UI_40')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_40"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_40')"/>&nbsp;	&nbsp;&nbsp;
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

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101000" name="Legacy Animal Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV667L" title="Did the case have contact with an animal?">
(Legacy) Did the case have contact with an animal?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV667)" styleId="INV667" title="Did the case have contact with an animal?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_32L" title="Animal contact by type of animal">
(Legacy) Animal Type:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_32)" styleId="FDD_Q_32" title="Animal contact by type of animal"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_32-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_ANIMALST)" value="key" label="value" /> </html:select>
<div id="FDD_Q_32-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_243L" title="If Other, please specify other type of animal">
(Legacy) If Other, please specify other type of animal:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_243)" maxlength="50" title="If Other, please specify other type of animal" styleId="FDD_Q_243"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_295L" title="If Other Amphibian, please specify other type of amphibian">
(Legacy) If Other Amphibian, please specify other type of amphibian:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_295)" maxlength="50" title="If Other Amphibian, please specify other type of amphibian" styleId="FDD_Q_295"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_296L" title="If Other Reptile, please specify other type of reptile">
(Legacy) If Other Reptile, please specify other type of reptile:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_296)" maxlength="50" title="If Other Reptile, please specify other type of reptile" styleId="FDD_Q_296"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_374L" title="If Other Mammal, please specify other type of mammal">
(Legacy) If Other Mammal, please specify other type of mammal:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_374)" maxlength="50" title="If Other Mammal, please specify other type of mammal" styleId="FDD_Q_374"/>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_33L" title="What was the name of the farm, ranch, petting zoo, or other setting that has farm animals?">
(Legacy) Animal Contact Location:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(FDD_Q_33)" styleId ="FDD_Q_33" onkeyup="checkTextAreaLength(this, 200)" title="What was the name of the farm, ranch, petting zoo, or other setting that has farm animals?"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_34L" title="Did the patient acquire a pet prior to onset of illness?">
(Legacy) Did the patient acquire a pet prior to onset of illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_34)" styleId="FDD_Q_34" title="Did the patient acquire a pet prior to onset of illness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_244L" title="Applicable incubation period for this illness">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_244)" maxlength="50" title="Applicable incubation period for this illness" styleId="FDD_Q_244"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101001" name="Legacy Underlying Conditions" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_233L" title="Did patient have any of the following underlying conditions?">
(Legacy) Did patient have any of the following underlying conditions?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_233)" styleId="FDD_Q_233" title="Did patient have any of the following underlying conditions?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_233-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_DISEASES)" value="key" label="value" /> </html:select>
<div id="FDD_Q_233-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_234L" title="If Other Prior Illness, please specify">
(Legacy) If Other Prior Illness, please specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_234)" maxlength="50" title="If Other Prior Illness, please specify" styleId="FDD_Q_234"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_235L" title="If Diabetes Mellitus, specify whether on insulin">
(Legacy) If Diabetes Mellitus, specify whether on insulin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_235)" styleId="FDD_Q_235" title="If Diabetes Mellitus, specify whether on insulin">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_237L" title="If Gastric Surgery, please specify type">
(Legacy) If Gastric Surgery, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_237)" maxlength="50" title="If Gastric Surgery, please specify type" styleId="FDD_Q_237"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_238L" title="If Hematologic Disease, please specify type">
(Legacy) If Hematologic Disease, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_238)" maxlength="50" title="If Hematologic Disease, please specify type" styleId="FDD_Q_238"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_239L" title="If Immunodeficiency, please specify type:">
(Legacy) If Immunodeficiency, please specify type::</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_239)" maxlength="50" title="If Immunodeficiency, please specify type:" styleId="FDD_Q_239"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_240L" title="If Other Liver Disease, please specify type">
(Legacy) If Other Liver Disease, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_240)" maxlength="50" title="If Other Liver Disease, please specify type" styleId="FDD_Q_240"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_241L" title="If Other Malignancy, please specify type">
(Legacy) If Other Malignancy, please specify type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_241)" maxlength="50" title="If Other Malignancy, please specify type" styleId="FDD_Q_241"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_242L" title="If Other Renal Disease, please specify">
(Legacy) If Other Renal Disease, please specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_242)" maxlength="50" title="If Other Renal Disease, please specify" styleId="FDD_Q_242"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_236L" title="If Organ Transplant, please specify organ">
(Legacy) If Organ Transplant, please specify organ:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_236)" maxlength="50" title="If Organ Transplant, please specify organ" styleId="FDD_Q_236"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101002" name="Legacy Related Cases" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_77L" title="Does the patient know of other similarly ill persons?">
(Legacy) Does the patient know of other similarly ill persons?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_77)" styleId="FDD_Q_77" title="Does the patient know of other similarly ill persons?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_78L" title="If Yes, did the health department collect contact information about other similarly ill persons and investigate further?">
(Legacy) If Yes, did the health department collect contact information about other similarly ill persons and:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_78)" styleId="FDD_Q_78" title="If Yes, did the health department collect contact information about other similarly ill persons and investigate further?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_79L" title="Are there other cases related to this one?">
(Legacy) Are there other cases related to this one?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_79)" styleId="FDD_Q_79" title="Are there other cases related to this one?">
<nedss:optionsCollection property="codedValue(PHVSFB_EPIDEMGY)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102002" name="Legacy Suspect Food" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_139L" title="What suspect foods did the patient eat?">
(Legacy) What suspect foods did the patient eat?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_139)" styleId="FDD_Q_139" title="What suspect foods did the patient eat?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_139-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_PORKONOT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_139-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_144L" title="Where was the suspect (Pork) meat obtained?">
(Legacy) Where was the suspect (Pork) meat obtained?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_144)" styleId="FDD_Q_144" title="Where was the suspect (Pork) meat obtained?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_144-selectedValues');enableOrDisableOther('FDD_Q_144')" >
<nedss:optionsCollection property="codedValue(PHVSFB_SOURCEMT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_144-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspect (Pork) meat obtained?" id="FDD_Q_144OthL">Other (Legacy) Where was the suspect (Pork) meat obtained?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(FDD_Q_144Oth)" size="40" maxlength="40" title="Other Where was the suspect (Pork) meat obtained?" styleId="FDD_Q_144Oth"/></td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_154L" title="Where was the suspect (Non-Pork) meat obtained?">
(Legacy) Where was the suspect (Non-Pork) meat obtained?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_154)" styleId="FDD_Q_154" title="Where was the suspect (Non-Pork) meat obtained?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_154-selectedValues');enableOrDisableOther('FDD_Q_154')" >
<nedss:optionsCollection property="codedValue(PHVSFB_SOURCEMT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_154-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Where was the suspect (Non-Pork) meat obtained?" id="FDD_Q_154OthL">Other (Legacy) Where was the suspect (Non-Pork) meat obtained?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(FDD_Q_154Oth)" size="40" maxlength="40" title="Other Where was the suspect (Non-Pork) meat obtained?" styleId="FDD_Q_154Oth"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102000" name="Legacy Food Handler" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_8L" title="Did patient work as a food handler after onset of illness?">
(Legacy) Did patient work as a food handler after onset of illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(FDD_Q_8)" styleId="FDD_Q_8" title="Did patient work as a food handler after onset of illness?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="FDD_Q_9L" title="What was last date worked as a food handler after onset of illness?">
(Legacy) What was last date worked as a food handler after onset of illness?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(FDD_Q_9)" maxlength="10" size="10" styleId="FDD_Q_9" title="What was last date worked as a food handler after onset of illness?" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('FDD_Q_9','FDD_Q_9Icon'); return false;" styleId="FDD_Q_9Icon" onkeypress="showCalendarEnterKey('FDD_Q_9','FDD_Q_9Icon',event)"></html:img>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_10L" title="Where was patient a food handler?">
(Legacy) Where was patient a food handler?:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_10)" maxlength="50" title="Where was patient a food handler?" styleId="FDD_Q_10"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102001" name="Legacy Travel History" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_12L" title="Applicable incubation period for this illness">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_12)" maxlength="50" title="Applicable incubation period for this illness" styleId="FDD_Q_12"/>
</td> </tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="FDD_Q_13L" title="What was the purpose of the travel?">
(Legacy) What was the purpose of the travel?:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(FDD_Q_13)" styleId="FDD_Q_13" title="What was the purpose of the travel?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'FDD_Q_13-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVSFB_TRAVELTT)" value="key" label="value" /> </html:select>
<div id="FDD_Q_13-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_14L" title="If Other, please specify other purpose of travel">
(Legacy) If Other, please specify other purpose of travel:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_14)" maxlength="50" title="If Other, please specify other purpose of travel" styleId="FDD_Q_14"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_20L" title="If more than 3 destinations, specify details here">
(Legacy) If more than 3 destinations, specify details here:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_20)" maxlength="50" title="If more than 3 destinations, specify details here" styleId="FDD_Q_20"/>
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
