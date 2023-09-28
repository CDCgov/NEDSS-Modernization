<?xml version="1.0" encoding="UTF-8"?>
<!-- ### DMB:BEGIN JSP PAGE GENERATE ###- - -->
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
String tabId = "editSusceptibilityTest";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Susceptibility Test"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2" name="Related Tests and Results" isHidden="F" classType="subSect"  addedClass="batchSubSection">
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td>.</td>
<td   width="100%">
<div class="infoBox errors" style="display: none;visibility: none;" id="NBS_UI_2errorMessages">
<b> <a name="NBS_UI_2errorMessages_errorMessagesHref"></a> Please fix the following errors:</b> <br/>
</div>
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_2"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_2">
<tr id="patternNBS_UI_2" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;">
<input id="viewNBS_UI_2" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
</td><td style="width:3%;text-align:center;">
<input id="editNBS_UI_2" type="image" src="page_white_edit.gif" tabIndex="0" onclick="editClicked(this.id,'NBS_UI_2');return false" name="image" align="middle" cellspacing="2" 		cellpadding="3" border="55" class="cursorHand" title="Edit" alt="Edit">
</td><td style="width:3%;text-align:center;">
<input id="deleteNBS_UI_2" type="image" src="cross.gif" tabIndex="0" onclick="deleteClicked(this.id,'NBS_UI_2','patternNBS_UI_2','questionbodyNBS_UI_2');return false" name="image" align="middle" 	cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="Delete" alt="Delete">	</td>
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
<tbody id="questionbodyNBS_UI_2">
<tr id="nopatternNBS_UI_2" class="odd" style="display:">
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

<!--processing Coded with Search  -->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span class="requiredInputFieldNBS_UI_2 InputFieldLabel" id="NBS_LAB110L" title="The description for the result test code, i.e. test name.">
Drug Name:</span>
</td>
<td>
<div style="float:left">
<html:select name="PageSubForm" property="pageClientVO.answer(NBS_LAB110)" styleId="NBS_LAB110" title="The description for the result test code, i.e. test name." onchange="unhideBatchImg('NBS_UI_2');">
<nedss:optionsCollection property="codedValue(LAB_TEST)" value="key" label="value"/></html:select>
</div>
<span><input name="attributeMap.NBS_LAB110Code" id="NBS_LAB110CodeId" type="hidden" value=""></input>
<input name="attributeMap.NBS_LAB110DescriptionWithCode" id="NBS_LAB110DescriptionId" type="hidden" value=""></input>
<span title="The description for the result test code, i.e. test name." id="NBS_LAB110Description"></span></span>
<input type="button" class="Button" value="Search"
id="NBS_LAB110Search" style="margin-left:5px" onclick="searchFromSingleSelectWithSearch('NBS_LAB110');" />
<input type="button" class="Button" value="Clear" id="NBS_LAB110ClearButton" onclick="clearSingleSelectWithSearchButton('NBS_LAB110')"/>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_2 InputFieldLabel" id="NBS377L" title="Indicates the susceptibility test technique or method used, (e.g., serum neutralization, titration, dipstick, test strip, anaerobic culture).">
Result Method:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS377)" styleId="NBS377" title="Indicates the susceptibility test technique or method used, (e.g., serum neutralization, titration, dipstick, test strip, anaerobic culture)." onchange="unhideBatchImg('NBS_UI_2');">
<nedss:optionsCollection property="codedValue(OBS_METH_SUSC)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span class="InputFieldLabel" id="NBS369L" title="Indicates the susceptibility test numeric result first numeric value.">
Numeric Result:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS369)" size="35" maxlength="35" title="Indicates the susceptibility test numeric result first numeric value." styleId="NBS369" onkeyup="unhideBatchImg('NBS_UI_2');"/>
</td> </tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_2 InputFieldLabel" id="NBS372L" title="Indicates the susceptibility test numeric result units of measure.">
Units:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS372)" styleId="NBS372" title="Indicates the susceptibility test numeric result units of measure." onchange="unhideBatchImg('NBS_UI_2');">
<nedss:optionsCollection property="codedValue(UNIT_ISO)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_2 InputFieldLabel" id="NBS367L" title="Indicates the susceptibility test coded result (non-organism).">
Coded Result:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS367)" styleId="NBS367" title="Indicates the susceptibility test coded result (non-organism)." onchange="unhideBatchImg('NBS_UI_2');">
<nedss:optionsCollection property="codedValue(CODED_LAB_RESULT)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="Indicates the susceptibility test text result." id="NBS365L">
Text Result:</span>
</td>
<td>
<span id="NBS365S"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS365)" />
</td>
<td>
<html:hidden name="PageSubForm"  property="pageClientVO.answer(NBS365)" styleId="NBS365" />
</td>
</tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="Indicates the susceptibility test reference range low value." id="NBS373L">
Reference Range - From:</span>
</td>
<td>
<span id="NBS373S"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS373)" />
</td>
<td>
<html:hidden name="PageSubForm"  property="pageClientVO.answer(NBS373)" styleId="NBS373" />
</td>
</tr>

