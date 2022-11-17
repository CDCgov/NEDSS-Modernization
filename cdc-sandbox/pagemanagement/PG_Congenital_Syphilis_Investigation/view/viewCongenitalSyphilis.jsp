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
String tabId = "viewCongenitalSyphilis";
int subSectionIndex = 0;
int sectionIndex = 0;
String sectionId = "";
String [] sectionNames  = {"Congenital Syphilis Information","Maternal Information","Infant Information"};
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
<nedss:container id="NBS_INV_STD_UI_77" name="Congenital Syphilis Report" isHidden="F" classType="subSect" >

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the expected delivery date of the infant" id="NBS388L">Expected Delivery Date:</span>
</td><td>
<span id="NBS388"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS388)"  />
</td></tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS383L" title="The mother's OB/GYN provider.">
Mother OB/GYN:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS383)"/>
<span id="NBS383">${PageForm.attributeMap.NBS383SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS384L" title="Who will be the Delivering Phyisician?">
Delivering MD:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS384)"/>
<span id="NBS384">${PageForm.attributeMap.NBS384SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS386L" title="The name of the hospital where the infant was born.">
Delivering Hospital:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS386)"/>
<span id="NBS386">${PageForm.attributeMap.NBS386SearchResult}</span>
</td> </tr>

<!--processing Participation Question-->
<tr>
<td valign="top" class="fieldName">
<span id="NBS385L" title="Who is the attending Pediatrician?">
Pediatrician:</span>
</td>
<td>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS385)"/>
<span id="NBS385">${PageForm.attributeMap.NBS385SearchResult}</span>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="The Medical Record Number as reported by health care provider or facility" id="NBS336L">
Infant's Medical Record Number:</span>
</td>
<td>
<span id="NBS336"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS336)" />
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_79" name="Mother Administrative Information" isHidden="F" classType="subSect" >

