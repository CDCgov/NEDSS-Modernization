<%@ include file="/jsp/tags.jsp" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*"%>
<%@ page import="gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns" %> 

<%@ page isELIgnored ="false" %>

<html lang="en">
    <head>
		<title>${fn:escapeXml(PageTitle)}</title>
		<base target="_self">
        <%@ include file="/jsp/resources.jsp" %>
       <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JAssociateToInvestigationsForm.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="associateInvestigations.js"></SCRIPT>
		<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script language="JavaScript">

		
		 	function onKeyUpValidate()
	 		{      	  
	        	if(getElementByIdOrByName("SearchText1").value != ""){
	         		getElementByIdOrByName("b1SearchText1").disabled=false;
	         		getElementByIdOrByName("b2SearchText1").disabled=false;
	         	   }else if(getElementByIdOrByName("SearchText1").value == ""){
	         		getElementByIdOrByName("b1SearchText1").disabled=true;
	         		getElementByIdOrByName("b2SearchText1").disabled=true;
	         	   }
	         	   
			}
			function makeMSelects() {
				$j("#sdate").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#inv").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#juris").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#cond").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#stat").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#invstat").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
						
			}						
			function showCount() {
				$j(".pagebanner b").each(function(i){ 
					$j(this).append(" of ").append($j("#queueCnt").attr("value"));
				});
				$j(".singlepagebanner b").each(function(i){ 
					var cnt = $j("#queueCnt").attr("value");
					if(cnt > 0)
						$j(this).append(" Results 1  to ").append(cnt).append(" of ").append(cnt);
				});				
			}
			function createLink(element, url)
			{
				// call the JS function to block the UI while saving is on progress.
				blockUIDuringFormSubmissionNoGraphic();
                document.forms[0].action= url;
                document.forms[0].submit();  
			}
			
	    function submitBtn(){
	    	var procDecisionVal = "";
	        if (checkDispositionNeeded()) {
	        	OpenProcessingDecisionPopup("Association");
	        	procDecisionVal = getElementByIdOrByName("processingDecisionReason").value;
	        	if (procDecisionVal == "" || procDecisionVal ==null)  //indicates cancel on Proc Decision
	        		return false;
	        	if (procDecisionVal == "BFP") {
	        		if (checkForNewAssocToNonSyphInves()) {
	        			BFPAssocToNonSyphError();
	        			return false;
	        		}	
	        	} //BFP
	        } //checkDispositionNeeded
	        
	        if (checkForErrors()) //submit with nothing changed?
	        	return false;
		if (checkForCompletelyDisassociatedOnSubmit()) {
			var confirmDisMsg = "This report will no longer be associated to any investigation(s). It will be marked as unprocessed and sent back to the Documents Requiring Review Queue. Are you sure you want to disassociate?";
			if (!confirm(confirmDisMsg))
				return false;
		}
	        // next call the JS function to block the UI while saving is on progress.
	        blockUIDuringFormSubmission();
	        // continue with the form submission
	        setCheckBoxValue();	        	
	        var submit='${fn:escapeXml(formHref)}';
	        //fn:escapeXml converts & to &amp;, as its url it requires to have & instead of &amp;. Converting back to &
	        document.forms[0].action =submit.replaceAll("&amp;","&");
	        document.forms[0].submit();
	    }
	function cancelButton(){
			
		var cancel ='${fn:escapeXml(cancelButtonHref)}';
		var confirmMsg="If you continue with the Cancel action, you will lose the information you have entered. Select OK to continue, or Cancel to not continue.";
		if (confirm(confirmMsg)) {
						//fn:escapeXml converts & to &amp;, as its url it requires to have & instead of &amp;. Converting back to &
		            	document.forms[0].action =cancel.replaceAll("&amp;","&");
		     	        document.forms[0].submit();
		} 
	 }	    
	 function checkDispositionNeeded(){
		//check if we are associating and STD to a closed investigation
		var procLogic = getElementByIdOrByName("theProcDecisionLogic").value; 
		if (procLogic == "NA")
			return false; //not STD
 	        var elements = $j('input.uniqueForMorb');
 	        var wasAssocElements = $j('td.wasAssoc');
 	        var dispoElements = $j('td.hadDispo');
 	        var progAreaElements = $j('td.progArea');
 	        var invesStatusElements = $j('td.invStatCls');
		//add checked checkboxes to a string for processing
	        for(var i = 0; i < elements.length; i++)   {
	                if(elements[i].type == 'checkbox') {  //safety check
	                         if(elements[i].checked == true) {
	                         	var wasAssoc = wasAssocElements[i].innerText;
	                         	var hadDispo = dispoElements[i].innerText;
	               				var hadProgArea = progAreaElements[i].innerText;
	               				var hadInvesStatus = invesStatusElements[i].innerText;
	               				if ((hadInvesStatus.indexOf("Closed")!=-1) && (wasAssoc=="false") && (hadDispo==""))
	               					return true;
	                         } //if checked
	                } //if checkbox
 	    	}//for
 	    	return false;
	    }	      
	    
	    function checkForNewAssocToNonSyphInves() {
	     	var conditionElName = "";
	     	var conditionSubstr = "";
 	        var wasAssoc = "";
	     	var checkboxElements = $j('input.uniqueForMorb');
 	        var wasAssocElements = $j('td.wasAssoc');
 	        var conditionElements = $j('td.hadCondition');

	        for(var i = 0; i < checkboxElements.length; i++)   {
	                if(checkboxElements[i].type == 'checkbox') {  
	                	wasAssoc = wasAssocElements[i].innerText;
	                	if(checkboxElements[i].checked == true) {
	                             if (wasAssoc == "false") {
	                        		conditionElName = conditionElements[i].innerText;
	                        		if (conditionElName.length > 7) {
	                        			conditionSubstr = conditionElName.substring(0,7);
	                        			if (conditionSubstr != "Syphili")
	                        				return true;
	                        		}
	                             }
	                         } //if checked  
	                } //chkbox
 	    	} //for 
 	    	return false;
	    } //checkForNewAssocToSyphInves()  
	    
	    function checkForErrors(){
	    
                var errorMsgArray = new Array(); 
		if (checkForNoItemsChangedOnSubmit())
			errorMsgArray.push("No changes to associations were made.  Please try again.  If you do not wish to change any associations, choose Cancel.\n");
	    
	    	if(errorMsgArray!=null && errorMsgArray.length > 0){
                    	displayGlobalErrorMessage(errorMsgArray);
                    	return true;
                }
                $j(".errFeedback").hide();
                return false;  //no errors
	    }
	    
	    function BFPAssocToNonSyphError() {
	    	var errorMsgArray = new Array();
	    	errorMsgArray.push("Error: You can not select a BFP disposition for a non-syphilis Investigation\n");
	    	displayGlobalErrorMessage(errorMsgArray);
	    }
	    
	    function checkForNoItemsChangedOnSubmit(){
	    	var itemsChanged = false;
 	        var checkboxElements = $j('input.uniqueForMorb');
 	        var wasAssocElements = $j('td.wasAssoc');
		//see if anything was changed
	        for(var i = 0; i < checkboxElements.length; i++)   {
	                if(checkboxElements[i].type == 'checkbox') {  //safety check
	                	var wasAssoc = wasAssocElements[i].innerText;
	                	if(checkboxElements[i].checked == true) {
	                             if (wasAssoc == "false")
	                        		itemsChanged = true;
	                         } else {
	                             if (wasAssoc == "true")
	                         		itemsChanged = true;
	                         }    
	                } //chkbox
 	    	} //for	    
	    	if (itemsChanged)
	    		return false;
	    	else
	    		return true;
	    
	    }
	    
	    function checkForCompletelyDisassociatedOnSubmit(){
	    	var allUnassociated = true;
	    	var hadAssociated = false;
 	        var checkboxElements = $j('input.uniqueForMorb');
 	        var wasAssocElements = $j('td.wasAssoc');
		//see if anything was changed
	        for(var i = 0; i < checkboxElements.length; i++)   {
	                if(checkboxElements[i].type == 'checkbox') {  //safety check
	                	var wasAssoc = wasAssocElements[i].innerText;
	                	if(checkboxElements[i].checked == true) {
	                        	allUnassociated = false;
	                         } else {
	                             if (wasAssoc == "true")
	                         		hadAssociated = true;
	                         }    
	                } //chkbox
 	    	} //for	    
	    	if (allUnassociated && hadAssociated)
	    		return true;
	    	else
	    		return false;
	    }
	    
	    
	    function setCheckBoxValue(){
	        var sb = "";
	        var filler = getElementByIdOrByName("chkboxIds");
 	        var elements = $j('input.uniqueForMorb');
		//add checked checkboxes to a string for processing
	        for(var i = 0; i < elements.length; i++)   {
	                if(elements[i].type == 'checkbox') {  //safety check
	                         if(elements[i].checked == true) {
	                        	sb = sb + elements[i].name + "|";
	                         }
	                }
 	    	}        
	        filler.value=sb;
	    }
	    //this function called during onload to only allow a single investigation to be checked
	    //for a morbidity observation
	    function setMorbUnique(){ 
	        if (document.title != "Associate Morbidity Report to an Investigation")
	        	return;
		$j('input.uniqueForMorb').click(function() {
    		$j('input.uniqueForMorb:checked').not(this).removeAttr('checked');
		}); 
	    }		
			
		</script>
		<style type="text/css">
		.removefilter{
			background-color:#003470; width:100%; height:25px;
			line-height:25px;float:right;text-align:right;
			}
			removefilerLink {vertical-align:bottom;  }
			.hyperLink
			{
			    font-size : 10pt;
			    font-family : Geneva, Arial, Helvetica, sans-serif;
			    color : #FFFFFF;
				text-decoration: none;
			}
		</style>
		
   </head>
    <body onload="attachIcons();makeMSelects();displayTooltips();startCountdown();setMorbUnique()">
    <div id="blockparent"></div>
   <div id="doc3">
                 
 
  <!-- Container Div: To hold top nav bar, button bar, body and footer -->
     
