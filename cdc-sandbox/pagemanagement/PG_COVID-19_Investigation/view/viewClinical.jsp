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
String tabId = "viewClinical";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Information Source","Signs &amp; Symptoms","Medical History","Vaccination"};
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
<nedss:container id="NBS_UI_GA25014" name="Information Source Details" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="NBS553L" title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.).">
Information Source for Clinical Information (check all that apply):</span></td><td>
<span id="NBS553" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS553)"
codeSetNm="INFO_SOURCE_COVID"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Indicate the source(s) of information (e.g., symptoms, clinical course, past medical history, social history, etc.)." id="NBS553OthL">Other Information Source for Clinical Information (check all that apply):</span></td>
<td> <span id="NBS553Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(NBS553Oth)"/></td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21003" name="Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Were symptoms present during course of illness?" id="INV576L" >
Symptoms present during course of illness:</span></td><td>
<span id="INV576" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV576)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." id="INV137L">Date of Symptom Onset:</span>
</td><td>
<span id="INV137"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV137)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The time at which the disease or condition ends." id="INV138L">Date of Symptom Resolution:</span>
</td><td>
<span id="INV138"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV138)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Status of reported symptom(s)." id="NBS555L" >
If symptomatic, symptom status:</span></td><td>
<span id="NBS555" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS555)"
codeSetNm="SYMPTOM_STATUS_COVID"/>
</td> </tr>

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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21016" name="Clinical Findings" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have pneumonia?" id="233604007L" >
Did the patient develop pneumonia?:</span></td><td>
<span id="233604007" />
<nedss:view name="PageForm" property="pageClientVO.answer(233604007)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have acute respiratory distress syndrome?" id="67782005L" >
Did the patient have acute respiratory distress syndrome?:</span></td><td>
<span id="67782005" />
<nedss:view name="PageForm" property="pageClientVO.answer(67782005)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have an abnormal chest X-ray?" id="168734001L" >
Did the patient have an abnormal chest X-ray?:</span></td><td>
<span id="168734001" />
<nedss:view name="PageForm" property="pageClientVO.answer(168734001)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have an abnormal EKG?" id="102594003L" >
Did the patient have an abnormal EKG?:</span></td><td>
<span id="102594003" />
<nedss:view name="PageForm" property="pageClientVO.answer(102594003)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have another diagnosis/etiology for their illness?" id="NBS546L" >
Did the patient have another diagnosis/etiology for their illness?:</span></td><td>
<span id="NBS546" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS546)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6L">
Secondary Diagnosis Description 1:</span>
</td>
<td>
<span id="81885_6"/>
<nedss:view name="PageForm" property="pageClientVO.answer(81885_6)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6_2L">
Secondary Diagnosis Description 2:</span>
</td>
<td>
<span id="81885_6_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(81885_6_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient had another diagnosis/etiology for their illness, specify the diagnosis or etiology" id="81885_6_3L">
Secondary Diagnosis Description 3:</span>
</td>
<td>
<span id="81885_6_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(81885_6_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the patient had other clinical findings associated with the illness being reported" id="NBS776L" >
Other Clinical Finding:</span></td><td>
<span id="NBS776" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS776)"
codeSetNm="YES_NO_UNKNOWN_NA"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Specify other clinical finding" id="NBS776_OTHL">
Other Clinical Finding Specify:</span>
</td>
<td>
<span id="NBS776_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS776_OTH)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35003" name="Clinical Treatments" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive mechanical ventilation (MV)/intubation?" id="NBS673L" >
Did the patient receive mechanical ventilation (MV)/intubation?:</span></td><td>
<span id="NBS673" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS673)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV547L" title="Total days with mechanical ventilation.">
Total days with Mechanical Ventilation:</span>
</td><td>
<span id="INV547"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV547)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive ECMO?" id="233573008L" >
Did the patient receive ECMO?:</span></td><td>
<span id="233573008" />
<nedss:view name="PageForm" property="pageClientVO.answer(233573008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS780L" title="Total days with Extracorporeal Membrane Oxygenation">
Total days with ECMO:</span>
</td><td>
<span id="NBS780"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS780)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicator for other treatment type specify indicator" id="NBS781L" >
Other Treatment Type?:</span></td><td>
<span id="NBS781" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS781)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If the treatment type is Other, specify the treatment." id="NBS389L">
Other Treatment Specify:</span>
</td>
<td>
<span id="NBS389"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS389)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS782L" title="Total days with Other Treatment Type">
Total days with Other Treatment Type:</span>
</td><td>
<span id="NBS782"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS782)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21002" name="Symptom Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have fever &gt;100.4F (38C)c?" id="386661006L" >
Fever &gt;100.4F (38C):</span></td><td>
<span id="386661006" />
<nedss:view name="PageForm" property="pageClientVO.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV202L" title="What was the patients highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature:</span>
</td><td>
<span id="INV202"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV202)"  />
<span id="INV202UNIT" /><nedss:view name="PageForm" property="pageClientVO.answer(INV202Unit)" codeSetNm="PHVS_TEMP_UNIT" />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have subjective fever (felt feverish)?" id="103001002L" >
Subjective fever (felt feverish):</span></td><td>
<span id="103001002" />
<nedss:view name="PageForm" property="pageClientVO.answer(103001002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have chills?" id="28376_2L" >
Chills:</span></td><td>
<span id="28376_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(28376_2)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have rigors?" id="38880002L" >
Rigors:</span></td><td>
<span id="38880002" />
<nedss:view name="PageForm" property="pageClientVO.answer(38880002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have muscle aches (myalgia)?" id="68962001L" >
Muscle aches (myalgia):</span></td><td>
<span id="68962001" />
<nedss:view name="PageForm" property="pageClientVO.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient  have runny nose (rhinorrhea)?" id="82272006L" >
Runny nose (rhinorrhea):</span></td><td>
<span id="82272006" />
<nedss:view name="PageForm" property="pageClientVO.answer(82272006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a sore throat?" id="267102003L" >
Sore Throat:</span></td><td>
<span id="267102003" />
<nedss:view name="PageForm" property="pageClientVO.answer(267102003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience new olfactory disorder?" id="44169009L" >
New Olfactory Disorder:</span></td><td>
<span id="44169009" />
<nedss:view name="PageForm" property="pageClientVO.answer(44169009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience new taste disorder?" id="36955009L" >
New Taste Disorder:</span></td><td>
<span id="36955009" />
<nedss:view name="PageForm" property="pageClientVO.answer(36955009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have headache?" id="25064002L" >
Headache:</span></td><td>
<span id="25064002" />
<nedss:view name="PageForm" property="pageClientVO.answer(25064002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have new confusion or change in mental status?" id="419284004L" >
New confusion or change in mental status?:</span></td><td>
<span id="419284004" />
<nedss:view name="PageForm" property="pageClientVO.answer(419284004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have fatigue or malaise?" id="271795006L" >
Fatigue or malaise:</span></td><td>
<span id="271795006" />
<nedss:view name="PageForm" property="pageClientVO.answer(271795006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Inability to Wake or Stay Awake" id="NBS793L" >
Inability to Wake or Stay Awake:</span></td><td>
<span id="NBS793" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS793)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a Cough (new onset or worsening of chronic cough)?" id="49727002L" >
Cough (new onset or worsening of chronic cough):</span></td><td>
<span id="49727002" />
<nedss:view name="PageForm" property="pageClientVO.answer(49727002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience wheezing?" id="56018004L" >
Wheezing:</span></td><td>
<span id="56018004" />
<nedss:view name="PageForm" property="pageClientVO.answer(56018004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have shortness of breath (dyspnea)?" id="267036007L" >
Shortness of Breath (dyspnea):</span></td><td>
<span id="267036007" />
<nedss:view name="PageForm" property="pageClientVO.answer(267036007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience difficulty breathing?" id="230145002L" >
Difficulty Breathing:</span></td><td>
<span id="230145002" />
<nedss:view name="PageForm" property="pageClientVO.answer(230145002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have pale, gray, or blue colored skin, lips, or nail beds, depending on skin tone?" id="3415004L" >
Pale/gray/blue skin/lips/nail beds?:</span></td><td>
<span id="3415004" />
<nedss:view name="PageForm" property="pageClientVO.answer(3415004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience chest pain?" id="29857009L" >
Chest Pain:</span></td><td>
<span id="29857009" />
<nedss:view name="PageForm" property="pageClientVO.answer(29857009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Persistent Pain or Pressure in Chest" id="NBS794L" >
Persistent Pain or Pressure in Chest:</span></td><td>
<span id="NBS794" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS794)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have nausea?" id="16932000L" >
Nausea:</span></td><td>
<span id="16932000" />
<nedss:view name="PageForm" property="pageClientVO.answer(16932000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience vomiting?" id="422400008L" >
Vomiting:</span></td><td>
<span id="422400008" />
<nedss:view name="PageForm" property="pageClientVO.answer(422400008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have abdominal pain or tenderness?" id="21522001L" >
Abdominal Pain or Tenderness:</span></td><td>
<span id="21522001" />
<nedss:view name="PageForm" property="pageClientVO.answer(21522001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have diarrhea (=3 loose/looser than normal stools/24hr period)?" id="62315008L" >
Diarrhea (=3 loose/looser than normal stools/24hr period):</span></td><td>
<span id="62315008" />
<nedss:view name="PageForm" property="pageClientVO.answer(62315008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did patient have loss of appetite?" id="79890006L" >
Loss of appetite:</span></td><td>
<span id="79890006" />
<nedss:view name="PageForm" property="pageClientVO.answer(79890006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the patient had other symptom(s) not otherwise specified." id="NBS338L" >
Other symptom(s)?:</span></td><td>
<span id="NBS338" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS338)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Specify other signs and symptoms." id="NBS338_OTHL">
Other Symptoms:</span>
</td>
<td>
<span id="NBS338_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS338_OTH)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21004" name="Symptom Notes" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Notes pertaining to the symptoms indicated for this case." id="NBS337L">
Symptom Notes:</span>
</td>
<td>
<span id="NBS337"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS337)"  />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21009" name="Pre-Existing Conditions" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of pre-existing medical conditions?" id="102478008L" >
Pre-existing medical conditions?:</span></td><td>
<span id="102478008" />
<nedss:view name="PageForm" property="pageClientVO.answer(102478008)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA21008" name="Medical History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have diabetes mellitus?" id="73211009L" >
Diabetes Mellitus:</span></td><td>
<span id="73211009" />
<nedss:view name="PageForm" property="pageClientVO.answer(73211009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Before your infection, did a health care provider ever tell you that you had high blood pressure (hypertension)?" id="ARB017L" >
Hypertension:</span></td><td>
<span id="ARB017" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB017)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the patient severely obese (BMI &gt;= 40)?" id="414916001L" >
Severe Obesity (BMI &gt;=40):</span></td><td>
<span id="414916001" />
<nedss:view name="PageForm" property="pageClientVO.answer(414916001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of cardiovascular disease?" id="128487001L" >
Cardiovascular disease:</span></td><td>
<span id="128487001" />
<nedss:view name="PageForm" property="pageClientVO.answer(128487001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of chronic renal disease?" id="709044004L" >
Chronic Renal disease:</span></td><td>
<span id="709044004" />
<nedss:view name="PageForm" property="pageClientVO.answer(709044004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of chronic liver disease?" id="328383001L" >
Chronic Liver disease:</span></td><td>
<span id="328383001" />
<nedss:view name="PageForm" property="pageClientVO.answer(328383001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?" id="413839001L" >
Chronic Lung Disease (asthma/emphysema/COPD):</span></td><td>
<span id="413839001" />
<nedss:view name="PageForm" property="pageClientVO.answer(413839001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have any history of other chronic disease?" id="NBS662L" >
Other Chronic Diseases:</span></td><td>
<span id="NBS662" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS662)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify the other chronic disease(s)." id="NBS662_OTHL">
Specify Other Chronic Diseases:</span>
</td>
<td>
<span id="NBS662_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS662_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have any other underlying conditions or risk behaviors?" id="NBS677L" >
Other Underlying Condition or Risk Behavior:</span></td><td>
<span id="NBS677" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS677)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If patient had an other underlying condition or risk behavior, specify the condition or behavior." id="NBS677_OTHL">
Specify Other Underlying Condition or Risk Behavior:</span>
</td>
<td>
<span id="NBS677_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS677_OTH)" />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of an immunocompromised/immunosuppressive condition?" id="370388006L" >
Immunosuppressive Condition:</span></td><td>
<span id="370388006" />
<nedss:view name="PageForm" property="pageClientVO.answer(370388006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have an autoimmune disease or condition?" id="85828009L" >
Autoimmune Condition:</span></td><td>
<span id="85828009" />
<nedss:view name="PageForm" property="pageClientVO.answer(85828009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the patient a current smoker?" id="65568007L" >
Current smoker:</span></td><td>
<span id="65568007" />
<nedss:view name="PageForm" property="pageClientVO.answer(65568007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the patient a former smoker?" id="8517006L" >
Former smoker:</span></td><td>
<span id="8517006" />
<nedss:view name="PageForm" property="pageClientVO.answer(8517006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient engage in substance abuse or misuse?" id="NBS676L" >
Substance Abuse or Misuse:</span></td><td>
<span id="NBS676" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS676)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of neurologic/neurodevelopmental/intellectual disability, physical, vision or hearing impairment?" id="110359009L" >
Disability:</span></td><td>
<span id="110359009" />
<nedss:view name="PageForm" property="pageClientVO.answer(110359009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671L">
Specify Disability 1:</span>
</td>
<td>
<span id="NBS671"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS671)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671_2L">
Specify Disability 2:</span>
</td>
<td>
<span id="NBS671_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS671_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If disability indicated as a risk factor, indicate type of disability (neurologic, neurodevelopmental, intellectual, physical, vision or hearing impairment)" id="NBS671_3L">
Specify Disability 3:</span>
</td>
<td>
<span id="NBS671_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS671_3)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a psychological or psychiatric condition?" id="74732009L" >
Psychological or Psychiatric Condition:</span></td><td>
<span id="74732009" />
<nedss:view name="PageForm" property="pageClientVO.answer(74732009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691L">
Specify Psychological or Psychiatric Condition 1:</span>
</td>
<td>
<span id="NBS691"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS691)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691_2L">
Specify Psychological or Psychiatric Condition 2:</span>
</td>
<td>
<span id="NBS691_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS691_2)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If psychological/psychiatric condition indicated as a risk factor, indicate type of psychological/psychiatric condition." id="NBS691_3L">
Specify Psychological or Psychiatric Condition 3:</span>
</td>
<td>
<span id="NBS691_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS691_3)" />
</td> </tr>

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

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAR159L" title="If the case-patient was pregnant at time of illness onset, specify the number of weeks gestation at onset of illness (1-45 weeks).">
Number of Weeks Gestation at Onset of Illness:</span>
</td><td>
<span id="VAR159"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAR159)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the case-patient was pregnant at time of illness onset, indicate trimester of gestation at time of disease." id="VAR160L" >
Trimester at Onset of Illness:</span></td><td>
<span id="VAR160" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAR160)"
codeSetNm="PHVS_PREG_TRIMESTER"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA35004" name="Vaccination Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did subject ever receive a COVID-containing vaccine?" id="VAC126L" >
Did the patient ever received a COVID-containing vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC140L" title="Number of vaccine doses against this disease prior to illness onset">
Vaccination Doses Prior to Onset:</span>
</td><td>
<span id="VAC140"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC140)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of last vaccine dose against this disease prior to illness onset" id="VAC142L">Date of Last Dose Prior to Illness Onset:</span>
</td><td>
<span id="VAC142"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC142)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was subject vaccinated as recommended by the Advisory Committee on Immunization Practices (ACIP)?" id="VAC148L" >
Vaccinated per ACIP Recommendations:</span></td><td>
<span id="VAC148" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Reason subject not vaccinated as recommended by ACIP" id="VAC149L" >
Reason Not Vaccinated Per ACIP Recommendations:</span></td><td>
<span id="VAC149" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC149)"
codeSetNm="VACCINE_NOT_GIVEN_REASON_COVID19"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Reason subject not vaccinated as recommended by ACIP" id="VAC149OthL">Other Reason Not Vaccinated Per ACIP Recommendations:</span></td>
<td> <span id="VAC149Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(VAC149Oth)"/></td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Comments about the subjects vaccination history" id="VAC133L">
Vaccine History Comments:</span>
</td>
<td>
<span id="VAC133"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC133)"  />
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