<!--processing Static Comment-->
<tr><td colspan="2" align="left">&nbsp;&nbsp;Please complete this section for the mother's information. Though this information is available on the contact record, information entered into this section will be sent in the case notification to CDC.</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the relationship of the next of kin to the case patient. This question should have a default value for the subject (typically mother of the case) and be hidden on the page." id="NBS387L" >
Next of Kin Relationship:</span></td><td>
<span id="NBS387" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS387)"
codeSetNm="PH_RELATIONSHIP_HL7_2X"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the state of residence of the mother of the case patient" id="MTH166L" >
Mother's Residence State:</span></td><td>
<span id="MTH166" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH166)"
codeSetNm="<%=NEDSSConstants.STATE_LIST%>"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="Enter the zip code of the case patient's mother" id="MTH169L">
Mother's Residence Zip Code:</span>
</td>
<td>
<span id="MTH169"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH169)" />
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="The county of residence of the case patient's mother" id="MTH168L" >
Mother's County of Residence:</span></td><td>
<span id="MTH168" />
<logic:notEmpty name="PageForm" property="pageClientVO.answer(MTH168)">
<logic:notEmpty name="PageForm" property="pageClientVO.answer(MTH166)">
<bean:define id="value" name="PageForm" property="pageClientVO.answer(MTH166)"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH168)" methodNm="CountyCodes" methodParam="${PageForm.attributeMap.MTH168_STATE}"/>
</logic:notEmpty>
</logic:notEmpty>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the country of residence of the case patient's mother" id="MTH167L" >
Mother's Country of Residence:</span></td><td>
<span id="MTH167" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH167)"
codeSetNm="PSL_CNTRY"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the date of birth of the case patient's mother" id="MTH153L">Mother's Date of Birth:</span>
</td><td>
<span id="MTH153"/>
<nedss:view name="PageForm" property="pageClientVO.answer(MTH153)"  />
</td></tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="MTH157L" title="Enter the race of the patient's mother">
Race of Mother:</span></td><td>
<span id="MTH157" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(MTH157)"
codeSetNm="PHVS_RACECATEGORY_CDC_NULLFLAVOR"/>
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

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the marital status of the case patient's mother" id="MTH165L" >
Mother's Marital Status:</span></td><td>
<span id="MTH165" />
<nedss:view name="PageForm" property="pageClientVO.answer(MTH165)"
codeSetNm="P_MARITAL"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_80" name="Mother Medical History" isHidden="F" classType="subSect" >

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="75201_4L" title="Enter the number of pregnancies, include current and previous pregnancies (G)">
Number of Pregnancies:</span>
</td><td>
<span id="75201_4"/>
<nedss:view name="PageForm" property="pageClientVO.answer(75201_4)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="75202_2L" title="Enter the total number of live births">
Number of Live Births:</span>
</td><td>
<span id="75202_2"/>
<nedss:view name="PageForm" property="pageClientVO.answer(75202_2)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Mother Last menstrual period (LMP) start date before delivery." id="752030L">Mother's Last Menstrual Period Before Delivery:</span>
</td><td>
<span id="752030"/>
<nedss:view name="PageForm" property="pageClientVO.answer(752030)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if there was a prenatal visit" id="75204_8L" >
Was There a Prenatal Visit:</span></td><td>
<span id="75204_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(75204_8)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="NBS390L" title="Enter the total number of prenatal visits the mother had related to the pregnancy">
Total Number of Prenatal Visits:</span>
</td><td>
<span id="NBS390"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS390)"  />
</td></tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicate the date of the first prenatal visit" id="75200_6L">Indicate Date of First Prenatal Visit:</span>
</td><td>
<span id="75200_6"/>
<nedss:view name="PageForm" property="pageClientVO.answer(75200_6)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the trimester of the first prenatal visit" id="75163_6L" >
Indicate Trimester of First Prenatal Visit:</span></td><td>
<span id="75163_6" />
<nedss:view name="PageForm" property="pageClientVO.answer(75163_6)"
codeSetNm="PHVS_PREG_TRIMESTER"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject have a Non-treponemal Test or Treponemal Test at First Prenatal Visit" id="75164_4L" >
Did Mother Have Non-Treponemal or Treponemal Test at First Prenatal Visit:</span></td><td>
<span id="75164_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(75164_4)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject have a  Non-treponemal Test or Treponemal Test at 28-32 Weeks Gestation" id="75165_1L" >
Did Mother Have Non-Treponemal or Treponemal Test at 28-32 Weeks Gestation?:</span></td><td>
<span id="75165_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(75165_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject have a Non-treponemal Test or Treponemal Test at Delivery" id="75166_9L" >
Did Mother Have Non-Treponemal or Treponemal Tests at Delivery:</span></td><td>
<span id="75166_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(75166_9)"
codeSetNm="YNU"/>
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_81" name="Mother Interpretive Lab Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_81"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_81">
<tr id="patternNBS_INV_STD_UI_81" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_81');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_81">
<tr id="nopatternNBS_INV_STD_UI_81" class="odd" style="display:none">
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
<td class="fieldName" valign="top">
<span title="Select the appropriate value to indicate timing and subject of the test being entered." id="LAB588_MTHL" >
Lab Test Performed Modifier (Mother):</span></td><td>
<span id="LAB588_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB588_MTH)"
codeSetNm="LAB_TST_MODIFIER_MTH"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the type of lab test performed" id="INV290_MTHL" >
Test Type (Mother):</span></td><td>
<span id="INV290_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290_MTH)"
codeSetNm="PHVS_TESTTYPE_SYPHILIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the result for the lab test being reported." id="INV291_MTHL" >
Test Result (Mother):</span></td><td>
<span id="INV291_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291_MTH)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the quantitative test result for the non-treponemal serologic test of the mother of the case. If the test performed provides a quantifiable result, provide quantitative results as a coded value. For example, if the titer is 1:64, choose the corresponding value from the drop down." id="STD123_MTHL" >
Non-Treponemal Serologic Test Result (Quantitative) (Mother):</span></td><td>
<span id="STD123_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD123_MTH)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Enter the lab result date for the lab test being reported." id="LAB167_MTHL">Lab Result Date (Mother):</span>
</td><td>
<span id="LAB167_MTH"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB167_MTH)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_82" name="Mother Clinical Information" isHidden="F" classType="subSect" >
<logic:equal name="PageForm" property="securityMap.hasHIVPermissions" value="T">

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the HIV status of the mother of case." id="NBS153_MTHL" >
Mother HIV Status:</span></td><td>
<span id="NBS153_MTH" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS153_MTH)"
codeSetNm="PHVS_HIVSTATUS_STD"/>
</td> </tr>
</logic:equal>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the clinical stage of syphilis the mother had during pregnancy" id="75180_0L" >
What CLINICAL Stage of Syphilis Did Mother Have During Pregnancy:</span></td><td>
<span id="75180_0" />
<nedss:view name="PageForm" property="pageClientVO.answer(75180_0)"
codeSetNm="PHVS_SYPHILISCLINICALSTAGE_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the mother's surveillance stage of syphilis during pregnancy" id="75181_8L" >
What SURVEILLANCE Stage of Syphilis Did Mother Have During Pregnancy:</span></td><td>
<span id="75181_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(75181_8)"
codeSetNm="PHVS_SYPHILISSURVEILLANCESTAGE_CS"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Indicate the date the patient received the first dose of benzathine penicillin" id="75182_6L">When Did Mother Receive Her First Dose of Benzathine Penicillin:</span>
</td><td>
<span id="75182_6"/>
<nedss:view name="PageForm" property="pageClientVO.answer(75182_6)"  />
</td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the trimester the patient received the first dose of benzathine penicillin" id="75183_4L" >
Which Trimester Did Mother Receive Her First Dose of Benzathine Penicillin:</span></td><td>
<span id="75183_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(75183_4)"
codeSetNm="PHVS_PREGNANCYTREATMENTSTAGE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate if the mother was treated at least 30 days prior to delivery" id="NBS391L" >
Was the Mother Treated At Least 30 Days Prior to Delivery:</span></td><td>
<span id="NBS391" />
<nedss:view name="PageForm" property="pageClientVO.answer(NBS391)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the mother's treatment" id="75184_2L" >
What Was the Mother's Treatment:</span></td><td>
<span id="75184_2" />
<nedss:view name="PageForm" property="pageClientVO.answer(75184_2)"
codeSetNm="PHVS_SYPHILISTREATMENTMOTHER_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the subject have an appropriate serologic response to treatment" id="75185_9L" >
Did Mother Have an Appropriate Serologic Response:</span></td><td>
<span id="75185_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(75185_9)"
codeSetNm="PHVS_SEROLOGICRESPONSE_CS"/>
</td> </tr>
</nedss:container>
</nedss:container>

