<html lang="en">
    <head>
        <base target="_self">
        <title>Question Search Results</title>
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <%@ page import="gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT,
                         java.util.ArrayList" %>
        <%@ page isELIgnored="false"%>
        <meta http-equiv="MSThemeCompatible" content="yes"/>
        <style>
		/*For fixing the buttons at the bottom in Question Search Results window*/
		#searchResultsBlock .sectBody{
			float:left;	
		}
		</style>
        <script type='text/javascript' src='/nbs/dwr/engine.js'></script>
        <script type='text/javascript' src='/nbs/dwr/util.js'></script>        
        <script type="text/javascript" src="/nbs/dwr/interface/JPageBuilder.js"></script>
        <script type="text/javaScript" src="/nbs/dwr/interface/JManageQuestionsForm.js"></script>
        <script type="text/javascript">
            window.onload = function() {
                $j("body").find(':input:visible:enabled:first').focus();
            }

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
                    
					if(unblock){
						// get reference to opener/parent           
                        var opener = getDialogArgument();
                        
                        var grayOverlay = getElementByIdOrByNameNode("parentWindowDiv", opener.document);
                        if (grayOverlay == null) {
                            grayOverlay = getElementByIdOrByNameNode("blockparent", opener.document);
                        }
                        grayOverlay.style.display = "none";
						
					}
                    if ((closePopup == true) || (e != null && e != undefined && e.clientY < 0)) {
                        
                                                 
                        self.close();
                        return true;
                    }
                }
            }
            
            /**
                Check to see if the question selected already exists on the page. If it does,
                uncheck the checkbox and display a message.
            */
            var selectedQuestions = [];
            function checkforimportedquestions(questionUid, uniquename) {
                var qUid = questionUid;   
                JManageQuestionsForm.isQuestionImported(qUid, function(data) {
                    // FIXME: remove hard coded value
                    data = "imported";
                
                    if(data == "imported"){
                        var alreadyExists = false;
                        
                        if (selectedQuestions.length > 0) {
                            for (var i = 0; i < selectedQuestions.length; i++) {
                                if (selectedQuestions[i] == qUid) {
                                    alreadyExists = true;
                                    break;
                                }
                            }
                        }
                        
                        if (alreadyExists == false) {
                            selectedQuestions[selectedQuestions.length] = qUid;
                        }
                        
                        return true;
                    }
                    
                    if(data == "notimported"){
                        alert(uniquename + " already exists on this page. Please select another " + 
                               " question or click 'Cancel' to return to the Page Builder screen");
                        getElementByIdOrByName(questionUid).checked = false;
                        return false;
                    }
                });
            }
            
            /**
                Make a DWR call and retrieve the JSON equivalent of all the 
                selected questions. 
            */
            function importQuestions(event)
            {
            	var subSectionId = '${fn:escapeXml(SubsectionId)}';
                var childEltsCount = '${fn:escapeXml(childEltsCount)}';
                var linkUrls = $j("span.pagelinks").find("a");
                var selectionsStr = "";
                 

                // if pagination links are present for this 
                // display tag table, there is a possibility that user 
                // has selected question in other pages as well. So,
                // use the pagination URL data to infer the IDs of questions
                // selected
                if (linkUrls.length > 0) {
                    var singleUrl = $j(linkUrls[0]).attr("href");
					var selection = singleUrl.substring(singleUrl.indexOf("selections"));
					var selected = "";
					if(selection.indexOf("&")!=-1)
						selected = selection.substring(0,selection.indexOf("&"));
					else
						selected = selection;
					
					
                    if (singleUrl.indexOf("selections") > 0 && selected.length>12) 
                    {                    
                        var tmp = singleUrl.substring(singleUrl.indexOf("&selections")+1, singleUrl.length);                       
                        if (tmp.indexOf("&") > 0) {
                            selectionsStr = tmp.substring(0, tmp.indexOf("&", 1)); 
                        }
                        else {
                            selectionsStr = tmp;
                        }
                        selectionsStr = selectionsStr.replace("selections=", "");
                        selectionsStr = selectionsStr.replace(/_/g, ",");
                    }
                }
                // since there are no pagination links, infer the Ids of 
                // questions selected by going through the checkboxes in this
                // page
                else {
                    var checkBoxElts = $j("table#searchResultsDT").find("input.selectionCheckBoxElt");
                    for (var i = 0; i < checkBoxElts.length; i++) {
                        if ($j(checkBoxElts[i]).attr("checked") == true) {
                            selectionsStr += $j(checkBoxElts[i]).attr("id") + ",";    
                        }
                    }
                }


                    // import selected questions
                    if (selectionsStr.length > 0) {
                        //alert("selectionsStr = " + selectionsStr);
                        JPageBuilder.addQuestionsToPMProxyVO(selectionsStr, function(data){
                            if (data == null || jQuery.trim(data).length == 0) {
                                alert("no question JSON object was returned.");
                            }
                            else {
                                // get reference to opener/parent
                                var opener = getDialogArgument();
                                opener.importQuestionsDialogCallback(data,subSectionId,childEltsCount);
    
                                // close this popup
                                handlePageUnload(true, event);
                            }
                        });
                }

            }
            
            function addQuestion()
            {
                document.forms[0].action ="/nbs/LoadManageQuestions.do?method=addQuestionLoadfromPageBuilder";
                document.forms[0].submit();
            }

            /**
                To maintain the checkbox selections when user goes from
                one page to another, the pagination links are updated
                everytime a user makes a new selection. For example, if a user
                "checks/selects" a checkbox, the id of the checked box row is appended
                to all the pagination URLs available for this display tag table. 
                On the other hand, if the user "unchecks/deselects" a checkbox, 
                it is removed from all pagination URLs for this display tag table.  
            */            
            function updatePaginationLinks(checkBoxElt) {
                var linkUrls = $j("span.pagelinks   ").find("a");
                
                // update the links only if the results spawn multipl pages. 
                if (linkUrls.length > 0) {
                    var singleUrl = $j(linkUrls[0]).attr("href");                       
                    if (checkBoxElt.checked == true) {                    
                        if (singleUrl.indexOf("selections") < 0) {
                            var addStr = "&selections=" + $j(checkBoxElt).attr("id") + "_";
                            
                            for (var i = 0; i < linkUrls.length; i++) {
                               $j(linkUrls[i]).attr("href", $j(linkUrls[i]).attr("href") + addStr);
                           }
                        }
                        else {
                           var selectionsStr = singleUrl.substring(singleUrl.indexOf("&selections"), singleUrl.indexOf("_", singleUrl.indexOf("&selections"))+1);                           
                           for (var i = 0; i < linkUrls.length; i++) {
                               $j(linkUrls[i]).attr("href", $j(linkUrls[i]).attr("href").replace(selectionsStr, selectionsStr + $j(checkBoxElt).attr("id") + "_"));
                           }                                                      
                        }
                    }
                    else {
                        // update the selectall checkbox
                        $j("input#selectAllCheckBoxElt").attr("checked", false); 
                        if (singleUrl.indexOf($j(checkBoxElt).attr("id")) > 0) {
                           //var selectionsStr = singleUrl.substring(singleUrl.indexOf("&selections"), singleUrl.indexOf("_", singleUrl.indexOf("&selections"))+1);
                           
                            if (singleUrl.indexOf("_" + $j(checkBoxElt).attr("id") + "_") > 0) {                                
                                for (var i = 0; i < linkUrls.length; i++) {
                                   $j(linkUrls[i]).attr("href", $j(linkUrls[i]).attr("href").replace("_"+$j(checkBoxElt).attr("id")+"_", "_"));
                                }
                            }
							else
								if (singleUrl.indexOf($j(checkBoxElt).attr("id") + "_") > 0) {                                
                                for (var i = 0; i < linkUrls.length; i++) {
                                   $j(linkUrls[i]).attr("href", $j(linkUrls[i]).attr("href").replace($j(checkBoxElt).attr("id")+"_", "_"));
                                }
                            }
                            else {
                                for (var i = 0; i < linkUrls.length; i++) {
                                   $j(linkUrls[i]).attr("href", $j(linkUrls[i]).attr("href").replace($j(checkBoxElt).attr("id")+"_", ""));
                                   $j(linkUrls[i]).attr("href", $j(linkUrls[i]).attr("href").replace("&selections=", ""));
                                   
                                }
                            }
                          
                        }

                    }
                }
                
            }
            
            function updateCheckBoxSelections() {
                var linkUrls = $j("span.pagelinks").find("a");
                if (linkUrls.length > 0) {
                    var singleUrl = $j(linkUrls[0]).attr("href");
                    if (singleUrl.indexOf("selections") > 0) {
                        var tmp = singleUrl.substring(singleUrl.indexOf("&selections")+1, singleUrl.length);
                        //alert("tmp = " + tmp);
                        var selectionsStr = "";
                        if (tmp.indexOf("&") > 0) {
                            selectionsStr = tmp.substring(0, tmp.indexOf("&", 1)); 
                        }
                        else {
                            selectionsStr = tmp;
                        }
                        //alert("selectionsStr = " + selectionsStr); 
                        selectionsStr = selectionsStr.replace("selections=", "");
                        //alert("updated selectionsStr = " + selectionsStr);
                        
                        var qIds = selectionsStr.split("_");
                        for (var i = 0; i < qIds.length; i++) {
                            if (jQuery.trim(qIds[i]) != "") {
                                //alert("id to check = " + jQuery.trim(qIds[i]));
                                //alert("current state of " + jQuery.trim(qIds[i]) + " = " + $j("#" + jQuery.trim(qIds[i])).attr("checked"));
                                $j("#" + jQuery.trim(qIds[i])).attr("checked", true);
                            }
                        }
                        //alert("# of checkboxes to update = " + qIds.length);
                    }
                    else {
                        //alert("There are no selections available...");
                    }
                }
            }
            
            function selectAllQuestions(selectAllCheckBoxElt)
            {
                var linkUrls = $j("span.pagelinks").find("a");
                var singleUrl = $j(linkUrls[0]).attr("href");
                                    
                var checkBoxEltsInPage = $j(".selectionCheckBoxElt");                
                for (var i = 0; i < checkBoxEltsInPage.length; i++) {
                    if (selectAllCheckBoxElt.checked == true) {
                        if (checkBoxEltsInPage[i].checked == false) {
                            checkBoxEltsInPage[i].checked = true;
                            updatePaginationLinks(checkBoxEltsInPage[i]);
                        }
                    }
                    else {
                        checkBoxEltsInPage[i].checked = false;
                        updatePaginationLinks(checkBoxEltsInPage[i]);
                    }
                }                                 
            }
                function updateSelectAllCheckBox()
            {
                var elts = $j("INPUT[class='selectionCheckBoxElt'][type='checkbox']");
                var allChecked = true;                
                for (var i = 0; i < elts.length; i++) {                
                    if ($j(elts[i]).attr("checked") == false) {
                        allChecked = false;
                    }
                }
                if (allChecked == true) {
                    $j("INPUT[name='selectAll'][type='checkbox']").attr('checked', true);
                } 
                else {
                    $j("INPUT[name='selectAll'][type='checkbox']").attr('checked', false);
                }
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
  
            
        </script>
    </head>
    <body class="popup" onload="updateCheckBoxSelections();startCountdown();showCount();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;">
        <div class="popupTitle"> Questions Search Results </div>
        
        <!-- Top button bar -->
        <div class="popupButtonBar">
            <input type="button" value="Add Selected Question(s)" onclick="importQuestions(event)"></input>
            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
            <!-- TODO: for post 4.0 release     
            <input type="button" value="Add New Question" onclick="addQuestion()"></input>
             -->
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
        
        <!-- Search results table -->
        <div id="searchResultsBlock" style="height:400px;">
            
                <nedss:container id="section1" name="Search Results" classType="sect" 
                        displayImg ="false" displayLink="false" includeBackToTopLink="no">
                        
                    <div style="width:100%; text-align:right;margin:4px 0px 4px 0px;">
                        <a href="${fn:escapeXml(NewSearchLink)}">New Search</a>&nbsp;|&nbsp;
                        <a href="${fn:escapeXml(RefineSearchLink)}">Refine Search</a>
                    </div>
                    
                    <div class="infoBox messages" align="left">
                        Your Search Criteria: <i> <c:out value="${SearchCriteria}"/> </i>
                        resulted in <b> <%=request.getAttribute("ResultsCount")%> </b> possible matches.
                    </div>
                    
                    <table role="presentation" width="98%" border="0" cellspacing="0" align="center">
                        <tr>
                            <td align="center">
                                <display:table name="manageList" class="dtTable" pagesize="10"  
                                        id="searchResultsDT" requestURI="" sort="list" defaultsort="2" defaultorder="ascending">
                                    <% 
                                        String qIdentifier = "";
                                        if (((ArrayList) request.getAttribute("manageList")).size() > 0) {
                                            qIdentifier = "" + ((WaQuestionDT)pageContext.getAttribute("searchResultsDT")).getWaQuestionUid();    
                                        }
                                    %>
                                    

    
                                
  <display:column title="<p style='display:none'>Select/Deselect All</p>
   
   <input type='checkbox' title='Select All' name='selectAll' onclick='selectAllQuestions(this)' />" headerClass="selectAll" style="width:3%;" media="html">
       <input title="Select/Deselect checkbox" type="checkbox"
        class="selectionCheckBoxElt" name="isRemoved-"$qIdentifier.questionIdentifier"
        id="<%= qIdentifier %>" 
        onclick="updatePaginationLinks(this)"> 
    </input>
    </display:column>  
                                 <display:column property="questionIdentifier" defaultorder="ascending" sortable="true" sortName= "getQuestionIdentifier" title="Unique ID" />
                                 <display:column property="questionNm" defaultorder="ascending" sortable="true" sortName= "getQuestionNm" title="Unique Name" />
                                 <display:column property="subGroupDesc" defaultorder="ascending" sortable="true" sortName= "getSubGroup" title="Subgroup" />                                 
                                 <display:column property="questionLabel" defaultorder="ascending" sortable="true" sortName= "getQuestionLabel" title="Label" />
                                 <display:setProperty name="basic.empty.showtable" value="true"/>
                                </display:table>
                            </td>
                        </tr>
                    </table>
                </nedss:container>
            
        </div>
        
        <!-- Bottom button bar -->    
        <div class="popupButtonBar" style="margin-top:1em; float:left;">
            <input type="button" value="Add Selected Question(s)" onclick="importQuestions(event)"></input>
            <input type="button" value="Cancel" onclick="handlePageUnload(true, event)"></input>          
            <!-- TODO: for post 4.0 release     
                <input type="button" value="Add New Question" onclick="addQuestion()"></input>
             -->
        </div>
      	<input type="hidden" id="queueCnt" value="${fn:escapeXml(queueCount)}"/>
    </body>
</html>