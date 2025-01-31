<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*,java.util.*"%>
<%@ page import="gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>
<html lang="en">
    <head>
        <title>${fn:escapeXml(PageTitle)}</title>
		        <%@ include file="/jsp/resources.jsp" %>
		       <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JInvestigationForm.js"></SCRIPT>
				<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
				<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
				<SCRIPT Language="JavaScript" Src="RejectedNotifQueue.js"></SCRIPT>
				<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script language="JavaScript">
		
		blockEnterKey();
        
		<!-- IE 11 issue-->
		function removeMargin(){
			document.getElementById("removeFilters").style.marginTop="0px";
		}
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
						$j("#cond").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
						$j("#stat").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
						$j("#notif").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
						$j("#recipient").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
						$j("#rejBy").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
						$j("#patient").text({actionMode: '${fn:escapeXml(ActionMode)}'});
						$j("#comment").text({actionMode: '${fn:escapeXml(ActionMode)}'});
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


		</script>
		<style type="text/css">
		.removefilter{
			background-color:#003470; width:100%; height:25px;
			line-height:25px;float:right;text-align:right;
			margin-top:1%;
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

    </font>
      <body onload="attachIcons();showCount();makeMSelects();displayTooltips();startCountdown();removeMargin();">
      	<div id="blockparent"></div>
      		<div id="doc3">



      		<html:form action="/LoadReviewRejectedNotifications1.do" >
      		<tr> <td>
            <!-- Body div -->
                <div id="bd">
                    <!-- Top Nav Bar and top button bar -->
                     <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>
                     <!-- Container Div -->
           <!-- Page Code Starts here -->
			<div class="printexport" id="printExport" align="right">
				<img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
				<img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
			</div>

			<table role="presentation" width="100%">
			<tr> <td align="center">
			<div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
			<div class="removefilter" id="removeFilters">
			<a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
			</div>
			    <display:table name="rejectedNotificationList" class="dtTable" style="margin-top:0em;" pagesize="${investigationForm.attributeMap.queueSize}"   id="parent" requestURI="/LoadReviewRejectedNotifications1.do?method=loadQueue&existing=true" sort="external" export="true" excludedParams="answerArray(SUBMITDATE) answerArray(SUBMITTEDBY)  answerArray(CONDITION) answerArray(STATUS) answerArray(TYPE) answerArray(RECIPIENT) answerArray(REJECTEDBY) answerArrayText(SearchText1) answerArrayText(SearchText2) method">
			    	<display:setProperty name="export.csv.filename" value="RejectedNotificationsQueue.csv"/>
			    	<display:setProperty name="export.pdf.filename" value="RejectedNotificationsQueue.pdf"/>
	               <display:column property="addTime" defaultorder="ascending" sortable="true" sortName= "getAddTime" title="Submit Date" format="{0,date,MM/dd/yyyy}" style="width:12%;"/>
		          <display:column property="addUserName" defaultorder="ascending" sortable="true" sortName= "getAddUserName" title="Submitted By"  style="width:12%;"/>
		          <display:column property="recipient" defaultorder="ascending" sortable="true" sortName= "getRecipient" title="Recipient"  style="width:10%;"/>
		          <display:column property="notificationCd" defaultorder="ascending" sortable="true"  sortName= "getNotificationCd"  title="Type" style="width:8%;"/>
		          <display:column property="patientFullNameLnk" defaultorder="ascending" sortable="true"  media="html" sortName= "getPatientFullName"  title="Patient" style="width:10%;"/>
		          <display:column property="patientFullName" title="Patient" media="csv pdf" sortable="true" sortName="getPatientFullName" defaultorder="descending" style="width:20%;"/>
		          <display:column property="conditionCodeTextLnk" defaultorder="ascending" sortable="true"  sortName= "getCondition" media="html" title="Condition" style="width:15%;"/>
		          <display:column property="condition" title="Condition" media="csv pdf" sortable="true" sortName="getCondition" defaultorder="descending" style="width:18%;"/>
		          <display:column property="caseStatus" defaultorder="ascending" sortable="true"  sortName= "getCaseStatus"  title="Status" style="width:9%;"/>
		          <display:column property="rejectedByUserName" defaultorder="ascending" sortable="true"  sortName= "getRejectedByUserName"  title="Rejected By" style="width:12%;"/>
				  <display:column property="cdTxt" defaultorder="ascending" sortable="true"  sortName= "getCdTxt"  title="Comments" maxLength="200" style="width:20%;"/>
		          <display:setProperty name="basic.empty.showtable" value="true"/>
		       </display:table>
		       </td>
		     </tr>
		     </table>
		     <div style="display: none;visibility: none;" id="errorMessages">
				<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
				<ul>
					<logic:iterate id="errors" name="investigationForm" property="attributeMap.searchCriteria">
						<li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
					</logic:iterate>
				</ul>
			</div>
		     <div class="printexport" id="printExport" align="right">
				<img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
				<img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
			</div>

			</div>
			</div>
			
			<!-- Include the filter dropdowns for this display tag table. This is hidden when the page
                loads in order to avoid the page flickering. Once the page finishes loading, the attachIcons() method
                will be used to remove the hidden property of this filter dropdowns. --> 
                <div id="filterDropDowns" style="display:none;">
                    <%@ include file="dropDowns_Rejected_Notification.jsp" %>
                    <input type='hidden' id='SearchText1' value="${fn:escapeXml(PATIENT)}"/>
            		<input type='hidden' id='SearchText2' value="${fn:escapeXml(COMMENT)}"/>
                </div>
		     </html:form>
		     </div>
		     <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
	   </body>
</html>
