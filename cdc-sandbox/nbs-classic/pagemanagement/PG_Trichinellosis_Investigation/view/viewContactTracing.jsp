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
String tabId = "viewContactTracing";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Contact Investigation","Retired Data Elements"};
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
<nedss:container id="NBS_UI_28" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc." id="NBS055L" >
Contact Investigation Priority:</span></td><td>
<span id="NBS055" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS055)"
codeSetNm="NBS_PRIORITY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period." id="NBS056L">Infectious Period From:</span>
</td><td>
<span id="NBS056"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS056)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period." id="NBS057L">Infectious Period To:</span>
</td><td>
<span id="NBS057"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS057)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The status of the contact investigation." id="NBS058L" >
Contact Investigation Status:</span></td><td>
<span id="NBS058" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS058)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation." id="NBS059L">
Contact Investigation Comments:</span>
</td>
<td>
<span id="NBS059"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS059)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101000" name="Legacy Animal Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the case have contact with an animal?" id="INV667L" >
(Legacy) Did the case have contact with an animal?:</span></td><td>
<span id="INV667" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV667)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="FDD_Q_32L" title="Animal contact by type of animal">
(Legacy) Animal Type:</span></td><td>
<span id="FDD_Q_32" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(FDD_Q_32)"
codeSetNm="PHVSFB_ANIMALST"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other, please specify other type of animal" id="FDD_Q_243L">
(Legacy) If Other, please specify other type of animal:</span>
</td>
<td>
<span id="FDD_Q_243"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_243)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other Amphibian, please specify other type of amphibian" id="FDD_Q_295L">
(Legacy) If Other Amphibian, please specify other type of amphibian:</span>
</td>
<td>
<span id="FDD_Q_295"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_295)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other Reptile, please specify other type of reptile" id="FDD_Q_296L">
(Legacy) If Other Reptile, please specify other type of reptile:</span>
</td>
<td>
<span id="FDD_Q_296"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_296)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other Mammal, please specify other type of mammal" id="FDD_Q_374L">
(Legacy) If Other Mammal, please specify other type of mammal:</span>
</td>
<td>
<span id="FDD_Q_374"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_374)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="What was the name of the farm, ranch, petting zoo, or other setting that has farm animals?" id="FDD_Q_33L">
(Legacy) Animal Contact Location:</span>
</td>
<td>
<span id="FDD_Q_33"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_33)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient acquire a pet prior to onset of illness?" id="FDD_Q_34L" >
(Legacy) Did the patient acquire a pet prior to onset of illness?:</span></td><td>
<span id="FDD_Q_34" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_34)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Applicable incubation period for this illness" id="FDD_Q_244L">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<span id="FDD_Q_244"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_244)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101001" name="Legacy Underlying Conditions" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="FDD_Q_233L" title="Did patient have any of the following underlying conditions?">
(Legacy) Did patient have any of the following underlying conditions?:</span></td><td>
<span id="FDD_Q_233" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(FDD_Q_233)"
codeSetNm="PHVSFB_DISEASES"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other Prior Illness, please specify" id="FDD_Q_234L">
(Legacy) If Other Prior Illness, please specify:</span>
</td>
<td>
<span id="FDD_Q_234"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_234)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If Diabetes Mellitus, specify whether on insulin" id="FDD_Q_235L" >
(Legacy) If Diabetes Mellitus, specify whether on insulin:</span></td><td>
<span id="FDD_Q_235" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_235)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Gastric Surgery, please specify type" id="FDD_Q_237L">
(Legacy) If Gastric Surgery, please specify type:</span>
</td>
<td>
<span id="FDD_Q_237"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_237)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Hematologic Disease, please specify type" id="FDD_Q_238L">
(Legacy) If Hematologic Disease, please specify type:</span>
</td>
<td>
<span id="FDD_Q_238"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_238)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Immunodeficiency, please specify type:" id="FDD_Q_239L">
(Legacy) If Immunodeficiency, please specify type::</span>
</td>
<td>
<span id="FDD_Q_239"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_239)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other Liver Disease, please specify type" id="FDD_Q_240L">
(Legacy) If Other Liver Disease, please specify type:</span>
</td>
<td>
<span id="FDD_Q_240"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_240)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other Malignancy, please specify type" id="FDD_Q_241L">
(Legacy) If Other Malignancy, please specify type:</span>
</td>
<td>
<span id="FDD_Q_241"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_241)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other Renal Disease, please specify" id="FDD_Q_242L">
(Legacy) If Other Renal Disease, please specify:</span>
</td>
<td>
<span id="FDD_Q_242"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_242)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Organ Transplant, please specify organ" id="FDD_Q_236L">
(Legacy) If Organ Transplant, please specify organ:</span>
</td>
<td>
<span id="FDD_Q_236"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_236)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA101002" name="Legacy Related Cases" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient know of other similarly ill persons?" id="FDD_Q_77L" >
(Legacy) Does the patient know of other similarly ill persons?:</span></td><td>
<span id="FDD_Q_77" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_77)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If Yes, did the health department collect contact information about other similarly ill persons and investigate further?" id="FDD_Q_78L" >
(Legacy) If Yes, did the health department collect contact information about other similarly ill persons and:</span></td><td>
<span id="FDD_Q_78" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_78)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Are there other cases related to this one?" id="FDD_Q_79L" >
(Legacy) Are there other cases related to this one?:</span></td><td>
<span id="FDD_Q_79" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_79)"
codeSetNm="PHVSFB_EPIDEMGY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102002" name="Legacy Suspect Food" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="FDD_Q_139L" title="What suspect foods did the patient eat?">
(Legacy) What suspect foods did the patient eat?:</span></td><td>
<span id="FDD_Q_139" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(FDD_Q_139)"
codeSetNm="PHVSFB_PORKONOT"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="FDD_Q_144L" title="Where was the suspect (Pork) meat obtained?">
(Legacy) Where was the suspect (Pork) meat obtained?:</span></td><td>
<span id="FDD_Q_144" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(FDD_Q_144)"
codeSetNm="PHVSFB_SOURCEMT"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Where was the suspect (Pork) meat obtained?" id="FDD_Q_144OthL">Other (Legacy) Where was the suspect (Pork) meat obtained?:</span></td>
<td> <span id="FDD_Q_144Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_144Oth)"/></td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="FDD_Q_154L" title="Where was the suspect (Non-Pork) meat obtained?">
(Legacy) Where was the suspect (Non-Pork) meat obtained?:</span></td><td>
<span id="FDD_Q_154" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(FDD_Q_154)"
codeSetNm="PHVSFB_SOURCEMT"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Where was the suspect (Non-Pork) meat obtained?" id="FDD_Q_154OthL">Other (Legacy) Where was the suspect (Non-Pork) meat obtained?:</span></td>
<td> <span id="FDD_Q_154Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_154Oth)"/></td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102000" name="Legacy Food Handler" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did patient work as a food handler after onset of illness?" id="FDD_Q_8L" >
(Legacy) Did patient work as a food handler after onset of illness?:</span></td><td>
<span id="FDD_Q_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_8)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="What was last date worked as a food handler after onset of illness?" id="FDD_Q_9L">(Legacy) What was last date worked as a food handler after onset of illness?:</span>
</td><td>
<span id="FDD_Q_9"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_9)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Where was patient a food handler?" id="FDD_Q_10L">
(Legacy) Where was patient a food handler?:</span>
</td>
<td>
<span id="FDD_Q_10"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_10)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA102001" name="Legacy Travel History" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Applicable incubation period for this illness" id="FDD_Q_12L">
(Legacy) Applicable incubation period for this illness:</span>
</td>
<td>
<span id="FDD_Q_12"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_12)" />
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="FDD_Q_13L" title="What was the purpose of the travel?">
(Legacy) What was the purpose of the travel?:</span></td><td>
<span id="FDD_Q_13" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(FDD_Q_13)"
codeSetNm="PHVSFB_TRAVELTT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If Other, please specify other purpose of travel" id="FDD_Q_14L">
(Legacy) If Other, please specify other purpose of travel:</span>
</td>
<td>
<span id="FDD_Q_14"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_14)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If more than 3 destinations, specify details here" id="FDD_Q_20L">
(Legacy) If more than 3 destinations, specify details here:</span>
</td>
<td>
<span id="FDD_Q_20"/>
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_20)" />
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
