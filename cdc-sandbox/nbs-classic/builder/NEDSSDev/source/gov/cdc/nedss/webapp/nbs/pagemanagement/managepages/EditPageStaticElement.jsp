<html lang="en">
    <head>
        <base target="_self">
        <title>NBS: Manage Page</title>
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <%@ page isELIgnored ="false" %>
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <meta http-equiv="MSThemeCompatible" content="yes"/>
        <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
        <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
        <script src="/nbs/dwr/interface/JPageBuilder.js" type="text/javascript"></script>
        <script type="text/javascript">
            if (typeof window.event != 'undefined')
                document.onkeydown = function()
            {
                var t=event.srcElement.type;
                if(t == '' || t == 'undefined' || t == 'button') {
                    return;
                }
                var kc=event.keyCode;
                if(t == 'text' && kc == 13) {
                    searchPatient();
                }
                
                return preventF12(event);
            }
            
            function submitForm()
            {
                if (validateAddStaticEltForm()) {
                 document.pageElementForm.action = "/nbs/ManagePageElement.do?method=editSubmit";
                 document.pageElementForm.submit();
                 return true;
                }
           }
           
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
                @param closePopup - If this is true, close the popup, else do nothing.
            */
            var closeCalled = false;
            function handlePageUnload(closePopup, e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                    if (e.clientY < 0 || closePopup == true) {

                        // get reference to opener/parent           
                        var opener = getDialogArgument();
                        
                        var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                        if (grayOverlay == null) {
                            grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                        }
                        grayOverlay.style.display = "none";
                                                 
                        self.close();
                        return true;
                    }
                }
            }
            
            function validateAddStaticEltForm() 
            {
                var componentUid = "${fn:escapeXml(pageElementForm.pageEltVo.waUiMetadataDT.nbsUiComponentUid)}";
                var questionLabel = $j("#questionLabel").val();
                var linkUrl = $j("#linkUrl").val();
                var commentsText = $j("#commentsText").val();
                var adminComments = $j("#adminComments").val();
                var errors = new Array();

                // Read-only comments
                if (componentUid == "1014") {
                    // comments text
                    if (jQuery.trim(commentsText) == "") {
		                errors.push($j("#commentsTextL").attr("title") + " is required.");
		                $j("#commentsTextL").css("color", "#CC0000");
                    }
	                else {
	                    $j("#commentsTextL").css("color", "black");
	                }
                }
                
                // hyperlink 
                else if (componentUid == "1003") {
                    // label
                    if (jQuery.trim(questionLabel) == "") {
                        errors.push($j("#questionLabelL").attr("title") + " is required.");
                        $j("#questionLabelL").css("color", "#CC0000");
                    }
                    else {
                        $j("#questionLabelL").css("color", "black");
                    }
                    // link url
                    if (jQuery.trim(linkUrl) == "") {
                        errors.push($j("#linkUrlL").attr("title") + " is required.");
                        $j("#linkUrlL").css("color", "#CC0000");
                    }
                    else {
                        $j("#linkUrlL").css("color", "black");
                    }
                }
                
                if (errors != null && errors.length > 0) {
                    displayGlobalErrorMessage(errors);
                    return false;    
                }
                else {
                    return true;
                }
            }
            
            function handlePageOnload()
            {
                // focus on the first valid element
                $j("div#addStaticElementBlock").find(':input[type!=button]:visible:enabled:first').focus();
            }

            function chkMaxLength(sTxtBox, maxlimit) {  
            
                if (sTxtBox.value.length > maxlimit){               
                    sTxtBox.value = sTxtBox.value.substring(0, maxlimit);   
                    }
            }            
        </script>
    </head>
    <body class="popup" onload="handlePageOnload();startCountdown();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;">
        <div class="popupTitle">Manage Pages: Edit Element</div>

        <!-- Top button bar -->
        <div class="popupButtonBar">
            <input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
        </div>
        
        <!-- Required Field Indicator -->
        <div style="text-align:right; width:100%; margin-top:0.5em;"> 
            <span style="color:#CC0000;"> * </span>
            <span style="color:black; font-style:italic;"> Indicates a Required Field </span>  
        </div>
        
        <!-- error1 (error message block) -->
        <div class="none" id="error1" style="width:100%; text-align:center;"> </div>
        
        <!-- Error Messages using Action Messages-->
        <div id="globalFeedbackMessagesBar" class="screenOnly">
            <logic:messagesPresent name="error_messages">
                <div class="infoBox errors" id="errorMessages">
                    <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
                    <ul>
                        <html:messages id="msg" name="error_messages">
                            <li> <bean:write name="msg" /> </li>
                        </html:messages>
                    <ul>
                </div>
            </logic:messagesPresent>
        </div>
        
        <div id="addStaticElementBlock">
            <nedss:container id="sect_addStatic" name="Edit Static Element" classType="sect" 
                   displayImg="false" includeBackToTopLink="no" displayLink="no">
                <nedss:container id="subsect_staticElt" name="" classType="subSect" 
                        displayImg="false" includeBackToTopLink="no">
                    <html:form action="/ManagePageElement.do?action=editSubmit">
                     <tr>
                         <td class="fieldName"> 
                             <span id="eltTypeL" title="Static Element Type">Static Element Type:</span>
                         </td>
                         <td>
                             ${fn:escapeXml(pageElementForm.uiComponentDesc)}
                           <%--   <bean:write name="pageElementForm" property="uiComponentDesc" /> --%>
                        </td>
                     </tr>
                    
                    <!-- read-only comment --> 
                    <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.nbsUiComponentUid" value="1014">
                        <tr>
                            <td class="fieldName"> 
                                <span class="boldRed">*</span>
	                            <span id="commentsTextL" title="Comments Text">Comments Text:</span>
	                         </td>
	                         <td>
	                            <html:textarea title="Comments Text" styleId="commentsText" property="pageEltVo.waUiMetadataDT.questionLabel" rows="3" cols="30" onkeyup="chkMaxLength(this,300)" onkeydown="chkMaxLength(this,300)" />
	                        </td>
	                     </tr>   
                     </logic:equal>
                     
                     <!-- hyperlink -->
                     <logic:equal name="pageElementForm" property="pageEltVo.waUiMetadataDT.nbsUiComponentUid" value="1003">
                        <tr>
	                         <td class="fieldName">
	                             <span class="boldRed">*</span>
	                             <span id="questionLabelL" title="Label">Label:</span>
	                         </td>
	                         <td>
	                            <html:text title="Label" styleId="questionLabel" property="pageEltVo.waUiMetadataDT.questionLabel" maxlength="300" size="40"/>
	                            <br/>
	                            <i>Example: Click here for CDC News</i>
	                         </td>
	                     </tr>
	                     <tr>
	                         <td class="fieldName">
	                             <span class="boldRed">*</span>
	                             <span id="linkUrlL" title="Link URL">Link URL:</span>
	                         </td>
	                         <td>
	                            <html:text title="Link URL" styleId="linkUrl" property="pageEltVo.waUiMetadataDT.defaultValue" maxlength="300" size="50"/>
	                               <br/>
	                               <i>Example: http://www.cdc.gov/news</i>
	                         </td>
	                     </tr>
                     </logic:equal>
                     
                     <tr>
                         <td class="fieldName"> 
                            <span id="adminCommentsL" title="Administrative Comments">Administrative Comments:</span>
                         </td>
                         <td>
                            <html:textarea title="Administrative Comments" styleId="adminComments" property="pageEltVo.waUiMetadataDT.adminComment" rows="5" cols="30"/>
                         </td>
                     </tr>
                    </html:form>
                </nedss:container>
            </nedss:container>
        </div>
        
        <!-- Bottom button bar -->    
        <div class="popupButtonBar" style="margin-top:1em;">
            <input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
        </div>
    </body>
</html>