<!-- ################# SECTION ################  -->
<nedss:container id='<%= sectionNames[sectionIndex].replaceAll(" ", "") %>' name="<%= sectionNames[sectionIndex++] %>" isHidden="F" classType="sect">

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_84" name="Infant Delivery Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Indicate the vital status of the infant" id="75186_7L" >
Vital Status:</span></td><td>
<span id="75186_7" />
<nedss:view name="PageForm" property="pageClientVO.answer(75186_7)"
codeSetNm="PHVS_BIRTHSTATUS_NND"/>
</td> </tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="DEM229L" title="Infants birth weight in grams">
Birth Weight in Grams:</span>
</td><td>
<span id="DEM229"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM229)"  />
</td></tr>

<!--processing Numeric Question-->
<tr><td class="fieldName" valign="top">
<span id="DEM228L" title="Gestational age in weeks">
Estimated Gestational Age in Weeks:</span>
</td><td>
<span id="DEM228"/>
<nedss:view name="PageForm" property="pageClientVO.answer(DEM228)"  />
</td></tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_85" name="Infant Clinical Information" isHidden="F" classType="subSect" >

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did the infant/child have long bone x-rays" id="75194_1L" >
Did the Infant/Child Have Long Bone X-rays:</span></td><td>
<span id="75194_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(75194_1)"
codeSetNm="PHVS_XRAYRESULT_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Did infant/child placenta or cord have Darkfield exam, DFA, or special stain?" id="75192_5L" >
Did the Infant/Child, Placenta, or Cord Have Darkfield Exam, DFA, or Special Stains:</span></td><td>
<span id="75192_5" />
<nedss:view name="PageForm" property="pageClientVO.answer(75192_5)"
codeSetNm="LAB_RSLT_INTERP_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the interpretation of the CSF WBC count testing results" id="LP48341_9L" >
CSF WBC Count Interpretation:</span></td><td>
<span id="LP48341_9" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP48341_9)"
codeSetNm="LAB_RSLT_CSF_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the interpretation of the CSF Protein level testing results" id="LP69956_8L" >
CSF Protein Level Interpretation:</span></td><td>
<span id="LP69956_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(LP69956_8)"
codeSetNm="LAB_RSLT_PRT_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Enter the CSF VDRL Test Finding" id="75195_8L" >
Did the Infant/Child Have CSF-VDRL:</span></td><td>
<span id="75195_8" />
<nedss:view name="PageForm" property="pageClientVO.answer(75195_8)"
codeSetNm="LAB_RSLT_VDRL_CS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Is this case classified as a syphilitic stillbirth?" id="75207_1L" >
Stillbirth Indicator:</span></td><td>
<span id="75207_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(75207_1)"
codeSetNm="YNU"/>
</td> </tr>

