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
String [] sectionNames  = {"Contact Record"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
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
<span style="color:#CC0000">*</span>
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
<nedss:container id="GA17102" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The status of this contact record (e.g., open, closed)." id="CON139L" >
Status:</span></td><td>
<span id="CON139" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON139)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The priority or importance assigned to tracking the contact." id="CON112L" >
Priority:</span></td><td>
<span id="CON112" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON112)"
codeSetNm="NBS_PRIORITY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="An identifier given to all individuals within an epi network (a network of related individuals potentially involved in the transmission of a disease/illness)." id="CON127L" >
Group/Lot ID:</span></td><td>
<span id="CON127" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON127)"
codeSetNm="NBS_GROUP_NM"/>
</td> </tr>

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
codeSetNm="NBS_DISPO"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date that the contact record was dispositioned." id="CON140L">Disposition Date:</span>
</td><td>
<span id="CON140"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON140)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17103" name="Contact Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The date that the contact was named within this investigation." id="CON101L">Date Named:</span>
</td><td>
<span id="CON101"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON101)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The relationship the contact has with the subject of the investigation." id="CON103L" >
Relationship:</span></td><td>
<span id="CON103" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON103)"
codeSetNm="NBS_RELATIONSHIP"/>
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
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17104" name="Exposure Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The type of exposure the contact had with the subject of the investigation." id="CON104L" >
Exposure Type:</span></td><td>
<span id="CON104" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON104)"
codeSetNm="NBS_EXPOSURE_TYPE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The type of site (location) where the exopsure occurred." id="CON105L" >
Exposure Site Type:</span></td><td>
<span id="CON105" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON105)"
codeSetNm="NBS_EXPOSURE_LOC"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="CON106L" title="The site (location) where the exposure occurred.">
Exposure Site:</span>
</td>
<td>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON106)"/>
<span id="CON106">${contactTracingForm.attributeMap.CON106SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date on which the contact was first exposed to the subject of the investigation." id="CON107L">First Exposure Date:</span>
</td><td>
<span id="CON107"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON107)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date on which the contact was last exposed to the subject of the investigation." id="CON108L">Last Exposure Date:</span>
</td><td>
<span id="CON108"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON108)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17105" name="Contact Record Comments" isHidden="F" classType="subSect" >

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
