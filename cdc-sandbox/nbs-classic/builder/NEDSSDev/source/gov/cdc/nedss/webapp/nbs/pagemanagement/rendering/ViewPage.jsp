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

<script type="text/javascript" src="Globals.js"></script>

     <script type="text/javascript">
   
			   function downloadMetadata()
			{
				document.forms[0].action ="/nbs/ManagePage.do?method=exportPageMetadata";
				document.forms[0].submit();
			}

            function editLoad()
	        {
            	blockUIDuringFormSubmissionNoGraphic();
	            document.forms[0].action ="/nbs/ManagePage.do?method=editPageContentsLoad&fromWhere=V";
	            document.forms[0].submit();
	            return true;
	        }
	        function publish()
	        {
	            document.forms[0].action ="/nbs/ManagePage.do?method=publishPage";
	            document.forms[0].submit();
	            return true;
	        }
	        
              function deleteDraft()
	        {
                var pageNm = '${fn:escapeXml(pgPageName)}';
                 var confirmMsg="You have indicated that you would like to delete the draft version of " +pageNm +".   Once the page draft is deleted, it will no longer be available in the System; any changes you have made will be lost. Select OK to continue or Cancel to return to page Details";
	            if (confirm(confirmMsg)) {
	            	blockUIDuringFormSubmissionNoGraphic();
		            document.forms[0].action ="/nbs/ManagePage.do?method=deleteDraft";
		            document.forms[0].submit();
	       	     return true;
	       	}else{
	       	 	return false;		
	       	}
	        }
    
    	      function saveAsTemplatePopUp(){      
	   //   alert("inside function");
                       
     		var urlToOpen =  "/nbs/ManagePage.do?method=saveAsTemplateLoad";
     		//  alert("urlToOpen :"+urlToOpen );

     		var divElt = getElementByIdOrByName("parentWindowDiv");
     		
     	    	if (divElt == null) {
     	       	 divElt = getElementByIdOrByName("blockparent");
     	    	}
     	  //  alert("divElt :"+divElt.value );
     	    	divElt.style.display = "block";
     	    	var o = new Object();
     	    	o.opener = self;
     	    	//var returnMessage = window.showModalDialog(urlToOpen,o, GetDialogFeatures(760, 350, false));
     	    	
     	    	var modWin = openWindow(urlToOpen, o,GetDialogFeatures(760, 350, false, true), divElt, "");
  	          
  	          
  	          
  	          if(modWin){
  	        	modWin.onbeforeunload = function(){
	      			$j.unblockUI();
	      		}
    	          	blockUIDuringFormSubmissionNoGraphic();
    	          }
     	    	
     	    	//window.open(urlToOpen);
     	}
     	
     	        
      	function reloadLibrary(templateNm, descTxt){
         		//alert(templateNm.value);      
            		//alert(descTxt.value); 
            		
            	//	alert("name was unique....");
            		
            		// close the popup
            		
            		// submit the page.
            		getElementByIdOrByName("templateNmp").value = templateNm.value;
            		getElementByIdOrByName("descTxtp").value = descTxt.value;            
            		document.forms[0].action ="/nbs/ManagePage.do?method=saveAsTemplate";
		       		document.forms[0].submit(); 
		  	              	
	  }
	  
        function reldPage() {
 	  	  document.forms[0].action ="/nbs/ManagePage.do?method=saveAsTemplate";
		  document.forms[0].submit(); 
		}
  
        function rulesListLoad(){
        	blockUIDuringFormSubmissionNoGraphic();
        	 document.forms[0].action ="/nbs/ManagePage.do?method=rulesListLoad&initLoad=true";	         
	         document.forms[0].submit();
	         return true;
	    }
	    
        function viewPageDetails(){
        	blockUIDuringFormSubmissionNoGraphic();
        	 document.forms[0].action ="/nbs/ManagePage.do?method=viewPageDetailsLoad&initLoad=true";	         
	         document.forms[0].submit();
	         return true;
	    }	    
 		
        function createNewDraft(){
        		blockUIDuringFormSubmissionNoGraphic();
        		var createDraftInd = "<%=request.getAttribute("createDraftInd")%>";
	            document.forms[0].action ="/nbs/ManagePage.do?method=createNewDraft&createDraftInd="+createDraftInd;
	            document.forms[0].submit();
	            return true;
        }        

	function checkForRelatedCondition() {
	        // Check for at least one related condition
	        <% if (request.getAttribute("relatedConditionCode") != null && 
					request.getAttribute("relatedConditionCode").toString().equalsIgnoreCase("none")) { %>
			var pubErrors = new Array();
		        var index = 0;
		        var errorMsg = "At least one condition must be related to this page before it can be published. Please update the Page Details by mapping Related Condition(s) to the page.";
		        pubErrors[index++] = errorMsg;
		        displayGlobalErrorMessage(pubErrors);
		        return false;
		<%}%>
			return true;
	}

        function publishPopUp(){  
        
        
		if (checkForRelatedCondition() == false) {
			return false;
		}
        
		var urlToOpen =  "/nbs/ManagePage.do?method=publishPopUpLoad";
            
            var divElt = getElementByIdOrByName("parentWindowDiv");
            if (divElt == null) {
	                   divElt = getElementByIdOrByName("blockparent");
	        }
	              //  alert("divElt :"+divElt.value );
	          divElt.style.display = "block";
	          var o = new Object();
	          o.opener = self;
	          
			  //Center the window:
			  
			  var leftVar = (screen.width/2)-(840/2);
			  var topVar = (screen.height/2)-(700/2);
			  
			  
	          var dlgStyle = "scroll:on;scrollbars=yes;status:no;resizable:yes;help:no;dialogHeight:400px;dialogWidth:900px;top="+topVar+";left="+leftVar;
	          
	          //var returnMessage = window.showModalDialog(urlToOpen,o, dlgStyle);
	          
	          
	          var modWin = openWindow(urlToOpen, o,dlgStyle, divElt, "");
	          
	          
	          
	          if(modWin){
	        	  //Added for unblocking the page if the publish window is closed
	        	  modWin.onbeforeunload = function(){
	      			$j.unblockUI();
	      		}
	        	  
	          	blockUIDuringFormSubmissionNoGraphic();
	          }              
           }              

          function getValueFromPopUpWindow(versionNotes){
        	  //alert(VersionNotes.value + "  ---  " + versionNotes.value); 
              //getElementByIdOrByName("messageId").value = messageId.value;
              getElementByIdOrByName("verNote").value = versionNotes.value;
              //alert("Version = " + versionNotes.value);
              setTimeout("backToParentPage()", 1000);
          }
      
          function backToParentPage() {
        	  publish(); 
          }

          function viewPageHistoryPopUp(){      
        	  var conditionCd = '${fn:escapeXml(pgPageName)}';
              var urlToOpen =  "/nbs/ManagePage.do?method=viewHistoryPopUpLoad&conditionCd="+conditionCd;
              var divElt = getElementByIdOrByName("parentWindowDiv");
              if (divElt == null) {
                         divElt = getElementByIdOrByName("blockparent");
              }
                    //  alert("divElt :"+divElt.value );
                divElt.style.display = "block";
                var o = new Object();
                o.opener = self;
               // window.showModalDialog(urlToOpen,o, GetDialogFeatures(840, 400, false)); 
                
                var modWin = openWindow(urlToOpen, o,GetDialogFeatures(840, 400, false, true), divElt, "");
             } 

          function printPreview() {
              var divElt = getElementByIdOrByName("pageview");
              divElt.style.display = "block";
              var o = new Object();
              o.opener = self;   
              var URL = "/nbs/PreviewPage.do?method=viewPageLoad&mode=print";
              var dialogFeatures = "dialogWidth:780px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
              //window.showModalDialog(URL, o, dialogFeatures); 
              
              var modWin = openWindow(URL, o,dialogFeatures, divElt, "");
              return false;
           }

          function printTemplate() {
              var divElt = getElementByIdOrByName("pageview");
              var templateUid = '${fn:escapeXml(templateUid)}';
              divElt.style.display = "block";
              var o = new Object();
              o.opener = self;   
              var URL = "/nbs/PreviewTemplate.do?method=viewTemplate&mode=print&templateUid="+templateUid;
              var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
              //window.showModalDialog(URL, o, dialogFeatures); 
              
              var modWin = openWindow(URL, o,dialogFeatures, divElt, "");
              
              
              return false;
           }

          function makeInactive()
          {
          	var templateUid = '${fn:escapeXml(templateUid)}';
          	var templateNm = '${fn:escapeXml(pgPageName)}';
          	var confirmMsg="You have indicated that you would like to inactivate the "+ templateNm +" . Once inactivated, this template will be no longer available to the users when adding a page within the Page Builder. Select OK to continue or Cancel to return to View Template.";
  	        if (confirm(confirmMsg))
  	        {
  	        	document.forms[0].action ="/nbs/ManageTemplates.do?method=makeInactive&templateUid="+templateUid;
  	        	document.forms[0].submit();
  	        }
  	        else {
  	            return false;
  	        }
          }
          function makeActive()
          {
           var templateUid = '${fn:escapeXml(templateUid)}';
  	       document.forms[0].action ="/nbs/ManageTemplates.do?method=makeActive&templateUid="+templateUid;
  	       document.forms[0].submit();
  	        
          }
          function exportTemplate()
          {
           var templateUid = '${fn:escapeXml(templateUid)}';
  	       document.forms[0].action ="/nbs/ManageTemplates.do?method=exportTemplate&templateUid="+templateUid;
  	       document.forms[0].submit();
  	     
       
  	        
          }
      </script>       
  
    <body onload="autocompTxtValuesForJSP(); startCountdown();">
        <div id="parentWindowDiv"></div>
        <div id="doc3">
          <% if(request.getAttribute("bottom") == null){ %>
            <div id="bd" style="text-align:center;">               
            	<div align="right">
            	 <% if(request.getAttribute("Template") != null && request.getAttribute("Template").toString().equalsIgnoreCase("Template") && !(request.getAttribute("mode") != null && request.getAttribute("mode").toString().equalsIgnoreCase("print")) ) {%>
            	 	<a href="ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&context=ReturnToManage&existing=true">Return to Template Library</a> &nbsp;&nbsp;&nbsp;
            	 	 
            	 <%}else if(!(request.getAttribute("mode") != null && request.getAttribute("mode").toString().equalsIgnoreCase("print"))) { %>
                 	<a href="ManagePage.do?method=list&fromPage=returnToPage&context=ReturnToManage&existing=true">Return to Page Library</a> &nbsp;&nbsp;&nbsp;
                 <%} %>
            </div>  
		       
            <%} %>
	   	</div>
   
	  
	   <!-- Top button bar -->     
           <%if(request.getAttribute("mode") != null && request.getAttribute("mode").toString().equalsIgnoreCase("print")){ %>
	   		  <div class="">
	   		<% } else {%>
	   		  <div class="grayButtonBar">
	   		  <% } %>
	   		
	   		<table role="presentation" width="100%">
	   		 <tr>
	   		 	<td align="left" >
	   		 	<% if(request.getAttribute("Template") != null && request.getAttribute("Template").toString().equalsIgnoreCase("Template") ){%>
	   		 	  	<input type="button" name="View Rules" value="View Rules" onclick="rulesListLoad();"/> 
	   		 	<%}else{ %>
	   		 	
	   		 	<input type="button" name="Page Details" value="Page Details" onclick="viewPageDetails()"/>
				<input type="button" name="Page Rules" value="Page Rules" onclick="rulesListLoad();"/>  
				<input type="button" name="Page History" value="Page History" onclick="viewPageHistoryPopUp()"/>
				<input type="button" name="Page Metadata" value="Page Metadata" onclick="downloadMetadata()"/>
												
			    <%} %>   
	   			</td>
	   		
       			 <html:hidden  property="selection.templateNm" styleId = "templateNmp"/>
       			 <html:hidden  property="selection.descTxt" styleId="descTxtp" />
       			 <html:hidden  property="selection.versionNote" styleId = "verNote" />
       			 <html:hidden  property="selection.messageId" styleId = "messageId"/>     
 
				 <td align="right"> 
			   		 <% if(request.getAttribute("published") != null && request.getAttribute("published").toString().equalsIgnoreCase("published") ) { %>
		                    <input type="button"  name="Print" style="width: 70px" value="Print" onclick="return printPreview();" /> 
			                <input type="button"  name="Save As Template" value="Save As Template" onclick="saveAsTemplatePopUp();" />  
			         <% if(request.getAttribute("busObjectType") != null && !request.getAttribute("busObjectType").toString().equalsIgnoreCase(NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE) && !request.getAttribute("busObjectType").toString().equalsIgnoreCase(NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE)) { %>       
		       	            <input type="button"  name="Create New Draft" value="Create New Draft" onclick="createNewDraft();" />
		       	      <%} %>
		       	            
		       	     <%} else if(request.getAttribute("Template") != null && request.getAttribute("Template").toString().equalsIgnoreCase("Template") ){ %>
		       	     		<input type="button"  name="Print" value="Print" style="width: 70px" onclick="return printTemplate();" />                               
		                    <% String activeInd = (String)request.getAttribute("ActiveInd");
		                    if (activeInd != null && activeInd.equals("Inactive")) { %>
		                    	<!-- <input type="button" name="Edit" value="Edit" style="width: 70px" onclick=""/>  -->
		                    	<input type="button"  name="Export" value="Export" style="width: 70px" onclick="exportTemplate();" />
		                    	<input type="button"  name="Make Inactive" value="Make Inactive" onclick="makeInactive();" />  
		                    <%} else if(activeInd != null && activeInd.equals("Active"))  { %>
			                	<input type="button"  name="Make Active" value="Make Active" onclick="makeActive();" /> 
			                <%} %>    			                               
		             <% }else{ %> 
		             	    <% if(request.getAttribute("busObjectType") != null && !request.getAttribute("busObjectType").toString().equalsIgnoreCase(NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE) && !request.getAttribute("busObjectType").toString().equalsIgnoreCase(NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE)) { %>       
		                    <input type="button" name="Edit Page" value="Edit Page" onclick="editLoad();"/>
		                    <%} %> 
		                    <input type="button"  name="Print" value="Print" onclick="return printPreview();" /> 
		                    <input type="button"  name="Publish" value="Publish" onclick="publishPopUp()" /> 
		                    <input type="button"  name="Delete Draft" value="Delete Draft" onclick="deleteDraft()" />
		                    <input type="button"  name="Save As Template" value="Save As Template" onclick="saveAsTemplatePopUp();" />                       
	              </td>
                       
                 <%} %>
                </tr>
			</table>
			<div class="printerIconBlock screenOnly">
			    <table role="presentation" style="width:98%; margin:3px;">
			        <tr>
			            <td style="text-align:right; font-weight:bold;"> 
			                <a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
			            </td>
			        </tr>
			    </table>
			</div>
            </div>
        </div>
        
     <script>
     	var isDisablePublish = ${isDisablePublish};
     	var publishButtonEle = getElementByIdOrByName("Publish");
		if(isDisablePublish!=null && isDisablePublish==true){
			if(publishButtonEle!=null){
				getElementByIdOrByName("Publish").disabled=true;
			}
		}else{
			if(publishButtonEle!=null){
				getElementByIdOrByName("Publish").disabled=false;
			}
		}
     </script>
   
    </body>
