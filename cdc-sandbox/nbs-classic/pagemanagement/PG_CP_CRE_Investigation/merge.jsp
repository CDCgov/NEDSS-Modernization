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
String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Travel","Laboratory","Previous History","Contact Investigation"};
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
<html:select name="PageForm" property="pageClientVO.answer(DEM113)" styleId="DEM113" title="Patient's current sex." onchange="ruleEnDisDEM1138794();ruleHideUnhINV1788801();">
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
<html:select name="PageForm" property="pageClientVO.answer(DEM127)" styleId="DEM127" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1278791();">
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
<nedss:container id="NBS_UI_38" name="Industry and Occupation Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_38 InputFieldLabel" id="85659_1L" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation.">
Current Occupation Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(85659_1)" styleId="85659_1" title="This data element is used to capture the CDC NIOSH standard occupation code based upon the narrative text of a subjects current occupation." onchange="unhideBatchImg('NBS_UI_38');">
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
<span class="NBS_UI_38 InputFieldLabel" id="85657_5L" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry.">
Current  Industry Standardized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(85657_5)" styleId="85657_5" title="This data element is used to capture the CDC NIOSH standard industry code based upon the narrative text of a subjects current industry." onchange="unhideBatchImg('NBS_UI_38');">
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
<html:select name="PageForm" property="pageClientVO.answer(INV128)" styleId="INV128" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1288789();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1328795();">
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
<html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178" title="Assesses whether or not the patient is pregnant." onchange="ruleHideUnhINV1788801();">
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
<html:select name="PageForm" property="pageClientVO.answer(INV145)" styleId="INV145" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1458790();">
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
<html:select name="PageForm" property="pageClientVO.answer(INV150)" styleId="INV150" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1508792();">
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
<html:select name="PageForm" property="pageClientVO.answer(INV152)" styleId="INV152" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1528793();">
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
<html:select name="PageForm" property="pageClientVO.answer(INV502)" styleId="INV502" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS_UI_NBS_INV_GENV2_UI_4');ruleEnDisINV5028799();getDWRStatesByCountry(this, 'INV503');">
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
<html:select name="PageForm" property="pageClientVO.answer(NOT120)" styleId="NOT120" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1208800();">
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
<nedss:container id="NBS_UI_34" name="Travel and Healthcare" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;In the YEAR prior to specimen collection:</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL38L" title="Did the patient travel internationally in the past 1 year from the date of specimen collection?">
Did the patient travel internationally?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(TRAVEL38)" styleId="TRAVEL38" title="Did the patient travel internationally in the past 1 year from the date of specimen collection?" onchange="ruleEnDisTRAVEL388802();ruleEnDis90366_68803();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="TRAVEL05L" title="List any international destinations of recent travel">
International Destination(s):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(TRAVEL05)" styleId="TRAVEL05" title="List any international destinations of recent travel"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'TRAVEL05-selectedValues')" >
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
<div id="TRAVEL05-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="90366_6L" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection.">
Received Healthcare Outside the USA:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(90366_6)" styleId="90366_6" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection." onchange="ruleEnDis90366_68803();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="91514_0L" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection.">
Countries in which healthcare was received:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(91514_0)" styleId="91514_0" title="This data element is used to capture if the patient received healthcare outside of the United States in the year prior to the date of specimen collection."
multiple="true" size="4"
onchange="displaySelectedOptions(this, '91514_0-selectedValues')" >
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
<div id="91514_0-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_36" name="Laboratory Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done to confirm the diagnosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408804();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37" name="Laboratory Testing Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_37errorMessages">
<b> <a name="NBS_UI_37errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_37"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_37">
<tr id="patternNBS_UI_37" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_37" onkeypress="viewClicked(this.id,'NBS_UI_37');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_37');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_37" onkeypress="editClicked(this.id,'NBS_UI_37');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_37');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_37" onkeypress="deleteClicked(this.id,'NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_37">
<tr id="nopatternNBS_UI_37" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS674L" title="Unique isolate or specimen identifier; ID assigned by the local laboratory. Isolate ID is preferred, but if unknown specimen ID is acceptable.">
Performing Lab Specimen or Isolate ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS674)" maxlength="25" title="Unique isolate or specimen identifier; ID assigned by the local laboratory. Isolate ID is preferred, but if unknown specimen ID is acceptable." styleId="NBS674" onkeyup="unhideBatchImg('NBS_UI_37');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="FDD_Q_1141L" title="Lab isolate identifier from public health lab for mechanism testing">
State Lab Isolate ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(FDD_Q_1141)" maxlength="25" title="Lab isolate identifier from public health lab for mechanism testing" styleId="FDD_Q_1141" onkeyup="unhideBatchImg('NBS_UI_37');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV949L" title="NCBI SRA Accession Number (SRX#), the accession number generated by NCBI’s Sequence Read Archive when sequence data are uploaded to NCBI. This provides both the sequence data and metadata on how the sample was sequenced.">
NCBI SRA Accession Number (SRX #):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV949)" maxlength="25" title="NCBI SRA Accession Number (SRX#), the accession number generated by NCBI’s Sequence Read Archive when sequence data are uploaded to NCBI. This provides both the sequence data and metadata on how the sample was sequenced." styleId="INV949" onkeyup="unhideBatchImg('NBS_UI_37');"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date." onkeyup="unhideBatchImg('NBS_UI_37');DateMask(this,null,event)" styleClass="NBS_UI_37"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_37');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="667469L" title="This indicates the type of specimen tested.">
Specimen Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(667469)" styleId="667469" title="This indicates the type of specimen tested." onchange="unhideBatchImg('NBS_UI_37');">
<nedss:optionsCollection property="codedValue(SPECIMEN_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="INV290L" title="Epidemiologic interpretation of the type of test(s) performed for this case.">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Epidemiologic interpretation of the type of test(s) performed for this case." onchange="unhideBatchImg('NBS_UI_37');enableOrDisableOther('INV290');">
<nedss:optionsCollection property="codedValue(LAB_TEST_TYPE_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290OthL">Other Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Epidemiologic interpretation of the type of test(s) performed for this case." onkeyup="unhideBatchImg('NBS_UI_37')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="INV291L" title="Result of diagnostic test">
Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Result of diagnostic test" onchange="unhideBatchImg('NBS_UI_37');">
<nedss:optionsCollection property="codedValue(TEST_RESULT_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_37 InputFieldLabel" id="LAB278L" title="Species identified through testing">
Organism Name:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB278)" styleId="LAB278" title="Species identified through testing" onchange="unhideBatchImg('NBS_UI_37');enableOrDisableOther('LAB278');">
<nedss:optionsCollection property="codedValue(ORGANISM_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Species identified through testing" id="LAB278OthL">Other Organism Name:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB278Oth)" size="40" maxlength="40" title="Other Species identified through testing" onkeyup="unhideBatchImg('NBS_UI_37')" styleId="LAB278Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="85069_3L" title="Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case.">
Test Method:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(85069_3)" styleId="85069_3" title="Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case." onchange="unhideBatchImg('NBS_UI_37');enableOrDisableOther('85069_3');">
<nedss:optionsCollection property="codedValue(LAB_TEST_METHOD_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case." id="85069_3OthL">Other Test Method:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(85069_3Oth)" size="40" maxlength="40" title="Other Type of diagnostic test(s) performed for this subject: Complete a minimum of 1 repeat for a case. It is preferable to send a phenotypic and a molecular test for a case." onkeyup="unhideBatchImg('NBS_UI_37')" styleId="85069_3Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_37 InputFieldLabel" id="48018_6L" title="Gene identifier">
Gene Identifier:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(48018_6)" styleId="48018_6" title="Gene identifier" onchange="unhideBatchImg('NBS_UI_37');enableOrDisableOther('48018_6');">
<nedss:optionsCollection property="codedValue(GENE_NAME_CP_CRE)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Gene identifier" id="48018_6OthL">Other Gene Identifier:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(48018_6Oth)" size="40" maxlength="40" title="Other Gene identifier" onkeyup="unhideBatchImg('NBS_UI_37')" styleId="48018_6Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_37">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_37BatchAddFunction()) writeQuestion('NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_37">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_37BatchAddFunction()) writeQuestion('NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_37"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_37BatchAddFunction()) writeQuestion('NBS_UI_37','patternNBS_UI_37','questionbodyNBS_UI_37')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_37"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_37')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_39" name="Specimen Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;If multiple specimens were collected, County/State should be for the first positive specimen associated with this incident of disease.</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="89202_6L" title="County of facility where specimen was collected">
County of facility where specimen was collected:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(89202_6)" styleId="89202_6" title="County of facility where specimen was collected">
<nedss:optionsCollection property="codedValue(PHVS_COUNTY_FIPS_6-4)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="68488_6L" title="State of facility where specimen was collected">
State of facility where specimen was collected:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(68488_6)" styleId="68488_6" title="State of facility where specimen was collected">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42" name="Previously Counted Case" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV1109L" title="Clinical CP-CRE Only: Was patient previously counted as a colonization/screening case?">
Previously counted as a colonization/screening case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV1109)" styleId="INV1109" title="Clinical CP-CRE Only: Was patient previously counted as a colonization/screening case?" onchange="ruleEnDisINV11098805();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Case IDs" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Please provide the related case ID(s) if patient was previously counted as a colonization/screening case.</span></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_1L" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)">
Previously Reported State Case Number 1:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_1)" maxlength="30" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" styleId="INV1110_1"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_2L" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)">
Previously Reported State Case Number 2:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_2)" maxlength="30" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" styleId="INV1110_2"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_3L" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)">
Previously Reported State Case Number 3:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_3)" maxlength="30" title="If patient was previously counted as a colonization/screening case (clinical CP-CRE only), please provide the related case ID(s)" styleId="INV1110_3"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_4L" title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s)">
Previously Reported State Case Number 4:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_4)" maxlength="30" title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s)" styleId="INV1110_4"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV1110_5L" title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s).">
Previously Reported State Case Number 5:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV1110_5)" maxlength="30" title="If patient was previously counted as a colonization/screening case, please provide the related case ID(s)." styleId="INV1110_5"/>
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
