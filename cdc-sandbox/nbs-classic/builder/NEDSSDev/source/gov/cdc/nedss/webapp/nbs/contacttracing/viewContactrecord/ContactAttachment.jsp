<%@ page language="java" %>	
<%@ page isELIgnored ="false" %>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../jsp/tags.jsp" %>
<%@ include file="/jsp/resources.jsp" %>
<script language="javaScript" src="FilesUpload.js"></script>

<style type="text/css">
    table.formTable tr {background:#DFEFFF;}
    table.formTable tr td {vertical-align:top; padding:0.15em;}
    table.formTable tr td.fieldName {width:150px;}
</style>

<html:form styleId="fileAttachmentForm" action="/UploadFiles.do" enctype="multipart/form-data" method="post">
    <table role="presentation" class="formTable" style="background:red; width:100%; height:100%; margin:0px;">
        <tr>
            <td colspan="2">
                <div id="errorBlock" class="screenOnly infoBox errors" style="display:none;"> </div>
                <div id="msgBlock" class="screenOnly infoBox messages" style="display:none;"> </div>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:left;"> <h3> &nbsp;&nbsp;Attach New File </h3></td>
        </tr>
        <!-- File Attachment -->
        <tr>
            <td class="fieldName" id="chooseFileLabel"> <span style="color:#CC0000; font-weight:bold;">*</span> Choose File:</td>
            <td><html:file onkeypress="this.click();" property="ctFile" onchange="handleNewFileSelection(this.value)" name="fileUploadForm"> </html:file></td>
        </tr>
        <tr>
            <td class="fieldName"> File Chosen:</td>
            <td><span id="attachmentFileChosen">&nbsp;</span></td>
        </tr>
        <!-- File Name -->
        <tr>
            <td class="fieldName" id="fileNameLabel"> <span style="color:#CC0000; font-weight:bold;">*</span> Name:</td>
            <td><html:text property="fileName" name="fileUploadForm"> </html:text></td>
        </tr>
        <!-- File Description -->
        <tr>
            <td class="fieldName"> Description:</td>
            <td><html:textarea rows="5" cols="40" property="fileDescription" name="fileUploadForm"> </html:textarea></td>
        </tr>
        <!-- Actions -->
        <tr>
            <td colspan="2" style="text-align:center;">
                <input type="button" value="Submit" name="submitButton" onclick="return submitAttachment();"/>
                <input type="reset" value="Cancel" name="cancelButton" onclick="javascript:cancelAttachment()"/>
            </td>
        </tr>
    </table>
    
    <p><span align='left' class='status-text' id='updateStatusMsg'></span>
    <div id="progressBar" style="display: none;">
        <div id="theMeter">
            <div id="progressBarText"></div>
            <div id="progressBarBox">
                <div id="progressBarBoxContent"></div>
            </div>
        </div>
    </div>
    </p>
</html:form>

<script type="text/javascript">
    <% if (request.getAttribute("confirmation") != null && 
            String.valueOf(request.getAttribute("confirmation")).equals("true")) { %>
   	     var appnd ="";   
      
	    <%if (request.getAttribute("newAttachment") != null){%>		
		appnd ='<tr class="odd"><td style="text-align:center;"><a id="td_${fn:escapeXml(attachmentUid)}" href="javascript:deleteAttachment(${fn:escapeXml(attachmentUid)})">Delete</a></td>';
		appnd +='<td class="dateField">${fn:escapeXml(addedDate)}</td>';
		appnd +='<td class="nameField">${fn:escapeXml(userName)}</td>';
		appnd +='<td><a href="javascript:loadAttachment(&quot;/nbs/DownloadFile.do?ctContactAttachmentUid=${fn:escapeXml(attachmentUid)}&fileNmTxt=${fn:escapeXml(fileNmTxt)}&quot;)" disabled="" style="cursor: pointer;">${fn:escapeXml(fileNmTxt)}</a></td>';
		appnd +='<td>${fn:escapeXml(attachmentdescText)}</td></tr>';
	<%} else { %>
    	appnd ='<tr class="odd"></tr>';
	<%}%>
	
	
   		$j(parent.document.body).find("table#attachmentsDTTable tbody tr.empty").remove();
   		if($j(parent.document.body).find("table#attachmentsDTTable tbody").html() != null) {
	  		$j(parent.document.body).find("table#attachmentsDTTable tbody").append(appnd);
	  	} else {
	  		$j(parent.document.body).find("table#attachmentsDTTable").append("<tbody></tbody>");
	  		$j(parent.document.body).find("table#attachmentsDTTable tbody").append(appnd);
	  	}	
  		cancelAttachment();
	<%}%>
	
	// check for max size exceeded error
	<% if (request.getAttribute("maxFileSizeExceeded") != null && 
			String.valueOf(request.getAttribute("maxFileSizeExceeded")).equals("true")) { %>
		var errors = new Array();
        var index = 0;
        var maxSize = <%= String.valueOf(request.getAttribute("maxFileSizeInMB")) %>;
        var errorMsg = "You are trying to upload a file that is greater than " + maxSize + "MB. " + 
                    "This file is too large and cannot be uploaded. Please upload a different " +
                    " file that conforms to the file size above.";
        errors[index++] = errorMsg;
        displayErrors("errorBlock", errors);
        resetFileUploadForm();
        parent.resizeIFrame();
	<%}%>
	
	function saveAttachment() {
        document.ContactRecordAttachmentForm.submit();
    }
    	
	function submitAttachment() {
        var errors = new Array();
        var index = 0;
        var hasErrors = false;
        
        // file attached
        if (jQuery.trim(document.fileUploadForm.ctFile.value) == "") {
            errors[index++] = "Choose File is a required field.";
            hasErrors = true;
            $j("td#chooseFileLabel").css("color", "#CC0000");
        }
        else {
            $j("td#chooseFileLabel").css("color", "black");
        }
        
        // file name	
        if (jQuery.trim(document.fileUploadForm.fileName.value) == "") {
            errors[index++] = "Name is a required field.";
            hasErrors = true;
            $j("td#fileNameLabel").css("color", "#CC0000");
        }
        else {
            $j("td#fileNameLabel").css("color", "black");
        }
        
        if (hasErrors) {
            displayErrors("errorBlock", errors);
            // call resize fram to take into account the added error text, that caused
            // the iframe's height to grow. 
            parent.resizeIFrame();
            return false;
        }
        else {
            // disable save and cancel buttons
            document.fileUploadForm.submitButton.disabled = true;
            document.fileUploadForm.cancelButton.disabled = true;
            
            $j("div#msgBlock").html("Uploading file. Please wait...");
            $j("div#msgBlock").show();
            parent.resizeIFrame();
            
            $j("div#errorBlock").hide();
            document.fileUploadForm.action="/nbs/UploadFiles.do?method=doUpload";
            document.fileUploadForm.submit();
            return true;
        }
	}	
	
	function cancelAttachment() {
        resetFileUploadForm();
        
        // display elts
        $j("td#fileNameLabel").css("color", "black");
        $j("td#chooseFileLabel").css("color", "black");
        $j("div#errorBlock").hide();
        $j("div#msgBlock").hide();
        
        parent.cancelAttachmentBlock();
	}
	
	function resetFileUploadForm() {
        // form elts
        document.fileUploadForm.fileName.value = "";
        document.fileUploadForm.fileDescription.value = "";
        document.fileUploadForm.ctFile.value = "";
        $j("form#fileAttachmentForm span#attachmentFileChosen").html("");
        document.fileUploadForm.submitButton.disabled = false;
        document.fileUploadForm.cancelButton.disabled = false;
	}
	
	function handleNewFileSelection(filePath) {
	   $j("form#fileAttachmentForm span#attachmentFileChosen").html(filePath);
	   // windows
	   var fileName = filePath.substring(filePath.lastIndexOf("\\")+1, filePath.length);
	   
	   // linux
	   if (fileName.length == filePath.length) {
	       fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length);
	   } 
	   document.fileUploadForm.fileName.value = fileName; 
	   parent.resizeIFrame();
	}
	
	function uploadFiles() {
        // call the startProgress method defined in FilesUpload.js
        startProgress();
	}
</script>