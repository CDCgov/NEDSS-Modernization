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
String tabId = "view";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Patient Information","Address Information","Telephone and Email Contact Information","Race and Ethnicity Information","Other Identifying Information","Investigation Information","OOJ Initiating Agency Information","Reporting Information","Clinical","Epidemiologic","Comments","Case Numbers","Initial Follow-up","Surveillance","Notification of Exposure Information","Field Follow-up Information","Interview Case Assignment","Case Closure","Pregnant Information","900 Case Status","Risk Factors-Last 12 Months","Hangouts","Partner Information","Target Populations","STD Testing","Signs and Symptoms","STD History","900 Partner Services Information","Contact Investigation"};
;
%>
<tr><td>
<div style="float:left;width:100% ">    <%@ include file="/pagemanagement/patient/PatientSummaryCompare1.jsp" %> </div>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_6" name="General Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="As of Date is the last known date for which the information is valid." id="NBS104L">Information As of Date:</span>
</td><td>
<span id="NBS104"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS104)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="General comments pertaining to the patient." id="DEM196L">
Comments:</span>
</td>
<td>
<span id="DEM196"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM196)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_7" name="Name Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS095L">Name Information As Of Date:</span>
</td><td>
<span id="NBS095"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS095)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's first name." id="DEM104L">
First Name:</span>
</td>
<td>
<span id="DEM104"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM104)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's middle name or initial." id="DEM105L">
Middle Name:</span>
</td>
<td>
<span id="DEM105"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM105)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's last name." id="DEM102L">
Last Name:</span>
</td>
<td>
<span id="DEM102"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM102)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The patient's name suffix" id="DEM107L" >
Suffix:</span></td><td>
<span id="DEM107" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM107)"
codeSetNm="P_NM_SFX"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's alias or nickname." id="DEM250L">
Alias/Nickname:</span>
</td>
<td>
<span id="DEM250"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM250)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_8" name="Other Personal Details" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS096L">Other Personal Details As Of Date:</span>
</td><td>
<span id="NBS096"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS096)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Reported date of birth of patient." id="DEM115L">Date of Birth:</span>
</td><td>
<span id="DEM115"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM115)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV2001L" title="The patient's age reported at the time of interview.">
Reported Age:</span>
</td><td>
<span id="INV2001"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV2001)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's age units" id="INV2002L" >
Reported Age Units:</span></td><td>
<span id="INV2002" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV2002)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's current sex." id="DEM113L" >
Current Sex:</span></td><td>
<span id="DEM113" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM113)"
codeSetNm="SEX"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's current sex if identified as unknown (i.e., not male or female)." id="NBS272L" >
Unknown Reason:</span></td><td>
<span id="NBS272" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS272)"
codeSetNm="SEX_UNK_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's transgender identity." id="NBS274L" >
Gender Identity/Transgender Info:</span></td><td>
<span id="NBS274" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS274)"
codeSetNm="NBS_STD_GENDER_PARPT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The specific gender information of the index patient if other selections do not apply (i.e. intersex, two-spirited, etc.)." id="NBS213L">
Additional Gender:</span>
</td>
<td>
<span id="NBS213"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS213)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient Birth Sex" id="DEM114L" >
Birth Sex:</span></td><td>
<span id="DEM114" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM114)"
codeSetNm="SEX"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient identified sexual orientation (i.e., an individual's physical and/or emotional attraction to another individual of the same gender, opposite gender, or both genders)." id="INV592L" >
Sexual Orientation/Preference:</span></td><td>
<span id="INV592" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV592)"
codeSetNm="PHVS_SEXUALORIENTATION_CDC"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Patient identified sexual orientation (i.e., an individual's physical and/or emotional attraction to another individual of the same gender, opposite gender, or both genders)." id="INV592OthL">Other Sexual Orientation/Preference:</span></td>
<td> <span id="INV592Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV592Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS097L">Mortality Information As Of Date:</span>
</td><td>
<span id="NBS097"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS097)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Country of Birth" id="DEM126L" >
Country of Birth:</span></td><td>
<span id="DEM126" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM126)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicator of whether or not a patient is alive or dead." id="DEM127L" >
Is the patient deceased?:</span></td><td>
<span id="DEM127" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM127)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date on which the individual died." id="DEM128L">Deceased Date:</span>
</td><td>
<span id="DEM128"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM128)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS098L">Marital Status As Of Date:</span>
</td><td>
<span id="NBS098"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS098)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="A code indicating the married or similar partnership status of a patient." id="DEM140L" >
Marital Status:</span></td><td>
<span id="DEM140" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM140)"
codeSetNm="P_MARITAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Patient SSN as of Date" id="NBS451L">SSN as of Date:</span>
</td><td>
<span id="NBS451"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS451)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Patient SSN" id="DEM133L">
SSN:</span>
</td>
<td>
<span id="DEM133"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM133)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="ENTITYID100" name="Entity ID Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "ENTITYID100"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyENTITYID100">
<tr id="patternENTITYID100" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'ENTITYID100');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyENTITYID100">
<tr id="nopatternENTITYID100" class="odd" style="display:none">
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

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Entity ID as of Date." id="NBS452L">As Of:</span>
</td><td>
<span id="NBS452"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS452)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Entity ID type for patient" id="DEM144L" >
Type:</span></td><td>
<span id="DEM144" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM144)"
codeSetNm="EI_TYPE_PAT"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Entity ID type for patient" id="DEM144OthL">Other Type:</span></td>
<td> <span id="DEM144Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(DEM144Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Assigning Authority of Patient ID" id="DEM146L" >
Authority:</span></td><td>
<span id="DEM146" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM146)"
codeSetNm="EI_AUTH_PAT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Patient ID Value" id="DEM147L">
Value:</span>
</td>
<td>
<span id="DEM147"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM147)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_15" name="Reporting Address for Case Counting" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS102L">Address Information As Of Date:</span>
</td><td>
<span id="NBS102"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS102)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Line one of the address label." id="DEM159L">
Street Address 1:</span>
</td>
<td>
<span id="DEM159"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM159)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Line two of the address label." id="DEM160L">
Street Address 2:</span>
</td>
<td>
<span id="DEM160"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The city for a postal location." id="DEM161L">
City:</span>
</td>
<td>
<span id="DEM161"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM161)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The state code for a postal location." id="DEM162L" >
State:</span></td><td>
<span id="DEM162" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM162)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The zip code of a residence of the case patient or entity." id="DEM163L">
Zip:</span>
</td>
<td>
<span id="DEM163"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM163)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The county of residence of the case patient or entity." id="DEM165L" >
County:</span></td><td>
<span id="DEM165" />
<logic:notEmpty name="PageForm" property="pageClientVO.answer(DEM165)">
<logic:notEmpty name="PageForm" property="pageClientVO.answer(DEM162)">
<bean:define id="value" name="PageForm" property="pageClientVO.answer(DEM162)"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM165)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.DEM165_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts." id="DEM168L">
Census Tract:</span>
</td>
<td>
<span id="DEM168"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM168)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The country code for a postal location." id="DEM167L" >
Country:</span></td><td>
<span id="DEM167" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM167)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_2" name="Additional Residence Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The RELATIONSHIP (such as spouse, parents, sibling, partner, roommate, etc., not the name) of those living with the patient." id="NBS201L">
Living With:</span>
</td>
<td>
<span id="NBS201"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS201)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The type of residence in which the patient currenlty resides." id="NBS202L" >
Type of Residence:</span></td><td>
<span id="NBS202" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS202)"
codeSetNm="RESIDENCE_TYPE_STD"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS203L" title="The length of time the patient has lived at the current address.">
Time at Address:</span>
</td><td>
<span id="NBS203"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS203)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time at address." id="NBS204L" >
Units:</span></td><td>
<span id="NBS204" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS204)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS205L" title="The length of time the patient has lived in this state/territory.">
Time in State:</span>
</td><td>
<span id="NBS205"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS205)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time in state." id="NBS206L" >
Units:</span></td><td>
<span id="NBS206" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS206)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS207L" title="The length of time the patient has lived in the country.">
Time in Country:</span>
</td><td>
<span id="NBS207"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS207)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time in country." id="NBS208L" >
Units:</span></td><td>
<span id="NBS208" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS208)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the patient is institutionalized (i.e., in jail, in a group home, in a mental health facility, etc.)" id="NBS209L" >
Currently institutionalized:</span></td><td>
<span id="NBS209" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS209)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Name of Institutition" id="NBS210L">
If institutionalized, document the name of the facility.:</span>
</td>
<td>
<span id="NBS210"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS210)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of Institutition" id="NBS211L" >
Type of Institutition:</span></td><td>
<span id="NBS211" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS211)"
codeSetNm="INSTITUTION_TYPE_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_16" name="Telephone Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS103L">Telephone Information As Of Date:</span>
</td><td>
<span id="NBS103"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS103)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's home phone number." id="DEM177L">
Home Phone:</span>
</td>
<td>
<span id="DEM177"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM177)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's work phone number." id="NBS002L">
Work Phone:</span>
</td>
<td>
<span id="NBS002"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS002)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS003L" title="The patient's work phone number extension.">
Ext.:</span>
</td><td>
<span id="NBS003"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS003)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's cellular phone number." id="NBS006L">
Cell Phone:</span>
</td>
<td>
<span id="NBS006"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS006)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's email address." id="DEM182L">
Email:</span>
</td>
<td>
<span id="DEM182"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM182)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_9" name="Ethnicity and Race Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS100L">Ethnicity Information As Of Date:</span>
</td><td>
<span id="NBS100"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS100)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates if the patient is hispanic or not." id="DEM155L" >
Ethnicity:</span></td><td>
<span id="DEM155" />
<nedss:view name="PageForm" property="pageClientVO.answer(DEM155)"
codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Specify reason the patient's ethnicity is unknown." id="NBS273L" >
Reason Unknown:</span></td><td>
<span id="NBS273" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS273)"
codeSetNm="P_ETHN_UNK_REASON"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS101L">Race Information As Of Date:</span>
</td><td>
<span id="NBS101"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS101)"  />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Reported race; supports collection of multiple race categories.  This field could repeat." id="DEM152L">
Race:</span>
</td>
<td>
<div id="patientRacesViewContainer">
<logic:equal name="PageForm" property="pageClientVO.americanIndianAlskanRace" value="1"><bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.africanAmericanRace" value="1"><bean:message bundle="RVCT" key="rvct.black.or.african.american"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.whiteRace" value="1"><bean:message bundle="RVCT" key="rvct.white"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.asianRace" value="1"><bean:message bundle="RVCT" key="rvct.asian"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.hawaiianRace" value="1"><bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.otherRace" value="1"><bean:message bundle="RVCT" key="rvct.other"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.refusedToAnswer" value="1"><bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.notAsked" value="1"><bean:message bundle="RVCT" key="rvct.notAsked"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO.unKnownRace" value="1"><bean:message bundle="RVCT" key="rvct.unknown"/>,</logic:equal>
</div>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_6" name="Other Identifying Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific height of the patient." id="NBS155L">
Height:</span>
</td>
<td>
<span id="NBS155"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS155)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific weight or body type of the patient." id="NBS156L">
Size/Build:</span>
</td>
<td>
<span id="NBS156"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS156)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The description of the patients hair, including color, length, and/or style." id="NBS157L">
Hair:</span>
</td>
<td>
<span id="NBS157"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS157)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific skin tone/hue of the patient." id="NBS158L">
Complexion:</span>
</td>
<td>
<span id="NBS158"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS158)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Any additional demographic information (e.g., tattoos, etc)." id="NBS159L">
Other Identifying Info:</span>
</td>
<td>
<span id="NBS159"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS159)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_19" name="Investigation Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The jurisdiction of the investigation." id="INV107L" >
Jurisdiction:</span></td><td>
<span id="INV107" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV107)"
codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The program area associated with the investigaiton condition." id="INV108L" >
Program Area:</span></td><td>
<span id="INV108" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV108)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Document the reason (referral basis) why the investigation was initiated." id="NBS110L" >
Referral Basis:</span></td><td>
<span id="NBS110" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS110)"
codeSetNm="REFERRAL_BASIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The status of the investigation." id="INV109L" >
Investigation Status:</span></td><td>
<span id="INV109" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV109)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)" id="NBS115L" >
Current Process Stage:</span></td><td>
<span id="NBS115" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS115)"
codeSetNm="CM_PROCESS_STAGE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation was started or initiated." id="INV147L">Investigation Start Date:</span>
</td><td>
<span id="INV147"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV147)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation is closed." id="INV2006L">Investigation Close Date:</span>
</td><td>
<span id="INV2006"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV2006)"  />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr> <td class="fieldName">
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
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="NBS270L" title="Referral Basis - OOJ">Referral Basis - OOJ:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(NBS270)" styleId="NBS270"><html:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
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
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_8" name="OOJ Agency Initiating Report" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The Initiating Agency which sent the OOJ Contact Report." id="NBS111L" >
Initiating Agency:</span></td><td>
<span id="NBS111" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS111)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the OOJ Contact report was received from the Initiating Agency." id="NBS112L">Date Received from Init. Agency:</span>
</td><td>
<span id="NBS112"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS112)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date OOJ outcome is due back to the Initiating Agency." id="NBS113L">Date OOJ Due to Init. Agency:</span>
</td><td>
<span id="NBS113"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS113)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date OOJ outcome was sent back to the Initiating Agency." id="NBS114L">Date OOJ Info Sent:</span>
</td><td>
<span id="NBS114"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS114)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_9" name="Reported as OOJ Contact" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of first exposure with sex partner reported  by OOJ Contact." id="NBS118L">First Sexual Exposure:</span>
</td><td>
<span id="NBS118"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS118)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The frequency of exposure with sex partner reported by OOJ Contact." id="NBS119L">
Sexual Frequency:</span>
</td>
<td>
<span id="NBS119"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS119)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of last exposure with sex partner reported by OOJ Contact." id="NBS120L">Last Sexual Exposure:</span>
</td><td>
<span id="NBS120"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of first exposure with needle-sharing partner reported by OOJ Contact." id="NBS121L">First Needle-Sharing Exposure:</span>
</td><td>
<span id="NBS121"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS121)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The frequency of exposure with needle-sharing partner reported by OOJ Contact." id="NBS122L">
Needle-Sharing Frequency:</span>
</td>
<td>
<span id="NBS122"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS122)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of last exposure with needle-sharing partner reported by OOJ Contact." id="NBS123L">Last Needle-Sharing Exposure:</span>
</td><td>
<span id="NBS123"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS123)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The cluster relationship reported by OOJ Contact." id="NBS124L" >
Relationship:</span></td><td>
<span id="NBS124" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS124)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="As reported by OOJ Contact, is the patient the original patient's spouse?" id="NBS125L" >
OP Spouse?:</span></td><td>
<span id="NBS125" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS125)"
codeSetNm="OP_SPOUSE_IND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="As reported by OOJ Contact, did the patient meet the original patient on the internet?" id="NBS126L" >
Met OP via Internet?:</span></td><td>
<span id="NBS126" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During the course of the interview, did the DIS elicit internet information about the named contact?" id="NBS127L" >
Internet Info Elicited?:</span></td><td>
<span id="NBS127" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS127)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_22" name="Key Report Dates" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of report of the condition to the public health department." id="INV111L">Date of Report:</span>
</td><td>
<span id="INV111"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV111)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Earliest date reported to county public health system." id="INV120L">Earliest Date Reported to County:</span>
</td><td>
<span id="INV120"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
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
<td class="fieldName">
<span title="Type of facility or provider associated with the source of information sent to Public Health." id="INV112L" >
Reporting Source Type:</span></td><td>
<span id="INV112" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV112)"
codeSetNm="PHVS_REPORTINGSOURCETYPE_NND"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
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
<td class="fieldName">
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
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_12" name="Physician" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
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
<td class="fieldName">
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
<td class="fieldName">
<span title="Was the patient hospitalized as a result of this event?" id="INV128L" >
Was the patient hospitalized for this illness?:</span></td><td>
<span id="INV128" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV128)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV184L" title="The hospital associated with the investigation.">
Hospital:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(INV184)"/>
<span id="INV184">${PageForm.attributeMap.INV184SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's admission date to the hospital for the condition covered by the investigation." id="INV132L">Admission Date:</span>
</td><td>
<span id="INV132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV132)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's discharge date from the hospital for the condition covered by the investigation." id="INV133L">Discharge Date:</span>
</td><td>
<span id="INV133"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV133)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
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
<tr><td class="fieldName">
<span title="Date of diagnosis of condition being reported to public health system." id="INV136L">Diagnosis Date:</span>
</td><td>
<span id="INV136"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV136)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." id="INV137L">Illness Onset Date:</span>
</td><td>
<span id="INV137"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV137)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The time at which the disease or condition ends." id="INV138L">Illness End Date:</span>
</td><td>
<span id="INV138"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV138)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV139L" title="The length of time this person had this disease or condition.">
Illness Duration:</span>
</td><td>
<span id="INV139"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV139)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit of time used to describe the length of the illness or condition." id="INV140L" >
Illness Duration Units:</span></td><td>
<span id="INV140" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV140)"
codeSetNm="PHVS_DURATIONUNIT_CDC"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV143L" title="Subject's age at the onset of the disease or condition.">
Age at Onset:</span>
</td><td>
<span id="INV143"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV143)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The age units for an age." id="INV144L" >
Age at Onset Units:</span></td><td>
<span id="INV144" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV144)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates if the subject dies as a result of the illness." id="INV145L" >
Did the patient die from this illness?:</span></td><td>
<span id="INV145" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV145)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the subject’s death occurred." id="INV146L">Date of Death:</span>
</td><td>
<span id="INV146"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV146)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date treatment initiated for the condition that is the subject of this case report." id="STD105L">Treatment Start Date:</span>
</td><td>
<span id="STD105"/>
<nedss:view name="PageForm" property="pageClientVO.answer(STD105)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date." id="STD099L">Date of Initial Health Exam:</span>
</td><td>
<span id="STD099"/>
<nedss:view name="PageForm" property="pageClientVO.answer(STD099)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25" name="Epi-Link" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility." id="INV148L" >
Is this person associated with a day care facility?:</span></td><td>
<span id="INV148" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether the subject of the investigation was food handler." id="INV149L" >
Is this person a food handler?:</span></td><td>
<span id="INV149" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV149)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Denotes whether the reported case was associated with an identified outbreak." id="INV150L" >
Is this case part of an outbreak?:</span></td><td>
<span id="INV150" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV150)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
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
<td class="fieldName">
<span title="Indication of where the disease/condition was likely acquired." id="INV152L" >
Where was the disease acquired:</span></td><td>
<span id="INV152" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV152)"
codeSetNm="PHVS_DISEASEACQUIREDJURISDICTION_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, indicate the country in which the disease was likely acquired." id="INV153L" >
Imported Country:</span></td><td>
<span id="INV153" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV153)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, indicate the state in which the disease was likely acquired." id="INV154L" >
Imported State:</span></td><td>
<span id="INV154" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV154)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the disease or condition was imported, indicate the city in which the disease was likely acquired." id="INV155L">
Imported City:</span>
</td>
<td>
<span id="INV155"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV155)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
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
<td class="fieldName">
<span title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient." id="NBS135L" >
Source/Spread:</span></td><td>
<span id="NBS135" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS135)"
codeSetNm="SOURCE_SPREAD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
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
<td class="fieldName">
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
<td class="fieldName">
<span title="Indicates the country in which the disease was potentially acquired." id="INV502L" >
Country of Exposure:</span></td><td>
<span id="INV502" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV502)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the state in which the disease was potentially acquired." id="INV503L" >
State or Province of Exposure:</span></td><td>
<span id="INV503" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV503)"
codeSetNm="PHVS_STATEPROVINCEOFEXPOSURE_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Indicates the city in which the disease was potentially acquired." id="INV504L">
City of Exposure:</span>
</td>
<td>
<span id="INV504"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV504)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
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
<td class="fieldName">
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
<td class="fieldName">
<span title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate." id="INV157L" >
Transmission Mode:</span></td><td>
<span id="INV157" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV157)"
codeSetNm="PHC_TRAN_M"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc." id="INV159L" >
Detection Method:</span></td><td>
<span id="INV159" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV159)"
codeSetNm="PHVS_DETECTIONMETHOD_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV161L" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived.">
Confirmation Method:</span></td><td>
<span id="INV161" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV161)"
codeSetNm="PHC_CONF_M"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If an investigation is confirmed as a case, then the confirmation date is entered." id="INV162L">Confirmation Date:</span>
</td><td>
<span id="INV162"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV162)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The current status of the investigation/case." id="INV163L" >
Case Status:</span></td><td>
<span id="INV163" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV163)"
codeSetNm="PHVS_PHC_CLASS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The disease diagnosis of the patient." id="NBS136L" >
Diagnosis Reported to CDC:</span></td><td>
<span id="NBS136" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS136)"
codeSetNm="CASE_DIAGNOSIS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The MMWR week in which the case should be counted." id="INV165L">
MMWR Week:</span>
</td>
<td>
<span id="INV165"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV165)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The MMWR year in which the case should be counted." id="INV166L">
MMWR Year:</span>
</td>
<td>
<span id="INV166"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV166)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" id="NOT120L" >
Immediate National Notifiable Condition:</span></td><td>
<span id="NOT120" />
<nedss:view name="PageForm" property="pageClientVO.answer(NOT120)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." id="NOT120SPECL">
If Yes, describe:</span>
</td>
<td>
<span id="NOT120SPEC"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NOT120SPEC)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
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
<td class="fieldName">
<span title="Neurological Involvement?" id="STD102L" >
Neurological Manifestations:</span></td><td>
<span id="STD102" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD102)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="102957003L" title="What neurologic manifestations of syphilis are present?">
Neurologic Signs/Symptoms:</span></td><td>
<span id="102957003" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(102957003)"
codeSetNm="PHVS_NEUROLOGICALMANIFESTATION_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="What neurologic manifestations of syphilis are present?" id="102957003OthL">Other Neurologic Signs/Symptoms:</span></td>
<td> <span id="102957003Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(102957003Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis." id="410478005L" >
Ocular Manifestations:</span></td><td>
<span id="410478005" />
<nedss:view name="PageForm" property="pageClientVO.answer(410478005)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo." id="PHC1472L" >
Otic Manifestations:</span></td><td>
<span id="PHC1472" />
<nedss:view name="PageForm" property="pageClientVO.answer(PHC1472)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
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
<td class="fieldName">
<span title="Pelvic inflammatory disease present?" id="INV179L" >
PID:</span></td><td>
<span id="INV179" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV179)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Disseminated?" id="NBS137L" >
Disseminated:</span></td><td>
<span id="NBS137" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS137)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Conjunctivitis?" id="INV361L" >
Conjunctivitis:</span></td><td>
<span id="INV361" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV361)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="To what drug(s) is the disease resistant?" id="NBS138L" >
Resistant to:</span></td><td>
<span id="NBS138" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS138)"
codeSetNm="RESISTANT_TO_300_DRUG"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_27" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Field which contains general comments for the investigation." id="INV167L">
General Comments:</span>
</td>
<td>
<span id="INV167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV167)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_12" name="Case Numbers" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Unique field record identifier." id="NBS160L">
Field Record Number:</span>
</td>
<td>
<span id="NBS160"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191L">
Lot Number:</span>
</td>
<td>
<span id="NBS191"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS191)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." id="INV200L">
Legacy Case ID:</span>
</td>
<td>
<span id="INV200"/>
<nedss:view name="PageForm" property="pageClientVO.answer(INV200)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_14" name="Initial Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS139L" title="The investigator assigning the initial follow-up.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS139)"/>
<span id="NBS139">${PageForm.attributeMap.NBS139SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Initial Follow-up action." id="NBS140L" >
Initial Follow-Up:</span></td><td>
<span id="NBS140" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS140)"
codeSetNm="STD_NBS_PROCESSING_DECISION_ALL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the inital follow-up was identified as closed." id="NBS141L">Date Closed:</span>
</td><td>
<span id="NBS141"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS141)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Initiate for Internet follow-up?" id="NBS142L" >
Internet Follow-Up:</span></td><td>
<span id="NBS142" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS142)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If applicable, enter the specific clinic code identifying the initiating clinic." id="NBS144L">
Clinic Code:</span>
</td>
<td>
<span id="NBS144"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS144)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_16" name="Surveillance Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS145L" title="The investigator assigned for surveillance follow-up.">
Assigned To:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS145)"/>
<span id="NBS145">${PageForm.attributeMap.NBS145SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date surveillance follow-up is assigned." id="NBS146L">Date Assigned:</span>
</td><td>
<span id="NBS146"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS146)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date surveillance follow-up is completed." id="NBS147L">Date Closed:</span>
</td><td>
<span id="NBS147"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS147)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_17" name="Surveillance Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the contact with the provider was successful or not." id="NBS148L" >
Provider Contact:</span></td><td>
<span id="NBS148" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS148)"
codeSetNm="PRVDR_CONTACT_OUTCOME"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's reason for examing the patient." id="NBS149L" >
Exam Reason:</span></td><td>
<span id="NBS149" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS149)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's diagnosis." id="NBS150L" >
Reporting Provider Diagnosis (Surveillance):</span></td><td>
<span id="NBS150" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS150)"
codeSetNm="PRVDR_DIAGNOSIS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason." id="NBS151L" >
Patient Follow-Up:</span></td><td>
<span id="NBS151" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS151)"
codeSetNm="SURVEILLANCE_PATIENT_FOLLOWUP"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_18" name="Surveillance Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_18"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_18">
<tr id="patternNBS_INV_STD_UI_18" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_18');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_18">
<tr id="nopatternNBS_INV_STD_UI_18" class="odd" style="display:none">
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
<tr> <td class="fieldName">
<span title="Notes for surveillance activities (e.g., type of information needed, additional comments.)" id="NBS152L">
Surveillance Notes:</span>
</td>
<td>
<span id="NBS152"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS152)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_20" name="Patient Notification" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="For field follow-up, is patient eligible for notification of exposure?" id="NBS143L" >
Patient Eligible for Notification of Exposure:</span></td><td>
<span id="NBS143" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS143)"
codeSetNm="NOTIFIABLE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The method agreed upon by the patient and investigator for notifying the partner(s) and clusters of potential HIV exposure." id="NBS167L" >
Notification Plan:</span></td><td>
<span id="NBS167" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS167)"
codeSetNm="NOTIFICATION_PLAN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The notification method by which field follow-up patients are brought to examination, brought to treatment, and/or notified of exposure." id="NBS177L" >
Actual Referral Type:</span></td><td>
<span id="NBS177" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS177)"
codeSetNm="NOTIFICATION_ACTUAL_METHOD_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_22" name="Field Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS161L" title="The investigator assigned to field follow-up activities.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS161)"/>
<span id="NBS161">${PageForm.attributeMap.NBS161SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigator is assigned to field follow-up activities." id="NBS162L">Date Assigned:</span>
</td><td>
<span id="NBS162"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS162)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS163L" title="The investigator originally assigned to field follow-up activities.">
Initially Assigned:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS163)"/>
<span id="NBS163">${PageForm.attributeMap.NBS163SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of initial assignment for field follow-up." id="NBS164L">Initial Assignment Date:</span>
</td><td>
<span id="NBS164"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS164)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_23" name="Field Follow-up Exam Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's reason for examing the patient." id="NBS165L" >
Exam Reason:</span></td><td>
<span id="NBS165" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS165)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's diagnosis." id="NBS166L" >
Reporting Provider Diagnosis (Field Follow-up):</span></td><td>
<span id="NBS166" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS166)"
codeSetNm="PRVDR_DIAGNOSIS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Do you expect the patient to come in for examination?" id="NBS168L" >
Expected In:</span></td><td>
<span id="NBS168" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS168)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the patient is expected to come in for examination." id="NBS169L">Expected In Date:</span>
</td><td>
<span id="NBS169"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS169)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the patient was examined as a result of field activities." id="NBS170L">Exam Date:</span>
</td><td>
<span id="NBS170"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS170)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS171L" title="The provider who performed the exam.">
Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS171)"/>
<span id="NBS171">${PageForm.attributeMap.NBS171SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS172L" title="The facility at which the exam was performed.">
Facility:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS172)"/>
<span id="NBS172">${PageForm.attributeMap.NBS172SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_24" name="Case Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The disposition of the field follow-up activities." id="NBS173L" >
Disposition:</span></td><td>
<span id="NBS173" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS173)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="When the disposition was determined as relates to exam or treatment situation." id="NBS174L">Disposition Date:</span>
</td><td>
<span id="NBS174"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS174)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS175L" title="The person who brought the field record/activities to final disposition.">
Dispositioned By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS175)"/>
<span id="NBS175">${PageForm.attributeMap.NBS175SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS176L" title="The supervisor who should review the field record disposition.">
Supervisor:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS176)"/>
<span id="NBS176">${PageForm.attributeMap.NBS176SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The outcome of internet based activities." id="NBS178L" >
Internet Outcome:</span></td><td>
<span id="NBS178" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS178)"
codeSetNm="INTERNET_FOLLOWUP_OUTCOME"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_25" name="OOJ Field Record Sent To Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The name of the area where the out-of-jurisdiction Field Follow-up is sent." id="NBS179L" >
OOJ Agency FR Sent To:</span></td><td>
<span id="NBS179" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS179)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Field record number from initiating or receiving jurisdiction." id="NBS180L">
OOJ FR Number In Receiving Area:</span>
</td>
<td>
<span id="NBS180"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS180)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)" id="NBS181L">OOJ Due Date from Receiving Area:</span>
</td><td>
<span id="NBS181"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS181)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The outcome of the OOJ jurisdiction field activities." id="NBS182L" >
OOJ Outcome from Receiving Area:</span></td><td>
<span id="NBS182" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS182)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_STD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_26" name="Field Follow-Up Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_26"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_26">
<tr id="patternNBS_INV_STD_UI_26" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_26');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_26">
<tr id="nopatternNBS_INV_STD_UI_26" class="odd" style="display:none">
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
<tr> <td class="fieldName">
<span title="Note text." id="NBS185L">
Note:</span>
</td>
<td>
<span id="NBS185"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS185)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_27" name="Field Supervisory Review and Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_27"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_27">
<tr id="patternNBS_INV_STD_UI_27" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_27');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_27">
<tr id="nopatternNBS_INV_STD_UI_27" class="odd" style="display:none">
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
<tr> <td class="fieldName">
<span title="Note text" id="NBS268L">
Note:</span>
</td>
<td>
<span id="NBS268"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS268)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_29" name="Interview Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS186L" title="The investigator assigned to perform interview(s).">
Interviewer:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS186)"/>
<span id="NBS186">${PageForm.attributeMap.NBS186SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date assigned for interview." id="NBS187L">Date Assigned:</span>
</td><td>
<span id="NBS187"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS187)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS188L" title="The investigator originally assigned for interview.">
Initially Assigned:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS188)"/>
<span id="NBS188">${PageForm.attributeMap.NBS188SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of initial assignment for case assignment." id="NBS189L">Initial Assignment Date:</span>
</td><td>
<span id="NBS189"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS189)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS190L" title="The supervisor who should review the case follow-up closure.">
Supervisor:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS190)"/>
<span id="NBS190">${PageForm.attributeMap.NBS190SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the status of interviewing the patient of this investigation." id="NBS192L" >
Patient Interview Status:</span></td><td>
<span id="NBS192" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS192)"
codeSetNm="PAT_INTVW_STATUS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_30" name="Interview/Investigation Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_30"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_30">
<tr id="patternNBS_INV_STD_UI_30" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_30');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_30">
<tr id="nopatternNBS_INV_STD_UI_30" class="odd" style="display:none">
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
<tr> <td class="fieldName">
<span title="Note text." id="NBS195L">
Note:</span>
</td>
<td>
<span id="NBS195"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS195)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_32" name="Case Closure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Investigation may not be closed while interview status is awaiting or investigation is pending supervisor approval of field record closure. Also all contact records identified in this investigation must have a disposition.</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the case follow-up is closed." id="NBS196L">Date Closed:</span>
</td><td>
<span id="NBS196"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS196)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS197L" title="The investigator who closed out the case follow-up.">
Closed By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS197)"/>
<span id="NBS197">${PageForm.attributeMap.NBS197SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate whether or not the patient was in medical care at the time of the case close date." id="NBS444L" >
Care Status at Case Close Date:</span></td><td>
<span id="NBS444" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS444)"
codeSetNm="NBS_CARE_STATUS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_34" name="Supervisory Review/Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_34"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_34">
<tr id="patternNBS_INV_STD_UI_34" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_34');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_34">
<tr id="nopatternNBS_INV_STD_UI_34" class="odd" style="display:none">
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
<tr> <td class="fieldName">
<span title="Note text." id="NBS200L">
Note:</span>
</td>
<td>
<span id="NBS200"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS200)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_37" name="Pregnant Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Assesses whether or not the patient is pregnant." id="INV178L" >
Is the patient pregnant?:</span></td><td>
<span id="INV178" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV178)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS128L" title="Number of weeks pregnant at the time of diagnosis.">
Weeks:</span>
</td><td>
<span id="NBS128"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS128)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient pregnant at the initial exam for the condition?" id="NBS216L" >
Pregnant at Exam:</span></td><td>
<span id="NBS216" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS216)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS217L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of the initial exam.">
Weeks:</span>
</td><td>
<span id="NBS217"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS217)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient pregnant at the time of interview for the condition." id="NBS218L" >
Pregnant at Interview:</span></td><td>
<span id="NBS218" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS218)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS219L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of interview.">
Weeks:</span>
</td><td>
<span id="NBS219"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS219)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient receiving/received prenatal care for this pregnancy?" id="NBS220L" >
Currently in Prenatal Care:</span></td><td>
<span id="NBS220" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS220)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Determine if the patient has been pregnant during the last 12 months. If currently pregnant, a  Yes  answer indicates that the patient had another pregnancy within the past 12 months, not including her current  Pegnancy." id="NBS221L" >
Pregnant in Last 12 Months:</span></td><td>
<span id="NBS221" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS221)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If pregnant in the last 12 months, indicate the outcome of the pregnancy." id="NBS222L" >
Pregnancy Outcome:</span></td><td>
<span id="NBS222" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS222)"
codeSetNm="PREGNANCY_OUTCOME"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_39" name="Patient HIV Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The patient's HIV status." id="NBS153L" >
900 Status:</span></td><td>
<span id="NBS153" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS153)"
codeSetNm="STATUS_900"/>
</td> </tr>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the HIV status of this case investigated through search of eHARS?" id="INV892L" >
HIV Status Documented Through eHARS Record Search:</span></td><td>
<span id="INV892" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV892)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Enter the state-assigned HIV Case Number for this patient." id="NBS269L">
State HIV (eHARS) Case ID:</span>
</td>
<td>
<span id="NBS269"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS269)" />
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Mode of exposure from eHARS for HIV+ cases." id="INV894L" >
Transmission Category (eHARS):</span></td><td>
<span id="INV894" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV894)"
codeSetNm="PHVS_TRANSMISSIONCATEGORY_STD"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was this case selected by reporting jurisdiction for enhanced investigation?" id="INV895L" >
Case Sampled for Enhanced Investigation:</span></td><td>
<span id="INV895" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV895)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_41" name="Risk Factors (Last 12 Months)" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Were behavioral risk factors assessed for the client?" id="NBS229L" >
Was Behavioral Risk Assessed:</span></td><td>
<span id="NBS229" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS229)"
codeSetNm="RISK_PROFILE_IND"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_42" name="Sex Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with a male within past 12 months?" id="STD107L" >
Had Sex with Male:</span></td><td>
<span id="STD107" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD107)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with a female within past 12 months?" id="STD108L" >
Had Sex with Female:</span></td><td>
<span id="STD108" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD108)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with transgender partner within past 12 months?" id="NBS230L" >
Had Sex with Transgender:</span></td><td>
<span id="NBS230" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS230)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with an anonymous partner within past 12 months?" id="STD109L" >
Had Sex with Anonymous Partner:</span></td><td>
<span id="STD109" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD109)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_43" name="Sex Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex without a condom within past 12 months?" id="NBS231L" >
Had Sex Without a Condom:</span></td><td>
<span id="NBS231" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS231)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex while intoxicated/high within past 12 months?" id="STD111L" >
Had Sex While Intoxicated/High:</span></td><td>
<span id="STD111" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD111)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex in exchange for drugs/money within past 12 months?" id="STD112L" >
Exchanged Drugs/Money for Sex:</span></td><td>
<span id="STD112" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD112)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Female only.  Had sex with a known MSM within past 12 months?" id="STD113L" >
Females - Had Sex with Known MSM:</span></td><td>
<span id="STD113" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD113)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with a known Injection Drug User within past 12 months?" id="STD110L" >
Had Sex with Known IDU:</span></td><td>
<span id="STD110" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD110)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_44" name="Risk Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Incarcerated within the past 12 months?" id="STD118L" >
Been Incarcerated:</span></td><td>
<span id="STD118" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD118)"
codeSetNm="YNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Used injection drugs within the past 12 months?" id="STD114L" >
Injection Drug Use:</span></td><td>
<span id="STD114" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD114)"
codeSetNm="YNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Shared injection equipment within the past 12 months?" id="NBS232L" >
Shared Injection Equipment:</span></td><td>
<span id="NBS232" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS232)"
codeSetNm="YNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_45" name="Drug Use Past 12 Months" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;During the past 12 months, indicate whether or not the patient used any of the following injection or non-injection drugs.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="No drug use within the past 12 months?" id="NBS233L" >
No drug use reported:</span></td><td>
<span id="NBS233" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS233)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Cocaine use within the past 12 months?" id="NBS237L" >
Cocaine:</span></td><td>
<span id="NBS237" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS237)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Crack use within the past 12 months?" id="NBS235L" >
Crack:</span></td><td>
<span id="NBS235" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS235)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Heroine use within the past 12 months?" id="NBS239L" >
Heroin:</span></td><td>
<span id="NBS239" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS239)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Methamphetamine (meth)use within the past 12 months?" id="NBS234L" >
Methamphetamine:</span></td><td>
<span id="NBS234" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS234)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Nitrate/popper use within the past 12 months?" id="NBS236L" >
Nitrates/Poppers:</span></td><td>
<span id="NBS236" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS236)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Erectile dysfunction drug use within the past 12 months?" id="NBS238L" >
Erectile Dysfunction Medications:</span></td><td>
<span id="NBS238" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS238)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Other drug use within the past 12 months?" id="NBS240L" >
Other drug used:</span></td><td>
<span id="NBS240" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS240)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient indicated other drug use, specify the drug name(s)." id="STD300L">
Specify Other Drug Used:</span>
</td>
<td>
<span id="STD300"/>
<nedss:view name="PageForm" property="pageClientVO.answer(STD300)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_48" name="Places to Meet Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Places to Meet Partners" id="NBS242L" >
Places to Meet Partners:</span></td><td>
<span id="NBS242" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS242)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_49" name="Places Selected to Meet Partners" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_49"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_49">
<tr id="patternNBS_INV_STD_UI_49" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_49');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_49">
<tr id="nopatternNBS_INV_STD_UI_49" class="odd" style="display:none">
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

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS243L" title="Select the Hangout or Meetup.">
Place:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS243)"/>
<span id="NBS243Disp"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_50" name="Places to have Sex" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Places to Have Sex" id="NBS244L" >
Places to Have Sex:</span></td><td>
<span id="NBS244" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS244)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_51" name="Places Selected to Have Sex" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_51"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_51">
<tr id="patternNBS_INV_STD_UI_51" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_51');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_51">
<tr id="nopatternNBS_INV_STD_UI_51" class="odd" style="display:none">
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

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS290L" title="Select the Hangout or Sex.">
Place:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS290)"/>
<span id="NBS290Disp"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_53" name="Partners Past Year" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Female partners claimed in the last 12 months?" id="NBS223L" >
Female Partners (Past Year):</span></td><td>
<span id="NBS223" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS223)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS224L" title="The total number of female partners claimed in the last 12 months.">
Number Female (Past Year):</span>
</td><td>
<span id="NBS224"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS224)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Male partners claimed in the last 12 months?" id="NBS225L" >
Male Partners (Past Year):</span></td><td>
<span id="NBS225" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS225)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS226L" title="The total number of male partners claimed in the last 12 months.">
Number Male (Past Year):</span>
</td><td>
<span id="NBS226"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS226)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Transgender partners claimed in the last 12 months?" id="NBS227L" >
Transgender Partners (Past Year):</span></td><td>
<span id="NBS227" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS227)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS228L" title="The total number of transgender partners claimed in the last 12 months.">
Number Transgender (Past Year):</span>
</td><td>
<span id="NBS228"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS228)"  />
</td></tr>
<!--skipping Hidden Numeric Question-->
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD888L" title="Patient refused to answer questions regarding number of sex partners">Patient refused to answer questions regarding number of sex partners:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD888)" styleId="STD888"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD999L" title="Unknown number of sex partners in last 12 months">Unknown number of sex partners in last 12 months:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD999)" styleId="STD999"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_54" name="Partners in Interview Period" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Female sex/needle-sharing interview period partners claimed?" id="NBS129L" >
Female Partners (Interview Period):</span></td><td>
<span id="NBS129" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS129)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS130L" title="The total number of female sex/needle-sharing interview period partners claimed.">
Number Female (Interview Period):</span>
</td><td>
<span id="NBS130"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS130)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Male sex/needle-sharing interview period partners claimed?" id="NBS131L" >
Male Partners (Interview Period):</span></td><td>
<span id="NBS131" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS131)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS132L" title="The total number of male sex/needle-sharing interview period partners claimed.">
Number Male (Interview Period):</span>
</td><td>
<span id="NBS132"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS132)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Transgender sex/needle-sharing interview period partners claimed?" id="NBS133L" >
Transgender Partners (Interview Period):</span></td><td>
<span id="NBS133" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS133)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS134L" title="The total number of transgender sex/needle-sharing interview period partners claimed.">
Number Transgender (Interview Period):</span>
</td><td>
<span id="NBS134"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS134)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_55" name="Partner Internet Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Have you met sex partners through the Internet in the last 12 months?" id="STD119L" >
Met Sex Partners through the Internet:</span></td><td>
<span id="STD119" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD119)"
codeSetNm="YNRUD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_57" name="Target Populations" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS271L" title="Target populations identified for the patient.">
Target Population(s):</span></td><td>
<span id="NBS271" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(NBS271)"
codeSetNm="TARGET_POPULATIONS"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_59" name="Syphilis Test Results" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Have  non-treponemal or treponemal syphilis tests been performed?" id="NBS275L" >
Tests Performed?:</span></td><td>
<span id="NBS275" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS275)"
codeSetNm="YN"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD122L" title="What type of non-treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">Type of Nontreponemal Serologic Test for Syphilis:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD122)" styleId="STD122"><html:optionsCollection property="codedValue(PHVS_NONTREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD123L" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024.">Nontreponemal Serologic Syphilis Test Result (Quantitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD123)" styleId="STD123"><html:optionsCollection property="codedValue(PHVS_QUANTITATIVESYPHILISTESTRESULT_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD126L" title="Qualitative test result of STD123 Nontreponemal serologic syphilis test result (quantitative)">Nontreponemal Serologic Syphilis Test Result (Qualitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD126)" styleId="STD126"><html:optionsCollection property="codedValue(PHVS_LABTESTREACTIVITY_NND)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD124L" title="What type of treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">Type of Treponemal SerologicTest for Syphilis:</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD124)" styleId="STD124"><html:optionsCollection property="codedValue(PHVS_TREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD125L" title="If the test performed provides a qualitative result, provide qualitative result, e.g. weakly reactive.">Treponemal Serologic Syphilis Test Result  (Qualitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO.answer(STD125)" styleId="STD125"><html:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_71" name="STD Lab Test Results" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_71"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_71">
<tr id="patternNBS_INV_STD_UI_71" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_71');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_71">
<tr id="nopatternNBS_INV_STD_UI_71" class="odd" style="display:none">
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
<td class="fieldName">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="PHVS_LABTESTTYPE_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Coded quantitative test result (used for Nontreponemal serologic syphilis test result)." id="STD123_1L" >
Test Result Coded Quantitative:</span></td><td>
<span id="STD123_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD123_1)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Quantitative Test Result Value" id="LAB628L">
Test Result Quantitative:</span>
</td>
<td>
<span id="LAB628"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB628)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Units of measure for the Quantitative Test Result Value" id="LAB115L" >
Test Result Units:</span></td><td>
<span id="LAB115" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB115)"
codeSetNm="UNIT_ISO"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date result sent from Reporting Laboratory" id="LAB167L">Lab Result Date:</span>
</td><td>
<span id="LAB167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB167)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165L" >
Specimen Source:</span></td><td>
<span id="LAB165" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB165)"
codeSetNm="PHVS_SPECIMENSOURCE_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165OthL">Other Specimen Source:</span></td>
<td> <span id="LAB165Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LAB165Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of collection of initial laboratory specimen used for diagnosis of health event reported in this case report. PREFERRED date for assignment of MMWR week.  First date in hierarchy of date types associated with case report/event." id="338822L">Specimen Collection Date:</span>
</td><td>
<span id="338822"/>
<nedss:view name="PageForm" property="pageClientVO.answer(338822)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_72" name="Antimicrobial Susceptibility Testing(AST)" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_72"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_72">
<tr id="patternNBS_INV_STD_UI_72" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_72');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_72">
<tr id="nopatternNBS_INV_STD_UI_72" class="odd" style="display:none">
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
<td class="fieldName">
<span title="Pathogen/Organism Identified in Isolate." id="LABAST1L" >
Microorganism Identified in Isolate:</span></td><td>
<span id="LABAST1" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST1)"
codeSetNm="PHVS_ORGANISMIDENTIFIEDAST_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Isolate identifier unique for each isolate within laboratory." id="LABAST2L">
Isolate Identifier:</span>
</td>
<td>
<span id="LABAST2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST2)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3L" >
Specimen Type:</span></td><td>
<span id="LABAST3" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST3)"
codeSetNm="PHVS_SPECIMENTYPEAST_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3OthL">Other Specimen Type:</span></td>
<td> <span id="LABAST3Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LABAST3Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" id="LABAST4L" >
Specimen Collection Site:</span></td><td>
<span id="LABAST4" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST4)"
codeSetNm="PHVS_SPECIMENCOLLECTIONSITEAST_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" id="LABAST4OthL">Other Specimen Collection Site:</span></td>
<td> <span id="LABAST4Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(LABAST4Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Antimicrobial Susceptibility Specimen Collection Date" id="LABAST5L">Specimen Collection Date:</span>
</td><td>
<span id="LABAST5"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST5)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Type would includes drugs, enzymes, PCR and other genetic tests to detect the resistance against specific drugs." id="LABAST6L" >
AST Type:</span></td><td>
<span id="LABAST6" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST6)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTTYPE_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)" id="LABAST7L" >
AST Method:</span></td><td>
<span id="LABAST7" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST7)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTMETHOD_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Interpretation (e.g. Susceptible, Resistant, Intermediate, Not tested)" id="LABAST8L" >
AST Interpretation:</span></td><td>
<span id="LABAST8" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST8)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTINTERPRETATION_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Result - Coded Quantitative. List of coded values (i.e. valid dilutions) to represent the antimicrobial susceptibility test result." id="LABAST11L" >
AST Result Coded Quantitative:</span></td><td>
<span id="LABAST11" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST11)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTRESULTQUANTITATIVE_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Antimicrobial Susceptibility Test Result Quantitative Value (e.g. Quantitative MIC values, Disk Diffusion size in mm)" id="LABAST9L">
AST Result Quantitative Value:</span>
</td>
<td>
<span id="LABAST9"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST9)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Result Numerical Value Units (e.g. microgram/ml, mm)" id="LABAST10L" >
Test Result Units:</span></td><td>
<span id="LABAST10" />
<nedss:view name="PageForm" property="pageClientVO.answer(LABAST10)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTRESULTUNITS_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_61" name="Signs and Symptoms" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_61"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_61">
<tr id="patternNBS_INV_STD_UI_61" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_61');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_61">
<tr id="nopatternNBS_INV_STD_UI_61" class="odd" style="display:none">
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
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Is the sign/symptom experienced by the patient or observed by a clinician?" id="NBS246L" >
Source:</span></td><td>
<span id="NBS246" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS246)"
codeSetNm="SIGN_SX_OBSRV_SOURCE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The earliest date the symptom was first experienced by the patient and/or the date the sign was first observed by a clinician." id="NBS247L">Observation/Onset Date:</span>
</td><td>
<span id="NBS247"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS247)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Sign/symptom observed on exam or described." id="INV272L" >
Sign/Symptom:</span></td><td>
<span id="INV272" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV272)"
codeSetNm="SIGN_SX_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span id="STD121L" title="The anatomic site of the sign/symptom.">
Anatomic Site:</span></td><td>
<span id="STD121" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(STD121)"
codeSetNm="PHVS_CLINICIANOBSERVEDLESIONS_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Provide a description of the other anatomic site." id="NBS248L">
Other Anatomic Site, Specify:</span>
</td>
<td>
<span id="NBS248"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS248)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS249L" title="The number of days signs/symptoms were present. Document “99” if unknown.">
Duration (Days):</span>
</td><td>
<span id="NBS249"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS249)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_63" name="Previous STD History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of ever having had an STD prior to the condition reported in this case report?" id="STD117L" >
Previous STD history (self-reported)?:</span></td><td>
<span id="STD117" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD117)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_64" name="STD History" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_64"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_64">
<tr id="patternNBS_INV_STD_UI_64" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_64');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_64">
<tr id="nopatternNBS_INV_STD_UI_64" class="odd" style="display:none">
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
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="With what condition was the patient previously diagnosed?" id="NBS250L" >
Previous Condition:</span></td><td>
<span id="NBS250" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS250)"
codeSetNm="STD_HISTORY_DIAGNOSIS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Diagnosis Date of previous STD." id="NBS251L">Diagnosis Date:</span>
</td><td>
<span id="NBS251"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS251)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Treatment Date of previous STD." id="NBS252L">Treatment Date:</span>
</td><td>
<span id="NBS252"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS252)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Confirmed the Previous STD?" id="NBS253L" >
Confirmed:</span></td><td>
<span id="NBS253" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS253)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_66" name="Consented to Enrollment in Partner Services" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Document whether the patient accepted or declined enrollment into Partner Services (i.e. did s/he accept the interview)." id="NBS257L" >
Enrolled in Partner Services:</span></td><td>
<span id="NBS257" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS257)"
codeSetNm="ENROLL_HIV_PARTNER_SERVICES_IND"/>
</td> </tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_67" name="Self-Reported Results" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Previously tested for 900?" id="NBS254L" >
Previous 900 Test:</span></td><td>
<span id="NBS254" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS254)"
codeSetNm="YNRUD"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the self-reported or documented HIV test result at time of notification.  This should be the most recent test." id="STD106L" >
Self-reported or Documented Result:</span></td><td>
<span id="STD106" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD106)"
codeSetNm="STD_SELF_REPORTED_900_TEST_RESULT"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of last 900 Test" id="NBS259L">Date Last 900 Test:</span>
</td><td>
<span id="NBS259"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS259)"  />
</td></tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_68" name="Referred to Testing" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Referred for 900 test?" id="NBS260L" >
Refer for Test:</span></td><td>
<span id="NBS260" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS260)"
codeSetNm="YN"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date referred for 900 test." id="NBS261L">Referral Date:</span>
</td><td>
<span id="NBS261"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS261)"  />
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="900 test performed at this event?" id="NBS262L" >
900 Test:</span></td><td>
<span id="NBS262" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS262)"
codeSetNm="900_TST_AT_EVENT"/>
</td> </tr>
</logic:equal>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date that the specimen for the HIV test was collected." id="NBS450L">900 Test Sample Date:</span>
</td><td>
<span id="NBS450"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS450)"  />
</td></tr>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Result of 900 test at this event." id="NBS263L" >
900 Result:</span></td><td>
<span id="NBS263" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS263)"
codeSetNm="900_TEST_RESULTS"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The partner was informed of their 900 test result?" id="NBS265L" >
Result provided:</span></td><td>
<span id="NBS265" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS265)"
codeSetNm="900_RESULT_PROVIDED"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Post-test Counselling" id="NBS264L" >
Post-test Counselling:</span></td><td>
<span id="NBS264" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS264)"
codeSetNm="YNU"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if a client received a syphilis test in conjunction with an HIV test during partner services activities." id="NBS447L" >
Patient Tested for Syphilis In Conjunction with HIV Test:</span></td><td>
<span id="NBS447" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS447)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the outcome of the current syphilis test in conjunction with an HIV test while enrolled in partner services." id="NBS448L" >
Syphilis Test Result:</span></td><td>
<span id="NBS448" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS448)"
codeSetNm="SYPHILIS_TEST_RESULT_PS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_69" name="Referred to Medical Testing (900 +)" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Referred to 900 medical care/evaluation/treatment." id="NBS266L" >
Refer for Care:</span></td><td>
<span id="NBS266" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS266)"
codeSetNm="YN"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If referred, did patient keep 1st appointment?" id="NBS267L" >
Keep Appointment:</span></td><td>
<span id="NBS267" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS267)"
codeSetNm="KEEP_FIRST_APPT"/>
</td> </tr>
</logic:equal>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date the client attended his/her HIV medical care appointment after HIV diagnosis, current HIV test, or report to Partner Services." id="NBS302L">Appointment Date (If Confirmed):</span>
</td><td>
<span id="NBS302"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS302)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_75" name="Pre Exposure Prophylaxis (PrEP)" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the client is currently on pre-exposure prophylaxis (PrEP) medication." id="NBS443L" >
Is the Client Currently On PrEP?:</span></td><td>
<span id="NBS443" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS443)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the client was referred to a provider for pre-exposure prophylaxis (PrEP)." id="NBS446L" >
Has Client Been Referred to PrEP Provider?:</span></td><td>
<span id="NBS446" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS446)"
codeSetNm="PREP_REFERRAL_PS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_70" name="Anti-Retroviral Therapy for HIV Infection" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Anti-virals taken within the last 12 months?" id="NBS255L" >
Anti-viral Therapy - Last 12 Months:</span></td><td>
<span id="NBS255" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS255)"
codeSetNm="YNUR"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Anti-virals ever taken (including past year)?" id="NBS256L" >
Anti-viral Therapy - Ever:</span></td><td>
<span id="NBS256" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS256)"
codeSetNm="YNUR"/>
</td> </tr>
</logic:equal>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_28" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc." id="NBS055L" >
Contact Investigation Priority:</span></td><td>
<span id="NBS055" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS055)"
codeSetNm="NBS_PRIORITY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period." id="NBS056L">Infectious Period From:</span>
</td><td>
<span id="NBS056"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS056)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period." id="NBS057L">Infectious Period To:</span>
</td><td>
<span id="NBS057"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS057)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The status of the contact investigation." id="NBS058L" >
Contact Investigation Status:</span></td><td>
<span id="NBS058" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS058)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation." id="NBS059L">
Contact Investigation Comments:</span>
</td>
<td>
<span id="NBS059"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS059)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
