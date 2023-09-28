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
String tabId = "viewHepatitisCore";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Clinical Data","Diagnostic Tests","Hepatitis D Infection"};
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
<nedss:container id="NBS_INV_HEP_UI_5" name="Reason for Testing" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV575L" title="Listing of the reason(s) the subject was tested for the condition">
Reason for Testing (check all that apply):</span></td><td>
<span id="INV575" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV575)"
codeSetNm="PHVS_REASONFORTEST_HEPATITIS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Other reason(s) the patient was tested for hepatitis." id="INV901L">
Other Reason for Testing:</span>
</td>
<td>
<span id="INV901"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV901)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Clinical Data" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of diagnosis of condition being reported to public health system." id="INV136L">Diagnosis Date:</span>
</td><td>
<span id="INV136"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV136)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient symptomatic for the illness of interest?" id="INV576L" >
Is patient symptomatic?:</span></td><td>
<span id="INV576" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." id="INV137L">Illness Onset Date:</span>
</td><td>
<span id="INV137"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV137)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The time at which the disease or condition ends." id="INV138L">Illness End Date:</span>
</td><td>
<span id="INV138"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV138)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV139L" title="The length of time this person had this disease or condition.">
Illness Duration:</span>
</td><td>
<span id="INV139"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV139)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Unit of time used to describe the length of the illness or condition." id="INV140L" >
Illness Duration Units:</span></td><td>
<span id="INV140" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV140)"
codeSetNm="PHVS_DURATIONUNIT_CDC"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV143L" title="Subject's age at the onset of the disease or condition.">
Age at Onset:</span>
</td><td>
<span id="INV143"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV143)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The age units for an age." id="INV144L" >
Age at Onset Units:</span></td><td>
<span id="INV144" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV144)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="At the time of diagnosis, was the patient jaundiced?" id="INV578L" >
Was the patient jaundiced?:</span></td><td>
<span id="INV578" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV578)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient hospitalized as a result of this event?" id="INV128L" >
Was the patient hospitalized for this illness?:</span></td><td>
<span id="INV128" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV128)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="INV184L" title="The hospital associated with the investigation.">
Hospital:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV184)"/>
<span id="INV184">${PageForm.attributeMap.INV184SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Subject's admission date to the hospital for the condition covered by the investigation." id="INV132L">Admission Date:</span>
</td><td>
<span id="INV132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV132)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Subject's discharge date from the hospital for the condition covered by the investigation." id="INV133L">Discharge Date:</span>
</td><td>
<span id="INV133"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV133)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV134L" title="Subject's duration of stay at the hospital for the condition covered by the investigation.">
Total Duration of Stay in the Hospital (in days):</span>
</td><td>
<span id="INV134"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV134)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Assesses whether or not the patient is pregnant." id="INV178L" >
Is the patient pregnant?:</span></td><td>
<span id="INV178" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV178)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If the subject in pregnant, please enter the due date." id="INV579L">Due Date:</span>
</td><td>
<span id="INV579"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV579)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if the subject dies as a result of the illness." id="INV145L" >
Did the patient die from this illness?:</span></td><td>
<span id="INV145" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV145)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the subject’s death occurred." id="INV146L">Date of Death:</span>
</td><td>
<span id="INV146"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV146)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient aware s/he had hepatitis prior to lab testing?" id="INV650L" >
Was the patient aware s/he had hepatitis prior to lab testing?:</span></td><td>
<span id="INV650" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV650)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the subject have a provider of care for the condition?  This is any healthcare provider that monitors or treats the patient for the condition." id="INV651L" >
Does the patient have a provider of care for hepatitis?:</span></td><td>
<span id="INV651" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV651)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="INV182L" title="The physician associated with this case.">
Physician:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV182)"/>
<span id="INV182">${PageForm.attributeMap.INV182SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate whether the patient has diabetes" id="INV887L" >
Does the patient have diabetes?:</span></td><td>
<span id="INV887" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV887)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If subject has diabetes, date of diabetes diagnosis." id="INV842L">Diabetes Diagnosis Date:</span>
</td><td>
<span id="INV842"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV842)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEP_UI_6" name="Liver Enzyme Levels at Time of Diagnosis" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="1742_6L" title="Enter the ALT/SGPT result">
ALT [SGPT] Result:</span>
</td><td>
<span id="1742_6"/>
<nedss:view name="PageForm" property="pageClientVO.answer(1742_6)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the date of specimen collection of the ALT liver enzyme lab test result." id="INV826L">Specimen Collection Date (ALT):</span>
</td><td>
<span id="INV826"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV826)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV827L" title="Enter the upper limit normal value (numeric) for the ALT liver enzyme test result.">
Test Result Upper Limit Normal (ALT):</span>
</td><td>
<span id="INV827"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV827)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="11920_8L" title="Enter patient's AST/SGOT result">
AST [SGOT] Result:</span>
</td><td>
<span id="11920_8"/>
<nedss:view name="PageForm" property="pageClientVO.answer(11920_8)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the date of specimen collection of the AST liver enzyme lab test result." id="INV826bL">Specimen Collection Date (AST):</span>
</td><td>
<span id="INV826b"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV826b)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV827bL" title="Enter the upper limit normal value (numeric) for the AST liver enzyme test result.">
Test Result Upper Limit Normal (AST):</span>
</td><td>
<span id="INV827b"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV827b)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEP_UI_8" name="Diagnostic Test Results" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of specimen collection for total anti-HAV test result" id="LP38316_3_DTL">Specimen Collection Date (anti-HAV):</span>
</td><td>
<span id="LP38316_3_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38316_3_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Total antibody to Hepatitis A virus result" id="LP38316_3L" >
total anti-HAV Result:</span></td><td>
<span id="LP38316_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38316_3)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date of IgM anti-HAV test result" id="LP38318_9_DTL">Specimen Collection Date (IgM anti-HAV):</span>
</td><td>
<span id="LP38318_9_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38318_9_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="IgM antibody to Hepatitis A virus result" id="LP38318_9L" >
IgM anti-HAV Result:</span></td><td>
<span id="LP38318_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38318_9)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen Collection Date for HBsAg test" id="LP38331_2_DTL">Specimen Collection Date (HBsAg):</span>
</td><td>
<span id="LP38331_2_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38331_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Hepatitis B virus surface antigen result" id="LP38331_2L" >
HBsAg Result:</span></td><td>
<span id="LP38331_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38331_2)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date of total anti-HBc test result" id="LP38323_9_DTL">Specimen Collection Date (total anti-HBc):</span>
</td><td>
<span id="LP38323_9_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38323_9_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="total antibody to Hepatitis B core antigen result" id="LP38323_9L" >
total anti-HBc Result:</span></td><td>
<span id="LP38323_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38323_9)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date of IgM anti-HBc test result" id="LP38325_4_DTL">Specimen Collection Date (IgM anti-HBc):</span>
</td><td>
<span id="LP38325_4_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38325_4_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="IgM antibody to hepatitis B core antigen result" id="LP38325_4L" >
IgM anti-HBc Result:</span></td><td>
<span id="LP38325_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38325_4)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date of HBV DNA/NAT test" id="LP38320_5_DTL">Specimen Collection Date (HEP B DNA/NAT):</span>
</td><td>
<span id="LP38320_5_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38320_5_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Hepatitis B DNA-containing test (Nucleic Acid Test (NAT)) result" id="LP38320_5L" >
HEP B DNA/NAT Result:</span></td><td>
<span id="LP38320_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38320_5)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date of HBeAg test" id="LP38329_6_DTL">Specimen Collection Date (HBeAg):</span>
</td><td>
<span id="LP38329_6_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38329_6_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Hepatitis B virus e Antigen result" id="LP38329_6L" >
HBeAg Result:</span></td><td>
<span id="LP38329_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38329_6)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date of total anti-HCV" id="LP38332_0_DTL">Specimen Collection Date (total anti-HCV):</span>
</td><td>
<span id="LP38332_0_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38332_0_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Hepatitis C virus Ab result" id="LP38332_0L" >
total anti-HCV Result:</span></td><td>
<span id="LP38332_0" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38332_0)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If the antibody test to Hepatitis C Virus (anti-HCV) was positive or negative, enter the signal to cut-off ratio." id="INV841L">
anti-HCV signal to cut-off ratio:</span>
</td>
<td>
<span id="INV841"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV841)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date for supplemental anti-HCV assay" id="5199_5_DTL">Specimen Collection Date (supplemental anti-HCV assay):</span>
</td><td>
<span id="5199_5_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(5199_5_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="supplemental anti-HCV assay (e.g. RIBA) result" id="5199_5L" >
Supplemental anti-HCV Assay Result:</span></td><td>
<span id="5199_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(5199_5)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date for HCV RNA test" id="LP38335_3_DTL">Specimen Collection Date (HCV RNA):</span>
</td><td>
<span id="LP38335_3_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38335_3_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Hepatitis C virus RNA result" id="LP38335_3L" >
HCV RNA Result:</span></td><td>
<span id="LP38335_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38335_3)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date for total anti-HDV test" id="LP38345_2_DTL">Specimen Collection Date (total anti-HDV):</span>
</td><td>
<span id="LP38345_2_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38345_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Total Hepatitis D virus Ab result" id="LP38345_2L" >
anti-HDV Result:</span></td><td>
<span id="LP38345_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38345_2)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specimen collection date for total anti-HEV test" id="LP38350_2_DTL">Specimen Collection Date (total anti-HEV):</span>
</td><td>
<span id="LP38350_2_DT"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP38350_2_DT)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Total Hepatitis E virus Ab result" id="LP38350_2L" >
anti-HEV Result:</span></td><td>
<span id="LP38350_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP38350_2)"
codeSetNm="PNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Note: The question regarding a negative hepatitis-related tests refers to the condition being reported. If this is an acute hepatitis B case, indicate if the patient had a negative hepatitis B-related test in the previous 6 months.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a negative hepatitis-related test in the previous 6 months? For Hep B: Did patient have a negative HBsAg test in the previous 6 months? For Hep C: Did patient have a negative HCV antibody test in the previous 6 months?" id="INV832L" >
Did the patient have a negative hepatitis-related test in the previous 6 months?:</span></td><td>
<span id="INV832" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV832)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If the patient had a previous negative hepatitis test within 6 months, enter the date of the test." id="INV843L">Verified Test Date:</span>
</td><td>
<span id="INV843"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV843)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_2" name="Hepatitis D Infection" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient tested for Hepatitis D" id="INV840L" >
Was the patient tested for hepatitis D?:</span></td><td>
<span id="INV840" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV840)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a co-infection with Hepatitis D?" id="INV831L" >
Did the patient have a co-infection with hepatitis D?:</span></td><td>
<span id="INV831" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV831)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
