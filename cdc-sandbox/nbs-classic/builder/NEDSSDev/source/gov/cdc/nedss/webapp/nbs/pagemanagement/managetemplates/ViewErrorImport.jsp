<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/nedss.tld" prefix="nedss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>
<%@ page import = "gov.cdc.nedss.webapp.nbs.diseaseform.util.InvestigationFieldConstants"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.cdc.nedss.util.NEDSSConstants" %>

 <%@ include file="/jsp/resources.jsp" %>
     <script type="text/javascript">
   
            function editLoad()
	        {
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
                var conditionNm = '${fn:escapeXml(codeDesc)}';
                  var confirmMsg="You have indicated that you would like to delete the draft version of " +conditionNm +".   Once the page draft is deleted, it will no longer be available in the System; any changes you have made will be lost. Select OK to continue or Cancel to return to page Details";
	            if (confirm(confirmMsg)) {
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

     	    	var modWin = openWindow(urlToOpen, o, GetDialogFeatures(760, 700, false, true), divElt, "");
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
		  	              	
       	     // setTimeout("reldPage()", 1000);
	  }
	  
        function reldPage() {
 	  	  document.forms[0].action ="/nbs/ManagePage.do?method=saveAsTemplate";
		  document.forms[0].submit(); 
		}
  
        function rulesListLoad(){
        	document.forms[0].action ="/nbs/ManagePage.do?method=rulesListLoad&initLoad=true";	         
	         document.forms[0].submit();
	         return true;
	    }
 		
        function createNewDraft(){
	            document.forms[0].action ="/nbs/ManagePage.do?method=createNewDraft";
	            document.forms[0].submit();
	            return true;
	    }


        function publishPopUp(){      
            var urlToOpen =  "/nbs/ManagePage.do?method=publishPopUpLoad";
            var divElt = getElementByIdOrByName("parentWindowDiv");
            if (divElt == null) {
	                   divElt = getElementByIdOrByName("blockparent");
	        }
	              //  alert("divElt :"+divElt.value );
	          divElt.style.display = "block";
	          var o = new Object();
	          o.opener = self;
	          //window.showModalDialog(urlToOpen,o, GetDialogFeatures(840, 400, false));     
	         var modWin =  openWindow(urlToOpen, o, GetDialogFeatures(840, 400, false, true), divElt, "");
           }              

          function getValueFromPopUpWindow(messageId, versionNotes){
        	  //alert(messageId.value + "  ---  " + versionNotes.value); 
              getElementByIdOrByName("messageId").value = messageId.value;
              getElementByIdOrByName("descTxtp").value = versionNotes.value;
              setTimeout("backToParentPage()", 1000);
          }
      
          function backToParentPage() {
        	  publish(); 
          }

          function viewPageHistoryPopUp(){      
        	  var conditionCd = '<%=request.getAttribute("conditionCode")%>';
              var urlToOpen =  "/nbs/ManagePage.do?method=viewHistoryPopUpLoad&conditionCd="+conditionCd;
              var divElt = getElementByIdOrByName("parentWindowDiv");
              if (divElt == null) {
                         divElt = getElementByIdOrByName("blockparent");
              }
                    //  alert("divElt :"+divElt.value );
                divElt.style.display = "block";
                var o = new Object();
                o.opener = self;
                //window.showModalDialog(urlToOpen,o, GetDialogFeatures(840, 400, false));   
                var modWin = openWindow(urlToOpen, o,  GetDialogFeatures(840, 400, false, true), divElt, "");
             } 

          function printPreview() {
              var divElt = getElementByIdOrByName("pageview");
              divElt.style.display = "block";
              var o = new Object();
              o.opener = self;   
              var URL = "/nbs/PreviewPage.do?method=viewPageLoad&mode=print";
              var dialogFeatures = "dialogWidth:650px;dialogHeight:500px;status:no;unadorned:yes;scroll:yes;scrollbars:yes;help:no;resizable:yes;max:1;min:1";
              //window.showModalDialog(URL, o, dialogFeatures); 
              var modWin = openWindow(URL, o, dialogFeatures, divElt, "");
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
             // window.showModalDialog(URL, o, dialogFeatures); 
             var modWin =  openWindow(URL, o, dialogFeatures, divElt, "");
              return false;
           }

          function makeInactive()
          {
          	var templateUid = '${fn:escapeXml(templateUid)}';
          	var templateNm = '${fn:escapeXml(conditiondesc)}';
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
        	<%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
          <% if(request.getAttribute("bottom") == null){ %>
            <div id="bd" style="text-align:center;">               
            	<div align="right">
            	 <% if(request.getAttribute("Template") != null && request.getAttribute("Template").toString().equalsIgnoreCase("Template") ) {%>
            	 	<a href="ManageTemplates.do?method=ManageTemplatesLib&actionMode=Manage&context=ReturnToManage">Return to Template Library</a> &nbsp;&nbsp;&nbsp;
            	 	 
            	 <%}else { %>
                 	<a href="ManagePage.do?method=list&initLoad=true">Return to Page Library</a> &nbsp;&nbsp;&nbsp;
                 <%} %>
            </div>  
		       
            <%} %>
            <br></br>
              <% if (request.getAttribute("confirmation") != null && request.getAttribute("confirmation").toString().equals("uploaderror")) { %>
                        <div class="infoBox errors">
	                        Import of <b>${fn:escapeXml(srcTemplateNm)}</b> failed. Please see import log for more details.    
	                    </div>    
                    <% } %>
	   	</div>
   
	  
	 
    </body>
