<html lang="en">
    <head>
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <base target="_self">
        <title>NBS: Manage Pages</title>
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <meta http-equiv="MSThemeCompatible" content="yes"/>
        <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
        <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
        <script src="/nbs/dwr/interface/JPageBuilder.js" type="text/javascript"></script>
        <script type="text/javascript">
            /**
                Submit the form if the user presses enter key within the form
            */
            if (typeof window.event != 'undefined')
                document.onkeydown = function()
            {
                if (event != null && event != 'undefined') {
	                var t = event.srcElement.type;
	                if (t == '' || t == 'undefined' || t == 'button') {
	                    return;
	                }
	                var kc=event.keyCode;
	                if (t == 'text' && kc == 13) {
	                    submitForm();
	                }
	                
	                return preventF12(event);
                }
            }
            
            function importQuestions()
            {
                alert("inside importQuestions");
                
                // dummy set of questions
                var questionIds = [1, 3, 4];
                var qUIdsString = "";
                for (var i = 0; i < questionIds.length; i++) {
                    qUIdsString += questionIds[i] + ",";
                } 

                // retrieve question JSON from backend
                JPageBuilder.retrieveQuestions(qUIdsString, function(data){
                    alert("inside retrieveQuestions: data = " +  data);
		            if (data == null || jQuery.trim(data).length == 0) {
		                // no question JSON object was returned.
		            }
		            else {
		                alert("The question JSON object returned from backend is: " + data);
		                
		            }
		        });
                
                // pass the JSON object to page builder for further processing
                window.opener.searchQuestionsPopupCallback(getQuestionJSON(questionIds));
            }

            function submitForm()
            {
				unblock=false;
                var sQRadio = $j("input#searchQuestionsRadioOption");
                var sERadio = $j("input#staticEltRadioOption");
                var str ="${fn:escapeXml(SubsectionId)}";
                
                
                if ($j(sQRadio).attr("checked")) {
	                    if(validateQuestionElement()){
	                    	// document.manageQuestionsForm.action ="/nbs/SearchManageQuestions.do?method=searchQuestionsSubmitFromPageBuilder";
                             document.manageQuestionsForm.action ="/nbs/SearchManageQuestions.do?method=searchQuestionsSubmitFromPageBuilder&SubsectionId="+str;
                            
                             document.manageQuestionsForm.submit();
                             return true;
	                    } else {
							unblock = true;
							return false;
						}	   
                }
                else if ($j(sERadio).attr("checked")) {                    
                    if (validateAddStaticEltForm()) {
	                    document.pageElementForm.action = "/nbs/ManagePageElement.do?method=editSubmit";
	                    document.pageElementForm.submit();
	                    return true;
                    } else {
						unblock = true;
						return false;
					}
                }
                else {
                    alert("Could not determine the context of form submission. Form not submitted...");
                    unblock = true;
                }
           }

            function validateQuestionElement(){
            	var allEnabledSearchIpElts = $j("#searchQuestionsBlock").find(':input:enabled');
                var errorMsgArray = new Array();
                flag = false;
                for (var i = 0; i < allEnabledSearchIpElts.length; i++) {
                    if ($j(allEnabledSearchIpElts[i]).attr("type") != 'hidden' 
                        && $j(allEnabledSearchIpElts[i]).attr("type") != 'button'
                        && $j(allEnabledSearchIpElts[i]).attr("type") != 'select-one'  
                        ) 
                    {
                        if(jQuery.trim($j(allEnabledSearchIpElts[i]).attr("value")) == "")
                        	   flag = true;
                        else{
                        	   flag = false;
                        	   break;
                        } 
                    }
                }
                if(flag){
                    var msg = " Please enter at least one item to search." + "\n";
                    errorMsgArray.push(msg);
                }
                
                if (errorMsgArray != null && errorMsgArray.length > 0) {
                    displayGlobalErrorMessage(errorMsgArray);
                    return false;    
                }
                else {
                    return true;
                }
                
            }

            
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
                @param closePopup - If this is true, close the popup, else do nothing.
            */
            var closeCalled = false;
			var unblock = true;
			
                function handlePageUnload(closePopup, e)
                {
                    // This check is required to avoid duplicate invocation 
                    // during close button clicked and page unload.
                    if (closeCalled == false) {
                        closeCalled = true;
                        
    					//This has been changed to unblock regardless it is coming from Cancel button or from X button
    					
    						// get reference to opener/parent 
						if(unblock){							
                            var opener = getDialogArgument();
                            
                            var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                            if (grayOverlay == null) {
                                grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                            }
                            grayOverlay.style.display = "none";
						}
                        if (e.clientY < 0 || closePopup == true) {
                                                     
                            self.close();
                            return true;
    						}
                        }
                }
            
            function showDivOption(radioElt) {
                $j("div#globalFeedbackMessagesBar").html("");
                if (radioElt.checked == true) {
                    if (radioElt.value == 'questions') {
                        $j("div#searchQuestionsBlock").show();
                        $j("div#addStaticElementBlock").hide();
                    }
                    else {
                        $j("div#addStaticElementBlock").show();
                        $j("div#searchQuestionsBlock").hide();
                    }
                }
            }
            
            function displayElementProperties(selectElt) {
                var propName = selectElt.options[selectElt.selectedIndex].value;
                if (propName == '1003') {
                    propName = "hyperlink";
                    // to address the issue that 2 form fields cannot have 
                    // the same name.
                    $j("input#questionLabel").attr("disabled", false);
                    $j("input#commentsText").attr("disabled", true);
                }
                else if (propName == '1014') {
                    propName = "readOnlyComments";
                    $j("input#questionLabel").attr("disabled", true);
                    $j("input#commentsText").attr("disabled", false);
                }
                
                if (jQuery.trim(propName) != "") {
	                $j("tr.eltProperty").hide();
	                $j("tr." + propName + "Property").show();
	                $j("tr.allElementsProperty").show();
                }
                else {
                    $j("tr.eltProperty").hide();
                }
            }
             
            function validateAddStaticEltForm() {
                var eltType = $j("#eltType").val();
                var questionLabel = $j("#questionLabel").val();
                var linkUrl = $j("#linkUrl").val();
                var commentsText = $j("#commentsText").val();
                var adminComments = $j("#adminComments").val();
                var errors = new Array();

                if (jQuery.trim(eltType) == "") {
                    errors.push($j("#eltTypeL").attr("title") + " is required.");
                    $j("#eltTypeL").css("color", "#CC0000");
                }
                else {
                    $j("#eltTypeL").css("color", "black");
                    
                    // Read-only comments
                    if (eltType == "1014") {
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
                    else if (eltType == "1003") {
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
                    
                    if (adminComments.length > 2000) {
                        errors.push($j("#adminCommentsL").attr("title") + " is required.");
                        $j("#adminCommentsL").css("color", "#CC0000");
                    }
                    else {
                        $j("#adminCommentsL").css("color", "black");
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
                $j("div#searchQuestionsBlock").find(':input[type!=button]:visible:enabled:first').focus();
            }

            function chkMaxLength(sTxtBox, maxlimit) {  
            
                if (sTxtBox.value.length > maxlimit){               
                    sTxtBox.value = sTxtBox.value.substring(0, maxlimit);   
                    }
            }            
        </script>
    </head>
    <body class="popup" onload="handlePageOnload();startCountdown();autocompTxtValuesForJSP();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;">
        <div class="popupTitle">Manage Pages: Add Element</div>

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
        <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>         
        <!-- Options bar -->        
        <div style="text-align:left; margin:0.5em 0em; padding:5px 0px;">
            <b> &nbsp;<span><font class="boldTenRed" > * </font></span> Element Type: </b>
            <input id="searchQuestionsRadioOption" title="Question Element" type="radio" checked value="questions" name="manageOption" onclick="javascript:showDivOption(this)"/>Question Element &nbsp;&nbsp;&nbsp;
            <input id="staticEltRadioOption" title="Static Element" type="radio" value="staticElt" name="manageOption" onclick="javascript:showDivOption(this)"/> Static Element
        </div>
        <div class="expander"> </div>
        
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
        
        <!-- Block to search for questions/add static element -->
        <div>
	        <!-- Search results table --> 
	        <div id="searchQuestionsBlock" style="style:float; display:block; margin-top:0.5em;">
                <nedss:container id="sect_search" name="Search Criteria" classType="sect" 
                       displayImg="false" includeBackToTopLink="no" displayLink="no">
                    <nedss:container id="subsect_basicInfo" name="" classType="subSect" displayImg="false" includeBackToTopLink="no">
                        <html:form action="/SearchManageQuestions.do">
	                        <tr>
	                            <td class="fieldName"> <span title="Question Type">Question Type:</span></td>
	                            <td>
	                                <html:select title="Question Type" property="searchCriteria(questionType)" styleId = "questionType">
                                        <html:optionsCollection property="codedValue(NBS_QUESTION_TYPE)" value="key" label="value"/>
                                    </html:select>
	                           </td>
	                        </tr>
	                        <tr>
	                            <td class="fieldName"><span title="Unique ID">Unique ID:</span></td>
	                            <td>
	                                <html:text title="Unique ID" property="searchCriteria(questionIdentifier)"  size="40"  styleId="questionIdentifier" />      
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="fieldName"><span title="Unique Name">Unique Name:</span></td>
	                            <td>
	                                <html:text title="Unique Name" property="searchCriteria(questionNm)" size="40"  styleId="questionNm" />      
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="fieldName"> 
	                                <span title="Subgroup">Subgroup:</span>  
	                            </td>
	                            <td>
	                                <html:select title="Subgroup" property="searchCriteria(subGroup)" styleId = "subGroup">
	                                    <html:optionsCollection property="codedValue(NBS_QUES_SUBGROUP)" value="key" label="value"/>
	                                </html:select>
	                            </td>
	                        </tr>  
	                        <tr>
	                            <td class="fieldName"> 
	                                <span title="Label">Label:</span> 
	                            </td>
	                            <td>
	                                <html:text title="Label" property="searchCriteria(label)" styleId="label" size="40"/>      
	                            </td>
	                        </tr>
                        </html:form>   
                    </nedss:container>
                </nedss:container>
            </div>
            
            <div id="addStaticElementBlock" style="style:float; display:none; margin-top:0.5em;">
                <nedss:container id="sect_addStatic" name="Add Static Element" classType="sect" 
                       displayImg="false" includeBackToTopLink="no" displayLink="no">
                    <nedss:container id="subsect_staticElt" name="" classType="subSect" 
                            displayImg="false" includeBackToTopLink="no">
                        <html:form action="/ManagePageElement.do?action=editSubmit">
	                        <tr>
	                            <td class="fieldName"> 
	                                <span class="boldRed">*</span>
	                                <span id="eltTypeL" title="Static Element Type">Static Element Type:</span>
	                            </td>
	                            <td>
	                                <select id="eltType" title="Static Element Type" name="pageEltVo.waUiMetadataDT.nbsUiComponentUid" onchange="displayElementProperties(this)">
	                                    <option value=""></option>
	                                    <option value="1012"> Line Separator </option>
	                                    <option value="1003"> Hyperlink </option>
	                                    <option value="1014"> Read-Only Comments</option>
	                                    <option value="1030"> Read-Only Participant List</option>
	                                    <option value="1036"> Original Electronic Document List</option>
	                                </select>
	                           </td>
	                        </tr>
	                        <tr class="eltProperty hyperlinkProperty" style="display:none;">
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
	                        <tr class="eltProperty hyperlinkProperty" style="display:none;">
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
	                        <tr class="eltProperty readOnlyCommentsProperty" style="display:none;">
	                            <td class="fieldName"> 
	                                <span class="boldRed">*</span>
	                                <span id="commentsTextL" title="Comments Text">Comments Text:</span>
	                            </td>
	                            <td>
	                               <html:textarea title="Comments Text" styleId="commentsText" property="pageEltVo.waUiMetadataDT.questionLabel" rows="3" cols="30" onkeyup="chkMaxLength(this,300)" onkeydown="chkMaxLength(this,300)" />
	                           </td>
	                        </tr>
	                        <tr class="eltProperty allElementsProperty" style="display:none;">
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
            
            <div class="expander"></div>
        </div>
        
        <!-- Bottom button bar -->    
        <div class="popupButtonBar" style="margin-top:1em;">
            <input type="button" name="SubmitForm" value="Submit" onclick="submitForm()"/>
            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
        </div>
    </body>
</html>