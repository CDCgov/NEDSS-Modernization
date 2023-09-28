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
String [] sectionNames  = {"Perinatal Hep B Mother Information","Infant Information"};
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
<nedss:container id="NBS_INV_HEPBP_3" name="Mother Race and Ethnicity" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page." id="NBS387L" >
Next of Kin Relationship:</span></td><td>
<span id="NBS387" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS387)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="MTH157L" title="Enter the race of the patient's mother">
Race of Mother:</span></td><td>
<span id="MTH157" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(MTH157)"
codeSetNm="PHVS_RACECATEGORY_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If other race is selected for the mother, please specify" id="MTH171L">
Other Race for Mother (specify):</span>
</td>
<td>
<span id="MTH171"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH171)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Ethnicity of the patient's mother" id="MTH159L" >
Ethnicity of Mother:</span></td><td>
<span id="MTH159" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH159)"
codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
</td> </tr>
<!--skipping Hidden Text Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the patient's mother born outside of the United States?" id="MTH161L" >
Was Mother born outside of United States?:</span></td><td>
<span id="MTH161" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH161)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="What is the birth country of the mother" id="MTH109L" >
What is the birth country of the mother?:</span></td><td>
<span id="MTH109" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH109)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_4" name="Mother HBsAg Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the mother confirmed positive for the illness being reported prior to or at the time of delivery?" id="MTH162L" >
Was the mother confirmed positive prior to or at the time of delivery?:</span></td><td>
<span id="MTH162" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH162)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the mother confirmed positive for the illness being reported after delivery?" id="MTH163L" >
If no, was the mother confirmed positive after delivery?:</span></td><td>
<span id="MTH163" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH163)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the date of the mother's earliest postive test result" id="MTH164L">Date of Earliest Positive Test Result:</span>
</td><td>
<span id="MTH164"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH164)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_6" name="Hepatitis Containing Vaccination" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Has the child ever received a vaccination for Hepatitis B?" id="VAC126L" >
Has the child ever received a vaccination for Hepatitis B?:</span></td><td>
<span id="VAC126" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC132L" title="Total number of doses of vaccine the child received. Enter a numeric value based on the category on the form.">
How many doses of Hepatitis B vaccine did the child receive?:</span>
</td><td>
<span id="VAC132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC132)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_7" name="Vaccine Doses Received" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_HEPBP_7"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_HEPBP_7">
<tr id="patternNBS_INV_HEPBP_7" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_HEPBP_7');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_HEPBP_7">
<tr id="nopatternNBS_INV_HEPBP_7" class="odd" style="display:none">
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

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="VAC120L" title="Enter the vaccine dose number in the series of vaccination (e.g. 1, 2, 3, etc.)">
Dose Number:</span>
</td><td>
<span id="VAC120"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the date the vaccine was administered" id="VAC103L">Date of Vaccination:</span>
</td><td>
<span id="VAC103"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC103)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_HEPBP_8" name="HBIG Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Has the patient ever received immune globulin for this condition?" id="VAC143L" >
Did the child receive hepatitis B immune globulin (HBIG)?:</span></td><td>
<span id="VAC143" />
<nedss:view name="PageForm" property="pageClientVO.answer(VAC143)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date the child received the dose of HBIG." id="VAC144L">If yes, on what date did the child receive HBIG?:</span>
</td><td>
<span id="VAC144"/>
<nedss:view name="PageForm" property="pageClientVO.answer(VAC144)"  />
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
