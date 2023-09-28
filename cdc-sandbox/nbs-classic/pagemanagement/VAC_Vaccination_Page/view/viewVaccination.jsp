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
String tabId = "viewVaccination";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Vaccination"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_VAC_UI_7" name="Vaccination Administered" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Please note: Record ALL doses of EVERY vaccine given. Record all information that is known, even data on vaccine doses administered beyond the recommended guidelines.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The information source for this vaccination record." id="VAC147L" >
Vaccine Event Information Source:</span></td><td>
<span id="VAC147" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC147)"
codeSetNm="PHVS_VACCINEEVENTINFORMATIONSOURCE_NND"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date the vaccine was administered" id="VAC103L">Vaccine Administered Date:</span>
</td><td>
<span id="VAC103"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC103)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC105L" title="The person's age at the time the vaccination was given.">
Age At Vaccination:</span>
</td><td>
<span id="VAC105"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC105)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The age units of the person at the time the vaccination was given." id="VAC106L" >
Age At Vaccination Unit:</span></td><td>
<span id="VAC106" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC106)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The anatomical site where the vaccination was given." id="VAC104L" >
Vaccination Anatomical Site:</span></td><td>
<span id="VAC104" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC104)"
codeSetNm="NIP_ANATOMIC_ST"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_VAC_UI_1" name="Administered By" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="VAC117L" title="Provider that gave the vaccination to the patient.">
Vaccination Given By Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC117)"/>
<span id="VAC117">${PageForm.attributeMap.VAC117SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="VAC116L" title="Organization that gave the vaccination to the patient.">
Vaccination Given By Organization:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC116)"/>
<span id="VAC116">${PageForm.attributeMap.VAC116SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The type of vaccine administered for the condition being reported." id="VAC101L" >
Vaccine Type:</span></td><td>
<span id="VAC101" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC101)"
codeSetNm="VAC_NM"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The company which manufactured the vaccine." id="VAC107L" >
Vaccine Manufacturer:</span></td><td>
<span id="VAC107" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC107)"
codeSetNm="VAC_MFGR"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The expiration date for the vaccine administered." id="VAC109L">Vaccine Expiration Date:</span>
</td><td>
<span id="VAC109"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC109)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The lot number for the vaccine administered." id="VAC108L">
Vaccine Lot Number:</span>
</td>
<td>
<span id="VAC108"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC108)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="VAC120L" title="Enter the vaccine dose number in the series of vaccination (e.g. 1, 2, 3, etc.)">
Dose Number:</span>
</td><td>
<span id="VAC120"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC120)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_VAC_UI_5" name="Vaccine Schedule Links" isHidden="F" classType="subSect" >

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://www.cdc.gov/vaccines/schedules/downloads/adult/adult-schedule.pdf" TARGET="_blank">Adult Schedule (Over 18 years)</a></td></tr>

<!--processing Hyperlink-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;<a href="https://www.cdc.gov/vaccines/schedules/downloads/child/0-18yrs-child-combined-schedule.pdf" TARGET="_blank">Child Schedule (0-18 years)</a></td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
