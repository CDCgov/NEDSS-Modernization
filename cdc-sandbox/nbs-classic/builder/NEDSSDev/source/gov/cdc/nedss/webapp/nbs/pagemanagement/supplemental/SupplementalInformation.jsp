<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>
<div id="pamview"></div>
<tr> 
  <td>
	<%  
		String []sectionNames = {"Associations","Notes and Attachments","History"};
        String csAddButtonStatus = "";
		int sectionIndex = 0;
		int subSectionIndex = 0; 
		String tabId = "viewSupplementalInformation";
	%>
	
	<h2 class="printOnlyTabHeader">
	    Supplemental Information
	</h2>
	<style type="text/css">
	   div#addAttachmentBlock {margin-top:10px; display:none; width:100%; text-align:center;}
	</style>
   
	<div class="view" id="<%= tabId %>" style="text-align:center;">   
	 
        <table role="presentation" class="sectionsToggler" style="width:100%;">
            <tr>
                <td>
                    <ul class="horizontalList">
                        <li style="margin-right:5px;"><b>Go to: </b></li>
                        <%for(int i = 0; i < sectionNames.length; i++){%>
                        <%if( i != 0 ){ %> <li class="delimiter"> | </li> <%} %>
                        <li><a href="javascript:gotoSection('<%= "sect_" + tabId + i %>')"><%= sectionNames[i] %></a></li>
                        <%} %>
                    </ul>
                </td>
            </tr>
            <tr>
	            <td style="padding-top:1em;">
	                <a class="toggleHref" href="javascript:toggleAllSectionsDisplay('<%= tabId %>')">Collapse Sections</a>
	            </td>
	        </tr>
	    </table>
	    
	    <%
	        // reset the sectionIndex to 0 before utilizing the sectionNames array.
	        sectionIndex = 0;
	    %>

	    <!-- SECTION : Associations (Associated Lab Reports, Associated Morbidity Reports, 
	           Associated Treatments, and Associated Vaccination) --> 
	    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>'  name="<%= sectionNames[sectionIndex++] %>" classType="sect">
	            
	        <!-- SUB_SECTION : Associated Lab Reports-->     
				<logic:equal name="BaseForm" property="securityMap(ObsDisplay)"
					value="true">
					<nedss:container id="<%=tabId + (++subSectionIndex)%>"
						name="Associated Lab Reports" classType="subSect">
						<tr style="background: #FFF;">
							<td>
									<display:table name="observationSummaryLabList" class="dtTable"  id="eventLabReport">
										<display:column property="dateReceived" style="width:11%;text-align:left;" title="Date Received"/>		                                                     
										<display:column property="providerFacility" style="width:22%;text-align:left;" title="Reporting Facility/Provider"/>
										<display:column property="dateCollected" style="width:11%;text-align:left;" title="Date Collected"/>
										<display:column property="description" style="width:34%;text-align:left;" title="Test Results"/>
										<display:column property="progArea" style="width:15%;text-align:left;" title="Program Area"/>
										<display:column property="eventId" style="width:10%;text-align:left;" title="Event ID "/>
										<display:setProperty name="basic.empty.showtable" value="true"/>
									</display:table> 
		      				</td>
						</tr>
					</nedss:container>
				</logic:equal>

				<!-- SUB_SECTION : Associated Morbidity Reports -->
	        <logic:equal name="BaseForm" property="securityMap(MorbDisplay)" value="true">
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Morbidity Reports"  classType="subSect" >
		           <tr style="background:#FFF;">
	                    <td colspan="2">
	                        <display:table name="observationSummaryMorbList" class="dtTable" >
					            <display:column property="actionLink" title="Date Received" />
					            <display:column property="conditionDescTxt" title="Condition" />
					            <display:column property="reportDate" title="Report Date" format="{0,date,MM/dd/yyyy}" />
					            <display:column property="reportTypeDescTxt" title="Type"/>
					            <display:column property="localId" title="Observation ID" />
					            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
	                   </td>
	               </tr>
		        </nedss:container>
	        </logic:equal>
	        
	        <!-- SUB_SECTION : Treatments -->
	        <logic:equal name="BaseForm" property="securityMap(TreatmentDisplay)" value="true">
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Treatments"  classType="subSect" >
		           <tr style="background:#FFF;">
	                    <td colspan="2">
	                        <display:table name="treatmentList" class="dtTable" >
					            <display:column property="actionLink" title="Date" />
					            <display:column property="customTreatmentNameCode" title="Treatment" />
					            <display:column property="localId" title="Treatment ID"/>
					            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
	                   </td>
	               </tr>
		        </nedss:container>
	        </logic:equal>
	        
	        <!-- SUB_SECTION : Vaccination -->
	        <logic:equal name="BaseForm" property="securityMap(VaccinationDisplay)" value="true">
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Vaccinations"  classType="subSect" >
		           <tr style="background:#FFF;">
	                    <td colspan="2">
	                        <display:table name="vaccinationList" class="dtTable" >
					            <display:column property="actionLink" title="Date Administered" />
					            <display:column property="vaccineAdministered" title="Vaccine Administered" />
					            <display:column property="localId" title="Vaccination ID"/>
					            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
	                   </td>
	               </tr>
		        </nedss:container>
	        </logic:equal>
	        
	        <!-- SUB_SECTION : Document -->
	        <logic:equal name="BaseForm" property="securityMap(checkToViewDocument)" value="true">
		        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Associated Documents"  classType="subSect" >
		           <tr style="background:#FFF;">
	                    <td colspan="2">
	                        <display:table name="documentSummaryList" class="dtTable" >
					            <display:column property="actionLink" title="Date Received" />
					            <display:column property="docType" title="Type" />
					            <display:column property="docPurposeCd" title="Purpose"/>
					            <display:column property="cdDescTxt" title="Description"/>
					             <display:column property="localIdForUpdatedAndNewDoc" title="Document ID"/>
	         		            <display:setProperty name="basic.empty.showtable" value="true"/>
					        </display:table>
	                   </td>
	               </tr>
		        </nedss:container>
	        </logic:equal>
	        
	    </nedss:container>

	    <!-- SECTION : Notes and Attachment -->
	    <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' name="<%= sectionNames[sectionIndex++] %>" classType="sect">	       
	        <div class="grayButtonBar">
			   	<table role="presentation" width="100%">
		 	   	    <tr>
		 	   	         <td align="right"> 
		 	   	         	<logic:equal name="BaseForm" property="actionMode" value="View">
					        	<input type="button"  value="Print Notes" id=" " onclick="showPrintFriendlyNotes();"/> 	
					        </logic:equal>				      		      
					    </td>
					</tr>
				</table>
	        </div>
	        <!-- SUB_SECTION : Notes -->
            <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Notes" classType="subSect" >
                <tr>
                    <td style="padding:0.5em;">
                        <display:table id="notesDTTable" name="nbsNotes" class="dtTable" defaultsort="4" defaultorder="ascending">
                             <display:column class="dateField" property="lastChgTime" title="Date Added" style="width:14%;"/>
                             <display:column class="nameField" property="lastChgUserNm" title="Added By" style="width:14%;"/>
                             <display:column property="note" title="Note"  style="width:60%;"/>
                             <display:column class="iconField" property="privateIndCd" title="Private" style="text-align:center;"/>
                             <display:setProperty name="basic.empty.showtable" value="true"/>
                         </display:table>
                    </td>
                </tr>
                <tr>
                    <td style="padding:0.5em;">
                        <div class="nonPrintPreviewModeOnly" style="text-align:right;">
                        <logic:equal name="BaseForm" property="actionMode" value="View">
                            <input type="button" value="Add Notes" 
                                    onclick="javascript:showNotesBlock()" />
                        </logic:equal>
                        </div>
                        <div id="addNotesBlock" style="display:none;">
                            <iframe src="InvAddNotes.do?method=showForm" name="addNotesIFrame"
                                    class="addNotesIFrame" id="addNotesIFrame"
                                    width="100%"
                                    frameborder="0" scrolling='no'>
                            </iframe>
                        </div>
                    </td>
                </tr>
            </nedss:container>	    
	        <!-- SUB_SECTION : Attachments -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Attachments" classType="subSect" >
	            <tr>
	                <td style="padding:0.5em;">
                        <logic:equal name="mode" value="print">
	                         <display:table id="attachmentsDTTable" name="nbsAttachments" class="dtTable" defaultsort="3" defaultorder="ascending">
	                             <display:column class="dateField" property="lastChgTime" title="Date Added" format="{0,date,MM/dd/yyyy}" />
	                             <display:column class="nameField" property="lastChgUserNm" title="Added By" />
	                             <display:column property="fileNmTxt" title="File Name" />
	                             <display:column property="descTxt" title="Description" />
	                             <display:setProperty name="basic.empty.showtable" value="true"/>
	                         </display:table>
                        </logic:equal>
                        <logic:notEqual name="mode" value="print">
	                         <display:table id="attachmentsDTTable" name="nbsAttachments" class="dtTable" defaultsort="4" defaultorder="ascending">
	                         <logic:equal name="BaseForm" property="actionMode" value="View">
	                             <display:column class="iconField" property="deleteLink" title="<p style='display:none'>Select/Deselect All</p>"/>
	                             </logic:equal>
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
	                    <logic:equal name="BaseForm" property="actionMode" value="View">
	                        <input type="button" value="Add Attachment" 
	                                onclick="javascript:showAddAttachmentBlock()" />
	                                </logic:equal>
	                    </div>
	                    <div id="addAttachmentBlock">
                            <iframe src="InvFileUploadForm.do" name="addFileFrame"
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
	    </nedss:container>	    
	        
        <!-- SECTION : History (Investigation History, Notifications History) --> 
        <nedss:container id='<%= "sect_" + tabId + sectionIndex %>' 
                name="<%= sectionNames[sectionIndex++] %>" classType="sect">	        
	        <!-- SUB_SECTION : Investigation History -->
	        <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Investigation History"  classType="subSect" >
	           <tr style="background:#FFF;">
                    <td colspan="2">
                        <span id="investigationHistorySection">
                            <%= request.getAttribute("invHistoryTable") %>
                        </span>
                   </td>
               </tr>
	        </nedss:container>
	        
            <!-- SUB_SECTION : Notification History-->
            <nedss:container id="<%= tabId + (++subSectionIndex) %>" name="Notification History"  
                    classType="subSect">
               <tr style="background:#FFF;">
                    <td colspan="2">
                        <span id="notificationSection">
                            <%= request.getAttribute("notificationListTable") %>
                        </span>
                   </td>
               </tr>
            </nedss:container>
        </nedss:container>
        
      
        
	    <div class="tabNavLinks">
	        <a href="javascript:navigateTab('previous')"> Previous </a> &nbsp;&nbsp;&nbsp;
	        <a href="javascript:navigateTab('next')"> Next </a>
	    </div>
	</div>
   </td> 
