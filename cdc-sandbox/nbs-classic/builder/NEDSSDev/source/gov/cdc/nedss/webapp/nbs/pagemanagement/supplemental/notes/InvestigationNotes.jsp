<%@ page language="java" %>	
<%@ page isELIgnored ="false" %>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../jsp/tags.jsp" %>
<%@ include file="/jsp/resources.jsp" %>

<style type="text/css">
    table.formTable tr {background:#DFEFFF;}
    table.formTable tr td {vertical-align:top; padding:0.15em;}
    table.formTable tr td.fieldName {width:150px;}
</style>

<html:form styleId="addNotesForm" action="/InvAddNotes.do?method=saveForm" method="post">
    <table role="presentation" class="formTableNA" style="width:100%; height:100%; margin:0px;">
        <tr align="center">
            <td colspan="2">
                <div id="errorBlock" class="screenOnly infoBox errors" style="display:none;"></div>
                <div id="msgBlock" class="screenOnly infoBox messages" style="display:none;"> </div>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:left;"> <h3> &nbsp;&nbsp;Add New Notes </h3></td>
        </tr>
        <!-- Notes -->
        <tr>
            <td class="fieldName" id="notesLabel" valign="top"> <span style="color:#CC0000; font-weight:bold;">*</span> Notes:</td>
            <td><html:textarea style="WIDTH: 500px; HEIGHT: 100px;" name="addNotesForm" property="notes"  onkeyup="chkMaxLength(this,2000)" onkeydown="chkMaxLength(this,2000)"  > </html:textarea></td>
        </tr>
        <!-- Public/Private -->
        <tr>
            <td class="fieldName"> Public/Private:</td>
            <td>
                <html:radio name="addNotesForm" property="accessModifier" value="public"> Public </html:radio>
                <html:radio name="addNotesForm" property="accessModifier" value="private"> Private </html:radio>
            </td>
        </tr>
        <!-- Actions -->
        <tr>
            <td colspan="2" style="text-align:right;">
                <input type="button" value="Submit" name="submitButton" onclick="return submitNotes();"/>
                <input type="reset" value="Cancel" name="cancelButton" onclick="javascript:cancelNotes()"/>
            </td>
        </tr>
    </table>
</html:form>

<script type="text/javascript">
    <% if (request.getAttribute("confirmation") != null && 
         String.valueOf(request.getAttribute("confirmation")).equals("true")) { %>
   	 	 var appnd =""; 
     	<%if (request.getAttribute("newNote") != null){%>
        	appnd ='<tr class="odd"><td class="dateField">${fn:escapeXml(addedDate)}</td>';
        	appnd +='<td class="nameField">${fn:escapeXml(userName)}</td>';
        	appnd +='<td>${fn:escapeXml(newNote)}</td>';
        	var ind= '${fn:escapeXml(privateInd)}';
        	if(ind ==='T'){
       			appnd +='<td class="iconField"><img src="accept.gif" alt="True" title ="True"></img></td></tr>';	 	 
        	} else {
       			appnd +='<td class="iconField"></td></tr>';	 	 
       		}
   		<%} else { %>
        	appnd ='<tr class="odd"></tr>';
   		<%}%>
   
        $j(parent.document.body).find("table#notesDTTable tbody tr.empty").remove();
        if($j(parent.document.body).find("table#notesDTTable tbody").html() != null) {
            $j(parent.document.body).find("table#notesDTTable tbody").append(appnd);
        } else {
            $j(parent.document.body).find("table#notesDTTable").append("<tbody></tbody>");
            $j(parent.document.body).find("table#notesDTTable tbody").append(appnd);
        }   
        cancelNotes();
    <%}%>

    function submitNotes() {
        var errors = new Array();
        var index = 0;
        var hasErrors = false;
        
        // notes
        if (jQuery.trim(document.addNotesForm.notes.value) == "") {
            errors[index++] = "Notes is a required field.";
            hasErrors = true;
            $j("td#notesLabel").css("color", "#CC0000");
        }
        else {
            $j("td#notesLabel").css("color", "black");
        }
        
        // notes length
        var notesStr = jQuery.trim(document.addNotesForm.notes.value);
        if (notesStr != "" && notesStr.length > 2000) {
            errors[index++] = "Notes field cannot be more than 2000 characters.";
            hasErrors = true;
            $j("td#notesLabel").css("color", "#CC0000");
        } else {
            $j("td#notesLabel").css("color", "black");
        }
        
        if (hasErrors) {
            displayErrors("errorBlock", errors);
            // call resize frame to take into account the added error text, that caused
            // the iframe's height to grow. 
            parent.resizeIFrame();
            return false;
        }
        else {
 
            // disable save and cancel buttons
            document.addNotesForm.submitButton.disabled = true;
            document.addNotesForm.cancelButton.disabled = true;
            
            $j("div#msgBlock").html("Saving notes. Please wait...");
            $j("div#msgBlock").show();
            
            $j("div#errorBlock").hide();
            document.addNotesForm.action="/nbs/InvAddNotes.do?method=saveForm";
            document.addNotesForm.submit();
            return true;
        }
    }   
    
    function cancelNotes() {
        resetAddNotesForm();
        
        // display elts
        $j("td#notesLabel").css("color", "black");
        $j("div#errorBlock").hide();
        
        parent.cancelNotesBlock();
    }
    
    function resetAddNotesForm() {
        // form elts
        document.addNotesForm.notes.value = "";
        document.addNotesForm.accessModifier[0].checked = true;
    }
    
</script>