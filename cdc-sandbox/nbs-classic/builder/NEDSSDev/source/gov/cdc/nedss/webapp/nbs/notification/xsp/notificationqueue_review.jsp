<%@ include file="/jsp/tags.jsp" %>
<%@ page import="gov.cdc.nedss.util.*, gov.cdc.nedss.systemservice.nbssecurity.*,java.util.*"%>
<%@ page import="gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*"%>
<%@ page isELIgnored ="false" %>
<%@ page buffer = "16kb" %>
<html lang="en">
    <head>
        <title>${fn:escapeXml(PageTitle)}</title>
		        <%@ include file="/jsp/resources.jsp" %>
		       <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JInvestigationForm.js"></SCRIPT>
				<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
				<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
				<SCRIPT Language="JavaScript" Src="NotificationSpecific.js"></SCRIPT>
				<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
		<script language="JavaScript">

		blockEnterKey();
		
		//Added for resolving an issue with IE11
		function enableFiltering(){
			document.getElementById("dropdownsFiltering").style.display="block";
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
			function approveNotifPopUp(url){
				getElementByIdOrByName("urlStore").value=url;
				 var divElt =getElementByIdOrByName("blockparent");
				    divElt.style.display = "block";
				    var o = new Object();
				    o.opener = self;
				    
				    var URL = "/nbs/notification/xsp/approveNotification.jsp?" + url;

				    //window.showModalDialog("/nbs/notification/xsp/approveNotification.jsp?" + url, o, GetDialogFeatures(560, 300, false));
				    var modWin = openWindow(URL, o, GetDialogFeatures(610, 320, false, false), divElt, "");

					}
			function rejectNotifPopUp(url){
				getElementByIdOrByName("urlStore").value=url;
			 var divElt =getElementByIdOrByName("blockparent");
				divElt.style.display = "block";
				var o = new Object();
				o.opener = self;
				var URL = "/nbs/notification/xsp/rejectNotification.jsp?" + url;
				//window.showModalDialog("/nbs/notification/xsp/rejectNotification.jsp?" + url, o, GetDialogFeatures(560, 300, false));
				var modWin = openWindow(URL, o, GetDialogFeatures(560, 300, false, false), divElt, "");
				}

		</script>
		<style type="text/css">
		.removefilter{
			background-color:#003470; width:100%; height:25px;
			line-height:25px;float:right;text-align:right;
			margin-top: 1%;<!--Added for resolving IE 11 issue-->
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
      <body onload="attachIcons();showCount();makeMSelects();displayTooltips();startCountdown();enableFiltering();">
      	<div id="blockparent"></div>
      		<div id="doc3">


		<html:form action="/LoadReviewNotifications1.do" >
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
			<%
		      String apprRejMsg = request.getAttribute("confirmationMessage") == null ? "" : (String) request.getAttribute("confirmationMessage");
		      if( apprRejMsg!= "") { %>
				<div class="infoBox success">
					 <%= apprRejMsg %>
				</div>

			  <%}%>

			<table role="presentation" width="100%">
			<tr> <td align="center">
			<div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>	
			<div class="removefilter" id="removeFilters">
			<a class="removefilerLink" href="javascript:clearFilter()"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
			</div>
			
			    <display:table name="notificationList" class="dtTable" style="margin-top:0em;" pagesize="${investigationForm.attributeMap.queueSize}"   id="parent" requestURI="/LoadReviewNotifications1.do?method=loadQueue&existing=true" sort="external" export="true" excludedParams="answerArray(SUBMITDATE) answerArray(SUBMITTEDBY)  answerArray(CONDITION) answerArray(STATUS) answerArray(TYPE) answerArray(RECIPIENT) answerArrayText(SearchText1) answerArrayText(SearchText2) method">


			    <display:column title="<p style='display:none'>Approve</p>" style="width:2%; text-align:center;" media="html">
            	&nbsp;&nbsp;&nbsp;<img tabIndex="0" src="page_white_get.gif"  class="cursorHand" title="Approve" alt="Approve"  align="center"  cellspacing="2" cellpadding="3" border="55" onclick="javascript:approveNotifPopUp(
				'<%= request.getAttribute("ApproveButton")%>?ContextAction=<%=request.getAttribute("ContextActionApprove")%>&amp;publicHealthCaseUID=<%=((NotificationSummaryVO)parent).getPublicHealthCaseUid()%>&amp;publicHealthCaseLocalID=<%=((NotificationSummaryVO)parent).getPublicHealthCaseLocalId()%>&amp;notificationCd=<%=((NotificationSummaryVO)parent).getNotificationCd()%>&amp;notificationIndex=<%=((NotificationSummaryVO)parent).getNotificationUid()%>&amp;comments=<%=((NotificationSummaryVO)parent).getCodeConverterCommentTemp()%>&amp;jurisdiction=<%=((NotificationSummaryVO)parent).getJurisdictionCdTxt()%>&amp;notifRecip=<%=((NotificationSummaryVO)parent).getCodeConverterTemp()%>&amp;nndAssociated=<%=((NotificationSummaryVO)parent).isNndAssociated()%>&amp;shareAssocaited=<%=((NotificationSummaryVO)parent).isShareAssocaited()%>')"
				onkeypress="javascript:approveNotifPopUp(
				'<%= request.getAttribute("ApproveButton")%>?ContextAction=<%=request.getAttribute("ContextActionApprove")%>&amp;publicHealthCaseUID=<%=((NotificationSummaryVO)parent).getPublicHealthCaseUid()%>&amp;publicHealthCaseLocalID=<%=((NotificationSummaryVO)parent).getPublicHealthCaseLocalId()%>&amp;notificationCd=<%=((NotificationSummaryVO)parent).getNotificationCd()%>&amp;notificationIndex=<%=((NotificationSummaryVO)parent).getNotificationUid()%>&amp;comments=<%=((NotificationSummaryVO)parent).getCodeConverterCommentTemp()%>&amp;jurisdiction=<%=((NotificationSummaryVO)parent).getJurisdictionCdTxt()%>&amp;notifRecip=<%=((NotificationSummaryVO)parent).getCodeConverterTemp()%>&amp;nndAssociated=<%=((NotificationSummaryVO)parent).isNndAssociated()%>&amp;shareAssocaited=<%=((NotificationSummaryVO)parent).isShareAssocaited()%>')" >
				</display:column>
				<display:column title="<p style='display:none'>Reject</p>" style="width:2%;text-align:center;"  media="html">
				 &nbsp;&nbsp;&nbsp;&nbsp;<img tabIndex="0" src="page_white_delete.gif"  class="cursorHand" title="Reject" alt ="Reject"  onclick="javascript:rejectNotifPopUp('<%= request.getAttribute("RejectButton")%>?ContextAction=<%=request.getAttribute("ContextActionReject")%>&amp;publicHealthCaseUID=<%=((NotificationSummaryVO)parent).getPublicHealthCaseUid()%>&amp;publicHealthCaseLocalID=<%=((NotificationSummaryVO)parent).getPublicHealthCaseLocalId()%>&amp;notificationCd=<%=((NotificationSummaryVO)parent).getNotificationCd()%>&amp;notificationIndex=<%=((NotificationSummaryVO)parent).getNotificationUid()%>&amp;comments=<%=((NotificationSummaryVO)parent).getCodeConverterCommentTemp()%>')" 
				  onkeypress="javascript:rejectNotifPopUp('<%= request.getAttribute("RejectButton")%>?ContextAction=<%=request.getAttribute("ContextActionReject")%>&amp;publicHealthCaseUID=<%=((NotificationSummaryVO)parent).getPublicHealthCaseUid()%>&amp;publicHealthCaseLocalID=<%=((NotificationSummaryVO)parent).getPublicHealthCaseLocalId()%>&amp;notificationCd=<%=((NotificationSummaryVO)parent).getNotificationCd()%>&amp;notificationIndex=<%=((NotificationSummaryVO)parent).getNotificationUid()%>&amp;comments=<%=((NotificationSummaryVO)parent).getCodeConverterCommentTemp()%>')">
				 </display:column>
					 <display:setProperty name="export.csv.filename" value="InitialNotificationRequiringApprovalQueue.csv"/>
					  	<display:setProperty name="export.pdf.filename" value="InitialNotificationRequiringApprovalQueue.pdf"/>
	               <display:column property="addTime" defaultorder="ascending" sortable="true" sortName= "getAddTime" title="Submit Date" format="{0,date,MM/dd/yyyy}" style="width:11%;"/>
		          <display:column property="addUserName" defaultorder="ascending" sortable="true" sortName= "getAddUserName" title="Submitted By"  style="width:12%;"/>
		          <display:column property="recipient" defaultorder="ascending" sortable="true" sortName= "getRecipient" title="Recipient"  style="width:10%;"/>
		          <display:column property="notificationSrtDescCd" defaultorder="ascending" sortable="true"  sortName= "getNotificationSrtDescCd"  title="Type" style="width:10%;"/>
		          <display:column property="patientFullNameLnk" defaultorder="ascending" sortable="true"  media="html" sortName= "getPatientFullName"  title="Patient" style="width:10%;"/>
		          <display:column property="patientFullName" title="Patient" media="csv pdf" sortable="true" sortName="getPatientFullName" defaultorder="descending" style="width:22%;"/>
		          <display:column property="conditionCodeTextLnk" defaultorder="ascending" sortable="true"  sortName= "getCdTxt" media="html" title="Condition" style="width:22%;"/>
		          <display:column property="cdTxt" title="Condition" media="csv pdf" sortable="true" sortName="getCdTxt" defaultorder="descending" style="width:22%;"/>
		          <display:column property="caseClassCdTxt" defaultorder="ascending" sortable="true"  sortName= "getCaseClassCdTxt"  title="Status" style="width:8%;"/>
		          <display:column property="txt" defaultorder="ascending" sortable="true"  sortName= "getTxt"  title="Comments" maxLength="200" style="width:20%;"/>
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
			<div id="dropdownsFiltering" style="Display:none"><!-- Added to resolve an issue with IE11 -->
				<%@ include file="dropDowns_Initial_Notification.jsp" %>
			</div>
			<input type='hidden' id='SearchText1' value="${fn:escapeXml(PATIENT)}"/>
            <input type='hidden' id='SearchText2' value="${fn:escapeXml(COMMENT)}"/>
            </html:form>
		     </div>
		     <input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
		     <input type="hidden" id="urlStore"/>
   		     <input type="hidden" id="apprRejComments"/>
   </body>
</html>
