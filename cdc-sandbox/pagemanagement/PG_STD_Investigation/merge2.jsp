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
String [] sectionNames  = {"Patient Information","Address Information","Telephone and Email Contact Information","Race and Ethnicity Information","Other Identifying Information","Investigation Information","OOJ Initiating Agency Information","Reporting Information","Clinical","Epidemiologic","Comments","Case Numbers","Initial Follow-up","Surveillance","Notification of Exposure Information","Field Follow-up Information","Interview Case Assignment","Case Closure","Pregnant Information","900 Case Status","Risk Factors-Last 12 Months","Hangouts","Partner Information","Target Populations","STD Testing","Signs and Symptoms","STD History","900 Partner Services Information","Contact Investigation"};
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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM250_2L" title="The patient's alias or nickname.">
Alias/Nickname:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM250)" maxlength="40" title="The patient's alias or nickname." styleId="DEM250"/>
</td> </tr>
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
<span class=" InputFieldLabel" id="DEM113_2L" title="Patient's current sex.">
Current Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM113)" styleId="DEM113_2" title="Patient's current sex." onchange="ruleEnDisDEM1137880();ruleEnDisDEM1137877();ruleEnDisINV1787869();ruleRequireIfINV1787870();ruleEnDisINV1787869();ruleRequireIfINV1787870();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SEX)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS272_2L" title="Patient's current sex if identified as unknown (i.e., not male or female).">
Unknown Reason:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS272)" styleId="NBS272_2" title="Patient's current sex if identified as unknown (i.e., not male or female).">
<nedss:optionsCollection property="codedValue(SEX_UNK_REASON)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS274_2L" title="Patient's transgender identity.">
Gender Identity/Transgender Info:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS274)" styleId="NBS274_2" title="Patient's transgender identity.">
<nedss:optionsCollection property="codedValue(NBS_STD_GENDER_PARPT)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS213_2L" title="The specific gender information of the index patient if other selections do not apply (i.e. intersex, two-spirited, etc.).">
Additional Gender:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS213)" maxlength="30" title="The specific gender information of the index patient if other selections do not apply (i.e. intersex, two-spirited, etc.)." styleId="NBS213"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM114_2L" title="Patient Birth Sex">
Birth Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM114)" styleId="DEM114_2" title="Patient Birth Sex">
<nedss:optionsCollection property="codedValue(SEX)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV592_2L" title="Patient identified sexual orientation (i.e., an individual's physical and/or emotional attraction to another individual of the same gender, opposite gender, or both genders).">
Sexual Orientation/Preference:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV592)" styleId="INV592_2" title="Patient identified sexual orientation (i.e., an individual's physical and/or emotional attraction to another individual of the same gender, opposite gender, or both genders)." onchange="enableOrDisableOther('INV592');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SEXUALORIENTATION_CDC)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Patient identified sexual orientation (i.e., an individual's physical and/or emotional attraction to another individual of the same gender, opposite gender, or both genders)." id="INV592_2OthL">Other Sexual Orientation/Preference:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(INV592Oth)" size="40" maxlength="40" title="Other Patient identified sexual orientation (i.e., an individual's physical and/or emotional attraction to another individual of the same gender, opposite gender, or both genders)." styleId="INV592_2Oth"/></td></tr>

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
<span class=" InputFieldLabel" id="DEM126_2L" title="Country of Birth">
Country of Birth:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM126)" styleId="DEM126_2" title="Country of Birth">
<nedss:optionsCollection property="codedValue(PHVS_BIRTHCOUNTRY_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="DEM127_2L" title="Indicator of whether or not a patient is alive or dead.">
Is the patient deceased?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM127)" styleId="DEM127_2" title="Indicator of whether or not a patient is alive or dead." onchange="ruleEnDisDEM1277857();pgSelectNextFocus(this);">
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

<!--processing Date Question-->
<logic:notEqual name="PageForm" property="actionMode" value="Create">
<tr><td class="fieldName">
<span class="InputFieldLabel" id="NBS451_2L" title="Patient SSN as of Date">
SSN as of Date:</span>
</td>
<td>
<html:text name="PageForm" title="Patient SSN as of Date"  property="pageClientVO2.answer(NBS451)" maxlength="10" size="10" styleId="NBS451_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:notEqual>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM133_2L" title="Patient SSN">
SSN:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM133)" maxlength="12" title="Patient SSN" styleId="DEM133_2" onkeyup="SSNMask(this, event)"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="ENTITYID100_2" name="Entity ID Information" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="ENTITYID100_2errorMessages">
<b> <a name="ENTITYID100_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "ENTITYID100_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyENTITYID100_2">
<tr id="patternENTITYID100_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewENTITYID100_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'ENTITYID100_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyENTITYID100_2">
<tr id="nopatternENTITYID100_2" class="odd" style="display:">
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="
requiredInputField
InputFieldLabel" id="NBS452_2L" title="Entity ID as of Date.">
As Of:</span>
</td>
<td>
<html:text  name="PageForm" title="Entity ID as of Date." styleClass="requiredInputFieldENTITYID100_2" property="pageClientVO2.answer(NBS452)" maxlength="10" size="10" styleId="NBS452_2" onkeyup="unhideBatchImg('ENTITYID100');DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldENTITYID100_2 InputFieldLabel" id="DEM144_2L" title="Entity ID type for patient">
Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM144)" styleId="DEM144_2" title="Entity ID type for patient" onchange="unhideBatchImg('ENTITYID100');enableOrDisableOther('DEM144');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(EI_TYPE_PAT)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Entity ID type for patient" id="DEM144_2OthL">Other Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(DEM144Oth)" size="40" maxlength="40" title="Other Entity ID type for patient" onkeyup="unhideBatchImg('ENTITYID100')" styleId="DEM144_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="ENTITYID100 InputFieldLabel" id="DEM146_2L" title="Assigning Authority of Patient ID">
Authority:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(DEM146)" styleId="DEM146_2" title="Assigning Authority of Patient ID" onchange="unhideBatchImg('ENTITYID100');">
<nedss:optionsCollection property="codedValue(EI_AUTH_PAT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="InputFieldLabel" id="DEM147_2L" title="Patient ID Value">
Value:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM147)" maxlength="40" title="Patient ID Value" styleId="DEM147_2" onkeyup="unhideBatchImg('ENTITYID100');" styleClass="requiredInputFieldENTITYID100_2"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleENTITYID100">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgENTITYID100BatchAddFunction()) writeQuestion('ENTITYID100_2','patternENTITYID100_2','questionbodyENTITYID100_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleENTITYID100_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgENTITYID100BatchAddFunction()) writeQuestion('ENTITYID100_2','patternENTITYID100_2','questionbodyENTITYID100_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleENTITYID100_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgENTITYID100BatchAddFunction()) writeQuestion('ENTITYID100_2','patternENTITYID100_2','questionbodyENTITYID100_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleENTITYID100"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('ENTITYID100_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="DEM168_2L" title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts.">
Census Tract:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(DEM168)" maxlength="20" title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts." styleId="DEM168" onblur="checkCensusTract(this)"/>
</td> </tr>

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
<nedss:container id="NBS_INV_STD_UI_2_2" name="Additional Residence Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS201_2L" title="The RELATIONSHIP (such as spouse, parents, sibling, partner, roommate, etc., not the name) of those living with the patient.">
Living With:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS201)" maxlength="20" title="The RELATIONSHIP (such as spouse, parents, sibling, partner, roommate, etc., not the name) of those living with the patient." styleId="NBS201"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS202_2L" title="The type of residence in which the patient currenlty resides.">
Type of Residence:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS202)" styleId="NBS202_2" title="The type of residence in which the patient currenlty resides.">
<nedss:optionsCollection property="codedValue(RESIDENCE_TYPE_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS203_2L" title="The length of time the patient has lived at the current address.">
Time at Address:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS203)" size="2" maxlength="2"  title="The length of time the patient has lived at the current address." styleId="NBS203_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS204_2L" title="Unit if time used to describe time at address.">
Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS204)" styleId="NBS204_2" title="Unit if time used to describe time at address.">
<nedss:optionsCollection property="codedValue(WKS_MOS_YRS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS205_2L" title="The length of time the patient has lived in this state/territory.">
Time in State:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS205)" size="2" maxlength="2"  title="The length of time the patient has lived in this state/territory." styleId="NBS205_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS206_2L" title="Unit if time used to describe time in state.">
Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS206)" styleId="NBS206_2" title="Unit if time used to describe time in state.">
<nedss:optionsCollection property="codedValue(WKS_MOS_YRS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS207_2L" title="The length of time the patient has lived in the country.">
Time in Country:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS207)" size="2" maxlength="2"  title="The length of time the patient has lived in the country." styleId="NBS207_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS208_2L" title="Unit if time used to describe time in country.">
Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS208)" styleId="NBS208_2" title="Unit if time used to describe time in country.">
<nedss:optionsCollection property="codedValue(WKS_MOS_YRS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS209_2L" title="Indicate if the patient is institutionalized (i.e., in jail, in a group home, in a mental health facility, etc.)">
Currently institutionalized:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS209)" styleId="NBS209_2" title="Indicate if the patient is institutionalized (i.e., in jail, in a group home, in a mental health facility, etc.)" onchange="ruleHideUnhNBS2097882();ruleRequireIfNBS2097860();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS210_2L" title="Name of Institutition">
If institutionalized, document the name of the facility.:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS210)" maxlength="40" title="Name of Institutition" styleId="NBS210"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS211_2L" title="Type of Institutition">
Type of Institutition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS211)" styleId="NBS211_2" title="Type of Institutition">
<nedss:optionsCollection property="codedValue(INSTITUTION_TYPE_STD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

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
<html:select name="PageForm" property="pageClientVO2.answer(DEM155)" styleId="DEM155_2" title="Indicates if the patient is hispanic or not." onchange="ruleEnDisDEM1557879();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_ETHNICITYGROUP_CDC_UNK)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS273_2L" title="Specify reason the patient's ethnicity is unknown.">
Reason Unknown:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS273)" styleId="NBS273_2" title="Specify reason the patient's ethnicity is unknown.">
<nedss:optionsCollection property="codedValue(P_ETHN_UNK_REASON)" value="key" label="value" /></html:select>
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

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_6_2" name="Other Identifying Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS155_2L" title="The approximate or specific height of the patient.">
Height:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS155)" maxlength="15" title="The approximate or specific height of the patient." styleId="NBS155"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS156_2L" title="The approximate or specific weight or body type of the patient.">
Size/Build:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS156)" maxlength="15" title="The approximate or specific weight or body type of the patient." styleId="NBS156"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS157_2L" title="The description of the patients hair, including color, length, and/or style.">
Hair:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS157)" maxlength="15" title="The description of the patients hair, including color, length, and/or style." styleId="NBS157"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS158_2L" title="The approximate or specific skin tone/hue of the patient.">
Complexion:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS158)" maxlength="15" title="The approximate or specific skin tone/hue of the patient." styleId="NBS158"/>
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS159_2L" title="Any additional demographic information (e.g., tattoos, etc).">
Other Identifying Info:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS159)" styleId ="NBS159_2" onkeyup="checkTextAreaLength(this, 2000)" title="Any additional demographic information (e.g., tattoos, etc)."/>
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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS110_2L" title="Document the reason (referral basis) why the investigation was initiated.">
Referral Basis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS110)" styleId="NBS110_2" title="Document the reason (referral basis) why the investigation was initiated.">
<nedss:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /></html:select>
</td></tr>

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

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS115_2L" title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)">
Current Process Stage:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS115)" styleId="NBS115_2" title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)">
<nedss:optionsCollection property="codedValue(CM_PROCESS_STAGE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV147_2L" title="The date the investigation was started or initiated.">
Investigation Start Date:</span>
</td>
<td>
<html:text name="PageForm" title="The date the investigation was started or initiated."  property="pageClientVO2.answer(INV147)" maxlength="10" size="10" styleId="INV147_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->

