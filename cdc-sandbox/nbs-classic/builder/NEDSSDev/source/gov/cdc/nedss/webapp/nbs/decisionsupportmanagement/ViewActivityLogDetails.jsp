<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<html lang="en">
<head>
<title>NBS: Manage Activity Logs: View Activity Details</title>
<base target="_self">
<%@ include file="/jsp/resources.jsp"%>

<SCRIPT Language="JavaScript"
	Src="/nbs/dwr/interface/JDsmActivityLogForm.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css" />

<script type="text/javaScript">
	var isFormSubmission = false;
	function closePopup() {

		self.close();
		var opener = getDialogArgument();
		var invest = null;
		invest = getElementByIdOrByNameNode("pamview", opener.document)
		if (invest == null) {
			invest = getElementByIdOrByNameNode("blockparent", opener.document);
		}

		invest.style.display = "none";

		return true;

	}

	function retActLog() {
		document.manageImportExportLogForm.action = "/nbs//LoadDSMActivityLog.do?method=searchActivityLogSubmit&actionMode=Manage&context=ReturnToManage"
		document.manageImportExportLogForm.submit();
	}

	function printQueue() {
		window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#"
				: $j(".exportlinks a:last").attr("href");
	}
	function exportQueue() {
		window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#"
				: $j(".exportlinks a:first").attr("href");
	}
	 function attachIcons() {
	           $j("#parent").before($j("#whitebar"));
	           $j("#parent").before($j("#removeFilters"));
	       }
	 function showCount() {
			$j(".pagebanner b").each(function(i) {
				var mythis = $j(this);
				var myvalue = $j("#queueCnt").attr("value");
				$j(this).append(" of ").append($j("#queueCnt").attr("value"));
			});
			$j(".singlepagebanner b").each(
					function(i) {
						var cnt = $j("#queueCnt").attr("value");
						if (cnt > 0)
							$j(this).append(" Results 1 to ").append(cnt).append(
									" of ").append(cnt);
					});
		}
</script>
<style type="text/css">
div.messages {
	background: #E4F2FF;
	color: #000;
	padding: 0.5em;
	border-width: 1px 1px 1px 1px;
	border-color: #7AA6D5;
	border-style: solid;
	font-size: 95%;
}

div.popupButtonBar {
	text-align: right;
	width: 100%;
	background: #EEE;
	border-bottom: 1px solid #DDD;
}

.removefilter {
	background-color: #003470;
	width: 100%;
	height: 25px;
	line-height: 25px;
	float: right;
	text-align: right;
}

removefilerLink {
	vertical-align: bottom;
}

.hyperLink {
	font-size: 10pt;
	font-family: Geneva, Arial, Helvetica, sans-serif;
	color: #FFFFFF;
	text-decoration: none;
}
</style>
</head>

