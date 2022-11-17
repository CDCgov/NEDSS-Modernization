<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN JSP Generic PAGE GENERATE ###- - -->
       
       
    <html lang="en">
    <head>
    <title>NBS: Window</title>
    <%@ page isELIgnored ="false" %>
    <%@ include file="/jsp/tags.jsp" %>
    <%@ include file="/jsp/resources.jsp" %> 
    <%@ page import="java.util.*" %>
    <%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj, gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup, gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup, gov.cdc.nedss.util.PageConstants, gov.cdc.nedss.util.PropertyUtil" %>
    <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>  
    <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageSubForm.js"></SCRIPT>  
    <script language="JavaScript" src="Coinfection.js"></script>     
        <script language="JavaScript"> 
          
	<%
           response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
           response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
    	%>   
    	
    	blockEnterKey();
    	
    	function cancelContextForm()
        {
	        var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
                document.forms[0].action ="${PageForm.attributeMap.Cancel}";
                document.forms[0].submit();	
            } else {
                return false;
            }
        
        }
        function saveContextForm() 
        {
           if (saveWinCalled == false) { //don't submit twice
          	
	       var method="${PageForm.attributeMap.method}";  
           
           var map = setQuestionInAnswerMap("NBS_LAB112");
           
          if(method=="createSubmit"){
				if (pgCheckForErrorsOnSubmit() == true) {
					saveWinCalled = false;
				  return false;
				} else {
				  saveWinCalled = true;
				  var nbsSecurityJurisdictions = "${PageForm.attributeMap.NBSSecurityJurisdictions}";
				  var valJuris =  validatePageJurisdiction("INV107",nbsSecurityJurisdictions);
				  if (valJuris != null && valJuris == 'false'){
				  	saveWinCalled = false;
					return false; //user wants to edit jurisdiction
					
					}
				  
				if (valJuris != null && valJuris == 'true'){
					document.forms[0].action ="${PageForm.attributeMap.SubmitNoViewAccess}"+"&mapView="+map;
					//document.forms[0].submit();	
				  }else              
					document.forms[0].action ="${PageForm.attributeMap.Submit}"+"&mapView="+map;
					document.forms[0].submit();	
			    }
			}
			 else if(method=="editGenericSubmit"){
				if (pgCheckForErrorsOnSubmit() == true) {
					saveWinCalled = false;
				    return false;
				} else {
					saveWinCalled = true;
					document.forms[0].action ="${PageForm.attributeMap.Submit}"+"&mapView="+map;
					document.forms[0].submit();	
			    }
			}
        } 
        }
        
        function submitAndCreateInvestgationForm()
        {
          if (saveWinCalled == false) { //don't submit twice
          	saveWinCalled = true;
          if (pgCheckForErrorsOnSubmit() == true){
          		saveWinCalled = false;
			  	return false;
			  }
			document.forms[0].action ="${PageForm.attributeMap.SubmitAndCreateInvestigation}";
			document.forms[0].submit();	
       	 }
        }
    	
        function cancelForm()
        {
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
                document.forms[0].action ="${PageForm.attributeMap.Cancel}";
                // pass control to parent's call back handler
  
		var opener = getDialogArgument();    
		if(opener!=null){                
			// refresh parent's form
			opener.document.forms[0].action ="/nbs/PageAction.do?method=closeWindow";
					opener.document.forms[0].submit();
					window.returnValue = "windowClosed"; 
			window.close();                
				} else {
					return false;
				}
            }
        }
         var closeWinCalled = false;
         var saveWinCalled = false;
        function handlePageUnload(closePopup, e)
        {
			// This check is required to avoid duplicate invocation 
			// during close button clicked and page unload.
			if (closeWinCalled == false && saveWinCalled == false) {
				closeWinCalled = true;
				
				if (e.clientY < 0 || closePopup == true) {
							   // pass control to parent's call back handler
				   // refresh parent's form
				   // get reference to opener/parent 
	
				   var opener = getDialogArgument(); 
					if(opener!=null){                
						opener.document.forms[0].action ="/nbs/PageAction.do?method=closeWindow";
					   opener.document.forms[0].submit();          	                    
					   window.returnValue = "windowClosed"; 
					   window.close();
					}
				 }
            }
        }
        
        function onloadBlockParent(){
		   	var opener = getDialogArgument();
		   	opener.$j.blockUI({});
	 	}
 
 
        function unloadSubForms(closePopup, e){
        	var opener = getDialogArgument(); 
        	
        	if(e.clientY < 0 || closePopup == true){
				if(typeof(opener) !== "undefined" && opener !== null){
	  				opener.unblockUIDuringFormSubmissionNoGraphic();
  				
  				}
        	}
        
        }
        
        function saveForm() 
        {
        if (saveWinCalled == false) { //don't submit twice
          saveWinCalled = true;
          var method="${PageForm.attributeMap.method}";  
           
          if(method=="createSubmit"){
                if (pgCheckForErrorsOnSubmit() == true) {
                             saveWinCalled = false;
                             return false;
                }
				CheckIfCoinfectionsShouldBeAssociatedToInterview();
                
          } else   {       
                         if (pgCheckForErrorsOnSubmit() == true) {
                             saveWinCalled = false;
                             return false;
                         } else {
                             document.forms[0].action ="/nbs/PageAction.do?method=editGenericSubmit&ContextAction=Submit";	
                             document.forms[0].submit();          	   
                         }
          }
           
	          var msg = '<div class="submissionStatusMsg"> <div class="header"> Page Builder </div>' +  
	          '<div class="body"> Please wait...  The system is loading the requested page. </div> </div>';         
			  $j.blockUI({  
			            message: msg,  
			     		css: {  
			               top:  ($j(window).height() - 100) /2 + 'px', 
			     		   left: ($j(window).width() - 500) /2 + 'px', 
			     			width: '500px'
			     	    }  
	          });
          }//saveWinCalled = false
        }
        
        function submitForm() 
        {
        
          saveWinCalled = true;
          var method="${PageForm.attributeMap.method}";  
           
          if(method=="createSubmit"){
                         if (pgCheckForErrorsOnSubmit() == true) {
                             saveWinCalled = false;
                             return false;
                         } else {
                              document.forms[0].action ="/nbs/PageAction.do?method=createGenericSubmit&ContextAction=Submit";
                             document.forms[0].submit();
                         }
          } else   {       
                         if (pgCheckForErrorsOnSubmit() == true) {
                             saveWinCalled = false;
                             return false;
                         } else {
                             document.forms[0].action ="/nbs/PageAction.do?method=editGenericSubmit&ContextAction=Submit";	
                             document.forms[0].submit();          	   
                         }
          } 
        }