<!--processing ReadOnly Date-->
<tr><td class="fieldName">
<span title="The date the investigation is closed." id="INV2006_2L">Investigation Close Date:</span>
</td><td>
<span id="INV2006S"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV2006)"  />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO2.answer(INV2006)" styleId="INV2006_2" />
</td>
</tr>

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

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS270_2L" title="Referral Basis - OOJ">
Referral Basis - OOJ:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS270)" styleId="NBS270_2" title="Referral Basis - OOJ">
<nedss:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20_2" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180_2L" title="The Public Health Investigator assigned to the Investigation.">
Current Investigator:</span> </td>
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
<td class="fieldName" id="INV180_2S">Current Investigator Selected: </td>
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
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="The date the investigation was assigned/started." id="INV110_2L">
Date Assigned to Investigation:</span>
</td>
<td>
<html:text name="PageForm" title="The date the investigation was assigned/started." property="pageClientVO2.answer(INV110)" maxlength="10" size="10" styleId="INV110_2"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_8_2" name="OOJ Agency Initiating Report" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS111_2L" title="The Initiating Agency which sent the OOJ Contact Report.">
Initiating Agency:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS111)" styleId="NBS111_2" title="The Initiating Agency which sent the OOJ Contact Report." onchange="ruleEnDisNBS1117867();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(OOJ_AGENCY_LOCAL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS112_2L" title="The date the OOJ Contact report was received from the Initiating Agency.">
Date Received from Init. Agency:</span>
</td>
<td>
<html:text name="PageForm" title="The date the OOJ Contact report was received from the Initiating Agency."  property="pageClientVO2.answer(NBS112)" maxlength="10" size="10" styleId="NBS112_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS113_2L" title="The date OOJ outcome is due back to the Initiating Agency.">
Date OOJ Due to Init. Agency:</span>
</td>
<td>
<html:text name="PageForm" title="The date OOJ outcome is due back to the Initiating Agency."  property="pageClientVO2.answer(NBS113)" maxlength="10" size="10" styleId="NBS113_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS114_2L" title="The date OOJ outcome was sent back to the Initiating Agency.">
Date OOJ Info Sent:</span>
</td>
<td>
<html:text name="PageForm" title="The date OOJ outcome was sent back to the Initiating Agency."  property="pageClientVO2.answer(NBS114)" maxlength="10" size="10" styleId="NBS114_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_9_2" name="Reported as OOJ Contact" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS118_2L" title="The date of first exposure with sex partner reported  by OOJ Contact.">
First Sexual Exposure:</span>
</td>
<td>
<html:text name="PageForm" title="The date of first exposure with sex partner reported  by OOJ Contact."  property="pageClientVO2.answer(NBS118)" maxlength="10" size="10" styleId="NBS118_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS119_2L" title="The frequency of exposure with sex partner reported by OOJ Contact.">
Sexual Frequency:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS119)" maxlength="40" title="The frequency of exposure with sex partner reported by OOJ Contact." styleId="NBS119"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS120_2L" title="The date of last exposure with sex partner reported by OOJ Contact.">
Last Sexual Exposure:</span>
</td>
<td>
<html:text name="PageForm" title="The date of last exposure with sex partner reported by OOJ Contact."  property="pageClientVO2.answer(NBS120)" maxlength="10" size="10" styleId="NBS120_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS121_2L" title="The date of first exposure with needle-sharing partner reported by OOJ Contact.">
First Needle-Sharing Exposure:</span>
</td>
<td>
<html:text name="PageForm" title="The date of first exposure with needle-sharing partner reported by OOJ Contact."  property="pageClientVO2.answer(NBS121)" maxlength="10" size="10" styleId="NBS121_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS122_2L" title="The frequency of exposure with needle-sharing partner reported by OOJ Contact.">
Needle-Sharing Frequency:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS122)" maxlength="40" title="The frequency of exposure with needle-sharing partner reported by OOJ Contact." styleId="NBS122"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS123_2L" title="The date of last exposure with needle-sharing partner reported by OOJ Contact.">
Last Needle-Sharing Exposure:</span>
</td>
<td>
<html:text name="PageForm" title="The date of last exposure with needle-sharing partner reported by OOJ Contact."  property="pageClientVO2.answer(NBS123)" maxlength="10" size="10" styleId="NBS123_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS124_2L" title="The cluster relationship reported by OOJ Contact.">
Relationship:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS124)" styleId="NBS124_2" title="The cluster relationship reported by OOJ Contact.">
<nedss:optionsCollection property="codedValue(PH_RELATIONSHIP_HL7_2X)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS125_2L" title="As reported by OOJ Contact, is the patient the original patient's spouse?">
OP Spouse?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS125)" styleId="NBS125_2" title="As reported by OOJ Contact, is the patient the original patient's spouse?">
<nedss:optionsCollection property="codedValue(OP_SPOUSE_IND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS126_2L" title="As reported by OOJ Contact, did the patient meet the original patient on the internet?">
Met OP via Internet?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS126)" styleId="NBS126_2" title="As reported by OOJ Contact, did the patient meet the original patient on the internet?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS127_2L" title="During the course of the interview, did the DIS elicit internet information about the named contact?">
Internet Info Elicited?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS127)" styleId="NBS127_2" title="During the course of the interview, did the DIS elicit internet information about the named contact?" onchange="stdConInternetInfoElicitedRequireInternetFollowup();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
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

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="Date the report was first sent to the public health department (local, county or state) by reporter (physician, lab, etc.)." id="INV177_2L">
Date First Reported to PHD:</span>
</td>
<td>
<html:text name="PageForm" title="Date the report was first sent to the public health department (local, county or state) by reporter (physician, lab, etc.)." property="pageClientVO2.answer(INV177)" maxlength="10" size="10" styleId="INV177_2"/>
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
<nedss:container id="NBS_UI_32_2" name="Physician Clinic" isHidden="F" classType="subSect" >

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS291_2L" title="The clinic with which the physician associated with this case is affiliated.">
Physician Ordering Clinic:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS291Uid">
<span id="clearNBS291" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS291Uid">
<span id="clearNBS291">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS291CodeClearButton" onclick="clearOrganization('NBS291')"/>
</span>
<span id="NBS291_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS291Icon" onclick="getReportingOrg('NBS291');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS291)" styleId="NBS291Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('NBS291Text','NBS291_qec_list')"
title="The clinic with which the physician associated with this case is affiliated."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS291CodeLookupButton" onclick="getDWROrganization('NBS291')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS291Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS291_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS291_2S">Physician Ordering Clinic Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS291Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS291Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS291Uid"/>
<span id="NBS291_2">${PageForm.attributeMap2.NBS291SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS291_2Error"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_13_2" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV128_2L" title="Was the patient hospitalized as a result of this event?">
Was the patient hospitalized for this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV128)" styleId="INV128_2" title="Was the patient hospitalized as a result of this event?" onchange="ruleEnDisINV1287849();updateHospitalInformationFields('INV128', 'INV184','INV132','INV133','INV134');pgSelectNextFocus(this);;ruleDCompINV1327853();pgSelectNextFocus(this);">
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
Total duration of stay in the hospital (in days):</span>
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
<span class=" InputFieldLabel" id="INV145_2L" title="Indicates if the subject dies as a result of the illness.">
Did the patient die from this illness?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV145)" styleId="INV145_2" title="Indicates if the subject dies as a result of the illness." onchange="ruleEnDisINV1457850();pgSelectNextFocus(this);">
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="STD105_2L" title="Date treatment initiated for the condition that is the subject of this case report.">
Treatment Start Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date treatment initiated for the condition that is the subject of this case report."  property="pageClientVO2.answer(STD105)" maxlength="10" size="10" styleId="STD105_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="STD099_2L" title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date.">
Date of Initial Health Exam:</span>
</td>
<td>
<html:text name="PageForm" title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date."  property="pageClientVO2.answer(STD099)" maxlength="10" size="10" styleId="STD099_2" onkeyup="DateMask(this,null,event)"/>
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
<html:select name="PageForm" property="pageClientVO2.answer(INV150)" styleId="INV150_2" title="Denotes whether the reported case was associated with an identified outbreak." onchange="ruleEnDisINV1507851();pgSelectNextFocus(this);">
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
Where was the disease acquired:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV152)" styleId="INV152_2" title="Indication of where the disease/condition was likely acquired." onchange="ruleEnDisINV1527852();pgSelectNextFocus(this);">
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
<span class=" InputFieldLabel" id="NBS135_2L" title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient.">
Source/Spread:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS135)" styleId="NBS135_2" title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient.">
<nedss:optionsCollection property="codedValue(SOURCE_SPREAD)" value="key" label="value" /></html:select>
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
<nedss:container id="NBS11001_2" name="Reporting County" isHidden="F" classType="subSect" >

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
<nedss:container id="NBS11002_2" name="Exposure Location" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS11002_2errorMessages">
<b> <a name="NBS11002_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS11002_2">
<tr id="patternNBS11002_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS11002_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS11002_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS11002_2">
<tr id="nopatternNBS11002_2" class="odd" style="display:">
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
<span class="NBS11002 InputFieldLabel" id="INV502_2L" title="Indicates the country in which the disease was potentially acquired.">
Country of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV502)" styleId="INV502_2" title="Indicates the country in which the disease was potentially acquired." onchange="unhideBatchImg('NBS11002');ruleEnDisINV5027881();getDWRStatesByCountry(this, 'INV503');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PSL_CNTRY)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS11002 InputFieldLabel" id="INV503_2L" title="Indicates the state in which the disease was potentially acquired.">
State or Province of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV503)" styleId="INV503_2" title="Indicates the state in which the disease was potentially acquired." onchange="unhideBatchImg('NBS11002');getDWRCounties(this, 'INV505');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_STATEPROVINCEOFEXPOSURE_CDC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV504_2L" title="Indicates the city in which the disease was potentially acquired.">
City of Exposure:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV504)"  maxlength="50" title="Indicates the city in which the disease was potentially acquired." styleId="INV504_2" onkeyup="unhideBatchImg('NBS11002');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS11002 InputFieldLabel" id="INV505_2L" title="Indicates the county in which the disease was potentially acquired.">
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
<tr id="AddButtonToggleNBS11002">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS11002BatchAddFunction()) writeQuestion('NBS11002_2','patternNBS11002_2','questionbodyNBS11002_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS11002_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS11002BatchAddFunction()) writeQuestion('NBS11002_2','patternNBS11002_2','questionbodyNBS11002_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS11002_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS11002BatchAddFunction()) writeQuestion('NBS11002_2','patternNBS11002_2','questionbodyNBS11002_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS11002"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS11002_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS11003_2" name="Binational Reporting" isHidden="F" classType="subSect" >

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
<nedss:optionsCollection property="codedValue(PHVS_DETECTIONMETHOD_STD)" value="key" label="value" /></html:select>
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
<nedss:optionsCollection property="codedValue(PHVS_PHC_CLASS_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS136_2L" title="The disease diagnosis of the patient.">
Diagnosis Reported to CDC:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS136)" styleId="NBS136_2" title="The disease diagnosis of the patient." onchange="ruleEnDisNBS1367909();ruleEnDisNBS1367908();ruleEnDisNBS1367907();ruleEnDisNBS1367906();ruleEnDisNBS1367905();ruleEnDisNBS1367902();ruleEnDisSTD1027901();ruleEnDisSTD1027901();enableOrDisableOther('102957003');enableOrDisableOther('102957003');ruleEnDisSTD1027901();enableOrDisableOther('102957003');enableOrDisableOther('102957003');stdDiagnosisRelatedFields();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(CASE_DIAGNOSIS)" value="key" label="value" /></html:select>
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
<html:select name="PageForm" property="pageClientVO2.answer(NOT120)" styleId="NOT120_2" title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" onchange="ruleHideUnhNOT1207883();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NOT120SPEC_2L" title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition.">
If Yes, describe:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NOT120SPEC)" maxlength="50" title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." styleId="NOT120SPEC"/>
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_73_2" name="Syphilis Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD102_2L" title="Neurological Involvement?">
Neurological Manifestations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD102)" styleId="STD102_2" title="Neurological Involvement?" onchange="ruleEnDisSTD1027901();enableOrDisableOther('102957003');enableOrDisableOther('102957003');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="102957003_2L" title="What neurologic manifestations of syphilis are present?">
Neurologic Signs/Symptoms:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(102957003)" styleId="102957003_2" title="What neurologic manifestations of syphilis are present?"
multiple="true" size="4"
onchange="displaySelectedOptions(this, '102957003_2-selectedValues');enableOrDisableOther('102957003')" >
<nedss:optionsCollection property="codedValue(PHVS_NEUROLOGICALMANIFESTATION_STD)" value="key" label="value" /> </html:select>
<div id="102957003_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What neurologic manifestations of syphilis are present?" id="102957003_2OthL">Other Neurologic Signs/Symptoms:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(102957003Oth)" size="40" maxlength="40" title="Other What neurologic manifestations of syphilis are present?" styleId="102957003_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="410478005_2L" title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis.">
Ocular Manifestations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(410478005)" styleId="410478005_2" title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis.">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="PHC1472_2L" title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo.">
Otic Manifestations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(PHC1472)" styleId="PHC1472_2" title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo.">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="72083004_2L" title="Late clinical manifestations of syphilis (tertiary syphilis) may include inflammatory lesions of the cardiovascular system, skin, bone, or other tissue. Certain neurologic manifestations (e.g., general paresis and tabes dorsalis) are late clinical manifestations of syphilis.">
Late Clinical Manifestations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(72083004)" styleId="72083004_2" title="Late clinical manifestations of syphilis (tertiary syphilis) may include inflammatory lesions of the cardiovascular system, skin, bone, or other tissue. Certain neurologic manifestations (e.g., general paresis and tabes dorsalis) are late clinical manifestations of syphilis.">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_74_2" name="Other Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV179_2L" title="Pelvic inflammatory disease present?">
PID:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV179)" styleId="INV179_2" title="Pelvic inflammatory disease present?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS137_2L" title="Disseminated?">
Disseminated:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS137)" styleId="NBS137_2" title="Disseminated?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV361_2L" title="Conjunctivitis?">
Conjunctivitis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV361)" styleId="INV361_2" title="Conjunctivitis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS138_2L" title="To what drug(s) is the disease resistant?">
Resistant to:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS138)" styleId="NBS138_2" title="To what drug(s) is the disease resistant?">
<nedss:optionsCollection property="codedValue(RESISTANT_TO_300_DRUG)" value="key" label="value" /></html:select>
</td></tr>
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
<nedss:container id="NBS_INV_STD_UI_12_2" name="Case Numbers" isHidden="F" classType="subSect" >