</tr>
<script type="text/javascript">
  function showAddAttachmentBlock()
  {
    $j("body").find(":input").attr("disabled", "disabled");
    $j("div#addAttachmentBlock").show();
    $j("div#addAttachmentBlock").find(":input").attr("disabled", "");

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

  function resizeIFrame()
  {
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

  function cancelAttachmentBlock()
  {
    // enable all input elts
    $j("body").find(":input").attr("disabled", "");

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

  function deleteAttachment(uid)
  {
    var deleteMsg = "You have indicated that you would like to delete this attachment. By doing so, "
        + "this file will no longer be available in the system. Would you like to continue " + "with this action?";
    var choice = confirm(deleteMsg);
    if (choice) {
      JPageForm.deleteAttachment(uid, function(data)
      {
        if (data == true) {
          var toFind = "td_" + uid;
          $j("#" + toFind).parent().parent().remove();
        }
      });
    }
  }

  function loadAttachment(urlToLoad)
  {
    $j("iframe#downloadFileIFrame").attr("src", urlToLoad);
  }

  function showNotesBlock()
  {
    $j("body").find(":input").attr("disabled", "disabled");
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

  function cancelNotesBlock()
  {
    // enable all input elts
    $j("body").find(":input").attr("disabled", "");

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

  function showPrintFriendlyNotes()
  {
    var divElt = getElementByIdOrByName("pageview");
    divElt.style.display = "block";
    var o = new Object();
    o.opener = self;

    var URL = "/nbs/PageAction.do?method=printNotes&mode=print&pageId=pageview";
    var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:no;scrollbars:no;help:no;resizable:yes;max:1;min:1";
    //window.showModalDialog(URL, o, dialogFeatures);

    var modWin = openWindow(URL, o, dialogFeatures, divElt, "");
    
    return false;
  }

</script>

<script>
   <% if( request.getAttribute("TabToFocus") != null ) { %> 
      selectTab(0,7,6,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
   <%}%>
   <% if( request.getAttribute("TabtoFocusForGenericFlow") != null ) { %> 
      var tabCount =  $j('.ongletTextDis').length+1;
	  selectTab(0, tabCount , tabCount - 1, 'ongletTextEna', 'ongletTextDis', 'ongletTextErr', null, null);
   <%}%>
</script>

