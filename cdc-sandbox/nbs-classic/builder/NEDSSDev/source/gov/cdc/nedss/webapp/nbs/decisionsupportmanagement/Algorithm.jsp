<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/tags.jsp"%>
<%@ page isELIgnored="false"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NBS: Manage Workflow Decision Support</title>
<%@ include file="../../jsp/resources.jsp"%>
<SCRIPT Language="JavaScript" Src="DecisionSupportManagementSpecific.js"></SCRIPT>
<script type="text/javascript"
	src="/nbs/dwr/interface/JDecisionSupport.js"></SCRIPT>
<script language="JavaScript">

	function searchLabResultedTest() {
		//$j(".infoBox").hide();
		var urlToOpen = "/nbs/LabResultedTestLink.do?method=labTestSearchLoad";
		var params = "left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
		var o = new Object();
		//o.condiionvalueselected="true";
		o.opener = self;
		//var modWin = window.showModalDialog(urlToOpen, o, "dialogWidth: " + 760
		//		+ "px;dialogHeight: " + 500
		//		+ "px;status: no;unadorned: yes;scroll: yes;help: no;"
		//		+ (true ? "resizable: yes;" : ""));
		
		var dialogFeatures = "dialogWidth: " + 790
		+ "px;dialogHeight: " + 520
		+ "px;status: no;unadorned: yes;scroll: yes;scrollbars: yes;help: no;"
		+ (true ? "resizable: yes;" : "");
		
		
		var modWin = openWindow(urlToOpen, o, dialogFeatures, null, "");
	}

	function searchCodeResult() {
		//$j(".infoBox").hide();
		var urlToOpen = "/nbs/LabResultedTestLink.do?method=resultCodeSearchLoad";
		var params = "left=100, top=50, width=650, height=500, menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no,status=yes,top=150,left=150";
		var o = new Object();
		o.opener = self;
	//	var modWin = window.showModalDialog(urlToOpen, o, "dialogWidth: " + 760
		//		+ "px;dialogHeight: " + 500
		//		+ "px;status: no;unadorned: yes;scroll: yes;help: no;"
		//		+ (true ? "resizable: yes;" : ""));
		
		var dialogFeatures = "dialogWidth: " + 770
		+ "px;dialogHeight: " + 520
		+ "px;status: no;unadorned: yes;scroll: yes;help: no;"
		+ (true ? "resizable: yes;" : "");
		
		var modWin = openWindow(urlToOpen, o, dialogFeatures, null, "");
	}

	function saveForm() {
		var method = "${decisionSupportForm.attributeMap.method}";
		if (validateDSMRequiredFields()) {
			return false;
		}
		if (!validateBatchData()) {
			return false;
		}

		if (method == "createSubmit") {
			document.forms[0].action = "/nbs/DecisionSupport.do?method=createSubmit";
		} else {
			document.forms[0].action = "/nbs/DecisionSupport.do?method=editSubmit";
		}

		document.forms[0].submit();
	}

	function cancelForm() {
		var confirmMsg = "You have indicated that you would like to cancel the edits to the ${fn:escapeXml(BaseForm.attributeMap.AlgorithmName)} algorithm. Please select OK to continue or Cancel to return to Edit Algorithm.";
		if (confirm(confirmMsg)) {
			document.forms[0].action = "/nbs/ManageDecisionSupport.do?method=loadqueue";
			document.forms[0].submit();
		} else {
			return false;
		}
	}
	function selectBlur() {
		if (getElementByIdOrByName("questionList") != null) {
			getElementByIdOrByName("questionList").blur();
		}
	}

	function selectAdvacedCriteriaBlur() {
		if (getElementByIdOrByName("questionAdvList") != null) {
			getElementByIdOrByName("questionAdvList").blur();
		}
	}
	function selectAdvacedInvCriteriaBlur() {
		if (getElementByIdOrByName("questionAdvInvList") != null) {
			getElementByIdOrByName("questionAdvInvList").blur();
		}
	}
	function getConditionDropDownOnEdit(relatedPage) {
		if (relatedPage != null && relatedPage != "") {
			pausecomp(1000);
			JDecisionSupport
					.getConditionDropDown(
							relatedPage,
							function(data) {
								DWRUtil.removeAllOptions("conditionList");
								DWRUtil.addOptions("conditionList", data,
										"key", "value");
								JDecisionSupport
										.getMultiselectValues(
												"ApplyToConditions",
												function(data) {
													var selectCondList = "";
													if (data != null) {
														for (var j = 0; j < data.length; j++) {
															var conditionNode = document
																	.getElementById('conditionList');
															var conditionNodeVal = conditionNode.options.length;
															for (var i = 0; i < conditionNodeVal; i++) {
																if (conditionNode.options[i].value == data[j]) {
																	conditionNode.options[i].selected = true;
																}
															}
														}
														displaySelectedOptions(
																document
																		.getElementById("conditionList"),
																'conditionList-selectedValues');
													}
												});
							});
		}
	}
	function pausecomp(millis) {
		var date = new Date();
		var curDate = null;

		do {
			curDate = new Date();
		} while (curDate - date < millis);
	}
	
	function enableDisableTimeLogicOnLoad(){
		var elementField = getElementByIdOrByName("useEventDateLogic");
		if(elementField!=undefined && elementField != null && elementField.value=='Y'  ){
			enableDisableTimeLogic(getElementByIdOrByName("useEventDateLogic"));
		}
		else{
			enableDisableTimeLogic();
	}
	}
	
	function enableDisableInvLogicOnLoad(){
		var elementField = getElementByIdOrByName("useInvLogic");
		if(elementField!=undefined && elementField != null && elementField.value=='Y'  )
			enableDisableInvLogic(getElementByIdOrByName("useInvLogicRadioYes"));
		else
			enableDisableInvLogic();
	}

