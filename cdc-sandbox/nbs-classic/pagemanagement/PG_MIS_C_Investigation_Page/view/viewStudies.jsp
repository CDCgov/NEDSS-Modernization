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
<%
Map map = new HashMap();
if(request.getAttribute("SubSecStructureMap") != null){
// String watemplateUid="1000879";
// map = util.getBatchMap(new Long(watemplateUid));
map =(Map)request.getAttribute("SubSecStructureMap");
}%>
<%
String tabId = "viewStudies";
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
<tr><td class="fieldName" valign="top">
<span id="NBS714L" title="If the patient had a fibrinogen result, enter the highest value.">
Fibrinogen Highest Value:</span>
</td><td>
<span id="NBS714"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS714)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a fibrinogen test, enter the unit of measure associated with the value." id="NBS715L" >
Fibrinogen Units:</span></td><td>
<span id="NBS715" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS715)"
codeSetNm="FIBRINOGEN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a fibrinogen result, indicate the interpretation." id="NBS716L" >
Fibrinogen Interpretation:</span></td><td>
<span id="NBS716" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS716)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS717L" title="If the patient had a CRP result, enter the highest value.">
CRP Highest Value:</span>
</td><td>
<span id="NBS717"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS717)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a CRP result enter the unit of measure associated with the value." id="NBS728L" >
CRP Unit:</span></td><td>
<span id="NBS728" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS728)"
codeSetNm="CRP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a CRP result, indicate the interpretation." id="NBS729L" >
CRP Interpretation:</span></td><td>
<span id="NBS729" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS729)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS730L" title="If the patient had a ferritin result, enter the highest value.">
Ferritin Test Highest Value:</span>
</td><td>
<span id="NBS730"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS730)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a ferritin result enter the unit of measure associated with the value." id="NBS731L" >
Ferritin Units:</span></td><td>
<span id="NBS731" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS731)"
codeSetNm="FERRITIN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a ferritin result, indicate the interpretation." id="NBS732L" >
Ferritin Interpretation:</span></td><td>
<span id="NBS732" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS732)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS733L" title="If the patient had a troponin result, enter the highest value.">
Troponin Highest Value:</span>
</td><td>
<span id="NBS733"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS733)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a troponin result, enter the unit of measure associated with the value." id="NBS734L" >
Troponin Units:</span></td><td>
<span id="NBS734" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS734)"
codeSetNm="TROPONIN_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a troponin result, indicate the interpretation." id="NBS735L" >
Troponin Interpretation:</span></td><td>
<span id="NBS735" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS735)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS736L" title="If the patient had a BNP result, enter the highest value.">
BNP Highest Value:</span>
</td><td>
<span id="NBS736"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS736)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a BNP result, enter the unit of measure associated with the value." id="NBS737L" >
BNP Units:</span></td><td>
<span id="NBS737" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS737)"
codeSetNm="BNP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a BNP result, indicate the interpretation." id="NBS738L" >
BNP Interpretation:</span></td><td>
<span id="NBS738" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS738)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS739L" title="If the patient had a NT-proBNP result, enter the highest value.">
NT-proBNP Test Highest Value:</span>
</td><td>
<span id="NBS739"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS739)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a NT-proBNP result, enter the unit of measure associated with the value." id="NBS740L" >
NT-proBNP Units:</span></td><td>
<span id="NBS740" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS740)"
codeSetNm="BNP_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a NT-proBNP result, indicate the interpretation." id="NBS741L" >
NT-proBNP Interpretation:</span></td><td>
<span id="NBS741" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS741)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS742L" title="If the patient had a D-dimer result, enter the highest value.">
D-dimer Highest Value:</span>
</td><td>
<span id="NBS742"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS742)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a D-dimer result, enter the unit of measure associated with the value." id="NBS743L" >
D-dimer Units:</span></td><td>
<span id="NBS743" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS743)"
codeSetNm="D_DIMER_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a D-dimer result, indicate the interpretation." id="NBS744L" >
D-dimer Interpretation:</span></td><td>
<span id="NBS744" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS744)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS745L" title="If the patient had a IL-6 result, enter the highest value.">
IL-6 Highest Value:</span>
</td><td>
<span id="NBS745"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS745)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a IL-6 result, enter the unit of measure associated with the value." id="NBS746L" >
IL-6 Unit:</span></td><td>
<span id="NBS746" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS746)"
codeSetNm="IL_6_TEST_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a IL-6 result, indicate the interpretation." id="NBS747L" >
IL-6 Interpretation:</span></td><td>
<span id="NBS747" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS747)"
codeSetNm="LAB_TST_INTERP_MIS"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS748L" title="If the patient had a serum white blood count result, enter the test interpretation.">
Serum White Blood Count Highest Value:</span>
</td><td>
<span id="NBS748"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS748)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS749L" title="If the patient had a serum white blood count result, enter the lowest value.">
Serum White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS749"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS749)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a serum white blood count result, enter the unit of measure associated with the value." id="NBS750L" >
Serum White Blood Count Units:</span></td><td>
<span id="NBS750" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS750)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS751L" title="If the patient had a platelets result, enter the highest value.">
Platelets Highest Value:</span>
</td><td>
<span id="NBS751"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS751)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS752L" title="If the patient had a platelets result, enter the lowest value.">
Platelets Lowest Value:</span>
</td><td>
<span id="NBS752"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS752)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a platelets result, enter the unit of measure associated with the value." id="NBS753L" >
Platelets Units:</span></td><td>
<span id="NBS753" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS753)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS754L" title="If the patient had a neutrophils result, enter the highest value.">
Neutrophils Highest Value:</span>
</td><td>
<span id="NBS754"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS754)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS755L" title="If the patient had a neutrophils result, enter the lowest value.">
Neutrophils Lowest Value:</span>
</td><td>
<span id="NBS755"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS755)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a neutrophils result, enter the unit of measure associated with the value." id="NBS756L" >
Neutrophils Units:</span></td><td>
<span id="NBS756" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS756)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS757L" title="If the patient had a lymphocytes result, enter the highest value.">
Lymphocytes Highest Value:</span>
</td><td>
<span id="NBS757"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS757)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS758L" title="If the patient had a lymphocytes result, enter the lowest value.">
Lymphocytes Lowest Value:</span>
</td><td>
<span id="NBS758"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS758)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a lymphocytes result, enter the unit of measure associated with the value." id="NBS759L" >
Lymphocytes Units:</span></td><td>
<span id="NBS759" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS759)"
codeSetNm="BLOOD_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS760L" title="If the patient had a bands test result, enter the highest value.">
Bands Test Highest Value:</span>
</td><td>
<span id="NBS760"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS760)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS761L" title="If the patient had a band test result, enter the lowest value.">
Bands Test Lowest Value:</span>
</td><td>
<span id="NBS761"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS761)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a bands test result, enter the unit of measure associated with the value." id="NBS762L" >
Bands Test Units:</span></td><td>
<span id="NBS762" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS762)"
codeSetNm="BANDS_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27009" name="CSF Studies" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS763L" title="If the patient had a white blood count result, enter the highest value.">
White Blood Count Highest Value:</span>
</td><td>
<span id="NBS763"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS763)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS764L" title="If the patient had a white blood count result, enter the lowest value.">
White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS764"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS764)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a white blood count result, enter the unit of measure associated with the value." id="NBS765L" >
White Blood Count Units:</span></td><td>
<span id="NBS765" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS765)"
codeSetNm="WBC_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS766L" title="If the patient had a protein test result, enter the highest value.">
Protein Test Highest Value:</span>
</td><td>
<span id="NBS766"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS766)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS767L" title="If the patient had a protein test result, enter the lowest value.">
Protein Test Lowest Value:</span>
</td><td>
<span id="NBS767"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS767)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a protein test result, enter the unit of measure associated with the value." id="NBS768L" >
Protein Test Units:</span></td><td>
<span id="NBS768" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS768)"
codeSetNm="PROTEIN_TEST_UNIT"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS769L" title="If the patient had a glucose result, enter the highest value.">
Glucose Highest Value:</span>
</td><td>
<span id="NBS769"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS769)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS770L" title="If the patient had a glucose test result, enter the lowest value.">
Glucose Lowest Value:</span>
</td><td>
<span id="NBS770"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS770)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a glucose result, enter the unit of measure associated with the value." id="NBS771L" >
Glucose Units:</span></td><td>
<span id="NBS771" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS771)"
codeSetNm="GLUCOSE_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27010" name="Urinalysis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS772L" title="If the patient had a urine while blood count result, enter the highest value.">
Urine White Blood Count Highest Value:</span>
</td><td>
<span id="NBS772"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS772)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS773L" title="If the patient had a urine white blood count result, enter the lowest value.">
Urine White Blood Count Lowest Value:</span>
</td><td>
<span id="NBS773"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS773)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient had a urine white blood count result, enter the unit of measure associated with the value." id="NBS774L" >
Urine White Blood Count Units:</span></td><td>
<span id="NBS774" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS774)"
codeSetNm="URINE_WBC_TEST_UNIT"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27011" name="Echocardiogram" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="40701008L" title="Indicate echocardiogram result.">
Echocardiogram result:</span></td><td>
<span id="40701008" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(40701008)"
codeSetNm="ECHOCARDIOGRAM_RESULT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Indicate echocardiogram result." id="40701008OthL">Other Echocardiogram result:</span></td>
<td> <span id="40701008Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(40701008Oth)"/></td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicate max coronary artery Z-score." id="373065002L">
Max coronary artery Z-score:</span>
</td>
<td>
<span id="373065002"/>
<nedss:view name="PageForm" property="pageClientVO.answer(373065002)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate cardiac dysfunction type." id="NBS703L" >
Cardiac dysfunction type:</span></td><td>
<span id="NBS703" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS703)"
codeSetNm="CARDIAC_DYSFUNCTION_TYPE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicate the date of first test showing coronary artery aneurysm or dilatation." id="NBS704L">Date of first test showing coronary artery aneurysm or dilatation:</span>
</td><td>
<span id="NBS704"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS704)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate mitral regurgitation type." id="18113_1L" >
Mitral regurgitation type:</span></td><td>
<span id="18113_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(18113_1)"
codeSetNm="MITRAL_REGURG_TYPE"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27012" name="Abdominal Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was abdominal imaging was completed for this patient?" id="441987005L" >
Was abdominal imaging done?:</span></td><td>
<span id="441987005" />
<nedss:view name="PageForm" property="pageClientVO.answer(441987005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="NBS705L" title="Indicate abdominal imaging type.">
Abdominal imaging type:</span></td><td>
<span id="NBS705" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS705)"
codeSetNm="ABD_IMAGING_TYPE_MIS"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="NBS706L" title="Indicate abdominal imaging results.">
Abdominal imaging results:</span></td><td>
<span id="NBS706" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS706)"
codeSetNm="ABD_IMAGING_RSLT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Indicate abdominal imaging results." id="NBS706OthL">Other Abdominal imaging results:</span></td>
<td> <span id="NBS706Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS706Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27013" name="Chest Imaging" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was chest imaging completed for this patient?" id="413815006L" >
Was chest imaging done?:</span></td><td>
<span id="413815006" />
<nedss:view name="PageForm" property="pageClientVO.answer(413815006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="LAB677L" title="Indicate the type of chest study performed. Please provide a response for each of the main test types (plain chest radiograph, chest CT Scan) and if test was not done please indicate so.">
Type of Chest Study:</span></td><td>
<span id="LAB677" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(LAB677)"
codeSetNm="CHEST_IMAGING_TYPE_MIS"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="LAB678L" title="Result of chest diagnostic testing">
Result of Chest Study:</span></td><td>
<span id="LAB678" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(LAB678)"
codeSetNm="CHEST_IMAGING_RSLT_MIS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Result of chest diagnostic testing" id="LAB678OthL">Other Result of Chest Study:</span></td>
<td> <span id="LAB678Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB678Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27015" name="SARS-COV-2 Testing Details" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;If multiple tests were performed for a given test type, enter the date for the first positive.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="RT-PCR Test Result" id="NBS718L" >
RT-PCR Test Result:</span></td><td>
<span id="NBS718" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS718)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="RT-PCR Test Date" id="NBS719L">RT-PCR Test Date:</span>
</td><td>
<span id="NBS719"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS719)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Antigen Test Result" id="NBS720L" >
Antigen Test Result:</span></td><td>
<span id="NBS720" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS720)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Antigen Test Date" id="NBS721L">Antigen Test Date:</span>
</td><td>
<span id="NBS721"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS721)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="IgG Test Result" id="NBS722L" >
IgG Test Result:</span></td><td>
<span id="NBS722" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS722)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="IgG Test Date" id="NBS723L">IgG Test Date:</span>
</td><td>
<span id="NBS723"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS723)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="IgM Test Result" id="NBS724L" >
IgM Test Result:</span></td><td>
<span id="NBS724" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS724)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="IgM Test Date" id="NBS725L">IgM Test Date:</span>
</td><td>
<span id="NBS725"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS725)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="IgA Test Result" id="NBS726L" >
IgA Test Result:</span></td><td>
<span id="NBS726" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS726)"
codeSetNm="TEST_RESULT_COVID"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="IgA Test Date" id="NBS727L">IgA Test Date:</span>
</td><td>
<span id="NBS727"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS727)"  />
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
