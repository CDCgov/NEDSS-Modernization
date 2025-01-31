<%@ include file="../../jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>

<html lang="en">
    <head>
        <title>Contact Tracing</title>
        <base target="_self">
         <%@ include file="/jsp/resources.jsp" %>
		<script language="javascript" src="/nbs/dwr/interface/JPamForm.js"></script>
		<script language="javascript" src="PamSpecific.js"></script>
		<script language="javascript">
            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
            */
            var closeCalled = false;
            function handlePageUnload(closePopup, e)
            {
                //alert("event.clientX = " + event.clientX + "; event.clientY = " + event.clientY);
                    
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
	            if (closeCalled == false) {
	                closeCalled = true;
	                
	                // Note: A check for event.clientY < 0 is required to
	                // make sure the "X" icon the top right corner of
	                // a window is cliced. i.e., Page unloads 
	                // due to edit/other link clicks withing the window frame
	                // are therefore ignored. 
                    if (e.clientY < 0 || closePopup == true) {
	                    // get reference to opener/parent           
	                    var opener = getDialogArgument();
	                    // refresh parent's form
	                    opener.document.forms[0].action ="/nbs/LoadContactTracing.do?method=Cancel";
	                    opener.document.forms[0].submit();
	                    
	                    // pass control to parent's call back handler
	                    window.returnValue = "windowClosed";
	                    
                        window.close();
                    } 
                }
            }

            function showPrintFriendlyPage()
            {
                var divElt = getElementByIdOrByName("ContactTracingLoad");
                divElt.style.display = "block";
                var o = new Object();
                o.opener = self;
             
                var URL = "/nbs/ContactTracing.do?method=viewContact&mode=print";
                var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
             
            	
                var modWin = openWindow(URL, o, dialogFeatures, divElt, "");
             
                return false;
            }
            
            function disablePrintLinks() {
                $j("a[href]:not([href^=#])").removeAttr('href');	
            }

            function handlePrinterFriendlyPageClose()
            {
                self.close();
                var opener = getDialogArgument();        
                var cview = getElementByIdOrByNameNode("ContactTracingLoad", opener.document)
                cview.style.display = "none";
		     
                return false;   
            }

            function addContactRecord()
            {
                document.forms[0].action ="/nbs/ContactTracing.do?method=AddContactSubmit";
                document.forms[0].submit();
            }

            function editForm()
            { 
                document.forms[0].action ="/nbs/ContactTracing.do?method=editContactLoad";
                document.forms[0].submit();				
            }

            function loadPage()
            { 
              var jurisd = getElementByIdOrByName("CON134");
		    var NBSSecJurisdictionParseString = getElementByIdOrByName("NBSSecurityJurisdictions");
		    var items = NBSSecJurisdictionParseString.value.split("|");
		    var containsJurisdiction = false;
		    if (items.length > 1) {
		        for (var i=0; i < items.length; i++)
		        {
		            if (items[i]!=""  && items[i] == jurisd.value ) {
		                containsJurisdiction = true;
		            }
		        }
		    }
		    if(!containsJurisdiction) {
		          handlePageUnload(true);
		     }
	     }

            
            function deleteContact(phcUid) {
            	var confirmMsg="You have indicated that you would like to delete this Contact Record. By doing so, this record will no longer be available in the system and all notes and attachments related to this Contact will be deleted. Would you like to continue with this action?";
        		if (confirm(confirmMsg)) {
        			var opener = getDialogArgument();
                    var del = getElementByIdOrByName("delete").value;
                    opener.deleteContact(del, phcUid);
                    var pview = getElementByIdOrByNameNode("pamview", opener.document);
                     if (pview == null) {
                        pview = getElementByIdOrByNameNode("pageview", opener.document);                   
                    }                   
                    if (pview == null) {
                        pview = getElementByIdOrByNameNode("blockparent", opener.document);                   
                    }
                    pview.style.display = "none";
                    
                    window.close();
        		}
        		else
        			return false;
            }
        </script>
        <style type="text/css">
            body.popup div.popupTitle {width:100%; background:#185394; padding:3px; color:#FFF; text-align:left; font-size:110%; font-weight:bold;}	
            body.popup div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
            table.searchTable {width:98%; margin:0 auto; margin-top:1em; border-spacing:4px; margin-bottom:5px; margin-top:5px;}
        </style>
	</head>
	
	<%
	   String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode")); 
       if (printMode.equals("print")) { %>
	       <body class="popup" onload="shiftFocusToFirstTabElement();disablePrintLinks();addRolePresentationToTabsAndSections();" onunload="return handlePrinterFriendlyPageClose();">
	   <% } else { %>
	       <body class="popup" onload="shiftFocusToFirstTabElement();loadPage();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;">
        <% } 
    %>
	
	<div id="ContactTracingLoad" class="grayBackground"> </div>
        <html:form>
		<!-- Page title -->
        <div class="popupTitle">${fn:escapeXml(BaseForm.pageTitle)}</div>
        
          <%
          
          String phcUID = HTMLEncoder.encodeHtml((String) request.getAttribute("phcUID"));
          
          %>
        
        <!-- Top button bar -->
      	<div class="popupButtonBar">
	      	 <logic:equal name="contactTracingForm" property="securityMap(editContactTracingPermission)" value="true">
	            <input type="submit" name="Edit" value="Edit" onclick="editForm()"/>
	         </logic:equal>          
         	 <input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlyPage();"/>
	         <logic:equal name="contactTracingForm" property="securityMap(deleteContactTracingPermission)" value="true">
	         	<input type="submit" name="Delete" id="delete" value="Delete" onclick="return deleteContact('<%=phcUID%>');" />
	         </logic:equal> 
            <input type="button" name="Cancel" value="Close" onclick="handlePageUnload(true, event)" />
        </div>
        
        <!-- Tool bar for print friendly mode -->
        <div class="printerIconBlock screenOnly">
		    <table role="presentation" style="width:98%; margin:3px;">
		        <tr>
		            <td style="text-align:right; font-weight:bold;"> 
		                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
		            </td>
		        </tr>
		    </table>
		</div>
        
        <!-- Indicates Required field -->
        <div align="right"  style="padding-top: 8px;">
            <i>
                <font class="boldTenRed" > * </font><font class="boldTenBlack">Indicates a Required Field </font>
            </i>
        </div>
        
	   	<!-- ContactRecord Summary table-->
		<%@ include file="/contacttracing/ContactTracingSummary.jsp" %>
		
		<!--Layout Tabs -->
		<div style="width:100%; padding:1em;">
			<layout:tabs width="100%" styleClass="tabsContainer">
				<layout:tab key="Contact">
	                <jsp:include page="/contacttracing/viewContactrecord/View_Contact_patient.jsp"/> 
	            </layout:tab>  
				<layout:tab key="Contact Record">
	                 <jsp:include page="/contacttracing/viewContactrecord/View_ContactRecord.jsp"/>           
	            </layout:tab>  
	            <layout:tab key="Contact Follow Up">
	                 <jsp:include page="/contacttracing/viewContactrecord/View_ContactFollowUp.jsp"/>     
	            </layout:tab>
	            <layout:tab key="Supplemental Info">
	                 <jsp:include page="/contacttracing/viewContactrecord/View_SupplementalInfo.jsp"/>     
	            </layout:tab>
			 </layout:tabs>
		 </div>
		 
		 <!-- Bottom button bar -->
      	<div class="popupButtonBar">
             <logic:equal name="contactTracingForm" property="securityMap(editContactTracingPermission)" value="true">
	            <input type="submit" name="Edit" value="Edit" onclick="editForm()"/>
	         </logic:equal>          
         	 <input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlyPage();"/>
	         <logic:equal name="contactTracingForm" property="securityMap(deleteContactTracingPermission)" value="true">
	         	<input type="submit" name="Delete" value="Delete" onclick="return deleteContact('<%=phcUID%>');" />
	         </logic:equal> 
            <input type="button" name="Cancel" value="Close" onclick="handlePageUnload(true, event)" />
        </div>
	  </html:form>	
	</body>
</html>