<!--processing ReadOnly Textbox Text Question-->
<tr> <td class="fieldName">
<span title="Unique field record identifier." id="NBS160_2L">
Field Record Number:</span>
</td>
<td>
<span id="NBS160S_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS160)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO2.answer(NBS160)" styleId="NBS160_2" />
</td>
</tr>

<!--processing ReadOnly Textbox Text Question-->
<tr> <td class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191_2L">
Lot Number:</span>
</td>
<td>
<span id="NBS191S_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS191)" />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO2.answer(NBS191)" styleId="NBS191_2" />
</td>
</tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="INV200_2L" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here.">
Legacy Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(INV200)" maxlength="50" title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." styleId="INV200"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_14_2" name="Initial Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS139_2L" title="The investigator assigning the initial follow-up.">
Investigator:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS139Uid">
<span id="clearNBS139_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS139Uid">
<span id="clearNBS139_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS139CodeClearButton_2" onclick="clearProvider('NBS139')"/>
</span>
<span id="NBS139_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS139_2_Icon" onclick="getProvider('NBS139');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS139)" styleId="NBS139_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS139Text','NBS139_qec_list')"
title="The investigator assigning the initial follow-up."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS139CodeLookupButton" onclick="getDWRProvider('NBS139')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS139Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS139_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS139_2S">Investigator Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS139Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS139Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS139Uid"/>
<span id="NBS139_2">${PageForm.attributeMap2.NBS139SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS139_2Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS140_2L" title="Initial Follow-up action.">
Initial Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS140)" styleId="NBS140_2" title="Initial Follow-up action." onchange="stdFixedRuleInitialFollowupEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(STD_NBS_PROCESSING_DECISION_ALL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS141_2L" title="The date the inital follow-up was identified as closed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm" title="The date the inital follow-up was identified as closed."  property="pageClientVO2.answer(NBS141)" maxlength="10" size="10" styleId="NBS141_2" onkeyup="DateMask(this,null,event)" onblur="stdInitialFollowupDateClosedEntered()" onchange="stdInitialFollowupDateClosedEntered()"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS142_2L" title="Initiate for Internet follow-up?">
Internet Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS142)" styleId="NBS142_2" title="Initiate for Internet follow-up?">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS144_2L" title="If applicable, enter the specific clinic code identifying the initiating clinic.">
Clinic Code:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS144)" maxlength="8" title="If applicable, enter the specific clinic code identifying the initiating clinic." styleId="NBS144"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_16_2" name="Surveillance Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS145_2L" title="The investigator assigned for surveillance follow-up.">
Assigned To:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS145Uid">
<span id="clearNBS145_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS145Uid">
<span id="clearNBS145_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS145CodeClearButton_2" onclick="clearProvider('NBS145')"/>
</span>
<span id="NBS145_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS145_2_Icon" onclick="getProvider('NBS145');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS145)" styleId="NBS145_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS145Text','NBS145_qec_list')"
title="The investigator assigned for surveillance follow-up."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS145CodeLookupButton" onclick="getDWRProvider('NBS145')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS145Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS145_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS145_2S">Assigned To Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS145Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS145Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS145Uid"/>
<span id="NBS145_2">${PageForm.attributeMap2.NBS145SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS145_2Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS146_2L" title="The date surveillance follow-up is assigned.">
Date Assigned:</span>
</td>
<td>
<html:text name="PageForm" title="The date surveillance follow-up is assigned."  property="pageClientVO2.answer(NBS146)" maxlength="10" size="10" styleId="NBS146_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS147_2L" title="The date surveillance follow-up is completed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm" title="The date surveillance follow-up is completed."  property="pageClientVO2.answer(NBS147)" maxlength="10" size="10" styleId="NBS147_2" onkeyup="DateMask(this,null,event)" onblur="stdSurveillanceDateClosedEntered()" onchange="stdSurveillanceDateClosedEntered()"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_17_2" name="Surveillance Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS148_2L" title="Indicate if the contact with the provider was successful or not.">
Provider Contact:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS148)" styleId="NBS148_2" title="Indicate if the contact with the provider was successful or not.">
<nedss:optionsCollection property="codedValue(PRVDR_CONTACT_OUTCOME)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS149_2L" title="The reporting provider's reason for examing the patient.">
Exam Reason:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS149)" styleId="NBS149_2" title="The reporting provider's reason for examing the patient.">
<nedss:optionsCollection property="codedValue(PRVDR_EXAM_REASON)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS150_2L" title="The reporting provider's diagnosis.">
Reporting Provider Diagnosis (Surveillance):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS150)" styleId="NBS150_2" title="The reporting provider's diagnosis.">
<nedss:optionsCollection property="codedValue(PRVDR_DIAGNOSIS_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS151_2L" title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason.">
Patient Follow-Up:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS151)" styleId="NBS151_2" title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason." onchange="stdFixedRuleSurveillancePatientFollowupEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SURVEILLANCE_PATIENT_FOLLOWUP)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_18_2" name="Surveillance Notes" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_18_2errorMessages">
<b> <a name="NBS_INV_STD_UI_18_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_18_2">
<tr id="patternNBS_INV_STD_UI_18_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_18_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_18_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_18_2">
<tr id="nopatternNBS_INV_STD_UI_18_2" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS152_2L" title="Notes for surveillance activities (e.g., type of information needed, additional comments.)">
Surveillance Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS152)" styleId ="NBS152_2" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS152');unhideBatchImg('NBS_INV_STD_UI_18');" title="Notes for surveillance activities (e.g., type of information needed, additional comments.)"/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS152Date_2L">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" title="This is a hidden read-only field for the Date the note was added or updated" property="pageClientVO2.answer(NBS152Date)" maxlength="10" size="10" styleId="NBS152Date_2"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS152User_2L">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS152User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS152User_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_18');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_18">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18_2','patternNBS_INV_STD_UI_18_2','questionbodyNBS_INV_STD_UI_18_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_18_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18_2','patternNBS_INV_STD_UI_18_2','questionbodyNBS_INV_STD_UI_18_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_18_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_18BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_18_2','patternNBS_INV_STD_UI_18_2','questionbodyNBS_INV_STD_UI_18_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_18"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_18_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_20_2" name="Patient Notification" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS143_2L" title="For field follow-up, is patient eligible for notification of exposure?">
Patient Eligible for Notification of Exposure:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS143)" styleId="NBS143_2" title="For field follow-up, is patient eligible for notification of exposure?" onchange="ruleEnDisNBS1437888();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(NOTIFIABLE)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS167_2L" title="The method agreed upon by the patient and investigator for notifying the partner(s) and clusters of potential HIV exposure.">
Notification Plan:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS167)" styleId="NBS167_2" title="The method agreed upon by the patient and investigator for notifying the partner(s) and clusters of potential HIV exposure.">
<nedss:optionsCollection property="codedValue(NOTIFICATION_PLAN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS177_2L" title="The notification method by which field follow-up patients are brought to examination, brought to treatment, and/or notified of exposure.">
Actual Referral Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS177)" styleId="NBS177_2" title="The notification method by which field follow-up patients are brought to examination, brought to treatment, and/or notified of exposure.">
<nedss:optionsCollection property="codedValue(NOTIFICATION_ACTUAL_METHOD_STD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_22_2" name="Field Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS161_2L" title="The investigator assigned to field follow-up activities.">
Investigator:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS161Uid">
<span id="clearNBS161_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS161Uid">
<span id="clearNBS161_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS161CodeClearButton_2" onclick="clearProvider('NBS161')"/>
</span>
<span id="NBS161_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS161_2_Icon" onclick="getProvider('NBS161');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS161)" styleId="NBS161_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS161Text','NBS161_qec_list')"
title="The investigator assigned to field follow-up activities."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS161CodeLookupButton" onclick="getDWRProvider('NBS161')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS161Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS161_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS161_2S">Investigator Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS161Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS161Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS161Uid"/>
<span id="NBS161_2">${PageForm.attributeMap2.NBS161SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS161_2Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS162_2L" title="The date the investigator is assigned to field follow-up activities.">
Date Assigned:</span>
</td>
<td>
<html:text name="PageForm" title="The date the investigator is assigned to field follow-up activities."  property="pageClientVO2.answer(NBS162)" maxlength="10" size="10" styleId="NBS162_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS163_2L" title="The investigator originally assigned to field follow-up activities.">
Initially Assigned:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS163Uid">
<span id="clearNBS163_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS163Uid">
<span id="clearNBS163_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS163CodeClearButton_2" onclick="clearProvider('NBS163')"/>
</span>
<span id="NBS163_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS163_2_Icon" onclick="getProvider('NBS163');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS163)" styleId="NBS163_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS163Text','NBS163_qec_list')"
title="The investigator originally assigned to field follow-up activities."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS163CodeLookupButton" onclick="getDWRProvider('NBS163')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS163Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS163_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS163_2S">Initially Assigned Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS163Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS163Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS163Uid"/>
<span id="NBS163_2">${PageForm.attributeMap2.NBS163SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS163_2Error"/>
</td></tr>

<!--processing Date Question-->

<!--processing ReadOnly Date-->
<tr><td class="fieldName">
<span title="The date of initial assignment for field follow-up." id="NBS164_2L">Initial Assignment Date:</span>
</td><td>
<span id="NBS164S"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS164)"  />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO2.answer(NBS164)" styleId="NBS164_2" />
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_23_2" name="Field Follow-up Exam Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS165_2L" title="The reporting provider's reason for examing the patient.">
Exam Reason:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS165)" styleId="NBS165_2" title="The reporting provider's reason for examing the patient.">
<nedss:optionsCollection property="codedValue(PRVDR_EXAM_REASON)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS166_2L" title="The reporting provider's diagnosis.">
Reporting Provider Diagnosis (Field Follow-up):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS166)" styleId="NBS166_2" title="The reporting provider's diagnosis.">
<nedss:optionsCollection property="codedValue(PRVDR_DIAGNOSIS_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS168_2L" title="Do you expect the patient to come in for examination?">
Expected In:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS168)" styleId="NBS168_2" title="Do you expect the patient to come in for examination?" onchange="ruleHideUnhNBS1687884();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS169_2L" title="The date the patient is expected to come in for examination.">
Expected In Date:</span>
</td>
<td>
<html:text name="PageForm" title="The date the patient is expected to come in for examination."  property="pageClientVO2.answer(NBS169)" maxlength="10" size="10" styleId="NBS169_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS170_2L" title="The date the patient was examined as a result of field activities.">
Exam Date:</span>
</td>
<td>
<html:text name="PageForm" title="The date the patient was examined as a result of field activities."  property="pageClientVO2.answer(NBS170)" maxlength="10" size="10" styleId="NBS170_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS171_2L" title="The provider who performed the exam.">
Provider:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS171Uid">
<span id="clearNBS171_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS171Uid">
<span id="clearNBS171_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS171CodeClearButton_2" onclick="clearProvider('NBS171')"/>
</span>
<span id="NBS171_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS171_2_Icon" onclick="getProvider('NBS171');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS171)" styleId="NBS171_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS171Text','NBS171_qec_list')"
title="The provider who performed the exam."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS171CodeLookupButton" onclick="getDWRProvider('NBS171')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS171Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS171_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS171_2S">Provider Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS171Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS171Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS171Uid"/>
<span id="NBS171_2">${PageForm.attributeMap2.NBS171SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS171_2Error"/>
</td></tr>

<!--processing Organization Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS172_2L" title="The facility at which the exam was performed.">
Facility:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS172Uid">
<span id="clearNBS172" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS172Uid">
<span id="clearNBS172">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS172CodeClearButton" onclick="clearOrganization('NBS172')"/>
</span>
<span id="NBS172_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS172Icon" onclick="getReportingOrg('NBS172');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS172)" styleId="NBS172Text"
size="10" maxlength="10" onkeydown="genOrganizationAutocomplete('NBS172Text','NBS172_qec_list')"
title="The facility at which the exam was performed."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS172CodeLookupButton" onclick="getDWROrganization('NBS172')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS172Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS172_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS172_2S">Facility Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS172Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS172Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS172Uid"/>
<span id="NBS172_2">${PageForm.attributeMap2.NBS172SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS172_2Error"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_24_2" name="Case Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS173_2L" title="The disposition of the field follow-up activities.">
Disposition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS173)" styleId="NBS173_2" title="The disposition of the field follow-up activities." onchange="stdFixedRuleFieldFollowupDispositionEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(FIELD_FOLLOWUP_DISPOSITION_STD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS174_2L" title="When the disposition was determined as relates to exam or treatment situation.">
Disposition Date:</span>
</td>
<td>
<html:text name="PageForm" title="When the disposition was determined as relates to exam or treatment situation."  property="pageClientVO2.answer(NBS174)" maxlength="10" size="10" styleId="NBS174_2" onkeyup="DateMask(this,null,event)" onblur="stdFieldFollowupDispositionDateEntry()" onchange="stdFieldFollowupDispositionDateEntry()"/>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS175_2L" title="The person who brought the field record/activities to final disposition.">
Dispositioned By:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS175Uid">
<span id="clearNBS175_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS175Uid">
<span id="clearNBS175_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS175CodeClearButton_2" onclick="clearProvider('NBS175')"/>
</span>
<span id="NBS175_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS175_2_Icon" onclick="getProvider('NBS175');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS175)" styleId="NBS175_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS175Text','NBS175_qec_list')"
title="The person who brought the field record/activities to final disposition."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS175CodeLookupButton" onclick="getDWRProvider('NBS175')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS175Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS175_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS175_2S">Dispositioned By Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS175Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS175Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS175Uid"/>
<span id="NBS175_2">${PageForm.attributeMap2.NBS175SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS175_2Error"/>
</td></tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS176_2L" title="The supervisor who should review the field record disposition.">
Supervisor:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS176Uid">
<span id="clearNBS176_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS176Uid">
<span id="clearNBS176_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS176CodeClearButton_2" onclick="clearProvider('NBS176')"/>
</span>
<span id="NBS176_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS176_2_Icon" onclick="getProvider('NBS176');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS176)" styleId="NBS176_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS176Text','NBS176_qec_list')"
title="The supervisor who should review the field record disposition."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS176CodeLookupButton" onclick="getDWRProvider('NBS176')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS176Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS176_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS176_2S">Supervisor Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS176Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS176Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS176Uid"/>
<span id="NBS176_2">${PageForm.attributeMap2.NBS176SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS176_2Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS178_2L" title="The outcome of internet based activities.">
Internet Outcome:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS178)" styleId="NBS178_2" title="The outcome of internet based activities.">
<nedss:optionsCollection property="codedValue(INTERNET_FOLLOWUP_OUTCOME)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_25_2" name="OOJ Field Record Sent To Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS179_2L" title="The name of the area where the out-of-jurisdiction Field Follow-up is sent.">
OOJ Agency FR Sent To:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS179)" styleId="NBS179_2" title="The name of the area where the out-of-jurisdiction Field Follow-up is sent.">
<nedss:optionsCollection property="codedValue(OOJ_AGENCY_LOCAL)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS180_2L" title="Field record number from initiating or receiving jurisdiction.">
OOJ FR Number In Receiving Area:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS180)" maxlength="10" title="Field record number from initiating or receiving jurisdiction." styleId="NBS180"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS181_2L" title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)">
OOJ Due Date from Receiving Area:</span>
</td>
<td>
<html:text name="PageForm" title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)"  property="pageClientVO2.answer(NBS181)" maxlength="10" size="10" styleId="NBS181_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS182_2L" title="The outcome of the OOJ jurisdiction field activities.">
OOJ Outcome from Receiving Area:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS182)" styleId="NBS182_2" title="The outcome of the OOJ jurisdiction field activities.">
<nedss:optionsCollection property="codedValue(FIELD_FOLLOWUP_DISPOSITION_STD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_26_2" name="Field Follow-Up Notes" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_26_2errorMessages">
<b> <a name="NBS_INV_STD_UI_26_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_26_2">
<tr id="patternNBS_INV_STD_UI_26_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_26_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_26_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_26_2">
<tr id="nopatternNBS_INV_STD_UI_26_2" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS185_2L" title="Note text.">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS185)" styleId ="NBS185_2" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS185');unhideBatchImg('NBS_INV_STD_UI_26');" title="Note text."/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS185Date_2L">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" title="This is a hidden read-only field for the Date the note was added or updated" property="pageClientVO2.answer(NBS185Date)" maxlength="10" size="10" styleId="NBS185Date_2"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS185User_2L">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS185User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS185User_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_26');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_26">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26_2','patternNBS_INV_STD_UI_26_2','questionbodyNBS_INV_STD_UI_26_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_26_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26_2','patternNBS_INV_STD_UI_26_2','questionbodyNBS_INV_STD_UI_26_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_26_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_26BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_26_2','patternNBS_INV_STD_UI_26_2','questionbodyNBS_INV_STD_UI_26_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_26"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_26_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_27_2" name="Field Supervisory Review and Comments" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_27_2errorMessages">
<b> <a name="NBS_INV_STD_UI_27_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_27_2">
<tr id="patternNBS_INV_STD_UI_27_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_27_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_27_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_27_2">
<tr id="nopatternNBS_INV_STD_UI_27_2" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS268_2L" title="Note text">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS268)" styleId ="NBS268_2" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS268');unhideBatchImg('NBS_INV_STD_UI_27');" title="Note text"/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS268Date_2L">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" title="This is a hidden read-only field for the Date the note was added or updated" property="pageClientVO2.answer(NBS268Date)" maxlength="10" size="10" styleId="NBS268Date_2"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS268User_2L">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS268User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS268User_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_27');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_27">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27_2','patternNBS_INV_STD_UI_27_2','questionbodyNBS_INV_STD_UI_27_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_27_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27_2','patternNBS_INV_STD_UI_27_2','questionbodyNBS_INV_STD_UI_27_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_27_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_27BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_27_2','patternNBS_INV_STD_UI_27_2','questionbodyNBS_INV_STD_UI_27_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_27"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_27_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_29_2" name="Interview Case Assignment" isHidden="F" classType="subSect" >

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS186_2L" title="The investigator assigned to perform interview(s).">
Interviewer:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS186Uid">
<span id="clearNBS186_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS186Uid">
<span id="clearNBS186_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS186CodeClearButton_2" onclick="clearProvider('NBS186')"/>
</span>
<span id="NBS186_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS186_2_Icon" onclick="getProvider('NBS186');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS186)" styleId="NBS186_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS186Text','NBS186_qec_list')"
title="The investigator assigned to perform interview(s)."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS186CodeLookupButton" onclick="getDWRProvider('NBS186')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS186Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS186_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS186_2S">Interviewer Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS186Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS186Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS186Uid"/>
<span id="NBS186_2">${PageForm.attributeMap2.NBS186SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS186_2Error"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS187_2L" title="The date assigned for interview.">
Date Assigned:</span>
</td>
<td>
<html:text name="PageForm" title="The date assigned for interview."  property="pageClientVO2.answer(NBS187)" maxlength="10" size="10" styleId="NBS187_2" onkeyup="DateMaskFuture(this,null,event)"/>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS188_2L" title="The investigator originally assigned for interview.">
Initially Assigned:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS188Uid">
<span id="clearNBS188_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS188Uid">
<span id="clearNBS188_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS188CodeClearButton_2" onclick="clearProvider('NBS188')"/>
</span>
<span id="NBS188_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS188_2_Icon" onclick="getProvider('NBS188');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS188)" styleId="NBS188_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS188Text','NBS188_qec_list')"
title="The investigator originally assigned for interview."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS188CodeLookupButton" onclick="getDWRProvider('NBS188')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS188Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS188_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS188_2S">Initially Assigned Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS188Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS188Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS188Uid"/>
<span id="NBS188_2">${PageForm.attributeMap2.NBS188SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS188_2Error"/>
</td></tr>

<!--processing Date Question-->

<!--processing ReadOnly Date-->
<tr><td class="fieldName">
<span title="The date of initial assignment for case assignment." id="NBS189_2L">Initial Assignment Date:</span>
</td><td>
<span id="NBS189S"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS189)"  />
</td>
<td>
<html:hidden name="PageForm"  property="pageClientVO2.answer(NBS189)" styleId="NBS189_2" />
</td>
</tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS190_2L" title="The supervisor who should review the case follow-up closure.">
Supervisor:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS190Uid">
<span id="clearNBS190_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS190Uid">
<span id="clearNBS190_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS190CodeClearButton_2" onclick="clearProvider('NBS190')"/>
</span>
<span id="NBS190_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS190_2_Icon" onclick="getProvider('NBS190');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS190)" styleId="NBS190_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS190Text','NBS190_qec_list')"
title="The supervisor who should review the case follow-up closure."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS190CodeLookupButton" onclick="getDWRProvider('NBS190')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS190Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS190_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS190_2S">Supervisor Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS190Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS190Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS190Uid"/>
<span id="NBS190_2">${PageForm.attributeMap2.NBS190SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS190_2Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS192_2L" title="Indicate the status of interviewing the patient of this investigation.">
Patient Interview Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS192)" styleId="NBS192_2" title="Indicate the status of interviewing the patient of this investigation." onchange="stdFixedRulePatientInterviewStatusEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PAT_INTVW_STATUS)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_30_2" name="Interview/Investigation Notes" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_30_2errorMessages">
<b> <a name="NBS_INV_STD_UI_30_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_30_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_30_2">
<tr id="patternNBS_INV_STD_UI_30_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_30_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_30_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_30_2">
<tr id="nopatternNBS_INV_STD_UI_30_2" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS195_2L" title="Note text.">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS195)" styleId ="NBS195_2" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS195');unhideBatchImg('NBS_INV_STD_UI_30');" title="Note text."/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS195Date_2L">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" title="This is a hidden read-only field for the Date the note was added or updated" property="pageClientVO2.answer(NBS195Date)" maxlength="10" size="10" styleId="NBS195Date_2"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS195User_2L">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS195User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS195User_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_30');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_30">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_30BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_30_2','patternNBS_INV_STD_UI_30_2','questionbodyNBS_INV_STD_UI_30_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_30_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_30BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_30_2','patternNBS_INV_STD_UI_30_2','questionbodyNBS_INV_STD_UI_30_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_30_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_30BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_30_2','patternNBS_INV_STD_UI_30_2','questionbodyNBS_INV_STD_UI_30_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_30"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_30_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_32_2" name="Case Closure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Investigation may not be closed while interview status is awaiting or investigation is pending supervisor approval of field record closure. Also all contact records identified in this investigation must have a disposition.</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS196_2L" title="The date the case follow-up is closed.">
Date Closed:</span>
</td>
<td>
<html:text name="PageForm" title="The date the case follow-up is closed."  property="pageClientVO2.answer(NBS196)" maxlength="10" size="10" styleId="NBS196_2" onkeyup="DateMask(this,null,event)" onblur="stdCaseClosureDateClosedEntered()" onchange="stdCaseClosureDateClosedEntered()"/>
</td> </tr>

