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
String tabId = "editHepatitisExtended";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Perinatal Hep B Mother Information","Infant Information"};
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
<nedss:container id="NBS_INV_HEPBP_3" name="Mother Race and Ethnicity" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS387L" title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page.">
Next of Kin Relationship:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS387)" styleId="NBS387" title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page.">
<nedss:optionsCollection property="codedValue(PH_RELATIONSHIP_HL7_2X)" value="key" label="value" /></html:select>
</td></tr>

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
onchange="displaySelectedOptions(this, 'MTH157-selectedValues');ruleHideUnhMTH1577426()" >
<nedss:optionsCollection property="codedValue(PHVS_RACECATEGORY_CDC)" value="key" label="value" /> </html:select>
<div id="MTH157-selectedValues" style="margin:0.25em;">
<b> Selected Values: </b>
</div>
</div></td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="MTH171L" title="If other race is selected for the mother, please specify">
Other Race for Mother (specify):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(MTH171)" size="100" maxlength="100" title="If other race is selected for the mother, please specify" styleId="MTH171"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH159L" title="Ethnicity of the patient's mother">
Ethnicity of Mother:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH159)" styleId="MTH159" title="Ethnicity of the patient's mother">
<nedss:optionsCollection property="codedValue(PHVS_ETHNICITYGROUP_CDC_UNK)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="If mother's ethnicity is other, specify" id="MTH170L">
Other Ethnicity for Mother (specify):</span>
</td>
<td>
<html:text name="PageForm" property="pageClientVO.answer(MTH170)" size="100" maxlength="100" title="If mother's ethnicity is other, specify" styleId="MTH170"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH161L" title="Was the patient's mother born outside of the United States?">
Was Mother born outside of United States?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH161)" styleId="MTH161" title="Was the patient's mother born outside of the United States?" onchange="ruleHideUnhMTH1617424();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH109L" title="What is the birth country of the mother">
What is the birth country of the mother?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH109)" styleId="MTH109" title="What is the birth country of the mother">
<nedss:optionsCollection property="codedValue(PHVS_BIRTHCOUNTRY_CDC)" value="key" label="value" /></html:select>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_4" name="Mother HBsAg Status" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH162L" title="Was the mother confirmed positive for the illness being reported prior to or at the time of delivery?">
Was the mother confirmed positive prior to or at the time of delivery?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH162)" styleId="MTH162" title="Was the mother confirmed positive for the illness being reported prior to or at the time of delivery?" onchange="ruleHideUnhMTH1627425();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="MTH163L" title="Was the mother confirmed positive for the illness being reported after delivery?">
If no, was the mother confirmed positive after delivery?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(MTH163)" styleId="MTH163" title="Was the mother confirmed positive for the illness being reported after delivery?">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="MTH164L" title="Enter the date of the mother's earliest postive test result">
Date of Earliest Positive Test Result:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(MTH164)" maxlength="10" size="10" styleId="MTH164" onkeyup="DateMask(this,null,event)" title="Enter the date of the mother's earliest postive test result"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('MTH164','MTH164Icon'); return false;" styleId="MTH164Icon" onkeypress="showCalendarEnterKey('MTH164','MTH164Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_6" name="Hepatitis Containing Vaccination" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC126L" title="Has the child ever received a vaccination for Hepatitis B?">
Has the child ever received a vaccination for Hepatitis B?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC126)" styleId="VAC126" title="Has the child ever received a vaccination for Hepatitis B?" onchange="ruleHideUnhVAC1267429();ruleHideUnhVAC1267428();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Numeric Question-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="VAC132L" title="Total number of doses of vaccine the child received. Enter a numeric value based on the category on the form.">
How many doses of Hepatitis B vaccine did the child receive?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC132)" size="2" maxlength="2"  title="Total number of doses of vaccine the child received. Enter a numeric value based on the category on the form." styleId="VAC132" onkeyup="isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,0,4)"/>
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_7" name="Vaccine Doses Received" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_INV_HEPBP_7errorMessages">
<b> <a name="NBS_INV_HEPBP_7errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_HEPBP_7"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_HEPBP_7">
<tr id="patternNBS_INV_HEPBP_7" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_INV_HEPBP_7" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_HEPBP_7');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_INV_HEPBP_7" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_INV_HEPBP_7');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_INV_HEPBP_7" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_INV_HEPBP_7','patternNBS_INV_HEPBP_7','questionbodyNBS_INV_HEPBP_7');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_INV_HEPBP_7">
<tr id="nopatternNBS_INV_HEPBP_7" class="odd" style="display:">
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
<span class="InputFieldLabel" id="VAC120L" title="Enter the vaccine dose number in the series of vaccination (e.g. 1, 2, 3, etc.)">
Dose Number:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC120)" size="2" maxlength="2"  title="Enter the vaccine dose number in the series of vaccination (e.g. 1, 2, 3, etc.)" styleId="VAC120" onkeyup="unhideBatchImg('NBS_INV_HEPBP_7');isNumericCharacterEntered(this)" onblur="pgCheckFieldMinMax(this,1,4)"/>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC103L" title="Enter the date the vaccine was administered">
Date of Vaccination:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC103)" maxlength="10" size="10" styleId="VAC103" onkeyup="unhideBatchImg('NBS_INV_HEPBP_7');DateMask(this,null,event)" styleClass="NBS_INV_HEPBP_7" title="Enter the date the vaccine was administered"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAC103','VAC103Icon'); unhideBatchImg('NBS_INV_HEPBP_7');return false;" styleId="VAC103Icon" onkeypress="showCalendarEnterKey('VAC103','VAC103Icon',event)"></html:img>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_INV_HEPBP_7">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_INV_HEPBP_7BatchAddFunction()) writeQuestion('NBS_INV_HEPBP_7','patternNBS_INV_HEPBP_7','questionbodyNBS_INV_HEPBP_7')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_INV_HEPBP_7">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_INV_HEPBP_7BatchAddFunction()) writeQuestion('NBS_INV_HEPBP_7','patternNBS_INV_HEPBP_7','questionbodyNBS_INV_HEPBP_7');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_INV_HEPBP_7"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_INV_HEPBP_7BatchAddFunction()) writeQuestion('NBS_INV_HEPBP_7','patternNBS_INV_HEPBP_7','questionbodyNBS_INV_HEPBP_7');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_INV_HEPBP_7"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_INV_HEPBP_7')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_8" name="HBIG Information" isHidden="F" classType="subSect" >

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="VAC143L" title="Has the patient ever received immune globulin for this condition?">
Did the child receive hepatitis B immune globulin (HBIG)?:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(VAC143)" styleId="VAC143" title="Has the patient ever received immune globulin for this condition?" onchange="ruleHideUnhVAC1437427();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(YNU)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="VAC144L" title="Date the child received the dose of HBIG.">
If yes, on what date did the child receive HBIG?:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(VAC144)" maxlength="10" size="10" styleId="VAC144" onkeyup="DateMask(this,null,event)" title="Date the child received the dose of HBIG."/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('VAC144','VAC144Icon'); return false;" styleId="VAC144Icon" onkeypress="showCalendarEnterKey('VAC144','VAC144Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
