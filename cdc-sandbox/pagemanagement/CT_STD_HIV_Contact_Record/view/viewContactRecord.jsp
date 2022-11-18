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
String tabId = "viewContactRecord";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Contact Record","Disposition","Contact Record Comments"};
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
<nedss:container id="GA17101" name="Contact Record Security" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The geographic area responsible for managing public health activities including intervention, prevention and surveillance for health event associated with a particular geographic area such as county or city, associated with an event.  A jurisdiction is re" id="CON134L" >
Jurisdiction:</span></td><td>
<span id="CON134" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON134)"
codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The functional area accountable for managing the public health response and surveillance for health events associated with a particular condition(s).  For example, the TB program area works to control and prevent tuberculosis." id="CON135L" >
Program Area:</span></td><td>
<span id="CON135" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON135)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>
</td> </tr>

<!--processing Checkbox Coded Question-->
<tr> <td valign="top" class="fieldName">
<span id="CON136L" title="This field indicates whether or not the record should be shared with all users who have guest privileges for the Program Area/Jurisdiction.">
Shared Indicator:</span>
</td>
<td>
<logic:equal name="contactTracingForm" property="cTContactClientVO.answer(CON136)" value="1">
Yes</logic:equal>
<logic:notEqual name="contactTracingForm" property="cTContactClientVO.answer(CON136)" value="1">
No</logic:notEqual>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17103" name="Contact Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="Indicate if the relationship being recorded is a direct exposure/relationship between this patient and Contact or an exposure/relationship between the Contact and another known infected patient." id="CON141L" >
Relationship with Patient/Other infected Patient?:</span></td><td>
<span id="CON141" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON141)"
codeSetNm="CONTACT_REL_WITH"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="CON142L" title="Search for patients infected with the same condition.">
Other Infected Patient:</span>
</td>
<td>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON142)"/>
<span id="CON142">${contactTracingForm.attributeMap.CON142SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The interview at which the contact was named. May also indicate if the contact record was initiated w/out interview" id="CON143L" >
Named:</span></td><td>
<span id="CON143" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON143)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The current health status of the contact." id="CON115L" >
Health Status:</span></td><td>
<span id="CON115" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON115)"
codeSetNm="NBS_HEALTH_STATUS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The approximate or specific height of the patient." id="NBS155L">
Height:</span>
</td>
<td>
<span id="NBS155"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS155)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The approximate or specific weight or body type of the patient." id="NBS156L">
Size/Build:</span>
</td>
<td>
<span id="NBS156"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS156)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The description of the patients hair, including color, length, and/or style." id="NBS157L">
Hair:</span>
</td>
<td>
<span id="NBS157"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS157)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The approximate or specific skine tone/hue of the patient." id="NBS158L">
Complexion:</span>
</td>
<td>
<span id="NBS158"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS158)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Any additional demographic information (e.g., tattoos, etc)." id="NBS159L">
Other Identifying Information:</span>
</td>
<td>
<span id="NBS159"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS159)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17104" name="Exposure Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Document the appropriate identifier for the specific type of partner, social contact and/or Associate." id="CON144L" >
Referral Basis:</span></td><td>
<span id="CON144" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON144)"
codeSetNm="REFERRAL_BASIS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of the first sexual exposure to the original/index patient." id="NBS118L">First Sexual Exposure:</span>
</td><td>
<span id="NBS118"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS118)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The frequency (number) of sexual exposure(s)  between the first and last (most recent) exposure(s)." id="NBS119L">
Sexual Frequency:</span>
</td>
<td>
<span id="NBS119"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS119)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of the last sexual exposure to the original/index patient." id="NBS120L">Last Sexual Exposure:</span>
</td><td>
<span id="NBS120"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of the first needle-sharing exposure to the original/index patient." id="NBS121L">First Needle-Sharing Exposure:</span>
</td><td>
<span id="NBS121"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS121)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The frequency (number) of needle-sharing exposure(s) between the first and last (most recent) exposure(s)." id="NBS122L">
Needle-Sharing Frequency:</span>
</td>
<td>
<span id="NBS122"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS122)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date of the last needle-sharing exposure to the original/index patient." id="NBS123L">Last Needle-Sharing Exposure:</span>
</td><td>
<span id="NBS123"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS123)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The relationship the contact has with the subject of the investigation or other known infected patient." id="CON103L" >
Relationship:</span></td><td>
<span id="CON103" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON103)"
codeSetNm="NBS_RELATIONSHIP"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is this contact the original patient's spouse?" id="NBS125L" >
OP Spouse:</span></td><td>
<span id="NBS125" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS125)"
codeSetNm="OP_SPOUSE_IND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did this contact meet the original patient via the internet?" id="NBS126L" >
Met OP via Internet:</span></td><td>
<span id="NBS126" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="During the course of the interview, did the DIS elicit internet information about the contact? If Yes, Internet Outcome response will be required in Investigation started for the Contact" id="NBS127L" >
Internet Info Elicited:</span></td><td>
<span id="NBS127" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS127)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA25101" name="Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The action to be taken for this contact record (e.g., intitiate a new investigation associate to existing investigation, no follow-up reason)" id="CON145L" >
Processing Decision:</span></td><td>
<span id="CON145" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON145)"
codeSetNm="STD_CONTACT_RCD_PROCESSING_DECISION"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the contact was initiated for follow-up." id="CON146L">Initiate Follow-up Date:</span>
</td><td>
<span id="CON146"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON146)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="CON137L" title="The investigator assigned to this contact record.">
Investigator:</span>
</td>
<td>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON137)"/>
<span id="CON137">${contactTracingForm.attributeMap.CON137SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the investigator was assigned to this contact record." id="CON138L">Date Assigned:</span>
</td><td>
<span id="CON138"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON138)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The disposition (or outcome) of this contact record." id="CON114L" >
Disposition:</span></td><td>
<span id="CON114" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON114)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STDHIV"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date that the contact record was dispositioned." id="CON140L">Disposition Date:</span>
</td><td>
<span id="CON140"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON140)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="CON147L" title="The person that placed the disposition (or outcome) for this contact record.">
Dispositioned By:</span>
</td>
<td>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON147)"/>
<span id="CON147">${contactTracingForm.attributeMap.CON147SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient." id="NBS135L" >
Source/Spread:</span></td><td>
<span id="NBS135" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(NBS135)"
codeSetNm="SOURCE_SPREAD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA28101" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="General comments about the contact record that may not have been captured in other notes fields." id="CON133L">
General Comments:</span>
</td>
<td>
<span id="CON133"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON133)"  />
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
