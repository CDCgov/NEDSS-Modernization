<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*"%>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
    <head>
		<title>${fn:escapeXml(PageTitle)}</title>
        <%@ include file="/jsp/resources.jsp" %>
       <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JProgramAreaForm.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="openInvestigations.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="PamSpecific.js"></SCRIPT>
		
		<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script language="JavaScript">
		
		blockEnterKey();

		
		/* check box selection */
		function checkAll(ele) {
		     var checkboxes = document.getElementsByTagName('input');
		     if (getCorrectAttribute(ele,  "checked", ele.checked)) {
		         for (var i = 0; i < checkboxes.length; i++) {
		        	if(checkboxes[i].name == "selectCheckBox" && !checkboxes[i].disabled){
		                 checkboxes[i].checked = true;
		        	}
		    	}
		   	} else {
		         for (var i = 0; i < checkboxes.length; i++) {
		             console.log(i)
		           if(checkboxes[i].name == "selectCheckBox"){
		                 checkboxes[i].checked = false;
		             }
		        }
		   	}
		 }

		
			//Added for resolving an issue with IE11
			function enableFiltering(){
				document.getElementById("dropdownsFiltering").style.display="block";
			}
			
		
		//document.onkeypress = function (e) { //commented out unnecessary GST 2016-5-13
		//	if (e.which=='13')
		//		return false;
		//}
		
		 	function onKeyUpValidate()
	 		{      	  
	        	if(getElementByIdOrByName("SearchText1").value != ""){
	         		getElementByIdOrByName("b1SearchText1").disabled=false;
	         		getElementByIdOrByName("b2SearchText1").disabled=false;
	         	   }else if(getElementByIdOrByName("SearchText1").value == ""){
	         		getElementByIdOrByName("b1SearchText1").disabled=true;
	         		getElementByIdOrByName("b2SearchText1").disabled=true;
	         	   }
	        	if(getElementByIdOrByName("SearchText2").value != ""){
	         		getElementByIdOrByName("b1SearchText2").disabled=false;
	         		getElementByIdOrByName("b2SearchText2").disabled=false;
	         	   }else if(getElementByIdOrByName("SearchText2").value == ""){
	         		getElementByIdOrByName("b1SearchText2").disabled=true;
	         		getElementByIdOrByName("b2SearchText2").disabled=true;
	         	   }
	         	   
			}
			function makeMSelects() {
				$j("#sdate").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#inv").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#juris").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#cond").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#stat").multiSelect({actionMode:'${fn:escapeXml(ActionMode)}'});
				$j("#notif").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
				$j("#patient").text({actionMode: '${fn:escapeXml(ActionMode)}'});	
				$j("#investigationid").text({actionMode: '${fn:escapeXml(ActionMode)}'});
				
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
			function createLink(element, url)
			{
				// call the JS function to block the UI while saving is on progress.
				blockUIDuringFormSubmissionNoGraphic();
                document.forms[0].action= url;
                document.forms[0].submit();  
			}
		</script>
		<style type="text/css">

			div#bulkAssign {margin-top:10px; display:none; width:100%; text-align:center;}
			
			div#assignInvestigator {margin-top:10px; display:none; width:100%; text-align:center;}
			
            table.dtTable thead tr th.selectAll {text-align:center;} 
			.boxed {
                 border: 1px solid #5F8DBF ;background:#E4F2FF
            }
		
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
    <body onload="attachIcons();makeMSelects();showCount();displayTooltips();startCountdown();enableFiltering();uncheckHeader();clearProvider('INV207')">
    <div id="blockparent"></div>
   <div id="doc3">
    <html:form action="/LoadMyProgramAreaInvestigations1.do">
	 <tr> <td>                    
 
  <!-- Container Div: To hold top nav bar, button bar, body and footer -->
     
<!-- Body div -->
<div id="bd">
   <!-- Top Nav Bar and top button bar -->
    <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>

           <!-- Page Code Starts here -->
            <div class="printexport" id="printExport" align="right">
				<img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
             <img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
			</div>	
   
 			<div id="assignInvestigator" class="boxed">
				                        <table role="presentation" class="formTable" style=" width:100%; margin:0px;">
				                        <html:hidden name="programAreaForm" property="selectedChkboxIdsInfo" styleId="chkboxIdsInfo"/>
 												<tr>
									            <td colspan="3">
									                <div id="errorBlockP" class="screenOnly infoBox errors" style="display:none; width:99%"> </div>
									            </td>
									        </tr>
									        <tr>
									               <td colspan="3" style="text-align:left;"><span style="color:black; font-weight:bold;"> &nbsp;&nbsp;
									               Please indicate the assigned investigator for the selected investigations.</td>
									        </tr>

									                     
            <!-- Investigator -->              
		            <tr>
		                <td colspan= "2" class="fieldName" style="text-align:right; width:50%">Investigator:
			            </td>	
		                <td colspan="2">
		                    <span id="clearINV207" class="none">        
		                        <input type="button" class="Button" value="Clear/Reassign" 
		                                id="INV207CodeClearButton" onclick="clearProvider('INV207')"/>
		                    </span>   
		                    
		                    <span id="INV207SearchControls">
		                       <input type="button" class="Button" value="Search" 
		                                id="INV207Icon" onclick="getProvider('INV207');" />
		                        &nbsp; - OR - &nbsp;
		                        <html:text property="pamClientVO.answer(INV207)" styleId="INV207Text"
		                                size="10" maxlength="10" onkeydown="genProviderAutocomplete('INV207Text','qec_list')" title="Investigator"/>
		                        <input type="button" class="Button" value="Quick Code Lookup" 
		                            id="INV207CodeLookupButton" onclick="getDWRProvider('INV207')"/>                                
		                        <div class="page_name_auto_complete" id="qec_list" style="background:#DCDCDC"></div>
		                    </span>
		                </td>
		            </tr>
		            <tr>
		                <td colspan="2" class="fieldName">
		                <span style = "color:#990000" >*</span> Investigator Selected:  </td>
		                <td>
		                    <span id="test2">
		                        <html:hidden property="investigatorSelected"/>
		                        <span id="INV207"><c:out value="${programAreaForm.attributeMap.INV207SearchResult}"/></span>
		                    </span>
		                </td>
		            </tr>
		       
		        <!-- Invstigator search error. Not defined in the metadata -->
                <tr>
                    <td colspan="2" style="text-align:center;">
                        <span id="INV207Error"/></td>
                    </td>
                </tr>
		                 
		            <tr>
		               <td class="fieldName" colspan="2">
		                    <span style = "color:#990000" >*</span>
		                    <span id="INV110L" class="InputDisabledLabel" >
		                        Date Assigned to Investigation:
		                    </span> 
		                </td>
						<td>

		                <html:text  
		                            name="programAreaForm" property="dateAssigned" maxlength="10"  title="The date the investigation(s) was assigned/started"  
		                            styleId="INV110" value=""  size="10" onkeyup="DateMaskFuture(this,null,event)"/>
		                    <html:img src="calendar.gif" alt="Select a Date" onclick="getInvestigatorCalDate('INV110','INV110Icon'); return false;" onkeypress ="showInvestigatorCalendarEnterKey('INV110','INV110Icon',event);" 
		                            styleId="INV110Icon"></html:img>

					</td>
		            </tr>
		        
		        
									        <tr>
									            <td colspan="3" style="text-align:right;">
									                <input type="button" value="Submit" name="submitButton"  onclick="return assignInvestigatorSubmit();"/>
									                <input type="button" value="Cancel" name="cancelButton" onclick="javascript:cancelAttachment()"/>
									            </td>
									        </tr>      
									    </table>
									
						
					            </div>
 <table role="presentation" width="100%">
  <tr>
 		<td colspan="3">
					<div id="errorBlockSTD" class="screenOnly infoBox errors" style="display:none; width:99%"> </div>
		</td> 
 </tr>
									             
<tr>
	 <td colspan="3" >
			
			<d:if test="${empty confirmMsg2}">
			<div id="msgBlock" class="screenOnly infoBox success" style="width:99%;display:none">${fn:escapeXml(confirmMsg)}
			<b>${fn:escapeXml(confirmMsg1)}</b>
			${fn:escapeXml(confirmMsg2)}<b>
			${fn:escapeXml(confirmMsg3)}
			</b>
			
			</div>
			</d:if>
			
			<d:if test="${not empty confirmMsg2}">
			<div id="msgBlock" class="screenOnly infoBox success" style="width:99%;">${fn:escapeXml(confirmMsg)}
			<b>${fn:escapeXml(confirmMsg1)}</b>
			${fn:escapeXml(confirmMsg2)}<b>
			${fn:escapeXml(confirmMsg3)}
			</b></div>
			</d:if>
			
			
			
	 </td>
</tr>
 </table>     
          <table role="presentation" width="100%">
             <tr>
             <td align="center">
             <!-- Moved here to resolve an issue with IE 11 -->
             <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
			<div class="removefilter" id="removeFilters">
				<table role="presentation" style="width: 100%;">
						<tr>
							<td style="width: 50%; text-align: left; padding: 0.2em; padding-left:0.2%">

 <a class="reviewedLink" href="#" onclick="assignInvestigator()" onkeypress ="assignInvestigator()"><font class="hyperLink"> Assign </font></a> 

							</td>
							<td style="width: 50%; text-align: right; padding: 0.2em;">
								<a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
							</td>
						</tr>
						</table>
			</div>
			
			
<display:table name="investigationList" class="dtTable" style="margin-top:0em;" pagesize="${programAreaForm.attributeMap.queueSize}"  id="parent" requestURI="/LoadMyProgramAreaInvestigations1.do?method=loadQueue&existing=true" sort="external" export="true" excludedParams="answerArray(STARTDATE) answerArray(INVESTIGATOR) answerArray(JURISDICTION) answerArray(CONDITION) answerArray(CASESTATUS) answerArray(NOTIFICATION) answerArrayText(SearchText1) answerArrayText(SearchText2) method">
					  	<display:setProperty name="export.csv.filename" value="OpenInvestigationsQueue.csv"/>
					  	<display:setProperty name="export.pdf.filename" value="OpenInvestigationsQueue.pdf"/>
 
<%--     				  <logic:match name="programAreaForm" property="attributeMap.assignInvestigator" value="true">
 --%> 							 <display:column  style="width:1.5%;text-align:center" media="html" 
 title="<p style='display:none'>Select/Deselect All</p><input  title='Select/Deselect All' type='checkbox' style='margin-left:15%' name='selectAllValue' onchange='checkAll(this)' />" >
 										<input title="Select/Deselect checkbox" type="checkbox" name="selectCheckBox" />
 	 						</display:column>
						<%-- </logic:match>   --%> 				  	 
						<display:column property="activityFromTime_s" title="Start Date" format="{0,date,MM/dd/yyyy}" sortable="true" sortName="getActivityFromTime" defaultorder="descending" style="width:8%;"/>
						<display:column property="investigatorFullName" title="Investigator" sortable="true" sortName="getInvestigatorFullName" defaultorder="descending" style="width:14%;"/>
						<display:column property="jurisdictionDescTxt" title="Jurisdiction" sortable="true" sortName="getJurisdictionDescTxt" defaultorder="descending" style="width:14%;"/>
						<display:column property="patientFullNameLnk" title="Patient" media="html" sortable="true" sortName="getPatientFullName" defaultorder="descending" style="width:16%;"/>
						<display:column property="patientFullName" title="Patient" media="csv pdf" sortable="true" sortName="getPatientFullName" defaultorder="descending" style="width:16%;"/>
						<display:column property="conditionCodeTextLnk" title="Condition" media="html" sortable="true" sortName="getConditionCodeText" defaultorder="descending" style="width:18%;"/>
						<display:column property="conditionCodeText" title="Condition" media="csv pdf" sortable="true" sortName="getConditionCodeText" defaultorder="descending" style="width:21%;"/>
						<display:column property="caseClassCodeTxt" title="CaseStatus" sortable="true" sortName="getCaseClassCodeTxt" defaultorder="descending" style="width:8%;"/>
						<display:column property="notifStatusTransCd" title="Notification" sortable="true" sortName="getNotifRecordStatusCd" defaultorder="descending" style="width:13%;"/>
						<display:column property="localId" title="Investigation ID" sortable="true" sortName="getLocalId" defaultorder="descending" style="width:13%;"/>						
 
						<display:setProperty name="basic.empty.showtable" value="true"/>
 </display:table>
		      </td>
		     </tr>
		  </table>
				<div style="display: none;visibility: none;" id="errorMessages">
				<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
				<ul>
					<logic:iterate id="errors" name="programAreaForm" property="attributeMap.searchCriteria">
						<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
					</logic:iterate>
				</ul>
			</div> 
            <div class="printexport" id="printExport" align="right">
				<img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
				<img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
			</div>	

			</div>
		   <div id="dropdownsFiltering" style="Display:none"><!-- Added to resolve an issue with IE11 -->
		   	<%@ include file="OpenInvestigationsDropDowns.jsp" %>
		   </div>	
		   <input type='hidden' id='SearchText1' value="${fn:escapeXml(PATIENT)}"/>
		    <input type='hidden' id='SearchText2' value="${fn:escapeXml(INVESTIGATIONID)}"/>
	  </html:form>
      </div>
      <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
</body>
</html>