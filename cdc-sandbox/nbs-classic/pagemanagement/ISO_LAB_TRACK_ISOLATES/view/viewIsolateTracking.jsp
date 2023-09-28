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
String tabId = "viewIsolateTracking";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Isolate Tracking"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2" name="Isolate Tracking" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if an isolate was received at the state public health lab." id="NBS_LAB331L" >
Was an isolate received at the state public health lab?:</span></td><td>
<span id="NBS_LAB331" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB331)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If an isolate wasnt received at the state public health lab, specifies the reason the isolate wasnt received" id="NBS_LAB332L" >
If an isolate wasnt received at the state public health lab, what is the reason?:</span></td><td>
<span id="NBS_LAB332" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB332)"
codeSetNm="PHVSFB_SPECFORW"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If an isolate wasnt received at the state public health lab, specifies the other (free-text) reason the isolate wasnt received." id="NBS_LAB333L">
If Other, please specify:</span>
</td>
<td>
<span id="NBS_LAB333"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB333)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Specifies the date the isolate was received at the state public health lab" id="NBS_LAB334L">If Yes, please specify date received in state public health lab:</span>
</td><td>
<span id="NBS_LAB334"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB334)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="State public health lab isolate id number" id="NBS_LAB335L">
State public health lab isolate id number:</span>
</td>
<td>
<span id="NBS_LAB335"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB335)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if case was confirmed at the state public health lab" id="NBS_LAB336L" >
Was case confirmed at state public health lab?:</span></td><td>
<span id="NBS_LAB336" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB336)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if the specimen or isolate was forwarded to the CDC for testing or confirmation." id="NBS_LAB363L" >
Was specimen or isolate forwarded to CDC for testing or confirmation?:</span></td><td>
<span id="NBS_LAB363" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB363)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if a PulseNet isolate was collected" id="NBS_LAB337L" >
PulseNet Isolate?:</span></td><td>
<span id="NBS_LAB337" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB337)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if PulseNet isolate PFGE pattern was sent to central PulseNet database." id="NBS_LAB338L" >
Has isolate PFGE pattern been sent to central PulseNet database?:</span></td><td>
<span id="NBS_LAB338" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB338)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="PulseNet PFGE Designation Enzyme 1" id="NBS_LAB339L">
PulseNet PFGE Designation Enzyme 1:</span>
</td>
<td>
<span id="NBS_LAB339"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB339)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="State Health Dept Lab PFGE Designation Enzyme 1" id="NBS_LAB340L">
State Health Dept Lab PFGE Designation Enzyme 1:</span>
</td>
<td>
<span id="NBS_LAB340"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB340)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="PulseNet PFGE Designation Enzyme 2" id="NBS_LAB341L">
PulseNet PFGE Designation Enzyme 2:</span>
</td>
<td>
<span id="NBS_LAB341"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB341)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="State Health Dept Lab PFGE Designation Enzyme 2" id="NBS_LAB342L">
State Health Dept Lab PFGE Designation Enzyme 2:</span>
</td>
<td>
<span id="NBS_LAB342"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB342)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="PulseNet PFGE Designation Enzyme 3" id="NBS_LAB343L">
PulseNet PFGE Designation Enzyme 3:</span>
</td>
<td>
<span id="NBS_LAB343"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB343)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="State Health Dept Lab PFGE Designation Enzyme 3" id="NBS_LAB344L">
State Health Dept Lab PFGE Designation Enzyme 3:</span>
</td>
<td>
<span id="NBS_LAB344"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB344)" />
</td> </tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if NARMS isolate was collected" id="NBS_LAB345L" >
NARMS Isolate:</span></td><td>
<span id="NBS_LAB345" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB345)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if NARMs isolate was sent to NARMS" id="NBS_LAB346L" >
Has isolate been sent to NARMS?:</span></td><td>
<span id="NBS_LAB346" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB346)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the NARMS isolate was not sent to NARMS, specifies the reason the NARMS isolate was not sent" id="NBS_LAB347L" >
If an isolate was not sent to NARMS, what is the reason?:</span></td><td>
<span id="NBS_LAB347" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB347)"
codeSetNm="PHVSFB_ISOLATNO"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="State-assigned NARMS ID number" id="NBS_LAB348L">
State-assigned NARMS ID number:</span>
</td>
<td>
<span id="NBS_LAB348"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB348)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="NARMS Isolate Expected Ship Date" id="NBS_LAB349L">Expected Ship Date:</span>
</td><td>
<span id="NBS_LAB349"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB349)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="NARMS Isolate Actual Ship Date" id="NBS_LAB350L">Actual Ship Date:</span>
</td><td>
<span id="NBS_LAB350"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB350)"  />
</td></tr>

<!--processing Static Line Separator-->
<tr><td colspan="2" align="left"><hr/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if EIP isolate was collected." id="NBS_LAB351L" >
EIP Isolate?:</span></td><td>
<span id="NBS_LAB351" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB351)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if the EIP isolate specimen was available for further EIP testing." id="NBS_LAB352L" >
Is this specimen available for further EIP testing?:</span></td><td>
<span id="NBS_LAB352" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB352)"
codeSetNm="PHVSFB_ISOLATAV"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the EIP isolate specimen was not available for further EIP testing, specifies the reason the EIP isolate specimen was not
available." id="NBS_LAB353L" >
If a specimen is not available for further EIP testing, what is the reason?:</span></td><td>
<span id="NBS_LAB353" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB353)"
codeSetNm="PHVSFB_SPECAVAL"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If the EIP isolate specimen was not available for further EIP testing, specifies the other (free-text) reason the EIP isolate
specimen was not available." id="NBS_LAB354L">
If Other, please specify other reason why specimen is not available:</span>
</td>
<td>
<span id="NBS_LAB354"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB354)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the EIP isolate specimen was available for further EIP testing, indicates where the specimen will be shipped." id="NBS_LAB355L" >
If Yes, where will the specimen be shipped?:</span></td><td>
<span id="NBS_LAB355" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB355)"
codeSetNm="PHVSFB_CDCLABSH"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="EIP isolate expected ship date." id="NBS_LAB356L">Expected Ship Date:</span>
</td><td>
<span id="NBS_LAB356"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB356)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="EIP isolate actual ship date" id="NBS_LAB357L">Actual Ship Date:</span>
</td><td>
<span id="NBS_LAB357"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB357)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates if the EIP isolate specimen was requested for reshipment" id="NBS_LAB358L" >
Was specimen requested for reshipment?:</span></td><td>
<span id="NBS_LAB358" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB358)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the EIP isolate specimen was requested for reshipment for further EIP testing, what was the reason" id="NBS_LAB359L" >
If a specimen was requested for reshipment for further EIP testing, what is the reason?:</span></td><td>
<span id="NBS_LAB359" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB359)"
codeSetNm="PHVSFB_CONTAMIN"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If the EIP isolate specimen was requested for reshipment for further EIP testing, what was the other (free-text) reason" id="NBS_LAB360L">
If Other, please specify reason for reshipment:</span>
</td>
<td>
<span id="NBS_LAB360"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB360)" />
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="EIP isolate expected reship date" id="NBS_LAB361L">Expected Reship Date:</span>
</td><td>
<span id="NBS_LAB361"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB361)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="EIP isolate actual reship date" id="NBS_LAB362L">Actual Reship Date:</span>
</td><td>
<span id="NBS_LAB362"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB362)"  />
</td></tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
