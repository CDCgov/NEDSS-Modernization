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
String [] sectionNames  = {"Patient Information","Address Information","Telephone and Email Contact Information","Race and Ethnicity Information","Other Identifying Information","Investigation Information","OOJ Initiating Agency Information","Reporting Information","Clinical","Epidemiologic","Comments","Case Numbers","Initial Follow-up","Surveillance","Field Follow-up Information","Case Closure","Congenital Syphilis Information","Maternal Information","Infant Information","Contact Investigation"};
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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM250L" title="The patient's alias or nickname.">
Alias/Nickname:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM250)" maxlength="40" title="The patient's alias or nickname." styleId="DEM250"/>
</td> </tr>
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
<span class=" InputFieldLabel" id="DEM113L" title="Patient's current sex.">
Current Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM113)" styleId="DEM113" title="Patient's current sex." onchange="ruleEnDisDEM1137277();">
<nedss:optionsCollection property="codedValue(SEX)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS272L" title="Patient's current sex if identified as unknown (i.e., not male or female).">
Unknown Reason:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS272)" styleId="NBS272" title="Patient's current sex if identified as unknown (i.e., not male or female).">
<nedss:optionsCollection property="codedValue(SEX_UNK_REASON)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS274L" title="Patient's transgender identity.">
Gender Identity/Transgender Info:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS274)" styleId="NBS274" title="Patient's transgender identity.">
<nedss:optionsCollection property="codedValue(NBS_STD_GENDER_PARPT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS213L" title="The specific gender information of the index patient if other selections do not apply (i.e. intersex, two-spirited, etc.).">
Additional Gender:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS213)" maxlength="30" title="The specific gender information of the index patient if other selections do not apply (i.e. intersex, two-spirited, etc.)." styleId="NBS213"/>
</td> </tr>

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
<span class=" InputFieldLabel" id="DEM126L" title="Country of Birth">
Country of Birth:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM126)" styleId="DEM126" title="Country of Birth">
<nedss:optionsCollection property="codedValue(PHVS_BIRTHCOUNTRY_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM127L" title="Indicator of whether or not a patient is alive or dead.">
Is the patient deceased?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM127)" styleId="DEM127" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1277270();">
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
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS098L">
Marital Status As Of Date:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS098)" maxlength="10" size="10" styleId="NBS098" title="As of Date is the last known date for which the information is valid."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS098','NBS098Icon');return false;" styleId="NBS098Icon" onkeypress="showCalendarEnterKey('NBS098','NBS098Icon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="DEM140L" title="A code indicating the married or similar partnership status of a patient.">
Marital Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(DEM140)" styleId="DEM140" title="A code indicating the married or similar partnership status of a patient.">
<nedss:optionsCollection property="codedValue(P_MARITAL)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="INV178L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV178)" styleId="INV178" title="Assesses whether or not the patient is pregnant." onchange="ruleEnDisINV1787276()">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Numeric Question-->
<tr style="display:none">
<td class="fieldName">
<span class="InputDisabledLabel" id="NBS128L" title="Number of weeks pregnant at the time of diagnosis.">
Weeks:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS128)" size="2" maxlength="2"  title="Number of weeks pregnant at the time of diagnosis." styleId="NBS128" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99);stdCheckFieldMinMaxUnk(this,0,45,99)"/>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM168L" title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts.">
Census Tract:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(DEM168)" maxlength="20" title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts." styleId="DEM168" onblur="checkCensusTract(this)"/>
</td> </tr>

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
<nedss:container id="NBS_INV_STD_UI_2" name="Additional Residence Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS201L" title="The RELATIONSHIP (such as spouse, parents, sibling, partner, roommate, etc., not the name) of those living with the patient.">
Living With:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS201)" maxlength="20" title="The RELATIONSHIP (such as spouse, parents, sibling, partner, roommate, etc., not the name) of those living with the patient." styleId="NBS201"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS202L" title="The type of residence in which the patient currenlty resides.">
Type of Residence:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS202)" styleId="NBS202" title="The type of residence in which the patient currenlty resides.">
<nedss:optionsCollection property="codedValue(RESIDENCE_TYPE_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS203L" title="The length of time the patient has lived at the current address.">
Time at Address:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS203)" size="2" maxlength="2"  title="The length of time the patient has lived at the current address." styleId="NBS203" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS204L" title="Unit if time used to describe time at address.">
Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS204)" styleId="NBS204" title="Unit if time used to describe time at address.">
<nedss:optionsCollection property="codedValue(WKS_MOS_YRS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS205L" title="The length of time the patient has lived in this state/territory.">
Time in State:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS205)" size="2" maxlength="2"  title="The length of time the patient has lived in this state/territory." styleId="NBS205" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS206L" title="Unit if time used to describe time in state.">
Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS206)" styleId="NBS206" title="Unit if time used to describe time in state.">
<nedss:optionsCollection property="codedValue(WKS_MOS_YRS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS207L" title="The length of time the patient has lived in the country.">
Time in Country:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS207)" size="2" maxlength="2"  title="The length of time the patient has lived in the country." styleId="NBS207" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS208L" title="Unit if time used to describe time in country.">
Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS208)" styleId="NBS208" title="Unit if time used to describe time in country.">
<nedss:optionsCollection property="codedValue(WKS_MOS_YRS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS209L" title="Indicate if the patient is institutionalized (i.e., in jail, in a group home, in a mental health facility, etc.)">
Currently institutionalized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS209)" styleId="NBS209" title="Indicate if the patient is institutionalized (i.e., in jail, in a group home, in a mental health facility, etc.)" onchange="ruleHideUnhNBS2097280();ruleRequireIfNBS2097273();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS210L" title="Name of Institutition">
If institutionalized, document the name of the facility.:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS210)" maxlength="40" title="Name of Institutition" styleId="NBS210"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS211L" title="Type of Institutition">
Type of Institutition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS211)" styleId="NBS211" title="Type of Institutition">
<nedss:optionsCollection property="codedValue(INSTITUTION_TYPE_STD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
<html:select name="PageForm" property="pageClientVO.answer(DEM155)" styleId="DEM155" title="Indicates if the patient is hispanic or not." onchange="ruleEnDisDEM1557278();">
<nedss:optionsCollection property="codedValue(PHVS_ETHNICITYGROUP_CDC_UNK)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS273L" title="Specify reason the patient's ethnicity is unknown.">
Reason Unknown:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS273)" styleId="NBS273" title="Specify reason the patient's ethnicity is unknown.">
<nedss:optionsCollection property="codedValue(P_ETHN_UNK_REASON)" value="key" label="value" /></html:select>
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

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_6" name="Other Identifying Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS155L" title="The approximate or specific height of the patient.">
Height:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS155)" maxlength="15" title="The approximate or specific height of the patient." styleId="NBS155"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS156L" title="The approximate or specific weight or body type of the patient.">
Size/Build:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS156)" maxlength="15" title="The approximate or specific weight or body type of the patient." styleId="NBS156"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS157L" title="The description of the patients hair, including color, length, and/or style.">
Hair:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS157)" maxlength="15" title="The description of the patients hair, including color, length, and/or style." styleId="NBS157"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS158L" title="The approximate or specific skin tone/hue of the patient.">
Complexion:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS158)" maxlength="15" title="The approximate or specific skin tone/hue of the patient." styleId="NBS158"/>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS159L" title="Any additional demographic information (e.g., tattoos, etc).">
Other Identifying Info:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS159)" styleId ="NBS159" onkeyup="checkTextAreaLength(this, 2000)" title="Any additional demographic information (e.g., tattoos, etc)."/>
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS110L" title="Document the reason (referral basis) why the investigation was initiated.">
Referral Basis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS110)" styleId="NBS110" title="Document the reason (referral basis) why the investigation was initiated.">
<nedss:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /></html:select>
</td></tr>

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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS115L" title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)">
Current Process Stage:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS115)" styleId="NBS115" title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)">
<nedss:optionsCollection property="codedValue(CM_PROCESS_STAGE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV147L" title="The date the investigation was started or initiated.">
Investigation Start Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV147)" maxlength="10" size="10" styleId="INV147" title="The date the investigation was started or initiated." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('INV147','INV147Icon'); return false;" styleId="INV147Icon" onkeypress ="showCalendarFutureEnterKey('INV147','INV147Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->