<!--processing Provider Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS197_2L" title="The investigator who closed out the case follow-up.">
Closed By:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS197Uid">
<span id="clearNBS197_2" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS197Uid">
<span id="clearNBS197_2">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS197CodeClearButton_2" onclick="clearProvider('NBS197')"/>
</span>
<span id="NBS197_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS197_2_Icon" onclick="getProvider('NBS197');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS197)" styleId="NBS197_2Text"
size="10" maxlength="10" onkeydown="genProviderAutocomplete('NBS197Text','NBS197_qec_list')"
title="The investigator who closed out the case follow-up."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS197CodeLookupButton" onclick="getDWRProvider('NBS197')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS197Uid">
style="visibility:hidden"
</logic:notEmpty>
/><div class="page_name_auto_complete" id="NBS197_qec_list" style="background:#DCDCDC"></div>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS197_2S">Closed By Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS197Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS197Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS197Uid"/>
<span id="NBS197_2">${PageForm.attributeMap2.NBS197SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS197_2Error"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS444_2L" title="Indicate whether or not the patient was in medical care at the time of the case close date.">
Care Status at Case Close Date:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS444)" styleId="NBS444_2" title="Indicate whether or not the patient was in medical care at the time of the case close date.">
<nedss:optionsCollection property="codedValue(NBS_CARE_STATUS)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_34_2" name="Supervisory Review/Comments" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_34_2errorMessages">
<b> <a name="NBS_INV_STD_UI_34_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
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
<tbody id="questionbodyNBS_INV_STD_UI_34_2">
<tr id="patternNBS_INV_STD_UI_34_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_34_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_34_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_34_2">
<tr id="nopatternNBS_INV_STD_UI_34_2" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS200_2L" title="Note text.">
Note:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px; background-color:white; color:black; border:currentColor;" name="PageForm" property="pageClientVO2.answer(NBS200)" styleId ="NBS200_2" onkeyup="checkTextAreaLength(this, 1900)" onchange="rollingNoteSetUserDate('NBS200');unhideBatchImg('NBS_INV_STD_UI_34');" title="Note text."/>
</td> </tr>
<!--Adding Hidden Date and User fields for Batch Rolling Note-->

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="This is a hidden read-only field for the Date the note was added or updated" id="NBS200Date_2L">
Date Added or Updated:</span>
</td>
<td>
<html:text name="PageForm" title="This is a hidden read-only field for the Date the note was added or updated" property="pageClientVO2.answer(NBS200Date)" maxlength="10" size="10" styleId="NBS200Date_2"/>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="This is a hidden read-only field for the user that added or updated the note" id="NBS200User_2L">
Added or Updated By:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS200User)" maxlength="30" title="This is a hidden read-only field for the user that added or updated the note" styleId="NBS200User_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_34');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_34">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34_2','patternNBS_INV_STD_UI_34_2','questionbodyNBS_INV_STD_UI_34_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_34_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34_2','patternNBS_INV_STD_UI_34_2','questionbodyNBS_INV_STD_UI_34_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_34_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_34BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_34_2','patternNBS_INV_STD_UI_34_2','questionbodyNBS_INV_STD_UI_34_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_34"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_34_2')"/>&nbsp;	&nbsp;&nbsp;
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
<nedss:container id="NBS_INV_STD_UI_37_2" name="Pregnant Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV178_2L" title="Assesses whether or not the patient is pregnant.">
Is the patient pregnant?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV178)" styleId="INV178_2" title="Assesses whether or not the patient is pregnant." onchange="ruleEnDisINV1787869();ruleRequireIfINV1787870();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS128_2L" title="Number of weeks pregnant at the time of diagnosis.">
Weeks:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS128)" size="2" maxlength="2"  title="Number of weeks pregnant at the time of diagnosis." styleId="NBS128_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99);stdCheckFieldMinMaxUnk(this,0,45,99)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS216_2L" title="Was the patient pregnant at the initial exam for the condition?">
Pregnant at Exam:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS216)" styleId="NBS216_2" title="Was the patient pregnant at the initial exam for the condition?" onchange="ruleEnDisNBS2167861();ruleRequireIfNBS2167862();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS217_2L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of the initial exam.">
Weeks:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS217)" size="2" maxlength="2"  title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of the initial exam." styleId="NBS217_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99);stdCheckFieldMinMaxUnk(this,0,45,99)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS218_2L" title="Was the patient pregnant at the time of interview for the condition.">
Pregnant at Interview:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS218)" styleId="NBS218_2" title="Was the patient pregnant at the time of interview for the condition." onchange="ruleEnDisNBS2187863();ruleRequireIfNBS2187864();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS219_2L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of interview.">
Weeks:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS219)" size="2" maxlength="2"  title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of interview." styleId="NBS219_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99);stdCheckFieldMinMaxUnk(this,0,45,99)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS220_2L" title="Is the patient receiving/received prenatal care for this pregnancy?">
Currently in Prenatal Care:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS220)" styleId="NBS220_2" title="Is the patient receiving/received prenatal care for this pregnancy?">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS221_2L" title="Determine if the patient has been pregnant during the last 12 months. If currently pregnant, a  Yes  answer indicates that the patient had another pregnancy within the past 12 months, not including her current  Pegnancy.">
Pregnant in Last 12 Months:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS221)" styleId="NBS221_2" title="Determine if the patient has been pregnant during the last 12 months. If currently pregnant, a  Yes  answer indicates that the patient had another pregnancy within the past 12 months, not including her current  Pegnancy." onchange="ruleEnDisNBS2217865();ruleRequireIfNBS2217866();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS222_2L" title="If pregnant in the last 12 months, indicate the outcome of the pregnancy.">
Pregnancy Outcome:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS222)" styleId="NBS222_2" title="If pregnant in the last 12 months, indicate the outcome of the pregnancy.">
<nedss:optionsCollection property="codedValue(PREGNANCY_OUTCOME)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_39_2" name="Patient HIV Status" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS153_2L" title="The patient's HIV status.">
900 Status:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS153)" styleId="NBS153_2" title="The patient's HIV status.">
<nedss:optionsCollection property="codedValue(STATUS_900)" value="key" label="value" /></html:select>
</td></tr>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV892_2L" title="Was the HIV status of this case investigated through search of eHARS?">
HIV Status Documented Through eHARS Record Search:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV892)" styleId="INV892_2" title="Was the HIV status of this case investigated through search of eHARS?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS269_2L" title="Enter the state-assigned HIV Case Number for this patient.">
State HIV (eHARS) Case ID:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS269)" maxlength="16" title="Enter the state-assigned HIV Case Number for this patient." styleId="NBS269"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV894_2L" title="Mode of exposure from eHARS for HIV+ cases.">
Transmission Category (eHARS):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV894)" styleId="INV894_2" title="Mode of exposure from eHARS for HIV+ cases.">
<nedss:optionsCollection property="codedValue(PHVS_TRANSMISSIONCATEGORY_STD)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV895_2L" title="Was this case selected by reporting jurisdiction for enhanced investigation?">
Case Sampled for Enhanced Investigation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV895)" styleId="INV895_2" title="Was this case selected by reporting jurisdiction for enhanced investigation?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_41_2" name="Risk Factors (Last 12 Months)" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS229_2L" title="Were behavioral risk factors assessed for the client?">
Was Behavioral Risk Assessed:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS229)" styleId="NBS229_2" title="Were behavioral risk factors assessed for the client?" onchange="stdBehavorialRiskAssessedEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(RISK_PROFILE_IND)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_42_2" name="Sex Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD107_2L" title="Had sex with a male within past 12 months?">
Had Sex with Male:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD107)" styleId="STD107_2" title="Had sex with a male within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD108_2L" title="Had sex with a female within past 12 months?">
Had Sex with Female:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD108)" styleId="STD108_2" title="Had sex with a female within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS230_2L" title="Had sex with transgender partner within past 12 months?">
Had Sex with Transgender:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS230)" styleId="NBS230_2" title="Had sex with transgender partner within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD109_2L" title="Had sex with an anonymous partner within past 12 months?">
Had Sex with Anonymous Partner:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD109)" styleId="STD109_2" title="Had sex with an anonymous partner within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_43_2" name="Sex Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS231_2L" title="Had sex without a condom within past 12 months?">
Had Sex Without a Condom:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS231)" styleId="NBS231_2" title="Had sex without a condom within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD111_2L" title="Had sex while intoxicated/high within past 12 months?">
Had Sex While Intoxicated/High:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD111)" styleId="STD111_2" title="Had sex while intoxicated/high within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD112_2L" title="Had sex in exchange for drugs/money within past 12 months?">
Exchanged Drugs/Money for Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD112)" styleId="STD112_2" title="Had sex in exchange for drugs/money within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD113_2L" title="Female only.  Had sex with a known MSM within past 12 months?">
Females - Had Sex with Known MSM:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD113)" styleId="STD113_2" title="Female only.  Had sex with a known MSM within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD110_2L" title="Had sex with a known Injection Drug User within past 12 months?">
Had Sex with Known IDU:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD110)" styleId="STD110_2" title="Had sex with a known Injection Drug User within past 12 months?">
<nedss:optionsCollection property="codedValue(HAD_SEX_WITH_YOUNRD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_44_2" name="Risk Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD118_2L" title="Incarcerated within the past 12 months?">
Been Incarcerated:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD118)" styleId="STD118_2" title="Incarcerated within the past 12 months?">
<nedss:optionsCollection property="codedValue(YNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD114_2L" title="Used injection drugs within the past 12 months?">
Injection Drug Use:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD114)" styleId="STD114_2" title="Used injection drugs within the past 12 months?" onchange="ruleEnDisSTD1147900();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNRD)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS232_2L" title="Shared injection equipment within the past 12 months?">
Shared Injection Equipment:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS232)" styleId="NBS232_2" title="Shared injection equipment within the past 12 months?">
<nedss:optionsCollection property="codedValue(YNRD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_45_2" name="Drug Use Past 12 Months" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;During the past 12 months, indicate whether or not the patient used any of the following injection or non-injection drugs.</span></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS233_2L" title="No drug use within the past 12 months?">
No drug use reported:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS233)" styleId="NBS233_2" title="No drug use within the past 12 months?" onchange="ruleEnDisNBS2337898();ruleEnDisNBS2407899();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS237_2L" title="Cocaine use within the past 12 months?">
Cocaine:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS237)" styleId="NBS237_2" title="Cocaine use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS235_2L" title="Crack use within the past 12 months?">
Crack:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS235)" styleId="NBS235_2" title="Crack use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS239_2L" title="Heroine use within the past 12 months?">
Heroin:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS239)" styleId="NBS239_2" title="Heroine use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS234_2L" title="Methamphetamine (meth)use within the past 12 months?">
Methamphetamine:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS234)" styleId="NBS234_2" title="Methamphetamine (meth)use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS236_2L" title="Nitrate/popper use within the past 12 months?">
Nitrates/Poppers:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS236)" styleId="NBS236_2" title="Nitrate/popper use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS238_2L" title="Erectile dysfunction drug use within the past 12 months?">
Erectile Dysfunction Medications:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS238)" styleId="NBS238_2" title="Erectile dysfunction drug use within the past 12 months?">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS240_2L" title="Other drug use within the past 12 months?">
Other drug used:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS240)" styleId="NBS240_2" title="Other drug use within the past 12 months?" onchange="ruleEnDisNBS2407899();stdOtherDrugEntry();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_YNRD_CDC)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="STD300_2L" title="If patient indicated other drug use, specify the drug name(s).">
Specify Other Drug Used:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(STD300)" maxlength="50" title="If patient indicated other drug use, specify the drug name(s)." styleId="STD300"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_48_2" name="Places to Meet Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS242_2L" title="Places to Meet Partners">
Places to Meet Partners:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS242)" styleId="NBS242_2" title="Places to Meet Partners" onchange="ruleHideUnhNBS2427885();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_49_2" name="Places Selected to Meet Partners" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_49_2errorMessages">
<b> <a name="NBS_INV_STD_UI_49_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_49_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_49_2">
<tr id="patternNBS_INV_STD_UI_49_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_49_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_49_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_49_2">
<tr id="nopatternNBS_INV_STD_UI_49_2" class="odd" style="display:">
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

