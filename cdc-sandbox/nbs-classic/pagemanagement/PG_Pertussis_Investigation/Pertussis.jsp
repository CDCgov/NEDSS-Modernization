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
String tabId = "editPertussis";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Pregnancy and Birth","Symptoms","Complications","Treatment","Laboratory","Vaccination Information","Epidemiology"};
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
<li class="delimiter"> | </li>
<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
<li class="delimiter"> | </li>
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
<nedss:container id="NBS_UI_56" name="Pregnancy and Birth Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS364L" title="Was the patient &lt; 12 months old at illness onset?">
Was the patient &lt; 12 months old at illness onset?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS364)" styleId="NBS364" title="Was the patient &lt; 12 months old at illness onset?" onchange="ruleEnDisNBS3648616();;ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="MTH111L" title="Mothers age at infants birth (used only if patient &lt; 1 year of age)">
Mothers Age at Infants Birth (in Years):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(MTH111)" size="10" maxlength="10"  title="Mothers age at infants birth (used only if patient &lt; 1 year of age)" styleId="MTH111" onkeyup="isStructuredNumericCharEntered(this)" styleClass="structuredNumericField"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS347L" title="Birth Weight in Grams">
Birth Weight (in grams):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS347)" size="20" maxlength="20"  title="Birth Weight in Grams" styleId="NBS347" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr style="display:none">
<td class="fieldName">
<span class="InputFieldLabel" id="NBS345L" title="Birth Weight in Pounds">
Pounds:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS345)" size="20" maxlength="20"  title="Birth Weight in Pounds" styleId="NBS345" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr style="display:none">
<td class="fieldName">
<span class="InputFieldLabel" id="NBS346L" title="Birth Weight in Ounces">
Ounces:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS346)" size="20" maxlength="20"  title="Birth Weight in Ounces" styleId="NBS346" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="DEM228L" title="Indicate the patient's gestational age (in weeks) if case-patient was &lt; 1 year of age at illness onset.">
Patient's Gestational Age (in weeks):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(DEM228)" size="3" maxlength="3"  title="Indicate the patient's gestational age (in weeks) if case-patient was &lt; 1 year of age at illness onset." styleId="DEM228" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,50)"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH172L" title="Did mother receive Tdap (if case-patient &lt; 1 year of age at illness onset)?">
Did the mother receive Tdap?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH172)" styleId="MTH172" title="Did mother receive Tdap (if case-patient &lt; 1 year of age at illness onset)?" onchange="ruleEnDisMTH1728617();null;enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');enableOrDisableOther('MTH173');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH173L" title="If mother received Tdap, when was it administered in relation to the pregnancy?">
If mother received Tdap, when was it administered in relation to the pregnancy?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH173)" styleId="MTH173" title="If mother received Tdap, when was it administered in relation to the pregnancy?" onchange="enableOrDisableOther('MTH173');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TIMINGOFMATERNALTREATMENT_NND)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="If mother received Tdap, when was it administered in relation to the pregnancy?" id="MTH173OthL">Other If mother received Tdap, when was it administered in relation to the pregnancy?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(MTH173Oth)" size="40" maxlength="40" title="Other If mother received Tdap, when was it administered in relation to the pregnancy?" styleId="MTH173Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="MTH174L" title="If mother received Tdap, what date was it administered?*(if available)">
If mother received Tdap, what date was it administered? (if available):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(MTH174)" maxlength="10" size="10" styleId="MTH174" onkeyup="DateMask(this,null,event)" title="If mother received Tdap, what date was it administered?*(if available)"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('MTH174','MTH174Icon'); return false;" styleId="MTH174Icon" onkeypress="showCalendarEnterKey('MTH174','MTH174Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_32" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV576L" title="Indicate whether the patient is/was symptomatic for pertussis?">
Did the patient experience any symptoms related to pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV576)" styleId="INV576" title="Indicate whether the patient is/was symptomatic for pertussis?" onchange="ruleEnDisINV5768614();ruleHideUnh497270028615();ruleHideUnhNBS3388619();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="49727002L" title="Did the patient's illness include the symptom of cough?">
Did the patient have any cough?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(49727002)" styleId="49727002" title="Did the patient's illness include the symptom of cough?" onchange="ruleHideUnh497270028615();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV550L" title="Cough onset date">
Cough Onset Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV550)" maxlength="10" size="10" styleId="INV550" onkeyup="DateMask(this,null,event)" title="Cough onset date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV550','INV550Icon'); return false;" styleId="INV550Icon" onkeypress="showCalendarEnterKey('INV550','INV550Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV902L" title="Patients age at cough onset">
Age at Cough Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV902)" size="3" maxlength="3"  title="Patients age at cough onset" styleId="INV902" onkeyup="isNumericCharacterEntered(this)" styleClass="relatedUnitsField"/>
<html:select name="PageForm" property="pageClientVO.answer(INV902Unit)" styleId="INV902UNIT">
<nedss:optionsCollection property="codedValue(PHVS_AGEUNIT_NND)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV557L" title="What was the duration (in days) of the patients cough?">
Total Cough Duration (in days):</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV557)" size="3" maxlength="3"  title="What was the duration (in days) of the patients cough?" styleId="INV557" onkeyup="isNumericCharacterEntered(this)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV555L" title="Date of the patients final interview">
Date of Final Interview:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV555)" maxlength="10" size="10" styleId="INV555" onkeyup="DateMask(this,null,event)" title="Date of the patients final interview"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV555','INV555Icon'); return false;" styleId="INV555Icon" onkeypress="showCalendarEnterKey('INV555','INV555Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS349L" title="Was there a cough at the patients final interview?">
Cough at Final Interview:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS349)" styleId="NBS349" title="Was there a cough at the patients final interview?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="43025008L" title="Did the patients illness include the symptom of paroxysmal cough?">
Did the patient have paroxysmal cough?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(43025008)" styleId="43025008" title="Did the patients illness include the symptom of paroxysmal cough?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="60537006L" title="Did the patients illness include the symptom of whoop?">
Did the patient have whoop?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(60537006)" styleId="60537006" title="Did the patients illness include the symptom of whoop?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="424580008L" title="Did the patients illness include the symptom of post-tussive vomiting?">
Did the patient have post-tussive vomiting?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(424580008)" styleId="424580008" title="Did the patients illness include the symptom of post-tussive vomiting?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="1023001L" title="Did the patients illness include the symptom of apnea?">
Did the patient have apnea?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(1023001)" styleId="1023001" title="Did the patients illness include the symptom of apnea?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="3415004L" title="Did the patient's illness include the symptom of cyanosis?">
Did the patient have cyanosis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(3415004)" styleId="3415004" title="Did the patient's illness include the symptom of cyanosis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS338L" title="Indication of whether the patient had other symptom(s) not otherwise specified.">
Other symptom(s)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS338)" styleId="NBS338" title="Indication of whether the patient had other symptom(s) not otherwise specified." onchange="ruleHideUnhNBS3388619();pgSelectNextFocus(this);">
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

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS337L" title="Notes pertaining to the symptoms indicated for this case.">
Symptom Notes:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(NBS337)" styleId ="NBS337" onkeyup="checkTextAreaLength(this, 2000)" title="Notes pertaining to the symptoms indicated for this case."/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_64" name="Signs and Symptoms Repeating Block" isHidden="T" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_64errorMessages">
<b> <a name="NBS_UI_64errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_64"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_64">
<tr id="patternNBS_UI_64" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_64" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_64');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_64" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_64');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_64" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_64','patternNBS_UI_64','questionbodyNBS_UI_64');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_64">
<tr id="nopatternNBS_UI_64" class="odd" style="display:">
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
<span class="NBS_UI_64 InputFieldLabel" id="INV272L" title="Select all the signs and symptoms that are associated with the patient">
Signs and Symptoms Associated with Patient:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV272)" styleId="INV272" title="Select all the signs and symptoms that are associated with the patient" onchange="unhideBatchImg('NBS_UI_64');enableOrDisableOther('INV272');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SIGNSSYMPTOMS_PERTUSSIS_NND)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Select all the signs and symptoms that are associated with the patient" id="INV272OthL">Other Signs and Symptoms Associated with Patient:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV272Oth)" size="40" maxlength="40" title="Other Select all the signs and symptoms that are associated with the patient" onkeyup="unhideBatchImg('NBS_UI_64')" styleId="INV272Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_64 InputFieldLabel" id="INV919L" title="Indicator for associated signs and symptoms">
Signs and Symptoms Indicator:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV919)" styleId="INV919" title="Indicator for associated signs and symptoms" onchange="unhideBatchImg('NBS_UI_64');">
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
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_64BatchAddFunction()) writeQuestion('NBS_UI_64','patternNBS_UI_64','questionbodyNBS_UI_64')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_64">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_64BatchAddFunction()) writeQuestion('NBS_UI_64','patternNBS_UI_64','questionbodyNBS_UI_64');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_64"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_64BatchAddFunction()) writeQuestion('NBS_UI_64','patternNBS_UI_64','questionbodyNBS_UI_64');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_64"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_64')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_33" name="Complications" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS340L" title="Indicate whether the patient experienced any complications related to pertussis.">
Did the patient experience any complications related to pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS340)" styleId="NBS340" title="Indicate whether the patient experienced any complications related to pertussis." onchange="ruleEnDisNBS3408613();ruleHideUnhNBS3438618();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV923L" title="Result of chest x-ray for pneumonia">
Result of Chest X-Ray for Pneumonia:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV923)" styleId="INV923" title="Result of chest x-ray for pneumonia">
<nedss:optionsCollection property="codedValue(PHVS_PNUND)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="91175000L" title="Did the patient have generalized or focal seizures due to pertussis?">
Did the patient have generalized or focal seizures due to pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(91175000)" styleId="91175000" title="Did the patient have generalized or focal seizures due to pertussis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="81308009L" title="Did the patient have acute encephalopathy due to pertussis?">
Did the patient have acute encephalopathy due to pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(81308009)" styleId="81308009" title="Did the patient have acute encephalopathy due to pertussis?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS343L" title="Other Complications">
Other Complication(s):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS343)" styleId="NBS343" title="Other Complications" onchange="ruleHideUnhNBS3438618();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS343_OTHL" title="Specify Other Complication">
Specify Other Complication:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS343_OTH)" size="100" maxlength="100" title="Specify Other Complication" styleId="NBS343_OTH"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_65" name="Complications Repeating Block" isHidden="T" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_65errorMessages">
<b> <a name="NBS_UI_65errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_65"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_65">
<tr id="patternNBS_UI_65" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_65" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_65');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_65" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_65');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_65" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_65','patternNBS_UI_65','questionbodyNBS_UI_65');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_65">
<tr id="nopatternNBS_UI_65" class="odd" style="display:">
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
<span class="NBS_UI_65 InputFieldLabel" id="67187_5L" title="Complications associated with the illness being reported">
Type of Complications:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(67187_5)" styleId="67187_5" title="Complications associated with the illness being reported" onchange="unhideBatchImg('NBS_UI_65');enableOrDisableOther('67187_5');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_COMPLICATIONS_PERTUSSIS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Complications associated with the illness being reported" id="67187_5OthL">Other Type of Complications:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(67187_5Oth)" size="40" maxlength="40" title="Other Complications associated with the illness being reported" onkeyup="unhideBatchImg('NBS_UI_65')" styleId="67187_5Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_65 InputFieldLabel" id="INV920L" title="Indicator for associated complication">
Type of Complications Indicator:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV920)" styleId="INV920" title="Indicator for associated complication" onchange="unhideBatchImg('NBS_UI_65');">
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
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_65BatchAddFunction()) writeQuestion('NBS_UI_65','patternNBS_UI_65','questionbodyNBS_UI_65')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_65">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_65BatchAddFunction()) writeQuestion('NBS_UI_65','patternNBS_UI_65','questionbodyNBS_UI_65');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_65"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_65BatchAddFunction()) writeQuestion('NBS_UI_65','patternNBS_UI_65','questionbodyNBS_UI_65');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_65"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_65')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_53" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV559L" title="Were antibiotics given to the patient?">
Were antibiotics given to the patient?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV559)" styleId="INV559" title="Were antibiotics given to the patient?" onchange="ruleHideUnhINV5598607();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_54" name="Please list all of the antibiotics the patient received in the order the antibiotics were taken." isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_54errorMessages">
<b> <a name="NBS_UI_54errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_54"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_54">
<tr id="patternNBS_UI_54" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_54" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_54');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_54" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_54');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_54" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_54','patternNBS_UI_54','questionbodyNBS_UI_54');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_54">
<tr id="nopatternNBS_UI_54" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_54 InputFieldLabel" id="29303_5L" title="Indicate the antibiotic the patient received.">
Medication (Antibiotic) Administered:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(29303_5)" styleId="29303_5" title="Indicate the antibiotic the patient received." onchange="unhideBatchImg('NBS_UI_54');">
<nedss:optionsCollection property="codedValue(PHVS_ANTIBIOTICRECEIVED_PERTUSSIS)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="INV924L" title="Indicate the date the treatment was initiated.">
Treatment Start Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV924)" maxlength="10" size="10" styleId="INV924" onkeyup="unhideBatchImg('NBS_UI_54');DateMask(this,null,event)" styleClass="NBS_UI_54" title="Indicate the date the treatment was initiated."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV924','INV924Icon'); unhideBatchImg('NBS_UI_54');return false;" styleId="INV924Icon" onkeypress="showCalendarEnterKey('INV924','INV924Icon',event)"></html:img>
</td> </tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="67453_1L" title="Indicate the number of days the patient actually took the antibiotic referenced.">
Number of Days Actually Taken:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(67453_1)" size="3" maxlength="3"  title="Indicate the number of days the patient actually took the antibiotic referenced." styleId="67453_1" onkeyup="unhideBatchImg('NBS_UI_54');isNumericCharacterEntered(this)"/>
</td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_54">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_54BatchAddFunction()) writeQuestion('NBS_UI_54','patternNBS_UI_54','questionbodyNBS_UI_54')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_54">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_54BatchAddFunction()) writeQuestion('NBS_UI_54','patternNBS_UI_54','questionbodyNBS_UI_54');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_54"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_54BatchAddFunction()) writeQuestion('NBS_UI_54','patternNBS_UI_54','questionbodyNBS_UI_54');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_54"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_54')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_47" name="Lab Testing" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV740L" title="Was laboratory testing done to confirm the diagnosis?">
Was laboratory testing done for pertussis?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV740)" styleId="INV740" title="Was laboratory testing done to confirm the diagnosis?" onchange="ruleEnDisINV7408606();ruleEnDisINV7408605();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV164L" title="Was the case laboratory confirmed?">
Was the case laboratory confirmed?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV164)" styleId="INV164" title="Was the case laboratory confirmed?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="LAB515L" title="Was a specimen sent to CDC for testing?">
Specimen Sent to CDC:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB515)" styleId="LAB515" title="Was a specimen sent to CDC for testing?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<!--Date Field Visible set to False-->
<tr style="display:none"><td class="fieldName">
<span title="Date sent for genotyping" id="NBS380L">
LEGACY Date sent for genotyping:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS380)" maxlength="10" size="10" styleId="NBS380" title="Date sent for genotyping"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS380','NBS380Icon');return false;" styleId="NBS380Icon" onkeypress="showCalendarEnterKey('NBS380','NBS380Icon',event);" ></html:img>
</td> </tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="Legacy Specimen Type" id="NBS381L">
LEGACY Specimen Type:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS381)" size="25" maxlength="25" title="Legacy Specimen Type" styleId="NBS381"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_44" name="Interpretive Lab Data Repeating Block" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_44errorMessages">
<b> <a name="NBS_UI_44errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_44"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_44">
<tr id="patternNBS_UI_44" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_44" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_44');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_44" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_44');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_44" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_44','patternNBS_UI_44','questionbodyNBS_UI_44');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_44">
<tr id="nopatternNBS_UI_44" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_44 InputFieldLabel" id="INV290L" title="Type of test(s) performed for this case.">
Lab Test Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV290)" styleId="INV290" title="Type of test(s) performed for this case." onchange="unhideBatchImg('NBS_UI_44');enableOrDisableOther('INV290');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTTYPE_PERTUSSIS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Type of test(s) performed for this case." id="INV290OthL">Other Lab Test Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV290Oth)" size="40" maxlength="40" title="Other Type of test(s) performed for this case." onkeyup="unhideBatchImg('NBS_UI_44')" styleId="INV290Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_44 InputFieldLabel" id="INV291L" title="Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative).">
Lab Test Result Qualitative:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV291)" styleId="INV291" title="Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative)." onchange="unhideBatchImg('NBS_UI_44');enableOrDisableOther('INV291');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_LABTESTINTERPRETATION_PERTUSSIS)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative)." id="INV291OthL">Other Lab Test Result Qualitative:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV291Oth)" size="40" maxlength="40" title="Other Indicate the qualitative test result value for the lab test performed, (e.g., positive, detected, negative)." onkeyup="unhideBatchImg('NBS_UI_44')" styleId="INV291Oth"/></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB628L" title="Indicate the quantitative test result value for the lab test performed.">
Lab Test Result Quantitative:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB628)" size="10" maxlength="10" title="Indicate the quantitative test result value for the lab test performed." styleId="LAB628" onkeyup="unhideBatchImg('NBS_UI_44');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_44 InputFieldLabel" id="LAB115L" title="Units of measure for the Quantitative Test Result Value">
Quantitative Test Result Units:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB115)" styleId="LAB115" title="Units of measure for the Quantitative Test Result Value" onchange="unhideBatchImg('NBS_UI_44');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB163L" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date.">
Specimen Collection Date/Time:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB163)" maxlength="10" size="10" styleId="LAB163" onkeyup="unhideBatchImg('NBS_UI_44');DateMask(this,null,event)" styleClass="NBS_UI_44" title="Date of collection of laboratory specimen used for diagnosis of health event reported in this case report. Time of collection is an optional addition to date."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB163','LAB163Icon'); unhideBatchImg('NBS_UI_44');return false;" styleId="LAB163Icon" onkeypress="showCalendarEnterKey('LAB163','LAB163Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_44 InputFieldLabel" id="LAB165L" title="Anatomic site or specimen type from which positive lab specimen was collected.">
Specimen Source:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB165)" styleId="LAB165" title="Anatomic site or specimen type from which positive lab specimen was collected." onchange="unhideBatchImg('NBS_UI_44');enableOrDisableOther('LAB165');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(SPECIMENTYPEVPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165OthL">Other Specimen Source:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB165Oth)" size="40" maxlength="40" title="Other Anatomic site or specimen type from which positive lab specimen was collected." onkeyup="unhideBatchImg('NBS_UI_44')" styleId="LAB165Oth"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="LAB516L" title="Date specimen sent to CDC">
Date Specimen Sent to CDC:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(LAB516)" maxlength="10" size="10" styleId="LAB516" onkeyup="unhideBatchImg('NBS_UI_44');DateMask(this,null,event)" styleClass="NBS_UI_44" title="Date specimen sent to CDC"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('LAB516','LAB516Icon'); unhideBatchImg('NBS_UI_44');return false;" styleId="LAB516Icon" onkeypress="showCalendarEnterKey('LAB516','LAB516Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_44 InputFieldLabel" id="LAB606L" title="Enter the performing laboratory type">
Performing Lab Type:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB606)" styleId="LAB606" title="Enter the performing laboratory type" onchange="unhideBatchImg('NBS_UI_44');enableOrDisableOther('LAB606');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_PERFORMINGLABORATORYTYPE_VPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Enter the performing laboratory type" id="LAB606OthL">Other Performing Lab Type:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(LAB606Oth)" size="40" maxlength="40" title="Other Enter the performing laboratory type" onkeyup="unhideBatchImg('NBS_UI_44')" styleId="LAB606Oth"/></td></tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_44">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44','patternNBS_UI_44','questionbodyNBS_UI_44')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_44">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44','patternNBS_UI_44','questionbodyNBS_UI_44');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_44"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_44BatchAddFunction()) writeQuestion('NBS_UI_44','patternNBS_UI_44','questionbodyNBS_UI_44');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_44"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_44')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_42" name="Vaccine Preventable Disease (VPD) Lab Message Linkage" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_42errorMessages">
<b> <a name="NBS_UI_42errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_42"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_42">
<tr id="patternNBS_UI_42" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_42" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_42');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_42" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_42');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_42" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_42','patternNBS_UI_42','questionbodyNBS_UI_42');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_42">
<tr id="nopatternNBS_UI_42" class="odd" style="display:">
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
<span class="requiredInputFieldNBS_UI_42 InputFieldLabel" id="LAB143L" title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message">
VPD Lab Message Reference Laboratory:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(LAB143)" styleId="LAB143" title="Vaccine Preventable Disease (VPD) reference laboratory that will be used along with the patient identifier and specimen identifier to uniquely identify a VPD lab message" onchange="unhideBatchImg('NBS_UI_42');">
<nedss:optionsCollection property="codedValue(VPD_LAB_REFERENCE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB598L" title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message">
VPD Lab Message Patient Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB598)" size="25" maxlength="25" title="VPD lab message patient Identifier that will be used along with the reference laboratory and specimen identifier to uniquely identify a VPD lab message" styleId="LAB598" onkeyup="unhideBatchImg('NBS_UI_42');"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="LAB125L" title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message">
VPD Lab Message Specimen Identifier:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(LAB125)" size="25" maxlength="25" title="VPD lab message specimen identifier that will be used along with the patient identifier and reference laboratory to uniquely identify a VPD lab message" styleId="LAB125" onkeyup="unhideBatchImg('NBS_UI_42');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_42">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_42BatchAddFunction()) writeQuestion('NBS_UI_42','patternNBS_UI_42','questionbodyNBS_UI_42')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_42">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_42BatchAddFunction()) writeQuestion('NBS_UI_42','patternNBS_UI_42','questionbodyNBS_UI_42');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_42"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_42BatchAddFunction()) writeQuestion('NBS_UI_42','patternNBS_UI_42','questionbodyNBS_UI_42');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_42"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_42')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_37" name="Vaccination Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126L" title="Did the patient ever receive pertussis-containing vaccine?">
Did the patient ever receive pertussis-containing vaccine?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC126)" styleId="VAC126" title="Did the patient ever receive pertussis-containing vaccine?" onchange="ruleEnDisVAC1268612();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_66L">&nbsp;&nbsp;For the next 2 questions, to indicate that the number of vaccine doses is unknown, enter 99.</span> </td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC132L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine). To indicate that the number of doses is unknown, enter 99.">
If yes, how many doses?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC132)" size="2" maxlength="2"  title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine). To indicate that the number of doses is unknown, enter 99." styleId="VAC132" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC140L" title="Number of vaccine doses against this disease prior to illness onset. To indicate that the number of doses is unknown, enter 99.">
Vaccination Doses Prior to Onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC140)" size="2" maxlength="2"  title="Number of vaccine doses against this disease prior to illness onset. To indicate that the number of doses is unknown, enter 99." styleId="VAC140" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,99)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC142L" title="Indicate the date the patient received the last vaccine dose against mumps prior to illness onset.">
Date of last dose prior to illness onset:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC142)" maxlength="10" size="10" styleId="VAC142" onkeyup="DateMask(this,null,event)" title="Indicate the date the patient received the last vaccine dose against mumps prior to illness onset."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAC142','VAC142Icon'); return false;" styleId="VAC142Icon" onkeypress="showCalendarEnterKey('VAC142','VAC142Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC148L" title="This data element is used for all cases. For example, a case might not have received a vaccine because they were too young per ACIP schedules.">
Was the patient vaccinated per ACIP recommendations?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC148)" styleId="VAC148" title="This data element is used for all cases. For example, a case might not have received a vaccine because they were too young per ACIP schedules." onchange="ruleEnDisVAC1488604();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC149L" title="Indicate the reason the patient was not vaccinated as recommended by ACIP.">
Reason patient not vaccinated per ACIP recommendations:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC149)" styleId="VAC149" title="Indicate the reason the patient was not vaccinated as recommended by ACIP.">
<nedss:optionsCollection property="codedValue(PHVS_VAC_NOTG_RSN)" value="key" label="value" /></html:select>
</td></tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC133L" title="Indicate any pertinent notes regarding the patient's vaccination history that may not have already been communicated via the standard vaccination questions on this form.">
Notes pertaining to the patient's vaccination history:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageForm" property="pageClientVO.answer(VAC133)" styleId ="VAC133" onkeyup="checkTextAreaLength(this, 2000)" title="Indicate any pertinent notes regarding the patient's vaccination history that may not have already been communicated via the standard vaccination questions on this form."/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_36" name="Disease Transmission" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV907L" title="Is this case epi-linked to a laboratory-confirmed case?">
Is this case epi-linked to a laboratory-confirmed case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV907)" styleId="INV907" title="Is this case epi-linked to a laboratory-confirmed case?" onchange="ruleEnDisINV9078608();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS350L" title="If epi-linked to a laboratory-confirmed case, Case ID of epi-linked case.">
Case ID of epi-linked case:</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(NBS350)" size="25" maxlength="25" title="If epi-linked to a laboratory-confirmed case, Case ID of epi-linked case." styleId="NBS350"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV224L" title="What was the transmission setting where the condition was acquired?">
Transmission Setting (Where did the case acquire pertusis?):</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV224)" styleId="INV224" title="What was the transmission setting where the condition was acquired?" onchange="enableOrDisableOther('INV224');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_TRAN_SETNG)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What was the transmission setting where the condition was acquired?" id="INV224OthL">Other Transmission Setting (Where did the case acquire pertusis?):</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV224Oth)" size="40" maxlength="40" title="Other What was the transmission setting where the condition was acquired?" styleId="INV224Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS351L" title="Was there documented transmission from this case of pertussis to a new setting (outside of the household)?">
Was there documented transmission from this case to a new setting (outside of the household)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS351)" styleId="NBS351" title="Was there documented transmission from this case of pertussis to a new setting (outside of the household)?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="INV561L" title="What was the new setting (outside of the household) for transmission of pertussis from this case?">
What was the new setting (outside of the household) for transmission of pertussis from this case?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(INV561)" styleId="INV561" title="What was the new setting (outside of the household) for transmission of pertussis from this case?" onchange="enableOrDisableOther('INV561');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_SETTINGOFFURTHERSPREAD_CDC)" value="key" label="value" /></html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="What was the new setting (outside of the household) for transmission of pertussis from this case?" id="INV561OthL">Other What was the new setting (outside of the household) for transmission of pertussis from this case?:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(INV561Oth)" size="40" maxlength="40" title="Other What was the new setting (outside of the household) for transmission of pertussis from this case?" styleId="INV561Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS354L" title="Were there one or more suspected sources of infection (A suspected source is another person with a cough who was in contact with  the case 7-20 days before the cases cough).">
Were there one or more suspected sources of infection?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS354)" styleId="NBS354" title="Were there one or more suspected sources of infection (A suspected source is another person with a cough who was in contact with  the case 7-20 days before the cases cough)." onchange="ruleHideUnhNBS3548610();ruleEnDisNBS3548609();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS355L" title="Indicate the number of suspected sources of infection.">
Number of suspected sources of infection:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS355)" size="3" maxlength="3"  title="Indicate the number of suspected sources of infection." styleId="NBS355" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,999)"/>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="INV562L" title="Indicate the number of contacts of this case recommended to receive antibiotic prophylaxis.">
Number of contacts of this case recommended to receive antibiotic prophylaxis:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(INV562)" size="3" maxlength="3"  title="Indicate the number of contacts of this case recommended to receive antibiotic prophylaxis." styleId="INV562" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,999)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_57" name="For each suspected source of infection, indicate the following:" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_57errorMessages">
<b> <a name="NBS_UI_57errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_57"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_57">
<tr id="patternNBS_UI_57" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_57" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_57');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_57" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_57');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_57" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_57">
<tr id="nopatternNBS_UI_57" class="odd" style="display:">
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
<span class="InputFieldLabel" id="NBS356L" title="Suspected source age">
Age:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS356)" size="3" maxlength="3"  title="Suspected source age" styleId="NBS356" onkeyup="unhideBatchImg('NBS_UI_57');isNumericCharacterEntered(this)" styleClass="relatedUnitsFieldNBS_UI_57"/>
<html:select name="PageForm" property="pageClientVO.answer(NBS356Unit)" styleId="NBS356UNIT" onchange="unhideBatchImg('NBS_UI_57')">
<nedss:optionsCollection property="codedValue(AGE_UNIT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_57 InputFieldLabel" id="NBS358L" title="Suspected source sex">
Sex:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS358)" styleId="NBS358" title="Suspected source sex" onchange="unhideBatchImg('NBS_UI_57');">
<nedss:optionsCollection property="codedValue(SEX)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS363L" title="Suspected source cough onset date">
Cough Onset Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS363)" maxlength="10" size="10" styleId="NBS363" onkeyup="unhideBatchImg('NBS_UI_57');DateMask(this,null,event)" styleClass="NBS_UI_57" title="Suspected source cough onset date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS363','NBS363Icon'); unhideBatchImg('NBS_UI_57');return false;" styleId="NBS363Icon" onkeypress="showCalendarEnterKey('NBS363','NBS363Icon',event)"></html:img>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_57 InputFieldLabel" id="NBS359L" title="Suspected source relationship to case">
Relationship to Case:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS359)" styleId="NBS359" title="Suspected source relationship to case" onchange="unhideBatchImg('NBS_UI_57');enableOrDisableOther('NBS359');pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(PHVS_RELATIONSHIP_VPD)" value="key" label="value" /> </html:select>
</td></tr>
<!--Other entry allowed for this Coded Question-->
<tr><td class="fieldName">
<span class="InputDisabledLabel otherEntryField" title="Suspected source relationship to case" id="NBS359OthL">Other Relationship to Case:</span></td>
<td><html:text name="PageForm" disabled="true" property="pageClientVO.answer(NBS359Oth)" size="40" maxlength="40" title="Other Suspected source relationship to case" onkeyup="unhideBatchImg('NBS_UI_57')" styleId="NBS359Oth"/></td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_57 InputFieldLabel" id="NBS362L" title="How many doses of pertussis-containing vaccine has the suspected source received?">
How many doses of pertussis-containing vaccine has the suspected source received?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS362)" styleId="NBS362" title="How many doses of pertussis-containing vaccine has the suspected source received?" onchange="unhideBatchImg('NBS_UI_57');">
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
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_57">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_57"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_57BatchAddFunction()) writeQuestion('NBS_UI_57','patternNBS_UI_57','questionbodyNBS_UI_57');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_57"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_57')"/>&nbsp;	&nbsp;&nbsp;
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
