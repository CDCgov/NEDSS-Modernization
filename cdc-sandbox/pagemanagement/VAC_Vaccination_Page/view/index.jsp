<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN VIEW GENERIC INDEX JSP PAGE GENERATE ###- - -->
    <html lang="en">
    <head>
    <title>NBS: Window</title>
    <%@ page isELIgnored ="false" %>
    <%@ page import="java.util.*" %>
    <%@ include file="/jsp/tags.jsp" %>
    <%@ include file="/jsp/resources.jsp" %>        
    <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>
	<script language="JavaScript" src="Coinfection.js"></script>
    <script language="JavaScript"> 
          
     
	
	 var closeCalled = false;
	 var editCalled = false;
    function handleWindowUnload(closePopup, e)
    {                         
     		// This check is required to avoid duplicate invocation during close button clicked and page unload.
     		if (closeCalled == false) {
				closeCalled = true;
				
				// Note: A check for event.clientY < 0 is required to
				// make sure the "X" icon the top right corner of
				// a window is clicked. i.e., Page unloads 
				// due to edit/other link clicks withing the window frame
				// are therefore ignored. 
				
				if(typeof event=='undefined')
					if(e!=null && e!=undefined)
						event=e;
					
                if ((event.clientY < 0 || closePopup == true) && (editCalled == false)) {
					// pass control to parent's call back handler
					// refresh parent's form
					// get reference to opener/parent 
				  var o = window.dialogArguments;
				  var opener;
				  if (o != null) {
					opener = o.opener;
				  } else {
					opener = window.opener;
				  }
				  opener.document.forms[0].action ="/nbs/PageAction.do?method=closeWindow";
				  opener.document.forms[0].submit();          	                    
				  window.returnValue = "windowClosed"; 
				  window.close();
                } 
     		}
    }
         
	function cancelView(event)
	{
	   handleWindowUnload(true, event);
	}
	
	<logic:equal name="PageForm" property="businessObjectType" value="IXS">
	function deleteInterview() {
	  var confirmMsg="You have indicated that you would like to delete this Interview. Would you like to continue with this action?";
	  if (confirm(confirmMsg)) {
		_submitCloseWindow();
	  } else return(false);
	}
	</logic:equal>
	<logic:equal name="PageForm" property="businessObjectType" value="VAC">
	function deleteVaccination(actUid) {
		  var confirmMsg="You have indicated that you would like to delete this Vaccination. Would you like to continue with this action?";
		  if (confirm(confirmMsg)) {
		  	  var businessObjType = 'VAC';
			  JPageForm.checkAssociationBeforeDelete(actUid, businessObjType, function(data){
			  	var msg = data[0];
			  	if(msg !=null && msg !=""){
			  		alert(msg);
			  	}else{
			  		_submitCloseWindow();
			  	}
			  	
			  });
		  } else {
		    return(false);
		  }
		  return(false);
	}
	
	function editVaccinationForm(actUid){
		var msg = "";
		var confirmMsg = "A notification message request exists against this event. If you continue with this action, you may change the content of the message. Select OK to continue or Cancel to not continue.";
		var businessObjType = 'VAC';
		  JPageForm.checkForExistingNotificationsByCdsAndUid(actUid, businessObjType, function(data){
		  	msg = data[0];
		  	if(msg !=null && msg !=""){
			    if (confirm(confirmMsg)) {
					editCalled = true;
					document.forms[0].action = "<%= request.getAttribute("genericViewEditUrl")%>";
					document.forms[0].submit();
			    } else {
					return(false);
			    }
			}else{
				editCalled = true;
				document.forms[0].action = "<%= request.getAttribute("genericViewEditUrl")%>";
				document.forms[0].submit();
		  	}
		  });
			
		return(false);
	}
	
	</logic:equal>
	
	function _submitCloseWindow(){

	  var opener = getDialogArgument();
      // refresh parent's form
	  opener.document.forms[0].action ="<%= request.getAttribute("genericViewDeleteUrl")%>";
	  opener.document.forms[0].submit();

	  // pass control to parent's call back handler
	  window.returnValue = "windowClosed";        
	  window.close();
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
	 
		var URL = "/nbs/PageAction.do?method=viewGenericLoad&mode=print";
		var dialogFeatures = "dialogWidth:780px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
					
	    var modWin = openWindow(URL, o,dialogFeatures, divElt, "");
					
		return false;
	}
         
	function checkPageDelete() {
		var checkErrorConditions =getElementByIdOrByName("deleteError");
		if (checkErrorConditions != null) {
			if (checkErrorConditions.value != "")
				alert(checkErrorConditions.value);
		}
	}

	function disablePrintLinks() {
		$j("a[href]:not([href^=#])").removeAttr('href');
	}

	function closePrinterFriendlyWindow() {
		self.close();

		var opener = getDialogArgument(); 
		var cview = opener.document.getElementById("PageView")
		if (cview != null)
			cview.style.display = "none";

		return false;
	}
	function printForm() {
		document.forms[0].target = "_blank";
		document.forms[0].action = "/nbs/PageAction.do?method=printLoad";
	}

	function editForm() {
		editCalled = true;
		document.forms[0].action = "<%= request.getAttribute("genericViewEditUrl")%>";
		document.forms[0].submit();
	}
	function selectTabOnSubmit() {
		var contactTabtoFocus = '<%=request.getAttribute("ContactTabtoFocus")%>';
		if (contactTabtoFocus != null && contactTabtoFocus == 'ContactTabtoFocus') {
			var tabCount = $j('.ongletTextDis').length + 1; //only one enabled, rest disabled
			//alert("number of tabs -  = " + tabCount); //go to Contact Record tab
			selectTab(0, tabCount - 1, tabCount - 2, 'ongletTextEna', 'ongletTextDis', 'ongletTextErr', null, null);
		} 
	}

         
          </script>  
     
       
