<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page
	import="gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.*"%>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>

<html lang="en">
<head>
<title>${fn:escapeXml(PageTitle)}</title>
<%@ include file="/jsp/resources.jsp"%>
<SCRIPT Language="JavaScript"
	Src="/nbs/dwr/interface/JReceivingFacilityForm.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jquery.dimensions.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT>
<SCRIPT Language="JavaScript" Src="ReceivingFacilitySpecific.js"></SCRIPT>
<link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
		function makeMSelects() {
			$j("#fApplicationName").text({actionMode: '${fn:escapeXml(ActionMode)}'});
	        $j("#fDisplayName").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#owner").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#sen").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#reci").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#trans").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#reportType").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});

		}
           function addNewSystem()
            {
                //document.forms[0].action ="/nbs/ReceivingSystem.do?method=createLoadRecFacility&exportReceivingFacilityUid"+"#"+"expAlg";
                window.location.href="/nbs/ReceivingSystem.do?method=createLoadRecFacility&exportReceivingFacilityUid"+"#"+"expAlg";
            }
           function viewRecFacSys(exportReceivingFacilityUid)
           {
				document.forms[0].action ="/nbs/ReceivingSystem.do?method=viewRecFacility&"+exportReceivingFacilityUid+"#"+"expAlg";
				document.forms[0].submit();
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
			function editRecFacSys(exportReceivingFacilityUid){
				document.forms[0].action ="/nbs/ReceivingSystem.do?method=editRecFacility&"+exportReceivingFacilityUid+"#"+"expAlg";
				document.forms[0].submit();
           }
			
			window.onload = function() {
				attachIcons();
				makeMSelects();
				showCount();
				displayTooltips();
				startCountdown();
				recFecOnLoad();
				//getActionDropdown();
			
				
				// called during the second phase of the page laod
				<logic:present name="accessMode">
					var accessMode = "<bean:write name="accessMode" />";
					if (accessMode == "createSystem") {
						disableTransfer();
						disableRecSys();
						disablejurDerive();
					//	getActionDropdown();
						
					}
					
				</logic:present>
			}

			
        </script>
<style type="text/css">
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
<body>
	<div id="blockparent"></div>
	<div id="doc3">
		<%
				  String apprRejMsg = request.getAttribute("confirmationMessage") == null ? "" : HTMLEncoder.encodeHtml((String) request.getAttribute("confirmationMessage"));
				  if( apprRejMsg!= "") { %>
		<div class="infoBox success">
			<%= apprRejMsg %>
		</div>

		<%}%>
		<html:form action="/ReceivingSystem.do">
			<tr>
				<td>
					<!-- Body div -->
					<div id="bd">
						<!-- Top Nav Bar and top button bar -->
						<%@ include file="/jsp/topNavFullScreenWidth.jsp"%>
						<%@ include file="/importandexport/impAndExp-topbar.jsp"%>
						<!-- Container Div -->
						<!-- Page Code Starts here -->
						<html:select property="answerArray(SearchText1)" styleId = "fApplicationName" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
						    <html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
						</html:select>
						<html:select property="answerArray(SearchText2)" styleId = "fDisplayName" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
						    <html:optionsCollection property="noDataArray" value="key" label="value" style="width:180"/>
						</html:select>
						<html:select property="answerArray(Owner)" styleId="owner"
							onchange="selectfilterCriteria()" multiple="true" size="4"
							style="width:180">
							<html:optionsCollection property="owner" value="key"
								label="value" style="width:180" />
						</html:select>
						<html:select property="answerArray(Sender)" styleId="sen"
							onchange="selectfilterCriteria()" multiple="true" size="4"
							style="width:180">
							<html:optionsCollection property="sender" value="key"
								label="value" />
						</html:select>
						<html:select property="answerArray(Recipient)" styleId="reci"
							onchange="selectfilterCriteria()" multiple="true" size="4"
							style="width:180">
							<html:optionsCollection property="receipient" value="key"
								label="value" />
						</html:select>
						<html:select property="answerArray(Transfer)" styleId="trans"
							onchange="selectfilterCriteria()" multiple="true" size="4"
							style="width:180">
							<html:optionsCollection property="transfer" value="key"
								label="value" />
						</html:select>
						<html:select property="answerArray(ReportType)"
							styleId="reportType" onchange="selectfilterCriteria()"
							multiple="true" size="4" style="width:180">
							<html:optionsCollection property="reportType" value="key"
								label="value" />
						</html:select>

						<nedss:container id="section2"
							name="Manage Sending and Receiving Systems" classType="sect"
							displayImg="false" displayLink="false"
							includeBackToTopLink="false">
							<fieldset style="border-width: 0px;" id="result">
								<html:hidden styleId="queueCnt"
									property="attributeMap.queueCount" />
								<table role="presentation" width="98%">
									<tr>
										<td align="center"><display:table name="manageList"
												class="dtTable" style="margin-top:0em;" id="parent"
												pagesize="${receivingFacilityForm.attributeMap.queueSize}"
												requestURI="/ReceivingSystem.do?method=manageLoad&existing=true"
												sort="external" export="true"
												excludedParams="answerArray(Owner) answerArray(Sender) answerArray(Recipient) answerArray(Transfer) answerArrayText(SearchText1) answerArrayText(SearchText2)  method">
												<display:column title="<p style='display:none'>View</p>" style="width:5%;" media="html">
                                        	&nbsp;&nbsp;&nbsp;<img tabindex="0"
														src="page_white_text.gif" tabIndex="0" class="cursorHand" title="View" alt="View"
														align="middle" cellspacing="2" cellpadding="3" border="55"
														onkeypress="viewRecFacSys('exportReceivingFacilityUid=<%=((ExportReceivingFacilityDT)parent).getExportReceivingFacilityUid()%>');"
														onclick="viewRecFacSys('exportReceivingFacilityUid=<%=((ExportReceivingFacilityDT)parent).getExportReceivingFacilityUid()%>');">
												</display:column>
												<display:column title="<p style='display:none'>Edit</p>" style="width:5%;" media="html">
                            				 &nbsp;&nbsp;&nbsp;<img tabindex="0"
														src="page_white_edit.gif" tabIndex="0" class="cursorHand" title="Edit" alt="Edit"
														onkeypress="editRecFacSys('exportReceivingFacilityUid=<%=((ExportReceivingFacilityDT)parent).getExportReceivingFacilityUid()%>');"
														onclick="editRecFacSys('exportReceivingFacilityUid=<%=((ExportReceivingFacilityDT)parent).getExportReceivingFacilityUid()%>');">
												</display:column>

												<display:setProperty name="export.csv.filename"
													value="OpenInvestigationsQueue.csv" />
												<display:setProperty name="export.pdf.filename"
													value="OpenInvestigationsQueue.pdf" />

												<display:column property="receivingSystemNm"
													title="Application Name" sortable="true"
													sortName="getReceivingSystemNm" defaultorder="descending"
													style="width:15%;" />
												<display:column property="receivingSystemShortName"
													title="Display Name" sortable="true"
													sortName="getReceivingSystemShortName"
													defaultorder="descending" style="width:20%;" />
												<display:column property="receivingSystemOwner"
													title="Facility Name" sortable="true"
													sortName="getReceivingSystemOwner"
													defaultorder="descending" style="width:20%;" />
												<display:column property="reportTypeDescTxt"
													title="Report Type" sortable="true"
													sortName="getReportType" defaultorder="descending"
													style="width:12%;" />
												<display:column property="sendingIndDescTxt" title="Sender"
													sortable="true" sortName="getSendingIndCd"
													defaultorder="descending" style="width:8%;" />
												<display:column property="receivingIndDescTxt"
													title="Recipient" sortable="true"
													sortName="getReceivingIndCd" defaultorder="descending"
													style="width:8%;" />
												<display:column property="allowTransferIndDescTxt"
													title="Transfer" sortable="true"
													sortName="getAllowTransferIndCd" defaultorder="descending"
													style="width:14%;" />
												<display:setProperty name="basic.empty.showtable"
													value="true" />
											</display:table></td>
									</tr>
									<tr>
										<td align="right" class="InputButton"><input
											type="button" name="submitCr" id="submitCr"
											value="Add New System" onclick="addNewSystem();" /></td>
									</tr>
								</table>

							</fieldset>
						</nedss:container>

						<div id="whitebar"
							style="background-color: #FFFFFF; width: 100%; height: 1px;"
							align="right"></div>
						<div class="removefilter" id="removeFilters">
							<a class="removefilerLink" href="javascript:clearFilter()"><font
								class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font>
							</a>
						</div>
					</div> <logic:equal name="receivingFacilityForm" property="actionMode"
						value="View">
						<%@ include file="receivingSystems.jsp"%>
					</logic:equal> <logic:equal name="receivingFacilityForm" property="actionMode"
						value="Edit">
						<%@ include file="receivingSystems.jsp"%>
					</logic:equal> <logic:equal name="receivingFacilityForm" property="actionMode"
						value="Create">
						<%@ include file="receivingSystems.jsp"%>
					</logic:equal>
	</div>
	</div>
	</div>
	
	<input type='hidden' id='SearchText1' value="${fn:escapeXml(FILTERBYAPPLICATIONNAME)}"/>
    <input type='hidden' id='SearchText2' value="${fn:escapeXml(FILTERBYDISPLAYNAME)}"/>
		  
		  
	</html:form>
	</div>
	<%@ include file="/jsp/footer.jsp"%>
</body>
</html>