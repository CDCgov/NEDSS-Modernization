<%@ include file="../../jsp/tags.jsp" %>
<%@ page language="java" %>	
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>

<tr> <td>
	<% 
	    int subSectionIndex = 0;
	    String tabId = "viewSupplementalInfo";
	    String []sectionNames = {"Supplemental Information"};
	    int sectionIndex = 0;
	%>
	
	<style type="text/css">
	   div#addAttachmentBlock {margin-top:10px; display:none; width:100%; text-align:center;}
	</style>
	<div class="view"  id="<%= tabId %>" style="text-align:center;">
	    <table role="presentation" class="sectionsToggler" style="width:100%;">
	        <tr>
	            <td>
	                <ul class="horizontalList">
	                    <li style="margin-right:5px;"><b>Go to: </b></li>
	                    <li><a href="javascript:gotoSection('<%= "sect_" + tabId + sectionIndex %>')"><%= sectionNames[sectionIndex++] %></a></li>
	                </ul>
	            </td>
	        </tr>
	        <tr>
	            <td style="padding-top:1em;">
	                <a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')"/>Collapse Sections</a>
	            </td>
	        </tr>
	    </table>
	
	    <%  // reset the sectionIndex to 0 before utilizing the sectionNames array.
	        sectionIndex = 0;
	    %>
	    
	    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect" includeBackToTopLink="no">
	        <!-- SUB_SECTION : Attachments -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Attachments" classType="subSect" >
	            <tr>
	                <td style="padding:0.5em;">
                        <logic:equal name="mode" value="print">
	                         <display:table id="attachmentsDTTable" name="contactAttachments" class="dtTable" defaultsort="3" defaultorder="ascending">
	                             <display:column class="dateField" property="lastChgTime" title="Date Added" format="{0,date,MM/dd/yyyy}" />
	                             <display:column class="nameField" property="lastChgUserNm" title="Added By" />
	                             <display:column property="fileNmTxt" title="File Name" />
	                             <display:column property="descTxt" title="Description" />
	                             <display:setProperty name="basic.empty.showtable" value="true"/>
	                         </display:table>
                        </logic:equal>
                        <logic:notEqual name="mode" value="print">
	                         <display:table id="attachmentsDTTable" name="contactAttachments" class="dtTable" defaultsort="4" defaultorder="ascending">
	                             <display:column class="iconField" property="deleteLink" title="<p style='display:none'>Delete</p>"/>
	                             <display:column class="dateField" property="lastChgTime" title="Date Added" format="{0,date,MM/dd/yyyy}" />
	                             <display:column class="nameField" property="lastChgUserNm" title="Added By" />
	                             <display:column property="viewLink" title="File Name" sortProperty="fileNmTxt"/>
	                             <display:column property="descTxt" title="Description" />
	                             <display:setProperty name="basic.empty.showtable" value="true"/>
	                         </display:table>
                        </logic:notEqual>        	                       
	                </td>
	            </tr>
	            <tr>
	                <td style="padding:0.5em;">
	                    <div class="nonPrintPreviewModeOnly" style="text-align:right;">
	                        <input type="button" value="Add Attachment" 
	                                onclick="javascript:showAddAttachmentBlock()" />
	                    </div>
	                    <div id="addAttachmentBlock">
                            <iframe src="ShowFileUploadForm.do" name="addFileFrame"
                                    class="fileAttachmentIFrame" id="addFileFrame"
                                    width="100%"
                                    frameborder="0" scrolling='no'>
                            </iframe>
	                    </div>
	                </td>
	            </tr>
	            <tr>
	               <td>
	                   <iframe id="downloadFileIFrame" src="" height="0" width="0"> </iframe>
	               </td>
	            </tr>
	        </nedss:container>
	        
	        <!-- SUB_SECTION : Notes -->
            <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Notes" classType="subSect" >
                <tr>
                    <td style="padding:0.5em;">
                        <display:table id="notesDTTable" name="contactNotes" class="dtTable" defaultsort="4" defaultorder="ascending">
                             <display:column class="dateField" property="lastChgTime" title="Date Added" format="{0,date,MM/dd/yyyy}" />
                             <display:column class="nameField" property="lastChgUserNm" title="Added By" />
                             <display:column property="note" title="Note" />
                             <display:column class="iconField" property="privateIndCd" title="Private" style="text-align:center;"/>
                             <display:setProperty name="basic.empty.showtable" value="true"/>
                         </display:table>
                    </td>
                </tr>
                <tr>
                    <td style="padding:0.5em;">
                        <div class="nonPrintPreviewModeOnly" style="text-align:right;">
                            <input type="button" value="Add Notes" 
                                    onclick="javascript:showNotesBlock()" />
                        </div>
                        <div id="addNotesBlock" style="display:none;">
                            <iframe src="AddNotes.do?method=showForm" name="addNotesIFrame"
                                    class="addNotesIFrame" id="addNotesIFrame"
                                    width="100%"
                                    frameborder="0" scrolling='no'>
                            </iframe>
                        </div>
                    </td>
                </tr>
            </nedss:container>

            
	        <!-- SUB_SECTION : Revision History -->
	        <!--
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Revision History" classType="subSect" >
	        </nedss:container>
	        -->
	        
	        <!-- SUB_SECTION : Investigation Summary -->
	        <!--
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigation Summary" classType="subSect">
	        </nedss:container>
	        -->
	        
	         <!-- SUB_SECTION : Investigation Summary  -->
	        <logic:equal name="viewInves" value="true">
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigation Summary" classType="subSect" >
	       
	        <tr><td>		
				<display:table name="strContactInvestigationList" class="bluebardtTable"  id="eventSumaryInv">
				   <display:column property="rscSecRef" style="width:6%;text-align:left;" title="RSC/SecRef"/>
				   <display:column property="status" style="width:6%;text-align:left;" title="Status"/>
				   <display:column property="conditions" style="width:24%;text-align:left;" title="Condition"/>                                                  
				   <display:column property="caseStatus" style="width:10%;text-align:left;" title="Case Status"/>
				   <display:column property="disposition" style="width:12%;text-align:left;" title="Disposition"/>
				   <display:column property="jurisdiction" style="width:14%;text-align:left;" title="Jurisdiction"/>
				   <display:column property="investigator" style="width:14%;text-align:left;" title="Investigator"/>
				   <display:column property="investigationId" style="width:12%;text-align:left;" title="Investigation ID "/>
				</display:table> 
			</td></tr>
			
	        </nedss:container>
	        </logic:equal>
	       
	    </nedss:container>
	    <div class="tabNavLinks">
            <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
            <a href="javascript:navigateTab('next')"> Next </a>
            <!-- Note : Is used to denote the end of tab for the "moveToNextField() JS 
                function to work properly -->
            <input type="hidden" name="endOfTab" />
    	</div>
	    
	</div>
	<script type="text/javascript" src="/nbs/dwr/interface/JCTContactForm.js"></script>
	<script type="text/javascript">
	    function showAddAttachmentBlock() {
            $j("body").find(":input").attr("disabled","disabled");
            $j("div#addAttachmentBlock").show();
            $j("div#addAttachmentBlock").find(":input").attr("disabled","");
            
            // resize the iframe
            resizeIFrame();
            
            // disable links
            var hrefs = $j("table.dtTable").find("a");
            for (var li = 0; li < hrefs.length; li++) {
                var href = $j(hrefs[li]).attr("href");
                var hiddenSpan = "<span style=\"display:none;\">" + href + "</span>";
                $j(hrefs[li]).append(hiddenSpan);
                $j(hrefs[li]).removeAttr("href");
                $j(hrefs[li]).attr("disabled", "disabled");
                $j(hrefs[li]).css("cursor", "none");        
            }
	    }
	    
	    function resizeIFrame() {
            // resize the add attachment iframe
            var jQueryId = "div" + "#" + "addAttachmentBlock";
            var filesAttachmentIFrame = $j(jQueryId).find("iframe").get(0);
            if (filesAttachmentIFrame.style.height != filesAttachmentIFrame.contentWindow.document.body.scrollHeight) {
                filesAttachmentIFrame.style.height = (filesAttachmentIFrame.contentWindow.document.body.scrollHeight) + "px";    
            }

            // resize the add notes iframe
            var jQueryId2 = "div" + "#" + "addNotesBlock";
            var addNotesIFrame = $j(jQueryId2).find("iframe").get(0);
            if (addNotesIFrame.style.height != addNotesIFrame.contentWindow.document.body.scrollHeight) {
                addNotesIFrame.style.height = (addNotesIFrame.contentWindow.document.body.scrollHeight) + "px";    
            }
	    }
	    
	    function cancelAttachmentBlock() {
	        // enable all input elts
	        $j("body").find(":input").attr("disabled","");

	        // hide attachments block
	        $j("div#addAttachmentBlock").hide();
	        
            // enable links
            var hrefs = $j("table.dtTable").find("a");
            for (var li = 0; li < hrefs.length; li++) {
                var href = jQuery.trim($j($j(hrefs[li]).find("span").get(0)).html());
                if (href != "") {
                    href = href.replace(/&amp;/g, "&");
                    $j(hrefs[li]).attr("href", href);    
                }
                $j(hrefs[li]).find("span").remove();
                $j(hrefs[li]).attr("disabled", "");
                $j(hrefs[li]).css("cursor", "hand");
            }
	    }
	    
        function deleteAttachment(uid) {
            var deleteMsg = "You have indicated that you would like to delete this attachment. By doing so, " +
                        "this file will no longer be available in the system. Would you like to continue " + 
                        "with this action?";
            var choice = confirm(deleteMsg);
            if (choice) {
	            JCTContactForm.deleteAttachment (uid, function(data) {
	                if(data == true) {
	                    var toFind = "td_"+uid;
	                    $j("#" + toFind).parent().parent().remove();
	                }
	            });
            } 
        }
        
        function loadAttachment(urlToLoad) {
            $j("iframe#downloadFileIFrame").attr("src",urlToLoad);
        }
        
        function showNotesBlock() {
            $j("body").find(":input").attr("disabled","disabled");
            $j("div#addNotesBlock").show();
            
            // resize the iframe
            resizeIFrame();
            
            // disable links
            var hrefs = $j("table.dtTable").find("a");
            for (var li = 0; li < hrefs.length; li++) {
                var href = $j(hrefs[li]).attr("href");
                var hiddenSpan = "<span style=\"display:none;\">" + href + "</span>";
                $j(hrefs[li]).append(hiddenSpan);
                $j(hrefs[li]).removeAttr("href");
                $j(hrefs[li]).attr("disabled", "disabled");
                $j(hrefs[li]).css("cursor", "none");        
            }
        }
        
        function cancelNotesBlock() {
            // enable all input elts
            $j("body").find(":input").attr("disabled","");

            // hide notes block
            $j("div#addNotesBlock").hide();
            
            // enable links
            var hrefs = $j("table.dtTable").find("a");
            for (var li = 0; li < hrefs.length; li++) {
                var href = jQuery.trim($j($j(hrefs[li]).find("span").get(0)).html());
                if (href != "") {
                    href = href.replace(/&amp;/g, "&");
                    $j(hrefs[li]).attr("href", href);    
                }
                $j(hrefs[li]).find("span").remove();
                $j(hrefs[li]).attr("disabled", "");
                $j(hrefs[li]).css("cursor", "hand");
            }
        }
	</script>
</td> </tr>