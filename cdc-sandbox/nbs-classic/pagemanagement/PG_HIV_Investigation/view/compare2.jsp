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
Map map2 = new HashMap();
if(request.getAttribute("SubSecStructureMap2") != null){
// String watemplateUid="1000879";
// map2 = util.getBatchMap(new Long(watemplateUid));
map2 =(Map)request.getAttribute("SubSecStructureMap2");
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
<div style="float:left;width:100% ">    <%@ include file="/pagemanagement/patient/PatientSummaryCompare2.jsp" %> </div>
<div class="view" id="<%= tabId %>" style="text-align:center;">
<%  sectionIndex = 0; %>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_6_2" name="General Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="As of Date is the last known date for which the information is valid." id="NBS104_2L">Information As of Date:</span>
</td><td>
<span id="NBS104_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS104)"  />
</td></tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="General comments pertaining to the patient." id="DEM196_2L">
Comments:</span>
</td>
<td>
<span id="DEM196_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM196)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_7_2" name="Name Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS095_2L">Name Information As Of Date:</span>
</td><td>
<span id="NBS095_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS095)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's first name." id="DEM104_2L">
First Name:</span>
</td>
<td>
<span id="DEM104_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM104)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's middle name or initial." id="DEM105_2L">
Middle Name:</span>
</td>
<td>
<span id="DEM105_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM105)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's last name." id="DEM102_2L">
Last Name:</span>
</td>
<td>
<span id="DEM102_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM102)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The patient's name suffix" id="DEM107_2L" >
Suffix:</span></td><td>
<span id="DEM107_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM107)"
codeSetNm="P_NM_SFX"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's alias or nickname." id="DEM250_2L">
Alias/Nickname:</span>
</td>
<td>
<span id="DEM250_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM250)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_8_2" name="Other Personal Details" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS096_2L">Other Personal Details As Of Date:</span>
</td><td>
<span id="NBS096_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS096)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Reported date of birth of patient." id="DEM115_2L">Date of Birth:</span>
</td><td>
<span id="DEM115_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM115)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV2001_2L" title="The patient's age reported at the time of interview.">
Reported Age:</span>
</td><td>
<span id="INV2001_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV2001)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's age units" id="INV2002_2L" >
Reported Age Units:</span></td><td>
<span id="INV2002_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV2002)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's current sex." id="DEM113_2L" >
Current Sex:</span></td><td>
<span id="DEM113_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM113)"
codeSetNm="SEX"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's current sex if identified as unknown (i.e., not male or female)." id="NBS272_2L" >
Unknown Reason:</span></td><td>
<span id="NBS272_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS272)"
codeSetNm="SEX_UNK_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient's transgender identity." id="NBS274_2L" >
Gender Identity/Transgender Info:</span></td><td>
<span id="NBS274_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS274)"
codeSetNm="NBS_STD_GENDER_PARPT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The specific gender information of the index patient if other selections do not apply (i.e. intersex, two-spirited, etc.)." id="NBS213_2L">
Additional Gender:</span>
</td>
<td>
<span id="NBS213_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS213)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient Birth Sex" id="DEM114_2L" >
Birth Sex:</span></td><td>
<span id="DEM114_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM114)"
codeSetNm="SEX"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Patient identified sexual orientation (i.e., an individual's physical and/or emotional attraction to another individual of the same gender, opposite gender, or both genders)." id="INV592_2L" >
Sexual Orientation/Preference:</span></td><td>
<span id="INV592_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV592)"
codeSetNm="PHVS_SEXUALORIENTATION_CDC"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Patient identified sexual orientation (i.e., an individual's physical and/or emotional attraction to another individual of the same gender, opposite gender, or both genders)." id="INV592_2_OthL">Other Sexual Orientation/Preference:</span></td>
<td> <span id="INV592_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(INV592Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS097_2L">Mortality Information As Of Date:</span>
</td><td>
<span id="NBS097_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS097)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Country of Birth" id="DEM126_2L" >
Country of Birth:</span></td><td>
<span id="DEM126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM126)"
codeSetNm="PHVS_BIRTHCOUNTRY_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicator of whether or not a patient is alive or dead." id="DEM127_2L" >
Is the patient deceased?:</span></td><td>
<span id="DEM127_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM127)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date on which the individual died." id="DEM128_2L">Deceased Date:</span>
</td><td>
<span id="DEM128_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM128)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS098_2L">Marital Status As Of Date:</span>
</td><td>
<span id="NBS098_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS098)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="A code indicating the married or similar partnership status of a patient." id="DEM140_2L" >
Marital Status:</span></td><td>
<span id="DEM140_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM140)"
codeSetNm="P_MARITAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Patient SSN as of Date" id="NBS451_2L">SSN as of Date:</span>
</td><td>
<span id="NBS451_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS451)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Patient SSN" id="DEM133_2L">
SSN:</span>
</td>
<td>
<span id="DEM133_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM133)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="ENTITYID100_2" name="Entity ID Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "ENTITYID100_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyENTITYID100_2">
<tr id="patternENTITYID100_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'ENTITYID100_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyENTITYID100_2">
<tr id="nopatternENTITYID100_2" class="odd" style="display:none">
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
<span title="Entity ID as of Date." id="NBS452_2L">As Of:</span>
</td><td>
<span id="NBS452_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS452)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Entity ID type for patient" id="DEM144_2L" >
Type:</span></td><td>
<span id="DEM144_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM144)"
codeSetNm="EI_TYPE_PAT"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Entity ID type for patient" id="DEM144_2_OthL">Other Type:</span></td>
<td> <span id="DEM144_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(DEM144Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Assigning Authority of Patient ID" id="DEM146_2L" >
Authority:</span></td><td>
<span id="DEM146_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM146)"
codeSetNm="EI_AUTH_PAT"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Patient ID Value" id="DEM147_2L">
Value:</span>
</td>
<td>
<span id="DEM147_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM147)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_15_2" name="Reporting Address for Case Counting" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS102_2L">Address Information As Of Date:</span>
</td><td>
<span id="NBS102_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS102)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Line one of the address label." id="DEM159_2L">
Street Address 1:</span>
</td>
<td>
<span id="DEM159_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM159)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Line two of the address label." id="DEM160_2L">
Street Address 2:</span>
</td>
<td>
<span id="DEM160_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The city for a postal location." id="DEM161_2L">
City:</span>
</td>
<td>
<span id="DEM161_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM161)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The state code for a postal location." id="DEM162_2L" >
State:</span></td><td>
<span id="DEM162_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM162)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The zip code of a residence of the case patient or entity." id="DEM163_2L">
Zip:</span>
</td>
<td>
<span id="DEM163_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM163)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The county of residence of the case patient or entity." id="DEM165_2L" >
County:</span></td><td>
<span id="DEM165_2" />
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(DEM165)">
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(DEM162)">
<bean:define id="value" name="PageForm" property="pageClientVO2.answer(DEM162)"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM165)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.DEM165_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Census tract where the address is located is a unique identifier associated with a small statistical subdivision of a county. A single community may be composed of several census tracts." id="DEM168_2L">
Census Tract:</span>
</td>
<td>
<span id="DEM168_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM168)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The country code for a postal location." id="DEM167_2L" >
Country:</span></td><td>
<span id="DEM167_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM167)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_2_2" name="Additional Residence Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The RELATIONSHIP (such as spouse, parents, sibling, partner, roommate, etc., not the name) of those living with the patient." id="NBS201_2L">
Living With:</span>
</td>
<td>
<span id="NBS201_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS201)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The type of residence in which the patient currenlty resides." id="NBS202_2L" >
Type of Residence:</span></td><td>
<span id="NBS202_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS202)"
codeSetNm="RESIDENCE_TYPE_STD"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS203_2L" title="The length of time the patient has lived at the current address.">
Time at Address:</span>
</td><td>
<span id="NBS203_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS203)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time at address." id="NBS204_2L" >
Units:</span></td><td>
<span id="NBS204_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS204)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS205_2L" title="The length of time the patient has lived in this state/territory.">
Time in State:</span>
</td><td>
<span id="NBS205_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS205)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time in state." id="NBS206_2L" >
Units:</span></td><td>
<span id="NBS206_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS206)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS207_2L" title="The length of time the patient has lived in the country.">
Time in Country:</span>
</td><td>
<span id="NBS207_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS207)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit if time used to describe time in country." id="NBS208_2L" >
Units:</span></td><td>
<span id="NBS208_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS208)"
codeSetNm="WKS_MOS_YRS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the patient is institutionalized (i.e., in jail, in a group home, in a mental health facility, etc.)" id="NBS209_2L" >
Currently institutionalized:</span></td><td>
<span id="NBS209_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS209)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Name of Institutition" id="NBS210_2L">
If institutionalized, document the name of the facility.:</span>
</td>
<td>
<span id="NBS210_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS210)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of Institutition" id="NBS211_2L" >
Type of Institutition:</span></td><td>
<span id="NBS211_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS211)"
codeSetNm="INSTITUTION_TYPE_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_16_2" name="Telephone Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS103_2L">Telephone Information As Of Date:</span>
</td><td>
<span id="NBS103_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS103)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's home phone number." id="DEM177_2L">
Home Phone:</span>
</td>
<td>
<span id="DEM177_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM177)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's work phone number." id="NBS002_2L">
Work Phone:</span>
</td>
<td>
<span id="NBS002_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS002)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS003_2L" title="The patient's work phone number extension.">
Ext.:</span>
</td><td>
<span id="NBS003_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS003)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's cellular phone number." id="NBS006_2L">
Cell Phone:</span>
</td>
<td>
<span id="NBS006_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS006)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The patient's email address." id="DEM182_2L">
Email:</span>
</td>
<td>
<span id="DEM182_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM182)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_9_2" name="Ethnicity and Race Information" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS100_2L">Ethnicity Information As Of Date:</span>
</td><td>
<span id="NBS100_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS100)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates if the patient is hispanic or not." id="DEM155_2L" >
Ethnicity:</span></td><td>
<span id="DEM155_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(DEM155)"
codeSetNm="PHVS_ETHNICITYGROUP_CDC_UNK"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Specify reason the patient's ethnicity is unknown." id="NBS273_2L" >
Reason Unknown:</span></td><td>
<span id="NBS273_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS273)"
codeSetNm="P_ETHN_UNK_REASON"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="As of Date is the last known date for which the information is valid." id="NBS101_2L">Race Information As Of Date:</span>
</td><td>
<span id="NBS101_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS101)"  />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr>
<td class="fieldName">
<span title="Reported race; supports collection of multiple race categories.  This field could repeat." id="DEM152_2L">
Race:</span>
</td>
<td>
<div id="patientRacesViewContainer2">
<logic:equal name="PageForm" property="pageClientVO2.americanIndianAlskanRace" value="1"><bean:message bundle="RVCT" key="rvct.american.indian.or.alaska.native"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.africanAmericanRace" value="1"><bean:message bundle="RVCT" key="rvct.black.or.african.american"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.whiteRace" value="1"><bean:message bundle="RVCT" key="rvct.white"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.asianRace" value="1"><bean:message bundle="RVCT" key="rvct.asian"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.hawaiianRace" value="1"><bean:message bundle="RVCT" key="rvct.native.hawaiian.or.other.pacific.islander"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.otherRace" value="1"><bean:message bundle="RVCT" key="rvct.other"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.refusedToAnswer" value="1"><bean:message bundle="RVCT" key="rvct.refusedToAnswer"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.notAsked" value="1"><bean:message bundle="RVCT" key="rvct.notAsked"/>,</logic:equal>
<logic:equal name="PageForm" property="pageClientVO2.unKnownRace" value="1"><bean:message bundle="RVCT" key="rvct.unknown"/>,</logic:equal>
</div>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_6_2" name="Other Identifying Information" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific height of the patient." id="NBS155_2L">
Height:</span>
</td>
<td>
<span id="NBS155_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS155)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific weight or body type of the patient." id="NBS156_2L">
Size/Build:</span>
</td>
<td>
<span id="NBS156_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS156)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The description of the patients hair, including color, length, and/or style." id="NBS157_2L">
Hair:</span>
</td>
<td>
<span id="NBS157_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS157)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The approximate or specific skin tone/hue of the patient." id="NBS158_2L">
Complexion:</span>
</td>
<td>
<span id="NBS158_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS158)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Any additional demographic information (e.g., tattoos, etc)." id="NBS159_2L">
Other Identifying Info:</span>
</td>
<td>
<span id="NBS159_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS159)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_19_2" name="Investigation Details" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The jurisdiction of the investigation." id="INV107_2L" >
Jurisdiction:</span></td><td>
<span id="INV107_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV107)"
codeSetNm="<%=NEDSSConstants.JURIS_LIST%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The program area associated with the investigaiton condition." id="INV108_2L" >
Program Area:</span></td><td>
<span id="INV108_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV108)"
codeSetNm="<%=NEDSSConstants.PROG_AREA%>"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Document the reason (referral basis) why the investigation was initiated." id="NBS110_2L" >
Referral Basis:</span></td><td>
<span id="NBS110_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS110)"
codeSetNm="REFERRAL_BASIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="The status of the investigation." id="INV109_2L" >
Investigation Status:</span></td><td>
<span id="INV109_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV109)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The stage of the investigation (e.g, No Follow-up, Surveillance, Field Follow-up)" id="NBS115_2L" >
Current Process Stage:</span></td><td>
<span id="NBS115_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS115)"
codeSetNm="CM_PROCESS_STAGE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation was started or initiated." id="INV147_2L">Investigation Start Date:</span>
</td><td>
<span id="INV147_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV147)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigation is closed." id="INV2006_2L">Investigation Close Date:</span>
</td><td>
<span id="INV2006_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV2006)"  />
</td></tr>

<!--processing Checkbox Coded Question-->
<tr> <td class="fieldName">
<span style="color:#CC0000">*</span>
<span id="NBS012_2L" title="Should this record be shared with guests with program area and jurisdiction rights?">
Shared Indicator:</span>
</td>
<td>
<logic:equal name="PageForm" property="pageClientVO2.answer(NBS012)" value="1">
Yes</logic:equal>
<logic:notEqual name="PageForm" property="pageClientVO2.answer(NBS012)" value="1">
No</logic:notEqual>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="NBS270_2L" title="Referral Basis - OOJ">Referral Basis - OOJ:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(NBS270)" styleId="NBS270_2"><html:optionsCollection property="codedValue(REFERRAL_BASIS)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_20_2" name="Investigator" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV180_2L" title="The Public Health Investigator assigned to the Investigation.">
Current Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV180)"/>
<span id="INV180_2">${PageForm.attributeMap2.INV180SearchResult}</span>
</td> </tr>
<!--skipping Hidden Date Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_8_2" name="OOJ Agency Initiating Report" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The Initiating Agency which sent the OOJ Contact Report." id="NBS111_2L" >
Initiating Agency:</span></td><td>
<span id="NBS111_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS111)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the OOJ Contact report was received from the Initiating Agency." id="NBS112_2L">Date Received from Init. Agency:</span>
</td><td>
<span id="NBS112_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS112)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date OOJ outcome is due back to the Initiating Agency." id="NBS113_2L">Date OOJ Due to Init. Agency:</span>
</td><td>
<span id="NBS113_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS113)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date OOJ outcome was sent back to the Initiating Agency." id="NBS114_2L">Date OOJ Info Sent:</span>
</td><td>
<span id="NBS114_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS114)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_9_2" name="Reported as OOJ Contact" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of first exposure with sex partner reported  by OOJ Contact." id="NBS118_2L">First Sexual Exposure:</span>
</td><td>
<span id="NBS118_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS118)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The frequency of exposure with sex partner reported by OOJ Contact." id="NBS119_2L">
Sexual Frequency:</span>
</td>
<td>
<span id="NBS119_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS119)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of last exposure with sex partner reported by OOJ Contact." id="NBS120_2L">Last Sexual Exposure:</span>
</td><td>
<span id="NBS120_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of first exposure with needle-sharing partner reported by OOJ Contact." id="NBS121_2L">First Needle-Sharing Exposure:</span>
</td><td>
<span id="NBS121_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS121)"  />
</td></tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The frequency of exposure with needle-sharing partner reported by OOJ Contact." id="NBS122_2L">
Needle-Sharing Frequency:</span>
</td>
<td>
<span id="NBS122_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS122)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of last exposure with needle-sharing partner reported by OOJ Contact." id="NBS123_2L">Last Needle-Sharing Exposure:</span>
</td><td>
<span id="NBS123_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS123)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The cluster relationship reported by OOJ Contact." id="NBS124_2L" >
Relationship:</span></td><td>
<span id="NBS124_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS124)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="As reported by OOJ Contact, is the patient the original patient's spouse?" id="NBS125_2L" >
OP Spouse?:</span></td><td>
<span id="NBS125_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS125)"
codeSetNm="OP_SPOUSE_IND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="As reported by OOJ Contact, did the patient meet the original patient on the internet?" id="NBS126_2L" >
Met OP via Internet?:</span></td><td>
<span id="NBS126_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS126)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="During the course of the interview, did the DIS elicit internet information about the named contact?" id="NBS127_2L" >
Internet Info Elicited?:</span></td><td>
<span id="NBS127_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS127)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_22_2" name="Key Report Dates" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of report of the condition to the public health department." id="INV111_2L">Date of Report:</span>
</td><td>
<span id="INV111_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV111)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Earliest date reported to county public health system." id="INV120_2L">Earliest Date Reported to County:</span>
</td><td>
<span id="INV120_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV120)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Earliest date reported to state public health system." id="INV121_2L">Earliest Date Reported to State:</span>
</td><td>
<span id="INV121_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV121)"  />
</td></tr>
<!--skipping Hidden Date Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_23_2" name="Reporting Organization" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Type of facility or provider associated with the source of information sent to Public Health." id="INV112_2L" >
Reporting Source Type:</span></td><td>
<span id="INV112_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV112)"
codeSetNm="PHVS_REPORTINGSOURCETYPE_NND"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV183_2L" title="The organization that reported the case.">
Reporting Organization:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV183)"/>
<span id="INV183_2">${PageForm.attributeMap2.INV183SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_10_2" name="Reporting Provider" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV181_2L" title="The provider that reported the case.">
Reporting Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV181)"/>
<span id="INV181_2">${PageForm.attributeMap2.INV181SearchResult}</span>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_12_2" name="Physician" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV182_2L" title="The physician associated with this case.">
Physician:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV182)"/>
<span id="INV182_2">${PageForm.attributeMap2.INV182SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_32_2" name="Physician Clinic" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS291_2L" title="The clinic with which the physician associated with this case is affiliated.">
Physician Ordering Clinic:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS291)"/>
<span id="NBS291_2">${PageForm.attributeMap2.NBS291SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_13_2" name="Hospital" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient hospitalized as a result of this event?" id="INV128_2L" >
Was the patient hospitalized for this illness?:</span></td><td>
<span id="INV128_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV128)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="INV184_2L" title="The hospital associated with the investigation.">
Hospital:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV184)"/>
<span id="INV184_2">${PageForm.attributeMap2.INV184SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's admission date to the hospital for the condition covered by the investigation." id="INV132_2L">Admission Date:</span>
</td><td>
<span id="INV132_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV132)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Subject's discharge date from the hospital for the condition covered by the investigation." id="INV133_2L">Discharge Date:</span>
</td><td>
<span id="INV133_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV133)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV134_2L" title="Subject's duration of stay at the hospital for the condition covered by the investigation.">
Total duration of stay in the hospital (in days):</span>
</td><td>
<span id="INV134_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV134)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_14_2" name="Condition" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of diagnosis of condition being reported to public health system." id="INV136_2L">Diagnosis Date:</span>
</td><td>
<span id="INV136_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV136)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of the beginning of the illness.  Reported date of the onset of symptoms of the condition being reported to the public health system." id="INV137_2L">Illness Onset Date:</span>
</td><td>
<span id="INV137_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV137)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The time at which the disease or condition ends." id="INV138_2L">Illness End Date:</span>
</td><td>
<span id="INV138_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV138)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV139_2L" title="The length of time this person had this disease or condition.">
Illness Duration:</span>
</td><td>
<span id="INV139_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV139)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Unit of time used to describe the length of the illness or condition." id="INV140_2L" >
Illness Duration Units:</span></td><td>
<span id="INV140_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV140)"
codeSetNm="PHVS_DURATIONUNIT_CDC"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="INV143_2L" title="Subject's age at the onset of the disease or condition.">
Age at Onset:</span>
</td><td>
<span id="INV143_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV143)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The age units for an age." id="INV144_2L" >
Age at Onset Units:</span></td><td>
<span id="INV144_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV144)"
codeSetNm="AGE_UNIT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates if the subject dies as a result of the illness." id="INV145_2L" >
Did the patient die from this illness?:</span></td><td>
<span id="INV145_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV145)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the subject’s death occurred." id="INV146_2L">Date of Death:</span>
</td><td>
<span id="INV146_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV146)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date treatment initiated for the condition that is the subject of this case report." id="STD105_2L">Treatment Start Date:</span>
</td><td>
<span id="STD105_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(STD105)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of earliest healthcare encounter/visit /exam associated with this event/case report.  May equate with date of exam or date of diagnosis. If helath exam is missing, use the lab specimen collection date." id="STD099_2L">Date of Initial Health Exam:</span>
</td><td>
<span id="STD099_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(STD099)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_25_2" name="Epi-Link" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether the subject of the investigation was associated with a day care facility.  The association could mean that the subject attended daycare or work in a daycare facility." id="INV148_2L" >
Is this person associated with a day care facility?:</span></td><td>
<span id="INV148_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV148)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates whether the subject of the investigation was food handler." id="INV149_2L" >
Is this person a food handler?:</span></td><td>
<span id="INV149_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV149)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Denotes whether the reported case was associated with an identified outbreak." id="INV150_2L" >
Is this case part of an outbreak?:</span></td><td>
<span id="INV150_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV150)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="A name assigned to an individual outbreak.   State assigned in SRT.  Should show only those outbreaks for the program area of the investigation." id="INV151_2L" >
Outbreak Name:</span></td><td>
<span id="INV151_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV151)"
codeSetNm="OUTBREAK_NM"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_1_2" name="Disease Acquisition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indication of where the disease/condition was likely acquired." id="INV152_2L" >
Where was the disease acquired:</span></td><td>
<span id="INV152_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV152)"
codeSetNm="PHVS_DISEASEACQUIREDJURISDICTION_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, indicate the country in which the disease was likely acquired." id="INV153_2L" >
Imported Country:</span></td><td>
<span id="INV153_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV153)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, indicate the state in which the disease was likely acquired." id="INV154_2L" >
Imported State:</span></td><td>
<span id="INV154_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV154)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If the disease or condition was imported, indicate the city in which the disease was likely acquired." id="INV155_2L">
Imported City:</span>
</td>
<td>
<span id="INV155_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV155)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If the disease or condition was imported, this field will contain the county of origin of the disease or condition." id="INV156_2L" >
Imported County:</span></td><td>
<span id="INV156_2" />
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(INV156)">
<logic:notEmpty name="PageForm" property="pageClientVO2.answer(INV154)">
<bean:define id="value" name="PageForm" property="pageClientVO2.answer(INV154)"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV156)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.INV156_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Document if the partner is determined to be the source of condition for the index patient or a spread from the index patient." id="NBS135_2L" >
Source/Spread:</span></td><td>
<span id="NBS135_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS135)"
codeSetNm="SOURCE_SPREAD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Where does the person usually live (defined as their residence)." id="INV501_2L" >
Country of Usual Residence:</span></td><td>
<span id="INV501_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV501)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS11001_2" name="Reporting County" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="County reporting the notification." id="NOT113_2L" >
Reporting County:</span></td><td>
<span id="NOT113_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NOT113)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.NOT113_STATE}"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS11002_2" name="Exposure Location" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS11002_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS11002_2">
<tr id="patternNBS11002_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS11002_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS11002_2">
<tr id="nopatternNBS11002_2" class="odd" style="display:none">
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
<span title="Indicates the country in which the disease was potentially acquired." id="INV502_2L" >
Country of Exposure:</span></td><td>
<span id="INV502_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV502)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the state in which the disease was potentially acquired." id="INV503_2L" >
State or Province of Exposure:</span></td><td>
<span id="INV503_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV503)"
codeSetNm="PHVS_STATEPROVINCEOFEXPOSURE_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Indicates the city in which the disease was potentially acquired." id="INV504_2L">
City of Exposure:</span>
</td>
<td>
<span id="INV504_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV504)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicates the county in which the disease was potentially acquired." id="INV505_2L" >
County of Exposure:</span></td><td>
<span id="INV505_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV505)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap2.INV505_STATE}"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS11003_2" name="Binational Reporting" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV515_2L" title="For cases meeting the binational criteria, select all the criteria which are met.">
Binational Reporting Criteria:</span></td><td>
<span id="INV515_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV515)"
codeSetNm="PHVS_BINATIONALREPORTINGCRITERIA_CDC"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_2_2" name="Case Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Code for the mechanism by which disease or condition was acquired by the subject of the investigation.  Includes sexually transmitted, airborne, bloodborne, vectorborne, foodborne, zoonotic, nosocomial, mechanical, dermal, congenital, environmental exposure, indeterminate." id="INV157_2L" >
Transmission Mode:</span></td><td>
<span id="INV157_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV157)"
codeSetNm="PHC_TRAN_M"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Code for the method by which the public health department was made aware of the case. Includes provider report, patient self-referral, laboratory report, case or outbreak investigation, contact investigation, active surveillance, routine physical, prenatal testing, perinatal testing, prison entry screening, occupational disease surveillance, medical record review, etc." id="INV159_2L" >
Detection Method:</span></td><td>
<span id="INV159_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV159)"
codeSetNm="PHVS_DETECTIONMETHOD_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="INV161_2L" title="Code for the mechanism by which the case was classified. This attribute is intended to provide information about how the case classification status was derived.">
Confirmation Method:</span></td><td>
<span id="INV161_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(INV161)"
codeSetNm="PHC_CONF_M"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="If an investigation is confirmed as a case, then the confirmation date is entered." id="INV162_2L">Confirmation Date:</span>
</td><td>
<span id="INV162_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV162)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The current status of the investigation/case." id="INV163_2L" >
Case Status:</span></td><td>
<span id="INV163_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV163)"
codeSetNm="PHVS_PHC_CLASS_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The disease diagnosis of the patient." id="NBS136_2L" >
Diagnosis Reported to CDC:</span></td><td>
<span id="NBS136_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS136)"
codeSetNm="CASE_DIAGNOSIS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The MMWR week in which the case should be counted." id="INV165_2L">
MMWR Week:</span>
</td>
<td>
<span id="INV165_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV165)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="The MMWR year in which the case should be counted." id="INV166_2L">
MMWR Year:</span>
</td>
<td>
<span id="INV166_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV166)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does this case meet the criteria for immediate (extremely urgent or urgent) notification to CDC?" id="NOT120_2L" >
Immediate National Notifiable Condition:</span></td><td>
<span id="NOT120_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NOT120)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="This field is for local use to describe any phone contact with CDC regading this Immediate National Notifiable Condition." id="NOT120SPEC_2L">
If Yes, describe:</span>
</td>
<td>
<span id="NOT120SPEC_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NOT120SPEC)" />
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Do not send personally identifiable information to CDC in this field. Use this field, if needed, to communicate anything unusual about this case, which is not already covered with the other data elements.  Alternatively, use this field to communicate information to the CDC NNDSS staff processing the data." id="INV886_2L">
Notification Comments to CDC:</span>
</td>
<td>
<span id="INV886_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV886)"  />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_73_2" name="Syphilis Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Neurological Involvement?" id="STD102_2L" >
Neurological Manifestations:</span></td><td>
<span id="STD102_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD102)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="102957003_2L" title="What neurologic manifestations of syphilis are present?">
Neurologic Signs/Symptoms:</span></td><td>
<span id="102957003_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(102957003)"
codeSetNm="PHVS_NEUROLOGICALMANIFESTATION_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="What neurologic manifestations of syphilis are present?" id="102957003_2_OthL">Other Neurologic Signs/Symptoms:</span></td>
<td> <span id="102957003_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(102957003Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Infection of any eye structure with T. pallidum, as evidenced by manifestations including posterior uveitis, panuveitis, anterior uveitis, optic neuropathy, and retinal vasculitis." id="410478005_2L" >
Ocular Manifestations:</span></td><td>
<span id="410478005_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(410478005)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Infection of the cochleovestibular system with T. pallidum, as evidenced by manifestations including sensorineural hearing loss, tinnitus, and vertigo." id="PHC1472_2L" >
Otic Manifestations:</span></td><td>
<span id="PHC1472_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(PHC1472)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Late clinical manifestations of syphilis (tertiary syphilis) may include inflammatory lesions of the cardiovascular system, skin, bone, or other tissue. Certain neurologic manifestations (e.g., general paresis and tabes dorsalis) are late clinical manifestations of syphilis." id="72083004_2L" >
Late Clinical Manifestations:</span></td><td>
<span id="72083004_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(72083004)"
codeSetNm="PHVS_SYPHILISNEUROLOGICINVOLVEMENT_STD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_74_2" name="Other Manifestations" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Pelvic inflammatory disease present?" id="INV179_2L" >
PID:</span></td><td>
<span id="INV179_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV179)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Disseminated?" id="NBS137_2L" >
Disseminated:</span></td><td>
<span id="NBS137_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS137)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Conjunctivitis?" id="INV361_2L" >
Conjunctivitis:</span></td><td>
<span id="INV361_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV361)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="To what drug(s) is the disease resistant?" id="NBS138_2L" >
Resistant to:</span></td><td>
<span id="NBS138_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS138)"
codeSetNm="RESISTANT_TO_300_DRUG"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_27_2" name="General Comments" isHidden="F" classType="subSect" >

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="Field which contains general comments for the investigation." id="INV167_2L">
General Comments:</span>
</td>
<td>
<span id="INV167_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV167)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_12_2" name="Case Numbers" isHidden="F" classType="subSect" >

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Unique field record identifier." id="NBS160_2L">
Field Record Number:</span>
</td>
<td>
<span id="NBS160_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS160)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Unique Epi-Link identifier (Epi-Link ID) to group contacts." id="NBS191_2L">
Lot Number:</span>
</td>
<td>
<span id="NBS191_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS191)" />
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="CDC uses this field to link current case notifications to case notifications submitted by a previous system. If this case has a case ID from a previous system (e.g. NETSS, STD-MIS, etc.), please enter it here." id="INV200_2L">
Legacy Case ID:</span>
</td>
<td>
<span id="INV200_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(INV200)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_14_2" name="Initial Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS139_2L" title="The investigator assigning the initial follow-up.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS139)"/>
<span id="NBS139_2">${PageForm.attributeMap2.NBS139SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Initial Follow-up action." id="NBS140_2L" >
Initial Follow-Up:</span></td><td>
<span id="NBS140_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS140)"
codeSetNm="STD_NBS_PROCESSING_DECISION_ALL"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the inital follow-up was identified as closed." id="NBS141_2L">Date Closed:</span>
</td><td>
<span id="NBS141_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS141)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Initiate for Internet follow-up?" id="NBS142_2L" >
Internet Follow-Up:</span></td><td>
<span id="NBS142_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS142)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If applicable, enter the specific clinic code identifying the initiating clinic." id="NBS144_2L">
Clinic Code:</span>
</td>
<td>
<span id="NBS144_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS144)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_16_2" name="Surveillance Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS145_2L" title="The investigator assigned for surveillance follow-up.">
Assigned To:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS145)"/>
<span id="NBS145_2">${PageForm.attributeMap2.NBS145SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date surveillance follow-up is assigned." id="NBS146_2L">Date Assigned:</span>
</td><td>
<span id="NBS146_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS146)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date surveillance follow-up is completed." id="NBS147_2L">Date Closed:</span>
</td><td>
<span id="NBS147_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS147)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_17_2" name="Surveillance Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the contact with the provider was successful or not." id="NBS148_2L" >
Provider Contact:</span></td><td>
<span id="NBS148_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS148)"
codeSetNm="PRVDR_CONTACT_OUTCOME"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's reason for examing the patient." id="NBS149_2L" >
Exam Reason:</span></td><td>
<span id="NBS149_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS149)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's diagnosis." id="NBS150_2L" >
Reporting Provider Diagnosis (Surveillance):</span></td><td>
<span id="NBS150_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS150)"
codeSetNm="PRVDR_DIAGNOSIS_HIV"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the investigation will continue with field follow-up.  If not, indicate the reason." id="NBS151_2L" >
Patient Follow-Up:</span></td><td>
<span id="NBS151_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS151)"
codeSetNm="SURVEILLANCE_PATIENT_FOLLOWUP"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_18_2" name="Surveillance Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_18_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_18_2">
<tr id="patternNBS_INV_STD_UI_18_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_18_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_18_2">
<tr id="nopatternNBS_INV_STD_UI_18_2" class="odd" style="display:none">
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
<span title="Notes for surveillance activities (e.g., type of information needed, additional comments.)" id="NBS152_2L">
Surveillance Notes:</span>
</td>
<td>
<span id="NBS152_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS152)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_20_2" name="Patient Notification" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="For field follow-up, is patient eligible for notification of exposure?" id="NBS143_2L" >
Patient Eligible for Notification of Exposure:</span></td><td>
<span id="NBS143_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS143)"
codeSetNm="NOTIFIABLE"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The method agreed upon by the patient and investigator for notifying the partner(s) and clusters of potential HIV exposure." id="NBS167_2L" >
Notification Plan:</span></td><td>
<span id="NBS167_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS167)"
codeSetNm="NOTIFICATION_ACTUAL_METHOD_HIV"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The notification method by which field follow-up patients are brought to examination, brought to treatment, and/or notified of exposure." id="NBS177_2L" >
Actual Referral Type:</span></td><td>
<span id="NBS177_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS177)"
codeSetNm="NOTIFICATION_ACTUAL_METHOD_HIV"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_22_2" name="Field Follow-up Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS161_2L" title="The investigator assigned to field follow-up activities.">
Investigator:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS161)"/>
<span id="NBS161_2">${PageForm.attributeMap2.NBS161SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the investigator is assigned to field follow-up activities." id="NBS162_2L">Date Assigned:</span>
</td><td>
<span id="NBS162_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS162)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS163_2L" title="The investigator originally assigned to field follow-up activities.">
Initially Assigned:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS163)"/>
<span id="NBS163_2">${PageForm.attributeMap2.NBS163SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of initial assignment for field follow-up." id="NBS164_2L">Initial Assignment Date:</span>
</td><td>
<span id="NBS164_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS164)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_23_2" name="Field Follow-up Exam Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's reason for examing the patient." id="NBS165_2L" >
Exam Reason:</span></td><td>
<span id="NBS165_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS165)"
codeSetNm="PRVDR_EXAM_REASON"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The reporting provider's diagnosis." id="NBS166_2L" >
Reporting Provider Diagnosis (Field Follow-up):</span></td><td>
<span id="NBS166_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS166)"
codeSetNm="PRVDR_DIAGNOSIS_HIV"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Do you expect the patient to come in for examination?" id="NBS168_2L" >
Expected In:</span></td><td>
<span id="NBS168_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS168)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the patient is expected to come in for examination." id="NBS169_2L">Expected In Date:</span>
</td><td>
<span id="NBS169_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS169)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the patient was examined as a result of field activities." id="NBS170_2L">Exam Date:</span>
</td><td>
<span id="NBS170_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS170)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS171_2L" title="The provider who performed the exam.">
Provider:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS171)"/>
<span id="NBS171_2">${PageForm.attributeMap2.NBS171SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS172_2L" title="The facility at which the exam was performed.">
Facility:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS172)"/>
<span id="NBS172_2">${PageForm.attributeMap2.NBS172SearchResult}</span>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_24_2" name="Case Disposition" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The disposition of the field follow-up activities." id="NBS173_2L" >
Disposition:</span></td><td>
<span id="NBS173_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS173)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_HIV"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="When the disposition was determined as relates to exam or treatment situation." id="NBS174_2L">Disposition Date:</span>
</td><td>
<span id="NBS174_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS174)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS175_2L" title="The person who brought the field record/activities to final disposition.">
Dispositioned By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS175)"/>
<span id="NBS175_2">${PageForm.attributeMap2.NBS175SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS176_2L" title="The supervisor who should review the field record disposition.">
Supervisor:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS176)"/>
<span id="NBS176_2">${PageForm.attributeMap2.NBS176SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The outcome of internet based activities." id="NBS178_2L" >
Internet Outcome:</span></td><td>
<span id="NBS178_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS178)"
codeSetNm="INTERNET_FOLLOWUP_OUTCOME"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_25_2" name="OOJ Field Record Sent To Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The name of the area where the out-of-jurisdiction Field Follow-up is sent." id="NBS179_2L" >
OOJ Agency FR Sent To:</span></td><td>
<span id="NBS179_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS179)"
codeSetNm="OOJ_AGENCY_LOCAL"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Field record number from initiating or receiving jurisdiction." id="NBS180_2L">
OOJ FR Number In Receiving Area:</span>
</td>
<td>
<span id="NBS180_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS180)" />
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The expected date for the completion of the investigation by the receiving area (generally two weeks.)" id="NBS181_2L">OOJ Due Date from Receiving Area:</span>
</td><td>
<span id="NBS181_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS181)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The outcome of the OOJ jurisdiction field activities." id="NBS182_2L" >
OOJ Outcome from Receiving Area:</span></td><td>
<span id="NBS182_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS182)"
codeSetNm="FIELD_FOLLOWUP_DISPOSITION_HIV"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_26_2" name="Field Follow-Up Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_26_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_26_2">
<tr id="patternNBS_INV_STD_UI_26_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_26_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_26_2">
<tr id="nopatternNBS_INV_STD_UI_26_2" class="odd" style="display:none">
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
<span title="Note text." id="NBS185_2L">
Note:</span>
</td>
<td>
<span id="NBS185_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS185)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_27_2" name="Field Supervisory Review and Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_27_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_27_2">
<tr id="patternNBS_INV_STD_UI_27_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_27_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_27_2">
<tr id="nopatternNBS_INV_STD_UI_27_2" class="odd" style="display:none">
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
<span title="Note text" id="NBS268_2L">
Note:</span>
</td>
<td>
<span id="NBS268_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS268)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_29_2" name="Interview Case Assignment" isHidden="F" classType="subSect" >

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS186_2L" title="The investigator assigned to perform interview(s).">
Interviewer:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS186)"/>
<span id="NBS186_2">${PageForm.attributeMap2.NBS186SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date assigned for interview." id="NBS187_2L">Date Assigned:</span>
</td><td>
<span id="NBS187_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS187)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS188_2L" title="The investigator originally assigned for interview.">
Initially Assigned:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS188)"/>
<span id="NBS188_2">${PageForm.attributeMap2.NBS188SearchResult}</span>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date of initial assignment for case assignment." id="NBS189_2L">Initial Assignment Date:</span>
</td><td>
<span id="NBS189_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS189)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS190_2L" title="The supervisor who should review the case follow-up closure.">
Supervisor:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS190)"/>
<span id="NBS190_2">${PageForm.attributeMap2.NBS190SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the status of interviewing the patient of this investigation." id="NBS192_2L" >
Patient Interview Status:</span></td><td>
<span id="NBS192_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS192)"
codeSetNm="PAT_INTVW_STATUS"/>
</td> </tr>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Provide reason for not being able to locate the 900 case patient for interview." id="NBS276_2L" >
Reason Unable to Locate 900 Case:</span></td><td>
<span id="NBS276_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS276)"
codeSetNm="HIV_REASON_NOT_LOCATE"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Describe other reason for not being able to locate the 900 case patient for interview." id="NBS277_2L">
Other Reason, Specify:</span>
</td>
<td>
<span id="NBS277_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS277)" />
</td> </tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_30_2" name="Interview/Investigation Notes" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_30_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_30_2">
<tr id="patternNBS_INV_STD_UI_30_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_30_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_30_2">
<tr id="nopatternNBS_INV_STD_UI_30_2" class="odd" style="display:none">
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
<span title="Note text." id="NBS195_2L">
Note:</span>
</td>
<td>
<span id="NBS195_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS195)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_32_2" name="Case Closure" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;Investigation may not be closed while interview status is awaiting or investigation is pending supervisor approval of field record closure. Also all contact records identified in this investigation must have a disposition.</span></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date the case follow-up is closed." id="NBS196_2L">Date Closed:</span>
</td><td>
<span id="NBS196_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS196)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td class="fieldName">
<span id="NBS197_2L" title="The investigator who closed out the case follow-up.">
Closed By:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS197)"/>
<span id="NBS197_2">${PageForm.attributeMap2.NBS197SearchResult}</span>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate whether or not the patient was in medical care at the time of the case close date." id="NBS444_2L" >
Care Status at Case Close Date:</span></td><td>
<span id="NBS444_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS444)"
codeSetNm="NBS_CARE_STATUS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_34_2" name="Supervisory Review/Comments" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_34_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_34_2">
<tr id="patternNBS_INV_STD_UI_34_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_34_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_34_2">
<tr id="nopatternNBS_INV_STD_UI_34_2" class="odd" style="display:none">
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
<span title="Note text." id="NBS200_2L">
Note:</span>
</td>
<td>
<span id="NBS200_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS200)"  />
</td> </tr>
<!--skipping Hidden Date Question-->
<!--skipping Hidden Text Question-->
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_37_2" name="Pregnant Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Assesses whether or not the patient is pregnant." id="INV178_2L" >
Is the patient pregnant?:</span></td><td>
<span id="INV178_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV178)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS128_2L" title="Number of weeks pregnant at the time of diagnosis.">
Weeks:</span>
</td><td>
<span id="NBS128_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS128)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient pregnant at the initial exam for the condition?" id="NBS216_2L" >
Pregnant at Exam:</span></td><td>
<span id="NBS216_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS216)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS217_2L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of the initial exam.">
Weeks:</span>
</td><td>
<span id="NBS217_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS217)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the patient pregnant at the time of interview for the condition." id="NBS218_2L" >
Pregnant at Interview:</span></td><td>
<span id="NBS218_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS218)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS219_2L" title="The duration of the pregnancy in weeks at exam if the patient was pregnant at the time of interview.">
Weeks:</span>
</td><td>
<span id="NBS219_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS219)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Is the patient receiving/received prenatal care for this pregnancy?" id="NBS220_2L" >
Currently in Prenatal Care:</span></td><td>
<span id="NBS220_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS220)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Determine if the patient has been pregnant during the last 12 months. If currently pregnant, a  Yes  answer indicates that the patient had another pregnancy within the past 12 months, not including her current  Pegnancy." id="NBS221_2L" >
Pregnant in Last 12 Months:</span></td><td>
<span id="NBS221_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS221)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If pregnant in the last 12 months, indicate the outcome of the pregnancy." id="NBS222_2L" >
Pregnancy Outcome:</span></td><td>
<span id="NBS222_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS222)"
codeSetNm="PREGNANCY_OUTCOME"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_39_2" name="Patient HIV Status" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The patient's HIV status." id="NBS153_2L" >
900 Status:</span></td><td>
<span id="NBS153_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS153)"
codeSetNm="STATUS_900"/>
</td> </tr>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was the HIV status of this case investigated through search of eHARS?" id="INV892_2L" >
HIV Status Documented Through eHARS Record Search:</span></td><td>
<span id="INV892_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV892)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Enter the state-assigned HIV Case Number for this patient." id="NBS269_2L">
State HIV (eHARS) Case ID:</span>
</td>
<td>
<span id="NBS269_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS269)" />
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Mode of exposure from eHARS for HIV+ cases." id="INV894_2L" >
Transmission Category (eHARS):</span></td><td>
<span id="INV894_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV894)"
codeSetNm="PHVS_TRANSMISSIONCATEGORY_STD"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Was this case selected by reporting jurisdiction for enhanced investigation?" id="INV895_2L" >
Case Sampled for Enhanced Investigation:</span></td><td>
<span id="INV895_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV895)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_41_2" name="Risk Factors (Last 12 Months)" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Were behavioral risk factors assessed for the client?" id="NBS229_2L" >
Was Behavioral Risk Assessed:</span></td><td>
<span id="NBS229_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS229)"
codeSetNm="RISK_PROFILE_IND"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_42_2" name="Sex Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with a male within past 12 months?" id="STD107_2L" >
Had Sex with Male:</span></td><td>
<span id="STD107_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD107)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with a female within past 12 months?" id="STD108_2L" >
Had Sex with Female:</span></td><td>
<span id="STD108_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD108)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with transgender partner within past 12 months?" id="NBS230_2L" >
Had Sex with Transgender:</span></td><td>
<span id="NBS230_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS230)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with an anonymous partner within past 12 months?" id="STD109_2L" >
Had Sex with Anonymous Partner:</span></td><td>
<span id="STD109_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD109)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_43_2" name="Sex Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex without a condom within past 12 months?" id="NBS231_2L" >
Had Sex Without a Condom:</span></td><td>
<span id="NBS231_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS231)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex while intoxicated/high within past 12 months?" id="STD111_2L" >
Had Sex While Intoxicated/High:</span></td><td>
<span id="STD111_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD111)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex in exchange for drugs/money within past 12 months?" id="STD112_2L" >
Exchanged Drugs/Money for Sex:</span></td><td>
<span id="STD112_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD112)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Female only.  Had sex with a known MSM within past 12 months?" id="STD113_2L" >
Females - Had Sex with Known MSM:</span></td><td>
<span id="STD113_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD113)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Had sex with a known Injection Drug User within past 12 months?" id="STD110_2L" >
Had Sex with Known IDU:</span></td><td>
<span id="STD110_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD110)"
codeSetNm="HAD_SEX_WITH_YOUNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_44_2" name="Risk Behavior" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Incarcerated within the past 12 months?" id="STD118_2L" >
Been Incarcerated:</span></td><td>
<span id="STD118_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD118)"
codeSetNm="YNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Used injection drugs within the past 12 months?" id="STD114_2L" >
Injection Drug Use:</span></td><td>
<span id="STD114_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD114)"
codeSetNm="YNRD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Shared injection equipment within the past 12 months?" id="NBS232_2L" >
Shared Injection Equipment:</span></td><td>
<span id="NBS232_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS232)"
codeSetNm="YNRD"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_45_2" name="Drug Use Past 12 Months" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left"><span class="staticComment">&nbsp;&nbsp;During the past 12 months, indicate whether or not the patient used any of the following injection or non-injection drugs.</span></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="No drug use within the past 12 months?" id="NBS233_2L" >
No drug use reported:</span></td><td>
<span id="NBS233_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS233)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Cocaine use within the past 12 months?" id="NBS237_2L" >
Cocaine:</span></td><td>
<span id="NBS237_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS237)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Crack use within the past 12 months?" id="NBS235_2L" >
Crack:</span></td><td>
<span id="NBS235_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS235)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Heroine use within the past 12 months?" id="NBS239_2L" >
Heroin:</span></td><td>
<span id="NBS239_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS239)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Methamphetamine (meth)use within the past 12 months?" id="NBS234_2L" >
Methamphetamine:</span></td><td>
<span id="NBS234_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS234)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Nitrate/popper use within the past 12 months?" id="NBS236_2L" >
Nitrates/Poppers:</span></td><td>
<span id="NBS236_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS236)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Erectile dysfunction drug use within the past 12 months?" id="NBS238_2L" >
Erectile Dysfunction Medications:</span></td><td>
<span id="NBS238_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS238)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Other drug use within the past 12 months?" id="NBS240_2L" >
Other drug used:</span></td><td>
<span id="NBS240_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS240)"
codeSetNm="PHVS_YNRD_CDC"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="If patient indicated other drug use, specify the drug name(s)." id="STD300_2L">
Specify Other Drug Used:</span>
</td>
<td>
<span id="STD300_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(STD300)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_48_2" name="Places to Meet Partners" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Places to Meet Partners" id="NBS242_2L" >
Places to Meet Partners:</span></td><td>
<span id="NBS242_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS242)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_49_2" name="Places Selected to Meet Partners" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_49_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_49_2">
<tr id="patternNBS_INV_STD_UI_49_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_49_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_49_2">
<tr id="nopatternNBS_INV_STD_UI_49_2" class="odd" style="display:none">
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
<span id="NBS243_2L" title="Select the Hangout or Meetup.">
Place:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS243)"/>
<span id="NBS243_2_Disp"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_50_2" name="Places to have Sex" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Places to Have Sex" id="NBS244_2L" >
Places to Have Sex:</span></td><td>
<span id="NBS244_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS244)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_51_2" name="Places Selected to Have Sex" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_51_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_51_2">
<tr id="patternNBS_INV_STD_UI_51_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_51_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_51_2">
<tr id="nopatternNBS_INV_STD_UI_51_2" class="odd" style="display:none">
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
<span id="NBS290_2L" title="Select the Hangout or Sex.">
Place:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS290)"/>
<span id="NBS290_2_Disp"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_53_2" name="Partners Past Year" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Female partners claimed in the last 12 months?" id="NBS223_2L" >
Female Partners (Past Year):</span></td><td>
<span id="NBS223_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS223)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS224_2L" title="The total number of female partners claimed in the last 12 months.">
Number Female (Past Year):</span>
</td><td>
<span id="NBS224_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS224)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Male partners claimed in the last 12 months?" id="NBS225_2L" >
Male Partners (Past Year):</span></td><td>
<span id="NBS225_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS225)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS226_2L" title="The total number of male partners claimed in the last 12 months.">
Number Male (Past Year):</span>
</td><td>
<span id="NBS226_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS226)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Transgender partners claimed in the last 12 months?" id="NBS227_2L" >
Transgender Partners (Past Year):</span></td><td>
<span id="NBS227_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS227)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS228_2L" title="The total number of transgender partners claimed in the last 12 months.">
Number Transgender (Past Year):</span>
</td><td>
<span id="NBS228_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS228)"  />
</td></tr>
<!--skipping Hidden Numeric Question-->
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD888_2L" title="Patient refused to answer questions regarding number of sex partners">Patient refused to answer questions regarding number of sex partners:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(STD888)" styleId="STD888_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD999_2L" title="Unknown number of sex partners in last 12 months">Unknown number of sex partners in last 12 months:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(STD999)" styleId="STD999_2"><html:optionsCollection property="codedValue(YNU)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_54_2" name="Partners in Interview Period" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Female sex/needle-sharing interview period partners claimed?" id="NBS129_2L" >
Female Partners (Interview Period):</span></td><td>
<span id="NBS129_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS129)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS130_2L" title="The total number of female sex/needle-sharing interview period partners claimed.">
Number Female (Interview Period):</span>
</td><td>
<span id="NBS130_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS130)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Male sex/needle-sharing interview period partners claimed?" id="NBS131_2L" >
Male Partners (Interview Period):</span></td><td>
<span id="NBS131_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS131)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS132_2L" title="The total number of male sex/needle-sharing interview period partners claimed.">
Number Male (Interview Period):</span>
</td><td>
<span id="NBS132_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS132)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Transgender sex/needle-sharing interview period partners claimed?" id="NBS133_2L" >
Transgender Partners (Interview Period):</span></td><td>
<span id="NBS133_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS133)"
codeSetNm="YNUR"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS134_2L" title="The total number of transgender sex/needle-sharing interview period partners claimed.">
Number Transgender (Interview Period):</span>
</td><td>
<span id="NBS134_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS134)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_55_2" name="Partner Internet Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Have you met sex partners through the Internet in the last 12 months?" id="STD119_2L" >
Met Sex Partners through the Internet:</span></td><td>
<span id="STD119_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD119)"
codeSetNm="YNRUD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_57_2" name="Target Populations" isHidden="F" classType="subSect" >

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span id="NBS271_2L" title="Target populations identified for the patient.">
Target Population(s):</span></td><td>
<span id="NBS271_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(NBS271)"
codeSetNm="TARGET_POPULATIONS"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_59_2" name="Syphilis Test Results" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Have  non-treponemal or treponemal syphilis tests been performed?" id="NBS275_2L" >
Tests Performed?:</span></td><td>
<span id="NBS275_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS275)"
codeSetNm="YN"/>
</td> </tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD122_2L" title="What type of non-treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">Type of Nontreponemal Serologic Test for Syphilis:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(STD122)" styleId="STD122_2"><html:optionsCollection property="codedValue(PHVS_NONTREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD123_2L" title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024.">Nontreponemal Serologic Syphilis Test Result (Quantitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(STD123)" styleId="STD123_2"><html:optionsCollection property="codedValue(PHVS_QUANTITATIVESYPHILISTESTRESULT_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD126_2L" title="Qualitative test result of STD123 Nontreponemal serologic syphilis test result (quantitative)">Nontreponemal Serologic Syphilis Test Result (Qualitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(STD126)" styleId="STD126_2"><html:optionsCollection property="codedValue(PHVS_LABTESTREACTIVITY_NND)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD124_2L" title="What type of treponemal serologic test for syphilis was performed on specimen collected to support case patient's diagnosis of syphilis?">Type of Treponemal SerologicTest for Syphilis:</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(STD124)" styleId="STD124_2"><html:optionsCollection property="codedValue(PHVS_TREPONEMALSEROLOGICTEST_STD)" value="key" label="value" /> </html:select> </td></tr>
<!--skipping Hidden Coded Question-->
<tr style="display:none"><td class="fieldName">
<span class="InputDisabledLabel" id="STD125_2L" title="If the test performed provides a qualitative result, provide qualitative result, e.g. weakly reactive.">Treponemal Serologic Syphilis Test Result  (Qualitative):</span>
</td><td><html:select name="PageForm" property="pageClientVO2.answer(STD125)" styleId="STD125_2"><html:optionsCollection property="codedValue(PHVS_LABTESTRESULTQUALITATIVE_NND)" value="key" label="value" /> </html:select> </td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_71_2" name="STD Lab Test Results" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_71_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_71_2">
<tr id="patternNBS_INV_STD_UI_71_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_71_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_71_2">
<tr id="nopatternNBS_INV_STD_UI_71_2" class="odd" style="display:none">
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
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290_2L" >
Test Type:</span></td><td>
<span id="INV290_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV290)"
codeSetNm="PHVS_LABTESTTYPE_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." id="INV291_2L" >
Test Result:</span></td><td>
<span id="INV291_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV291)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Coded quantitative test result (used for Nontreponemal serologic syphilis test result)." id="STD123_1_2L" >
Test Result Coded Quantitative:</span></td><td>
<span id="STD123_1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD123_1)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Quantitative Test Result Value" id="LAB628_2L">
Test Result Quantitative:</span>
</td>
<td>
<span id="LAB628_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB628)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Units of measure for the Quantitative Test Result Value" id="LAB115_2L" >
Test Result Units:</span></td><td>
<span id="LAB115_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB115)"
codeSetNm="UNIT_ISO"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date result sent from Reporting Laboratory" id="LAB167_2L">Lab Result Date:</span>
</td><td>
<span id="LAB167_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB167)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165_2L" >
Specimen Source:</span></td><td>
<span id="LAB165_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LAB165)"
codeSetNm="PHVS_SPECIMENSOURCE_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Anatomic site or specimen type from which positive lab specimen was collected." id="LAB165_2_OthL">Other Specimen Source:</span></td>
<td> <span id="LAB165_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(LAB165Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of collection of initial laboratory specimen used for diagnosis of health event reported in this case report. PREFERRED date for assignment of MMWR week.  First date in hierarchy of date types associated with case report/event." id="338822_2L">Specimen Collection Date:</span>
</td><td>
<span id="338822_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(338822)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_72_2" name="Antimicrobial Susceptibility Testing(AST)" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_72_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_72_2">
<tr id="patternNBS_INV_STD_UI_72_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_72_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_72_2">
<tr id="nopatternNBS_INV_STD_UI_72_2" class="odd" style="display:none">
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
<span title="Pathogen/Organism Identified in Isolate." id="LABAST1_2L" >
Microorganism Identified in Isolate:</span></td><td>
<span id="LABAST1_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST1)"
codeSetNm="PHVS_ORGANISMIDENTIFIEDAST_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Isolate identifier unique for each isolate within laboratory." id="LABAST2_2L">
Isolate Identifier:</span>
</td>
<td>
<span id="LABAST2_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST2)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3_2L" >
Specimen Type:</span></td><td>
<span id="LABAST3_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST3)"
codeSetNm="PHVS_SPECIMENTYPEAST_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Antimicrobial Susceptibility Specimen Type (e.g. Exudate, Blood, Serum, Urine)" id="LABAST3_2_OthL">Other Specimen Type:</span></td>
<td> <span id="LABAST3_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(LABAST3Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" id="LABAST4_2L" >
Specimen Collection Site:</span></td><td>
<span id="LABAST4_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST4)"
codeSetNm="PHVS_SPECIMENCOLLECTIONSITEAST_STD"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName">
<span title="Anatomic site where the specimen was collected (e.g. Urethra, Throat, Nasopharynx)" id="LABAST4_2_OthL">Other Specimen Collection Site:</span></td>
<td> <span id="LABAST4_2_Oth" /> <nedss:view name="PageForm" property="pageClientVO2.answer(LABAST4Oth)"/></td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Antimicrobial Susceptibility Specimen Collection Date" id="LABAST5_2L">Specimen Collection Date:</span>
</td><td>
<span id="LABAST5_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST5)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Type would includes drugs, enzymes, PCR and other genetic tests to detect the resistance against specific drugs." id="LABAST6_2L" >
AST Type:</span></td><td>
<span id="LABAST6_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST6)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTTYPE_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Method (e.g. E-Test, MIC, Disk Diffusion)" id="LABAST7_2L" >
AST Method:</span></td><td>
<span id="LABAST7_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST7)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTMETHOD_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Interpretation (e.g. Susceptible, Resistant, Intermediate, Not tested)" id="LABAST8_2L" >
AST Interpretation:</span></td><td>
<span id="LABAST8_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST8)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTINTERPRETATION_STD"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Result - Coded Quantitative. List of coded values (i.e. valid dilutions) to represent the antimicrobial susceptibility test result." id="LABAST11_2L" >
AST Result Coded Quantitative:</span></td><td>
<span id="LABAST11_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST11)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTRESULTQUANTITATIVE_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Antimicrobial Susceptibility Test Result Quantitative Value (e.g. Quantitative MIC values, Disk Diffusion size in mm)" id="LABAST9_2L">
AST Result Quantitative Value:</span>
</td>
<td>
<span id="LABAST9_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST9)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Antimicrobial Susceptibility Test Result Numerical Value Units (e.g. microgram/ml, mm)" id="LABAST10_2L" >
Test Result Units:</span></td><td>
<span id="LABAST10_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(LABAST10)"
codeSetNm="PHVS_SUSCEPTIBILITYTESTRESULTUNITS_STD"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_61_2" name="Signs and Symptoms" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_61_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_61_2">
<tr id="patternNBS_INV_STD_UI_61_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_61_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_61_2">
<tr id="nopatternNBS_INV_STD_UI_61_2" class="odd" style="display:none">
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
<span title="Is the sign/symptom experienced by the patient or observed by a clinician?" id="NBS246_2L" >
Source:</span></td><td>
<span id="NBS246_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS246)"
codeSetNm="SIGN_SX_OBSRV_SOURCE"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The earliest date the symptom was first experienced by the patient and/or the date the sign was first observed by a clinician." id="NBS247_2L">Observation/Onset Date:</span>
</td><td>
<span id="NBS247_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS247)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Sign/symptom observed on exam or described." id="INV272_2L" >
Sign/Symptom:</span></td><td>
<span id="INV272_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(INV272)"
codeSetNm="SIGN_SX_STD"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span id="STD121_2L" title="The anatomic site of the sign/symptom.">
Anatomic Site:</span></td><td>
<span id="STD121_2" />
<nedss:view name="PageForm" property="pageClientVO2.answerArray(STD121)"
codeSetNm="PHVS_CLINICIANOBSERVEDLESIONS_STD"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td class="fieldName">
<span title="Provide a description of the other anatomic site." id="NBS248_2L">
Other Anatomic Site, Specify:</span>
</td>
<td>
<span id="NBS248_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS248)" />
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName">
<span id="NBS249_2L" title="The number of days signs/symptoms were present. Document “99” if unknown.">
Duration (Days):</span>
</td><td>
<span id="NBS249_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS249)"  />
</td></tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_63_2" name="Previous STD History" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Does the patient have a history of ever having had an STD prior to the condition reported in this case report?" id="STD117_2L" >
Previous STD history (self-reported)?:</span></td><td>
<span id="STD117_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD117)"
codeSetNm="YNUR"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_64_2" name="STD History" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_64_2"; String batchrec[][]= null;   Iterator mapIt = map2.entrySet().iterator();
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
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<th width="<%=batchrec[i][4]%>%"><font color="black"><%=batchrec[i][3]%></font></th>
<%} %>
<%} %>
</tr>
</thead>
<tbody id="questionbodyNBS_INV_STD_UI_64_2">
<tr id="patternNBS_INV_STD_UI_64_2" class="odd" style="display:none">
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked2(this.id,'NBS_INV_STD_UI_64_2');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
<% for(int i=0;batchrec!=null && i<batchrec.length;i++){%>
<% String validdisplay =batchrec[i][0]; %>
<%    if(batchrec[i][2] != null && batchrec[i][2].equals("Y"))  {%>
<td width="<%=batchrec[i][4]%>%" align="left">
<span id="table<%=batchrec[i][0]%>"> </span>
</td>
<%} %>
<%} %>
</tr>
</tbody>
<tbody id="noquestionbodyNBS_INV_STD_UI_64_2">
<tr id="nopatternNBS_INV_STD_UI_64_2" class="odd" style="display:none">
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
<span title="With what condition was the patient previously diagnosed?" id="NBS250_2L" >
Previous Condition:</span></td><td>
<span id="NBS250_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS250)"
codeSetNm="STD_HISTORY_DIAGNOSIS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Diagnosis Date of previous STD." id="NBS251_2L">Diagnosis Date:</span>
</td><td>
<span id="NBS251_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS251)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Treatment Date of previous STD." id="NBS252_2L">Treatment Date:</span>
</td><td>
<span id="NBS252_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS252)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span style="color:#CC0000">*</span>
<span title="Confirmed the Previous STD?" id="NBS253_2L" >
Confirmed:</span></td><td>
<span id="NBS253_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS253)"
codeSetNm="YN"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_66_2" name="Consented to Enrollment in Partner Services" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Document whether the patient accepted or declined enrollment into Partner Services (i.e. did s/he accept the interview)." id="NBS257_2L" >
Enrolled in Partner Services:</span></td><td>
<span id="NBS257_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS257)"
codeSetNm="ENROLL_HIV_PARTNER_SERVICES_IND"/>
</td> </tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_67_2" name="Self-Reported Results" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Previously tested for 900?" id="NBS254_2L" >
Previous 900 Test:</span></td><td>
<span id="NBS254_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS254)"
codeSetNm="YNRUD"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Enter the self-reported or documented HIV test result at time of notification.  This should be the most recent test." id="STD106_2L" >
Self-reported or Documented Result:</span></td><td>
<span id="STD106_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(STD106)"
codeSetNm="STD_SELF_REPORTED_900_TEST_RESULT"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date of last 900 Test" id="NBS259_2L">Date Last 900 Test:</span>
</td><td>
<span id="NBS259_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS259)"  />
</td></tr>
</logic:equal>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_68_2" name="Referred to Testing" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Referred for 900 test?" id="NBS260_2L" >
Refer for Test:</span></td><td>
<span id="NBS260_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS260)"
codeSetNm="YN"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Date referred for 900 test." id="NBS261_2L">Referral Date:</span>
</td><td>
<span id="NBS261_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS261)"  />
</td></tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="900 test performed at this event?" id="NBS262_2L" >
900 Test:</span></td><td>
<span id="NBS262_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS262)"
codeSetNm="900_TST_AT_EVENT"/>
</td> </tr>
</logic:equal>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Indicate the date that the specimen for the HIV test was collected." id="NBS450_2L">900 Test Sample Date:</span>
</td><td>
<span id="NBS450_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS450)"  />
</td></tr>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Result of 900 test at this event." id="NBS263_2L" >
900 Result:</span></td><td>
<span id="NBS263_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS263)"
codeSetNm="900_TEST_RESULTS"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The partner was informed of their 900 test result?" id="NBS265_2L" >
Result provided:</span></td><td>
<span id="NBS265_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS265)"
codeSetNm="900_RESULT_PROVIDED"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Post-test Counselling" id="NBS264_2L" >
Post-test Counselling:</span></td><td>
<span id="NBS264_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS264)"
codeSetNm="YNU"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if a client received a syphilis test in conjunction with an HIV test during partner services activities." id="NBS447_2L" >
Patient Tested for Syphilis In Conjunction with HIV Test:</span></td><td>
<span id="NBS447_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS447)"
codeSetNm="YN"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate the outcome of the current syphilis test in conjunction with an HIV test while enrolled in partner services." id="NBS448_2L" >
Syphilis Test Result:</span></td><td>
<span id="NBS448_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS448)"
codeSetNm="SYPHILIS_TEST_RESULT_PS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_69_2" name="Referred to Medical Testing (900 +)" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Referred to 900 medical care/evaluation/treatment." id="NBS266_2L" >
Refer for Care:</span></td><td>
<span id="NBS266_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS266)"
codeSetNm="YN"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="If referred, did patient keep 1st appointment?" id="NBS267_2L" >
Keep Appointment:</span></td><td>
<span id="NBS267_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS267)"
codeSetNm="KEEP_FIRST_APPT"/>
</td> </tr>
</logic:equal>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="Enter the date the client attended his/her HIV medical care appointment after HIV diagnosis, current HIV test, or report to Partner Services." id="NBS302_2L">Appointment Date (If Confirmed):</span>
</td><td>
<span id="NBS302_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS302)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_75_2" name="Pre Exposure Prophylaxis (PrEP)" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the client is currently on pre-exposure prophylaxis (PrEP) medication." id="NBS443_2L" >
Is the Client Currently On PrEP?:</span></td><td>
<span id="NBS443_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS443)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Indicate if the client was referred to a provider for pre-exposure prophylaxis (PrEP)." id="NBS446_2L" >
Has Client Been Referred to PrEP Provider?:</span></td><td>
<span id="NBS446_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS446)"
codeSetNm="PREP_REFERRAL_PS"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_70_2" name="Anti-Retroviral Therapy for HIV Infection" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Anti-virals taken within the last 12 months?" id="NBS255_2L" >
Anti-viral Therapy - Last 12 Months:</span></td><td>
<span id="NBS255_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS255)"
codeSetNm="YNUR"/>
</td> </tr>
</logic:equal>
<logic:equal name="PageForm" property="securityMap2.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="Anti-virals ever taken (including past year)?" id="NBS256_2L" >
Anti-viral Therapy - Ever:</span></td><td>
<span id="NBS256_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS256)"
codeSetNm="YNUR"/>
</td> </tr>
</logic:equal>
</nedss:container>
</nedss:container>
</div> </td></tr>