<!-- Body div -->
<div id="bd">
   <!-- Top Nav Bar and top button bar -->
    <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>

              <!-- Top button bar -->
            <table role="presentation" alt ="" style="background-image: url('task_button/tb_cel_bak.jpg');background-repeat: repeat-x;" 
                        class="bottomButtonBar">
                  <tr>
                     <!-- General page actions like create, edit, delete and print --> 
                     <td style="vertical-align:top; padding:0px;">
                          <table role="presentation" align="right">
                              <tr>
                                  <td style="vertical-align:top; padding-top:0px;">
                                        <input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Submit" id="Submit" 
                                                alt="Submit button" title="Submit button" class="cursorHand" onclick="submitBtn()"><br/>Submit
                                  </td>
                                  <td style="vertical-align:top; padding-top:0px;">
                                        <input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Cancel" id="Cancel" alt="Cancel button" title="Cancel button" 
                                                class="cursorHand" onclick="javascript:cancelButton();"><br/>Cancel
                                  </td>
                              </tr>            
                          </table>
                     </td>
                 </tr>
          </table>
          
         <!-- Required Field Indicator -->
        <div style="text-align:right; width:100%; margin-top:0.5em;"> 
            <span style="color:#CC0000;"> * </span>
            <span style="color:black; font-style:italic;"> Indicates a Required Field </span>  
        </div> 
        
         <!-- Instruction Box  -->
     	<div class="infoBox messages" align="left">
     		<%
		  String instructions = (String) request.getAttribute("PageTitle") == "Associate Morbidity Report to an Investigation" ? 
		  "To associate the Morbidity Report to an investigation, please select an investigation and choose Submit. If associating a STD or HIV report to a Closed STD or HIV investigation, a report processing decision is required." :  "To associate the Lab Report to investigation(s), please select one or more investigations and choose Submit. If associating a STD or HIV report to Closed STD or HIV investigation(s), a report processing decision is required.";
	  	%>	
	       <p style="text-align:left"><%= instructions %>
	</div>     
 		  
        <!-- Error Messages using Action Messages-->
        <div id="globalFeedbackMessagesBar" class="screenOnly errFeedback">
            <logic:messagesPresent name="error_messages">
                <div class="infoBox errors" id="errorMessages">
                    <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
                    <ul>
                        <html:messages id="msg" name="error_messages">
                            <li> <bean:write name="msg" /> </li>
                        </html:messages>
                    <ul>
                </div>
            </logic:messagesPresent>
        </div>
			
			
			         
          
     <!-- Show the Patient Name Line  -->
    <% if(request.getAttribute("personLocalID")!=null) {%>	 
	  <table role="presentation" class="style">
	  	<tr class="cellColor">
	  		<td class="border" colspan="2">
	  			<%
	  			String name = (String) request.getAttribute("patientFullLegalName") == null ? "---" :  (String) request.getAttribute("patientFullLegalName");
	  			name = name.trim(); 
	 		        if (name.length() != 0) {
	  				name = name;
	  			}else
	  				name="---";
	  					            
	  			String suffix = (String) request.getAttribute("patientSuffixName") == null ? "" : (String) request.getAttribute("patientSuffixName");
	  			if (suffix.trim().length() != 0)
	  				{
	  				name = name + ", "+suffix;
	  				}
	  			String currentSex =  request.getAttribute("currSexCd") == null ? "---" :  CachedDropDowns.getCodeDescTxtForCd((String) request.getAttribute("currSexCd"),"SEX");
	  			currentSex = currentSex.trim(); 
	  			if(currentSex.length() !=0){
	  				currentSex = currentSex;
	  			}else
	  				currentSex="---";
	  					            
	  			String DOB = (String) request.getAttribute("birthTime") == null ? "---" :  (String) request.getAttribute("birthTime");
	  			if(DOB.length() !=0){
	  				DOB = DOB;
	  			}else
	  				DOB="---";
	  			//DOB = currentsex.trim();
	  					           
	  			%>
	  			<span class="valueTopLine"> <%= name %> </span>
	  			<span class="valueTopLine">|</span>
	  			<span class="valueTopLine"> <%= currentSex %> </span>
	  			<span class="valueTopLine">|</span>
	  			<%if(request.getAttribute("currentAgeUnitCd") != null){%>
	  			<span class="valueTopLine"> <%= DOB %> (<%=request.getAttribute("currentAge")%> <%=CachedDropDowns.getCodeDescTxtForCd((String)request.getAttribute("currentAgeUnitCd"),"P_AGE_UNIT")%>) </span>
	  				<%}else{ %>
	  			<span class="valueTopLine"> <%= DOB %>  </span>
	  				<%} %>
	  			</td>
	  			<td style="padding:0.15em;width:24%; border-style:solid;border-color:#AFAFAF;text-align:right;}">
	  				<span class="valueTopLine"> Patient ID: </span>
	  			<span style="font:16px Arial; margin-left:0.2em;">${fn:escapeXml(personLocalID)} </span>
	  			<span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
	  			</td>
	  			</tr>
	  			</table>
      <%}%>  <!-- end of Patient Name Line  -->
          
        <html:form action="/LoadInvestigationsToAssoc.do">
          <table role="presentation" width="100%">
             <tr>
             <td align="center">
				<display:table name="investigationList" class="dtTable" style="margin-top:0em;" pagesize="${associateToInvestigationsForm.attributeMap.queueSize}"  id="parent" requestURI="/LoadInvestigationsToAssoc.do?method=loadQueue&existing=true" sort="external" export="true" excludedParams="answerArray(STARTDATE) answerArray(INVESTIGATOR) answerArray(JURISDICTION) answerArray(CONDITION) answerArray(CASESTATUS) answerArray(INVSTATUS) answerArrayText(SearchText1) method">
					  	<display:setProperty name="export.csv.filename" value="AssociateInvestigationsQueue.csv"/>
					  	<display:setProperty name="export.pdf.filename" value="AssociateInvestigationsQueue.pdf"/>
						<display:column title="Associate" style="width:5%;">
							<div title="Select/Deselect All" align="center" valign="top" style="text-align: center; vertical-align: middle;">
							<input type="checkbox" class="uniqueForMorb" value="${parent.isAssociated}" name="${parent.publicHealthCaseUid}" ${parent.checkBoxId}  ${parent.disabled}/>
							</div>
                    				</display:column>				  	
						<display:column property="activityFromTime_s" title="Start Date" format="{0,date,MM/dd/yyyy}" sortable="true" sortName="getActivityFromTime" defaultorder="descending" style="width:9%;"/>
						<display:column property="investigationStatusDescTxt" class="invStatCls" title="Status" sortable="true" sortName="getInvestigationStatusDescTxt" defaultorder="descending" style="width:10%;"/>
						<display:column property="conditionCodeTextLnk" title="Condition" media="html" class="hadCondition" sortable="true" sortName="getConditionCodeText" defaultorder="descending" style="width:18%;"/>
						<display:column property="conditionCodeText" title="Condition" media="csv pdf" sortable="true" sortName="getConditionCodeText" defaultorder="descending" style="width:21%;"/>			
						<display:column property="caseClassCodeTxt" title="CaseStatus" sortable="true" sortName="getCaseClassCodeTxt" defaultorder="descending" style="width:13%;"/>
						<display:column property="jurisdictionDescTxt" title="Jurisdiction" sortable="true" sortName="getJurisdictionDescTxt" defaultorder="descending" style="width:14%;"/>
						<display:column property="investigatorFullName" title="Investigator" sortable="true" sortName="getInvestigatorFullName" defaultorder="descending" style="width:15%;"/>
						<display:column property="isAssociated" title="wasAssociated" class="hidden wasAssoc" headerClass="hidden" media="html" />
						<display:column property="dispositionCd" title="Disposition" class="hidden hadDispo" headerClass="hidden" media="html" />
						<display:column property="progAreaCd" title="ProgramArea" class="hidden progArea" headerClass="hidden" media="html" />
						<display:setProperty name="basic.empty.showtable" value="true"/>
			       	</display:table>
		      </td>
		     </tr>
		  </table>
	 	  <table role="presentation"> 
	 		<tr>
	 		<td>  
	  		<html:hidden name="associateToInvestigationsForm" property="selectedcheckboxIds" styleId="chkboxIds"/>
	  		</td>
	  		<td>
	  		<html:hidden name="associateToInvestigationsForm" property="processingDecision" styleId="processingDecisionReason"/>
	  		</td>
	  		<td>
	  		<html:hidden name="associateToInvestigationsForm" property="processingDecisionLogic" styleId="theProcDecisionLogic"/>
	  		</td> 	  		
	  		</tr>
	  	  </table>		  

			
            <div class="printexport" id="printExport" align="right">
				<img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
				<img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
			</div>	
			<div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
			<div class="removefilter" id="removeFilters">
				<a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
			</div>
		   </div>
		   <%@ include file="AssociateInvestigationsDropDowns.jsp" %>

      </div>
      <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
	</html:form>
              <!-- Bottom button bar -->
            <table role="presentation" alt ="" style="background-image: url('task_button/tb_cel_bak.jpg');background-repeat: repeat-x;" 
                        class="bottomButtonBar">
                  <tr>
                     <!-- General page actions like submit cancel--> 
                     <td style="vertical-align:top; padding-right:10px;">
                          <table role="presentation" align="right">
                              <tr>
                                  <td style="vertical-align:top; padding-top:0px;">
                                        <input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Submit" id="Submit" 
                                                alt="Submit button" title="Submit button" class="cursorHand" onclick="submitBtn()"><br/>Submit
                                  </td>
                                  <td style="vertical-align:top; padding-top:0px;">
                                        <input type="image" src="task_button/fa_submit.jpg" width="30" height="40" border="0" name="Cancel" id="Cancel" alt="Cancel button" title="Cancel button"
                                                class="cursorHand" onclick="cancelButton();"><br/>Cancel
                                  </td>
                              </tr>            
                          </table>
                     </td>
                 </tr>
          </table>		   
</body>
</html>