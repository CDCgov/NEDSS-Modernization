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
String [] sectionNames  = {"Inclusion Criteria","Comorbidities","Hospitalization","Clinical Signs and Symptoms","Complications","Treatments","Vaccination"};
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
<nedss:container id="NBS_UI_GA28002" name="Inclusion Criteria Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient less than 21 years old at illness onset?" id="NBS694L" >
Age &lt; 21:</span></td><td>
<span id="NBS694" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS694)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have a fever greater than 38C or 100.4F for 24 hours or more, or report of subjective fever lasting 24 hours or more" id="426000000L" >
Fever For At Least 24 Hrs:</span></td><td>
<span id="426000000" />
<nedss:view name="PageForm" property="pageClientVO.answer(426000000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Laboratory markers of inflammation (including, but not limited to one or more; an elevated C-reactive protein (CRP), erythrocyte sedimentation rate (ESR), fibrinogen, procalcitonin, d-dimer, ferritin, lactic acid dehydrogenase (LDH), or interleukin 6 (IL-6), elevated neutrophils, reduced lymphocytes and low albumin." id="NBS695L" >
Laboratory Markers of Inflammation:</span></td><td>
<span id="NBS695" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS695)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Evidence of clinically severe illness requiring hospitalization." id="434081000124108L" >
Evidence of clinically severe illness requiring hospitalization with multisystem organ involvement:</span></td><td>
<span id="434081000124108" />
<nedss:view name="PageForm" property="pageClientVO.answer(434081000124108)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience cardiac disorder(s) (e.g. shock, elevated troponin, BNP, abnormal echocardiogram, arrhythmia)?" id="56265001L" >
Cardiac:</span></td><td>
<span id="56265001" />
<nedss:view name="PageForm" property="pageClientVO.answer(56265001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience kidney disorder(s) (e.g. acute kidney injury or renal failure)?" id="90708001L" >
Renal:</span></td><td>
<span id="90708001" />
<nedss:view name="PageForm" property="pageClientVO.answer(90708001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience respiratory disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?" id="50043002L" >
Respiratory:</span></td><td>
<span id="50043002" />
<nedss:view name="PageForm" property="pageClientVO.answer(50043002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience actual hematologic disorder(s) (e.g. elevated D-dimers, thrombophilia, or thrombocytopenia)?" id="128480004L" >
Hematologic:</span></td><td>
<span id="128480004" />
<nedss:view name="PageForm" property="pageClientVO.answer(128480004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have gastrointestinal disorder(s) (e.g. elevated bilirubin, elevated liver enzymes, or diarrhea)." id="119292006L" >
Gastrointestinal:</span></td><td>
<span id="119292006" />
<nedss:view name="PageForm" property="pageClientVO.answer(119292006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have skin disorder(s) (e.g. pneumonia, ARDS, pulmonary embolism)?" id="95320005L" >
Dermatologic:</span></td><td>
<span id="95320005" />
<nedss:view name="PageForm" property="pageClientVO.answer(95320005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have nervous system disorder(s) (e.g. CVA, aseptic meningitis, encephalopathy)?" id="118940003L" >
Neurological:</span></td><td>
<span id="118940003" />
<nedss:view name="PageForm" property="pageClientVO.answer(118940003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates whether there is no alternative plausible diagnosis." id="NBS696L" >
No alternative plausible diagnosis:</span></td><td>
<span id="NBS696" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS696)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the patient positive for current or recent SARS-COV-2 infection by lab testing?" id="NBS697L" >
Positive for current or recent SARS-COV-2 infection by lab testing?:</span></td><td>
<span id="NBS697" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS697)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was RT-PCR testing completed for this disease?" id="NBS709L" >
RT-PCR:</span></td><td>
<span id="NBS709" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS709)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was serology testing completed for this disease?" id="NBS710L" >
Serology:</span></td><td>
<span id="NBS710" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS710)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was antigen testing completed for this disease?" id="NBS711L" >
Antigen:</span></td><td>
<span id="NBS711" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS711)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient exposed to COVID-19 within the 4 weeks prior to the onset of symptoms?" id="840546002L" >
COVID-19 exposure within the 4 weeks prior to the onset of symptoms?:</span></td><td>
<span id="840546002" />
<nedss:view name="PageForm" property="pageClientVO.answer(840546002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of first exposure to individual with confirmed illness within the 4 weeks prior." id="LP248166_3L">Date of first exposure within the 4 weeks prior:</span>
</td><td>
<span id="LP248166_3"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LP248166_3)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Exposure date unknown." id="NBS698L" >
Exposure Date Unknown:</span></td><td>
<span id="NBS698" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS698)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28004" name="Weight and BMI" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS434L" title="Enter the height of the patient in inches.">
Height (in inches):</span>
</td><td>
<span id="NBS434"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS434)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="DEM255L" title="Enter the patients weight at diagnosis in pounds (lbs).">
Weight (in lbs):</span>
</td><td>
<span id="DEM255"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM255)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="39156_5L" title="What is the patient's body mass index?">
BMI:</span>
</td><td>
<span id="39156_5"/>
<nedss:view name="PageForm" property="pageClientVO.answer(39156_5)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA28003" name="Comorbidities Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have a history of an immunocompromised condition?" id="370388006L" >
Immunosuppressive disorder or malignancy:</span></td><td>
<span id="370388006" />
<nedss:view name="PageForm" property="pageClientVO.answer(370388006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is the patient obese?" id="414916001L" >
Obesity:</span></td><td>
<span id="414916001" />
<nedss:view name="PageForm" property="pageClientVO.answer(414916001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have type 1 diabetes?" id="46635009L" >
Type 1 diabetes:</span></td><td>
<span id="46635009" />
<nedss:view name="PageForm" property="pageClientVO.answer(46635009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have type 2 diabetes?" id="44054006L" >
Type 2 diabetes:</span></td><td>
<span id="44054006" />
<nedss:view name="PageForm" property="pageClientVO.answer(44054006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have seizures?" id="91175000L" >
Seizures:</span></td><td>
<span id="91175000" />
<nedss:view name="PageForm" property="pageClientVO.answer(91175000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Congenital heart disease" id="13213009L" >
Congenital heart disease:</span></td><td>
<span id="13213009" />
<nedss:view name="PageForm" property="pageClientVO.answer(13213009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have sickle cell disease?" id="417357006L" >
Sickle cell disease:</span></td><td>
<span id="417357006" />
<nedss:view name="PageForm" property="pageClientVO.answer(417357006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have chronic lung disease (asthma/emphysema/COPD)?" id="413839001L" >
Chronic Lung Disease:</span></td><td>
<span id="413839001" />
<nedss:view name="PageForm" property="pageClientVO.answer(413839001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have any other congenital malformations?" id="276654001L" >
Other congenital malformations:</span></td><td>
<span id="276654001" />
<nedss:view name="PageForm" property="pageClientVO.answer(276654001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify other congenital malformations." id="276654001_OTHL">
Specify other congenital malformations:</span>
</td>
<td>
<span id="276654001_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(276654001_OTH)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_NBS_INV_GENV2_UI_3" name="Hospital" isHidden="F" classType="subSect" >

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
<span title="Subject's admission date to the hospital for the condition covered by the investigation." id="INV132L">Hospital Admission Date:</span>
</td><td>
<span id="INV132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV132)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Subject's discharge date from the hospital for the condition covered by the investigation." id="INV133L">Hospital Discharge:</span>
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
<span title="During any part of the hospitalization, did the subject stay in an Intensive Care Unit (ICU) or a Critical Care Unit (CCU)?" id="309904001L" >
Was patient admitted to ICU?:</span></td><td>
<span id="309904001" />
<nedss:view name="PageForm" property="pageClientVO.answer(309904001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the date of admission." id="NBS679L">ICU Admission Date:</span>
</td><td>
<span id="NBS679"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS679)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="74200_7L" title="Indicate the number of days the patient was in intensive care.">
Number of days in the ICU:</span>
</td><td>
<span id="74200_7"/>
<nedss:view name="PageForm" property="pageClientVO.answer(74200_7)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Patients Outcome" id="FDD_Q_1038L" >
Patients Outcome:</span></td><td>
<span id="FDD_Q_1038" />
<nedss:view name="PageForm" property="pageClientVO.answer(FDD_Q_1038)"
codeSetNm="PATIENT_HOSP_STATUS_MIS"/>
</td> </tr>

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
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29007" name="Previous COVID Like Illness" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have preceding COVID-like illness?" id="NBS707L" >
Did patient have preceding COVID-like illness:</span></td><td>
<span id="NBS707" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS707)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of COVID-like illness symptom onset." id="NBS708L">Date of COVID-like illness symptom onset:</span>
</td><td>
<span id="NBS708"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS708)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27016" name="Symptoms Onset and Fever" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Onset information below relates to onset of Multisystem Inflammatory Syndrome Associated with COVID-19.</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of diagnosis of MIS-C." id="INV136L">Diagnosis Date:</span>
</td><td>
<span id="INV136"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV136)"  />
</td></tr>

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
<span title="Did the patient have fever?" id="386661006L" >
Fever Greater Than 38.0 C or 100.4 F:</span></td><td>
<span id="386661006" />
<nedss:view name="PageForm" property="pageClientVO.answer(386661006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicates the date of fever onset" id="INV203L">Date of Fever Onset:</span>
</td><td>
<span id="INV203"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV203)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV202L" title="What was the subjects highest measured temperature during this illness, in degress Celsius?">
Highest Measured Temperature (in degrees C):</span>
</td><td>
<span id="INV202"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV202)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAR125L" title="Total number of days fever lasted">
Number of Days Febrile:</span>
</td><td>
<span id="VAR125"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAR125)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27017" name="Cardiac" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Shock is captured in the Complications section of the page.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have elevated troponin?" id="444931001L" >
Elevated troponin:</span></td><td>
<span id="444931001" />
<nedss:view name="PageForm" property="pageClientVO.answer(444931001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does the patient have elevated BNP or NT-proBNP?" id="414798009L" >
Elevated BNP or NT-proBNP:</span></td><td>
<span id="414798009" />
<nedss:view name="PageForm" property="pageClientVO.answer(414798009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27018" name="Renal" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have acute kidney injury?" id="14350001000004108L" >
Acute kidney injury:</span></td><td>
<span id="14350001000004108" />
<nedss:view name="PageForm" property="pageClientVO.answer(14350001000004108)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have renal failure?" id="42399005L" >
Renal failure:</span></td><td>
<span id="42399005" />
<nedss:view name="PageForm" property="pageClientVO.answer(42399005)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27019" name="Respiratory" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patients illness include the symptom of cough?" id="49727002L" >
Cough:</span></td><td>
<span id="49727002" />
<nedss:view name="PageForm" property="pageClientVO.answer(49727002)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have shortness of breath (dyspnea)?" id="267036007L" >
Shortness of breath (dyspnea):</span></td><td>
<span id="267036007" />
<nedss:view name="PageForm" property="pageClientVO.answer(267036007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient experience chest pain?" id="29857009L" >
Chest pain or tightness:</span></td><td>
<span id="29857009" />
<nedss:view name="PageForm" property="pageClientVO.answer(29857009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Pneumonia and ARDS are captured in Complications section of the page.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have pulmonary embolism?" id="59282003L" >
Pulmonary embolism:</span></td><td>
<span id="59282003" />
<nedss:view name="PageForm" property="pageClientVO.answer(59282003)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27020" name="Hematologic" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have elevated D-dimers?" id="449830004L" >
Elevated D-dimers:</span></td><td>
<span id="449830004" />
<nedss:view name="PageForm" property="pageClientVO.answer(449830004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have thrombophilia?" id="234467004L" >
Thrombophilia:</span></td><td>
<span id="234467004" />
<nedss:view name="PageForm" property="pageClientVO.answer(234467004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject have thrombocytopenia?" id="302215000L" >
Thrombocytopenia:</span></td><td>
<span id="302215000" />
<nedss:view name="PageForm" property="pageClientVO.answer(302215000)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27021" name="Gastrointestinal" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have abdominal pain or tenderness?" id="21522001L" >
Abdominal pain:</span></td><td>
<span id="21522001" />
<nedss:view name="PageForm" property="pageClientVO.answer(21522001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject experience vomiting?" id="422400008L" >
Vomiting:</span></td><td>
<span id="422400008" />
<nedss:view name="PageForm" property="pageClientVO.answer(422400008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have diarrhea?" id="62315008L" >
Diarrhea:</span></td><td>
<span id="62315008" />
<nedss:view name="PageForm" property="pageClientVO.answer(62315008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have elevated bilirubin?" id="26165005L" >
Elevated bilirubin:</span></td><td>
<span id="26165005" />
<nedss:view name="PageForm" property="pageClientVO.answer(26165005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have elevated liver enzymes?" id="707724006L" >
Elevated liver enzymes:</span></td><td>
<span id="707724006" />
<nedss:view name="PageForm" property="pageClientVO.answer(707724006)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27022" name="Dermatologic" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have rash?" id="271807003L" >
Rash:</span></td><td>
<span id="271807003" />
<nedss:view name="PageForm" property="pageClientVO.answer(271807003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have mucocutaneous lesions?" id="95346009L" >
Mucocutaneous lesions:</span></td><td>
<span id="95346009" />
<nedss:view name="PageForm" property="pageClientVO.answer(95346009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27023" name="Neurological" isHidden="F" classType="subSect" >

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
<span title="Did the patient have altered mental status?" id="419284004L" >
Altered Mental Status:</span></td><td>
<span id="419284004" />
<nedss:view name="PageForm" property="pageClientVO.answer(419284004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have syncope/near syncope?" id="NBS712L" >
Syncope/near syncope:</span></td><td>
<span id="NBS712" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS712)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have meningitis?" id="44201003L" >
Meningitis:</span></td><td>
<span id="44201003" />
<nedss:view name="PageForm" property="pageClientVO.answer(44201003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have encephalopathy?" id="81308009L" >
Encephalopathy?:</span></td><td>
<span id="81308009" />
<nedss:view name="PageForm" property="pageClientVO.answer(81308009)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA27024" name="Other Signs and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have neck pain?" id="81680005L" >
Neck pain:</span></td><td>
<span id="81680005" />
<nedss:view name="PageForm" property="pageClientVO.answer(81680005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have myalgia?" id="68962001L" >
Myalgia:</span></td><td>
<span id="68962001" />
<nedss:view name="PageForm" property="pageClientVO.answer(68962001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have conjunctival injection?" id="193894004L" >
Conjunctival injection:</span></td><td>
<span id="193894004" />
<nedss:view name="PageForm" property="pageClientVO.answer(193894004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have periorbital edema?" id="49563000L" >
Periorbital edema:</span></td><td>
<span id="49563000" />
<nedss:view name="PageForm" property="pageClientVO.answer(49563000)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have cervical lymphadenopathy?" id="127086001L" >
Cervical lymphadenopathy:</span></td><td>
<span id="127086001" />
<nedss:view name="PageForm" property="pageClientVO.answer(127086001)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29002" name="Complications Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have arrhythmia?" id="698247007L" >
Arrhythmia:</span></td><td>
<span id="698247007" />
<nedss:view name="PageForm" property="pageClientVO.answer(698247007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="76281_5L" title="Indicate the type of arrhythmia.">
Type of arrhythmia:</span></td><td>
<span id="76281_5" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(76281_5)"
codeSetNm="CARDIAC_ARRHYTHMIA_TYPE"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Indicate the type of arrhythmia." id="76281_5OthL">Other Type of arrhythmia:</span></td>
<td> <span id="76281_5Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(76281_5Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have congestive heart failure?" id="ARB020L" >
Congestive Heart Failure:</span></td><td>
<span id="ARB020" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB020)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have myocarditis?" id="50920009L" >
Myocarditis:</span></td><td>
<span id="50920009" />
<nedss:view name="PageForm" property="pageClientVO.answer(50920009)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have pericarditis?" id="3238004L" >
Pericarditis:</span></td><td>
<span id="3238004" />
<nedss:view name="PageForm" property="pageClientVO.answer(3238004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have liver failure?" id="59927004L" >
Liver failure:</span></td><td>
<span id="59927004" />
<nedss:view name="PageForm" property="pageClientVO.answer(59927004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have deep vein thrombosis (DVT) or pulmonary embolism (PE)?" id="NBS713L" >
Deep vein thrombosis (DVT) or pulmonary embolism (PE):</span></td><td>
<span id="NBS713" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS713)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have acute respiratory distress syndrome?" id="67782005L" >
ARDS:</span></td><td>
<span id="67782005" />
<nedss:view name="PageForm" property="pageClientVO.answer(67782005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have pneumonia?" id="233604007L" >
Pneumonia:</span></td><td>
<span id="233604007" />
<nedss:view name="PageForm" property="pageClientVO.answer(233604007)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did patient develop CVA or stroke?" id="ARB021L" >
CVA or Stroke:</span></td><td>
<span id="ARB021" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB021)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have encephalitis?" id="31646008L" >
Encephalitis or aseptic meningitis:</span></td><td>
<span id="31646008" />
<nedss:view name="PageForm" property="pageClientVO.answer(31646008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject have septic shock?" id="27942005L" >
Shock:</span></td><td>
<span id="27942005" />
<nedss:view name="PageForm" property="pageClientVO.answer(27942005)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient have hypotension?" id="45007003L" >
Hypotension:</span></td><td>
<span id="45007003" />
<nedss:view name="PageForm" property="pageClientVO.answer(45007003)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA29004" name="Treatments Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive low flow nasal cannula?" id="466713001L" >
Low flow nasal cannula:</span></td><td>
<span id="466713001" />
<nedss:view name="PageForm" property="pageClientVO.answer(466713001)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive high flow nasal cannula?" id="426854004L" >
High flow nasal cannula:</span></td><td>
<span id="426854004" />
<nedss:view name="PageForm" property="pageClientVO.answer(426854004)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive non-invasive ventilation?" id="428311008L" >
Non-invasive ventilation:</span></td><td>
<span id="428311008" />
<nedss:view name="PageForm" property="pageClientVO.answer(428311008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive intubation?" id="52765003L" >
Intubation:</span></td><td>
<span id="52765003" />
<nedss:view name="PageForm" property="pageClientVO.answer(52765003)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive mechanical ventilation?" id="NBS673L" >
Mechanical ventilation:</span></td><td>
<span id="NBS673" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS673)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive ECMO?" id="233573008L" >
ECMO:</span></td><td>
<span id="233573008" />
<nedss:view name="PageForm" property="pageClientVO.answer(233573008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive vasoactive medications (e.g. epinephrine, milrinone, norepinephrine, or vasopressin)?" id="NBS699L" >
Vasoactive medications:</span></td><td>
<span id="NBS699" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS699)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify the vasoactive medications the patient received." id="NBS700L">
Specify vasoactive medications:</span>
</td>
<td>
<span id="NBS700"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS700)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="At the time you were diagnosed with West Nile virus infection, were you receiving oral or injected steroids?" id="ARB040L" >
Steroids:</span></td><td>
<span id="ARB040" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB040)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive any immune modulators (e.g. anakinra, tocilizumab, etc)?" id="ARB045L" >
Immune modulators:</span></td><td>
<span id="ARB045" />
<nedss:view name="PageForm" property="pageClientVO.answer(ARB045)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify immune modulators the patient received." id="373244000L">
Specify immune modulators:</span>
</td>
<td>
<span id="373244000"/>
<nedss:view name="PageForm" property="pageClientVO.answer(373244000)" />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive antiplatelets (e.g. aspirin, clopidogrel)?" id="372560006L" >
Antiplatelets:</span></td><td>
<span id="372560006" />
<nedss:view name="PageForm" property="pageClientVO.answer(372560006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify antiplatelets the patient received." id="372560006_OTHL">
Specify antiplatelets:</span>
</td>
<td>
<span id="372560006_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(372560006_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient received anticoagulation (e.g. heparin, enoxaparin, warfarin)?" id="372862008L" >
Anticoagulation:</span></td><td>
<span id="372862008" />
<nedss:view name="PageForm" property="pageClientVO.answer(372862008)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Specify anticoagulation the patient received?" id="372862008_OTHL">
Specify anticoagulation:</span>
</td>
<td>
<span id="372862008_OTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(372862008_OTH)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive hemodialysis?" id="302497006L" >
Dialysis:</span></td><td>
<span id="302497006" />
<nedss:view name="PageForm" property="pageClientVO.answer(302497006)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive first intravenous immunoglobulin?" id="NBS701L" >
First IVIG:</span></td><td>
<span id="NBS701" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS701)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive second intravenous immunoglobulin?" id="NBS702L" >
Second IVIG:</span></td><td>
<span id="NBS702" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS702)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_GA40001" name="Vaccine Interpretive Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Has the patient received a COVID-19 vaccine?" id="VAC126L" >
Has the patient received a COVID-19 vaccine?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Total number of doses of vaccine the patient received for this condition (e.g., if the condition is hepatitis A, total number of doses of hepatitis A-containing vaccine)." id="VAC132_CDL" >
If yes, how many doses?:</span></td><td>
<span id="VAC132_CD" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC132_CD)"
codeSetNm="VAC_DOSE_NUM_MIS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date the first vaccine dose was received." id="NBS791L">Vaccine Dose 1 Received Date:</span>
</td><td>
<span id="NBS791"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS791)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date the second vaccine dose was received." id="NBS792L">Vaccine Dose 2 Received Date:</span>
</td><td>
<span id="NBS792"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS792)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="COVID-19 vaccine manufacturer" id="VAC107L" >
COVID-19 vaccine manufacturer:</span></td><td>
<span id="VAC107" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC107)"
codeSetNm="MIS_VAC_MFGR"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="COVID-19 vaccine manufacturer" id="VAC107OthL">Other COVID-19 vaccine manufacturer:</span></td>
<td> <span id="VAC107Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(VAC107Oth)"/></td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
