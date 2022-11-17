<?xml version="1.0" encoding="UTF-8"?>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
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

<!-- ################### A PAGE TAB ###################### - - -->
<%
Map map = new HashMap();
if(request.getAttribute("SubSecStructureMap") != null){
map =(Map)request.getAttribute("SubSecStructureMap");
}
%>
<%
String tabId = "editCongenitalSyphilis";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Congenital Syphilis Information","Maternal Information","Infant Information"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<table style="width:100%;" class="sectionsToggler" role="presentation">
<tr><td><ul class="horizontalList">
<li style="margin-right:5px;"><b>Go to: </b></li>
<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
<li class="delimiter"> | </li>
<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
<li class="delimiter"> | </li>
<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
</ul> </td> </tr>
<tr>
<td style="padding-top:1em;">
<a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')"/>Collapse Sections</a>
</td>
</tr>
</table>
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
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
<html:text name="PageForm"  property="pageClientVO.answer(NBS388)" maxlength="10" size="10" styleId="NBS388" onkeyup="DateMaskFuture(this,null,event)" title="Enter the expected delivery date of the infant"/>
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
<td class="fieldName" valign="top" id="NBS383S">Mother OB/GYN Selected: </td>
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
<td class="fieldName" valign="top" id="NBS384S">Delivering MD Selected: </td>
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
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="NBS386">
<span id="NBS386L" title="The name of the hospital where the infant was born.">
Delivering Hospital:</span>
</logic:notEqual>
</td>
<td>
<logic:empty name="PageForm" property="attributeMap.NBS386Uid">
<span id="clearNBS386" class="none">
</logic:empty>
<logic:notEmpty name="PageForm" property="attributeMap.NBS386Uid">
<span id="clearNBS386">
</logic:notEmpty>
<logic:notEqual name="PageForm" property="attributeMap.readOnlyParticipant" value="NBS386">
<input type="button" class="Button" value="Clear/Reassign" id="NBS386CodeClearButton" onclick="clearOrganization('NBS386')"/>
</logic:notEqual>
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
<td class="fieldName" valign="top" id="NBS386S">Delivering Hospital Selected: </td>
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
<td class="fieldName" valign="top" id="NBS385S">Pediatrician Selected: </td>
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
<html:text name="PageForm" property="pageClientVO.answer(NBS336)" size="25" maxlength="25" title="The Medical Record Number as reported by health care provider or facility" styleId="NBS336"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_79" name="Mother Administrative Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_INV_STD_UI_87L">&nbsp;&nbsp;Please complete this section for the mother's information. Though this information is available on the contact record, information entered into this section will be sent in the case notification to CDC.</span> </td></tr>

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
<html:text name="PageForm" property="pageClientVO.answer(MTH169)" size="40" maxlength="40" title="Enter the zip code of the case patient's mother" styleId="MTH169" onkeyup="ZipMask(this,event)"/>
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
<html:text name="PageForm"  property="pageClientVO.answer(MTH153)" maxlength="10" size="10" styleId="MTH153" onkeyup="DateMask(this,null,event)" title="Enter the date of birth of the case patient's mother"/>
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
<html:text name="PageForm"  property="pageClientVO.answer(752030)" maxlength="10" size="10" styleId="752030" onkeyup="DateMask(this,null,event)" title="Mother Last menstrual period (LMP) start date before delivery."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('752030','752030Icon'); return false;" styleId="752030Icon" onkeypress="showCalendarEnterKey('752030','752030Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="75204_8L" title="Indicate if there was a prenatal visit">
Was There a Prenatal Visit:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(75204_8)" styleId="75204_8" title="Indicate if there was a prenatal visit" onchange="ruleEnDis75204_87283();pgSelectNextFocus(this);">
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
<html:text name="PageForm"  property="pageClientVO.answer(75200_6)" maxlength="10" size="10" styleId="75200_6" onkeyup="DateMask(this,null,event)" title="Indicate the date of the first prenatal visit"/>
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
<% for(int i=0;i<batchrec.length;i++){%>
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
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_81" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_81');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_81" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_81');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_81" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_81','patternNBS_INV_STD_UI_81','questionbodyNBS_INV_STD_UI_81');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
<% for(int i=0;i<batchrec.length;i++){%>
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
<html:select name="PageForm" property="pageClientVO.answer(INV290_MTH)" styleId="INV290_MTH" title="Enter the type of lab test performed" onchange="unhideBatchImg('NBS_INV_STD_UI_81');ruleEnDisINV290_MTH7287();pgSelectNextFocus(this);">
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
<html:text name="PageForm"  property="pageClientVO.answer(LAB167_MTH)" maxlength="10" size="10" styleId="LAB167_MTH" onkeyup="unhideBatchImg('NBS_INV_STD_UI_81');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_81" title="Enter the lab result date for the lab test being reported."/>
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
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_81BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_81','patternNBS_INV_STD_UI_81','questionbodyNBS_INV_STD_UI_81');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_81"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_81BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_81','patternNBS_INV_STD_UI_81','questionbodyNBS_INV_STD_UI_81');"/>&nbsp;		&nbsp;
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
<html:text name="PageForm"  property="pageClientVO.answer(75182_6)" maxlength="10" size="10" styleId="75182_6" onkeyup="DateMask(this,null,event)" title="Indicate the date the patient received the first dose of benzathine penicillin"/>
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
<html:select name="PageForm" property="pageClientVO.answer(75197_4)" styleId="75197_4" title="Was the infant/child treated for congenital syphilis?" onchange="ruleEnDis75197_47284();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SYPHILISTREATMENTINFANT_CS)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS389L" title="If the treatment type is 'Other', specify the treatment.">
Other Treatment Specify:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS389)" size="100" maxlength="100" title="If the treatment type is 'Other', specify the treatment." styleId="NBS389"/>
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
<% for(int i=0;i<batchrec.length;i++){%>
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
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_STD_UI_86" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_86');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_STD_UI_86" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_STD_UI_86');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_STD_UI_86" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_STD_UI_86','patternNBS_INV_STD_UI_86','questionbodyNBS_INV_STD_UI_86');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
<% for(int i=0;i<batchrec.length;i++){%>
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
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Epidemiologic interpretation of the type of test(s) performed for this case." onchange="unhideBatchImg('NBS_INV_STD_UI_86');ruleEnDisINV2907286();pgSelectNextFocus(this);">
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
<html:text name="PageForm"  property="pageClientVO.answer(LAB167)" maxlength="10" size="10" styleId="LAB167" onkeyup="unhideBatchImg('NBS_INV_STD_UI_86');DateMask(this,null,event)" styleClass="NBS_INV_STD_UI_86" title="Date result sent from Reporting Laboratory"/>
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
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_STD_UI_86BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_86','patternNBS_INV_STD_UI_86','questionbodyNBS_INV_STD_UI_86');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_STD_UI_86"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_STD_UI_86BatchAddFunction()) writeQuestion('NBS_INV_STD_UI_86','patternNBS_INV_STD_UI_86','questionbodyNBS_INV_STD_UI_86');"/>&nbsp;		&nbsp;
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
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
