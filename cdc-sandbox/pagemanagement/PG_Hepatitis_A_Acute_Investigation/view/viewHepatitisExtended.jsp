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
String tabId = "viewHepatitisExtended";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Hepatitis A","Vaccination History"};
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
<nedss:container id="NBS_INV_HEPA_UI_3" name="Epidemiologic Link" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If this case has a diagnosis of Hepatitis A that has not been serologically confirmed, is there an epidemiologic link between this pataient and a laboratory-confirmed heptatitis A case?" id="INV217L" >
Is there an epidemiologic link between this patient and a laboratory-confirmed case of Hepatitis A?:</span></td><td>
<span id="INV217" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV217)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the 2-6 weeks prior to the onset of symptoms, was the subject a contact of a person with confirmed or suspected hepatitis A virus infection?" id="INV602L" >
During the 2-6 weeks prior to onset, was patient a contact of a confirmed or suspected case?:</span></td><td>
<span id="INV602" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV602)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_4" name="Types of Contact" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;If yes, was the contact:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient is a contact of a confirmed or suspected case, is the contact a household member (non-sexual)?" id="INV603_3L" >
Household Member (Non-Sexual) (Contact Type):</span></td><td>
<span id="INV603_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_3)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact a sex partner?" id="INV603_5L" >
Sex Partner (Contact Type):</span></td><td>
<span id="INV603_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_5)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact a child cared for by this patient?" id="INV603_2L" >
Child Cared For By This Patient (Contact Type):</span></td><td>
<span id="INV603_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_2)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact a babysitter of this patient?" id="INV603_1L" >
Babysitter of This Patient (Contact Type):</span></td><td>
<span id="INV603_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact a playmate?" id="INV603_4L" >
Playmate (Contact Type):</span></td><td>
<span id="INV603_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_4)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact type other?" id="INV603_6L" >
Other (Contact Type):</span></td><td>
<span id="INV603_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_6)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If contact type with a confirmed or suspected case was 'other', specify other contact type." id="INV897L">
Other Contact Type (Specify):</span>
</td>
<td>
<span id="INV897"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV897)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_6" name="Daycare Exposures 2-6 weeks prior to onset" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Was the patient:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the 2-6 weeks prior to the onset of symptoms, was the patient a child or employee in daycare center, nursery, or preschool?" id="INV615L" >
A Child or Employee in a Day Care Center/Nursery/Preschool:</span></td><td>
<span id="INV615" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV615)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the 2-6 weeks prior to the onset of symptoms, was the patient a household contact of a child or employee in a daycare center, nursery, or preschool?" id="INV616L" >
A Household Contact of a Child or Employee in a Day Care/Nursery/Preschool:</span></td><td>
<span id="INV616" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV616)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient was a child or employee or  a household contact of a child or employee in a child care facility, was there an identified hepatitis case in the childcare facility?" id="INV896L" >
If yes for either of these, was there an identified hepatitis case in the child care facility?:</span></td><td>
<span id="INV896" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV896)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_8" name="Sexual Exposures 2-6 weeks prior to onset" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Please enter the sexual preference of the patient." id="INV592L" >
What is the sexual preference of the patient?:</span></td><td>
<span id="INV592" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV592)"
codeSetNm="PHVS_SEXUALPREFERENCE_NETSS"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Note: If 0 is selected on the form, enter 0; if 1 is selected on the form, enter 1; if 2-5 is selected on the form, enter 2; if &gt;5 is selected on the form, enter 6.</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV605L" title="During the time period prior to the onset of symptoms, number of male sex partners the person had.">
How many male sex partners did the patient have?:</span>
</td><td>
<span id="INV605"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV605)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV606L" title="During the time period prior to the onset of symptoms, number of female sex partners the person had.">
How many female sex partners did the patient have?:</span>
</td><td>
<span id="INV606"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV606)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_10" name="Drug Exposures 2-6 weeks prior to onset" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the time period prior to the onset of symptoms, did the subject inject drugs?" id="INV607L" >
Did the patient inject drugs?:</span></td><td>
<span id="INV607" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV607)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the time period prior to the onset of symptoms, did the subject use street drugs but not inject?" id="INV608L" >
Did the patient use street drugs but not inject?:</span></td><td>
<span id="INV608" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV608)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_11" name="Travel Exposures Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the 2-6 weeks prior to the onset of symptoms, did the subject travel or live outside the U.S.A. or Canada?" id="TRAVEL30L" >
In the 2-6 weeks prior to onset, did the patient travel or live outside of the US or Canada?:</span></td><td>
<span id="TRAVEL30" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL30)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="TRAVEL31L" title="The country(s) to which the subject traveled or lived (outside the U.S.A. or Canada) prior to symptom onset.">
If the patient traveled, where (select all that apply)?:</span></td><td>
<span id="TRAVEL31" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(TRAVEL31)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Principal Reason for Travel" id="TRAVEL16L" >
Principal Reason for Travel:</span></td><td>
<span id="TRAVEL16" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL16)"
codeSetNm="PHVS_TRAVELREASON_HEPATITISA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the 3 months prior to the onset of symptoms, did anyone in the subject's household travel outside the U.S.A. or Canada?" id="TRAVEL32L" >
During 3 months prior to onset, did anyone in patient's household travel outside of US or Canada?:</span></td><td>
<span id="TRAVEL32" />
<nedss:view name="PageForm" property="pageClientVO.answer(TRAVEL32)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="TRAVEL33L" title="The country(s) to which anyone in the subject's household traveled (outside the U.S.A. or Canada) prior to symptom onset.">
If someone in patient's household traveled, where (select all that apply)?:</span></td><td>
<span id="TRAVEL33" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(TRAVEL33)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_12" name="Outbreak Association" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the subject suspected as being part of a common-source outbreak?" id="INV618L" >
Is the patient suspected as being part of a common-source outbreak?:</span></td><td>
<span id="INV618" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV618)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If outbreak assoicated, was the patient associated with a foodborne outbreak that is associated with an infected food handler?" id="INV609L" >
Was the outbreak Foodborne - Associated with Infected Food Handler?:</span></td><td>
<span id="INV609" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV609)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient is associated with an outbreak, is the patient associated with a foodborne outbreak that is not associated with an infected food handler?" id="INV610L" >
Was the outbreak Foodborne - NOT Associated With an Infected Food Handler?:</span></td><td>
<span id="INV610" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV610)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Food item with which the foodborne outbreak is associated." id="INV611L">
Specify Food Item:</span>
</td>
<td>
<span id="INV611"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV611)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If assoicated with an outbreak, was the patient associated with a waterborne outbreak?" id="INV612L" >
Was the outbreak waterborne?:</span></td><td>
<span id="INV612" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV612)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If associated with an outbreak, is patient associated with an outbreak that does not have an identifed source?" id="INV613L" >
Was the oubreak source not identified?:</span></td><td>
<span id="INV613" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV613)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the 2 weeks prior to the onset of symptoms or while ill, was the subject employed as a food handler?" id="INV621L" >
Was the patient employed as a food handler during the TWO WEEKS prior to onset or while ill?:</span></td><td>
<span id="INV621" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV621)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPA_UI_14" name="Hepatitis Containing Vaccine" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did patient ever receive a hepatitis-containing vaccine? If this is a hepatitis A investigation, answer this question regarding hepatitis A-containing vaccine. If it is a hepatitis B investigation, answer this question regarding hepatitis B-containing vaccine." id="VAC126L" >
Did patient ever receive a hepatitis A-containing vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC132L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
How many doses?:</span>
</td><td>
<span id="VAC132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC132)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC142bL" title="Enter the 4 digit year of when the last dose of vaccine for the condition being investigated was received.">
In what year was the last dose received?:</span>
</td><td>
<span id="VAC142b"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC142b)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Has the patient ever received immune globulin for this condition?" id="VAC143L" >
Has the patient ever received immune globulin?:</span></td><td>
<span id="VAC143" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC143)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date the patient received the last dose of immune globulin. For example, if the patient received the last dose in 2012, enter 01/01/2012. To convey an unknown date, enter 00/00/0000." id="VAC144L">When was the last dose of IG received?:</span>
</td><td>
<span id="VAC144"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC144)"  />
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
