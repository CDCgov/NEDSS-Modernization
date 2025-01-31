<%@page import="gov.cdc.nedss.report.dt.ReportFilterDT"%>
<%@page import="gov.cdc.nedss.report.dt.ReportDT"%>
<%@page import="gov.cdc.nedss.report.vo.ReportVO"%>
<%@page import="gov.cdc.nedss.report.util.ReportConstantUtil"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored="false"%>
<%@ page
	import="gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<html lang="en">
<head>
<title>NBS: Manage Questions</title>
<base target="_self">
<%@ include file="../../../jsp/resources.jsp"%>
<script language="JavaScript">
	function submitForm() {
		//var atLeastOneReportCriteria = false;
		var errorTD = getElementByIdOrByName("error1");
		errorTD.setAttribute("className", "none");
		var errorText = "";
		var isError = false;
		var errorMsgArray = new Array();
		var errorMsgArray;
		if (getElementByIdOrByName("textTypeFilter")!=null && (getElementByIdOrByName("textTypeFilter") == null
				|| isEmpty(getElementByIdOrByName("textTypeFilter").value))) {
			//atLeastOneReportCriteria = true;
			errorText = getElementByIdOrByName("textTypeFilterLabel").innerText + " is required to run report";
			
			if(getElementByIdOrByName("textTypeFilterLabel").innerText==undefined)
				errorText = getElementByIdOrByName("textTypeFilterLabel").textContent + " is required to run report";
			
			isError = true;
			errorMsgArray.push(errorText);
			getElementByIdOrByName("textTypeFilterLabel").style.color = "#CC0000";
		}else if(getElementByIdOrByName("textTypeFilterLabel")!=null){
			getElementByIdOrByName("textTypeFilterLabel").style.color = "#000";
		}
		
		if (getElementByIdOrByName("timeFrom")!=null && getElementByIdOrByName("timeTo")!=null && (getElementByIdOrByName("timeFrom") == null
				|| isEmpty(getElementByIdOrByName("timeFrom").value) || getElementByIdOrByName("timeTo") == null
				|| isEmpty(getElementByIdOrByName("timeTo").value))) {
			//atLeastOneReportCriteria = true;
			errorText = "Both From Date and To Date are required to run report";
			isError = true;
			errorMsgArray.push(errorText);
			getElementByIdOrByName("dateTypeFilterLabel").style.color = "#CC0000";
		}else if(getElementByIdOrByName("dateTypeFilterLabel")!=null){
			getElementByIdOrByName("dateTypeFilterLabel").style.color = "#000";
		}
		
		if (getElementByIdOrByName("codedTypeFilter2")!=null && (getElementByIdOrByName("codedTypeFilter2") == null
				|| isEmpty(getElementByIdOrByName("codedTypeFilter2").value))) {
			//atLeastOneReportCriteria = true;
			errorText = getElementByIdOrByName("codedTypeFilterLabel2").innerText + " is required to run report";
			if(getElementByIdOrByName("codedTypeFilterLabel2").innerText==undefined)
				errorText = getElementByIdOrByName("codedTypeFilterLabel2").textContent + " is required to run report";
			
			isError = true;
			errorMsgArray.push(errorText);
			getElementByIdOrByName("codedTypeFilterLabel2").style.color = "#CC0000";
		}else if(getElementByIdOrByName("codedTypeFilterLabel2")!=null){
			getElementByIdOrByName("codedTypeFilterLabel2").style.color = "#000";
		}
		
		if (getElementByIdOrByName("codedTypeFilter")!=null && (getElementByIdOrByName("codedTypeFilter") == null
				|| isEmpty(getElementByIdOrByName("codedTypeFilter").value))) {
			//atLeastOneReportCriteria = true;
			errorText = getElementByIdOrByName("codedTypeFilterLabel1").innerText + " is required to run report";
			if(getElementByIdOrByName("codedTypeFilterLabel1").innerText==undefined)
				errorText = getElementByIdOrByName("codedTypeFilterLabel1").textContent + " is required to run report";
			isError = true;
			errorMsgArray.push(errorText);
			getElementByIdOrByName("codedTypeFilterLabel1").style.color = "#CC0000";
		}else if(getElementByIdOrByName("codedTypeFilterLabel1")!=null){
			getElementByIdOrByName("codedTypeFilterLabel1").style.color = "#000";
		}
		
		if(isError){
			displayGlobalErrorMessage(errorMsgArray);
			return false;
		}else {
			document.forms[0].action = "/nbs/RunReport.do?method=LoadReport";
			document.forms[0].submit();
		}
	}
	var closeCalled = false;
	function handlePageUnload(closePopup,e) {
		// This check is required to avoid duplicate invocation 
		// during close button clicked and page unload.
		if (closeCalled == false) {
			closeCalled = true;

			if (e.clientY < 0 || closePopup == true) {
				// pass control to parent's call back handler
				var opener = getDialogArgument();

				window.returnValue = "windowClosed";
				window.close();
			}
		}
	}