// validate Interview Date on submit
// from values passed in request
function ixsValidateInterviewDateOnSubmit() {
		var i = 0;
		var errorElts = new Array();
		var errorMsgs = new Array();
  		//check if interview status conflicts with interview Date
  		var ixsStatusElement =getElementByIdOrByName('IXS100');
		if (ixsStatusElement == null || typeof(ixsStatusElement) == 'undefined') {
  			return {elements : errorElts, labels : errorMsgs}
  		}
  		var ixsDateStr =getElementByIdOrByName('IXS101');
		if (ixsDateStr == null || typeof(ixsDateStr) == 'undefined') {
		  	return {elements : errorElts, labels : errorMsgs}
  		}
		var theDateString  =   $j('#IXS101').val();
		if (ixsDateStr == null || ixsDateStr == '') {
			return {elements : errorElts, labels : errorMsgs}
  		}
  		var interviewDate = new Date(theDateString);
  		var interviewStatus = $j("#IXS100 :selected").val();
  		var curDate = new Date();
  		if ((interviewStatus == "COMPLETE") && (interviewDate > curDate)) {
  				var a1Str = buildErrorAnchorLink(document.getElementById('IXS101'), "Interview Date");
				errorMsgs[i] ="The " + a1Str +  " cannot be a future date since the Interview Status is Closed/Completed";
	   			errorElts[i] =getElementByIdOrByName('IXS101');
	   			$j('#IXS101L').css("color", "990000");
	   			i++;
	   	}
	   	
	   	var originalInterviewDateStr = '<%=request.getAttribute("ixsOriginalInterviewDate") == null ? "" : request.getAttribute("ixsOriginalInterviewDate")%>';     
		var presumptiveInterviewDateStr = '<%=request.getAttribute("ixsLatestPresumptiveInterviewDate") == null ? "" : request.getAttribute("ixsLatestPresumptiveInterviewDate")%>';     
		var ixsEarliestReInterviewDateStr = '<%=request.getAttribute("ixsEarliestReInterviewDate") == null ? "" : request.getAttribute("ixsEarliestReInterviewDate")%>';
		
		var interviewType = $j("#IXS105 :selected").val();
		if (interviewType == null || interviewType == '') {
			return {elements : errorElts, labels : errorMsgs}
		}
       		var interviewDate = new Date(theDateString);
       		//check if interview type is Original but Original already exists
       		if (originalInterviewDateStr != "") {
       			if (interviewType == "INITIAL") {
       				var a1Str = buildErrorAnchorLink(document.getElementById('IXS105L'), "Interview Type");
				errorMsgs[i] ="Only one Original Interview may occur with the Subject of the Investigation. Please correct the " + a1Str + " and try again.";
	   			errorElts[i] =getElementByIdOrByName('IXS105');
	   			$j('#IXS105L').css("color", "990000");
	   			i++;       			
       			}
       		}
	   	
	   	if (presumptiveInterviewDateStr != "") {
	   		var presumptiveDate = new Date(presumptiveInterviewDateStr);
	   		if ((interviewType == "INITIAL") && (interviewDate.getTime() < presumptiveDate.getTime())){
	   			var a1Str = buildErrorAnchorLink(document.getElementById('IXS101'), "Interview Date");
				errorMsgs[i] ="The Interview Date of an Original Interview cannot be prior to the Interview Date of existing Presumptive Interview(s). Please correct the " + a1Str + " and try again.";
	   			errorElts[i] =getElementByIdOrByName('IXS101');
	   			$j('#IXS101L').css("color", "990000");
	   			i++;       			
       			}	   		
	   	}
	   	
	   	if (ixsEarliestReInterviewDateStr != "") {
	   		var reinterviewDate = new Date(ixsEarliestReInterviewDateStr);
	   		if ((interviewType == "INITIAL") && (interviewDate.getTime() > reinterviewDate.getTime())){
	   			var a1Str = buildErrorAnchorLink(document.getElementById('IXS101'), "Interview Date");
				errorMsgs[i] ="The Interview Date of an Original Interview cannot be after the Interview Date of existing Re-Interviews. Please correct the " + a1Str + " and try again.";
	   			$j('#IXS101L').css("color", "990000");
	   			i++;       			
       			}	   		
	   	}	
	   	if (originalInterviewDateStr != "") {
	   		var originalDate = new Date(originalInterviewDateStr);
	   		if ((interviewType == "PRESMPTV") && (originalDate.getTime() < interviewDate.getTime())){
	   			var a1Str = buildErrorAnchorLink(document.getElementById('IXS101'), "Interview Date");
				errorMsgs[i] ="The Interview Date of a Presumptive Interview cannot be after the Interview Date of the existing Original Interview. Please correct the " + a1Str + " and try again.";
	   			errorElts[i] =getElementByIdOrByName('IXS101');
	   			$j('#IXS101L').css("color", "990000");
	   			i++;       			
       			}	   		
	   	}
	   	if (ixsEarliestReInterviewDateStr != "")  {
	   		var reinterviewDate = new Date(ixsEarliestReInterviewDateStr);
	   		if ((interviewType == "PRESMPTV") && (interviewDate.getTime() > reinterviewDate .getTime())){
	   			var a1Str = buildErrorAnchorLink(document.getElementById('IXS101'), "Interview Date");
				errorMsgs[i] ="The Interview Date of a Presumptive Interview cannot be after the Interview Date of the existing Re-interview(s). Please correct the " + a1Str + " and try again.";
	   			errorElts[i] =getElementByIdOrByName('IXS101');
	   			$j('#IXS101L').css("color", "990000");
	   			i++;       			
       			}	   		
	   	}
       		if (originalInterviewDateStr != "") {
       			var originalDate = new Date(originalInterviewDateStr);
       			if ((interviewType == "REINTVW") && (interviewDate.getTime() < originalDate .getTime())){
       				var a1Str = buildErrorAnchorLink(document.getElementById('IXS101'), "Interview Date");
				errorMsgs[i] ="The Interview Date of a Re-interview cannot be prior to the Interview Date of the Original Interview. Please correct the " + a1Str + " and try again.";
	   			errorElts[i] =getElementByIdOrByName('IXS101');
	   			$j('#IXS101L').css("color", "990000");
	   			i++;       			
       			}
       		}
	   	if (presumptiveInterviewDateStr != "") {
	   		var presumptiveDate = new Date(presumptiveInterviewDateStr);
	   		if ((interviewType == "REINTVW") && (interviewDate.getTime() < presumptiveDate.getTime())){
	   			var a1Str = buildErrorAnchorLink(document.getElementById('IXS101'), "Interview Date");
				errorMsgs[i] ="The Interview Date of a Re-interview cannot be prior to the Interview Date of existing Presumptive Interview(s). Please correct the " + a1Str + " and try again.";
	   			errorElts[i] =getElementByIdOrByName('IXS101');
	   			$j('#IXS101L').css("color", "990000");
	   			i++;       			
       			}	   		
	   	}
       		if ((interviewType == "REINTVW") && (originalInterviewDateStr == "" && presumptiveInterviewDateStr == "") ) {
       			var a1Str = buildErrorAnchorLink(document.getElementById('IXS105L'), "Interview Type");
			errorMsgs[i] ="A Re-Interview may occur only after a Presumptive or an Original Interview has been created. Please correct the " + a1Str + " and try again.";
	   		errorElts[i] =getElementByIdOrByName('IXS105');
	   		$j('#IXS105L').css("color", "990000");
	   		i++;      			
       		}	   	
	   	if (i == 0) {
	   		$j('#IXS101L').css("color", "black"); //clear if color present
	   		$j('#IXS105L').css("color", "black"); //clear if color present
	   	}
	 	return {elements : errorElts, labels : errorMsgs}
	}         
 
 //If Lab Report is shown from Data Entry, then the Retain section and Patient Search section are shown, otherwise, it will be  hidden: ND-14108
 function hideOrShowRetainButtonsForNextEntry(){

	var retainNextEntry = '<%=request.getAttribute("retainNextEntry")%>';     

	if(retainNextEntry=="true"){
		if(getElementByIdOrByName("NBS_UI_28")!=null && getElementByIdOrByName("NBS_UI_28")!=undefined)
			getElementByIdOrByName("NBS_UI_28").show();//Retain Next Entry
		if(getElementByIdOrByName("NBS_UI_27")!=null && getElementByIdOrByName("NBS_UI_27")!=undefined)
			getElementByIdOrByName("NBS_UI_27").show();//Patient Search
	}
	else{
		if(getElementByIdOrByName("NBS_UI_28")!=null && getElementByIdOrByName("NBS_UI_28")!=undefined)
			getElementByIdOrByName("NBS_UI_28").hide();//Retain Next Entry
		if(getElementByIdOrByName("NBS_UI_27")!=null && getElementByIdOrByName("NBS_UI_27")!=undefined)
			getElementByIdOrByName("NBS_UI_27").hide();//Patient Search
	}
	
}

function readOnlyReportingFacilityOnEdit(){

	var editMode = '<%=request.getAttribute("editMode")%>';   
	
	if(editMode == "edit")
		if($j("#NBS_LAB365CodeClearButton")!=null && $j("#NBS_LAB365CodeClearButton")!=undefined)
			$j("#NBS_LAB365CodeClearButton").hide();

}



 function saveSubForm() 
        {

          var method="${PageSubForm.attributeMap.method}";  
          	 
          if(method=="createSubmit"){
            if (pgCheckForErrorsOnSubmit() == true) {
              return false;
            } else {
              stdSpecialSubmitCheck(); //last min checks for STD
              var nbsSecurityJurisdictions = "${PageForm.attributeMap.NBSSecurityJurisdictions}";
              var valJuris =  validatePageJurisdiction("INV107",nbsSecurityJurisdictions);
              if (valJuris != null && valJuris == 'false')
              	return false; //user wants to edit jurisdiction
              	
              //	self.close();
              	var opener = getDialogArgument();	
              	
              if (valJuris != null && valJuris == 'true')
              	document.forms[0].action ="/nbs/PageSubFormAction.do?method=createSubmit&ContextAction=SubmitNoViewAccess";
              else              
                document.forms[0].action ="/nbs/PageSubFormAction.do?method=createSubmit&ContextAction=Submit";
                
                
            	document.forms[0].submit();
           
            return true;
				  
				  
			  var msg = '<div class="submissionStatusMsg"> <div class="header"> Page Builder </div>' +  
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
          } 
          else   {       
            if (pgCheckForErrorsOnSubmit() == true) {
				 return false;
		    } else {
		      stdSpecialSubmitCheck(); //last min checks for STD
			  document.forms[0].action ="/nbs/PageSubFormAction.do?method=editSubmit&ContextAction=Submit";	
			  document.forms[0].submit();
			  var msg = '<div class="submissionStatusMsg"> <div class="header"> Page Builder </div>' +  
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
          } 
        }
        
        
        
        function cancelSubForm()
        {
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
                document.forms[0].action ="${PageForm.attributeMap.Cancel}";
                // pass control to parent's call back handler
  
 			window.returnValue = "windowClosed"; 
			window.close();         
            }
        }
        
            
              
          
     <!-- =========Begin Javascript Functions for Dynamic Rules==========-->
     
  function ruleHideUnhNBS4556413()
{
 var foo = [];
$j('#NBS455 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#NBS455').html()!=null){foo[0]=$j('#NBS455').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Electronic'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Electronic')==true)){
pgUnhideElement('NBS405');
pgUnhideElement('NBS373');
pgUnhideElement('NBS374');
pgUnhideElement('NBS_LAB121_1');
pgUnhideElement('NBS375');
pgUnhideElement('NBS376');
pgUnhideElement('NBS_LAB293_1');
pgUnhideElement('NBS365');
 } else { 
pgHideElement('NBS405');
pgHideElement('NBS373');
pgHideElement('NBS374');
pgHideElement('NBS_LAB121_1');
pgHideElement('NBS375');
pgHideElement('NBS376');
pgHideElement('NBS_LAB293_1');
pgHideElement('NBS365');
 }
 var foo_2 = [];
