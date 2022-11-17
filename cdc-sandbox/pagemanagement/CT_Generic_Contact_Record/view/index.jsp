<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN VIEW CONTACT INDEX JSP PAGE GENERATE ###- - -->
    <html lang="en">
    <head>
     <%@ page import="java.util.*" %>
     <%@ include file="/jsp/tags.jsp" %>
    <title>View Contact Tracing</title>
    <%@ include file="/jsp/resources.jsp" %>  
    <script language="JavaScript" src="Coinfection.js"></script>
     <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JCTContactForm.js"></SCRIPT>
        <script language="JavaScript"> 
              
     
             var closeCalled = false;
             function handlePageUnload(closePopup, e)
             {                         
     		// This check is required to avoid duplicate invocation 
     		// during close button clicked and page unload.
     		if (closeCalled == false) {
          	                closeCalled = true;
          	                
          	                // Note: A check for event.clientY < 0 is required to
          	                // make sure the "X" icon the top right corner of
          	                // a window is clicked. i.e., Page unloads 
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
         
            function deleteContact(phcUid) {
            	var deleteContactInvCheck = "${contactTracingForm.attributeMap.DeleteStdContactInvCheck}";
                if (deleteContactInvCheck != null && deleteContactInvCheck == "FF") {
                	alert("This Contact Record cannot be deleted because an investigation was initiated from it. To delete this Contact Record, first delete the investigation created from the Field Follow-up from this Contact Record.");
                	return false;
                }
            
            	var confirmMsg="You have indicated that you would like to delete this Contact Record. By doing so, this record will no longer be available in the system and all notes and attachments related to this Contact will be deleted. Would you like to continue with this action?";
        		if (confirm(confirmMsg)) {

                    var opener = getDialogArgument(); 
                    var del =getElementByIdOrByName("delete").value;
                    opener.deleteContact(del, phcUid);
                    var pview = opener.document.getElementById("pageview");
                    if (pview == null) {
                        pview = opener.document.getElementById("pamview");                   
                    }
                    if (pview == null) {
                        pview = opener.document.getElementById("blockparent");                   
                    }
                    pview.style.display = "none";
                    
                    window.close();
        		}
        		else
        			return false;
            }
      


      /** Popup a child window and load the page that is currently being 
       *   viewed on the parent window. The call to load the page includes an additional 
       *   parameter called 'mode' that has a value of print. This value is used to load
       *   a seperate css file named 'print.css' when the page loads in the child window.
       */
       
            
            
            function showPrintFriendlyPage()
            {
                var divElt =getElementByIdOrByName("pageview");
                divElt.style.display = "block";
                var o = new Object();
                o.opener = self;
             
                var URL = "/nbs/ContactTracing.do?method=viewContact&mode=print";
                var dialogFeatures = "dialogWidth:780px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
                

             var modWin = openWindow(URL, o,dialogFeatures, divElt, "");
                return false;
            }
         
	function checkPageDelete(){
			var checkErrorConditions =getElementByIdOrByName("deleteError");
			if(checkErrorConditions!=null){
				if(checkErrorConditions.value!="")
					alert(checkErrorConditions.value);
			}
	}	 

    	function disablePrintLinks() {
                $j("a[href]:not([href^=#])").removeAttr('href');	
    	}

    	function closePrinterFriendlyWindow()
            {
                self.close();
                var o = window.dialogArguments;
                var opener = o.opener;          
                var cview = opener.document.getElementById("PageView");
                if (cview != null)
                	cview.style.display = "none";
		     
                return false;   
            }
    	function printForm() {
	            document.forms[0].target="_blank";
	            document.forms[0].action ="/nbs/PageAction.do?method=printLoad";
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
	 
        
	 	

        function selectTabOnSubmit()
	{     
		var contactTabtoFocus='<%=request.getAttribute("ContactTabtoFocus")%>';
		if(contactTabtoFocus != null && contactTabtoFocus == 'ContactTabtoFocus'){
			var tabCount =  $j('.ongletTextDis').length + 1; //only one enabled, rest disabled
			//alert("number of tabs -  = " + tabCount); //go to Contact Record tab
			selectTab(0,tabCount-1,tabCount-2,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
		}

	}	
	function stdSpecialViewProcessing() {
	  // see if Named is present
	  var elementId = 'CON143';
	  var namedElement =getElementByIdOrByName(elementId);
	  if (namedElement == null || typeof(namedElement) == 'undefined') 
	    	return;
	   var con143Value = "${contactTracingForm.attributeMap.StdInterviewSelectedValue}";
	   if (con143Value != null) {
	   	$j('#CON143').text(con143Value);
	   }
        }

         
          </script>  
     
       
                       <% 
                        Map map = new HashMap();
                        if(request.getAttribute("SubSecStructureMap") != null){
							  map =(Map)request.getAttribute("SubSecStructureMap");
                      }%>
                     
               
  <script language="JavaScript"> 
	      
                    var answerCache = { };
                		var viewed = -1,count=0;   
                    
function populateBatchRecords()
{
   dwr.engine.beginBatch();
   var map,ans;          
   JCTContactForm.getBatchEntryMap(function(map) {
        for (var key in map) {
               count++;    
               fillTable(key,"pattern"+key ,"questionbody"+key );				
        } 			             		     
   }); 	
   dwr.engine.endBatch();
}		  
                		
function fillTable(subSecNm,pattern,questionbody) {
	JCTContactForm.getAllAnswer(subSecNm,function(answer) {
		// Delete all the rows except for the "pattern" row
	    dwr.util.removeAllRows(questionbody, { filter:function(tr) { return (tr.id != pattern); }});					
		dwr.util.setEscapeHtml(false);		
	    // Create a new set cloned from the pattern row -gt
	    var ans, id,rowclass="";
	<%  if(map !=  null){
		Iterator    itLab3 = map.entrySet().iterator(); 
		String[][] batchrecview  = null;			   	 
		while(itLab3.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab3.next();%>
		if(subSecNm == "<%=pair.getKey().toString()%>"){
		<% batchrecview =  (String[][])pair.getValue();%>   
		//alert("answer.length-" + answer.length);
		if(answer.length != 0){
			for (var i = 0; i < answer.length; i++){
				ans = answer[i];	
				//alert( answer[i])	; 
				id = ans.id;	     
				dwr.util.cloneNode(pattern, { idSuffix:id });    	           
				// alert("id = "+id);
				<% for(int i=0;i<batchrecview.length;i++){   
					String checknull = batchrecview[i][0];
					if (batchrecview[i][0] == null) continue; %>                    
				
				for (var key in ans.answerMap) {
					if(!(key == null || key == 'null') && key == "<%=batchrecview[i][0]%>"){
						var val = ans.answerMap[key];
						<%if( "1017".equalsIgnoreCase(batchrecview[i][5]) ){%>
                            val = ans.answerMap[key + "Disp"] ? ans.answerMap[key + "Disp"] : val;
                         <%}%>
						val = repeatingBlockFillValue(val);
						dwr.util.setValue("table" + key + id, val);	
					}
				} 
			<%}%>
			$(pattern + id).style.display = "";   
			answerCache[id] = ans;   
			if(rowclass=="")
				rowclass="odd";
				document.getElementById(pattern  + id).setAttribute("className",rowclass);
				if(rowclass=="odd"){
					// rowclass = "even";
					rowclass = "odd";
				} else if(rowclass=="even"){
					rowclass = "odd";
				} 				                     	   
			}
			$j("#no"+pattern).hide(); 
		} else{				
			$j("#no"+pattern).show();
		}  //if else answer.length ==0 ends 
				
		}
				
	<%}}%>		
	}); 		 
} //fillTable	

                		
function deleteClicked(eleid,subSecNm,pattern,questionbody) {
     //alert(eleid); 		
     // we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
     var answer = answerCache[eleid.substring(6)];
     if (confirm("You have indicated that you would like to delete this row. Would you like to continue with this action?")) {
        dwr.engine.beginBatch();
        PageForm.deleteAnswer(answer);
        fillTable(subSecNm,pattern,questionbody);
        dwr.engine.endBatch();
     }
} 		
function clearQuestion() {
     viewed = -1;
     dwr.util.setValues({subsecNm:"Others", id:viewed,answerMap:null });
}
                	        
function getDropDownValues(newValue) {
	//alert(newValue);
    	JCTContactForm.getDropDownValues(newValue, function(data) {
        	dwr.util.removeAllOptions(newValue);  
        	dwr.util.addOptions(newValue,data,"key","value"); 		       
        });
}
                  
function viewClicked(eleid,subSecNm) {		               		    
	var key;
	var answer = answerCache[eleid.substring(4)];
	<% String[][] batchrecview  = null;
	   if(map != null) {
		    Iterator  itLab2 = map.entrySet().iterator(); 
		    String[][] batchrecedit  = null;
		    while(itLab2.hasNext()){  
		       Map.Entry pair = (Map.Entry)itLab2.next();%>

			if(subSecNm == "<%=pair.getKey().toString()%>"){  
			    <% batchrecview =  (String[][])pair.getValue();   
			    for(int i=0;i<batchrecview.length;i++){
					if (batchrecview[i][0] == null) continue;
					String strQuesId = batchrecview[i][0];%>
					dwr.util.setValue( "<%=strQuesId%>", "");
					dwr.util.setValue("<%=strQuesId%>"+"Oth", "");
		                        dwr.util.setValue("<%=strQuesId%>"+"UNIT", "");
		            <%}%>		 			
			for (var key in answer.answerMap) {   
				       // alert(key); alert(answer.answerMap[key]);
				var val = answer.answerMap[key];
				dwr.util.setValue(key+"Oth", "");
				if(val != null && val.indexOf("||") != -1){
					var  mulVal ;
					var othVal ;
					val = val.substring(0, val.length-2);	
					if(val.indexOf("||") != -1){
						mulVal  =  val.substring(0, val.indexOf("||"));
						val = val.substring(val.indexOf("||")+2);
						if(mulVal.indexOf("$MulOth$") != -1){
                               				mulVal   = mulVal.substring(mulVal.indexOf("$$")+2, mulVal.indexOf("$MulOth$"));  
                               				othVal =  mulVal.substring(mulVal.indexOf("$MulOth$")+8, mulVal.indexOf("#MulOth#"));
                               			}else{		
							mulVal   = mulVal.substring(mulVal.indexOf("$$")+2, mulVal.length);  
						}             
					}
					while(val.indexOf("||") != -1){
						var val1 =  val.substring(0, val.indexOf("||"));
						if(val1.indexOf("$MulOth$") != -1){
                         				mulVal  = mulVal  +","+ val1.substring(val1.indexOf("$$")+2, val1.indexOf("$MulOth$"));  
                         				othVal =  val1.substring(val1.indexOf("$MulOth$")+8, val1.indexOf("#MulOth#"))
                         			}else{	
                         				mulVal  = mulVal  +","+  val1.substring(val1.indexOf("$$")+2, val1.length); 
						}   
						val = val.substring(val.indexOf("||")+2);
					}			   
					if(mulVal != '' && mulVal != 'undefined' && mulVal != null){
						if(val.indexOf("$MulOth$") != -1){
                            				mulVal  = mulVal  +","+ val.substring(val.indexOf("$$")+2, val.indexOf("$MulOth$"));  
                            				othVal =  val.substring(val.indexOf("$MulOth$")+8, val.indexOf("#MulOth#"));
                            			}else{	
                            				mulVal  = mulVal  +","+  val.substring(val.indexOf("$$")+2, val.length);	
						}   	
					}else{
						if(val.indexOf("$MulOth$") != -1){
                        				mulVal  =  val.substring(val.indexOf("$$")+2, val.indexOf("$MulOth$"));  
                        				othVal =  val.substring(val.indexOf("$MulOth$")+8, val.indexOf("#MulOth#"));
                        			}else{	
                        				mulVal  =  val.substring(val.indexOf("$$")+2, val.length);
                        			} 
					}
					val = mulVal ;
					mulVal  = null;
					dwr.util.setValue(key,val);
                                        if(getElementByIdOrByName(key+"Oth") != null){
			                               dwr.util.setValue(key+"Oth",othVal); 			
			                }
				}  else if(val != null && val.indexOf(":") != -1 && val.indexOf("$$") != -1 && val.indexOf("OTH") != -1){
					dwr.util.setValue(key,val.substring(val.indexOf("$$")+2,val.indexOf(":")));
					dwr.util.setValue(key+"Oth", val.substring(val.indexOf(":")+1));
				} else if(val != null && val.indexOf("$$") != -1){
					val = val.substring(val.indexOf("$$")+2, val.length);
					dwr.util.setValue(key,val); 
					}
				else if(val != null && val.indexOf("$sn$") != -1){
					val = val.substring(0,val.indexOf("$sn$")) + ' ' +val.substring(val.indexOf("$val$")+5, val.length);
					dwr.util.setValue(key,val);
				}else if(val == null){
					val ="";  
					dwr.util.setValue(key,val); 
				} else {
					dwr.util.setValue(key,val);
				}
			}

		}
		<%}}%>
} //viewClicked
          
               
     
     </script>
        <style type="text/css">
            body.popup div.popupTitle {width:100%; background:#185394; padding:3px; color:#FFF; text-align:left; font-size:110%; font-weight:bold;}	
            body.popup div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
            table.searchTable {width:98%; margin:0 auto; margin-top:1em; border-spacing:4px; margin-bottom:5px; margin-top:5px;}
        </style>
    </head>
         
     <% 
    int subSectionIndex = 0;

    String tabId = "";   


      String [] sectionNames  = {"Patient Information","Contact Record","Contact Follow Up"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";    
    
        String PatientRevision = (request.getAttribute("PatientRevision") == null) ? "" : ((String)request.getAttribute("PatientRevision"));
        String caseLocalId = (request.getAttribute("DSInvUid") == null) ? "" : ((String)request.getAttribute("DSInvUid"));
       	String perMprUid =  (request.getAttribute("DSPatientPersonUID") == null) ? "" : ((String)request.getAttribute("DSPatientPersonUID"));									
       
  %>       


  <% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
    // Note: include a call to close the child window and make the parent window's bg setting from gray to white.
    // since the same JSP is used for both regular display mode and printer friendly display mode, this kind of check
    // is required to prevent the window from closing itself in the regular display mode.
    if (printMode.equals("print")) { %>
        <body class="popup" onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();disablePrintLinks();populateBatchRecords();stdSpecialViewProcessing();addTabs();addRolePresentationToTabsAndSections();" onunload="return closePrinterFriendlyWindow();selectTabOnSubmit();">
    <% } else { %> 
        <body class="popup" onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();selectTabOnSubmit();populateBatchRecords();stdSpecialViewProcessing();addTabs();addRolePresentationToTabsAndSections();" onunload="handlePageUnload(false, event); return false;">
    <% } %>

        <div id="pageview"></div>        
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
            <html:form action="/ContactTracing.do">
            	<input type="hidden" name="deleteError" value="<%= request.getAttribute("deleteError") == null ? "" : request.getAttribute("deleteError")%>"/>
	             <div class="popupTitle"> ${BaseForm.pageTitle} <a name=pageTop></a></div>
	              <%String phcUID = (String) request.getAttribute("phcUID");%>

         <!-- Top button bar -->
         
      	<div class="popupButtonBar">
	      	 <logic:equal name="contactTracingForm" property="securityMap(editContactTracingPermission)" value="true">
	            <input type="submit" name="Edit" value="Edit" onclick="editForm()"/>
	         </logic:equal>          
         	 <input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlyPage();"/>
	         <logic:equal name="contactTracingForm" property="securityMap(deleteContactTracingPermission)" value="true">
	         	<input type="submit" name="Delete" id="delete" value="Delete" onclick="return deleteContact('<%=phcUID%>');" />
	         </logic:equal>
	         <logic:equal name="contactTracingForm" property="attributeMap.COINFECTION_INV_EXISTS" value="true">
	         	<bean:define id="showAssociateInvestigationBtn" value="${showAssociateInvestigationBtn}" />
				<logic:equal name="showAssociateInvestigationBtn" value="true">
                    <input type="button" style="width:20%;" name="AssociateInvestigation" value="Associate Investigations" onclick="ManageContactInvestigationAssociations(${subjectEntityPHCUid})"/>
             	</logic:equal>       
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
	<!-- Body div -->
         <div id="bd">
                       <!-- Page Errors -->
                       <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
                       
                        <!-- Patient summary -->
                         <%@ include file="/contacttracing/ContactTracingSummary.jsp" %> 
                                             
                        <!-- Required Field Indicator -->
                        <div style="text-align:right; width:100%;"> 
                            <span class="boldTenRed"> * </span>
                            <span class="boldTenBlack"> Indicates a Required Field </span>  
                        </div> 
                
                        
      
     <!-- ################### PAGE TAB ###################### - - -->
      <layout:tabs width="100%" styleClass="tabsContainer">
             
       		
        
	  	
         <layout:tab key="Contact">     
        <jsp:include page="viewContact.jsp"/>
        </layout:tab>  
        
              
       		
        
	  	
         <layout:tab key="Contact Record">     
        <jsp:include page="viewContactRecord.jsp"/>
        </layout:tab>  
        
              
       		
        
	  	
         <layout:tab key="Contact Follow Up">     
        <jsp:include page="viewContactFollowUp.jsp"/>
        </layout:tab>  
        
           
           
			<layout:tab key="Supplemental Info">
			       <jsp:include page="/contacttracing/viewContactrecord/View_SupplementalInfo.jsp"/> 
			 </layout:tab>    
          </layout:tabs>  
          
          <div class="popupButtonBar">
             	<logic:equal name="contactTracingForm" property="securityMap(editContactTracingPermission)" value="true">
	            <input type="submit" name="Edit" value="Edit" onclick="editForm()"/>
	         </logic:equal>          
         	 <input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlyPage();"/>
	         <logic:equal name="contactTracingForm" property="securityMap(deleteContactTracingPermission)" value="true">
	         	<input type="submit" name="Delete" value="Delete" onclick="return deleteContact('<%=phcUID%>');" />
	         </logic:equal> 
             <logic:equal name="contactTracingForm" property="attributeMap.COINFECTION_INV_EXISTS" value="true">
                <bean:define id="showAssociateInvestigationBtn" value="${showAssociateInvestigationBtn}" />
				<logic:equal name="showAssociateInvestigationBtn" value="true">
                    <input type="button" style="width:20%;" name="AssociateInvestigation" value="Associate Investigations" onclick="ManageContactInvestigationAssociations(${subjectEntityPHCUid})"/>
                </logic:equal>
             </logic:equal>    
	         
            <input type="button" name="Cancel" value="Close" onclick="handlePageUnload(true, event)" />
        </div>
	</html:form>
      </div> <!-- Container Div -->
    </body>
     
</html>

