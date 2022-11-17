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
String [] sectionNames  = {"Chronic Hepatitis Infection"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPCHBC_UI_3" name="Chronic Hepatitis C Only" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive a blood transfusion prior to 1992?" id="INV645L" >
Did the patient receive a blood transfusion prior to 1992?:</span></td><td>
<span id="INV645" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV645)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive an organ transplant prior to 1992?" id="INV646L" >
Did the patient receive an organ transplant prior to 1992?:</span></td><td>
<span id="INV646" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV646)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPCHBC_UI_4" name="Risk Factors" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the patient receive clotting factor concentrates prior to 1987?" id="INV647L" >
Did the patient receive clotting factor concentrates prior to 1987?:</span></td><td>
<span id="INV647" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV647)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient ever on long-term hemodialysis?" id="INV644L" >
Was the patient ever on long-term hemodialysis?:</span></td><td>
<span id="INV644" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV644)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Has the patient ever injected drugs (those not prescribed by a doctor) even if only once or a few times?" id="INV643L" >
Has the patient ever injected drugs not prescribed by a doctor?:</span></td><td>
<span id="INV643" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV643)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="INV642L" title="How many sex partners has the patient had (approximate lifetime)?">
How many sex partners has the patient had?:</span>
</td><td>
<span id="INV642"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV642)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient ever incarcerated?" id="INV649L" >
Was the patient ever incarcerated?:</span></td><td>
<span id="INV649" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV649)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient ever treated for a sexually transmitted disease?" id="INV653bL" >
Was the patient ever treated for a sexually transmitted disease?:</span></td><td>
<span id="INV653b" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV653b)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient ever a contact of a  person who had hepatitis?" id="INV829L" >
Was the patient ever a contact of a  person who had hepatitis?:</span></td><td>
<span id="INV829" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV829)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPCHBC_UI_5" name="Contact Types" isHidden="F" classType="subSect" >

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
<span title="If the patient is a contact of a confirmed or suspected case, is the contact a household member (non-sexual)?" id="INV603_3L" >
Household Member (Non-Sexual) (Contact Type):</span></td><td>
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

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPCHBC_UI_6" name="Risk Factors Continued" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient ever employed in a medical or dental field  involving direct contact with human blood?" id="INV648L" >
Patient ever employed in a medical or dental field involving direct contact with human blood?:</span></td><td>
<span id="INV648" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV648)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="What is the birth country of the mother?" id="MTH109L" >
What is the birth country of the mother?:</span></td><td>
<span id="MTH109" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH109)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

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
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