<body onload="attachIcons();showCount();startCountdown();">
	<div id="blockparent"></div>
	<html:form action="/ManageImpExpLog.do">
		<div id="doc3">
			<tr>
				<td>
					<!-- Body div -->
					<div id="bd">
						<!-- Top Nav Bar and top button bar -->
						<%@ include file="../../jsp/topNavFullScreenWidth.jsp"%>

						<table role="presentation" style="width: 100%;">
							<tr>
								<td style="text-align: right" id="srtLink"><a href="#"
									onclick="return retActLog();" class="backToTopLink"> Return
										to Activity Log </a> <input type="hidden" id="actionMode"
									value="${dsmActivityLogForm.actionMode}" /></td>
							</tr>

						</table>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<!-- Name, Sex, DOB, Patient Id -->
						<logic:equal name="dsmActivityLogForm" property="docType" value="PHC236">
						<div>
							<table role="presentation" class="style">
								<tr class="cellColor">
									<td class="border1"><span class="label">Algorithm
											Name: </span> <span class="value">
											${dsmActivityLogForm.attributeMap.algorithmName} </span></td>
									<td class="border1"><span class="label"> Processed
											Time: </span> <span class="value">
											${dsmActivityLogForm.attributeMap.processedTime} </span></td>
								</tr>
								<tr class="cellColor">
									<td class="border1"><span class="label"> Action: </span> <span
										class="value">
											${dsmActivityLogForm.attributeMap.action} </span></td>
									<td class="border1"><span class="label"> Event ID:
									</span> <span class="value">
											${dsmActivityLogForm.attributeMap.eventId}</span></td>
								</tr>
							</table>
						</div>
						</logic:equal>
						<logic:equal name="dsmActivityLogForm" property="docType" value="11648804">
						<div>
							<table role="presentation" class="style">
								<tr class="cellColor">
									
									<td class="border1"><span class="label"> Processed
											Time: </span> <span class="value">
											${dsmActivityLogForm.attributeMap.processedTime} </span></td>
									<td class="border1"><span class="label">Message
											ID: </span> <span class="value">
											${dsmActivityLogForm.attributeMap.messageId} </span></td>
								</tr>
								<tr class="cellColor">
								<td class="border1"><span class="label">Algorithm
											Name: </span> <span class="value">
											${dsmActivityLogForm.attributeMap.algorithmName} </span></td>
															
									<td class="border1"><span class="label"> Observation ID:
									</span> <span class="value">
											${dsmActivityLogForm.attributeMap.businessObjLocalId}</span></td>
								</tr>
								<tr class="cellColor">
								<td class="border1"><span class="label">Action:	</span> <span class="value">
											${dsmActivityLogForm.attributeMap.action} </span></td>
															
									<td class="border1"><span class="label">Reporting Facility:
									</span> <span class="value">
											${dsmActivityLogForm.attributeMap.srcName}</span></td>
								</tr>
								<tr class="cellColor">
								<td class="border1"><span class="label">Patient Name:</span> <span class="value">
											${dsmActivityLogForm.attributeMap.entityNm} </span></td>
															
									<td class="border1"><span class="label">Accession #:
									</span> <span class="value">
											${dsmActivityLogForm.attributeMap.accessionNbr}</span></td>
								</tr>
							</table>
						</div>
						</logic:equal>
						

						<!-- top bar -->
						<tr>
							<td>&nbsp;</td>
						</tr>
						<div class="grayButtonBar">
							<table role="presentation" width="100%">
								<tr>
									<td align="right"><input type="button" value="Print"
										id=" " onclick="printQueue();" /> <input type="button"
										value="Download" id=" " onclick="exportQueue();" /></td>
								</tr>
							</table>
						</div>
						<!-- Container Div -->
						
						<nedss:container id="section1" name="Activity Details" classType="sect"
					displayImg="false" displayLink="false" includeBackToTopLink="false">
					<fieldset style="border-width: 0px;" id="result">
						<input type="hidden" id="queueCnt" value="${dsmActivityLogForm.attributeMap.queueCount}">
						<div id="whitebar"
							style="background-color: #FFFFFF; width: 100%; height: 1px;"
							align="right"></div>
						<div class="removefilter" id="removeFilters">
						</div>
						<table role="presentation" width="98%">
							<tr>
								<td align="center"><display:table name="activityDetailList"
										class="dtTable" style="margin-top:0em;" id="parent"
										pagesize="${dsmActivityLogForm.attributeMap.queueSize}"
										requestURI="/LoadDSMActivityLog.do?method=viewActivityLogDetails&existing=true"
										sort="external" export="true"
										excludedParams="answerArray(LOGTYPE) answerArray(COMMENT) sort method">
										<display:setProperty name="export.csv.filename"
											value="ActivityLogDetails.csv" />
										<display:setProperty name="export.pdf.filename"
											value="ActivityLogDetails.pdf" />
										<display:column property="logTypeHtml" title="Success/Failure"
											media="html" style="width:20%;" />
										<display:column property="logType" title="Success/Failure" media="csv pdf" 
											style="width:20%;" />
										<display:column property="commentHtml" title="Description"
											media="html" style="width:80%;" />
										<display:column property="comment" title="Description" media="csv pdf" 
											style="width:80%;" />
										<display:setProperty name="basic.empty.showtable"
											value="true" />
									</display:table></td>
							</tr>
						</table>
					</fieldset>
				</nedss:container>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<div class="grayButtonBar">
				<table role="presentation" width="100%">
					<tr>
						<td align="left">
							<!-- <input type="button" name="View Import/Export Activity Log" style="width: 190px" value="View Import/Export Activity Log" onclick="viewPageHistoryPopUp()"/>
							   	--> &nbsp;
						</td>
						<td align="right"><input type="button" value="Print" id=" "
							onclick="printQueue();" /> <input type="button" value="Download"
							id=" " onclick="exportQueue();" /></td>
					</tr>
				</table>
			</div>
		</div>
	</html:form>
</body>
</html>