<!--processing ReadOnly Date-->
<tr><td class="fieldName">
<span title="The date the investigation is closed." id="INV2006L">Investigation Close Date:</span>
</td><td>
<span id="INV2006S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV2006)"  />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(INV2006)" styleId="INV2006" />
</td>
</tr>

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

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS270L" title="Referral Basis - OOJ">
Referral Basis - OOJ:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS270)" styleId="NBS270" title="Referral Basis - OOJ">
<nedss:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180L" title="The Public Health Investigator assigned to the Investigation.">
Current Investigator:</span> </td>
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
<td class="fieldName" id="INV180S">Current Investigator Selected: </td>
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
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="The date the investigation was assigned/started." id="INV110L">
Date Assigned to Investigation:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV110)" maxlength="10" size="10" styleId="INV110" title="The date the investigation was assigned/started."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV110','INV110Icon');return false;" styleId="INV110Icon" onkeypress="showCalendarEnterKey('INV110','INV110Icon',event);" ></html:img>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_8" name="OOJ Agency Initiating Report" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS111L" title="The Initiating Agency which sent the OOJ Contact Report.">
Initiating Agency:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS111)" styleId="NBS111" title="The Initiating Agency which sent the OOJ Contact Report." onchange="ruleEnDisNBS1117289();ruleEnDisNBS1117274();">
<nedss:optionsCollection property="codedValue(OOJ_AGENCY_LOCAL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS112L" title="The date the OOJ Contact report was received from the Initiating Agency.">
Date Received from Init. Agency:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS112)" maxlength="10" size="10" styleId="NBS112" title="The date the OOJ Contact report was received from the Initiating Agency." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS112','NBS112Icon'); return false;" styleId="NBS112Icon" onkeypress="showCalendarEnterKey('NBS112','NBS112Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS113L" title="The date OOJ outcome is due back to the Initiating Agency.">
Date OOJ Due to Init. Agency:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS113)" maxlength="10" size="10" styleId="NBS113" title="The date OOJ outcome is due back to the Initiating Agency." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS113','NBS113Icon'); return false;" styleId="NBS113Icon" onkeypress ="showCalendarFutureEnterKey('NBS113','NBS113Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS114L" title="The date OOJ outcome was sent back to the Initiating Agency.">
Date OOJ Info Sent:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS114)" maxlength="10" size="10" styleId="NBS114" title="The date OOJ outcome was sent back to the Initiating Agency." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS114','NBS114Icon'); return false;" styleId="NBS114Icon" onkeypress="showCalendarEnterKey('NBS114','NBS114Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_9" name="Reported as OOJ Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS124L" title="The cluster relationship reported by OOJ Contact.">
Relationship:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS124)" styleId="NBS124" title="The cluster relationship reported by OOJ Contact.">
<nedss:optionsCollection property="codedValue(PH_RELATIONSHIP_HL7_2X)" value="key" label="value" /></html:select>
</td></tr>
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
<nedss:container id="NBS_UI_32" name="Physician Clinic" isHidden="F" classType="subSect" >

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS291L" title="The clinic with which the physician associated with this case is affiliated.">
Physician Ordering Clinic:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS291Uid">
<span id="clearNBS291" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS291Uid">
<span id="clearNBS291">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS291CodeClearButton" onclick="clearOrganization('NBS291')"/>
</span>
<span id="NBS291SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS291Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS291Icon" onclick="getReportingOrg('NBS291');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS291)" styleId="NBS291Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('NBS291Text','NBS291_qec_list')"
title="The clinic with which the physician associated with this case is affiliated."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS291CodeLookupButton" onclick="getDWROrganization('NBS291')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS291Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS291_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS291S">Physician Ordering Clinic Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS291Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS291Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS291Uid"/>
<span id="NBS291">${PageForm.attributeMap.NBS291SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS291Error"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_13" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV128)" styleId="INV128" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1287262();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1327266();">
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
Total duration of stay in the hospital (in days):</span>
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
<span class=" InputFieldLabel" id="INV145L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV145)" styleId="INV145" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1457263();">
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="STD105L" title="Date treatment initiated for the condition that is the subject of this case report.">
Treatment Start Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(STD105)" maxlength="10" size="10" styleId="STD105" title="Date treatment initiated for the condition that is the subject of this case report." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('STD105','STD105Icon'); return false;" styleId="STD105Icon" onkeypress="showCalendarEnterKey('STD105','STD105Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="STD099L" title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date.">
Date of Initial Health Exam:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(STD099)" maxlength="10" size="10" styleId="STD099" title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('STD099','STD099Icon'); return false;" styleId="STD099Icon" onkeypress="showCalendarEnterKey('STD099','STD099Icon',event)"></html:img>
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
<html:select name="PageForm" property="pageClientVO.answer(INV150)" styleId="INV150" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1507264();">
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
Where was the disease acquired:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV152)" styleId="INV152" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1527265();">
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
<span class=" InputFieldLabel" id="NBS135L" title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient.">
Source/Spread:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS135)" styleId="NBS135" title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient.">
<nedss:optionsCollection property="codedValue(SOURCE_SPREAD)" value="key" label="value" /></html:select>
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
<nedss:container id="NBS11001" name="Reporting County" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS11002" name="Exposure Location" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS11002errorMessages">
<b> <a name="NBS11002errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS11002">
<tr id="patternNBS11002" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS11002" onkeypress="viewClicked(this.id,'NBS11002');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS11002');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS11002" onkeypress="editClicked(this.id,'NBS11002');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS11002');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS11002" onkeypress="deleteClicked(this.id,'NBS11002','patternNBS11002','questionbodyNBS11002');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS11002','patternNBS11002','questionbodyNBS11002');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS11002">
<tr id="nopatternNBS11002" class="odd" style="display:">
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
<span class="NBS11002 InputFieldLabel" id="INV502L" title="Indicates the country in which the disease was potentially acquired.">
Country of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV502)" styleId="INV502" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS11002');ruleEnDisINV5027279();getDWRStatesByCountry(this, 'INV503');">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS11002 InputFieldLabel" id="INV503L" title="Indicates the state in which the disease was potentially acquired.">
State or Province of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV503)" styleId="INV503" title="Indicates the state in which the disease was potentially acquired." onchange="unhideBatchImg('NBS11002');getDWRCounties(this, 'INV505');">
<nedss:optionsCollection property="codedValue(PHVS_STATEPROVINCEOFEXPOSURE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV504L" title="Indicates the city in which the disease was potentially acquired.">
City of Exposure:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV504)" maxlength="50" title="Indicates the city in which the disease was potentially acquired." styleId="INV504" onkeyup="unhideBatchImg('NBS11002');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS11002 InputFieldLabel" id="INV505L" title="Indicates the county in which the disease was potentially acquired.">
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
<tr id="AddButtonToggleNBS11002">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS11002BatchAddFunction()) writeQuestion('NBS11002','patternNBS11002','questionbodyNBS11002')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS11002">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS11002BatchAddFunction()) writeQuestion('NBS11002','patternNBS11002','questionbodyNBS11002')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS11002"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS11002BatchAddFunction()) writeQuestion('NBS11002','patternNBS11002','questionbodyNBS11002')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS11002"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS11002')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS11003" name="Binational Reporting" isHidden="F" classType="subSect" >

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
<nedss:optionsCollection property="codedValue(PHVS_DETECTIONMETHOD_STD)" value="key" label="value" /></html:select>
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
<nedss:optionsCollection property="codedValue(PHVS_PHC_CLASS_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS136L" title="The disease diagnosis of the patient.">
Diagnosis Reported to CDC:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS136)" styleId="NBS136" title="The disease diagnosis of the patient." onchange="stdDiagnosisRelatedFields();">
<nedss:optionsCollection property="codedValue(CASE_DIAGNOSIS_CS)" value="key" label="value" /></html:select>
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
<html:select name="PageForm" property="pageClientVO.answer(NOT120)" styleId="NOT120" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1207281();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV176L" title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition.">
Date CDC was first verbally notified of this case:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV176)" maxlength="10" size="10" styleId="INV176" title="Enter the date the case of an Immediately National Notifiable Condition was first verbally reported to the CDC Emergency Operation Center or the CDC Subject Matter Expert responsible for this condition." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV176','INV176Icon'); return false;" styleId="INV176Icon" onkeypress="showCalendarEnterKey('INV176','INV176Icon',event)"></html:img>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NOT120SPECL" title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition.">
If Yes, describe:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NOT120SPEC)" maxlength="50" title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." styleId="NOT120SPEC"/>
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_73" name="Syphilis Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD102L" title="Neurological Involvement?">
Neurological Manifestations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD102)" styleId="STD102" title="Neurological Involvement?" onchange="ruleEnDisSTD1027282();enableOrDisableOther('102957003');">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="102957003L" title="What neurologic manifestations of syphilis are present?">
Neurologic Signs/Symptoms:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(102957003)" styleId="102957003" title="What neurologic manifestations of syphilis are present?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, '102957003-selectedValues');enableOrDisableOther('102957003')" >
<nedss:optionsCollection property="codedValue(PHVS_NEUROLOGICALMANIFESTATION_STD)" value="key" label="value" /> </html:select>
<div id="102957003-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What neurologic manifestations of syphilis are present?" id="102957003OthL">Other Neurologic Signs/Symptoms:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(102957003Oth)" size="40" maxlength="40" title="Other What neurologic manifestations of syphilis are present?" styleId="102957003Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="410478005L" title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis.">
Ocular Manifestations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(410478005)" styleId="410478005" title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis.">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="PHC1472L" title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo.">
Otic Manifestations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(PHC1472)" styleId="PHC1472" title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo.">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="72083004L" title="Late clinical manifestations of syphilis (tertiary syphilis) may include inflammatory lesions of the cardiovascular system, skin, bone, or other tissue. Certain neurologic manifestations (e.g., general paresis and tabes dorsalis) are late clinical manifestations of syphilis.">
Late Clinical Manifestations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(72083004)" styleId="72083004" title="Late clinical manifestations of syphilis (tertiary syphilis) may include inflammatory lesions of the cardiovascular system, skin, bone, or other tissue. Certain neurologic manifestations (e.g., general paresis and tabes dorsalis) are late clinical manifestations of syphilis.">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD)" value="key" label="value" /></html:select>
</td></tr>
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
<nedss:container id="NBS_INV_STD_UI_12" name="Case Numbers" isHidden="F" classType="subSect" >

