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
String tabId = "viewCaseInfo";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Investigation Information","OOJ Initiating Agency Information","Reporting Information","Clinical","Epidemiologic","Comments"};
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
<nedss:container id="NBS_UI_19" name="Investigation Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The jurisdiction of the investigation." id="INV107L" >
Jurisdiction:</span></td><td>
<span id="INV107" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV107)"
codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The program area associated with the investigaiton condition." id="INV108L" >
Program Area:</span></td><td>
<span id="INV108" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV108)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Document the reason (referral basis) why the investigation was initiated." id="NBS110L" >
Referral Basis:</span></td><td>
<span id="NBS110" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS110)"
codeSetNm="REFERRAL_BASIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The status of the investigation." id="INV109L" >
Investigation Status:</span></td><td>
<span id="INV109" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV109)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)" id="NBS115L" >
Current Process Stage:</span></td><td>
<span id="NBS115" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS115)"
codeSetNm="CM_PROCESS_STAGE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the investigation was started or initiated." id="INV147L">Investigation Start Date:</span>
</td><td>
<span id="INV147"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV147)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the investigation is closed." id="INV2006L">Investigation Close Date:</span>
</td><td>
<span id="INV2006"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV2006)"  />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr> <td valign="top" class="fieldName">
<span style="color:#CC0000">*</span>
<span id="NBS012L" title="Should this record be shared with guests with program area and jurisdiction rights?">
Shared Indicator:</span>
</td>
<td>
<logic:equal name="PageForm" property="pageClientVO.answer(NBS012)" value="1">
Yes</logic:equal>
<logic:notEqual name="PageForm" property="pageClientVO.answer(NBS012)" value="1">
No</logic:notEqual>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="NBS270L" title="Referral Basis - OOJ">Referral Basis - OOJ:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(NBS270)" styleId="NBS270"><html:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="INV180L" title="The Public Health Investigator assigned to the Investigation.">
Current Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV180)"/>
<span id="INV180">${PageForm.attributeMap.INV180SearchResult}</span>
</td> </tr>
<!--skipping Hidden Date Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_8" name="OOJ Agency Initiating Report" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The Initiating Agency which sent the OOJ Contact Report." id="NBS111L" >
Initiating Agency:</span></td><td>
<span id="NBS111" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS111)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the OOJ Contact report was received from the Initiating Agency." id="NBS112L">Date Received from Init. Agency:</span>
</td><td>
<span id="NBS112"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS112)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date OOJ outcome is due back to the Initiating Agency." id="NBS113L">Date OOJ Due to Init. Agency:</span>
</td><td>
<span id="NBS113"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS113)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date OOJ outcome was sent back to the Initiating Agency." id="NBS114L">Date OOJ Info Sent:</span>
</td><td>
<span id="NBS114"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS114)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_9" name="Reported as OOJ Contact" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of first exposure with sex partner reported  by OOJ Contact." id="NBS118L">First Sexual Exposure:</span>
</td><td>
<span id="NBS118"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS118)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The frequency of exposure with sex partner reported by OOJ Contact." id="NBS119L">
Sexual Frequency:</span>
</td>
<td>
<span id="NBS119"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS119)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of last exposure with sex partner reported by OOJ Contact." id="NBS120L">Last Sexual Exposure:</span>
</td><td>
<span id="NBS120"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of first exposure with needle-sharing partner reported by OOJ Contact." id="NBS121L">First Needle-Sharing Exposure:</span>
</td><td>
<span id="NBS121"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS121)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The frequency of exposure with needle-sharing partner reported by OOJ Contact." id="NBS122L">
Needle-Sharing Frequency:</span>
</td>
<td>
<span id="NBS122"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS122)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of last exposure with needle-sharing partner reported by OOJ Contact." id="NBS123L">Last Needle-Sharing Exposure:</span>
</td><td>
<span id="NBS123"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS123)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The cluster relationship reported by OOJ Contact." id="NBS124L" >
Relationship:</span></td><td>
<span id="NBS124" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS124)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="As reported by OOJ Contact, is the patient the original patient's spouse?" id="NBS125L" >
OP Spouse?:</span></td><td>
<span id="NBS125" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS125)"
codeSetNm="OP_SPOUSE_IND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="As reported by OOJ Contact, did the patient meet the original patient on the internet?" id="NBS126L" >
Met OP via Internet?:</span></td><td>
<span id="NBS126" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the course of the interview, did the DIS elicit internet information about the named contact?" id="NBS127L" >
Internet Info Elicited?:</span></td><td>
<span id="NBS127" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS127)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_22" name="Key Report Dates" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of report of the condition to the public health department." id="INV111L">Date of Report:</span>
</td><td>
<span id="INV111"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV111)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Earliest date reported to county public health system." id="INV120L">Earliest Date Reported to County:</span>
</td><td>
<span id="INV120"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Earliest date reported to state public health system." id="INV121L">Earliest Date Reported to State:</span>
</td><td>
<span id="INV121"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV121)"  />
</td></tr>
<!--skipping Hidden Date Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_23" name="Reporting Organization" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Type of facility or provider associated with the source of information sent to Public Health." id="INV112L" >
Reporting Source Type:</span></td><td>
<span id="INV112" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV112)"
codeSetNm="PHVS_REPORTINGSOURCETYPE_NND"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="INV183L" title="The organization that reported the case.">
Reporting Organization:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV183)"/>
<span id="INV183">${PageForm.attributeMap.INV183SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_10" name="Reporting Provider" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="INV181L" title="The provider that reported the case.">
Reporting Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV181)"/>
<span id="INV181">${PageForm.attributeMap.INV181SearchResult}</span>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_12" name="Physician" isHidden="F" classType="subSect" >

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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_32" name="Physician Clinic" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS291L" title="The clinic with which the physician associated with this case is affiliated.">
Physician Ordering Clinic:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS291)"/>
<span id="NBS291">${PageForm.attributeMap.NBS291SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_13" name="Hospital" isHidden="F" classType="subSect" >

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
Total duration of stay in the hospital (in days):</span>
</td><td>
<span id="INV134"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV134)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14" name="Condition" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of diagnosis of condition being reported to public health system." id="INV136L">Diagnosis Date:</span>
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

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date treatment initiated for the condition that is the subject of this case report." id="STD105L">Treatment Start Date:</span>
</td><td>
<span id="STD105"/>
<nedss:view name="PageForm" property="pageClientVO.answer(STD105)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date." id="STD099L">Date of Initial Health Exam:</span>
</td><td>
<span id="STD099"/>
<nedss:view name="PageForm" property="pageClientVO.answer(STD099)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25" name="Epi-Link" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility." id="INV148L" >
Is this person associated with a day care facility?:</span></td><td>
<span id="INV148" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates whether the subject of the investigation was food handler." id="INV149L" >
Is this person a food handler?:</span></td><td>
<span id="INV149" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV149)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Denotes whether the reported case was associated with an identified outbreak." id="INV150L" >
Is this case part of an outbreak?:</span></td><td>
<span id="INV150" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV150)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="A name assigned to an individual outbreak.   State assigned in SRT.  Should show only those outbreaks for the program area of the investigation." id="INV151L" >
Outbreak Name:</span></td><td>
<span id="INV151" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV151)"
codeSetNm="OUTBREAK_NM"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_1" name="Disease Acquisition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of where the disease/condition was likely acquired." id="INV152L" >
Where was the disease acquired:</span></td><td>
<span id="INV152" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV152)"
codeSetNm="PHVS_DISEASEACQUIREDJURISDICTION_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the disease or condition was imported, indicate the country in which the disease was likely acquired." id="INV153L" >
Imported Country:</span></td><td>
<span id="INV153" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV153)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the disease or condition was imported, indicate the state in which the disease was likely acquired." id="INV154L" >
Imported State:</span></td><td>
<span id="INV154" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV154)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If the disease or condition was imported, indicate the city in which the disease was likely acquired." id="INV155L">
Imported City:</span>
</td>
<td>
<span id="INV155"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV155)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the disease or condition was imported, this field will contain the county of origin of the disease or condition." id="INV156L" >
Imported County:</span></td><td>
<span id="INV156" />
<logic:notEmpty name="PageForm" property="pageClientVO.answer(INV156)">
<logic:notEmpty name="PageForm" property="pageClientVO.answer(INV154)">
<bean:define id="value" name="PageForm" property="pageClientVO.answer(INV154)"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV156)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.INV156_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient." id="NBS135L" >
Source/Spread:</span></td><td>
<span id="NBS135" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS135)"
codeSetNm="SOURCE_SPREAD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Where does the person usually live (defined as their residence)." id="INV501L" >
Country of Usual Residence:</span></td><td>
<span id="INV501" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV501)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS11001" name="Reporting County" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="County reporting the notification." id="NOT113L" >
Reporting County:</span></td><td>
<span id="NOT113" />
<nedss:view name="PageForm" property="pageClientVO.answer(NOT113)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.NOT113_STATE}"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS11002" name="Exposure Location" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS11002"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
while(mapIt .hasNext())
{
Map.Entry mappairs = (Map.Entry)mapIt .next();
if(mappairs.getKey().toString().equals(subSecNm)){
batchrec =(String[][]) mappairs.getValue();
break;
}
}%>
<%int wid =100/11; %>
<td style="background-color: #EFEFEF; border:1px solid #666666" width="3%"> &nbsp;</td>
<% for(int i=0;i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS11002">
<tr id="patternNBS11002" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS11002');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS11002">
<tr id="nopatternNBS11002" class="odd" style="display:none">
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

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the country in which the disease was potentially acquired." id="INV502L" >
Country of Exposure:</span></td><td>
<span id="INV502" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV502)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the state in which the disease was potentially acquired." id="INV503L" >
State or Province of Exposure:</span></td><td>
<span id="INV503" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV503)"
codeSetNm="PHVS_STATEPROVINCEOFEXPOSURE_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the city in which the disease was potentially acquired." id="INV504L">
City of Exposure:</span>
</td>
<td>
<span id="INV504"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV504)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the county in which the disease was potentially acquired." id="INV505L" >
County of Exposure:</span></td><td>
<span id="INV505" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV505)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.INV505_STATE}"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS11003" name="Binational Reporting" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV515L" title="For cases meeting the binational criteria, select all the criteria which are met.">
Binational Reporting Criteria:</span></td><td>
<span id="INV515" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV515)"
codeSetNm="PHVS_BINATIONALREPORTINGCRITERIA_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2" name="Case Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate." id="INV157L" >
Transmission Mode:</span></td><td>
<span id="INV157" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV157)"
codeSetNm="PHC_TRAN_M"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc." id="INV159L" >
Detection Method:</span></td><td>
<span id="INV159" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV159)"
codeSetNm="PHVS_DETECTIONMETHOD_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV161L" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived.">
Confirmation Method:</span></td><td>
<span id="INV161" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV161)"
codeSetNm="PHC_CONF_M"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="If an investigation is confirmed as a case, then the confirmation date is entered." id="INV162L">Confirmation Date:</span>
</td><td>
<span id="INV162"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV162)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The current status of the investigation/case." id="INV163L" >
Case Status:</span></td><td>
<span id="INV163" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV163)"
codeSetNm="PHVS_PHC_CLASS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The disease diagnosis of the patient." id="NBS136L" >
Diagnosis Reported to CDC:</span></td><td>
<span id="NBS136" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS136)"
codeSetNm="CASE_DIAGNOSIS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The MMWR week in which the case should be counted." id="INV165L">
MMWR Week:</span>
</td>
<td>
<span id="INV165"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV165)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The MMWR year in which the case should be counted." id="INV166L">
MMWR Year:</span>
</td>
<td>
<span id="INV166"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV166)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" id="NOT120L" >
Immediate National Notifiable Condition:</span></td><td>
<span id="NOT120" />
<nedss:view name="PageForm" property="pageClientVO.answer(NOT120)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." id="NOT120SPECL">
If Yes, describe:</span>
</td>
<td>
<span id="NOT120SPEC"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NOT120SPEC)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Do not send personally identifiable information to CDC in this field. Use this field, if needed, to communicate anything unusual about this case, which is not already covered with the other data elements.  Alternatively, use this field to communicate information to the CDC NNDSS staff processing the data." id="INV886L">
Notification Comments to CDC:</span>
</td>
<td>
<span id="INV886"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV886)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_73" name="Syphilis Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Neurological Involvement?" id="STD102L" >
Neurological Manifestations:</span></td><td>
<span id="STD102" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD102)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="102957003L" title="What neurologic manifestations of syphilis are present?">
Neurologic Signs/Symptoms:</span></td><td>
<span id="102957003" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(102957003)"
codeSetNm="PHVS_NEUROLOGICALMANIFESTATION_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="What neurologic manifestations of syphilis are present?" id="102957003OthL">Other Neurologic Signs/Symptoms:</span></td>
<td> <span id="102957003Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(102957003Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis." id="410478005L" >
Ocular Manifestations:</span></td><td>
<span id="410478005" />
<nedss:view name="PageForm" property="pageClientVO.answer(410478005)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo." id="PHC1472L" >
Otic Manifestations:</span></td><td>
<span id="PHC1472" />
<nedss:view name="PageForm" property="pageClientVO.answer(PHC1472)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Late clinical manifestations of syphilis (tertiary syphilis) may include inflammatory lesions of the cardiovascular system, skin, bone, or other tissue. Certain neurologic manifestations (e.g., general paresis and tabes dorsalis) are late clinical manifestations of syphilis." id="72083004L" >
Late Clinical Manifestations:</span></td><td>
<span id="72083004" />
<nedss:view name="PageForm" property="pageClientVO.answer(72083004)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_74" name="Other Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Pelvic inflammatory disease present?" id="INV179L" >
PID:</span></td><td>
<span id="INV179" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV179)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Disseminated?" id="NBS137L" >
Disseminated:</span></td><td>
<span id="NBS137" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS137)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Conjunctivitis?" id="INV361L" >
Conjunctivitis:</span></td><td>
<span id="INV361" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV361)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="To what drug(s) is the disease resistant?" id="NBS138L" >
Resistant to:</span></td><td>
<span id="NBS138" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS138)"
codeSetNm="RESISTANT_TO_300_DRUG"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_27" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Field which contains general comments for the investigation." id="INV167L">
General Comments:</span>
</td>
<td>
<span id="INV167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV167)"  />
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