</script>
<style type="text/css">
table.FORM {
	width: 100%;
	margin-top: 15em;
}

div.printexport {
	width: 100%;
	border: 1px solid #DDD;
	margin: 0.5em 0em 0.5em 0em;
	background: #efefef;
	border-color: none;
	border-style: solid;
	font: bold 78% verdana;
	font-size: 12px;
	font-weight: bold;
	padding: 2px;
	border-left-style: outset;
	border-bottom-style: outset
}

table.actionSect {
	background-color: #DCE7F7;
}
</style>
</head>
<logic:equal name="decisionSupportForm" property="actionMode"
	value="Create">
	<body
		onload="onLoadFunction();autocompTxtValuesForJSP();startCountdown();enableDisableTimeLogicOnLoad();enableDisableInvLogicOnLoad();">
</logic:equal>
<logic:equal name="decisionSupportForm" property="actionMode"
	value="Edit">
	<%
		String formCode = (String) request.getAttribute("FormCd");
	%>
	<body
		onload="onLoadFunction();autocompTxtValuesForJSP();startCountdown();getConditionDropDownOnEdit('<%=formCode%>');enableDisableTimeLogicOnLoad();enableDisableInvLogicOnLoad();">
</logic:equal>


<div id="algorithmview"></div>
<!-- Container Div: To hold top nav bar, button bar, body and footer -->
<div id="doc3">
	<html:form>
		<!-- Body div -->
		<div id="bd">
			<!-- Top Nav Bar and top button bar -->
			<%@ include file="../../jsp/topNavFullScreenWidth.jsp"%>

			<!-- For create/edit mode only -->

			<!-- Page Errors -->
			<%@ include file="../../../jsp/feedbackMessagesBar.jsp"%>
			<%@ include file="../../../jsp/errors.jsp"%>
			<!-- Required Field Indicator -->
			<div align="right">
				<span class="boldTenRed"> * </span> <span class="boldTenBlack">
					Indicates a Required Field </span>
			</div>
			<!-- top button bar -->
			<div class="grayButtonBar">
				<input type="button" id="Submit" name="Submit" value="Submit"
					onclick="return saveForm();" /> <input type="button" name="Cancel"
					value="Cancel" onclick="return cancelForm();" />
			</div>
			<logic:equal name="decisionSupportForm" property="actionMode"
				value="Edit">
				<logic:notEqual name="decisionSupportForm" property="fromImport"
					value="Yes">
					<%@ include file="viewAlgorithm/AlgorithmSummary.jsp"%>
				</logic:notEqual>
			</logic:equal>

			<!-- For preview mode only -->
			<!-- Tab container -->
			<%
				String pageNm = "";
					String eventTypeName = (String) request
							.getAttribute("EventTypeName");
			%>
			<logic:equal name="decisionSupportForm" property="actionMode"
				value="Create">
				<%
					pageNm = "Add Algorithm";
				%>
			</logic:equal>
			<logic:equal name="decisionSupportForm" property="actionMode"
				value="Import">
				<%
					pageNm = "Add Algorithm";
				%>
			</logic:equal>
			<logic:equal name="decisionSupportForm" property="actionMode"
				value="Edit">
				<logic:equal name="decisionSupportForm" property="fromImport"
					value="Yes">
					<%
						pageNm = "Add Algorithm" + " - " + eventTypeName;
					%>
				</logic:equal>
				<logic:notEqual name="decisionSupportForm" property="fromImport"
					value="Yes">
					<%
						pageNm = "Edit Algorithm" + " - " + eventTypeName;
					%>
				</logic:notEqual>
			</logic:equal>

			<nedss:container id="section1" name="<%=pageNm%>" classType="sect"
				displayImg="false" displayLink="false" includeBackToTopLink="no">
				<table role="presentation">
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
				<layout:tabs width="100%" styleClass="tabsContainer">
					<layout:tab key="Basic Criteria">
						<jsp:include page="Algorithm_BasicCriteria.jsp" />
					</layout:tab>
					<layout:tab key="Action">
						<jsp:include page="Algorithm_Action.jsp" />
					</layout:tab>
				</layout:tabs>
			</nedss:container>
			<!-- Bottom button bar -->
			<div class="grayButtonBar">
				<input type="button" id="Submit" name="Submit" value="Submit"
					onclick="return saveForm();" /> <input type="button" name="Cancel"
					value="Cancel" onclick="return cancelForm();" />
			</div>
		</div>
		<!-- id=bd -->
	</html:form>
</div>
<!-- Container Div -->
</body>
</html>
<script language="JavaScript">
	var selectedActionType = getElementByIdOrByName("ActionList").value;
	var selectedInvestigationType = getElementByIdOrByName("relatedPage").value;
	var selectedPublishedCondition = document
			.getElementById("PublishedCondition").value;
	var selectedEventType = getElementByIdOrByName("EVENT_TY").value;
	var initEventType = true;
<%if (request.getAttribute("highLightTab") != null
					&& request.getAttribute("highLightTab").equals("action")) {%>
	var tabElts = new Array();
	var parentTab = getItsParentTab('IdSubSectionerrorMessages');
	tabElts.push(parentTab);
	setErrorTabProperties(tabElts);
<%}%>
	
</script>