<!--processing ReadOnly Textbox Text Question-->
<tr><td class="fieldName">
<span title="Indicates the susceptibility test reference range high value." id="NBS374L">
Reference Range - To:</span>
</td>
<td>
<span id="NBS374S"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS374)" />
</td>
<td>
<html:hidden name="PageSubForm"  property="pageClientVO.answer(NBS374)" styleId="NBS374" />
</td>
</tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class="NBS_UI_2 InputFieldLabel" id="NBS378L" title="Indicates the susceptibility test interpretation. The interpretation flag identifies a result that is not typical, as well as how its not typical (e.g., susceptible, resistant, normal, above upper panic limits, below absolute low).">
Interpretation:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS378)" styleId="NBS378" title="Indicates the susceptibility test interpretation. The interpretation flag identifies a result that is not typical, as well as how its not typical (e.g., susceptible, resistant, normal, above upper panic limits, below absolute low)." onchange="unhideBatchImg('NBS_UI_2');">
<nedss:optionsCollection property="codedValue(OBS_INTRP)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Date Question-->

<!--processing ReadOnly Date-->
<tr><td class="fieldName">
<span title="Indicates the date the specimen was collected." id="NBS405L">Lab Report Date:</span>
</td><td>
<span id="NBS405S"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS405)"  />
</td>
<td>
<html:hidden name="PageSubForm"  property="pageClientVO.answer(NBS405)" styleId="NBS405" />
</td>
</tr>

<!--processing ReadOnly Select Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the susceptibility test result status." id="NBS376L" >
Status:</span></td><td>
<span id="NBS376" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS376)"
codeSetNm="ACT_OBJ_ST"/>
</td><td><html:hidden name="PageSubForm" property="pageClientVO.answer(NBS376)" styleId="NBS376cd" />
</td> </tr>

<!--processing ReadOnly Select Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the resulted test; in most cases this is a standardized test (LOINC) test." id="NBS_LAB293_1L" >
Test Code(s):</span></td><td>
<span id="NBS_LAB293_1" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB293_1)"
codeSetNm="RESULTED_LAB_TEST"/>
</td><td><html:hidden name="PageSubForm" property="pageClientVO.answer(NBS_LAB293_1)" styleId="NBS_LAB293_1cd" />
</td> </tr>

<!--processing ReadOnly Select Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the coded result value text description, (e.g., positive, detected, etc.)" id="NBS_LAB121_1L" >
Result Code(s):</span></td><td>
<span id="NBS_LAB121_1" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB121_1)"
codeSetNm="CODED_LAB_RESULT"/>
</td><td><html:hidden name="PageSubForm" property="pageClientVO.answer(NBS_LAB121_1)" styleId="NBS_LAB121_1cd" />
</td> </tr>

<!--processing TextArea-->
<tr>
<td class="fieldName">
<span class="InputFieldLabel" id="NBS375L" title="Indicates the susceptibility test result comments (notes and comment related to the result being reported).">
Result Comments:</span>
</td>
<td>
<html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="PageSubForm" property="pageClientVO.answer(NBS375)" styleId ="NBS375" onkeyup="checkTextAreaLength(this, 2000)" title="Indicates the susceptibility test result comments (notes and comment related to the result being reported)."/>
</td> </tr>

<!--processing Hidden Coded Question  -->
<tr style="display:none"><td class="fieldName">
<span class="InputFieldLabel" id="NBS455L" title="Provide context of the Lab Report. That is if Lab is created Internally, Externally or Electronically.">
Electronic Indicator:</span>
</td>
<td>
<html:select name="PageSubForm" property="pageClientVO.answer(NBS455)" styleId="NBS455" title="Provide context of the Lab Report. That is if Lab is created Internally, Externally or Electronically." onchange="unhideBatchImg('NBS_UI_2');ruleHideUnhNBS4556413()">
<nedss:optionsCollection property="codedValue(DATA_SOURCE_TYPE)" value="key" label="value" /> </html:select>
</td></tr>

<!--processing Hidden Text Question-->
<tr style="display:none"> <td class="fieldName">
<span title="It is use in linking the resulted test data to the isolate and susceptibility data. It identifies individual entries in resulted test block." id="NBS458L">
Parent UID:</span>
</td>
<td>
<html:text name="PageSubForm" property="pageClientVO.answer(NBS458)" size="20" maxlength="20" title="It is use in linking the resulted test data to the isolate and susceptibility data. It identifies individual entries in resulted test block." styleId="NBS458" onkeyup="unhideBatchImg('NBS_UI_2');"/>
</td> </tr>
<% String disableSubmitButton="no";
if(request.getAttribute("disableSubmitButton") != null){
disableSubmitButton= request.getAttribute("disableSubmitButton").toString();
}
%>
<%if(disableSubmitButton.equals("yes")) {%>
<tr id="AddButtonToggleNBS_UI_2">
<td colspan="2" align="right">
<input type="button" value="     Add     "   disabled="disabled" onclick="if (pgNBS_UI_2BatchAddFunction()) writeQuestion('NBS_UI_2','patternNBS_UI_2','questionbodyNBS_UI_2')"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} else {%>
<tr id="AddButtonToggleNBS_UI_2">
<td colspan="2" align="right">
<input type="button" value="     Add     "  onclick="if (pgNBS_UI_2BatchAddFunction()) writeQuestion('NBS_UI_2','patternNBS_UI_2','questionbodyNBS_UI_2');"/>&nbsp;&nbsp;
&nbsp;
</td>
</tr>
<%} %>
<tr id="UpdateButtonToggleNBS_UI_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="   Update   "    onclick="if (pgNBS_UI_2BatchAddFunction()) writeQuestion('NBS_UI_2','patternNBS_UI_2','questionbodyNBS_UI_2');"/>&nbsp;		&nbsp;
&nbsp;
</td>
</tr>
<tr id="AddNewButtonToggleNBS_UI_2"
style="display:none">
<td colspan="2" align="right">
<input type="button" value="  Add New  "  onclick="clearClicked('NBS_UI_2')"/>&nbsp;	&nbsp;&nbsp;
</td>
</tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
