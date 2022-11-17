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
String tabId = "editStudies";
tabId = tabId.replace("]","");
tabId = tabId.replace("[","");
tabId = tabId.replaceAll(" ", "");
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Studies Details","SARS-COV-2 Testing"};
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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30002L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30003L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30004L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30005L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30007L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30006L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30008L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30009L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30010L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30011L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30012L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30013L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30001L"><hr/></span> </td></tr>

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
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30000L"><hr/></span> </td></tr>

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
<html:text name="PageForm" property="pageClientVO.answer(373065002)" size="10" maxlength="10" title="Indicate max coronary artery Z-score." styleId="373065002"/>
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
<html:text name="PageForm"  property="pageClientVO.answer(NBS704)" maxlength="10" size="10" styleId="NBS704" onkeyup="DateMask(this,null,event)" title="Indicate the date of first test showing coronary artery aneurysm or dilatation."/>
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
<html:select name="PageForm" property="pageClientVO.answer(441987005)" styleId="441987005" title="Was abdominal imaging was completed for this patient?" onchange="ruleEnDis4419870058757();enableOrDisableOther('NBS706');pgSelectNextFocus(this);">
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
<html:select name="PageForm" property="pageClientVO.answer(413815006)" styleId="413815006" title="Was chest imaging completed for this patient?" onchange="ruleEnDis4138150068758();enableOrDisableOther('LAB678');pgSelectNextFocus(this);">
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
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27015" name="SARS-COV-2 Testing Details" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA30014L">&nbsp;&nbsp;If multiple tests were performed for a given test type, enter the date for the first positive.</span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS718L" title="RT-PCR Test Result">
RT-PCR Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS718)" styleId="NBS718" title="RT-PCR Test Result" onchange="ruleEnDisNBS7188764();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS719L" title="RT-PCR Test Date">
RT-PCR Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS719)" maxlength="10" size="10" styleId="NBS719" onkeyup="DateMask(this,null,event)" title="RT-PCR Test Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS719','NBS719Icon'); return false;" styleId="NBS719Icon" onkeypress="showCalendarEnterKey('NBS719','NBS719Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA31000L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS720L" title="Antigen Test Result">
Antigen Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS720)" styleId="NBS720" title="Antigen Test Result" onchange="ruleEnDisNBS7208765();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS721L" title="Antigen Test Date">
Antigen Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS721)" maxlength="10" size="10" styleId="NBS721" onkeyup="DateMask(this,null,event)" title="Antigen Test Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS721','NBS721Icon'); return false;" styleId="NBS721Icon" onkeypress="showCalendarEnterKey('NBS721','NBS721Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA31001L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS722L" title="IgG Test Result">
IgG Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS722)" styleId="NBS722" title="IgG Test Result" onchange="ruleEnDisNBS7228766();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS723L" title="IgG Test Date">
IgG Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS723)" maxlength="10" size="10" styleId="NBS723" onkeyup="DateMask(this,null,event)" title="IgG Test Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS723','NBS723Icon'); return false;" styleId="NBS723Icon" onkeypress="showCalendarEnterKey('NBS723','NBS723Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA31002L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS724L" title="IgM Test Result">
IgM Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS724)" styleId="NBS724" title="IgM Test Result" onchange="ruleEnDisNBS7248767();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS725L" title="IgM Test Date">
IgM Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS725)" maxlength="10" size="10" styleId="NBS725" onkeyup="DateMask(this,null,event)" title="IgM Test Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS725','NBS725Icon'); return false;" styleId="NBS725Icon" onkeypress="showCalendarEnterKey('NBS725','NBS725Icon',event)"></html:img>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><span id="NBS_UI_GA31003L"><hr/></span> </td></tr>

<!--processing Coded Question  -->
<tr><td class="fieldName">
<span class=" InputFieldLabel" id="NBS726L" title="IgA Test Result">
IgA Test Result:</span>
</td>
<td>
<html:select name="PageForm" property="pageClientVO.answer(NBS726)" styleId="NBS726" title="IgA Test Result" onchange="ruleEnDisNBS7268768();pgSelectNextFocus(this);">
<nedss:optionsCollection property="codedValue(TEST_RESULT_COVID)" value="key" label="value" /></html:select>
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span class="
InputFieldLabel" id="NBS727L" title="IgA Test Date">
IgA Test Date:</span>
</td>
<td>
<html:text name="PageForm"  property="pageClientVO.answer(NBS727)" maxlength="10" size="10" styleId="NBS727" onkeyup="DateMask(this,null,event)" title="IgA Test Date"/>
<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('NBS727','NBS727Icon'); return false;" styleId="NBS727Icon" onkeypress="showCalendarEnterKey('NBS727','NBS727Icon',event)"></html:img>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
