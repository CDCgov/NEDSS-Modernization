<%@ include file="../../jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>

<html lang="en">
	<head>
		<title>Contact Tracing</title>
		<base target="_self">
		<%@ include file="/jsp/resources.jsp" %>
		
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPamForm.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JCTContactForm.js"></SCRIPT>
		<SCRIPT Language="JavaScript" Src="contactTracingSpecific.js"></SCRIPT>
		<script language="JavaScript">
			$j(document).ready(function() {
				$j("div#childPopupContent").show();
				$j("div#pageLoadingMessage").hide();
				shiftFocusToFirstTabElement();
				autocompTxtValuesForJSP();
				ctCreateLoad('${PamForm.attributeMap.selectEltIdsArray}');			
			});
			
	
            function addContactRecord()
            {
	            var jurisd = getElementByIdOrByName("CON134");
	            var validJurisdiction=	validateContactJurisdiction();
	            
	            if(!validJurisdiction)
	            return false;
	  	  		var method="${contactTracingForm.attributeMap.method}";  
	                if(method=="AddContactSubmit") {
	                    document.forms[0].action ="/nbs/ContactTracing.do?method=AddContactSubmit";
	                    document.forms[0].submit();
	                }
	                else {
	                    document.forms[0].action ="/nbs/ContactTracing.do?method=editContactSubmit";
	                    document.forms[0].submit();
	                }
	
	                 var msg = '<div class="submissionStatusMsg"> <div class="header"> Legacy Contact Record </div>' +  
		             '<div class="body"> Please wait...  The system is loading the requested page. </div> </div>';         
					 $j.blockUI({  
			            message: msg,  
			     		css: {  
			               top:  ($j(window).height() - 100) /2 + 'px', 
			     		   left: ($j(window).width() - 500) /2 + 'px', 
			     			width: '500px'
			     	    }  
				     });
            }

            /**
                handlePageUnload(). This function is used to close the current popup window. While doing so,
                it refreshes the parent that called it.
            */
            var closeCalled = false;
            function handlePageUnload(closePopup, e)
            {
                // This check is required to avoid duplicate invocation 
                // during close button clicked and page unload.
                if (closeCalled == false) {
                    closeCalled = true;
                    
                    if (e.clientY < 0 || closePopup == true) {
	                    // pass control to parent's call back handler
	                    var opener = getDialogArgument();
                        
                        // refresh parent's form
                        opener.document.forms[0].action ="/nbs/LoadContactTracing.do?method=Cancel";
                        opener.document.forms[0].submit();
	                    window.returnValue = "windowClosed";
                        window.close();
	                }
                }
            }
        </script>
		<style type="text/css">
            body.popup div.popupTitle {width:100%; background:#185394; padding:3px; color:#FFF; text-align:left; font-size:110%; font-weight:bold;}	
            body.popup div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
            table.searchTable {width:98%; margin:0 auto; margin-top:1em; border-spacing:4px; margin-bottom:5px; margin-top:5px;}
        </style>
    </head>
	<body class="popup" onunload="handlePageUnload(false, event);addRolePresentationToTabsAndSections(); return false;">
        <div id="pageLoadingMessage" 
                style="color:green; font-size:13px; width:100%; height:100px; text-align:center; vertical-align:center;">
            Please wait while the page finishes loading...
        </div>
        
		<div id="childPopupContent" style="display:none;">
            <div id="ContactTracingLoad"> </div>
            <html:form>
            <!-- Page title -->
	        <div class="popupTitle">${fn:escapeXml(BaseForm.pageTitle)}</div>
	        
            <%
            
            String phcUID = HTMLEncoder.encodeHtml((String) request.getAttribute("phcUID"));
            
            %>
	        <!-- Top button bar -->
	      	<div class="popupButtonBar">
	            <input type="button" name="Submit" value="Submit" onclick="addContactRecord();"/>
	            <input type="button" name="Cancel" value="Cancel" onclick="handlePageUnload(true, event)" />
	        </div>
	        
	        <!-- Indicates Required field -->
	        <div align="right"  style="padding-top: 8px;">
	            <i>
	                <font class="boldTenRed" > * </font><font class="boldTenBlack">Indicates a Required Field </font>
	            </i>
	        </div>
	        
            <!-- Page Errors -->
            <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>
            	        
			<!-- ContactRecord Summary table-->
            <%@ include file="/contacttracing/ContactTracingSummary.jsp" %>  
            <div> <tr><td>&nbsp;</td></tr> </div>
	            
			<!--Layout Tabs -->
			<div style="width:100%; padding:1em;">
				<layout:tabs width="100%" styleClass="tabsContainer">
					<layout:tab key="Contact">
		                <jsp:include page="/contacttracing/contactrecord/Contact_Patient.jsp"/> 
		            </layout:tab>  
					<layout:tab key="Contact Record">
		                 <jsp:include page="/contacttracing/contactrecord/ContactRecord.jsp"/>           
		            </layout:tab>  
		            <layout:tab key="Contact Follow Up">
		                 <jsp:include page="/contacttracing/contactrecord/ContactFollowUp.jsp"/>     
		            </layout:tab>
		            <layout:tab key="Supplemental Info">
                         <jsp:include page="/contacttracing/contactrecord/SupplementalInfo.jsp"/>     
                    </layout:tab>    
				 </layout:tabs>
			 </div>
			 
			 <!-- Bottom button bar -->
	  	    <div class="popupButtonBar">
		        <input type="button" name="Submit" value="Submit" onclick="addContactRecord()"/>
		        <input type="button" name="Cancel" value="Cancel" onclick="handlePageUnload(true, event)" />
		    </div>
		  </html:form>	
        </div>
	</body>
</html>