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
String tabId = "viewInterviewDetails";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Interview Details"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA24102" name="Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The status of the interview." id="IXS100L" >
Interview Status:</span></td><td>
<span id="IXS100" />
<nedss:view name="PageForm" property="pageClientVO.answer(IXS100)"
codeSetNm="NBS_INTVW_STATUS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The date of the interview." id="IXS101L">Date of Interview:</span>
</td><td>
<span id="IXS101"/>
<nedss:view name="PageForm" property="pageClientVO.answer(IXS101)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span style="color:#CC0000">*</span>
<span id="IXS102L" title="The person performing the interview.">
Interviewer:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(IXS102)"/>
<span id="IXS102">${PageForm.attributeMap.IXS102SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The role of the interviewee." id="IXS103L" >
Interviewee Role:</span></td><td>
<span id="IXS103" />
<nedss:view name="PageForm" property="pageClientVO.answer(IXS103)"
codeSetNm="NBS_INTVWEE_ROLE"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="IXS104L" title="The subject of the interview (i.e., person interviewed).">
Interviewee:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(IXS104)"/>
<span id="IXS104">${PageForm.attributeMap.IXS104SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The type of interview (e.g., initial interview)" id="IXS105L" >
Interview Type:</span></td><td>
<span id="IXS105" />
<nedss:view name="PageForm" property="pageClientVO.answer(IXS105)"
codeSetNm="NBS_INTERVIEW_TYPE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The location of the interview (e.g., phone, clinic, etc.)." id="IXS106L" >
Interview Location:</span></td><td>
<span id="IXS106" />
<nedss:view name="PageForm" property="pageClientVO.answer(IXS106)"
codeSetNm="NBS_INTVW_LOC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="GA25100" name="Interview Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "GA25100"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyGA25100">
<tr id="patternGA25100" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'GA25100');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyGA25100">
<tr id="nopatternGA25100" class="odd" style="display:none">
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

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="The interview notes that were recorded during the interview process." id="IXS111L">
Interview Notes:</span>
</td>
<td>
<span id="IXS111"/>
<nedss:view name="PageForm" property="pageClientVO.answer(IXS111)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>
</div> </td></tr>