<!--processing ReadOnly Textbox Text Question-->
<tr> <td class="fieldName">
<span title="Unique field record identifier." id="NBS160L">
Field Record Number:</span>
</td>
<td>
<span id="NBS160S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS160)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS160)" styleId="NBS160" />
</td>
</tr>

<!--processing ReadOnly Textbox Text Question-->
<tr> <td class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191L">
Lot Number:</span>
</td>
<td>
<span id="NBS191S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS191)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS191)" styleId="NBS191" />
</td>
</tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV200L" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here.">
Legacy Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(INV200)" maxlength="25" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." styleId="INV200"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_14" name="Initial Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS139L" title="The investigator assigning the initial follow-up.">
Investigator:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS139Uid">
<span id="clearNBS139" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS139Uid">
<span id="clearNBS139">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS139CodeClearButton" onclick="clearProvider('NBS139')"/>
</span>
<span id="NBS139SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS139Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS139Icon" onclick="getProvider('NBS139');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS139)" styleId="NBS139Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS139Text','NBS139_qec_list')"
title="The investigator assigning the initial follow-up."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS139CodeLookupButton" onclick="getDWRProvider('NBS139')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS139Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS139_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS139S">Investigator Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS139Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS139Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS139Uid"/>
<span id="NBS139">${PageForm.attributeMap.NBS139SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS139Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS140L" title="Initial Follow-up action.">
Initial Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS140)" styleId="NBS140" title="Initial Follow-up action." onchange="stdFixedRuleInitialFollowupEntry();">
<nedss:optionsCollection property="codedValue(STD_NBS_PROCESSING_DECISION_ALL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS141L" title="The date the inital follow-up was identified as closed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS141)" maxlength="10" size="10" styleId="NBS141" title="The date the inital follow-up was identified as closed." onkeyup="DateMask(this,null,event)" onblur="stdInitialFollowupDateClosedEntered()" onchange="stdInitialFollowupDateClosedEntered()"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS141','NBS141Icon'); return false;" styleId="NBS141Icon" onkeypress="showCalendarEnterKey('NBS141','NBS141Icon',event)"></html:img>
</td> </tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS142L" title="Initiate for Internet follow-up?">
Internet Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS142)" styleId="NBS142" title="Initiate for Internet follow-up?">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS144L" title="If applicable, enter the specific clinic code identifying the initiating clinic.">
Clinic Code:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS144)" maxlength="8" title="If applicable, enter the specific clinic code identifying the initiating clinic." styleId="NBS144"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_16" name="Surveillance Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS145L" title="The investigator assigned for surveillance follow-up.">
Assigned To:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS145Uid">
<span id="clearNBS145" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS145Uid">
<span id="clearNBS145">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS145CodeClearButton" onclick="clearProvider('NBS145')"/>
</span>
<span id="NBS145SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS145Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS145Icon" onclick="getProvider('NBS145');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS145)" styleId="NBS145Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS145Text','NBS145_qec_list')"
title="The investigator assigned for surveillance follow-up."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS145CodeLookupButton" onclick="getDWRProvider('NBS145')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS145Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS145_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS145S">Assigned To Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS145Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS145Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS145Uid"/>
<span id="NBS145">${PageForm.attributeMap.NBS145SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS145Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS146L" title="The date surveillance follow-up is assigned.">
Date Assigned:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS146)" maxlength="10" size="10" styleId="NBS146" title="The date surveillance follow-up is assigned." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS146','NBS146Icon'); return false;" styleId="NBS146Icon" onkeypress ="showCalendarFutureEnterKey('NBS146','NBS146Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS147L" title="The date surveillance follow-up is completed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS147)" maxlength="10" size="10" styleId="NBS147" title="The date surveillance follow-up is completed." onkeyup="DateMask(this,null,event)" onblur="stdSurveillanceDateClosedEntered()" onchange="stdSurveillanceDateClosedEntered()"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS147','NBS147Icon'); return false;" styleId="NBS147Icon" onkeypress="showCalendarEnterKey('NBS147','NBS147Icon',event)"></html:img>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_17" name="Surveillance Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS148L" title="Indicate if the contact with the provider was successful or not.">
Provider Contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS148)" styleId="NBS148" title="Indicate if the contact with the provider was successful or not.">
<nedss:optionsCollection property="codedValue(PRVDR_CONTACT_OUTCOME)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS149L" title="The reporting provider's reason for examing the patient.">
Exam Reason:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS149)" styleId="NBS149" title="The reporting provider's reason for examing the patient.">
<nedss:optionsCollection property="codedValue(PRVDR_EXAM_REASON)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS150L" title="The reporting provider's diagnosis.">
Reporting Provider Diagnosis (Surveillance):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS150)" styleId="NBS150" title="The reporting provider's diagnosis.">
<nedss:optionsCollection property="codedValue(PRVDR_DIAGNOSIS_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS151L" title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason.">
Patient Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS151)" styleId="NBS151" title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason." onchange="stdFixedRuleSurveillancePatientFollowupEntry();">
<nedss:optionsCollection property="codedValue(SURVEILLANCE_PATIENT_FOLLOWUP)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_18" name="Surveillance Notes" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_18errorMessages">
<b> <a name="NBS_INV_STD_UI_18errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_18">
<tr id="patternNBS_INV_STD_UI_18" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_18" onkeypress="viewClicked(this.id,'NBS_INV_STD_UI_18');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_18');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_18" onkeypress="editClicked(this.id,'NBS_INV_STD_UI_18');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_18');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_18" onkeypress="deleteClicked(this.id,'NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_18">
<tr id="nopatternNBS_INV_STD_UI_18" class="odd" style="display:">
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

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS152L" title="Notes for surveillance activities (e.g., type of information needed, additional comments.)">
Surveillance Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS152)" styleId ="NBS152" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS152');unhideBatchImg('NBS_INV_STD_UI_18');" title="Notes for surveillance activities (e.g., type of information needed, additional comments.)"/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS152DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS152Date)" maxlength="10" size="10" styleId="NBS152Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS152Date','NBS152DateIcon');unhideBatchImg('NBS_INV_STD_UI_18');return false;" styleId="NBS152DateIcon" onkeypress="showCalendarEnterKey('NBS152Date','NBS152DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS152UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS152User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS152User" onkeyup="unhideBatchImg('NBS_INV_STD_UI_18');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_18">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_18">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_18"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18','patternNBS_INV_STD_UI_18','questionbodyNBS_INV_STD_UI_18')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_18"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_18')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_22" name="Field Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS161L" title="The investigator assigned to field follow-up activities.">
Investigator:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS161Uid">
<span id="clearNBS161" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS161Uid">
<span id="clearNBS161">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS161CodeClearButton" onclick="clearProvider('NBS161')"/>
</span>
<span id="NBS161SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS161Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS161Icon" onclick="getProvider('NBS161');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS161)" styleId="NBS161Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS161Text','NBS161_qec_list')"
title="The investigator assigned to field follow-up activities."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS161CodeLookupButton" onclick="getDWRProvider('NBS161')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS161Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS161_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS161S">Investigator Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS161Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS161Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS161Uid"/>
<span id="NBS161">${PageForm.attributeMap.NBS161SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS161Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS162L" title="The date the investigator is assigned to field follow-up activities.">
Date Assigned:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS162)" maxlength="10" size="10" styleId="NBS162" title="The date the investigator is assigned to field follow-up activities." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS162','NBS162Icon'); return false;" styleId="NBS162Icon" onkeypress ="showCalendarFutureEnterKey('NBS162','NBS162Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS163L" title="The investigator originally assigned to field follow-up activities.">
Initially Assigned:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS163Uid">
<span id="clearNBS163" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS163Uid">
<span id="clearNBS163">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS163CodeClearButton" onclick="clearProvider('NBS163')"/>
</span>
<span id="NBS163SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS163Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS163Icon" onclick="getProvider('NBS163');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS163)" styleId="NBS163Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS163Text','NBS163_qec_list')"
title="The investigator originally assigned to field follow-up activities."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS163CodeLookupButton" onclick="getDWRProvider('NBS163')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS163Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS163_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS163S">Initially Assigned Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS163Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS163Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS163Uid"/>
<span id="NBS163">${PageForm.attributeMap.NBS163SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS163Error"/>
</td></tr>

<!--processing Date Question-->

<!--processing ReadOnly Date-->
<tr><td class="fieldName">
<span title="The date of initial assignment for field follow-up." id="NBS164L">Initial Assignment Date:</span>
</td><td>
<span id="NBS164S"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS164)"  />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO.answer(NBS164)" styleId="NBS164" />
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_23" name="Field Follow-up Exam Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS165L" title="The reporting provider's reason for examing the patient.">
Exam Reason:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS165)" styleId="NBS165" title="The reporting provider's reason for examing the patient.">
<nedss:optionsCollection property="codedValue(PRVDR_EXAM_REASON)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS166L" title="The reporting provider's diagnosis.">
Reporting Provider Diagnosis (Field Follow-up):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS166)" styleId="NBS166" title="The reporting provider's diagnosis.">
<nedss:optionsCollection property="codedValue(PRVDR_DIAGNOSIS_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS168L" title="Do you expect the patient to come in for examination?">
Expected In:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS168)" styleId="NBS168" title="Do you expect the patient to come in for examination?" onchange="ruleEnDisNBS1687288();">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS169L" title="The date the patient is expected to come in for examination.">
Expected In Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS169)" maxlength="10" size="10" styleId="NBS169" title="The date the patient is expected to come in for examination." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS169','NBS169Icon'); return false;" styleId="NBS169Icon" onkeypress ="showCalendarFutureEnterKey('NBS169','NBS169Icon',event)"></html:img>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS170L" title="The date the patient was examined as a result of field activities.">
Exam Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS170)" maxlength="10" size="10" styleId="NBS170" title="The date the patient was examined as a result of field activities." onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS170','NBS170Icon'); return false;" styleId="NBS170Icon" onkeypress ="showCalendarFutureEnterKey('NBS170','NBS170Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS171L" title="The provider who performed the exam.">
Provider:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS171Uid">
<span id="clearNBS171" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS171Uid">
<span id="clearNBS171">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS171CodeClearButton" onclick="clearProvider('NBS171')"/>
</span>
<span id="NBS171SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS171Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS171Icon" onclick="getProvider('NBS171');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS171)" styleId="NBS171Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS171Text','NBS171_qec_list')"
title="The provider who performed the exam."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS171CodeLookupButton" onclick="getDWRProvider('NBS171')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS171Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS171_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS171S">Provider Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS171Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS171Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS171Uid"/>
<span id="NBS171">${PageForm.attributeMap.NBS171SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS171Error"/>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS172L" title="The facility at which the exam was performed.">
Facility:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS172Uid">
<span id="clearNBS172" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS172Uid">
<span id="clearNBS172">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS172CodeClearButton" onclick="clearOrganization('NBS172')"/>
</span>
<span id="NBS172SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS172Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS172Icon" onclick="getReportingOrg('NBS172');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS172)" styleId="NBS172Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('NBS172Text','NBS172_qec_list')"
title="The facility at which the exam was performed."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS172CodeLookupButton" onclick="getDWROrganization('NBS172')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS172Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS172_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS172S">Facility Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS172Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS172Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS172Uid"/>
<span id="NBS172">${PageForm.attributeMap.NBS172SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS172Error"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_24" name="Case Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS173L" title="The disposition of the field follow-up activities.">
Disposition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS173)" styleId="NBS173" title="The disposition of the field follow-up activities." onchange="ruleEnDisNBS1737285();stdFixedRuleFieldFollowupDispositionEntry();">
<nedss:optionsCollection property="codedValue(FIELD_FOLLOWUP_DISPOSITION_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS174L" title="When the disposition was determined as relates to exam or treatment situation.">
Disposition Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS174)" maxlength="10" size="10" styleId="NBS174" title="When the disposition was determined as relates to exam or treatment situation." onkeyup="DateMask(this,null,event)" onblur="stdFieldFollowupDispositionDateEntry()" onchange="stdFieldFollowupDispositionDateEntry()"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS174','NBS174Icon'); return false;" styleId="NBS174Icon" onkeypress="showCalendarEnterKey('NBS174','NBS174Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS175L" title="The person who brought the field record/activities to final disposition.">
Dispositioned By:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS175Uid">
<span id="clearNBS175" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS175Uid">
<span id="clearNBS175">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS175CodeClearButton" onclick="clearProvider('NBS175')"/>
</span>
<span id="NBS175SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS175Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS175Icon" onclick="getProvider('NBS175');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS175)" styleId="NBS175Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS175Text','NBS175_qec_list')"
title="The person who brought the field record/activities to final disposition."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS175CodeLookupButton" onclick="getDWRProvider('NBS175')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS175Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS175_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS175S">Dispositioned By Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS175Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS175Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS175Uid"/>
<span id="NBS175">${PageForm.attributeMap.NBS175SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS175Error"/>
</td></tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS176L" title="The supervisor who should review the field record disposition.">
Supervisor:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS176Uid">
<span id="clearNBS176" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS176Uid">
<span id="clearNBS176">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS176CodeClearButton" onclick="clearProvider('NBS176')"/>
</span>
<span id="NBS176SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS176Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS176Icon" onclick="getProvider('NBS176');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS176)" styleId="NBS176Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS176Text','NBS176_qec_list')"
title="The supervisor who should review the field record disposition."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS176CodeLookupButton" onclick="getDWRProvider('NBS176')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS176Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS176_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS176S">Supervisor Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS176Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS176Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS176Uid"/>
<span id="NBS176">${PageForm.attributeMap.NBS176SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS176Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS392L" title="Enter the investigator is unable to disposition the case">
Reason Unable to Disposition Case:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS392)" styleId="NBS392" title="Enter the investigator is unable to disposition the case">
<nedss:optionsCollection property="codedValue(REASON_NOT_DISPO_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS178L" title="The outcome of internet based activities.">
Internet Outcome:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS178)" styleId="NBS178" title="The outcome of internet based activities.">
<nedss:optionsCollection property="codedValue(INTERNET_FOLLOWUP_OUTCOME)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_25" name="OOJ Field Record Sent To Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS179L" title="The name of the area where the out-of-jurisdiction Field Follow-up is sent.">
OOJ Agency FR Sent To:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS179)" styleId="NBS179" title="The name of the area where the out-of-jurisdiction Field Follow-up is sent.">
<nedss:optionsCollection property="codedValue(OOJ_AGENCY_LOCAL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS180L" title="Field record number from initiating or receiving jurisdiction.">
OOJ FR Number In Receiving Area:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS180)" maxlength="10" title="Field record number from initiating or receiving jurisdiction." styleId="NBS180"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS181L" title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)">
OOJ Due Date from Receiving Area:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS181)" maxlength="10" size="10" styleId="NBS181" title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)" onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS181','NBS181Icon'); return false;" styleId="NBS181Icon" onkeypress ="showCalendarFutureEnterKey('NBS181','NBS181Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS182L" title="The outcome of the OOJ jurisdiction field activities.">
OOJ Outcome from Receiving Area:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS182)" styleId="NBS182" title="The outcome of the OOJ jurisdiction field activities.">
<nedss:optionsCollection property="codedValue(FIELD_FOLLOWUP_DISPOSITION_STD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_26" name="Field Follow-Up Notes" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_26errorMessages">
<b> <a name="NBS_INV_STD_UI_26errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_26">
<tr id="patternNBS_INV_STD_UI_26" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_26" onkeypress="viewClicked(this.id,'NBS_INV_STD_UI_26');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_26');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_26" onkeypress="editClicked(this.id,'NBS_INV_STD_UI_26');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_26');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_26" onkeypress="deleteClicked(this.id,'NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_26">
<tr id="nopatternNBS_INV_STD_UI_26" class="odd" style="display:">
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

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS185L" title="Note text.">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS185)" styleId ="NBS185" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS185');unhideBatchImg('NBS_INV_STD_UI_26');" title="Note text."/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS185DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS185Date)" maxlength="10" size="10" styleId="NBS185Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS185Date','NBS185DateIcon');unhideBatchImg('NBS_INV_STD_UI_26');return false;" styleId="NBS185DateIcon" onkeypress="showCalendarEnterKey('NBS185Date','NBS185DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS185UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS185User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS185User" onkeyup="unhideBatchImg('NBS_INV_STD_UI_26');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_26">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_26">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_26"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26','patternNBS_INV_STD_UI_26','questionbodyNBS_INV_STD_UI_26')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_26"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_26')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_27" name="Field Supervisory Review and Comments" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_27errorMessages">
<b> <a name="NBS_INV_STD_UI_27errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_27">
<tr id="patternNBS_INV_STD_UI_27" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_27" onkeypress="viewClicked(this.id,'NBS_INV_STD_UI_27');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_27');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_27" onkeypress="editClicked(this.id,'NBS_INV_STD_UI_27');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_27');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_27" onkeypress="deleteClicked(this.id,'NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_27">
<tr id="nopatternNBS_INV_STD_UI_27" class="odd" style="display:">
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

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS268L" title="Note text">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS268)" styleId ="NBS268" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS268');unhideBatchImg('NBS_INV_STD_UI_27');" title="Note text"/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS268DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS268Date)" maxlength="10" size="10" styleId="NBS268Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS268Date','NBS268DateIcon');unhideBatchImg('NBS_INV_STD_UI_27');return false;" styleId="NBS268DateIcon" onkeypress="showCalendarEnterKey('NBS268Date','NBS268DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS268UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS268User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS268User" onkeyup="unhideBatchImg('NBS_INV_STD_UI_27');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_27">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_27">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_27"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27','patternNBS_INV_STD_UI_27','questionbodyNBS_INV_STD_UI_27')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_27"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_27')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_32" name="Case Closure" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS196L" title="The date the case follow-up is closed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS196)" maxlength="10" size="10" styleId="NBS196" title="The date the case follow-up is closed." onkeyup="DateMask(this,null,event)" onblur="stdCaseClosureDateClosedEntered()" onchange="stdCaseClosureDateClosedEntered()"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS196','NBS196Icon'); return false;" styleId="NBS196Icon" onkeypress="showCalendarEnterKey('NBS196','NBS196Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS197L" title="The investigator who closed out the case follow-up.">
Closed By:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS197Uid">
<span id="clearNBS197" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS197Uid">
<span id="clearNBS197">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS197CodeClearButton" onclick="clearProvider('NBS197')"/>
</span>
<span id="NBS197SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS197Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS197Icon" onclick="getProvider('NBS197');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS197)" styleId="NBS197Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS197Text','NBS197_qec_list')"
title="The investigator who closed out the case follow-up."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS197CodeLookupButton" onclick="getDWRProvider('NBS197')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS197Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS197_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS197S">Closed By Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS197Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS197Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS197Uid"/>
<span id="NBS197">${PageForm.attributeMap.NBS197SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS197Error"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_34" name="Supervisory Review/Comments" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_34errorMessages">
<b> <a name="NBS_INV_STD_UI_34errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_34">
<tr id="patternNBS_INV_STD_UI_34" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_34" onkeypress="viewClicked(this.id,'NBS_INV_STD_UI_34');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_34');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_34" onkeypress="editClicked(this.id,'NBS_INV_STD_UI_34');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_34');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_34" onkeypress="deleteClicked(this.id,'NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_34">
<tr id="nopatternNBS_INV_STD_UI_34" class="odd" style="display:">
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

<!--processing Rolling Note-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS200L" title="Note text.">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS200)" styleId ="NBS200" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS200');unhideBatchImg('NBS_INV_STD_UI_34');" title="Note text."/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS200DateL">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS200Date)" maxlength="10" size="10" styleId="NBS200Date" title="This is a hidden read-only field for the Date the note was added or updated"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS200Date','NBS200DateIcon');unhideBatchImg('NBS_INV_STD_UI_34');return false;" styleId="NBS200DateIcon" onkeypress="showCalendarEnterKey('NBS200Date','NBS200DateIcon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS200UserL">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS200User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS200User" onkeyup="unhideBatchImg('NBS_INV_STD_UI_34');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_34">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_34">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_34"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34','patternNBS_INV_STD_UI_34','questionbodyNBS_INV_STD_UI_34')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_34"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_34')"/>&nbsp;	&nbsp;&nbsp;
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
<nedss:container id="NBS_INV_STD_UI_77" name="Congenital Syphilis Report" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS388L" title="Enter the expected delivery date of the infant">
Expected Delivery Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS388)" maxlength="10" size="10" styleId="NBS388" title="Enter the expected delivery date of the infant" onkeyup="DateMaskFuture(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDateFuture('NBS388','NBS388Icon'); return false;" styleId="NBS388Icon" onkeypress ="showCalendarFutureEnterKey('NBS388','NBS388Icon',event)"></html:img>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS383L" title="The mother's OB/GYN provider.">
Mother OB/GYN:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS383Uid">
<span id="clearNBS383" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS383Uid">
<span id="clearNBS383">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS383CodeClearButton" onclick="clearProvider('NBS383')"/>
</span>
<span id="NBS383SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS383Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS383Icon" onclick="getProvider('NBS383');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS383)" styleId="NBS383Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS383Text','NBS383_qec_list')"
title="The mother's OB/GYN provider."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS383CodeLookupButton" onclick="getDWRProvider('NBS383')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS383Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS383_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS383S">Mother OB/GYN Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS383Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS383Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS383Uid"/>
<span id="NBS383">${PageForm.attributeMap.NBS383SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS383Error"/>
</td></tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS384L" title="Who will be the Delivering Phyisician?">
Delivering MD:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS384Uid">
<span id="clearNBS384" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS384Uid">
<span id="clearNBS384">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS384CodeClearButton" onclick="clearProvider('NBS384')"/>
</span>
<span id="NBS384SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS384Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS384Icon" onclick="getProvider('NBS384');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS384)" styleId="NBS384Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS384Text','NBS384_qec_list')"
title="Who will be the Delivering Phyisician?"/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS384CodeLookupButton" onclick="getDWRProvider('NBS384')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS384Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS384_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS384S">Delivering MD Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS384Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS384Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS384Uid"/>
<span id="NBS384">${PageForm.attributeMap.NBS384SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS384Error"/>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS386L" title="The name of the hospital where the infant was born.">
Delivering Hospital:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS386Uid">
<span id="clearNBS386" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS386Uid">
<span id="clearNBS386">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS386CodeClearButton" onclick="clearOrganization('NBS386')"/>
</span>
<span id="NBS386SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS386Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS386Icon" onclick="getReportingOrg('NBS386');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS386)" styleId="NBS386Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('NBS386Text','NBS386_qec_list')"
title="The name of the hospital where the infant was born."/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS386CodeLookupButton" onclick="getDWROrganization('NBS386')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS386Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS386_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS386S">Delivering Hospital Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS386Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS386Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS386Uid"/>
<span id="NBS386">${PageForm.attributeMap.NBS386SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS386Error"/>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS385L" title="Who is the attending Pediatrician?">
Pediatrician:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS385Uid">
<span id="clearNBS385" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS385Uid">
<span id="clearNBS385">
</logic:notEmpty>
<input type="button" class="Button" value="Clear/Reassign" id="NBS385CodeClearButton" onclick="clearProvider('NBS385')"/>
</span>
<span id="NBS385SearchControls"
<logic:notEmpty name="PageForm" property="attributeMap.NBS385Uid">
class="none"
</logic:notEmpty>
><input type="button" class="Button" value="Search"
id="NBS385Icon" onclick="getProvider('NBS385');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO.answer(NBS385)" styleId="NBS385Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS385Text','NBS385_qec_list')"
title="Who is the attending Pediatrician?"/>
<input type="button" class="Button" value="Quick Code Lookup"
id="NBS385CodeLookupButton" onclick="getDWRProvider('NBS385')"
<logic:notEmpty name="PageForm" property="attributeMap.NBS385Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS385_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS385S">Pediatrician Selected: </td>
<logic:empty name="PageForm" property="attributeMap.NBS385Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS385Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap.NBS385Uid"/>
<span id="NBS385">${PageForm.attributeMap.NBS385SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS385Error"/>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS336L" title="The Medical Record Number as reported by health care provider or facility">
Infant's Medical Record Number:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS336)" maxlength="25" title="The Medical Record Number as reported by health care provider or facility" styleId="NBS336"/>
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS387L" title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page.">
Next of Kin Relationship:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS387)" styleId="NBS387" title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page.">
<nedss:optionsCollection property="codedValue(PH_RELATIONSHIP_HL7_2X)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH166L" title="Enter the state of residence of the mother of the case patient">
Mother's Residence State:</span>
</td>
<td>

<!--processing State Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(MTH166)" styleId="MTH166" title="Enter the state of residence of the mother of the case patient" onchange="getDWRCounties(this, 'MTH168')">
<html:optionsCollection property="stateList" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="MTH169L" title="Enter the zip code of the case patient's mother">
Mother's Residence Zip Code:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(MTH169)" maxlength="40" title="Enter the zip code of the case patient's mother" styleId="MTH169" onkeyup="ZipMask(this,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH168L" title="The county of residence of the case patient's mother">
Mother's County of Residence:</span>
</td>
<td>

<!--processing County Coded Question-->
<html:select name="PageForm" property="pageClientVO.answer(MTH168)" styleId="MTH168" title="The county of residence of the case patient's mother">
<html:optionsCollection property="dwrCounties" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH167L" title="Enter the country of residence of the case patient's mother">
Mother's Country of Residence:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH167)" styleId="MTH167" title="Enter the country of residence of the case patient's mother">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="MTH153L" title="Enter the date of birth of the case patient's mother">
Mother's Date of Birth:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(MTH153)" maxlength="10" size="10" styleId="MTH153" title="Enter the date of birth of the case patient's mother" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('MTH153','MTH153Icon'); return false;" styleId="MTH153Icon" onkeypress="showCalendarEnterKey('MTH153','MTH153Icon',event)"></html:img>
</td> </tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH157L" title="Enter the race of the patient's mother">
Race of Mother:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(MTH157)" styleId="MTH157" title="Enter the race of the patient's mother"
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'MTH157-selectedValues')" >
<nedss:optionsCollection property="codedValue(PHVS_RACECATEGORY_CDC_NULLFLAVOR)" value="key" label="value" /> </html:select>
<div id="MTH157-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH159L" title="Ethnicity of the patient's mother">
Ethnicity of Mother:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH159)" styleId="MTH159" title="Ethnicity of the patient's mother">
<nedss:optionsCollection property="codedValue(PHVS_ETHNICITYGROUP_CDC_UNK)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH165L" title="Enter the marital status of the case patient's mother">
Mother's Marital Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH165)" styleId="MTH165" title="Enter the marital status of the case patient's mother">
<nedss:optionsCollection property="codedValue(P_MARITAL)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_80" name="Mother Medical History" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="75201_4L" title="Enter the number of pregnancies, include current and previous pregnancies (G)">
Number of Pregnancies:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(75201_4)" size="2" maxlength="2"  title="Enter the number of pregnancies, include current and previous pregnancies (G)" styleId="75201_4" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="75202_2L" title="Enter the total number of live births">
Number of Live Births:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(75202_2)" size="2" maxlength="2"  title="Enter the total number of live births" styleId="75202_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="752030L" title="Mother Last menstrual period (LMP) start date before delivery.">
Mother's Last Menstrual Period Before Delivery:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(752030)" maxlength="10" size="10" styleId="752030" title="Mother Last menstrual period (LMP) start date before delivery." onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('752030','752030Icon'); return false;" styleId="752030Icon" onkeypress="showCalendarEnterKey('752030','752030Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75204_8L" title="Indicate if there was a prenatal visit">
Was There a Prenatal Visit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75204_8)" styleId="75204_8" title="Indicate if there was a prenatal visit" onchange="ruleEnDis75204_87283();">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS390L" title="Enter the total number of prenatal visits the mother had related to the pregnancy">
Total Number of Prenatal Visits:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS390)" size="2" maxlength="2"  title="Enter the total number of prenatal visits the mother had related to the pregnancy" styleId="NBS390" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="75200_6L" title="Indicate the date of the first prenatal visit">
Indicate Date of First Prenatal Visit:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(75200_6)" maxlength="10" size="10" styleId="75200_6" title="Indicate the date of the first prenatal visit" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('75200_6','75200_6Icon'); return false;" styleId="75200_6Icon" onkeypress="showCalendarEnterKey('75200_6','75200_6Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75163_6L" title="Indicate the trimester of the first prenatal visit">
Indicate Trimester of First Prenatal Visit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75163_6)" styleId="75163_6" title="Indicate the trimester of the first prenatal visit">
<nedss:optionsCollection property="codedValue(PHVS_PREG_TRIMESTER)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75164_4L" title="Did the subject have a Non-treponemal Test or Treponemal Test at First Prenatal Visit">
Did Mother Have Non-Treponemal or Treponemal Test at First Prenatal Visit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75164_4)" styleId="75164_4" title="Did the subject have a Non-treponemal Test or Treponemal Test at First Prenatal Visit">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75165_1L" title="Did the subject have a  Non-treponemal Test or Treponemal Test at 28-32 Weeks Gestation">
Did Mother Have Non-Treponemal or Treponemal Test at 28-32 Weeks Gestation?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75165_1)" styleId="75165_1" title="Did the subject have a  Non-treponemal Test or Treponemal Test at 28-32 Weeks Gestation">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75166_9L" title="Did the subject have a Non-treponemal Test or Treponemal Test at Delivery">
Did Mother Have Non-Treponemal or Treponemal Tests at Delivery:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75166_9)" styleId="75166_9" title="Did the subject have a Non-treponemal Test or Treponemal Test at Delivery">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_81" name="Mother Interpretive Lab Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_81errorMessages">
<b> <a name="NBS_INV_STD_UI_81errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_81">
<tr id="patternNBS_INV_STD_UI_81" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_81" onkeypress="viewClicked(this.id,'NBS_INV_STD_UI_81');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_81');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_81" onkeypress="editClicked(this.id,'NBS_INV_STD_UI_81');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_81');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_81" onkeypress="deleteClicked(this.id,'NBS_INV_STD_UI_81','patternNBS_INV_STD_UI_81','questionbodyNBS_INV_STD_UI_81');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_81','patternNBS_INV_STD_UI_81','questionbodyNBS_INV_STD_UI_81');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_81">
<tr id="nopatternNBS_INV_STD_UI_81" class="odd" style="display:">
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
<span class="NBS_INV_STD_UI_81 InputFieldLabel" id="LAB588_MTHL" title="Select the appropriate value to indicate timing and subject of the test being entered.">
Lab Test Performed Modifier (Mother):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB588_MTH)" styleId="LAB588_MTH" title="Select the appropriate value to indicate timing and subject of the test being entered." onchange="unhideBatchImg('NBS_INV_STD_UI_81');">
<nedss:optionsCollection property="codedValue(LAB_TST_MODIFIER_MTH)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_81 InputFieldLabel" id="INV290_MTHL" title="Enter the type of lab test performed">
Test Type (Mother):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290_MTH)" styleId="INV290_MTH" title="Enter the type of lab test performed" onchange="unhideBatchImg('NBS_INV_STD_UI_81');ruleEnDisINV290_MTH7287();">
<nedss:optionsCollection property="codedValue(PHVS_TESTTYPE_SYPHILIS)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_81 InputFieldLabel" id="INV291_MTHL" title="Enter the result for the lab test being reported.">
Test Result (Mother):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291_MTH)" styleId="INV291_MTH" title="Enter the result for the lab test being reported." onchange="unhideBatchImg('NBS_INV_STD_UI_81');">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_81 InputFieldLabel" id="STD123_MTHL" title="Enter the quantitative test result for the non-treponemal serologic test of the mother of the case. If the test performed provides a quantifiable result, provide quantitative results as a coded value. For example, if the titer is 1:64, choose the corresponding value from the drop down.">
Non-Treponemal Serologic Test Result (Quantitative) (Mother):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD123_MTH)" styleId="STD123_MTH" title="Enter the quantitative test result for the non-treponemal serologic test of the mother of the case. If the test performed provides a quantifiable result, provide quantitative results as a coded value. For example, if the titer is 1:64, choose the corresponding value from the drop down." onchange="unhideBatchImg('NBS_INV_STD_UI_81');">
<nedss:optionsCollection property="codedValue(PHVS_NONTREPONEMALTESTRESULT_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB167_MTHL" title="Enter the lab result date for the lab test being reported.">
Lab Result Date (Mother):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB167_MTH)" maxlength="10" size="10" styleId="LAB167_MTH" title="Enter the lab result date for the lab test being reported." onkeyup="unhideBatchImg('NBS_INV_STD_UI_81');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_81"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB167_MTH','LAB167_MTHIcon'); unhideBatchImg('NBS_INV_STD_UI_81');return false;" styleId="LAB167_MTHIcon" onkeypress="showCalendarEnterKey('LAB167_MTH','LAB167_MTHIcon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_81">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_81BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_81','patternNBS_INV_STD_UI_81','questionbodyNBS_INV_STD_UI_81')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_81">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_81BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_81','patternNBS_INV_STD_UI_81','questionbodyNBS_INV_STD_UI_81')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_81"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_81BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_81','patternNBS_INV_STD_UI_81','questionbodyNBS_INV_STD_UI_81')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_81"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_81')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_82" name="Mother Clinical Information" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS153_MTHL" title="Enter the HIV status of the mother of case.">
Mother HIV Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS153_MTH)" styleId="NBS153_MTH" title="Enter the HIV status of the mother of case.">
<nedss:optionsCollection property="codedValue(PHVS_HIVSTATUS_STD)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75180_0L" title="Indicate the clinical stage of syphilis the mother had during pregnancy">
What CLINICAL Stage of Syphilis Did Mother Have During Pregnancy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75180_0)" styleId="75180_0" title="Indicate the clinical stage of syphilis the mother had during pregnancy">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISCLINICALSTAGE_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75181_8L" title="Indicate the mother's surveillance stage of syphilis during pregnancy">
What SURVEILLANCE Stage of Syphilis Did Mother Have During Pregnancy:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75181_8)" styleId="75181_8" title="Indicate the mother's surveillance stage of syphilis during pregnancy">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISSURVEILLANCESTAGE_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="75182_6L" title="Indicate the date the patient received the first dose of benzathine penicillin">
When Did Mother Receive Her First Dose of Benzathine Penicillin:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(75182_6)" maxlength="10" size="10" styleId="75182_6" title="Indicate the date the patient received the first dose of benzathine penicillin" onkeyup="DateMask(this,null,event)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('75182_6','75182_6Icon'); return false;" styleId="75182_6Icon" onkeypress="showCalendarEnterKey('75182_6','75182_6Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75183_4L" title="Indicate the trimester the patient received the first dose of benzathine penicillin">
Which Trimester Did Mother Receive Her First Dose of Benzathine Penicillin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75183_4)" styleId="75183_4" title="Indicate the trimester the patient received the first dose of benzathine penicillin">
<nedss:optionsCollection property="codedValue(PHVS_PREGNANCYTREATMENTSTAGE_NND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS391L" title="Indicate if the mother was treated at least 30 days prior to delivery">
Was the Mother Treated At Least 30 Days Prior to Delivery:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS391)" styleId="NBS391" title="Indicate if the mother was treated at least 30 days prior to delivery">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75184_2L" title="Indicate the mother's treatment">
What Was the Mother's Treatment:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75184_2)" styleId="75184_2" title="Indicate the mother's treatment">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISTREATMENTMOTHER_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75185_9L" title="Did the subject have an appropriate serologic response to treatment">
Did Mother Have an Appropriate Serologic Response:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75185_9)" styleId="75185_9" title="Did the subject have an appropriate serologic response to treatment">
<nedss:optionsCollection property="codedValue(PHVS_SEROLOGICRESPONSE_CS)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_84" name="Infant Delivery Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75186_7L" title="Indicate the vital status of the infant">
Vital Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75186_7)" styleId="75186_7" title="Indicate the vital status of the infant">
<nedss:optionsCollection property="codedValue(PHVS_BIRTHSTATUS_NND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM229L" title="Infants birth weight in grams">
Birth Weight in Grams:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(DEM229)" size="4" maxlength="4"  title="Infants birth weight in grams" styleId="DEM229" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,9999)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM228L" title="Gestational age in weeks">
Estimated Gestational Age in Weeks:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(DEM228)" size="2" maxlength="2"  title="Gestational age in weeks" styleId="DEM228" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,50)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_85" name="Infant Clinical Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75194_1L" title="Did the infant/child have long bone x-rays">
Did the Infant/Child Have Long Bone X-rays:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75194_1)" styleId="75194_1" title="Did the infant/child have long bone x-rays">
<nedss:optionsCollection property="codedValue(PHVS_XRAYRESULT_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75192_5L" title="Did infant/child placenta or cord have Darkfield exam, DFA, or special stain?">
Did the Infant/Child, Placenta, or Cord Have Darkfield Exam, DFA, or Special Stains:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75192_5)" styleId="75192_5" title="Did infant/child placenta or cord have Darkfield exam, DFA, or special stain?">
<nedss:optionsCollection property="codedValue(LAB_RSLT_INTERP_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP48341_9L" title="Enter the interpretation of the CSF WBC count testing results">
CSF WBC Count Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP48341_9)" styleId="LP48341_9" title="Enter the interpretation of the CSF WBC count testing results">
<nedss:optionsCollection property="codedValue(LAB_RSLT_CSF_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LP69956_8L" title="Enter the interpretation of the CSF Protein level testing results">
CSF Protein Level Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LP69956_8)" styleId="LP69956_8" title="Enter the interpretation of the CSF Protein level testing results">
<nedss:optionsCollection property="codedValue(LAB_RSLT_PRT_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75195_8L" title="Enter the CSF VDRL Test Finding">
Did the Infant/Child Have CSF-VDRL:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75195_8)" styleId="75195_8" title="Enter the CSF VDRL Test Finding">
<nedss:optionsCollection property="codedValue(LAB_RSLT_VDRL_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75207_1L" title="Is this case classified as a syphilitic stillbirth?">
Stillbirth Indicator:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75207_1)" styleId="75207_1" title="Is this case classified as a syphilitic stillbirth?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV272L" title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click.">
Did the Infant/Child Have Any Signs of CS (Select All that Apply):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO.answerArray(INV272)" styleId="INV272" title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'INV272-selectedValues');enableOrDisableOther('INV272')" >
<nedss:optionsCollection property="codedValue(PHVS_SIGNSSYMPTOMS_CS)" value="key" label="value" /> </html:select>
<div id="INV272-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click." id="INV272OthL">Other Did the Infant/Child Have Any Signs of CS (Select All that Apply):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV272Oth)" size="40" maxlength="40" title="Other Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click." styleId="INV272Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75197_4L" title="Was the infant/child treated for congenital syphilis?">
Was the Infant/Child Treated?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75197_4)" styleId="75197_4" title="Was the infant/child treated for congenital syphilis?" onchange="ruleEnDis75197_47284();">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISTREATMENTINFANT_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS389L" title="If the treatment type is 'Other', specify the treatment.">
Other Treatment Specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS389)" maxlength="100" title="If the treatment type is 'Other', specify the treatment." styleId="NBS389"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_86" name="Infant Interpretive Lab Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_86errorMessages">
<b> <a name="NBS_INV_STD_UI_86errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_86">
<tr id="patternNBS_INV_STD_UI_86" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_86" onkeypress="viewClicked(this.id,'NBS_INV_STD_UI_86');return false"" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_86');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_86" onkeypress="editClicked(this.id,'NBS_INV_STD_UI_86');return false"" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_86');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_86" onkeypress="deleteClicked(this.id,'NBS_INV_STD_UI_86','patternNBS_INV_STD_UI_86','questionbodyNBS_INV_STD_UI_86');return false"" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_86','patternNBS_INV_STD_UI_86','questionbodyNBS_INV_STD_UI_86');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_STD_UI_86">
<tr id="nopatternNBS_INV_STD_UI_86" class="odd" style="display:">
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
<span class="NBS_INV_STD_UI_86 InputFieldLabel" id="LAB588L" title="Select the appropriate value to indicate timing and subject of the test being entered.">
Lab Test Performed Modifier (Infant):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB588)" styleId="LAB588" title="Select the appropriate value to indicate timing and subject of the test being entered." onchange="unhideBatchImg('NBS_INV_STD_UI_86');">
<nedss:optionsCollection property="codedValue(LAB_TST_MODIFIER_INFANT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_86 InputFieldLabel" id="INV290L" title="Epidemiologic interpretation of the type of test(s) performed for this case.">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Epidemiologic interpretation of the type of test(s) performed for this case." onchange="unhideBatchImg('NBS_INV_STD_UI_86');ruleEnDisINV2907286();">
<nedss:optionsCollection property="codedValue(PHVS_TESTTYPE_SYPHILIS)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_86 InputFieldLabel" id="INV291L" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative.">
Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." onchange="unhideBatchImg('NBS_INV_STD_UI_86');">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_86 InputFieldLabel" id="STD123_1L" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024.">
Nontreponemal Serologic Syphilis Test Result (Quantitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(STD123_1)" styleId="STD123_1" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024." onchange="unhideBatchImg('NBS_INV_STD_UI_86');">
<nedss:optionsCollection property="codedValue(PHVS_NONTREPONEMALTESTRESULT_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB167L" title="Date result sent from Reporting Laboratory">
Lab Result Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB167)" maxlength="10" size="10" styleId="LAB167" title="Date result sent from Reporting Laboratory" onkeyup="unhideBatchImg('NBS_INV_STD_UI_86');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_86"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB167','LAB167Icon'); unhideBatchImg('NBS_INV_STD_UI_86');return false;" styleId="LAB167Icon" onkeypress="showCalendarEnterKey('LAB167','LAB167Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_86">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_STD_UI_86BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_86','patternNBS_INV_STD_UI_86','questionbodyNBS_INV_STD_UI_86')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_86">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_86BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_86','patternNBS_INV_STD_UI_86','questionbodyNBS_INV_STD_UI_86')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_86"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_86BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_86','patternNBS_INV_STD_UI_86','questionbodyNBS_INV_STD_UI_86')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_86"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_STD_UI_86')"/>&nbsp;	&nbsp;&nbsp;
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

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="INV169L" title="Condition_Cd should always be a hidden or read-only field.">
Hidden Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV169)" styleId="INV169" title="Condition_Cd should always be a hidden or read-only field." disabled="true">
<nedss:optionsCollection property="codedValue(PHC_TYPE)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
