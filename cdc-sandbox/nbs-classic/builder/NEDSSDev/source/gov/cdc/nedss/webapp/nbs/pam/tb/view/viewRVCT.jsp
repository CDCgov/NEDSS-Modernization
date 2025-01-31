<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1); //prevents caching at the proxy server
%>

<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Tuberculosis</title>
        <%@ include file="/jsp/resources.jsp" %>
        <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPamForm.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="TBSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="PamSpecific.js"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript">
		 
		function checkPamDelete(){
			var checkErrorConditions = getElementByIdOrByName("deleteError");
			if(checkErrorConditions!=null){
				if(checkErrorConditions.value!="")
					alert(checkErrorConditions.value);
			}
		}	 
    	 function printForm() {
    	 	document.forms[0].target="_blank";
			document.forms[0].action ="/nbs/PamAction.do?method=printLoad";
    	 }
    	 
      function deleteForm(){
    		document.forms[0].target="";     
    		var confirmMsg="You have indicated that you would like to delete this Investigation. By doing so, this record will no longer be available in the system, and all Contact Records and Interview Records that were created within this investigation will be deleted. Would you like to continue with this action?";
			if (confirm(confirmMsg)) {
		      	document.forms[0].action ="${PamForm.attributeMap.deleteButtonHref}";
			} else {
				return false;
			}
		      	
      }
       function showPrintFriendlyPage()
            {
                var divElt = getElementByIdOrByName("pamview");
                divElt.style.display = "block";
                var o = new Object();
                o.opener = self;
                
                var URL = "/nbs/PamAction.do?method=viewLoad&mode=print";
                var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
                
            	
                
            	var modWin = openWindow(URL, o,dialogFeatures, divElt, "");
            	
                return false;
	}
        function editForm() { 
                
				var notificationCheck = getElementByIdOrByName("NotificationExists");
				if(notificationCheck !=null && notificationCheck.value=='true'
					&& getElementByIdOrByName("notificationSection")!=null && getElementByIdOrByName("notificationSection").innerText.indexOf("NND")!=-1){
					var confirmMsg="A notification message request exists against this event. If you continue with this action, you may change the content of the message. Select OK to continue or Cancel to not continue.";
					if (confirm(confirmMsg)) {
			    		  document.forms[0].target="";
			    		  document.forms[0].action ="${PamForm.attributeMap.Edit}";
				    		  
					} else {
						return false;
					}					
				} else {
			    		  document.forms[0].target="";
			    		  document.forms[0].action ="${PamForm.attributeMap.Edit}";
			      }
				return true;
    	  }

		function manageAssociations(){
			var notificationCheck = getElementByIdOrByName("NotificationExists");
			if(notificationCheck !=null && notificationCheck.value=='true'){
				var confirmMsg="A notification message request exists against this event. If you continue with this action, you may change the content of the message. Select OK to continue or Cancel to not continue.";
				if (confirm(confirmMsg)) {
		    		  document.forms[0].target="";
		    		  document.forms[0].action ="${PamForm.attributeMap.ManageEvents}";
				} else {
					return false;
				}					
			} else {
		    		  document.forms[0].target="";
		    		  document.forms[0].action ="${PamForm.attributeMap.ManageEvents}";
			}
	  
		}
				
		function transferOwnership(){
   		   document.forms[0].target="";		
			document.forms[0].action ="${PamForm.attributeMap.TransferOwnership}";
		}	
		
		
		function selectTabOnSubmit()
		{
			
			 var actionMode='${fn:escapeXml(ActionMode)}';
			 var contactTabtoFocus='<%=request.getAttribute("ContactTabtoFocus")%>';
				 JPamForm.getTabId(function(data) {
					//The third tab is the case_varification tab. On submit of ADD OR EDIT RVCT, the user should be taken to the Case Verification tab
				 if(actionMode=='CREATE_SUBMIT' || actionMode=='EDIT_SUBMIT') {
					data = '2';					
				 }
				 if(contactTabtoFocus != null && contactTabtoFocus == 'ContactTabtoFocus'){
                    data = '6';                 
                 }else{
			           // Show supplementalInfo tab if popup is opened and closed from supplementalInfo tab.
			          	var tabtoFocusForGenericFlow='<%=request.getAttribute("TabtoFocusForGenericFlow")%>';
			          	if (tabtoFocusForGenericFlow != null && tabtoFocusForGenericFlow == 'TabtoFocusForGenericFlow') {
			          		data = '5';  
			          	}
			      }
					 
				 if(data!=null && data!="")
				 {
					selectTab(0,pamTabCount(),data,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
				 } else {
				 
					selectTab(0,pamTabCount(),0,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
				 }
			    });
		}
		
		function checkLoadNotif()
		{
			var reqForNotif = '<%=request.getAttribute("REQ_FOR_NOTIF")%>';
			if(reqForNotif != "null" )
			{
				createPamNotification();
			}
			else
			{
				return
			}
		}
		 function appendPatientSearch(patientRevision,caseLocalId,perMprUid )
 		{
          // var patMprUid=  "${PamForm.attributeMap.patientLocalId}";
           // alert(patMprUid);
	           //var patList = document.form[0].patNamedByContactsListID;
 			$j("table#contactNamedByPatListID").append("<tr> <td colspan=\"6\" style=\"text-align:right;\"> <input type=\"button\" name=\"submitct\" value=\"Add New Contact Record\"  onclick=\"SearchPatientPopUp('" + patientRevision + "','" + caseLocalId + "','" + perMprUid + "');\"/> </td> </tr>");
        		
 		}   
 	 function appendManageAssociation(patientRevision,caseLocalId,perMprUid )
 	 {
          // var patMprUid=  "${PamForm.attributeMap.patientLocalId}";
           // alert(patMprUid);
	           //var patList = document.form[0].patNamedByContactsListID;
  			
        		$j("table#patNamedByContactsListID").append("<tr> <td colspan=\"6\" style=\"text-align:right;\"> <input type=\"button\" name=\"submitmanage\" value=\"Manage Contact Associations\"  onclick=\"ManageCtAssociationtPopUp('" + patientRevision + "','" + caseLocalId + "','" + perMprUid+ "');\"/> </td> </tr>");
 		}   

 		
 	function reloadInvs(filler){
         	//alert(filler.value);      
 	         JPamForm.callChildForm(filler.value, function(data) { 	   
 	         }); 	         	
 	         setTimeout("reldPage()", 1000);
	  }
	  
	  function reldPage() {
 	  	  document.forms[0].action ="/nbs/LoadManageCtAssociation.do?method=manageContactsSubmit";
		  document.forms[0].submit(); 
	  }

		</SCRIPT>  
        
        <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
            table#patNamedByContactsListID tr.empty {display:none;}
            table#contactNamedByPatListID tr.empty {display:none;}
        </style>
    </head>

    <!-- FIXME : Once all the tabs are updated to have field for 2009, replace the body tag below with the
    current one. -->
    <!--
    <body class onload="startCountdown();rvctCreateLoad();errorTab();onLoadF1();onLoadF2();enableDateTherapy()" 
            onfocus="parent_disable();">
    <body class onload="startCountdown();createLegacyTIMSBox();checkPamDelete();createLegacyTIMS();">            
    -->
    <% 
      String PatientRevision = (request.getAttribute("PatientRevision") == null) ? "" : ((String)request.getAttribute("PatientRevision"));
    	String caseLocalId = (request.getAttribute("DSInvUid") == null) ? "" : ((String)request.getAttribute("DSInvUid"));
    	String perMprUid =  (request.getAttribute("DSPatientPersonUID") == null) ? "" : ((String)request.getAttribute("DSPatientPersonUID"));
    											
   
    	%>
    
    <% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
       String addPermissionString =(request.getAttribute("checkToAddContactTracing") == null) ? "" : ((String)request.getAttribute("checkToAddContactTracing"));
       String manageAssoPerm=(request.getAttribute("manageAssoPerm") == null) ? "" : ((String)request.getAttribute("manageAssoPerm"));

    // Note: include a call to close the child window and make the parent window's bg setting from gray to white.
    // since the same JSP is used for both regular display mode and printer friendly display mode, this kind of check
    // is required to prevent the window from closing itself in the regular display mode.
    if (printMode.equals("print")) { %>
        <body class onload="startCountdown();checkPamDelete(); cleanupPatientRacesViewDisplay();checkLoadNotif();disablePrintLinks();" onunload="return closePrinterFriendlyWindow();">
          <% } else if(addPermissionString.equals("false") && manageAssoPerm.equals("false") ){ %> 
        <body class onload="startCountdown();checkPamDelete();cleanupPatientRacesViewDisplay();selectTabOnSubmit();checkLoadNotif();">
  	  <% } else if(addPermissionString.equals("true") && manageAssoPerm.equals("false") ){ %> 
        <body class onload="startCountdown();checkPamDelete();cleanupPatientRacesViewDisplay();selectTabOnSubmit();checkLoadNotif();appendPatientSearch('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');">
   	   <% } else if(addPermissionString.equals("false") && manageAssoPerm.equals("true") ){ %> 
        <body class onload="startCountdown();checkPamDelete();cleanupPatientRacesViewDisplay();selectTabOnSubmit();checkLoadNotif();appendManageAssociation('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');">
         <% } else if(addPermissionString.equals("true") && manageAssoPerm.equals("true") ){ %> 
        <body class onload="startCountdown();checkPamDelete();cleanupPatientRacesViewDisplay();selectTabOnSubmit();checkLoadNotif();appendPatientSearch('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');appendManageAssociation('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');">
    	<% } %>
        <div id="pamview"></div>
        
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
            <html:form action="/PamAction.do">
					<input type="hidden" name="deleteError" value="<%= request.getAttribute("deleteError") == null ? "" : request.getAttribute("deleteError")%>"/>
                 <html:hidden property="attributeMap.NotificationExists" styleId="NotificationExists"/>            
                <!-- Body div -->
                <div id="bd">
                    <!-- Top Nav Bar and top button bar -->
                  <%@ include file="../../../jsp/topNavFullScreenWidth.jsp" %>
	              <%@ include file="../../../jsp/topbuttonbarFullScreenWidth.jsp" %>
                  <%@ include file="../../../jsp/feedbackMessagesBar.jsp" %>  
	              <%@ include file="../../patient/PatientSummary.jsp" %>  

                    <!-- Required Field Indicator -->
                    <div class="requiredFieldIndicatorLink"> 
                        <span class="boldTenRed"> * </span>
                        <span class="boldTenBlack"> Indicates a Required Field </span>  
                    </div>

                    <logic:equal name="BaseForm" property="actionMode" value="Preview">
	                    <div style="text-align:right; width:100%;"> 
	                        <span class="boldTenBlack">
									<a id="manageLink" href="javascript:history.back();">Back to Manage Tuberculosis LDFs</a>                        
	                        </span>  
	                    </div>
			</logic:equal>
		
			<!-- <% if ((String)request.getAttribute("pamShareConfMsg") != null) { %>
			  <div class="infoBox messages">
			   <%= request.getAttribute("pamShareConfMsg") %>
			  </div>    
			<% } %>  -->


                    <!-- Tab container -->
                    <layout:tabs width="100%" styleClass="tabsContainer">
							<logic:notEqual name="BaseForm" property="actionMode" value="Preview">
	                        <layout:tab key="Patient">
	                            <jsp:include page="../../patient/view/viewRVCT_Patient.jsp"/>	
	                        </layout:tab>                        
							</logic:notEqual>
                        <layout:tab key="Tuberculosis">
                            <jsp:include page="viewRVCT_Tuberculosis.jsp"/>
                        </layout:tab>
                        <layout:tab key="Case Verification" >
                             <jsp:include page="viewRVCT_CaseVerification.jsp"/>                     
                        </layout:tab>
						<layout:tab key="Follow Up 1" >
                             <jsp:include page="viewRVCT_FollowUp1.jsp"/>						
                         </layout:tab>
						 <layout:tab key="Follow Up 2" >
                             <jsp:include page="viewRVCT_FollowUp2.jsp"/>						
                         </layout:tab>
                         <layout:tab key="Supplemental Info">
									<jsp:include page="/pam/supplemental/SupplementalInformation.jsp"/> 
                         </layout:tab>
                         <logic:equal name="PamForm" property="securityMap(ContactTracingEnableInd)" value="Y">
                         <logic:equal name="PamForm" property="securityMap(checkToViewContactTracing)" value="true">
	                         <layout:tab key="Contact Tracing">
										<jsp:include page="/pam/contactTracing/view/viewContactTracing.jsp"/> 
	                         </layout:tab>
                         </logic:equal>
                         </logic:equal>
                        
                        <!-- LDF Tab-->
                        <logic:notEmpty name="PamForm" property="formFieldMap.LDFTAB">
                             <layout:tab key="${PamForm.formFieldMap.LDFTAB.label}">
                                <tr>
                                    <td>
 									     <nedss:container id="ldfSection" 
 									          name="Custom Fields" classType="sect" displayLink="false" displayImg="false">
									        <nedss:container id="ldfSubsection" name="Custom Fields" 
									               classType="subSect" displayLink="false" displayImg="false">
									           <%= request.getAttribute("LDFTAB") == null ? "" :  request.getAttribute("LDFTAB") %>   
									        </nedss:container>
									     </nedss:container>
                                    </td>
                                </tr>
                            </layout:tab>
                        </logic:notEmpty>
                    </layout:tabs>
                    
                    <!-- Bottom button bar -->
						<logic:notEqual name="BaseForm" property="actionMode" value="Preview">
	                    <%@ include file="../../../jsp/bottombuttonbarFullScreenWidth.jsp" %>
						</logic:notEqual>
	                <logic:equal name="BaseForm" property="actionMode" value="Preview">
	                    <div style="text-align:right; width:100%;"> 
	                        <span class="boldTenBlack">
									<a id="manageLink" href="javascript:history.back();">Back to Manage Tuberculosis LDFs</a>                        
	                        </span>  
	                    </div>
	                </logic:equal>
                </div> <!-- id=bd -->
            </html:form>
        </div> <!-- Container Div -->
    </body>
</html>