<% 
    String sb = "";
    Map map = new HashMap();
    if(request.getAttribute("SubSecStructureMap") != null){
		map =(Map)request.getAttribute("SubSecStructureMap");
    }
%>
                     
    
    <script language="JavaScript"> 
            
		var answerCache = { };
		var viewed = -1,count=0;   
                    
function populateBatchRecords()
{
   dwr.engine.beginBatch();
   var map,ans;          
   JPageForm.getBatchEntryMap(function(map) {
        for (var key in map) {
               count++;    
               fillTable(key,"pattern"+key ,"questionbody"+key );				
        } 			             		     
   }); 	
   dwr.engine.endBatch();
}		  
                		
function fillTable(subSecNm,pattern,questionbody) {
	JPageForm.getAllAnswer(subSecNm,function(answer) {
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
    	JPageForm.getDropDownValues(newValue, function(data) {
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


      String [] sectionNames  = {"Patient Information","Vaccination"};
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
        <body class="popup" onload="startCountdown();checkPageDelete();disablePrintLinks();populateBatchRecords();cleanupPatientRacesViewDisplay();addTabs();addRolePresentationToTabsAndSections();" onunload="return closePrinterFriendlyWindow();selectTabOnSubmit();">
    <% } else { %> 
        <body class="popup" onload="startCountdown();checkPageDelete();selectTabOnSubmit();populateBatchRecords();cleanupPatientRacesViewDisplay();addTabs();addRolePresentationToTabsAndSections();" onunload="handleWindowUnload(); return false;">
    <% } %>

        <div id="pageview"></div>        
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
            <html:form action="/PageAction.do">
            	<input type="hidden" name="deleteError" value="<%= request.getAttribute("deleteError") == null ? "" : request.getAttribute("deleteError")%>"/>
	            <div class="popupTitle"> ${BaseForm.pageTitle} </div>

         <!-- Top button bar -->
         
		<div class="popupButtonBar">
			<logic:equal name="PageForm" property="securityMap(editGenericPermission)" value="true">
				<logic:equal name="PageForm" property="businessObjectType" value="VAC">
				   <input type="submit" name="Edit" value="Edit" onclick="return editVaccinationForm(${actUid})" />
	            </logic:equal>
	            <logic:notEqual name="PageForm" property="businessObjectType" value="VAC">
					<input type="submit" name="Edit" value="Edit" onclick="editForm()"/>
				</logic:notEqual>
			</logic:equal>          
         	<input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlyPage();"/>
             
			 
            <logic:equal name="PageForm" property="securityMap(deleteGenericPermission)" value="true">
	           	 <logic:equal name="PageForm" property="businessObjectType" value="VAC">
				   		<input type="submit" name="Delete" value="Delete" onclick="return deleteVaccination(${actUid});" />
	             </logic:equal>
	             <logic:equal name="PageForm" property="businessObjectType" value="IXS">
					 	<input type="submit" name="Delete" value="Delete" onclick="return deleteInterview();" />
					 	<logic:equal name="PageForm" property="attributeMap.COINFECTION_INV_EXISTS" value="true">
					 	    <input type="button" name="AssociateInvestigations" value="Associate Investigations" onclick="ManageInterviewInvestigationAssociations(${actUid})"/>
						</logic:equal>	
				 </logic:equal>
			</logic:equal>
            <input type="button" name="Cancel" value="Close" onclick="cancelView(event)" />
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
                       
                        <logic:equal name="PageForm" property="businessObjectType" value="VAC">
							<%@ include file="/pagemanagement/GenericEventSummary.jsp" %>
						</logic:equal>
						                     
                        <!-- Required Field Indicator -->
                        <div style="text-align:right; width:100%;"> 
                            <span class="boldTenRed"> * </span>
                            <span class="boldTenBlack"> Indicates a Required Field </span>  
                        </div> 
                
                        
      
     <!-- ################### PAGE TAB ###################### - - -->
      <layout:tabs width="100%" styleClass="tabsContainer">
             
       		
        
	  	
         <layout:tab key="Patient">     
        <jsp:include page="viewPatient.jsp"/>
        </layout:tab>  
        
              
       		
        
	  	
         <layout:tab key="Vaccination">     
        <jsp:include page="viewVaccination.jsp"/>
        </layout:tab>  
        
           
          </layout:tabs>  
          
            <div class="popupButtonBar">
				<logic:equal name="PageForm" property="securityMap(editGenericPermission)" value="true">
					<logic:equal name="PageForm" property="businessObjectType" value="VAC">
					   <input type="submit" name="Edit" value="Edit" onclick="return editVaccinationForm(${actUid})" />
		            </logic:equal>
		            <logic:notEqual name="PageForm" property="businessObjectType" value="VAC">
						<input type="submit" name="Edit" value="Edit" onclick="editForm()"/>
					</logic:notEqual>
				</logic:equal>          
				<input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlyPage();"/>
				<logic:equal name="PageForm" property="securityMap(deleteGenericPermission)" value="true">
					<logic:equal name="PageForm" property="businessObjectType" value="VAC">
					   <input type="submit" name="Delete" value="Delete" onclick="return deleteVaccination(${actUid});" />
		            </logic:equal>
					<logic:equal name="PageForm" property="businessObjectType" value="IXS">
					  <input type="submit" name="Delete" value="Delete" onclick="return deleteInterview();" />
					</logic:equal>
					<logic:equal name="PageForm" property="attributeMap.COINFECTION_INV_EXISTS" value="true">
					   <input type="button" name="AssociateInvestigation" value="Associate Investigations" onclick="ManageInterviewInvestigationAssociations(${actUid})"/>
					</logic:equal>					
				</logic:equal> 
				<input type="button" name="Cancel" value="Close" onclick="cancelView(event)" />
			</div>
	</html:form>
      </div> <!-- Container Div -->
    </body>
     
</html>

