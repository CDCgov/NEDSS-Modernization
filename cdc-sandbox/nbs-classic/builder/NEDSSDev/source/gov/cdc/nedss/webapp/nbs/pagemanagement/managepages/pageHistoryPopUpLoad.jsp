<html lang="en">
    <head>
        <base target="_self">
        <title>Nedss: Manage Pages</title>
        <%@ include file="/jsp/tags.jsp" %>
        <%@ include file="/jsp/resources.jsp" %>
        <%@ page import="gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT,
                         java.util.ArrayList" %>
        
        <meta http-equiv="MSThemeCompatible" content="yes"/>
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
            function handlePageUnload(closePopup, e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;                   
                    if ((closePopup == true) || (e != null && e != undefined && e.clientY < 0)) {
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
            
           
           
            
        </script>
    </head>
    <body class="popup" style = "padding-right:6px; overflow:auto;" onload="startCountdown();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;">
        <div class="popupTitle"> Manage Pages : View Page History </div>
        
        <!-- Top button bar -->
        <div class="popupButtonBar">      
            <input type="button" value="Close" onclick="handlePageUnload(true, event)"></input>          
            <!-- TODO: for post 4.0 release     
            <input type="button" value="Add New Question" onclick="addQuestion()"></input>
             -->
        </div>       
        <!-- Search results table -->
        <div id="searchResultsBlock" >
            
                <nedss:container id="section1" name= " Page Version History" classType="sect" 
                        displayImg ="false" displayLink="false" includeBackToTopLink="no">
                        
                  
                    <table role="presentation" width="98%" border="0" cellspacing="0" align="center">
                        <tr>
                            <td align="center">
                                <display:table name="pageHistoryList" class="dtTable" pagesize="10"  
                                        id="searchResultsDT" requestURI="">
                                 <display:column  title="<p style='display:none'>Empty Column</p>" />  
                                 <display:column property="publishVersionNbr" defaultorder="ascending" sortable="true" sortName= "getPublishVersionNbr" title="Version" />
                                 <display:column property="lastChgTime" defaultorder="ascending" sortable="true" sortName= "getLastChgTime" format="{0,date,MM/dd/yyyy}"  title="Last Updated" />
                                 <display:column property="firstLastName" defaultorder="ascending" sortable="true" sortName= "getFirstLastName" title="Last Updated By" />                                 
                                 <display:column property="descTxt" defaultorder="ascending" sortable="true" sortName= "getDescTxt" title="Notes" />
                                 <display:setProperty name="basic.empty.showtable" value="true"/>
                                </display:table>
                            </td>
                        </tr>
                    </table>
                </nedss:container>
            
        </div>
        
        <!-- Bottom button bar -->    
        <div class="popupButtonBar" style="margin-top:1em;">
            
            <input type="button" value="Close" onclick="handlePageUnload(true, event)"></input>          
            <!-- TODO: for post 4.0 release     
                <input type="button" value="Add New Question" onclick="addQuestion()"></input>
             -->
        </div>
    </body>
</html>