<!--processing Place Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS243_2L" title="Select the Hangout or Meetup.">
Place:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS243Uid">
<span id="clearNBS243" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS243Uid">
<span id="clearNBS243">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS243CodeClearButton" onclick="clearPlace('NBS243')"/>
</span>
<span id="NBS243_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS243_2_Icon" onclick="getHangoutPlace('NBS243');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS243)" styleId="NBS243_2Text"
size="10" maxlength="10"
title="Select the Hangout or Meetup."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS243CodeLookupButton" onclick="getDWRPlace('NBS243')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS243Uid">
style="visibility:hidden"
</logic:notEmpty>
/>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS243_2S">Place Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS243Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS243Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS243Disp" styleId="NBS243_2"/>
<span id="NBS243Disp_2">${PageForm.attributeMap2.NBS243SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS243_2Error"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_49">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_49BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_49_2','patternNBS_INV_STD_UI_49_2','questionbodyNBS_INV_STD_UI_49_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_49_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_49BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_49_2','patternNBS_INV_STD_UI_49_2','questionbodyNBS_INV_STD_UI_49_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_49_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_49BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_49_2','patternNBS_INV_STD_UI_49_2','questionbodyNBS_INV_STD_UI_49_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_49"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_49_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_50_2" name="Places to have Sex" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS244_2L" title="Places to Have Sex">
Places to Have Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS244)" styleId="NBS244_2" title="Places to Have Sex" onchange="ruleHideUnhNBS2447886();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_51_2" name="Places Selected to Have Sex" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_51_2errorMessages">
<b> <a name="NBS_INV_STD_UI_51_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_51_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_51_2">
<tr id="patternNBS_INV_STD_UI_51_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_51_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_51_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_51_2">
<tr id="nopatternNBS_INV_STD_UI_51_2" class="odd" style="display:">
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

