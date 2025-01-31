<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ include file="/jsp/resources.jsp" %> 
<%@ page import="gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm" %>    	
<%@ page import="gov.cdc.nedss.entity.person.vo.PatientSrchResultVO" %>	
<%@ page import="gov.cdc.nedss.webapp.nbs.form.file.FileDetailsForm" %>
<%@ page import="gov.cdc.nedss.entity.person.vo.PatientSrchResultVO" %>	
<%@ page import="gov.cdc.nedss.entity.person.vo.PersonVO" %>
<%@ page import="gov.cdc.nedss.entity.person.dt.PersonNameDT" %>
<%@ page import="gov.cdc.nedss.util.StringUtils" %>
<%@ page import="java.util.*" %>


<%
	String viewEventsOnlyAttr = (String)request.getAttribute("viewEventsOnly");
	boolean viewEventsOnly = false;
	if (viewEventsOnlyAttr != null && viewEventsOnlyAttr.equals("true")) {
		viewEventsOnly = true;
	}
%>     
 <style type="text/css">

	.header{
		background-color:white; width:100%; height:35px;
			line-height:35px;float:right;text-align:right;
		}
		
		.showup{}
  </style>
    <script language="JavaScript"> 
           function  getWorkUpPage(target){
               window.location = target;
           }


           function getComparePage(target){
        	   
        	   var checkElements = document.getElementsByName("selectCheckBox");
        	   var index=0;
        	   for(var i=0; i<checkElements.length; i++){
        		  var isChecked = checkElements[i].checked;
        		   if(isChecked){
        			   var href = document.getElementById("eventSumaryInv").getElementsByTagName("tr")[i+1].getElementsByTagName("td")[1].getElementsByTagName("a")[0].getAttribute("href")
        			   var phc = href.substring(href.lastIndexOf("=")+1);
        			   target+="&publicHealthCaseUID"+index+"="+phc;
					   index++;
				  }
        		   
        	   }
        	
        	   window.location = target;
           }

           
           function AddVaccinationPopUp(target)
           {
	           	var urlToOpen = "/nbs/PageAction.do?method=createGenericLoad&mode=Create&Action=DSFilePath&businessObjectType=VAC";
	           	var divElt = getElementByIdOrByName("pageview");
	           	if (divElt == null) {
	           		divElt = getElementByIdOrByName("blockparent");
	           	}
	           	divElt.style.display = "block";
	           	var o = new Object();
	           	o.opener = self;
	           	
	           	var modWin = openWindow(urlToOpen, o, GetDialogFeatures(980, 900, false, true), divElt, "");
           }
              
      </script>
  	
  	   
  <%
	  String strInvestigationEventSize =request.getAttribute("strInvestigationEventSize")!=null?
			  							(String)request.getAttribute("strInvestigationEventSize"):"0";
	  int investigationEventSize = 0;
	  if(request.getAttribute("strInvestigationEventSize") != null){
		  investigationEventSize = new Integer(strInvestigationEventSize).intValue();
	  }
	 
	  String statusInvestigation ="expand";
	  if(investigationEventSize > 0)
	  	statusInvestigation ="collapse";
    
    String eventMorbReportListsize = request.getAttribute("eventMorbReportListsize")!=null ? 
    								(String)request.getAttribute("eventMorbReportListsize"):"0";    
    int morbReportSize = 0;   
    if(request.getAttribute("eventMorbReportListsize") !=null){
    	morbReportSize= new Integer(eventMorbReportListsize).intValue();
    }
    String marbReport ="expand";
    if(morbReportSize > 0)
    	marbReport ="collapse";
    
    String eventLabReportListsize = request.getAttribute("eventLabReportListSize")!=null?
    								(String)request.getAttribute("eventLabReportListSize"):"0"; 
    int labReportSize = 0;   
    if(request.getAttribute("eventLabReportListSize") !=null){
    	labReportSize= new Integer(eventLabReportListsize).intValue();
    }
    String labReport ="expand";
    if(labReportSize > 0)
    	labReport ="collapse";
    	
    	
    String eventVaccinationDTListSize = request.getAttribute("eventVaccinationDTListSize")!=null?
    								(String)request.getAttribute("eventVaccinationDTListSize"):"0"; 
    int vaccinationSize = 0;   
    if(request.getAttribute("eventVaccinationDTListSize") !=null){
    	vaccinationSize= new Integer(eventVaccinationDTListSize).intValue();
    }
    String vaccination ="expand";
    if(vaccinationSize > 0)
    	vaccination ="collapse";
    
    
	String eventTreatmentDTListSize = request.getAttribute("eventTreatmentDTListSize")!=null?
									(String)request.getAttribute("eventTreatmentDTListSize"):"0"; 
	int treatmentSize = 0;   
	if(request.getAttribute("eventTreatmentDTListSize") !=null){
		treatmentSize= new Integer(eventTreatmentDTListSize).intValue();
	}
	String treatment ="expand";
	if(treatmentSize > 0)
	treatment ="collapse";
    	
	String eventSummaryDocListSize = request.getAttribute("eventSummaryDocListSize")!=null?
									(String)request.getAttribute("eventSummaryDocListSize"):"0"; 
	int caseReportsSize = 0;   
	if(request.getAttribute("eventSummaryDocListSize") !=null){
		caseReportsSize= new Integer(eventSummaryDocListSize).intValue();
	}
	String caseReports ="expand";
	if(caseReportsSize > 0)
		caseReports ="collapse";
    	
    	
	String contactDTListSize = request.getAttribute("contactDTListSize")!=null?
									(String)request.getAttribute("contactDTListSize"):"0"; 
	int contactRecordsSize = 0;   
	if(request.getAttribute("contactDTListSize") !=null){
		contactRecordsSize= new Integer(contactDTListSize).intValue();
	}
	String contactRecords ="expand";
	if(contactRecordsSize > 0)
		contactRecords ="collapse";    	
    String invAddFunction = "getWorkUpPage('"+request.getAttribute("addInvButtonHref")+"');";
    String labAddFunction = "getWorkUpPage('"+request.getAttribute("addLabButtonHref")+"');";
    String morbAddFunction = "getWorkUpPage('"+request.getAttribute("addMorbButtonHref")+"');";
    String vaccAddFunction = "AddVaccinationPopUp('"+request.getAttribute("addVacButtonHref")+"');";
  %>
 
 
  <script language="JavaScript"> 
	
		function invMergeFunction() {
				
			$j("#errorMessagesMerge").css("display","none");//Hide the errors div from merge
			validateSelection();
			
		}

		
		  /**
		  	 * validateSelection(): show the alert message when different condition or legacy pages selected
		  	 */
	
			function validateSelection() {

				var checkboxes = document.getElementsByName('selectCheckBox');
				var condition;
				var PageBuilderCdStatus = true;
				var PageBuilderCd;
				var PageBuilderCds = [];
				var count = 0;
				var conditions = [];
				var table = getElementByIdOrByName("eventSumaryInv");
				var indexDocumentType = 1;
				var indexLocalId = 8;
				var indexCondition = 3;
				var indexPageBuilderCd = 10;
				//var documentIds= new Array();

				for (var i = 1; i <= checkboxes.length; i++) {
					var row = table.getElementsByTagName("tr")[i];
					if (row != null
							&& row != 'undefined'
							&& getCorrectAttribute(
									row.getElementsByTagName("input")[0],
									"checked",
									row.getElementsByTagName("input")[0].checked) == true) {//todo getCorrectAttribute

						condition = row.getElementsByTagName("td")[indexCondition].innerHTML;
						PageBuilderCd = row.getElementsByTagName("td")[indexPageBuilderCd].innerHTML;
						conditions.push(condition);
						PageBuilderCds.push(PageBuilderCd);
						if (PageBuilderCd != "T")
							PageBuilderCdStatus = false;
						count++;
					}
				}

				if (!PageBuilderCdStatus) {
					getElementByIdOrByName('errorBlock').setAttribute("style","display:true;width:99%");
					getElementByIdOrByName('errorBlock').innerText = "You can only select two Page Builder investigations to compare. Legacy investigations cannot use Merge functionality.";
				} else if (count < 2) {
					getElementByIdOrByName('errorBlock').setAttribute("style", "display:true;width:99%");
					getElementByIdOrByName('errorBlock').innerText = "You must select two investigations to compare.";
				} else if (count > 2) {
					getElementByIdOrByName('errorBlock').setAttribute("style","display:true;width:99%");
					getElementByIdOrByName('errorBlock').innerText = "You can only select two investigations to compare.";
				} else if (conditions[0] != conditions[1]) {
					getElementByIdOrByName('errorBlock').setAttribute("style","display:true;width:99%");
					getElementByIdOrByName('errorBlock').innerText = "You can only select two investigations with the same condition to compare.";
				} else if (PageBuilderCdStatus
						&& conditions[0] == conditions[1]
						&& PageBuilderCds[0] == PageBuilderCds[1]) {
					getElementByIdOrByName('errorBlock').setAttribute("style","display:none;width:99%");
					//Write Compare Method
					getComparePage('/nbs/ViewFile1.do?ContextAction=CompareInvestigations');
				}

			}
		function tableCollapseEvent() {
			var inv = '<%= investigationEventSize %>';
 		if(inv == 0 ){
 		   toggleSectionDisplay1('subsect_Inv');
 		   if(getElementByIdOrByName('Compare')!= 'undefined' && getElementByIdOrByName('Compare')!= null) getElementByIdOrByName('Compare').setAttribute("style", "display:none");
 		}
 		var labReport = '<%= labReportSize %>';
		if(labReport == 0 ){
			toggleSectionDisplay1('subsect_Lab');
 		}
 		var morbReport = '<%= morbReportSize %>';
 		if(morbReport == 0 ){
 			toggleSectionDisplay1('subsect_Morb');
 		}
 		var vaccination = '<%= vaccinationSize %>';
		if(vaccination == 0 ){
			toggleSectionDisplay1('subsect_Vaccination');
 		}
 		var treatment = '<%= treatmentSize %>';
		if(treatment == 0 ){
			toggleSectionDisplay1('subsect_Treatment');
 		} 
 		var caseReports = '<%= caseReportsSize %>';
		if(caseReports == 0 ){
			toggleSectionDisplay1('subsect_CaseReport');
 		} 
 		var contactRecords = '<%= contactRecordsSize %>';
		if(contactRecords == 0 ){
			toggleSectionDisplay1('subsect_ContactRecords');
 		} 		
       	}
  </script>
 
         <div id="blockparent"></div>
         
      <tr><td> 
      			
	<div class="view" id="viewWorkupEvents" >
         	<div class="showup" style="text-align:right;">
			      		   <a  href="javascript:toggleAllSectionsDisplayWorkup('viewWorkupEvents',2)"/><font class="hyperLink"> Expand All</font></a>
			      		   | <a  href="javascript:toggleAllSectionsDisplayWorkup('viewWorkupEvents',1)"/><font class="hyperLink"> Collapse All</font></a>
					 </div>
				<div> 
			         <table role="presentation"><tr><td height="05 px"></td></tr></table>
         </div>
 <table role="presentation" class="sectionsToggler" style="width:100%;">
        <tr>
            <td>
                <ul class="horizontalList">
        
                    <li style="margin-right:5px;"><b>Go to: </b></li>        			
	<% if (viewEventsOnly) { %>
                    <li><a href="javascript:gotoSectionWorkup('subsect_basicInfo')">Patient Summary</a></li>
                    <li class="delimiter"> | </li>
		
	<% } %>
                    <li><a href="javascript:gotoSectionWorkup('subsect_Inv')">Investigations</a></li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_Lab')">Lab Reports</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_Morb')">Morbidity Reports</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_Vaccination')">Vaccinations</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_Treatment')">Treatments</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_CaseReport')">Documents</a> </li>
                    <li class="delimiter"> | </li>
                    <li><a href="javascript:gotoSectionWorkup('subsect_ContactRecords')">Contact Records</a> </li>
                 </ul>
            </td>
        </tr>
        <tr>
           <!-- <td>
                <a class="toggleHref" href="javascript:toggleAllSectionsDisplayWorkup('viewWorkupEvents')"/></a>
            </td>-->
        </tr>
    </table>
        
				
	<% if (viewEventsOnly) { 
		CompleteDemographicForm fform = (CompleteDemographicForm)request.getSession().getAttribute("DSPersonForm");
  			PatientSrchResultVO pVo = fform.getPatSrcResVO();
  		%>   
	    <nedss:bluebar id="subsect_basicInfo" name="Patient Summary" defaultDisplay="F" classType="bluebarsect" >
		  <nedss:bluebar id="Summary_summary" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
		  
			<tr>  
						                        <td valign="top" width="25%"><c:out value="${pVo.getPersonAddressProfile()}" escapeXml="false"/></td> 
						                        <td valign="top" width="25%"><c:out value="${pVo.getPersonPhoneprofile()}"/></td>       
						                        <td valign="top" width="25%"><c:out value="${pVo.getPersonIds()}"/></td>       
						                        <td valign="top" width="25%"><c:out value="${pVo.getProfile()}"/></td>                   
						                               
				                    </tr>
	          </nedss:bluebar>                 
		</nedss:bluebar>        
<% } %> 

		<table role="presentation" width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr bgcolor="#003470">
				<td height="25 px" valign="center" align="left"><font class="boldTwelveYellow">
				&nbsp;Patient Events History</font>
				</td>
				<td align="right">
				
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2"></td>
			</tr>
		</table>        

	<nedss:bluebar id="subsect_Inv" name="Investigations" classType="bluebarsect" displayLink="F" buttonLabel="Add New" buttonPermission="<%=(String)request.getAttribute(\"addInves\") %>" buttonJSFunction="<%=invAddFunction%>" buttonLabel1="Compare" buttonPermission1="<%=(String)request.getAttribute(\"mergeInves\") %>" buttonJSFunction1="invMergeFunction();" count="<%= strInvestigationEventSize %>" 
				defaultDisplay="<%= statusInvestigation %>" >
				
		<nedss:bluebar id="inv1" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
			<logic:equal name="viewInves" value="true">
				<tr><td colspan="3" style="font-weight:bold;width:99%">
					<div id="errorBlock" class="screenOnly infoBox errors" style="display:none;width:99%"></div>
				</td></tr>
				<logic:present name="success_merge" scope="request">
				<tr><td colspan="3" style="width:99%">
				 <div class="infoBox success" id="successMessagesMerge"> 
				${success_merge}
                    </div>
				</td></tr>
				</logic:present>
				
				<logic:present name="error_merge" scope="request">
				<tr><td colspan="3" style="width:99%">
				 <div class="infoBox errors" id="errorMessagesMerge"> 
				${error_merge}
                    </div>
				</td></tr>
				</logic:present>
				
				
				<tr><td>		
				<display:table name="strInvestigationEventList" class="bluebardtTable"  id="eventSumaryInv">
		 		<logic:equal name="mergeInves" value="true">
   				    <display:column title="<p style='display:none'>Select/Deselect All</p>" style="width:1.5%;text-align:center;" media="html"><input title="Select/Deselect checkbox" type="checkbox" name="selectCheckBox"/> </display:column>
   				</logic:equal>
				   <display:column property="startDate" style="width:8%;text-align:left;" title="Start Date"/>
				   <display:column property="status" style="width:6%;text-align:left;" title="Status"/>
				   <display:column property="conditions" style="width:24%;text-align:left;" title="Condition"/>                                                  
				   <display:column property="caseStatus" style="width:8%;text-align:left;" title="Case Status"/>
				   <display:column property="notification" style="width:10%;text-align:left;" title="Notification"/>
				   <display:column property="jurisdiction" style="width:12%;text-align:left;" title="Jurisdiction"/>
				   <display:column property="investigator" style="width:14%;text-align:left;" title="Investigator"/>
				   <display:column property="investigationId" style="width:10%;text-align:left;" title="Investigation ID "/>
				   <display:column property="coinfectionId" style="width:18%;text-align:left;" title="Co-Infection ID "/>
				   <display:column property="mergeInvStatus" style="width:18%;text-align:left;" title="<p style='display:none'>Merge Status</p" class="hidden" headerClass="hidden"/> 
				</display:table> 
			</td></tr>
			</logic:equal>
			<logic:equal name="viewInves" value="false">
				<tr><td>&nbsp;</td></tr>
	     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
			    <tr><td>&nbsp;</td></tr>
			</logic:equal>			
	     </nedss:bluebar>   
	</nedss:bluebar>
	<nedss:bluebar id="subsect_Lab" name="Lab Reports" classType="bluebarsect" displayLink="F" buttonLabel="Add New" buttonPermission="<%=(String)request.getAttribute(\"addObs\") %>" buttonJSFunction="<%=labAddFunction%>" count="<%= eventLabReportListsize %>" 
				defaultDisplay="<%= labReport %>">
     <nedss:bluebar id="lab1" displayImg="F" displayLink="F" defaultDisplay="F" includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
    	<logic:equal name="viewObs" value="true">
     		<tr><td>                
		      <display:table name="eventLabReportList" class="bluebardtTable"  id="eventLabReport">
			   <display:column property="dateReceived" style="width:11%;text-align:left;" title="Date Received"/>		                                                     
			   <display:column property="providerFacility" style="width:22%;text-align:left;" title="Facility/Provider"/>
			   <display:column property="dateCollected" style="width:11%;text-align:left;" title="Date Collected"/>
			   <display:column property="description" style="width:34%;text-align:left;" title="Test Results"/>
			   <display:column property="associatedWith" style="width:12%;text-align:left;" title="Associated With"/>
			   <display:column property="progArea" style="width:15%;text-align:left;" title="Program Area"/>
			   <display:column property="jurisdiction" style="width:15%;text-align:left;" title="Jurisdiction"/>
			   <display:column property="eventId" style="width:10%;text-align:left;" title="Event ID "/>
		      </display:table> 
		    </td></tr>
	    </logic:equal>
		<logic:equal name="viewObs" value="false">
			<tr><td>&nbsp;</td></tr>
     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
		    <tr><td>&nbsp;</td></tr>
		</logic:equal>		      
	    </nedss:bluebar>   
	</nedss:bluebar>
	       <nedss:bluebar id="subsect_Morb" name="Morbidity Reports" classType="bluebarsect" displayLink="F" buttonLabel="Add New" buttonPermission="<%=(String)request.getAttribute(\"addMorb\") %>" buttonJSFunction="<%=morbAddFunction%>"  count="<%= eventMorbReportListsize %>" 
				defaultDisplay="<%= marbReport %>">
      <nedss:bluebar id="mob1" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
          <logic:equal name="viewMorb" value="true">
      		<tr><td>               
		      <display:table name="eventMorbReportList" class="bluebardtTable"  id="eventMorbReport">
			   <display:column property="dateReceived" style="width:11%;text-align:left;" title="Date Received"/>		                                                     
			   <display:column property="providerFacility" style="width:22%;text-align:left;" title="Provider"/>
			   <display:column property="dateCollected" style="width:10%;text-align:left;" title="Report Date"/>
			   <display:column property="description" style="width:35%;text-align:left;" title="Condition"/>
			   <display:column property="jurisdiction" style="width:15%;text-align:left;" title="Jurisdiction"/>
			   <display:column property="associatedWith" style="width:12%;text-align:left;" title="Associated With"/>
			   <display:column property="eventId" style="width:10%;text-align:left;" title="Event ID "/>
		      </display:table> 
	      </td></tr>
	      </logic:equal>
			<logic:equal name="viewMorb" value="false">
				<tr><td>&nbsp;</td></tr>
	     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
			    <tr><td>&nbsp;</td></tr>
			</logic:equal>
	     </nedss:bluebar>   
	</nedss:bluebar>
	
	
	
	<nedss:bluebar id="subsect_Vaccination" name="Vaccinations" classType="bluebarsect" displayLink="F"  buttonLabel="Add New" buttonPermission="<%=(String)request.getAttribute(\"addVaccine\") %>" buttonJSFunction="<%=vaccAddFunction%>" count="<%= eventVaccinationDTListSize %>" 
					defaultDisplay="<%= vaccination %>">
	      <nedss:bluebar id="vaccination" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
	         <logic:equal name="viewVacc" value="true">
	      		<tr><td>               
			      <display:table name="eventVaccinationDTList" class="bluebardtTable"  id="eventVaccination">
				   <display:column property="dateReceived" style="width:10%;text-align:left;" title="Date Created"/>		                                                     
				   <display:column property="providerFacility" style="width:22%;text-align:left;" title="Provider"/>
				   <display:column property="dateCollected" style="width:13%;text-align:left;" title="Date Administered"/>
				   <display:column property="description" style="width:33%;text-align:left;" title="Vaccine Administered"/>
				   <display:column property="associatedWith" style="width:12%;text-align:left;" title="Associated With"/>
			   	   <display:column property="eventId" style="width:10%;text-align:left;" title="Event ID "/>
			      </display:table> 
		      </td></tr>
		    </logic:equal>
			<logic:equal name="viewVacc" value="false">
				<tr><td>&nbsp;</td></tr>
	     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
			    <tr><td>&nbsp;</td></tr>
			</logic:equal>
		 </nedss:bluebar>   
	</nedss:bluebar>
	
	<nedss:bluebar id="subsect_Treatment" name="Treatments" classType="bluebarsect" displayLink="F"   count="<%= eventTreatmentDTListSize %>" 
					defaultDisplay="<%= treatment %>">
	      <nedss:bluebar id="treatment" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
	          <logic:equal name="viewTreat" value="true">
	      		<tr><td>               
			      <display:table name="eventTreatmentDTList" class="bluebardtTable"  id="eventTreatment">
				   <display:column property="dateReceived" style="width:10%;text-align:left;" title="Date Created"/>		                                                     
				   <display:column property="providerFacility" style="width:22%;text-align:left;" title="Provider"/>
				   <display:column property="dateCollected" style="width:12%;text-align:left;" title="Treatment Date"/>
				   <display:column property="description" style="width:34%;text-align:left;" title="Treatment"/>
				   <display:column property="associatedWith" style="width:12%;text-align:left;" title="Associated With"/>
			   	   <display:column property="eventId" style="width:10%;text-align:left;" title="Event ID "/>
			      </display:table> 
		       </td></tr>
		      </logic:equal>
				<logic:equal name="viewTreat" value="false">
					<tr><td>&nbsp;</td></tr>
		     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
				    <tr><td>&nbsp;</td></tr>
				</logic:equal>
		     </nedss:bluebar>   
	</nedss:bluebar>
	
	<nedss:bluebar id="subsect_CaseReport" name="Documents" classType="bluebarsect" displayLink="F"   count="<%= eventSummaryDocListSize %>" 
					defaultDisplay="<%= caseReports %>">
	      <nedss:bluebar id="caseReports" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
	           <logic:equal name="viewDoc" value="true">
	      		<tr><td>               
			      <display:table name="eventSummaryDocList" class="bluebardtTable"  id="eventCaseReports">
				   <display:column property="dateReceived" style="width:11%;text-align:left;" title="Date Received"/>		      
				   <display:column property="eventType" style="width:10%;text-align:left;" title="Document Type"/>                                               
				   <display:column property="providerFacility" style="width:22%;text-align:left;" title="Sending Facility"/>
				   <display:column property="dateCollected" style="width:11%;text-align:left;" title="Date Reported"/>
				   <display:column property="description" style="width:34%;text-align:left;" title="Condition"/>
				   <display:column property="associatedWith" style="width:12%;text-align:left;" title="Associated With"/>
			       <display:column property="eventId" style="width:10%;text-align:left;" title="Event ID "/>
			     </display:table> 
		      </td></tr>
		      </logic:equal>
				<logic:equal name="viewDoc" value="false">
					<tr><td>&nbsp;</td></tr>
		     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
				    <tr><td>&nbsp;</td></tr>
				</logic:equal>
		     </nedss:bluebar>   
	</nedss:bluebar>
	
	<nedss:bluebar id="subsect_ContactRecords" name="Contact Records" classType="bluebarsect" displayLink="F" count="<%= contactDTListSize %>" 
					defaultDisplay="<%= contactRecords %>">
	      		<nedss:bluebar id="contactRecords" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
	      		<logic:equal name="viewInves" value="true">
	     		<tr><td><b><U>Contacts Named by Patient:</U></b></td></tr>
	     		<% 	Map contactNamedByPatListMap = (HashMap)request.getAttribute("contactNamedByPatListMap");
	     			if(contactNamedByPatListMap!=null && contactNamedByPatListMap.size()>0){
	     			Set contactNamedByPatListKeySet = contactNamedByPatListMap.keySet();
	     			Iterator contactNamedByPatListIte = contactNamedByPatListKeySet.iterator();
	     		 	while (contactNamedByPatListIte.hasNext()) 
                 	{
	     		   		String conditionKey = (String)contactNamedByPatListIte.next();
	     		   	    ArrayList aList = (ArrayList)contactNamedByPatListMap.get(conditionKey);
	     		   		if(aList==null || aList.size()==0)
	     		   			aList = new ArrayList();
                   		request.setAttribute("contactNamedByPatList", aList);
                 %>
                <tr><td>The following contacts were named in <b><%=request.getAttribute("patientName")==null?"":request.getAttribute("patientName")+"'s" %></b> Investigation of <b><%=conditionKey %></b>:</td></tr>
	      		<tr><td>               
			      <display:table name="contactNamedByPatList" class="bluebardtTable"  id="eventPatContactRecords">
				  <display:column property="createDate" style="width:10%;text-align:left;" title="Date Created"/> 
				  <display:column property="namedBy" style="width:22%;text-align:left;" title="Name"/>
				  <display:column property="dateNamed" style="width:10%;text-align:left;" title="Date Named"/>
				  <display:column property="description" style="width:36%;text-align:left;" title="Description"/>
				  <display:column property="associatedWith" style="width:12%;text-align:left;" title="Associated With"/>
				  <display:column property="eventId" style="width:10%;text-align:left;" title="Event ID"/>
			      </display:table> 
		      </td></tr>
		      <%	} 
	     		 		}%>
		      </logic:equal>
		      	<logic:equal name="viewInves" value="false">
					<tr><td>&nbsp;</td></tr>
		     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
				    <tr><td>&nbsp;</td></tr>
				</logic:equal>
		     </nedss:bluebar>  
		     <nedss:bluebar id="patRecords" displayImg="F" displayLink="F" defaultDisplay="F"  includeBackToTopLink ="F" isHidden="F"  classType="bluebarsubSect" >
		     <logic:equal name="viewInves" value="true">
		     	<tr><td><b><U>Patient Named by Contacts:</U></b></td></tr>
		       <% 	Map patNamedByContactsListMap = (HashMap)request.getAttribute("patNamedByContactsListMap");
	     			if(patNamedByContactsListMap!=null && patNamedByContactsListMap.size()>0){
	     			Set patNamedByContactsListKeySet = patNamedByContactsListMap.keySet();
	     			Iterator patNamedByContactsListIte = patNamedByContactsListKeySet.iterator();
	     		 	while (patNamedByContactsListIte.hasNext()) 
                 	{
	     		   		String conditionKey = (String)patNamedByContactsListIte.next();
	     		   	    ArrayList aList = (ArrayList)patNamedByContactsListMap.get(conditionKey);
	     		   		if(aList==null || aList.size()==0)
	     		   			aList = new ArrayList();
                   		request.setAttribute("patNamedByContactsList", aList);
                %>
              <tr><td><b><%=request.getAttribute("patientName")==null?"":request.getAttribute("patientName")%></b> was named as a contact in the following <b><%=conditionKey %></b> Investigations(s): </td></tr>
			  <tr><td>               		     			      
				<display:table name="patNamedByContactsList" class="bluebardtTable"  id="eventContactContactRecords">
				  <display:column property="createDate" style="width:10%;text-align:left;" title="Date Created"/> 
				  <display:column property="namedBy" style="width:22%;text-align:left;" title="Named By"/>
				  <display:column property="dateNamed" style="width:10%;text-align:left;" title="Date Named"/>
				  <display:column property="description" style="width:36%;text-align:left;" title="Description"/>
				  <display:column property="associatedWith" style="width:12%;text-align:left;" title="Associated With"/>
				  <display:column property="eventId" style="width:10%;text-align:left;" title="Event ID"/>			  
				</display:table>
		      </td></tr>
		      <%	} 
	     		 		}%>
		      </logic:equal>
		      	<logic:equal name="viewInves" value="false">
					<tr><td>&nbsp;</td></tr>
		     		<tr><td><b>&nbsp;&nbsp;You do not have access to view the information on this tab.</b></td></tr>
				    <tr><td>&nbsp;</td></tr>
				</logic:equal>
		     </nedss:bluebar> 
	</nedss:bluebar>
	<input id="ContextAction" type="hidden" name="ContextAction" value="${fn:escapeXml(ContextAction)}">	
	</div>
	<div class="tabNavLinks">
		<% if (!viewEventsOnly) { %>
			<a href="javascript:navigateTab('previous')"> Previous </a>&nbsp;&nbsp;&nbsp;
			<a href="javascript:navigateTab('next')"> Next </a>
		<% } %>
		<input name="endOfTab" type="hidden"/>
	</div>
</td> 
</tr> 
	
	