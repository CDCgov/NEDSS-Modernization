<%-- 
  - Author(s): Greg Tucker
  - Date:
  - Copyright Notice: SRA International 2015
  - @(#)
  - Description: Used to associate co-infections on create and all investigations on view
  - to treatments, (future- interviews and contacts)
  --%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<html lang="en">
    <head>
         <%@ include file="/jsp/resources.jsp" %>
		 <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JAssociateToCoinfectionForm.js"></SCRIPT>
		 <SCRIPT Language="JavaScript" Src="Coinfection.js"></SCRIPT>
		
         <meta http-equiv="MSThemeCompatible" content="yes"/>
	<title>${fn:escapeXml(BaseForm.pageTitle)}</title>
	<base target="_self">	
	<script type="text/javascript">
		var isFormSubmission = false;
		window.onload = function() {
			$j("body").find(':input:visible:enabled:first').focus();
		}
		
		if (typeof window.event != 'undefined')
		  	document.onkeydown = function()
		    		{
				var t=event.srcElement.type;
				if(t == '' || t == 'undefined' || t == 'button') {
					return;
				}
				var kc=event.keyCode;
				if(t == 'text' && kc == 13) {
				}
				
				return preventF12(event);
		    	}

	function cancelPopup()
	{
		var opener = getDialogArgument(); 
		if (getElementByIdOrByNameNode("IXS191", opener.document) != null) //list of coinfections in parent form for Create
			getElementByIdOrByNameNode("IXS191", opener.document).value = "";
		closePopup();
	}
	
		        
	function closePopup()
	{
		if (isFormSubmission == false) {
	                self.close();
	                var opener = getDialogArgument(); 
	                var pageId='${fn:escapeXml(pageId)}';
	                var invest = null; 
	                if(pageId == 'pageview') { 
	                	invest = getElementByIdOrByNameNode("pageview", opener.document);}
	                else        
	                	invest = getElementByIdOrByNameNode("pamview", opener.document)
	                if (invest == null) {
               			  invest = getElementByIdOrByNameNode("blockparent", opener.document);                   
         			 }
         		if (invest != null)
	                	invest.style.display = "none";  
	                return true;
			    } 
		    }
	function showCount() {
	       		$j(".pagebanner b").each(function(i){ 
				$j(this).append(" of ").append($j("#queueCnt").attr("value"));
			});
			$j(".singlepagebanner b").each(function(i){ 
				var cnt = $j("#queueCnt").attr("value");
			if(cnt > 0)
				$j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
			});				
	}
		
	function submitAssociateCoinfections()
	{
		if(validateBeforSubmit())
			return false;
		
		var filler =  setCheckBoxValue();
		var filler = getElementByIdOrByName("chkboxIds");

		var opener = getDialogArgument(); 

		var contextAction = getElementByIdOrByName("ContextAction").value;
		
		if (contextAction=="Edit") {
		     var sourceUid = getElementByIdOrByName("SourceUid").value;
		     //alert("sourceUid=" + sourceUid);
		     document.forms[0].action ="/nbs/LoadManageCoinfectionAssociation.do?method=associateContactToInvestigationsSubmit&ContextAction=Submit&AssociatedInvestigations="+filler.value+"&sourceUid="+sourceUid ;
                     document.forms[0].submit();
             	     return;		
		}
		getElementByIdOrByNameNode("IXS191", opener.document).value = filler.value;
		closePopup();				
	} 
			    
	function isValidCheck(){
	        $j(".infoBox").hide();
    
  		  	var errors = new Array();
  		  	var index = 0;
    		var isError = false;   
    		 
	        var sb = "";
	        var filler = getElementByIdOrByName("chkboxIds");
	        var input = document.getElementsByTagName("input");
	        var dropDownIndex = 0;
	        for(var i = 0; i < input.length; i++)   {
	        	if(input[i].type == 'checkbox') dropDownIndex++;
				if(input[i].type == 'checkbox' && input[i].disabled==false) {
					var selectedValue = getElementByIdOrByName("pdCodes"+dropDownIndex).value;
					if(input[i].checked == true){
						sb = sb + input[i].name + ":";
						if(input[i].disabled==false && (selectedValue==null || selectedValue=="")){
							errors[index++] = "You must select Processing Decision for selected coinfection record.";
							isError = true;	
						}
						
					}else{
						if(input[i].disabled==false && (selectedValue!=null && selectedValue.length>0)){
							errors[index++] = "You must check checkbox for selected processing decision.";
							isError = true;	
						}
						
					}
					
					//If contact has an open envestigation for the same condition and if user select FieldFollowup then show error message to user.
					var invStatus = getElementByIdOrByName("status"+dropDownIndex).value;
					if(input[i].disabled==false && (selectedValue!=null && selectedValue.length>0) && invStatus=='Open' && selectedValue=='FF'){
						var invCondition = getElementByIdOrByName("condition"+dropDownIndex).value;
						errors[index++] = "There is an open investigation for "+invCondition+". A new investigation cannot be created.";
                				isError = true;	
					}
					
				}
	        }

                if(sb = null || sb == ""){ 
                	errors[index++] = "You must select at least one coinfection record to associate";
                	isError = true;	
                }
	        if(isError) {
			displayErrors("errorBlock", errors);
			return false;
		}
		else {
			return true;
		}
	  }

			    
  	  function setCheckBoxValue(){
	        var sb = "";
	        var filler = getElementByIdOrByName("chkboxIds");
	        var input = document.getElementsByTagName("input");
	        for(var i = 0; i < input.length; i++)   {
	                if(input[i].type == 'checkbox') {
	                    if(input[i].checked == true && input[i].disabled==false)
	                        sb = sb + input[i].name + ":";
	                }
	        }
	        filler.value=sb;
	    }

		var isFFSelected = false;
	  	function showFieldFollouwpFields() {
		  	var totalInfections = '${fn:escapeXml(queueCount)}';
		  	var isFFSet = false;
		  	for (i = 1; i <= totalInfections; i++)
		  	{
		  		var selectedValue = getElementByIdOrByName("pdCodes"+i).value;
		  		
		  		var e = getElementByIdOrByName('fieldFollowupBlock');
				if(selectedValue == 'FF' || isFFSet==true){
				   e.style.display = 'block';
				   isFFSet =  true;
				   isFFSelected = true;
				}else{
				   e.style.display = 'none';
		        }
		  	}
	  		        
		}

		function validateBeforSubmit(){
			
			if(!isValidCheck()){
				return true;	
			}
						
			if(isFFSelected==true){
				var errorLabels = new Array();
				
				var INV147 = getElementByIdOrByName('INV147'); // The date the investigation was started or initiated
				var INV110 = getElementByIdOrByName('INV110'); //The date the investigation was assigned/started.
				var NBS143 = getElementByIdOrByName('NBS143'); // Notifiable
	
				var dateFormat = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
				
				if (INV147.value == null || INV147.value == "") {
					getElementByIdOrByName("INV147L").style.color="#CC0000";
					var errHref = "<a href=\"javascript: getElementByIdOrByName('INV147').focus()\">Investigation Start Date</a> is a required field.";
					errorLabels.push(errHref);
				}else if(INV147.value != '' && !INV147.value.match(dateFormat)) {
				    //highlight field label red if format error
					getElementByIdOrByName("INV147L").style.color="#CC0000";
					//var theErrEleId = strINV147LblId;
					//error message text
					var errHref="<a href=\"javascript:getElementByIdOrByName('INV147').focus()\">Investigation Start Date</a> must be in the format of mm/dd/yyyy.";
					errorLabels.push(errHref);
				} 
				else getElementByIdOrByName("INV147L").style.color="black";
	
	
				if (INV110.value == null || INV110.value == "") {
					getElementByIdOrByName("INV110L").style.color="#CC0000";
					var errHref = "<a href=\"javascript: getElementByIdOrByName('INV110').focus()\">Date Assigned to Investigation</a> is a required field.";
					errorLabels.push(errHref);
				}else if(INV110.value != '' && !INV110.value.match(dateFormat)) {
				    //highlight field label red if format error
					getElementByIdOrByName("INV110L").style.color="#CC0000";
					//var theErrEleId = strINV110LblId;
					//error message text
					var errHref="<a href=\"javascript:getElementByIdOrByName('INV110').focus()\">Date Assigned to Investigation</a> must be in the format of mm/dd/yyyy.";
					errorLabels.push(errHref);
				}
				else getElementByIdOrByName("INV110L").style.color="black";
	
	
				if (NBS143.value == null || NBS143.value == "") {
					getElementByIdOrByName("NBS143L").style.color="#CC0000";
					var errHref = "<a href=\"javascript: getElementByIdOrByName('NBS143').focus()\">Notifiable</a> is a required field.";
					errorLabels.push(errHref);
				} else getElementByIdOrByName("NBS143L").style.color="black";
	
				
				if (errorLabels.length > 0) {
					displayErrors('PatientSubmitErrorMessages', errorLabels); 
					return true;
				}
	
				$j('#PatientSubmitErrorMessages').css("display", "none");
				return false;
			
			}
		
		}
	     
	</script>
		        

   </head>
   
  

<body class="popup"  onload="showCount();autocompTxtValuesForJSP();addRolePresentationToTabsAndSections();" onunload="closePopup()">
 
	         <!-- Page Errors -->
                        <!--%@ include file="../../../jsp/feedbackMessagesBar.jsp" %-->	        
		    <!-- error -->
		    
		    	<div id="errorBlock" class="screenOnly infoBox errors" style="display:none">
			</div>

 <html:form action="/LoadManageCoinfectionAssociation.do">
 			<!-- Page title -->
	        <div class="popupTitle">
	        ${fn:escapeXml(BaseForm.pageTitle)}
	        </div>
	        
	        <!-- Top button bar -->
	      	<div class="popupButtonBar">
	            <input type="button" name="Submit" value="Submit" onclick="submitAssociateCoinfections();"/>
	            <input type="button" name="Cancel" value="Cancel" onclick="cancelPopup();" />
	        </div>
	        
		<% if (request.getAttribute("coinfMessage") != null) { %>
			  <div class="infoBox messages" style="text-align: left;">
			  	<%=HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("coinfMessage")))%>
			  </div>    
		<% } %>
	        

 <tr> <td>
 
             
	<html:hidden name="associateToCoinfectionForm" property="selectedcheckboxIds" styleId="chkboxIds"/>
	<table role="presentation" width="100%">
	<tr> <td align="center">
            <!-- Display tab table for listing all coinfection  -->
            <display:table name="coinfectionsSummaryList" class="dtTable" id="coinflist" style="margin-top:0em;" pagesize="${associateToCoinfectionForm.attributeMap.queueSize}"  export="true" requestURI="/LoadManageCoinfectionAssociation.do?method=associateToCoinfectionLoadForContact&existing=true" sort="external">
	        <display:column title="<p style='display:none'>Select/Deselect All</p>" style="width:5%;">
	           	<div align="center" style="margin-top: 3px">
	        		<input type="checkbox" value="${coinflist.associated}" name="${coinflist.publicHealthCaseUid}" ${coinflist.checkBoxId} ${coinflist.disabled}/>
	            </div>
	        </display:column>
	        <display:column property="condition" style="width:20%;text-align:left;" title="Patient's Condition"/>
			<display:column property="investigationStartDate" format="{0,date,MM/dd/yyyy}" style="width:8%;text-align:left;" title="Contact's Start Date"/>
			<display:column property="status" style="width:6%;text-align:left;" title="Contact's Status"/>
			<display:column property="caseStatus" style="width:14%;text-align:left;" title="Contact's Case Status"/>
			<display:column property="jurisdiction" style="width:14%;text-align:left;" title="Contact's Jurisdiction"/>
			<display:column property="investigator" style="width:14%;text-align:left;" title="Contact's Investigator"/>
			<display:column property="localId" style="width:12%;text-align:left;" title="Contact's Investigation ID "/>

		    <display:column sortable="false" style="width:15%;text-align:left;white-space:nowrap;" title="Contact's Processing Decision"  >
				<bean:define id="isAssociated" value="${coinflist.associated}" />
				<logic:equal name="isAssociated" value="true">
					<select Id="pdCodes${coinflist_rowNum}" disabled="true">
						<option selected="true">
						<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON145)"
							codeSetNm="STD_CONTACT_RCD_PROCESSING_DECISION"/>
						</option>
					</select>
				</logic:equal>
				<logic:notEqual name="isAssociated" value="true">	
					<html:select styleId="pdCodes${coinflist_rowNum}" name="associateToCoinfectionForm"  property="processingDecision" onchange="showFieldFollouwpFields()"> 
						<html:optionsCollection property="codedValue(${coinflist.processingDecisionCode})" value="key" label="value"/>
					</html:select>
				</logic:notEqual>
				
				<input type="hidden" id="status${coinflist_rowNum}" value="${coinflist.status}"/> 
				<input type="hidden" id="condition${coinflist_rowNum}" value="${coinflist.condition}"/>
			</display:column>
			 
			<display:setProperty name="basic.empty.showtable" value="true"/>
			 
		    </display:table>
	 </td>
      </tr>
  