<!--processing Place Type Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS290_2L" title="Select the Hangout or Sex.">
Place:</span> </td>
<td>
<logic:empty name="PageForm" property="attributeMap2.NBS290Uid">
<span id="clearNBS290" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS290Uid">
<span id="clearNBS290">
</logic:notEmpty>
<input type="button" class="Button" style="display: none;" value="Clear/Reassign" id="NBS290CodeClearButton" onclick="clearPlace('NBS290')"/>
</span>
<span id="NBS290_2_SearchControls"
class="none"
><input type="button" class="Button" style="display: none;" value="Search"
id="NBS290_2_Icon" onclick="getHangoutPlace('NBS290');" />&nbsp; - OR - &nbsp;
<html:text property="pageClientVO2.answer(NBS290)" styleId="NBS290_2Text"
size="10" maxlength="10"
title="Select the Hangout or Sex."/>
<input type="button" class="Button" style="display: none;" value="Quick Code Lookup"
id="NBS290CodeLookupButton" onclick="getDWRPlace('NBS290')"
<logic:notEmpty name="PageForm" property="attributeMap2.NBS290Uid">
style="visibility:hidden"
</logic:notEmpty>
/>
</span>
</td> </tr>
<tr>
<td class="fieldName" id="NBS290_2S">Place Selected: </td>
<logic:empty name="PageForm" property="attributeMap2.NBS290Uid">
<td> <span id="test2">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap2.NBS290Uid">
<td> <span class="test2">
</logic:notEmpty>
<html:hidden property="attributeMap2.NBS290Disp" styleId="NBS290_2"/>
<span id="NBS290Disp_2">${PageForm.attributeMap2.NBS290SearchResult}</span>
</span> </td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<span id="NBS290_2Error"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_51">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_51BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_51_2','patternNBS_INV_STD_UI_51_2','questionbodyNBS_INV_STD_UI_51_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_51_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_51BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_51_2','patternNBS_INV_STD_UI_51_2','questionbodyNBS_INV_STD_UI_51_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_51_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_51BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_51_2','patternNBS_INV_STD_UI_51_2','questionbodyNBS_INV_STD_UI_51_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_51"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_51_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_53_2" name="Partners Past Year" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS223_2L" title="Female partners claimed in the last 12 months?">
Female Partners (Past Year):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS223)" styleId="NBS223_2" title="Female partners claimed in the last 12 months?" onchange="ruleEnDisNBS2237874();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS224_2L" title="The total number of female partners claimed in the last 12 months.">
Number Female (Past Year):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS224)" size="3" maxlength="3"  title="The total number of female partners claimed in the last 12 months." styleId="NBS224_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS225_2L" title="Male partners claimed in the last 12 months?">
Male Partners (Past Year):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS225)" styleId="NBS225_2" title="Male partners claimed in the last 12 months?" onchange="ruleEnDisNBS2257875();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS226_2L" title="The total number of male partners claimed in the last 12 months.">
Number Male (Past Year):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS226)" size="3" maxlength="3"  title="The total number of male partners claimed in the last 12 months." styleId="NBS226_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS227_2L" title="Transgender partners claimed in the last 12 months?">
Transgender Partners (Past Year):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS227)" styleId="NBS227_2" title="Transgender partners claimed in the last 12 months?" onchange="ruleEnDisNBS2277876();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS228_2L" title="The total number of transgender partners claimed in the last 12 months.">
Number Transgender (Past Year):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS228)" size="3" maxlength="3"  title="The total number of transgender partners claimed in the last 12 months." styleId="NBS228_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Numeric Question-->
<tr style="display:none">
<td class="fieldName">
<span class="InputFieldLabel" id="STD120_2L" title="Total number of sex partners last 12 months?">
Total number of sex partners last 12 months?:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(STD120)" size="3" maxlength="3"  title="Total number of sex partners last 12 months?" styleId="STD120_2" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD888_2L" title="Patient refused to answer questions regarding number of sex partners">
Patient refused to answer questions regarding number of sex partners:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD888)" styleId="STD888_2" title="Patient refused to answer questions regarding number of sex partners">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD999_2L" title="Unknown number of sex partners in last 12 months">
Unknown number of sex partners in last 12 months:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD999)" styleId="STD999_2" title="Unknown number of sex partners in last 12 months">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_54_2" name="Partners in Interview Period" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS129_2L" title="Female sex/needle-sharing interview period partners claimed?">
Female Partners (Interview Period):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS129)" styleId="NBS129_2" title="Female sex/needle-sharing interview period partners claimed?" onchange="ruleEnDisNBS1297871();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS130_2L" title="The total number of female sex/needle-sharing interview period partners claimed.">
Number Female (Interview Period):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS130)" size="3" maxlength="3"  title="The total number of female sex/needle-sharing interview period partners claimed." styleId="NBS130_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS131_2L" title="Male sex/needle-sharing interview period partners claimed?">
Male Partners (Interview Period):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS131)" styleId="NBS131_2" title="Male sex/needle-sharing interview period partners claimed?" onchange="ruleEnDisNBS1317872();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS132_2L" title="The total number of male sex/needle-sharing interview period partners claimed.">
Number Male (Interview Period):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS132)" size="3" maxlength="3"  title="The total number of male sex/needle-sharing interview period partners claimed." styleId="NBS132_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS133_2L" title="Transgender sex/needle-sharing interview period partners claimed?">
Transgender Partners (Interview Period):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS133)" styleId="NBS133_2" title="Transgender sex/needle-sharing interview period partners claimed?" onchange="ruleEnDisNBS1337873();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS134_2L" title="The total number of transgender sex/needle-sharing interview period partners claimed.">
Number Transgender (Interview Period):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS134)" size="3" maxlength="3"  title="The total number of transgender sex/needle-sharing interview period partners claimed." styleId="NBS134_2" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_55_2" name="Partner Internet Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD119_2L" title="Have you met sex partners through the Internet in the last 12 months?">
Met Sex Partners through the Internet:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD119)" styleId="STD119_2" title="Have you met sex partners through the Internet in the last 12 months?">
<nedss:optionsCollection property="codedValue(YNRUD)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_57_2" name="Target Populations" isHidden="F" classType="subSect" >

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS271_2L" title="Target populations identified for the patient.">
Target Population(s):</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(NBS271)" styleId="NBS271_2" title="Target populations identified for the patient."
multiple="true" size="4"
onchange="displaySelectedOptions(this, 'NBS271_2-selectedValues')" >
<nedss:optionsCollection property="codedValue(TARGET_POPULATIONS)" value="key" label="value" /> </html:select>
<div id="NBS271_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_59_2" name="Syphilis Test Results" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS275_2L" title="Have  non-treponemal or treponemal syphilis tests been performed?">
Tests Performed?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS275)" styleId="NBS275_2" title="Have  non-treponemal or treponemal syphilis tests been performed?" onchange="ruleEnDisNBS2757910();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD122_2L" title="What type of non-treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">
Type of Nontreponemal Serologic Test for Syphilis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD122)" styleId="STD122_2" title="What type of non-treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">
<nedss:optionsCollection property="codedValue(PHVS_NONTREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD123_2L" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024.">
Nontreponemal Serologic Syphilis Test Result (Quantitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD123)" styleId="STD123_2" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024.">
<nedss:optionsCollection property="codedValue(PHVS_QUANTITATIVESYPHILISTESTRESULT_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD126_2L" title="Qualitative test result of STD123 Nontreponemal serologic syphilis test result (quantitative)">
Nontreponemal Serologic Syphilis Test Result (Qualitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD126)" styleId="STD126_2" title="Qualitative test result of STD123 Nontreponemal serologic syphilis test result (quantitative)">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTREACTIVITY_NND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD124_2L" title="What type of treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">
Type of Treponemal SerologicTest for Syphilis:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD124)" styleId="STD124_2" title="What type of treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">
<nedss:optionsCollection property="codedValue(PHVS_TREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="STD125_2L" title="If the test performed provides a qualitative result, provide qualitative result, e.g. weakly reactive.">
Treponemal Serologic Syphilis Test Result  (Qualitative):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD125)" styleId="STD125_2" title="If the test performed provides a qualitative result, provide qualitative result, e.g. weakly reactive.">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_71_2" name="STD Lab Test Results" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_71_2errorMessages">
<b> <a name="NBS_INV_STD_UI_71_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_71_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_71_2">
<tr id="patternNBS_INV_STD_UI_71_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_71_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_71_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_71_2">
<tr id="nopatternNBS_INV_STD_UI_71_2" class="odd" style="display:">
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
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="INV290_2L" title="Epidemiologic interpretation of the type of test(s) performed for this case.">
Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV290)" styleId="INV290_2" title="Epidemiologic interpretation of the type of test(s) performed for this case." onchange="unhideBatchImg('NBS_INV_STD_UI_71');ruleEnDisINV2907904();ruleEnDisINV2907903();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="INV291_2L" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative.">
Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV291)" styleId="INV291_2" title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." onchange="unhideBatchImg('NBS_INV_STD_UI_71');">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="STD123_1_2L" title="Coded quantitative test result (used for Nontreponemal serologic syphilis test result).">
Test Result Coded Quantitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD123_1)" styleId="STD123_1_2" title="Coded quantitative test result (used for Nontreponemal serologic syphilis test result)." onchange="unhideBatchImg('NBS_INV_STD_UI_71');">
<nedss:optionsCollection property="codedValue(PHVS_NONTREPONEMALTESTRESULT_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628_2L" title="Quantitative Test Result Value">
Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LAB628)"  maxlength="10" title="Quantitative Test Result Value" styleId="LAB628_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_71');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="LAB115_2L" title="Units of measure for the Quantitative Test Result Value">
Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB115)" styleId="LAB115_2" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('NBS_INV_STD_UI_71');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB167_2L" title="Date result sent from Reporting Laboratory">
Lab Result Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date result sent from Reporting Laboratory"  property="pageClientVO2.answer(LAB167)" maxlength="10" size="10" styleId="LAB167_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_71');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_71"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_71 InputFieldLabel" id="LAB165_2L" title="Anatomic site or specimen type from which positive lab specimen was collected.">
Specimen Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LAB165)" styleId="LAB165_2" title="Anatomic site or specimen type from which positive lab specimen was collected." onchange="unhideBatchImg('NBS_INV_STD_UI_71');enableOrDisableOther('LAB165');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENSOURCE_STD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165_2OthL">Other Specimen Source:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LAB165Oth)" size="40" maxlength="40" title="Other Anatomic site or specimen type from which positive lab specimen was collected." onkeyup="unhideBatchImg('NBS_INV_STD_UI_71')" styleId="LAB165_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="338822_2L" title="Date of collection of initial laboratory specimen used for diagnosis of health event reported in this case report. PREFERRED date for assignment of MMWR week.  First date in hierarchy of date types associated with case report/event.">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date of collection of initial laboratory specimen used for diagnosis of health event reported in this case report. PREFERRED date for assignment of MMWR week.  First date in hierarchy of date types associated with case report/event."  property="pageClientVO2.answer(338822)" maxlength="10" size="10" styleId="338822_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_71');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_71"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_71">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_71BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_71_2','patternNBS_INV_STD_UI_71_2','questionbodyNBS_INV_STD_UI_71_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_71_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_71BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_71_2','patternNBS_INV_STD_UI_71_2','questionbodyNBS_INV_STD_UI_71_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_71_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_71BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_71_2','patternNBS_INV_STD_UI_71_2','questionbodyNBS_INV_STD_UI_71_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_71"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_71_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_72_2" name="Antimicrobial Susceptibility Testing(AST)" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_72_2errorMessages">
<b> <a name="NBS_INV_STD_UI_72_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_72_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_72_2">
<tr id="patternNBS_INV_STD_UI_72_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_72_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_72_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_72_2">
<tr id="nopatternNBS_INV_STD_UI_72_2" class="odd" style="display:">
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
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST1_2L" title="Pathogen/Organism Identified in Isolate.">
Microorganism Identified in Isolate:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LABAST1)" styleId="LABAST1_2" title="Pathogen/Organism Identified in Isolate." onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_ORGANISMIDENTIFIEDAST_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LABAST2_2L" title="Isolate identifier unique for each isolate within laboratory.">
Isolate Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LABAST2)"  maxlength="30" title="Isolate identifier unique for each isolate within laboratory." styleId="LABAST2_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST3_2L" title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)">
Specimen Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LABAST3)" styleId="LABAST3_2" title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');enableOrDisableOther('LABAST3');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENTYPEAST_STD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3_2OthL">Other Specimen Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LABAST3Oth)" size="40" maxlength="40" title="Other Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72')" styleId="LABAST3_2Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST4_2L" title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)">
Specimen Collection Site:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LABAST4)" styleId="LABAST4_2" title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');enableOrDisableOther('LABAST4');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SPECIMENCOLLECTIONSITEAST_STD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" id="LABAST4_2OthL">Other Specimen Collection Site:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO2.answer(LABAST4Oth)" size="40" maxlength="40" title="Other Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72')" styleId="LABAST4_2Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LABAST5_2L" title="Antimicrobial Susceptibility Specimen Collection Date">
Specimen Collection Date:</span>
</td>
<td>
<html:text name="PageForm" title="Antimicrobial Susceptibility Specimen Collection Date"  property="pageClientVO2.answer(LABAST5)" maxlength="10" size="10" styleId="LABAST5_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_72"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST6_2L" title="Antimicrobial Susceptibility Test Type would includes drugs, enzymes, PCR and other genetic tests to detect the resistance against specific drugs.">
AST Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LABAST6)" styleId="LABAST6_2" title="Antimicrobial Susceptibility Test Type would includes drugs, enzymes, PCR and other genetic tests to detect the resistance against specific drugs." onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTTYPE_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST7_2L" title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)">
AST Method:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LABAST7)" styleId="LABAST7_2" title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTMETHOD_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST8_2L" title="Antimicrobial Susceptibility Test Interpretation (e.g. Susceptible, Resistant, Intermediate, Not tested)">
AST Interpretation:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LABAST8)" styleId="LABAST8_2" title="Antimicrobial Susceptibility Test Interpretation (e.g. Susceptible, Resistant, Intermediate, Not tested)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTINTERPRETATION_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST11_2L" title="Antimicrobial Susceptibility Test Result - Coded Quantitative. List of coded values (i.e. valid dilutions) to represent the antimicrobial susceptibility test result.">
AST Result Coded Quantitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LABAST11)" styleId="LABAST11_2" title="Antimicrobial Susceptibility Test Result - Coded Quantitative. List of coded values (i.e. valid dilutions) to represent the antimicrobial susceptibility test result." onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTRESULTQUANTITATIVE_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LABAST9_2L" title="Antimicrobial Susceptibility Test Result Quantitative Value (e.g. Quantitative MIC values, Disk Diffusion size in mm)">
AST Result Quantitative Value:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(LABAST9)"  maxlength="10" title="Antimicrobial Susceptibility Test Result Quantitative Value (e.g. Quantitative MIC values, Disk Diffusion size in mm)" styleId="LABAST9_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_72');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_INV_STD_UI_72 InputFieldLabel" id="LABAST10_2L" title="Antimicrobial Susceptibility Test Result Numerical Value Units (e.g. microgram/ml, mm)">
Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(LABAST10)" styleId="LABAST10_2" title="Antimicrobial Susceptibility Test Result Numerical Value Units (e.g. microgram/ml, mm)" onchange="unhideBatchImg('NBS_INV_STD_UI_72');">
<nedss:optionsCollection property="codedValue(PHVS_SUSCEPTIBILITYTESTRESULTUNITS_STD)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_72">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_72BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_72_2','patternNBS_INV_STD_UI_72_2','questionbodyNBS_INV_STD_UI_72_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_72_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_72BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_72_2','patternNBS_INV_STD_UI_72_2','questionbodyNBS_INV_STD_UI_72_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_72_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_72BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_72_2','patternNBS_INV_STD_UI_72_2','questionbodyNBS_INV_STD_UI_72_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_72"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_72_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_61_2" name="Signs and Symptoms" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_61_2errorMessages">
<b> <a name="NBS_INV_STD_UI_61_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_61_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_61_2">
<tr id="patternNBS_INV_STD_UI_61_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_61_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_61_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_61_2">
<tr id="nopatternNBS_INV_STD_UI_61_2" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_INV_STD_UI_61_2 InputFieldLabel" id="NBS246_2L" title="Is the sign/symptom experienced by the patient or observed by a clinician?">
Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS246)" styleId="NBS246_2" title="Is the sign/symptom experienced by the patient or observed by a clinician?" onchange="unhideBatchImg('NBS_INV_STD_UI_61');">
<nedss:optionsCollection property="codedValue(SIGN_SX_OBSRV_SOURCE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS247_2L" title="The earliest date the symptom was first experienced by the patient and/or the date the sign was first observed by a clinician.">
Observation/Onset Date:</span>
</td>
<td>
<html:text name="PageForm" title="The earliest date the symptom was first experienced by the patient and/or the date the sign was first observed by a clinician."  property="pageClientVO2.answer(NBS247)" maxlength="10" size="10" styleId="NBS247_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_61');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_61"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_INV_STD_UI_61_2 InputFieldLabel" id="INV272_2L" title="Sign/symptom observed on exam or described.">
Sign/Symptom:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(INV272)" styleId="INV272_2" title="Sign/symptom observed on exam or described." onchange="unhideBatchImg('NBS_INV_STD_UI_61');">
<nedss:optionsCollection property="codedValue(SIGN_SX_STD)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Multi-Select Coded Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_INV_STD_UI_61_2 InputFieldLabel" id="STD121_2L" title="The anatomic site of the sign/symptom.">
Anatomic Site:</span>
</td>
<td>
<div class="multiSelectBlock">
<i> (Use Ctrl to select more than one) </i> <br/>
<html:select property="pageClientVO2.answerArray(STD121)" styleId="STD121_2" title="The anatomic site of the sign/symptom."
multiple="true" size="4"
onchange="unhideBatchImg('NBS_INV_STD_UI_61');displaySelectedOptions(this, 'STD121_2-selectedValues');ruleEnDisSTD1217878()" >
<nedss:optionsCollection property="codedValue(PHVS_CLINICIANOBSERVEDLESIONS_STD)" value="key" label="value" /> </html:select>
<div id="STD121_2-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS248_2L" title="Provide a description of the other anatomic site.">
Other Anatomic Site, Specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO2.answer(NBS248)"  maxlength="50" title="Provide a description of the other anatomic site." styleId="NBS248_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_61');"/>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS249_2L" title="The number of days signs/symptoms were present. Document “99” if unknown.">
Duration (Days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO2.answer(NBS249)" size="4" maxlength="4"  title="The number of days signs/symptoms were present. Document “99” if unknown." styleId="NBS249_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_61');isNumericCharacterEntered(this)"/>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_61">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_61BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_61_2','patternNBS_INV_STD_UI_61_2','questionbodyNBS_INV_STD_UI_61_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_61_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_61BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_61_2','patternNBS_INV_STD_UI_61_2','questionbodyNBS_INV_STD_UI_61_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_61_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_61BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_61_2','patternNBS_INV_STD_UI_61_2','questionbodyNBS_INV_STD_UI_61_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_61"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_61_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_63_2" name="Previous STD History" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD117_2L" title="Does the patient have a history of ever having had an STD prior to the condition reported in this case report?">
Previous STD history (self-reported)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD117)" styleId="STD117_2" title="Does the patient have a history of ever having had an STD prior to the condition reported in this case report?" onchange="ruleHideUnhSTD1177887();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_64_2" name="STD History" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td  width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_STD_UI_64_2errorMessages">
<b> <a name="NBS_INV_STD_UI_64_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_64_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_64_2">
<tr id="patternNBS_INV_STD_UI_64_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_64_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_64_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View"></td>
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
<tbody id="questionbodyNBS_INV_STD_UI_64_2">
<tr id="nopatternNBS_INV_STD_UI_64_2" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_INV_STD_UI_64_2 InputFieldLabel" id="NBS250_2L" title="With what condition was the patient previously diagnosed?">
Previous Condition:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS250)" styleId="NBS250_2" title="With what condition was the patient previously diagnosed?" onchange="unhideBatchImg('NBS_INV_STD_UI_64');">
<nedss:optionsCollection property="codedValue(STD_HISTORY_DIAGNOSIS)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS251_2L" title="Diagnosis Date of previous STD.">
Diagnosis Date:</span>
</td>
<td>
<html:text name="PageForm" title="Diagnosis Date of previous STD."  property="pageClientVO2.answer(NBS251)" maxlength="10" size="10" styleId="NBS251_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_64');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_64"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS252_2L" title="Treatment Date of previous STD.">
Treatment Date:</span>
</td>
<td>
<html:text name="PageForm" title="Treatment Date of previous STD."  property="pageClientVO2.answer(NBS252)" maxlength="10" size="10" styleId="NBS252_2" onkeyup="unhideBatchImg('NBS_INV_STD_UI_64');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_64"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_INV_STD_UI_64_2 InputFieldLabel" id="NBS253_2L" title="Confirmed the Previous STD?">
Confirmed:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS253)" styleId="NBS253_2" title="Confirmed the Previous STD?" onchange="unhideBatchImg('NBS_INV_STD_UI_64');">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /> </html:select>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_64">
<td colspan="2" align="right">
<input type="button" value="     Add     "  style="display: none;"  disabled="disabled" onclick="if (pgNBS_INV_STD_UI_64BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_64_2','patternNBS_INV_STD_UI_64_2','questionbodyNBS_INV_STD_UI_64_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_STD_UI_64_2">
<td colspan="2" align="right">
<input type="button" value="     Add     " style="display: none;" onclick="if (pgNBS_INV_STD_UI_64BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_64_2','patternNBS_INV_STD_UI_64_2','questionbodyNBS_INV_STD_UI_64_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_64_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "  style="display: none;"  onclick="if (pgNBS_INV_STD_UI_64BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_64_2','patternNBS_INV_STD_UI_64_2','questionbodyNBS_INV_STD_UI_64_2')"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_STD_UI_64"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  " style="display: none;"  onclick="clearClicked('NBS_INV_STD_UI_64_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_66_2" name="Consented to Enrollment in Partner Services" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS257_2L" title="Document whether the patient accepted or declined enrollment into Partner Services (i.e. did s/he accept the interview).">
Enrolled in Partner Services:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS257)" styleId="NBS257_2" title="Document whether the patient accepted or declined enrollment into Partner Services (i.e. did s/he accept the interview).">
<nedss:optionsCollection property="codedValue(ENROLL_HIV_PARTNER_SERVICES_IND)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_67_2" name="Self-Reported Results" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS254_2L" title="Previously tested for 900?">
Previous 900 Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS254)" styleId="NBS254_2" title="Previously tested for 900?" onchange="ruleEnDisNBS2547889();ruleEnDisSTD1067891();ruleRequireIfSTD1067892();ruleRequireIfNBS2547890();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNRUD)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="STD106_2L" title="Enter the self-reported or documented HIV test result at time of notification.  This should be the most recent test.">
Self-reported or Documented Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(STD106)" styleId="STD106_2" title="Enter the self-reported or documented HIV test result at time of notification.  This should be the most recent test." onchange="ruleEnDisSTD1067891();ruleRequireIfSTD1067892();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(STD_SELF_REPORTED_900_TEST_RESULT)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS259_2L" title="Date of last 900 Test">
Date Last 900 Test:</span>
</td>
<td>
<html:text name="PageForm" title="Date of last 900 Test"  property="pageClientVO2.answer(NBS259)" maxlength="10" size="10" styleId="NBS259_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_68_2" name="Referred to Testing" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS260_2L" title="Referred for 900 test?">
Refer for Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS260)" styleId="NBS260_2" title="Referred for 900 test?" onchange="ruleEnDisNBS2607893();ruleEnDisNBS2627895();ruleRequireIfNBS2627896();;ruleEnDisNBS4477911();ruleRequireIfNBS2607894();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS261_2L" title="Date referred for 900 test.">
Referral Date:</span>
</td>
<td>
<html:text name="PageForm" title="Date referred for 900 test."  property="pageClientVO2.answer(NBS261)" maxlength="10" size="10" styleId="NBS261_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS262_2L" title="900 test performed at this event?">
900 Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS262)" styleId="NBS262_2" title="900 test performed at this event?" onchange="ruleEnDisNBS2627895();;ruleEnDisNBS4477911();ruleRequireIfNBS2627896();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(900_TST_AT_EVENT)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS450_2L" title="Indicate the date that the specimen for the HIV test was collected.">
900 Test Sample Date:</span>
</td>
<td>
<html:text name="PageForm" title="Indicate the date that the specimen for the HIV test was collected."  property="pageClientVO2.answer(NBS450)" maxlength="10" size="10" styleId="NBS450_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS263_2L" title="Result of 900 test at this event.">
900 Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS263)" styleId="NBS263_2" title="Result of 900 test at this event.">
<nedss:optionsCollection property="codedValue(900_TEST_RESULTS)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS265_2L" title="The partner was informed of their 900 test result?">
Result provided:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS265)" styleId="NBS265_2" title="The partner was informed of their 900 test result?">
<nedss:optionsCollection property="codedValue(900_RESULT_PROVIDED)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS264_2L" title="Post-test Counselling">
Post-test Counselling:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS264)" styleId="NBS264_2" title="Post-test Counselling">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS447_2L" title="Indicate if a client received a syphilis test in conjunction with an HIV test during partner services activities.">
Patient Tested for Syphilis In Conjunction with HIV Test:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS447)" styleId="NBS447_2" title="Indicate if a client received a syphilis test in conjunction with an HIV test during partner services activities." onchange="ruleEnDisNBS4477911();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS448_2L" title="Indicate the outcome of the current syphilis test in conjunction with an HIV test while enrolled in partner services.">
Syphilis Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS448)" styleId="NBS448_2" title="Indicate the outcome of the current syphilis test in conjunction with an HIV test while enrolled in partner services.">
<nedss:optionsCollection property="codedValue(SYPHILIS_TEST_RESULT_PS)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_69_2" name="Referred to Medical Testing (900 +)" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS266_2L" title="Referred to 900 medical care/evaluation/treatment.">
Refer for Care:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS266)" styleId="NBS266_2" title="Referred to 900 medical care/evaluation/treatment." onchange="ruleEnDisNBS2667897();ruleEnDisNBS2677912();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS267_2L" title="If referred, did patient keep 1st appointment?">
Keep Appointment:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS267)" styleId="NBS267_2" title="If referred, did patient keep 1st appointment?" onchange="ruleEnDisNBS2677912();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(KEEP_FIRST_APPT)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS302_2L" title="Enter the date the client attended his/her HIV medical care appointment after HIV diagnosis, current HIV test, or report to Partner Services.">
Appointment Date (If Confirmed):</span>
</td>
<td>
<html:text name="PageForm" title="Enter the date the client attended his/her HIV medical care appointment after HIV diagnosis, current HIV test, or report to Partner Services."  property="pageClientVO2.answer(NBS302)" maxlength="10" size="10" styleId="NBS302_2" onkeyup="DateMask(this,null,event)"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_75_2" name="Pre Exposure Prophylaxis (PrEP)" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS443_2L" title="Indicate if the client is currently on pre-exposure prophylaxis (PrEP) medication.">
Is the Client Currently On PrEP?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS443)" styleId="NBS443_2" title="Indicate if the client is currently on pre-exposure prophylaxis (PrEP) medication.">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS446_2L" title="Indicate if the client was referred to a provider for pre-exposure prophylaxis (PrEP).">
Has Client Been Referred to PrEP Provider?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS446)" styleId="NBS446_2" title="Indicate if the client was referred to a provider for pre-exposure prophylaxis (PrEP).">
<nedss:optionsCollection property="codedValue(PREP_REFERRAL_PS)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_70_2" name="Anti-Retroviral Therapy for HIV Infection" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS255_2L" title="Anti-virals taken within the last 12 months?">
Anti-viral Therapy - Last 12 Months:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS255)" styleId="NBS255_2" title="Anti-virals taken within the last 12 months?">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS256_2L" title="Anti-virals ever taken (including past year)?">
Anti-viral Therapy - Ever:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO2.answer(NBS256)" styleId="NBS256_2" title="Anti-virals ever taken (including past year)?">
<nedss:optionsCollection property="codedValue(YNUR)" value="key" label="value" /></html:select>
</td></tr>
</logic:equal>
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