<!-- ################# SECTION ################  -->
<tr><td>
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "").concat("_2") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_28_2" name="Risk Assessment" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The priority of the contact investigation, which should be determined based upon a number of factors, including such things as risk of transmission, exposure site type, etc." id="NBS055_2L" >
Contact Investigation Priority:</span></td><td>
<span id="NBS055_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS055)"
codeSetNm="NBS_PRIORITY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date from which the disease or condition is/was infectious, which generally indicates the start date of the interview period." id="NBS056_2L">Infectious Period From:</span>
</td><td>
<span id="NBS056_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS056)"  />
</td></tr>

<!--processing Date Question-->
<tr><td class="fieldName">
<span title="The date until which the disease or condition is/was infectious, which generally indicates the end date of the interview period." id="NBS057_2L">Infectious Period To:</span>
</td><td>
<span id="NBS057_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS057)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_UI_29_2" name="Administrative Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName">
<span title="The status of the contact investigation." id="NBS058_2L" >
Contact Investigation Status:</span></td><td>
<span id="NBS058_2" />
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS058)"
codeSetNm="PHC_IN_STS"/>
</td> </tr>

<!--processing TextArea-->
<tr> <td class="fieldName">
<span title="General comments about the contact investigation, which may include detail around how the investigation was prioritized, or comments about the status of the contact investigation." id="NBS059_2L">
Contact Investigation Comments:</span>
</td>
<td>
<span id="NBS059_2"/>
<nedss:view name="PageForm" property="pageClientVO2.answer(NBS059)"  />
</td> </tr>
</nedss:container>
</nedss:container>
</div> </td></tr>
