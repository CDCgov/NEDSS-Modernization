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
String tabId = "viewContactFollowUp";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Contact Follow Up"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17108" name="Sign and Symptoms" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the contact has/had any signs/symptoms for this condition." id="CON128L" >
Were there any signs/symptoms for this illness?:</span></td><td>
<span id="CON128" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON128)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The onset date of the symptom(s); i.e. when did the symptom first appear." id="CON129L">Symptom Onset Date:</span>
</td><td>
<span id="CON129"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON129)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Notes pertinent to the signs and symptoms." id="CON130L">
Signs &amp; Symptoms Notes:</span>
</td>
<td>
<span id="CON130"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON130)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17109" name="Risk Factors" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the contact has any risk factors for this condition." id="CON131L" >
Were there any risk factors for this illness?:</span></td><td>
<span id="CON131" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON131)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Notes pertinent to the risk factors." id="CON132L">
Risk Factor Notes:</span>
</td>
<td>
<span id="CON132"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON132)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17110" name="Testing and Evaluation" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the contact has been tested and/or evaluated for this condition." id="CON117L" >
Was testing/evaluation completed for this illness?:</span></td><td>
<span id="CON117" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON117)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date that the contact was evaluated." id="CON118L">Date of Evaluation:</span>
</td><td>
<span id="CON118"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON118)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="A textual description of the fndings from the evaluation." id="CON119L">
Evaluation Findings:</span>
</td>
<td>
<span id="CON119"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON119)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA17111" name="Treatment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the contact has been treated for this condition." id="CON120L" >
Was treatment initiated for this illness?:</span></td><td>
<span id="CON120" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON120)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the treatment was started." id="CON121L">Treatment Start Date:</span>
</td><td>
<span id="CON121"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON121)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If treatment was not started, the reason the treatment was not started." id="CON122L" >
Reason Treatment Not Started:</span></td><td>
<span id="CON122" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON122)"
codeSetNm="NBS_NO_TRTMNT_REAS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indication of whether the treatment for this condition was completed." id="CON123L" >
Was treatment completed?:</span></td><td>
<span id="CON123" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON123)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="The date the treatment was ended." id="CON124L">Treatment End Date:</span>
</td><td>
<span id="CON124"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON124)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If treatment was not completed, the reason the treatment was not completed." id="CON125L" >
Reason Treatment Not Completed:</span></td><td>
<span id="CON125" />
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON125)"
codeSetNm="NBS_NO_TRTMNT_REAS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Notes pertinent to the treatment." id="CON126L">
Treatment Notes:</span>
</td>
<td>
<span id="CON126"/>
<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON126)"  />
</td> </tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
