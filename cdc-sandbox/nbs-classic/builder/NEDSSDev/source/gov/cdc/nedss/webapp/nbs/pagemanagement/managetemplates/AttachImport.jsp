<%@ page language="java" %>	
<%@ page isELIgnored ="false" %>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../jsp/tags.jsp" %>
<%@ include file="/jsp/resources.jsp" %>
<html lang="en">
	<head>
		<title>Manage Templates: Import Template</title>
		<base target="_self">
	</head>	
<script language="javaScript" src="FilesUpload.js"></script>

<style type="text/css">
    table.formTable tr {background:white;}
    table.formTable tr td {vertical-align:top; padding:0.15em;}
    table.formTable tr td.fieldName {width:150px;}
</style>
<body onunload="closePopup()">	
<html:form styleId="fileAttachmentForm" action="/UploadExportFile.do" enctype="multipart/form-data" method="post">
    <table role="presentation" class="formTable" style="background:red; width:100%; height:100%; margin:0px;">
        <tr>
            <td colspan="2">
                <div id="errorBlock" class="screenOnly infoBox errors" style="display:none;"> </div>
                <div id="msgBlock" class="screenOnly infoBox messages" style="display:none;"> </div>
            </td>
        </tr>
    
        <tr>
               <td colspan="2" style="text-align:left;"><span style="color:black; font-weight:normal;"> &nbsp;&nbsp;Please enter the location of or browse to the Template you would like to import.	</a></td>
        </tr>
        <!-- File Attachment -->
        <tr>
            <td class="fieldName" id="chooseFileLabel"> <span style="color:#CC0000; font-weight:bold;">*</span>Template Name:</td>
            <td><html:file onkeypress="this.click();" title="Template Name" onkeypress="this.click();" property="importFile" maxlength="37" size= "37" onchange="handleNewFileSelection(this.value)" name="manageTemplateForm"> </html:file></td>
           
        </tr> 
         <tr>
            <td colspan="2" style="text-align:right;">
                <input type="button" value="OK" name="submitButton" onclick="return submitAttachment();"/>
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
</body>
<script type="text/javascript" src="/nbs/dwr/interface/JManageTemplateForm.js"></script>
<script type="text/javascript">
     var isFormSubmission = false;
    <% if (request.getAttribute("confirmation") != null && 
            String.valueOf(request.getAttribute("confirmation")).equals("true")) { %>
    	 var appnd ="";   
     	<%if (request.getAttribute("newAttachment") != null){%>
          	appnd ='<tr class="odd"><c:out value="${newAttachment}" escapeXml="false"/></tr>';
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
       // resetFileUploadForm();
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
        if (jQuery.trim(document.manageTemplateForm.importFile.value) == "") {
            errors[index++] = "Choose File is a required field.";
            hasErrors = true;
            $j("td#chooseFileLabel").css("color", "#CC0000");
        }
        else {
            $j("td#chooseFileLabel").css("color", "black");
        }
        
       
        if (hasErrors) {
            displayErrors("errorBlock", errors);
            // call resize fram to take into account the added error text, that caused
            // the iframe's height to grow. 
            //parent.resizeIFrame();
            return false;
        }
        else {
            // disable save and cancel buttons
            document.manageTemplateForm.submitButton.disabled = true;
            document.manageTemplateForm.cancelButton.disabled = true;
            
            $j("div#msgBlock").html("Uploading file. Please wait...");
            $j("div#msgBlock").show();
          //  parent.resizeIFrame();
            
            $j("div#errorBlock").hide();   
            var filePath = document.manageTemplateForm.importFile.value;          
            var opener = getDialogArgument();
			var formFile = " ";					
		    opener.importTemplateSubmit(filePath);
		    var invest = getElementByIdOrByNameNode("pamview", opener.document)
             if (invest == null) {
           			  invest = getElementByIdOrByNameNode("blockparent", opener.document);                   
     			 }
     			
             invest.style.display = "none"; 
               self.close();	 
             
			
				
			
           	
        }
	}	
	
	function cancelAttachment() {	
		var opener = getDialogArgument();
	 var invest = getElementByIdOrByNameNode("pamview", opener.document)
             if (invest == null) {
           			  invest = getElementByIdOrByNameNode("blockparent", opener.document);                   
     			 }
     			invest.style.display ="none";
     			 self.close();
      //  resetFileUploadForm();
        
        // display elts
       //$j("td#fileNameLabel").css("color", "black");
      // $j("td#chooseFileLabel").css("color", "black");
       // $j("div#errorBlock").hide();
       //$j("div#msgBlock").hide();
        
       // parent.cancelAttachmentBlock();
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
	//alert(filePath);
	   $j("form#fileAttachmentForm span#attachmentFileChosen").html(filePath);
	   // windows
	   var fileName = filePath.substring(filePath.lastIndexOf("\\")+1, filePath.length);
	   
	   // linux
	   if (fileName.length == filePath.length) {
	       fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length);
	   } 

	 //  parent.resizeIFrame();
	}
	
	function uploadFiles() {
        // call the startProgress method defined in FilesUpload.js
        startProgress();
	}
	
	function closePopup()
		    {
			    if (isFormSubmission == false) {
	                self.close();	
	                var opener = getDialogArgument(); 	                 
	                var invest = null; 	                      
	                invest = getElementByIdOrByNameNode("pamview", opener.document)
	                if (invest == null) {
               			  invest = getElementByIdOrByNameNode("blockparent", opener.document);                   
         			 }
         			 alert(invest.style.display);
	                invest.style.display = "none";  
	                return true;               
	               
			    } 
		    }

	
</script>