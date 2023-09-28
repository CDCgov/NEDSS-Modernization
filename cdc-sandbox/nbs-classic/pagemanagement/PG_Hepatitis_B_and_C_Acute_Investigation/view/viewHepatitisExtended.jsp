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
String [] sectionNames  = {"Contact with Case","Sexual and Drug Exposures","Exposures Prior to Onset","Hepatitis Treatment","Vaccination History"};
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
<nedss:container id="NBS_INV_HEPACBC_UI_5" name="Contact with a Case" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;The time period of interest differs for Acute Hepatitis B and C. For Hepatitis B, the time period is 6 weeks - 6 months prior to onset of of symptoms. For Hepatitis C, the time period is 2 weeks - 6 months prior to onset of symptoms.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the time period prior to the onset of symptoms, was the subject a contact of a person with confirmed or suspected hepatitis virus infection? For hepatitis B, the time period is 6 weeks to 6 months. For hepatitis C, it is 2 weeks to 6 months." id="INV602L" >
During the time period prior to onset, was patient a contact of a case?:</span></td><td>
<span id="INV602" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV602)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_7" name="Types of Contact" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient was a contact of a confirmed or suspected case, was the contact type sexual?" id="INV603_5L" >
Sexual (Contact Type):</span></td><td>
<span id="INV603_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_5)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the patient is a contact of a confirmed or suspected case, was the contact type  household non-sexual)?" id="INV603_3L" >
Household (Non-Sexual) (Contact Type):</span></td><td>
<span id="INV603_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV603_3)"
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
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_9" name="Sexual Exposures in Prior 6 Months" isHidden="F" classType="subSect" >

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

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the 6 months before symptom onset, how many:</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV605L" title="During the 6 months prior to the onset of symptoms, number of male sex partners the person had.">
Male Sex Partners Did the Patient Have:</span>
</td><td>
<span id="INV605"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV605)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV606L" title="During the 6 months prior to the onset of symptoms, number of female sex partners the person had.">
Female Sex Partners Did the Patient Have:</span>
</td><td>
<span id="INV606"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV606)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient ever treated for a sexually transmitted disease?" id="INV653bL" >
Was the patient ever treated for a sexually transmitted disease?:</span></td><td>
<span id="INV653b" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV653b)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV654L" title="If the subject was ever treated for a sexually-transmitted diases, in what year was the most recent treatment?">
If yes, in what year was the most recent treatment?:</span>
</td><td>
<span id="INV654"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV654)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_12" name="Blood Exposures Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;The time period of interest differs for Acute Hepatitis B and C. For Hepatitis B, the time period is 6 weeks - 6 months prior to onset of of symptoms. For Hepatitis C, the time period is 2 weeks - 6 months prior to onset of symptoms.</td></tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;During the time period prior to onset, did the patient:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, did the patient undergo hemodialysis? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV583L" >
Undergo Hemodialysis:</span></td><td>
<span id="INV583" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV583)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, did the patient have an accidental stick or puncture with a needle or other object contaminated with blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV655L" >
Have an Accidental Stick or Puncture With a Needle or Other Object Contaminated With Blood:</span></td><td>
<span id="INV655" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV655)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, did the patient receive blood or blood products (transfusion)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV580L" >
Receive Blood or Blood Products (Transfusion):</span></td><td>
<span id="INV580" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV580)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date the subject began receiving blood or blood products (transfusion) prior to symptom onset. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV614L">If Yes, Date of Transfusion:</span>
</td><td>
<span id="INV614"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV614)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, did the patient receive any IV infusions and/or injections in an outpatient setting? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV620L" >
Receive Any IV Infusions and/or Injections in the Outpatient Setting:</span></td><td>
<span id="INV620" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV620)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, did the patient have other exposure to someone else's blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV617L" >
Have Other Exposure to Someone Else's Blood:</span></td><td>
<span id="INV617" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV617)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="If patient had exposure to someone else's blood prior to symptom onset, specify the other blood exposure." id="INV898L">
Other Blood Exposure (Specify):</span>
</td>
<td>
<span id="INV898"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV898)"  />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, was the patient employed in a medical or dental field involving direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV590L" >
Was the patient employed in a medical or dental field involving contact with human blood?:</span></td><td>
<span id="INV590" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV590)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Subject's frequency of blood contact as an employee in a medical or dental field involving direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months" id="INV594L" >
If Yes, Frequency of Direct Blood Contact:</span></td><td>
<span id="INV594" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV594)"
codeSetNm="PHVS_BLOODCONTACTFREQUENCY_HEPATITIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, was the subject employed as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months.  For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV595L" >
Was the patient employed as a public safety worker having direct contact with human blood?:</span></td><td>
<span id="INV595" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV595)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Subject's frequency of blood contact as a public safety worker (fire fighter, law enforcement, or correctional officer) having direct contact with human blood. For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV596L" >
If Yes, Frequency of Direct Blood Contact:</span></td><td>
<span id="INV596" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV596)"
codeSetNm="PHVS_BLOODCONTACTFREQUENCY_HEPATITIS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_16" name="Tattooing/Drugs/Piercing" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;In the time period prior to onset:</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, did the patient receive a tattoo? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV597L" >
Did the patient receive a tattoo?:</span></td><td>
<span id="INV597" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV597)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV598L" title="Where was the tattooing performed (check all that apply)">
Where was the tattooing performed (check all that apply)?:</span></td><td>
<span id="INV598" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV598)"
codeSetNm="PHVS_TATTOOOBTAINEDFROM_HEPATITIS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="specify other location(s) where the patient received a tattoo" id="INV900L">
Other Location(s) Tattoo Received:</span>
</td>
<td>
<span id="INV900"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV900)"  />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the timeperiod prior to the onset of symptoms, did the subject inject drugs?" id="INV607L" >
Inject Drugs Not Prescribed By a Doctor:</span></td><td>
<span id="INV607" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV607)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the time period prior to the onset of symptoms, did the subject use street drugs but not inject?" id="INV608L" >
Use Street Drugs But Not Inject:</span></td><td>
<span id="INV608" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV608)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have any part of their body pierced (other than ear)?" id="INV622L" >
Did the patient have any part of their body pierced (other than ear)?:</span></td><td>
<span id="INV622" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV622)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV623L" title="Where was the piercing performed (check all that apply)">
Where was the piercing performed (check all that apply)?:</span></td><td>
<span id="INV623" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV623)"
codeSetNm="PHVS_TATTOOOBTAINEDFROM_HEPATITIS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Specify other location(s) where the patient received a piercing" id="INV899L">
Other Location(s) Piercing Received:</span>
</td>
<td>
<span id="INV899"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV899)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_18" name="Other Healthcare Exposure" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, did the patient have dental work or oral surgery? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV633L" >
Did the patient have dental work or oral surgery?:</span></td><td>
<span id="INV633" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV633)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, did the patient have surgery (other than oral surgery)? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV634L" >
Did the patient have surgery (other than oral surgery)?:</span></td><td>
<span id="INV634" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV634)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, was the patient hospitalized? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV635L" >
Was the patient hospitalized?:</span></td><td>
<span id="INV635" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV635)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, was the patient a resident of a long-term care facility? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV636L" >
Was the patient a resident of a long-term care facility?:</span></td><td>
<span id="INV636" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV636)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Prior to the onset of symptoms, was the patient incarcerated for longer than 24 hours? For Acute Hep B, the time period prior to onset of symptoms is 6 weeks - 6 months. For Acute Hep C, the time period prior to onset of symptoms is 2 weeks - 6 months." id="INV637L" >
Was the patient incarcerated for longer than 24 hours?:</span></td><td>
<span id="INV637" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV637)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_19" name="Incarceration Prior to Onset" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If patient was incarcerated, indicate if the incarceration type was prison." id="INV638_3L" >
Prison:</span></td><td>
<span id="INV638_3" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV638_3)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If patient was incarcerated, what type of facility?" id="INV638_1L" >
Jail:</span></td><td>
<span id="INV638_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV638_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If patient was incarcerated, indicate the type of facility?" id="INV638_2L" >
Juvenile Facility:</span></td><td>
<span id="INV638_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV638_2)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_20" name="Incarceration More than 6 Months" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During his/her lifetime, was the patient EVER incarcerated for more than 6 months?" id="INV639L" >
Was the patient ever incarcerated for longer than 6 months?:</span></td><td>
<span id="INV639" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV639)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV640L" title="Enter the 4-digit year the patient was most recently incarcerated for longer than 6 months.">
If yes, what year was the most recent incarceration?:</span>
</td><td>
<span id="INV640"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV640)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV641L" title="If the patient was incarcerated for longer than 6 months, enter the length of time of incarceration in months">
If yes, for how long (answer in months)?:</span>
</td><td>
<span id="INV641"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV641)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_22" name="Treatment Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Has the subject ever received medication for the type of Hepatitis being reported?" id="INV652L" >
Has the patient received medication for the type of hepatitis being reported?:</span></td><td>
<span id="INV652" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV652)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPACBC_UI_24" name="Hepatitis B Vaccination" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient ever receive hepatitis B vaccine?" id="VAC126L" >
Did the patient ever receive hepatitis B vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC132L" title="Total number of doses of vaccine the patient received for this condition (e.g. if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine).">
If yes, how many doses?:</span>
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
<span title="Was the patient tested for antibody to HBsAg (anti-HBS) within 1-2 months after the last does of vaccine?" id="HEP190L" >
Was patient tested for antibody to HBsAg (anti-HBs) within 1-2 mos after last dose?:</span></td><td>
<span id="HEP190" />
<nedss:view name="PageForm" property="pageClientVO.answer(HEP190)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the serum anti-HBs &gt;= 10ml U/ml?  (Answer 'Yes' if lab result reported as positive or reactive.)" id="HEP191L" >
Was the serum anti-HBs &gt;= 10ml U/ml?:</span></td><td>
<span id="HEP191" />
<nedss:view name="PageForm" property="pageClientVO.answer(HEP191)"
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
