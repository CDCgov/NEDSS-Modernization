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
String tabId = "viewSusceptibilityTest";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Susceptibility Test"};
;
%>
<tr><td>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2" name="Related Tests and Results" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_UI_2"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_UI_2">
<tr id="patternNBS_UI_2" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_UI_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_UI_2">
<tr id="nopatternNBS_UI_2" class="odd" style="display:none">
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

<!--processing Coded With Search Question-->
<tr>
<td class="fieldName" valign="top">
<span style="color:#CC0000">*</span>
<span title="The description for the result test code, i.e. test name." id="NBS_LAB110L" >
Drug Name:</span></td><td>
<span id="NBS_LAB110" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB110)"
codeSetNm="LAB_TEST"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the susceptibility test technique or method used, (e.g., serum neutralization, titration, dipstick, test strip, anaerobic culture)." id="NBS377L" >
Result Method:</span></td><td>
<span id="NBS377" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS377)"
codeSetNm="OBS_METH_SUSC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the susceptibility test numeric result first numeric value." id="NBS369L">
Numeric Result:</span>
</td>
<td>
<span id="NBS369"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS369)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the susceptibility test numeric result units of measure." id="NBS372L" >
Units:</span></td><td>
<span id="NBS372" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS372)"
codeSetNm="UNIT_ISO"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the susceptibility test coded result (non-organism)." id="NBS367L" >
Coded Result:</span></td><td>
<span id="NBS367" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS367)"
codeSetNm="CODED_LAB_RESULT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the susceptibility test text result." id="NBS365L">
Text Result:</span>
</td>
<td>
<span id="NBS365"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS365)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the susceptibility test reference range low value." id="NBS373L">
Reference Range - From:</span>
</td>
<td>
<span id="NBS373"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS373)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the susceptibility test reference range high value." id="NBS374L">
Reference Range - To:</span>
</td>
<td>
<span id="NBS374"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS374)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the susceptibility test interpretation. The interpretation flag identifies a result that is not typical, as well as how its not typical (e.g., susceptible, resistant, normal, above upper panic limits, below absolute low)." id="NBS378L" >
Interpretation:</span></td><td>
<span id="NBS378" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS378)"
codeSetNm="OBS_INTRP"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicates the date the specimen was collected." id="NBS405L">Lab Report Date:</span>
</td><td>
<span id="NBS405"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS405)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the susceptibility test result status." id="NBS376L" >
Status:</span></td><td>
<span id="NBS376" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS376)"
codeSetNm="ACT_OBJ_ST"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the resulted test; in most cases this is a standardized test (LOINC) test." id="NBS_LAB293_1L" >
Test Code(s):</span></td><td>
<span id="NBS_LAB293_1" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB293_1)"
codeSetNm="RESULTED_LAB_TEST"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicates the coded result value text description, (e.g., positive, detected, etc.)" id="NBS_LAB121_1L" >
Result Code(s):</span></td><td>
<span id="NBS_LAB121_1" />
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS_LAB121_1)"
codeSetNm="CODED_LAB_RESULT"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td valign="top" class="fieldName">
<span title="Indicates the susceptibility test result comments (notes and comment related to the result being reported)." id="NBS375L">
Result Comments:</span>
</td>
<td>
<span id="NBS375"/>
<nedss:view name="PageSubForm" property="pageClientVO.answer(NBS375)"  />
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td valign="top" class="fieldName">
<span class="InputDisabledLabel" id="NBS455L" title="Provide context of the Lab Report. That is if Lab is created Internally, Externally or Electronically.">Electronic Indicator:</span>
</td><td><html:select name="PageSubForm" property="pageClientVO.answer(NBS455)" styleId="NBS455"><html:optionsCollection property="codedValue(DATA_SOURCE_TYPE)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>
</div> </td></tr>