$j('#NBS455_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#NBS455_2').html()!=null){foo_2[0]=$j('#NBS455_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Electronic'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Electronic')==true)){
pgUnhideElement('NBS405_2');
pgUnhideElement('NBS373_2');
pgUnhideElement('NBS374_2');
pgUnhideElement('NBS_LAB121_1_2');
pgUnhideElement('NBS375_2');
pgUnhideElement('NBS376_2');
pgUnhideElement('NBS_LAB293_1_2');
pgUnhideElement('NBS365_2');
 } else { 
pgHideElement('NBS405_2');
pgHideElement('NBS373_2');
pgHideElement('NBS374_2');
pgHideElement('NBS_LAB121_1_2');
pgHideElement('NBS375_2');
pgHideElement('NBS376_2');
pgHideElement('NBS_LAB293_1_2');
pgHideElement('NBS365_2');
 }   
}
      
     function pgCheckForDynamicRuleErrorsOnSubmit() {
     	var errorElts = new Array();
    	var errorLabels = new Array();
    	var retVal;
     	
        return {elements : errorElts, labels : errorLabels};

      }
      
      function pgCheckForFieldsToHighlightOnEdit() {
          //only check if we are in edit mode
          var actionMode =getElementByIdOrByName("actionMode") == null ? "" :getElementByIdOrByName("actionMode").value;
          if(actionMode != 'Edit') { 
              return;
          }
          
          var strFields = "<%=request.getAttribute("field_list_to_hilight")%>";
           
          if(strFields == "null") {
          	return;
          }
          pgProcessErrorFieldsToHilight(strFields);
          
      }      
      
      function pgCheckDynamicRulesOnLoad() {
           
           	ruleHideUnhNBS4556413();
          
		  csMotherInfantRequired();
       return;
      }

	<!-- === batch subsection add edit check functions (if any) follow ===-->
     	function pgNBS_UI_2BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_UI_2');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_UI_2errorMessages', errorLabels); return false;}
		$j('#NBS_UI_2errorMessages').css("display", "none");
		return true;
		}
        
      
     </script>
     
      
 
    <% 
        Map map = new HashMap();
        if(request.getAttribute("SubSecStructureMap") != null){
           map =(Map)request.getAttribute("SubSecStructureMap");              
        }
     %>
   
  
  <script language="JavaScript"> 
        
    var answerCache = { }; //global for batch records
    var viewed = -1,count=0;   
    
    function getCorrectForm (){
    
  	  var form =JPageForm;
      var bussinessObject = "<%=request.getAttribute("businessObjectType")%>";
        
		if(bussinessObject=="SUS")
			form = JPageSubForm;
		else
			form = JPageForm;
			
		return form;
		
	}
    function populateBatchRecords()
    {
       dwr.engine.beginBatch();
       var map,ans;   
       
       var form = getCorrectForm();
              
       form.getBatchEntryMap(function(map) {
          for (var key in map) {
            count++;
            fillTable(key,"pattern"+key ,"questionbody"+key );
          } 		 
        }); 	
        dwr.engine.endBatch();
    }		
    
      

    function writeQuestion( subSecNm,pattern,questionbody) {
    
    
 			
    
	    var t =getElementByIdOrByName(subSecNm); 	
        var len=0;
	    //t.style.display = "block";
	    <% String[][]  batchrecinsert  =new String[20][7];  
	       if(map != null){ 
		     Iterator itLab1 = map.entrySet().iterator(); 
		     while(itLab1.hasNext()){  
			   Map.Entry pair = (Map.Entry)itLab1.next();
		%>
							     
	     if(subSecNm == "<%=pair.getKey().toString()%>"){
		<% batchrecinsert  =  (String[][])pair.getValue();  
		 for(int i=0;i<batchrecinsert.length;i++){   
			String checknull1 = batchrecinsert[i][0]; 
			if(checknull1 != null && checknull1 != ""){%>
		var key =   "<%=batchrecinsert[i][0]%>";
		if(key != null && key != 'undefined' && key != ''){
			len =  len +1; 
		}
		<%} }%>
	    }
	<%} }%>
		for (var i = 0; i <len+1; i++){
		   $j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		   $j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
		}
		var map = {};	var emptyrow="yes";
		var code = "1013";
		dwr.engine.beginBatch(); 
		<% batchrecinsert  =new String[20][7];  
		if(map != null){ 
			Iterator itSSMap = map.entrySet().iterator();
			while(itSSMap.hasNext()){ 
				Map.Entry pair = (Map.Entry)itSSMap.next();%>
				if(subSecNm == "<%=pair.getKey().toString()%>"){
					<% batchrecinsert  =  (String[][])pair.getValue();
					   for(int i=0;i<batchrecinsert.length;i++){
				            String checkifnull  = batchrecinsert[i][0];					
					    if(checkifnull  != null && checkifnull  != "null" && checkifnull  != ""){ %>
						var qId= "<%=batchrecinsert[i][0]%>";
						var componentId = "<%=batchrecinsert[i][5]%>";
						if(qId != null && qId != "null" && qId != ' '){
							if(document.getElementById(qId) != null){
								map[qId] = getRepeatingBlockUtilDispText(qId, componentId);
								<%if( "1017".equalsIgnoreCase(batchrecinsert[i][5]) ){%>
                                    map[qId + "Disp"] = $j("#" + qId + "Disp").html();
                                <%}%>
                                
								emptyrow = repeatingBlockCheckForEmptyRow(qId, emptyrow);
								
								
								
								<%if( "1031".equalsIgnoreCase(batchrecinsert[i][5]) ){%>
                                
                                
                                
                                var code = "";
                                
                               
                                if(document.getElementById(qId)!=null && document.getElementById(qId)!=""
								&& document.getElementById(qId)!='undefined' &&
								document.getElementById(qId).selectedIndex!=null
								&& document.getElementById(qId).selectedIndex!='undefined'
								&& document.getElementById(qId).selectedIndex!=-1)
								
								code = document.getElementById(qId).options[document.getElementById(qId).selectedIndex].value;
								map[qId+"CodeId"] = code;
								map[qId+"DescriptionId"] = map[qId]+" ("+code+")";
								map[qId+"Description"] = map[qId];
								
								emptyrow = repeatingBlockCheckForEmptyRow(qId, emptyrow);
								
								
								
                                if(emptyrow=="yes" || (code=="" || code==null || code==undefined)){//In case the values are read only
                                	var qCodeId = qId+"CodeId";
									var qDescriptionId = qId+"DescriptionId";
                                    qId=qId+"Description";
                                    
                                      if (getElementByIdOrByName(qId) != null) {
   										  var textval = getElementByIdOrByName(qId).textContent;
   										  var textvalCode = getElementByIdOrByName(qCodeId).value;
										  var textvalDescriptionId = getElementByIdOrByName(qDescriptionId).value;
										  
										  
   										  if (!(textval == null || textval == '')) {
									      emptyrow = "no";
									      map[qId]=textval;
										  map[qCodeId] = textvalCode;
										  map[qDescriptionId] = textvalDescriptionId;
										  
									 	 }
   									  } 
   									  
   									  }
    
                                <%}%>
                                
							}
			 			}
			<%} }%>
				} 
			<%} }%>
			
			var attrMap = {};
			
			attrMap = getAttributeMapFromScreen("NBS_LAB220", attrMap, subSecNm);
			attrMap = getAttributeMapFromScreen("NBS_LAB280", attrMap, subSecNm);
			attrMap = getAttributeMapFromScreen("NBS459", attrMap, subSecNm);
			
			
			
			var batchentry = { subsecNm:subSecNm, id:viewed,answerMap:map, attributeMap: attrMap};  
			 if(emptyrow=="yes"){
					var errorrow= subSecNm+"errorMessages";
					displayErrors(errorrow, " At least one field must be entered when adding a repeating block.");
		            dwr.engine.endBatch();	
		            return false;
			 }  	
			 
			    if(subSecNm=="RESULTED_TEST_CONTAINER" && checkAtLeastOneValueEnteredResultedTest()==false){
					var errorrow= subSecNm+"errorMessages";
					displayErrors(errorrow, " One of the result value fields Coded Result Value, Numeric Result Value, Text Result Value must contain data. Please enter a result value and try again.");
		            dwr.engine.endBatch();	
		            return false;
			}  	
			
			
			    if(subSecNm=="RESULTED_TEST_CONTAINER" && validateNumericResult('NBS_LAB364')==false){
					var errorrow= subSecNm+"errorMessages";
					displayErrors(errorrow, "Invalid Numeric Result Value");
		            dwr.engine.endBatch();	
		            return false;
			}  	
			
				
			
			 if(subSecNm=="NBS_UI_2" && checkOnlyOneNumericOrCodedResultFromSusceptibility()==false){
					var errorrow= subSecNm+"errorMessages";
					displayErrors(errorrow, " Only one Coded Result Value or Numeric Result Value must contain data. Please remove one of the values and try again.");
		            dwr.engine.endBatch();	
		            return false;
			} 
			
			if(subSecNm=="NBS_UI_2" && validateNumericResult('NBS369')==false){
					var errorrow= subSecNm+"errorMessages";
					displayErrors(errorrow, "Invalid Numeric Result Value");
		            dwr.engine.endBatch();	
		            return false;
			} 
			
			if(subSecNm=="RESULTED_TEST_CONTAINER"){//Hide organism name after adding the row to the batch
				deleteSubFormForUpdate(document, map['NBS_LAB220CodeId'])
			}
			
					

			var form = getCorrectForm();

			form.setAnswer(batchentry,"<%=request.getSession()%>");
			fillTable(subSecNm,pattern,questionbody);
			<%  if(map != null){ 
				Iterator itSSMap = map.entrySet().iterator();
				while(itSSMap.hasNext()){ 
					Map.Entry pair = (Map.Entry)itSSMap.next();%>
			if(subSecNm == "<%=pair.getKey().toString()%>"){
				<% batchrecinsert  =  (String[][])pair.getValue();
					 for(int i=0;i<batchrecinsert.length;i++){  
					String checknull1 = batchrecinsert[i][0];
					if(checknull1 != null && checknull1 != ""){%> 
				var key =   "<%=batchrecinsert[i][0]%>";
				if(key != null &&getElementByIdOrByName(key) != null){
					dwr.util.setValue(key, "");
					if(key+"Oth" != null &&getElementByIdOrByName(key+"Oth") != null){
						dwr.util.setValue(key+"Oth", "");
					}
					if(key+"UNIT" != null &&getElementByIdOrByName(key+"UNIT") != null){
						dwr.util.setValue(key+"UNIT", "");
					}
					if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
						displaySelectedOptions(document.getElementById(key), key+"-selectedValues");
					}
					var type = "<%=batchrecinsert[i][5]%>"; 
					if(type == "1007" || type == "1013"){	
						if(document.getElementById(key) != null){ 
							autocompTxtValuesForJSPByElement(key);
						}
					}
					if (type == '1017') {
						repeatingBlockClearParticipant(key);
					}
					if (type == '1031') {
						repeatingBlockClearCodedWithSearch(key);
					}
					if(document.getElementById(key+"UNIT") != null ) {
						autocompTxtValuesForJSPByElement(key+"UNIT"); 
		            }
		        }
				<%} }%> 
			 }
			<%} }%> 
			var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
			if(rowhide!=null){
			rowhide.style.display = '';
			}
			var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
			if(rowshow!=null){
			rowshow.style.display = 'none';
			}
			var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
			if(rowshow1!=null){
			rowshow1.style.display = 'none';
			}
			
			viewed = -1;
			dwr.engine.endBatch();
			
			//Empty the currentKey field
			setTimeout(function(){
					 if(document.getElementById("NBS459")!=null && document.getElementById("NBS459") !=undefined)
					document.getElementById("NBS459").setAttribute("value","");
					},500);
			
			
			if(subSecNm=="RESULTED_TEST_CONTAINER"){//Hide organism name after adding the row to the batch
				hideOrganism();
				clearSingleSelectWithSearchButton('NBS_LAB220');
				disableUnits('LAB115');
			}
			if(subSecNm=="NBS_UI_2"){//Hide organism name after adding the row to the batch
				disableUnits('NBS372');
			}
			
    }


    function editClicked(eleid,subSecNm) {
    	var form = getCorrectForm();
    	
		unhideBatchImg(subSecNm);
		dwr.engine.beginBatch(); 
		clearClicked(subSecNm); 
		// id of the form "edit{id}", eg "edit42". We lookup the "42"
		var answer = answerCache[eleid.substring(4+subSecNm.length)];
		viewed = answer.id;	 
		var map = answer.answerMap;
		var mulVal;
		var partVal;
		var othVal;
		var selectedmulVal;
		var handlemulVal;
		var code = "1013";	
		var disableUnitsNumeric = "true";
	  //Specific code for  country to state and state to county mapping
	var stateCode = answer.answerMap['INV503'];
	if(stateCode != null && stateCode != "" &&getElementByIdOrByName('INV505') != null){
		stateCode = stateCode.substring(0, stateCode.indexOf("$$"));
		
		form.getDwrCountiesForState(stateCode, function(data) {
		DWRUtil.removeAllOptions("INV505");
		DWRUtil.addOptions("INV505", data, "key", "value" );
		});
	}
	var countryCode = answer.answerMap['INV502'];
	if(countryCode != null && countryCode != "" &&getElementByIdOrByName('INV503') != null){
		countryCode = countryCode.substring(0, countryCode.indexOf("$$"));
		form.getfilteredStatesByCountry(countryCode, function(data) {
		DWRUtil.removeAllOptions("INV503");
		DWRUtil.addOptions("INV503", data, "key", "value" );
		});
	}
	<% String[][] batchrecedit  =new String[20][7]; 
	if(map != null) {
		Iterator  itLab2 = map.entrySet().iterator(); 
		while(itLab2.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab2.next();%>
			if(subSecNm == "<%=pair.getKey().toString()%>"){
		<% batchrecedit  =  (String[][])pair.getValue();
		    for(int i=0;i<batchrecedit.length;i++){
			if(  null != batchrecedit[i][0]) { 
				 String str1 = batchrecedit[i][0] + "UNIT" ;  %>
			dwr.util.setValue( "<%=batchrecedit[i][0]%>","");
			<%  str1 = batchrecedit[i][0] + "UNIT" ;  %>
                        dwr.util.setValue( "<%=str1%>","");
                        dwr.util.setValue( "<%=batchrecedit[i][0]%>"+"Oth","");
			var type = "<%=batchrecedit[i][5]%>"; 
			if( type == "1007" || type == "1013"){
				if(document.getElementById("<%=batchrecedit[i][0]%>") != null){
					autocompTxtValuesForJSPByElement("<%=batchrecedit[i][0]%>");
				}
			}
			if (type == "1017") {
				repeatingBlockReadyParticipantEdit("<%=batchrecedit[i][0]%>");
			}
			<%  str1 = batchrecedit[i][0] + "UNIT"; %>
			if(document.getElementById("<%=str1%>") != null ) {
				   autocompTxtValuesForJSPByElement("<%=str1%>"); 
			}
			<%}}%>
			form.updateAnswer(answer,function(answer) { 
			
			   for (var key in answer.answerMap) { 
	    			var uiComponent = "";
	    			<% for(int i=0;i<batchrecedit.length;i++){
	    		    	if(  null != batchrecedit[i][0]) { %> 
					if(key == "<%=batchrecedit[i][0]%>" )
						uiComponent = "<%=batchrecedit[i][5]%>";
				<%}}%>					
			    if(answer.answerMap[key] != null && answer.answerMap[key] != '' &&  (uiComponent == "1013" || uiComponent == "1017" || uiComponent == "1031")){
				<% for(int i=0;i<batchrecedit.length;i++){
					  if(  null != batchrecedit[i][0]) { %> 
				if(key == "<%=batchrecedit[i][0]%>" && code == "<%=batchrecedit[i][5]%>"){
					mulVal = answer.answerMap[key]; 
					repeatingBlockHandleMultiVal (mulVal, key);
				} 
				else if (key == "<%=batchrecedit[i][0]%>" && "1017" == "<%=batchrecedit[i][5]%>"){
					    			partVal = answer.answerMap[key];
					    			repeatingBlockHandleEditParticipant (partVal, key, answer.answerMap[key + "Disp"]);
	    			}
	    			else if (key == "<%=batchrecedit[i][0]%>" && "1031" == "<%=batchrecedit[i][5]%>"){
					    			codedWithSearchVal = answer.answerMap[key];
	    							repeatingBlockHandleEditCodedWithSearch (codedWithSearchVal, key, answer.answerMap[key + "Description"],answer.answerMap[key + "DescriptionId"],answer.answerMap[key + "CodeId"], eleid);
	    			
	    			}
				if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
							displaySelectedOptions(document.getElementById(key), key+"-selectedValues");
				}

			<% }}%>	
			}else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf(":") !=  -1 &&getElementByIdOrByName(key+"Oth") != undefined){	
					var otherVal = answer.answerMap[key];			 
					dwr.util.setValue(key,otherVal.substring(0,otherVal.indexOf(":")));
					dwr.util.setValue(key+"Oth", otherVal.substring(otherVal.indexOf(":")+1));
					document.getElementById(key+"Oth").disabled=false;
			} else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf("$sn$") !=  -1){
					var fval = answer.answerMap[key];								 
					dwr.util.setValue(key,fval.substring(0,fval.indexOf("$sn$"))); 
					// alert(fval.substring(structVal.length+1,fval.length));
					dwr.util.setValue(key+"UNIT", fval.substring(fval.indexOf("$sn$")+4,fval.length));
			} else {    
				// alert( answer.answerMap[key] + "....");
				mulVal = answer.answerMap[key];
				if(mulVal  != null && mulVal.indexOf("$MulOth$") != -1){ 
					othVal =  mulVal.substring(mulVal.indexOf("$MulOth$")+8, mulVal.indexOf("#MulOth#"));
			                mulVal = mulVal.substring(0,mulVal.indexOf("$MulOth$") );
			                if(mulVal  != null && mulVal != ''){	
			                       getElementByIdOrByName(key).value = mulVal ;
					}
                                        if(othVal != null && othVal != ''){	 
			                        getElementByIdOrByName(key+"Oth").value = othVal;
			                }
			          }else{
					if(answer.answerMap[key] != null && answer.answerMap[key] != ''){ 
						document.getElementById(key).value = answer.answerMap[key];
						
					
					}
			} 
			//getElementByIdOrByName(key).value=answer.answerMap[key];        
			// alert("id = " +key+ " val = "+document.getElementById(key));
		}
		if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
				displaySelectedOptions(document.getElementById(key), key+"-selectedValues");
		}
		
		
		if(key=="NBS_LAB364" && subSecNm=="RESULTED_TEST_CONTAINER"){
			var valueNumeric = answer.answerMap[key];
			
			if(valueNumeric!=""){
				enableUnits('LAB115');
				disableUnitsNumeric="false";	
			}
		}
		
		if(key=="NBS369" && subSecNm=="NBS_UI_2"){
			var valueNumeric = answer.answerMap[key];
			
			if(valueNumeric!=""){
				enableUnits('NBS372');
				disableUnitsNumeric="false";	
			}
		}
						
						
	}
	for (var key in answer.answerMap) {
	     <% for(int i=0;i<batchrecedit.length;i++){
		  if(  null != batchrecedit[i][0]) { %> 
		var type = "<%=batchrecedit[i][5]%>"; 
		if(key == "<%=batchrecedit[i][0]%>" &&( type == "1007" || type == "1013")){
			if(document.getElementById(key) != null){
				autocompTxtValuesForJSPByElement(key);
			}
		}
		<% String str1 = batchrecedit[i][0] + "UNIT" ;  %>
		if(document.getElementById("<%=str1%>") != null &&getElementByIdOrByName("<%=str1%>").value != null &&getElementByIdOrByName("<%=str1%>").value != '') {
			autocompTxtValuesForJSPByElement("<%=str1%>"); 
		}
	<%}}%> 		
	}    
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide .style.display = 'none';	
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = '';
	}
	<% if(batchrecedit != null && batchrecedit.length > 0 ){
	    for(int i=0;i<batchrecedit.length;i++){
		  if(null != batchrecedit[i][0]) {  
			 String str = batchrecedit[i][0] +"L"; 
			 String str1 = batchrecedit[i][0] +"Oth"; %>
		var key = "<%=batchrecedit[i][0]%>";
		var keyL =  "<%=str%>";
		var keyOth = "<%=str1%>";
		if(document.getElementById(key) != null){
			// alert(document.getElementById(key).value);
			document.getElementById(key).disabled = false;
			document.getElementById(keyL).disabled = false;
			$j("#"+key).parent().parent().find("img").attr("disabled", false);
			$j("#"+key).parent().parent().find("input").attr("disabled", false);
			var calendars = $j( "img[src*='calendar.gif']");
    		if($j("#"+key).parent().parent().find(calendars)[0]!=undefined && $j("#"+key).parent().parent().find(calendars)[0]!=null)
				$j("#"+key).parent().parent().find(calendars)[0].attr("tabIndex", "0");
		}
		if(document.getElementById(key+"Oth") != null){
			//alert(document.getElementById(key+"Oth").value);
			//alert(document.getElementById(key+"Oth").disabled);
			document.getElementById(key+"Oth").disabled = false;
			//alert(document.getElementById(key+"OthL").disabled);
			document.getElementById(key+"OthL").disabled = false;
			$j("#"+key+"Oth").parent().parent().find("input").attr("disabled", false);
			enableOrDisableOther(key) ;
		}
		<%}}}%>
		
		if(disableUnitsNumeric=="true" && subSecNm=="RESULTED_TEST_CONTAINER")
    		disableUnits('LAB115');
    	if(disableUnitsNumeric=="true" && subSecNm=="NBS_UI_2")
    		disableUnits('NBS372');
    		
	  	});
     		}
    	<%}}%>
    	
    	
     	dwr.engine.endBatch(); 
 
     	setCurrentKeyfromSelectedRow(eleid, "Edit");
     	if(disableUnitsNumeric=="true" && subSecNm=="RESULTED_TEST_CONTAINER")
    		disableUnits('LAB115');	
    	if(disableUnitsNumeric=="true" && subSecNm=="NBS_UI_2")
    		disableUnits('NBS372');	
	}
	
	function fillTable(subSecNm,pattern,questionbody) {
	  var form = getCorrectForm();
	  var currentKey ="";
	  form.getAllAnswer(subSecNm,function(answer) {
		  // Delete all the rows except for the "pattern" row
		  dwr.util.removeAllRows(questionbody, { filter:function(tr) { return (tr.id != pattern); }});
          dwr.util.setEscapeHtml(false);
		  // Create a new set cloned from the pattern row
		  var ans, id,rowclass="";
		<% if(map !=  null){
			Iterator    itLab3 = map.entrySet().iterator(); 
			String[][] batchrecview  = null; 
			while(itLab3.hasNext()){  
				Map.Entry pair = (Map.Entry)itLab3.next();%>
		if(subSecNm == "<%=pair.getKey().toString()%>"){
			<% batchrecview =  (String[][])pair.getValue();%>  
		if(answer !=null && answer.length != 0){
			for (var i = 0; i < answer.length; i++){
				ans = answer[i];		; 
				id = ans.id;	     
				dwr.util.cloneNode(pattern, { idSuffix:id });
			  <% for(int i=0;i<batchrecview.length;i++){
				String checknull = batchrecview[i][0]; 
				if(checknull != null && checknull != ""){ %>
				for (var key in ans.answerMap) {
					if(!(key == "NBS459" || key == null || key == 'null') && key == "<%=batchrecview[i][0]%>"){
					    var val = ans.answerMap[key];
						<%if( "1017".equalsIgnoreCase(batchrecview[i][5]) ){%>
					    val = ans.answerMap[key + "Disp"] ? ans.answerMap[key + "Disp"] : val ;
                        <%}%>
					    val = repeatingBlockFillValue(val);
					    dwr.util.setValue("table" + key + id, val);
					}
					
					if(key == "NBS459" )
						currentKey = ans.answerMap[key]; 
				}
		<%}}%>
		$(pattern + id).style.display = "";   
		setRowKey(pattern + id, currentKey, subSecNm);
		
		answerCache[id] = ans;
		if(rowclass=="")
			rowclass="odd";	
			document.getElementById(pattern  + id).setAttribute("className",rowclass);
			// alert("Alert new "+document.getElementById(pattern  + id).getAttribute("className"));
			$j("#" + pattern  + id).css("background-color","white");
			// alert("Alert new "+document.getElementById(pattern  + id));
			if(rowclass=="odd"){
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
	    clearClicked(subSecNm); 
	    
	   <logic:equal name="PageSubForm" property="businessObjectType" value="LAB">
	   		if(subSecNm == 'RESULTED_TEST_CONTAINER'){
	   			addSusceptibilityToResultedTestBatchEntry(subSecNm,0,5);
	   		}
	   	</logic:equal>
	     
	  });
	  
	  
	 
    }

function deleteClicked(eleid,subSecNm,pattern,questionbody, noConfirm) { 
      var form = getCorrectForm();		
      var t =getElementByIdOrByName(subSecNm); 	
      var len=0;
      //t.style.display = "block";    
      <%    String[][]  batchrecinsert2  =new String[20][7];  
       if(map != null){ 
		Iterator itLab1 = map.entrySet().iterator(); 
		while(itLab1.hasNext()){ 		   
		Map.Entry pair = (Map.Entry)itLab1.next();%>
	   if(subSecNm == "<%=pair.getKey().toString()%>"){
		<% batchrecinsert2  =  (String[][])pair.getValue();  
		 for(int i=0;i<batchrecinsert2.length;i++){   
		String checknull1 = batchrecinsert2[i][0];  
		if(checknull1 != null && checknull1 != ""){%> 
			var key =   "<%=batchrecinsert2[i][0]%>";
			if(key != null && key != 'undefined' && key != ''){
				len =  len +1;
			}
		<%} }%>
	   	}
	   <%} }%>
	for (var i = 0; i <len+1; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}
	for (var i = 0; i < len+1; i ++)   {
		$j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");	
	}		
        // we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
        var answer = answerCache[eleid.substring(6+subSecNm.length)];
        if ((noConfirm!=null && noConfirm=="true") || confirm("You have indicated that you would like to delete this row. Would you like to continue with this action?")) {
             dwr.engine.beginBatch();
             form.deleteAnswer(answer);
             fillTable(subSecNm,pattern,questionbody);
         <%  String[][] batchrecdel  =new String[20][7];  
	     if(map != null){ 
		Iterator itLab8 = map.entrySet().iterator();
		while(itLab8.hasNext()){		   
		   Map.Entry pair = (Map.Entry)itLab8.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	<% batchrecdel  =  (String[][])pair.getValue();
           for(int i=0;i<batchrecdel.length;i++){   
	   String delstr =  batchrecdel[i][0];
	   if(delstr != null && delstr != ""){ %>	
	   var key =   "<%=batchrecdel[i][0]%>";
	   if(key != null &&getElementByIdOrByName(key) != null){
		dwr.util.setValue(key, "");
		if(key+"Oth" != null &&getElementByIdOrByName(key+"Oth") != null){
			dwr.util.setValue(key+"Oth", "");
		}
		if(key+"UNIT" != null &&getElementByIdOrByName(key+"UNIT") != null){
			dwr.util.setValue(key+"UNIT", "");
		}	     
		if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
			displaySelectedOptions(document.getElementById(key), key+"-selectedValues")
		}  
		var type = "<%=batchrecdel[i][5]%>";
		if(type == "1007" || type == "1013" || type == "1031"){
			autocompTxtValuesForJSPByElement(key);
			
			if(type=="1031")
				showSearchClearButtonCodedWithSearch(key);
				
		}
		if(document.getElementById(key+"UNIT") != null ) {
			autocompTxtValuesForJSPByElement(key+"UNIT"); 
		}
	}
	<%}}%>
	}
	<%}}%>
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide.style.display = '';
	}		                          
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = 'none';
	}
	clearClicked(subSecNm);
	viewed = -1;
        dwr.engine.endBatch();
    }
    
     <logic:equal name="PageSubForm" property="businessObjectType" value="LAB">
	   		deleteKeyFromHashMapInSession(eleid);
	   	</logic:equal>
	     
	     
    	
} 


function clearQuestion() {
    viewed = -1;
    dwr.util.setValues({subsecNm:"Others", id:viewed,answerMap:null });
}

function getDropDownValues(newValue)
{
	var form = getCorrectForm();
    form.getDropDownValues(newValue, function(data) {
        dwr.util.removeAllOptions(newValue);  
        dwr.util.addOptions(newValue,data,"key","value"); 
    });
}
                  
function viewClicked(eleid,subSecNm) {
	var form = getCorrectForm();	
	var t =getElementByIdOrByName(subSecNm);
	var len=0;
	//t.style.display = "block";
     <% String[][]  batchrecinsert1  =new String[20][7];  
	if(map != null){
		Iterator itLab1 = map.entrySet().iterator(); 
		while(itLab1.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab1.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
     <% batchrecinsert1  =  (String[][])pair.getValue();
        for(int i=0;i<batchrecinsert1.length;i++){   
            String checknull1 = batchrecinsert1[i][0]; 
            if(checknull1 != null && checknull1 != ""){%> 
		var key =   "<%=batchrecinsert1[i][0]%>";
		if(key != null && key != 'undefined' && key != ''){
			len =  len +1;
		}
    <%}}%>
        }
    <%}}%>
	for (var i = 0; i <len+1; i++){
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}
	for (var i = 0; i < len+1; i ++) {
		$j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");
	}
	var key;
	dwr.engine.beginBatch(); 
	clearClicked(subSecNm); 
	// id of the form "edit{id}", eg "edit42". We lookup the "42"
	var answer = answerCache[eleid.substring(4+subSecNm.length)];
	viewed = answer.id;	 
	var map = answer.answerMap;	 
	var mulVal;
	var partVal;
	var codedWithSearchVal;
	var selectedmulVal;
	var handlemulVal;
	var code = "1013";	
	<% String[][] batchrecedit1  = null;
	if(map != null) {
		Iterator  itLab2 = map.entrySet().iterator(); 
		while(itLab2.hasNext()){  
		Map.Entry pair = (Map.Entry)itLab2.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	<% batchrecedit1  =  (String[][])pair.getValue(); 
	     for(int i=0;i<batchrecedit1.length;i++){
		 if(  null != batchrecedit1[i][0]) {  
		 	String str1 = batchrecedit1[i][0] + "UNIT" ;  %>
		dwr.util.setValue( "<%=batchrecedit1[i][0]%>","");
		<%  str1 = batchrecedit1[i][0] + "UNIT" ;  %>
                dwr.util.setValue( "<%=str1%>","");
                dwr.util.setValue( "<%=batchrecedit1[i][0]%>"+"Oth","");
		var type = "<%=batchrecedit1[i][5]%>";
		if( type == "1007" || type == "1013"){
		    if(document.getElementById("<%=batchrecedit1[i][0]%>") != null){
			autocompTxtValuesForJSPByElement("<%=batchrecedit1[i][0]%>");
		    }
		}
		<%  str1 = batchrecedit1[i][0] + "UNIT" ;  %>
		if(document.getElementById("<%=str1%>") != null ) {
			autocompTxtValuesForJSPByElement("<%=str1%>"); 
		}
	<%}}%>
	form.updateAnswer(answer,function(answer) {
	    for (var key in answer.answerMap) {
	    	var uiComponent = "";
	    		<% for(int i=0;i<batchrecedit1.length;i++){
	    		    if(  null != batchrecedit1[i][0]) { %> 
				if(key == "<%=batchrecedit1[i][0]%>" )
					uiComponent = "<%=batchrecedit1[i][5]%>";
			<%}}%>		
		if(answer.answerMap[key] != null && answer.answerMap[key] != '' && (uiComponent == "1013" || uiComponent == "1017" || uiComponent == "1031")){
			<% for(int i=0;i<batchrecedit1.length;i++){
			     if(  null != batchrecedit1[i][0]) { %> 
			if(key == "<%=batchrecedit1[i][0]%>" && code == "<%=batchrecedit1[i][5]%>"){
				mulVal = answer.answerMap[key]; 
				repeatingBlockHandleMultiVal (mulVal, key);
	    		} else if (key == "<%=batchrecedit1[i][0]%>" && "1017" == "<%=batchrecedit1[i][5]%>"){
	    			partVal = answer.answerMap[key];
	    			repeatingBlockHandleViewParticipant (partVal, key, answer.answerMap[key + "Disp"]);
	    		}else if (key == "<%=batchrecedit1[i][0]%>" && "1031" == "<%=batchrecedit1[i][5]%>"){
	    			codedWithSearchVal = answer.answerMap[key];
	    			repeatingBlockHandleViewCodedWithSearch (codedWithSearchVal, key, answer.answerMap[key + "Description"],answer.answerMap[key + "DescriptionId"],answer.answerMap[key + "CodeId"]);
	    			
	    		}
	    			
	    if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
		displaySelectedOptions(document.getElementById(key), key+"-selectedValues")
	    }					
	<%}}%>					
	}else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf(":") !=  -1 &&getElementByIdOrByName(key+"Oth") != undefined){	
		var otherVal = answer.answerMap[key];
		dwr.util.setValue(key,otherVal.substring(0,otherVal.indexOf(":")));
		dwr.util.setValue(key+"Oth", otherVal.substring(otherVal.indexOf(":")+1));
		document.getElementById(key+"Oth").disabled=false;
	}else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf("$sn$") !=  -1){	
		var fval = answer.answerMap[key];
		dwr.util.setValue(key,fval.substring(0,fval.indexOf("$sn$"))); 
		dwr.util.setValue(key+"UNIT", fval.substring(fval.indexOf("$sn$")+4,fval.length));
	}else {    
		mulVal = answer.answerMap[key]; 						
		if(mulVal != null && mulVal.indexOf("$MulOth$") != -1){
			othVal =  mulVal.substring(mulVal.indexOf("$MulOth$")+8, mulVal.indexOf("#MulOth#"));
			mulVal = mulVal.substring(0,mulVal.indexOf("$MulOth$") );
			if(mulVal  != null && mulVal  != ''){	
			    getElementByIdOrByName(key).value  = othVal ;
			}
                 if(othVal != null && othVal != ''){
                       getElementByIdOrByName(key+"Oth").value = othVal ;
		}
	}else{
		if(answer.answerMap[key] != null && answer.answerMap[key] != ''){
			document.getElementById(key).value  = answer.answerMap[key];
		}
	}

	}
	if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){
		displaySelectedOptions(document.getElementById(key), key+"-selectedValues");
	}
	}
	for (var key in answer.answerMap) {
		<% for(int i=0;i<batchrecedit1.length;i++){
		     if(  null != batchrecedit1[i][0]) { %>
	    var type = "<%=batchrecedit1[i][5]%>";
	    if(key == "<%=batchrecedit1[i][0]%>" &&( type == "1007" || type == "1013")){
		if(document.getElementById(key) != null){
			autocompTxtValuesForJSPByElement(key);
		}
	    }
	<% String str1 = batchrecedit1[i][0] + "UNIT" ;  %>
	if(document.getElementById("<%=str1%>") != null &&getElementByIdOrByName("<%=str1%>").value != null &&getElementByIdOrByName("<%=str1%>").value != '') {
		autocompTxtValuesForJSPByElement("<%=str1%>"); 
	}	
	<%}}%> 	
	}
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide .style.display = 'none';
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = '';
	}
	<% if(batchrecedit1 != null && batchrecedit1.length > 0 ){
	     for(int i=0;i<batchrecedit1.length;i++){
		  if(null != batchrecedit1[i][0]) { 
			 String str = batchrecedit1[i][0] +"L";
		 	String str1 = batchrecedit1[i][0] +"Oth"; %>
		var key = "<%=batchrecedit1[i][0]%>";
		var keyL =  "<%=str%>";
		var keyOth = "<%=str1%>";
		if(document.getElementById(key) != null){
			document.getElementById(key).disabled = false;
			document.getElementById(keyL).disabled = false;
			$j("#"+key).parent().parent().find("img").attr("disabled", false);
			var calendars = $j( "img[src*='calendar.gif']");
    		if($j("#"+key).parent().parent().find(calendars)[0]!=undefined && $j("#"+key).parent().parent().find(calendars)[0]!=null)
				$j("#"+key).parent().parent().find(calendars)[0].attr("tabIndex", "0");
			$j("#"+key).parent().parent().find("input").attr("disabled", false);
		}
		if(document.getElementById(key+"Oth") != null){					
			document.getElementById(key+"Oth").disabled = true;
			$j("#"+key+"Oth").parent().parent().find("input").attr("disabled", false);
		}
	<%}}}%>		

	<% String[][] batchrecview  = null;
	if(map != null) {
		Iterator  itLab21 = map.entrySet().iterator(); 
		while(itLab21.hasNext()){  
			pair = (Map.Entry)itLab21.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	    <%  batchrecview  =  (String[][])pair.getValue(); 
	    for(int i=0;i<batchrecview.length;i++){
		 if(null != batchrecview[i][0]) {  
		     String str = batchrecview[i][0] +"L"; 
		     String str1 = batchrecview[i][0] +"Oth"; %>
	    key = "<%=batchrecview[i][0]%>";
	    component = "<%=batchrecview[i][5]%>";
	    var keyL =  "<%=str%>";
	    var keyOth = "<%=str1%>";
	    if(document.getElementById(key) != null){
	    	
			document.getElementById(key).disabled = true;
		document.getElementById(keyL).disabled = true;
		$j("#"+key).parent().parent().find("img").attr("disabled", true);
		$j("#"+key).parent().parent().find("img").attr("tabIndex", "-1");
		$j("#"+key).parent().parent().find("input").attr("disabled", true);
	    }
	if(key+"-selectedValues" != null &&getElementByIdOrByName(key+"-selectedValues") != null){		
		document.getElementById(key+"-selectedValues").disabled = true;
	}
	if(document.getElementById(key+"Oth") != null){
		document.getElementById(key+"Oth").disabled = true;
		document.getElementById(key+"OthL").disabled = true;
		$j("#"+key+"Oth").parent().parent().find("input").attr("disabled", 	true);
	}

	<%}}%>
	}		        
	<%}}%>				               
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide .style.display = 'none';		
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = '';
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = 'none'; 
	}
	});
	}
	<%}}%>
	dwr.engine.endBatch();
	
	
	setCurrentKeyfromSelectedRow(eleid, "View");
	
        }      //viewClicked
        
   function clearClicked(subSecNm) {
   
   emptyCurrentKey();
	var key;
	var subsectionDisabled = $j("#"+subSecNm).hasClass("batchSubSectionDisabled");
	<% String[][] batchrecclear  = null;
	if(map != null) {
		Iterator  itLab5 = map.entrySet().iterator(); 
		while(itLab5.hasNext()){  
			Map.Entry pair = (Map.Entry)itLab5.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	<%  batchrecclear  =  (String[][])pair.getValue();
	 for(int i=0;i<batchrecclear.length;i++){ 
	 	if (batchrecclear[i][0] == null) continue;
		String checknull1 = batchrecclear[i][0];%>    
	    var key =   "<%=batchrecclear[i][0]%>";
	    var componentType = "<%=batchrecclear[i][5]%>";
	<%if(checknull1 != null && checknull1 != "" ){%>
	    if(key != null &&getElementByIdOrByName(key) != null && !subsectionDisabled){
		repeatingBatchClearFields(key, componentType);
	}   
	<%}}%>
	viewed = -1;
	}
	<%}}%>
	var rowhide =getElementByIdOrByName("AddButtonToggle"+subSecNm);
	if(rowhide!=null){
	rowhide.style.display = ''; 
	}
	var rowshow =getElementByIdOrByName("AddNewButtonToggle"+subSecNm);
	if(rowshow!=null){
	rowshow.style.display = 'none';             
	}
	var rowshow1 =getElementByIdOrByName("UpdateButtonToggle"+subSecNm);
	if(rowshow1!=null){
	rowshow1.style.display = 'none';
	}
	
	if(subSecNm=="RESULTED_TEST_CONTAINER"){
		hideOrganism(document);
		enableOrDisableNumericResult('NBS_LAB364');
		}
	if(subSecNm=="NBS_UI_2"){
		enableOrDisableNumericResult('NBS369');
		}
		
    } //clearClicked       

   function clearRepeatingblk(subSecNm)    {
   var form = getCorrectForm();
	form.clearRepeatingblk(subSecNm);           		
	fillTable(subSecNm,"pattern"+subSecNm,"questionbody"+subSecNm);	
   }
   function unhideBatchImg(subSecNm)
   {
    	var t =getElementByIdOrByName(subSecNm); 	
        var len=0;
        
    	//t.style.display = "block";    
    	<% if(map != null){ 
		Iterator itLab1 = map.entrySet().iterator();
		while(itLab1.hasNext()){   
		Map.Entry pair = (Map.Entry)itLab1.next();%>
	if(subSecNm == "<%=pair.getKey().toString()%>"){
	<% batchrecinsert  =  (String[][])pair.getValue();   
         for(int i=0;i<batchrecinsert.length;i++){  
	    String checknull1 = batchrecinsert[i][0];  
	    if(checknull1 != null && checknull1 != ""){%> 
		var key =   "<%=batchrecinsert[i][0]%>";
		if(key != null && key != 'undefined' && key != ''){
			len = len +1;
		}
	<%}}%>
	 }
	<%}}%>
	for (var i = 0; i < len+1; i ++)   {
		//alert($j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#95BAEF"));
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#BCD4F5");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#BCD4F5");
	}
	//  	alert( $j("#" + "questionbody"  +subSecNm));
	for (var i = 0; i < len+1; i ++)   {
		$j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");	
	}					       
	$j("#" + "nopattern"  +subSecNm).css("background-color","white");
	
	
    } //unhide batch image		                  
                     	
   function rollingNoteSetUserDate(elementId) {
		             <%
		             String theUserName = "";
		               try {
		                   NBSSecurityObj so = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		                   if (so != null) {
		                         theUserName = so.getTheUserProfile().getTheUser().getFirstName() + " " + so.getTheUserProfile().getTheUser().getLastName();
		                   }   
		               }
		               catch (Exception e) {
		               }           
		            %>
		          	var currentUser = "<%=theUserName%>";
		          	dwr.util.setValue(elementId+"User",currentUser);
		    
		    
		    	var todayDT = new Date();
		    	thedd = todayDT.getDate().toString();
		    	if (parseInt(thedd) < 10) thedd = "0" + thedd;
		    	themm = todayDT.getMonth()+1;//January is 0!
		    	if (parseInt(themm) < 10) themm = "0" + themm;
		    	theyyyy = todayDT.getFullYear();
		    	var theDate = themm + "/" + thedd + "/" + theyyyy;
		    	var theMinutes = todayDT.getMinutes();
		    	if (theMinutes < 10){
				theMinutes = "0" + theMinutes;
			}
		    	var theTime = " "+ todayDT.getHours() + ":" + theMinutes;
		    	dwr.util.setValue(elementId+"Date",theDate+theTime);
      }
        
      
               
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
      String [] sectionNames  = {"Susceptibility Test"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";

%> 
 <logic:notEqual name="PageSubForm"  property="businessObjectType" value="ISO">
	 <logic:notEqual name="PageSubForm"  property="businessObjectType" value="SUS">

	 <logic:notEqual name="PageSubForm"  property="businessObjectType" value="LAB">
	
 			<body class="popup" onunload="handlePageUnload(true, event); return false;" onload="startCountdown();autocompTxtValuesForJSP();pageCreateLoad('${PageForm.attributeMap.selectEltIdsArray}');
		    populateBatchRecords();
		    attachMoveFocusFunctionToTabKey();
		    pgOnLoadCalcRptAge('DEM115','INV2001','INV2002','NBS096','NBS104');
		    
		    pgCheckOnloadOtherEntryAllowedFields();pgCheckDynamicRulesOnLoad();pgCheckForFieldsToHighlightOnEdit();pgPopulateCounties();addRolePresentationToTabsAndSections();">
    
     </logic:notEqual>
    
     <logic:equal name="PageSubForm"  property="businessObjectType" value="LAB">
		 <body class="popup" onunload="handlePageUnload(true, event); return false;" onload="startCountdown();autocompTxtValuesForJSP();pageCreateLoad('${PageForm.attributeMap.selectEltIdsArray}',true);
		    populateBatchRecords();
		    attachMoveFocusFunctionToTabKey();
		    pgOnLoadCalcRptAge('DEM115','INV2001','INV2002','NBS096','NBS104');
		    
		    pgCheckOnloadOtherEntryAllowedFields();pgCheckDynamicRulesOnLoad();pgCheckForFieldsToHighlightOnEdit();pgPopulateCounties();addRolePresentationToTabsAndSections();populateSingleSelectInLabs();hideOrganism();disableUnits('LAB115');setOrganismShowHideToEventOnClick();addMethodToOnchangeNumericLabReportOnLoad('NBS_LAB364');hideSameAsReportingFacilityCheckBoxOnLoad();selectTabLab('<%=request.getAttribute("TabtoFocus")%>'); hideOrShowRetainButtonsForNextEntry();readOnlyReportingFacilityOnEdit()">
    
	 </logic:equal>
 
  </logic:notEqual>
 </logic:notEqual>
 
  <logic:equal name="PageSubForm"  property="businessObjectType" value="ISO">
  <body class="popup" onload="startCountdown();autocompTxtValuesForJSP();pageCreateLoad('${PageForm.attributeMap.selectEltIdsArray}');
   
    attachMoveFocusFunctionToTabKey();
    pgOnLoadCalcRptAge('DEM115','INV2001','INV2002','NBS096','NBS104');
    
    pgCheckOnloadOtherEntryAllowedFields();pgCheckDynamicRulesOnLoad();pgCheckForFieldsToHighlightOnEdit();pgPopulateCounties();addRolePresentationToTabsAndSections();onloadBlockParent();" onunload="unloadSubForms(true, event);">
    
  </logic:equal>
  
   <logic:equal name="PageSubForm"  property="businessObjectType" value="SUS">
  <body class="popup" onload="startCountdown();autocompTxtValuesForJSP();disableUnits('NBS372');pageCreateLoad('${PageForm.attributeMap.selectEltIdsArray}');
    populateBatchRecords();
    attachMoveFocusFunctionToTabKey();
    pgOnLoadCalcRptAge('DEM115','INV2001','INV2002','NBS096','NBS104');
    addMethodToOnchangeNumericLabReportOnLoad('NBS369');
    pgCheckOnloadOtherEntryAllowedFields();pgCheckDynamicRulesOnLoad();pgCheckForFieldsToHighlightOnEdit();pgPopulateCounties();addRolePresentationToTabsAndSections();populateDrugTestData();populateCodedResultDataSusc(); populateResultMethodDataSusc();onloadBlockParent();" onunload="unloadSubForms(true, event);">
    
  </logic:equal>
 
			
        <div id="pageview"></div>
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
        <html:hidden name="PageForm" property="pageClientVO.answer(DEM165)" styleId="DEM165_Hidden"/>
		<html:hidden name="PageForm" property="pageClientVO.answer(DEM165_W)" styleId="DEM165_W_Hidden"/>
		
            <html:form action="/PageAction.do"> 
                <!-- Page title -->
            <logic:notEqual name="PageSubForm"  property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
				<div class="popupTitle"> ${BaseForm.pageTitle} </div>
				<%String phcUID = (String) request.getAttribute("phcUID");%>
				<!-- Top button bar -->
				<div class="popupButtonBar">
				
				    <logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
				        <input type="button" name="Submit" value="Submit" onclick="saveSubForm();"/>
	            		<input type="button" name="Cancel" value="Cancel" onclick="cancelSubForm()" />	
					</logic:equal>
					<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
				        <input type="button" name="Submit" value="Submit" onclick="saveSubForm();"/>
	            		<input type="button" name="Cancel" value="Cancel" onclick="cancelSubForm()" />	
					</logic:equal>
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
				    <logic:notEqual name="PageSubForm" property="businessObjectType" value="SUS">
				    
				        <input type="button" name="Submit" value="Submit" onclick="saveForm();"/>
	            	    <input type="button" name="Cancel" value="Cancel" onclick="cancelForm()" />
					</logic:notEqual>
					</logic:notEqual>
					
							
	   
	        </logic:notEqual>
	        </div>            
            <!-- Body div -->
            <div id="bd">
            	<logic:equal name="PageSubForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
					 <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>     
					 <%@ include file="/jsp/topbuttonbarFullScreenWidth.jsp" %>
				</logic:equal> 
				
                       <!-- Page Errors -->
                       <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
					   
                        <!-- Note: No Patient Summary at top of Generic Window  -->
                        <logic:equal name="PageSubForm" property="businessObjectType" value="VAC">
							<%@ include file="/pagemanagement/GenericEventSummary.jsp" %>
						</logic:equal>
						<logic:equal name="PageSubForm" property="businessObjectType" value="LAB">
							<%@ include file="/pagemanagement/LabEventSummary.jsp" %>
						</logic:equal>
						                     
                        <!-- Required Field Indicator -->
                        <div style="text-align:right; width:100%;"> 
                            <span class="boldTenRed"> * </span>
                            <span class="boldTenBlack"> Indicates a Required Field </span>  
                        </div>

                     <!-- Error Messages using Action Messages-->
				    <div id="globalFeedbackMessagesBar" class="screenOnly">
				        <logic:messagesPresent name="error_messages">
				        <div class="infoBox errors" id="errorMessages">
				            <b> <a name="errorMessagesHref"></a> Please fix the following errors:</b> <br/>
				            <ul>
				                <html:messages id="msg" name="error_messages">
				                    <li> <bean:write name="msg" /> </li>
				                </html:messages>
				            <ul>
				        </div>
				    </logic:messagesPresent>
				    </div> 
                
                        
      
     <!-- ################### PAGE TAB ###################### - - -->
      <layout:tabs width="100%" styleClass="tabsContainer">
         		
         	
         	 
         	 
		 
      		 <layout:tab key="Susceptibility Test">     
      		<jsp:include page="SusceptibilityTest.jsp"/>
             	</layout:tab>               	
             	
                
    
      </layout:tabs>  
	  
			 <!-- Bottom button bar -->
	  	    <div class="popupButtonBar">
	  	    
	  	    
				    <logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
				        <input type="button" name="Submit" value="Submit" onclick="saveSubForm();"/>
	            		<input type="button" name="Cancel" value="Cancel" onclick="cancelSubForm()" />	
					</logic:equal>
					
				    <logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
				        <input type="button" name="Submit" value="Submit" onclick="saveSubForm();"/>
	            		<input type="button" name="Cancel" value="Cancel" onclick="cancelSubForm()" />	
					</logic:equal>
					
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="LAB">
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="SUS">
					
				        <input type="button" name="Submit" value="Submit" onclick="saveForm();"/>
	            	    <input type="button" name="Cancel" value="Cancel" onclick="cancelForm()" />
	            	</logic:notEqual>	
	            	</logic:notEqual>	    
					</logic:notEqual>
				
				
		    </div>
		    
	    	<logic:equal name="PageSubForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
				<%@ include file="/jsp/bottombuttonbarFullScreenWidth.jsp" %>
       		</logic:equal>
               </html:form>
          </div> <!-- Container Div -->
    </body>
     
</html>
		

	