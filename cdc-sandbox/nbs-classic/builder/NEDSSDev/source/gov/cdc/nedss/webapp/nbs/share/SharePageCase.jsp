<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants"%>
<html lang="en">
<head>
<meta http-equiv="MSThemeCompatible" content="yes" />
<title>NBS:Share Case Report</title>
<%@ include file="../../jsp/resources.jsp"%>
<script type='text/javascript' src='/nbs/dwr/engine.js'></script>
<script type='text/javascript' src='/nbs/dwr/util.js'></script>
<script type='text/javascript' src='/nbs/dwr/interface/JPageForm.js'></script>
<SCRIPT Language="JavaScript" Src="PageSpecific.js"></SCRIPT>

<script language="JavaScript">
	function cancelDocShare() {
		self.close();
		var opener = getDialogArgument();
		var pview = getElementByIdOrByNameNode("pageview", opener.document)
		pview.style.display = "none";
	}

	function submitDocShare() {

		if (validateShareDocInputFields()) {
			return false; //field validation error
		}

		var opener = getDialogArgument();
		var theRecipient = getElementByIdOrByName("recipient").value;
		var theDocumentType = getElementByIdOrByName("documentType").value;
		var theComment = getElementByIdOrByName("shareComments").value;
		//submit the request
		opener.sharePageCaseSubmit(theRecipient, theComment, theDocumentType );
		
		var invest = getElementByIdOrByNameNode("pageview", opener.document)
		invest.style.display = "none";
		window.close();
		

	}

	function validateShareDocInputFields() {

		getElementByIdOrByName("errorBlock").style.display = "none";
		var errors = new Array();
		var index = 0;
		var isError = false;
		var theRecipient = getElementByIdOrByName("recipient").value;
		var theDocumentType = getElementByIdOrByName("documentType").value;
		var theComment = getElementByIdOrByName("shareComments").value;
		//alert("recipient = " + theRecipient + " doc type=" + theDocumentType + " comments=" + theComment);
		
		if (theRecipient != null && theRecipient.length == 0) {
			errors[index++] = "Recipient is  required";
			getElementByIdOrByName("recipientLabel").style.color = "#CC0000";
			isError = true;
		} else
			getElementByIdOrByName("recipientLabel").style.color = "black";
			
		if (theDocumentType != null && theDocumentType.length == 0) {
			errors[index++] = "Document Type is  required";
			getElementByIdOrByName("documentTypeLabel").style.color = "#CC0000";
			isError = true;
		} else
			getElementByIdOrByName("documentTypeLabel").style.color = "black";
			
		if (theComment != null && theComment.length == 0) {
			errors[index++] = "Share Comments are required";
			getElementByIdOrByName("shareCommentsLabel").style.color = "#CC0000";
			isError = true;
		} else
			getElementByIdOrByName("shareCommentsLabel").style.color = "black";

		if (isError) {
			displayErrors("errorBlock", errors);
			return isError;
		}

	}


	function checkMaxLength(sTxtBox) {
		maxlimit = 1000;
		if (sTxtBox.value.length > maxlimit)
			sTxtBox.value = sTxtBox.value.substring(0, maxlimit);
	}
	function setDefaultDocType() {
	        $j('#documentType').val('PHDC');
          	AutocompleteSynch('documentType_textbox', 'documentType');
	}

</script>
</head>

<body onload="autocompTxtValuesForJSP(); setDefaultDocType();"
	onunload="cancelDocShare()" style="width: 96%">
	<div
		style="width: 100%; margin: 0 auto; margin-top: 3px; margin-bottom: 3px; text-align: left; font-size: 1.1em; font-weight: bold; color: white; background: #003470; padding: 3px 0px">
		Share Case Report</div>
	<div id="topButtId" class="grayButtonBar" style="text-align: right;">
		<input type="button" align="right" value="Submit"
			onclick="submitDocShare()" /> 
		<input type="button" align="right"
			value="Cancel" onclick="cancelDocShare()" />
	</div>

	<div class="infoBox messages" style="text-align: left;">
		Share <b>${PageForm.attributeMap.caseLocalId}</b> with:
	</div>

	<div id='requiredMsg'
		style="text-align: right; width: 100%; margin-top: 0.5em;">
		<span style="color: #CC0000;"> * </span> <span
			style="color: black; font-style: italic;"> Indicates a
			Required Field </span>
	</div>
	<div style="padding: 0.5em 0em;">
		<nedss:container id="sect_DocShare" name="Share Details"
			classType="sect" displayImg="false" includeBackToTopLink="no"
			displayLink="no">
			<nedss:container id="subsect_DocShare" name="" classType="subSect"
				displayImg="false" includeBackToTopLink="no">
				<html:form>
					<tr style="background: #FFF;">
						<td colspan="2" width="100%">
							<div class="infoBox errors" style="display: none;"
								id="errorBlock"></div>
						</td>
					</tr>
					<tr id="recipientRow">
						<td class="fieldName" id="recipientLabel"><font
							class="boldTenRed"> * </font><b>Recipient:<b></td>
						<td><html:select title="Recipient" name="PageForm"
								property="pageClientVO.oldPageProxyVO.notificationVO_s.theNotificationDT.exportReceivingFacilityUid"
								styleId="recipient">
								<html:optionsCollection property="exportFacilityList"
									value="key" label="value" />
							</html:select></td>
					</tr>
					<tr id="documentTypeRow">
						<td class="fieldName" id="documentTypeLabel"><font
							class="boldTenRed"> * </font><b>Document Type:<b></td>
						<td><html:select title="Document Type" name="PageForm"
								property="pageClientVO.oldPageProxyVO.notificationVO_s.theNotificationDT.rptSourceTypeCd"
								styleId="documentType">
								<html:optionsCollection
									property="codedValue(NBS_CASE_DOCUMENT_TYPE)" value="key"
									label="value" />
							</html:select></td>
					</tr>
					<tr id="shareCommentsRow">
						<td class="fieldName" id="shareCommentsLabel"><font
							class="boldTenRed"> * </font><b> Share Comments:<b></td>
						<td><textarea title="Share Comments" rows="6" cols="60" id="shareComments"
								onkeydown="checkMaxLength(this)" onkeyup="checkMaxLength(this)"></textarea>
						</td>
					</tr>

				</html:form>
			</nedss:container>
		</nedss:container>
	</div>
	<div id="topButtId" class="grayButtonBar" style="text-align: right;">
		<input type="button" align="right" value="Submit"
			onclick="submitDocShare()" /> <input type="button" align="right"
			value="Cancel" onclick="cancelDocShare()" />
	</div>

</body>
</html>