</table>
 	   <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>  
       <input type="hidden" id="ContextAction" value="${fn:escapeXml(ContextAction)}"/>  
       <input type="hidden" id="SourceUid" value="${fn:escapeXml(SourceUid)}"/> 
	<% if (request.getAttribute("RemovedInvestigationsInfoMsg") != null) { %>
			  <div class="infoBox messages" style="text-align: left;">
			  	<%=HTMLEncoder.encodeHtml(String.valueOf(request.getAttribute("RemovedInvestigationsInfoMsg")))%>
			  </div>    
	<% } %>
	
	<div id="fieldFollowupBlock" style="display:none">
		<nedss:container id="sect_processingDecision" name="Investigation Details" classType="sect" displayImg="false" defaultDisplay="collapse">
			<nedss:container id="GA30102" name="Investigation Details" isHidden="F" classType="subSect" >
					<tr>
						<td class="fieldName">
							<span title="Indicate if the relationship being recorded is a direct exposure/relationship between this patient and Contact or an exposure/relationship between the Contact and another known infected patient." id="CON141L" >
							Relationship with Patient/Other infected Patient?:</span></td><td>
							<span id="CON141" />
							<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON141)"
							codeSetNm="CONTACT_REL_WITH"/>
						</td>
					</tr>
					
					<!--processing Participation Question-->
					<tr>
						<td class="fieldName">
							<span title="Search for patients infected with the same condition.">
							Other Infected Patient:</span>
							</td>
							<td>
							<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON142)"/>
							<span id="CON142">${associateToCoinfectionForm.attributeMap.CON142SearchResult}</span>
						</td>
					</tr>
						
					<!--processing Coded Question-->
					<tr>
						<td class="fieldName">
							<span title="The interview at which the contact was named. May also indicate if the contact record was initiated w/out interview" id="CON143L" >
							Named:</span></td><td>
							<span id="CON143" />
							<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON143)"
							codeSetNm="YNU"/>
						</td>
					</tr>
	
					<tr>
						<td class="fieldName">
							<span title="Document the appropriate identifier for the specific type of partner, social contact and/or Associate." id="CON144L" >
							Referral Basis:</span></td><td>
							<span id="CON144" />
							<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON144)"
							codeSetNm="REFERRAL_BASIS"/>
						</td>
					</tr>
	
					<tr>
						<td class="fieldName">
							<span title="The relationship the contact has with the subject of the investigation or other known infected patient." id="CON103L" >
							Relationship:</span></td><td>
							<span id="CON103" />
							<nedss:view name="contactTracingForm" property="cTContactClientVO.answer(CON103)"
							codeSetNm="NBS_RELATIONSHIP"/>
						</td>
					</tr>
					
					<!-- Investigation Start Date -->
					<tr>
						<td class="fieldName">
							<span style="color:#CC0000">*</span>
							<span class="InputFieldLabel" id="INV147L" title="The date the investigation was started or initiated.">
							Investigation Start Date:</span>
							</td>
						<td>
							<html:text  name="associateToCoinfectionForm" property="cTContactClientVO.answer(INV147)"  maxlength="10" size="10" styleId="INV147" onkeyup="DateMask(this)"/>
							<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV147','INV147Icon');return false;" onkeypress ="showCalendarEnterKey('INV147','INV147Icon',event);" styleId="INV147Icon"></html:img>
						</td> 
					</tr>
					
					<tr>
						<td class="fieldName">
							<span id="CONINV180L" title="The Public Health Investigator assigned to the Investigation.">
							Investigator:</span> </td>
						<td>
							<logic:empty name="associateToCoinfectionForm" property="attributeMap.CONINV180Uid">
							<span id="clearCONINV180" class="none">
							</logic:empty>
							<logic:notEmpty name="associateToCoinfectionForm" property="attributeMap.CONINV180Uid">
							<span id="clearCONINV180">
							</logic:notEmpty>
							<input type="button" class="Button" value="Clear/Reassign" id="CONINV180CodeClearButton" onclick="clearInvestigatorForAssociateCoinfection('CONINV180')"/>
							</span>
							<span id="CONINV180SearchControls"
							<logic:notEmpty name="associateToCoinfectionForm" property="attributeMap.CONINV180Uid">
							class="none"
							</logic:notEmpty>
							><input type="button" class="Button" value="Search"
							id="CONINV180Icon" onclick="getInvestigatorForAssociateCoinfection('CONINV180');" />&nbsp; - OR - &nbsp;
							<html:text property="cTContactClientVO.answer(CONINV180)" styleId="CONINV180Text"
							size="10" maxlength="10" onkeydown="genProviderAutocomplete('CONINV180Text','CONINV180_qec_list')"
							title="The Public Health Investigator assigned to the Investigation."/>
							<input type="button" class="Button" value="Quick Code Lookup"
							id="CONINV180CodeLookupButton" onclick="getDWRInvestigatorForAssociateCoinfection('CONINV180')"
							<logic:notEmpty name="associateToCoinfectionForm" property="attributeMap.CONINV180Uid">
							style="visibility:hidden"
							</logic:notEmpty>
							/><div class="page_name_auto_complete" id="CONINV180_qec_list" style="background:#DCDCDC"></div>
							</span>
						</td> 
					</tr>
					
					<tr>
						<td class="fieldName" id="CONINV180S">Investigator Selected: </td>
							<logic:empty name="associateToCoinfectionForm" property="attributeMap.CONINV180Uid">
								<td> 
							<span id="test2">
							</logic:empty>
							<logic:notEmpty name="associateToCoinfectionForm" property="attributeMap.CONINV180Uid">
								<td>
								<span class="test2">
							</logic:notEmpty>
							<html:hidden property="attributeMap.CONINV180Uid"/>
							<span id="CONINV180">${associateToCoinfectionForm.attributeMap.CONINV180SearchResult}</span>
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="text-align:center;">
							<span id="CONINV180Error"/>
						</td>
					</tr>
					
					<!-- Date Investigator Assigned -->
					<tr>
						<td class="fieldName">
							<span style="color:#CC0000">*</span>
							<span class="InputFieldLabel" id="INV110L" title="The date the investigation was assigned/started.">
							Date Assigned to Investigation:</span>
						</td>
						<td>
							<html:text  name="associateToCoinfectionForm" property="cTContactClientVO.answer(INV110)" maxlength="10" size="10" styleId="INV110" onkeyup="DateMask(this)"/>
							<html:img src="calendar.gif" alt="Select a Date" onclick="getCalDate('INV110','INV110Icon');return false;" onkeypress ="showCalendarEnterKey('INV110','INV110Icon',event);" styleId="INV110Icon"></html:img>
						</td>
					</tr>
					
					<!--Internet Follow-up  -->
					<tr>
						<td class="fieldName">
							<span class=" InputFieldLabel" id="NBS142L" title="Initiate for Internet follow-up?">
							Internet Follow-Up:</span>
						</td>
						<td>
							<html:select title = "Internet Follow-Up" name="associateToCoinfectionForm" property="cTContactClientVO.answer(NBS142)" styleId="NBS142">
							<html:optionsCollection property="codedValue(YN)" value="key" label="value" /></html:select>
						</td>
					</tr>
					
					
					<!--Notifiable Question  -->
					<tr>
						<td class="fieldName">
							<span style="color:#CC0000">*</span>
							<span class=" InputFieldLabel" id="NBS143L" title="For field follow-up, is patient notifiable?">
							Notifiable:</span>
						</td>
						<td>
							<html:select title="Notifiable" name="associateToCoinfectionForm" property="cTContactClientVO.answer(NBS143)" styleId="NBS143">
							<html:optionsCollection property="codedValue(NOTIFIABLE)" value="key" label="value" /></html:select>
						</td>
					</tr>
					</nedss:container>
				</nedss:container>
	</div>
	<div id="ContactTracingLoad"></div>       
   <!-- Bottom button bar -->
   	<div class="popupButtonBar">
	    <input type="button" name="Submit" value="Submit" onclick="submitAssociateCoinfections();"/>
	    <input type="button" name="Cancel" value="Cancel" onclick="cancelPopup();" />
    </div>
    </html:form>
 
      </body>
</html>