<!--processing Multi Coded Question-->

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span id="INV272L" title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click.">
Did the Infant/Child Have Any Signs of CS (Select All that Apply):</span></td><td>
<span id="INV272" />
<nedss:view name="PageForm" property="pageClientVO.answerArray(INV272)"
codeSetNm="PHVS_SIGNSSYMPTOMS_CS"/>
</td> </tr>
<!--Other allowed for this Coded Question-->
<tr><td class="fieldName" valign="top">
<span title="Select any signs/symptoms that the infant/child had  (select all that apply). Multiple signs/symptoms can be selected by holding down the Ctrl key and selecting the values with a left-mouse click." id="INV272OthL">Other Did the Infant/Child Have Any Signs of CS (Select All that Apply):</span></td>
<td> <span id="INV272Oth" /> <nedss:view name="PageForm" property="pageClientVO.answer(INV272Oth)"/></td></tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Was the infant/child treated for congenital syphilis?" id="75197_4L" >
Was the Infant/Child Treated?:</span></td><td>
<span id="75197_4" />
<nedss:view name="PageForm" property="pageClientVO.answer(75197_4)"
codeSetNm="PHVS_SYPHILISTREATMENTINFANT_CS"/>
</td> </tr>

<!--processing Text Question-->
<tr> <td valign="top" class="fieldName">
<span title="If the treatment type is 'Other', specify the treatment." id="NBS389L">
Other Treatment Specify:</span>
</td>
<td>
<span id="NBS389"/>
<nedss:view name="PageForm" property="pageClientVO.answer(NBS389)" />
</td> </tr>
</nedss:container>

<!-- ########### SUB SECTION ###########  -->
<nedss:container id="NBS_INV_STD_UI_86" name="Infant Interpretive Lab Information" isHidden="F" classType="subSect" >
<tr> <td colspan="2" width="100%">
<table role="presentation" width="100%"  border="0" align="center">
<tr><td width="5%">.</td>
<td   width="90%">
<table role="presentation"  class="dtTable" align="center" >
<thead >
<tr> <%String subSecNm = "NBS_INV_STD_UI_86"; String batchrec[][]= null;   Iterator mapIt = map.entrySet().iterator();
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
<tbody id="questionbodyNBS_INV_STD_UI_86">
<tr id="patternNBS_INV_STD_UI_86" class="odd" style="display:none">
<td style="display:none"><input name="rowKey" type="hidden" value=""></input></td>
<td style="width:3%;text-align:center;"><input id="view" type="image" src="page_white_text.gif" tabIndex="0" onclick="viewClicked(this.id,'NBS_INV_STD_UI_86');return false" 		name="image" align="middle" cellspacing="2" cellpadding="3" border="55" class="cursorHand" title="View" alt="View">
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
<tbody id="noquestionbodyNBS_INV_STD_UI_86">
<tr id="nopatternNBS_INV_STD_UI_86" class="odd" style="display:none">
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
<td class="fieldName" valign="top">
<span title="Select the appropriate value to indicate timing and subject of the test being entered." id="LAB588L" >
Lab Test Performed Modifier (Infant):</span></td><td>
<span id="LAB588" />
<nedss:view name="PageForm" property="pageClientVO.answer(LAB588)"
codeSetNm="LAB_TST_MODIFIER_INFANT"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the type of test(s) performed for this case." id="INV290L" >
Test Type:</span></td><td>
<span id="INV290" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV290)"
codeSetNm="PHVS_TESTTYPE_SYPHILIS"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="Epidemiologic interpretation of the results of the test(s) performed for this case. This is a qualitative test result.  E.g. positive, detected, negative." id="INV291L" >
Test Result:</span></td><td>
<span id="INV291" />
<nedss:view name="PageForm" property="pageClientVO.answer(INV291)"
codeSetNm="PHVS_LABTESTRESULTQUALITATIVE_NND"/>
</td> </tr>

<!--processing Coded Question-->
<tr>
<td class="fieldName" valign="top">
<span title="If the test performed provides a quantifiable result, provide quantitative result (e.g. if RPR is positive, provide titer, e.g. 1:64). Example: If titer is 1:64, enter 64; if titer is 1:1024, enter 1024." id="STD123_1L" >
Nontreponemal Serologic Syphilis Test Result (Quantitative):</span></td><td>
<span id="STD123_1" />
<nedss:view name="PageForm" property="pageClientVO.answer(STD123_1)"
codeSetNm="PHVS_NONTREPONEMALTESTRESULT_STD"/>
</td> </tr>

<!--processing Date Question-->
<tr><td valign="top" class="fieldName">
<span title="Date result sent from Reporting Laboratory" id="LAB167L">Lab Result Date:</span>
</td><td>
<span id="LAB167"/>
<nedss:view name="PageForm" property="pageClientVO.answer(LAB167)"  />
</td></tr>
</nedss:container>
</nedss:container>
<div class="tabNavLinks">
<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
<a href="javascript:navigateTab('next')"> Next </a>
<input name="endOfTab" type="hidden"/>
</div>
</div> </td></tr>