</script>
<style type="text/css">
table.FORM {
	width: 100%;
	margin-top: 15em;
}
</style>
</head>

<body onload="autocompTxtValuesForJSP();startCountdown();"
	onunload="handlePageUnload(); return false;">
	<html:form>

		<table role="presentation" style="width: 100%;">
			<TR>
				<TD align='right'><i> <font class="boldTenRed"> * </font><font
						class="boldTenBlack">Indicates a Required Field </font>
				</i></TD>
			</TR>
		</table>

		<!-- Button bar -->
		<div class="grayButtonBar">
			<input type="button" name="Run" value="Run" onclick="submitForm();" />
			<input type="submit" id="submitB" value="Cancel"
				onclick="return handlePageUnload(true,event);" />
		</div>
		<!-- error1 (error message block) -->
		<div class="none" id="error1" style="width: 100%; text-align: center;">
		</div>
		<!-- Page Errors -->
		<%@ include file="../../../jsp/feedbackMessagesBar.jsp"%>
		<!-- Confirm Message -->
		<tr>
			<td>&nbsp;</td>
		</tr>
		<div class="view" id="reportFilterBlock" style="text-align: center;">
			<nedss:container id="report_filter" name="Report Filters"
				classType="sect" displayImg="false" includeBackToTopLink="no"
				displayLink="no">

				<%
					ReportVO reportVO = (ReportVO) request
									.getAttribute("ReportVO");
							Map filterColumnMap = (Map) request
									.getAttribute("filterColumnMap");
							ReportDT reportDT = reportVO.getTheReportDT();
							Collection filters = reportVO
									.getTheReportFilterDTCollection();
							if (filters != null && filters.size() > 0) {
								Iterator ite = filters.iterator();
								while (ite.hasNext()) {
									ReportFilterDT rfDT = (ReportFilterDT) ite.next();
									String filterCode = rfDT.getTheFilterCodeDT()
											.getFilterCode();
									String columnLabel = (String) filterColumnMap
											.get(filterCode);
									if (filterCode != null
											&& filterCode
													.equals(ReportConstantUtil.TEXT_FILTER)) {
				%>
				<bean:define id="filterCode1" value="<%=filterCode%>"
					toScope="request" />
				<nedss:container id="subsect_report_filter" name="Text Filter"
					classType="subSect" displayImg="false" includeBackToTopLink="no">
					<tr>
						<td class="fieldName"><span
							class="boldRed">*</span><span title="<%=columnLabel%>" id="textTypeFilterLabel"><%=columnLabel%>:</span></td>
						<td><html:text property="searchAttributeMap(${filterCode1})"
								size="20" styleId="textTypeFilter" /></td>
					</tr>
				</nedss:container>
				<%
					} else if (filterCode != null
											&& filterCode
													.equals(ReportConstantUtil.CVG_CUSTOM_1)) {
										String codeSetNm = rfDT.getTheFilterCodeDT()
												.getFilterCodeSetNm();
				%>
				<bean:define id="codeSetNm1" value="<%=codeSetNm%>"
					toScope="request" />
				<bean:define id="filterCode2" value="<%=filterCode%>"
					toScope="request" />
				<nedss:container id="subsect_report_filter" name="Coded Filter"
					classType="subSect" displayImg="false" includeBackToTopLink="no">
					<tr>
						<td class="fieldName"><span
							class="boldRed">*</span><span title="<%=columnLabel%>" id="codedTypeFilterLabel1"><%=columnLabel%>:</span></td>
						<td>
							<div class="multiSelectBlock">
								<i> <span class=" InputFieldLabel" id="sourceValueHDComment">(Use
										Ctrl to select more than one value)</span>
								</i> <br /> <br />
								<html:select property="answerArray(${filterCode2})"
									styleId="codedTypeFilter" size="4" multiple="true"
									onchange="displaySelectedSourceVal(this, 'codedValueDivHD',' , ')">
									<html:optionsCollection property="codedValue(${codeSetNm1})"
										value="key" label="value" />
								</html:select>
								<!-- Selected values -->
								<div id="codedValueDivHD" style="margin: 0.25em;">
									<b> Selected Values:</b>
								</div>
							</div>
						</td>
					</tr>
				</nedss:container>
								<%
					} else if (filterCode != null
											&& filterCode
													.equals(ReportConstantUtil.CVG_CUSTOM_2)) {
										String codeSetNm = rfDT.getTheFilterCodeDT()
												.getFilterCodeSetNm();
				%>
				<bean:define id="codeSetNm2" value="<%=codeSetNm%>"
					toScope="request" />
				<bean:define id="filterCode3" value="<%=filterCode%>"
					toScope="request" />
				<nedss:container id="subsect_report_filter_2" name="Coded Filter"
					classType="subSect" displayImg="false" includeBackToTopLink="no">
					<tr>
						<td class="fieldName"><span
							class="boldRed">*</span><span title="Place"  id="codedTypeFilterLabel2">Place:</span></td>
						<td>
							<div class="multiSelectBlock">
								<i> <span class=" InputFieldLabel" id="sourceValueHDComment2">(Use
										Ctrl to select more than one value)</span>
								</i> <br /> <br />
								<html:select property="answerArray(${filterCode3})"
									styleId="codedTypeFilter2" size="4" multiple="true"
									onchange="displaySelectedSourceVal(this, 'codedValueDivHD2',' , ')">
									<html:optionsCollection property="codedValue(${codeSetNm2})"
										value="key" label="value" />
								</html:select>
								<!-- Selected values -->
								<div id="codedValueDivHD2" style="margin: 0.25em;">
									<b> Selected Values:</b>
								</div>
							</div>
						</td>
					</tr>
				</nedss:container>
				
				<%
					} else if (filterCode != null
											&& filterCode
													.startsWith(ReportConstantUtil.TIME_RANGE_CODE)) {
				%>
				<bean:define id="filterCode4" value="<%=filterCode%>"
					toScope="request" />
				<nedss:container id="subsect_report_filter" name="Time Range Filter"
					classType="subSect" displayImg="false" includeBackToTopLink="no">
					<tr>
						<td class="fieldName"><span	title="<%=columnLabel%>" id="dateTypeFilterLabel"><%=columnLabel%></span></td><td>&nbsp;</td>
					</tr>
					<tr>
						<td class="fieldName"><span
							class="boldRed">*</span><span title="From">From:</span></td>
						<td><html:text maxlength="10"
								property="searchAttributeMap(${filterCode4}_FROM_TIME)"
								styleId="timeFrom" onkeyup="DateMask(this,null,event)" size="10"
								title="<%=columnLabel%>" /> <html:img src="calendar.gif" alt="Select a Date"
								onclick="getCalDate('timeFrom','timeFromIcon'); return false;"
								onkeypress="showCalendarEnterKey('timeFrom','timeFromIcon',event);"
								
								styleId="timeFromIcon"/></td>
					</tr>
					<tr>
						<td class="fieldName"><span
							class="boldRed">*</span><span title="To">To:</span></td>
						<td><html:text maxlength="10"
								property="searchAttributeMap(${filterCode4}_TO_TIME)"
								styleId="timeTo" onkeyup="DateMask(this,null,event)" size="10"
								title="<%=columnLabel%>" /> <html:img src="calendar.gif" alt="Select a Date"
								onclick="getCalDate('timeTo','timeToIcon'); return false;"
								onkeypress ="showCalendarEnterKey('timeTo','timeToIcon',event);"
								
								styleId="timeToIcon"/></td>
					</tr>
				</nedss:container>
				<%
					}
				%>
				<%
					}
							}
				%>
		   </nedss:container>

		</div>

		<!-- Bottom button bar -->
		<div class="grayButtonBar">
			<input type="button" name="Run" value="Run" onclick="submitForm();" />
			<input type="submit" id="submitB" value="Cancel" onclick="return handlePageUnload(true,event);" />
		</div>
	</html:form>
</body>
</html>