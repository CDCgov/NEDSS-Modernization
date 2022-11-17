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
    <script language="JavaScript" src="Coinfection.js"></script>     
        <script language="JavaScript"> 
          
	<%
           response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
           response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
    	%>   
        function cancelForm()
        {
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
                document.forms[0].action ="${PageForm.attributeMap.Cancel}";
                // pass control to parent's call back handler
  
		var opener = getDialogArgument();                    
		// refresh parent's form
		opener.document.forms[0].action ="/nbs/PageAction.do?method=closeWindow";
                opener.document.forms[0].submit();
                window.returnValue = "windowClosed"; 
		window.close();                
            } else {
                return false;
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
			   opener.document.forms[0].action ="/nbs/PageAction.do?method=closeWindow";
			   opener.document.forms[0].submit();          	                    
			   window.returnValue = "windowClosed"; 
			   window.close();
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
 
            
              
          
     <!-- =========Begin Javascript Functions for Dynamic Rules==========-->
     
  function ruleDCompDEM1156358() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array(); 

 if ((getElementByIdOrByName("DEM115").value)==''){ 
 return {elements : errorElts, labels : errorMsgs}; }
 var sourceStr =getElementByIdOrByName("DEM115").value;
 var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
 var targetElt;
 var targetStr = ''; 
 var targetDate = '';
 targetStr =getElementByIdOrByName("NBS104") == null ? "" :getElementByIdOrByName("NBS104").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("NBS104");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Information As of Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("NBS104"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
  function ruleDCompDEM1156359() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array(); 

 if ((getElementByIdOrByName("DEM115").value)==''){ 
 return {elements : errorElts, labels : errorMsgs}; }
 var sourceStr =getElementByIdOrByName("DEM115").value;
 var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
 var targetElt;
 var targetStr = ''; 
 var targetDate = '';
 targetStr =getElementByIdOrByName("DEM128") == null ? "" :getElementByIdOrByName("DEM128").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("DEM128");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Deceased Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("DEM128"); 
}
  }
 targetStr =getElementByIdOrByName("VAC103") == null ? "" :getElementByIdOrByName("VAC103").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("VAC103");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Vaccine Administered Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("VAC103"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
  function ruleDCompVAC1096360() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array(); 

 if ((getElementByIdOrByName("VAC109").value)==''){ 
 return {elements : errorElts, labels : errorMsgs}; }
 var sourceStr =getElementByIdOrByName("VAC109").value;
 var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
 var targetElt;
 var targetStr = ''; 
 var targetDate = '';
 targetStr =getElementByIdOrByName("VAC103") == null ? "" :getElementByIdOrByName("VAC103").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  >=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("VAC109");
 var targetDateEle=getElementByIdOrByName("VAC103");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Vaccine Expiration Date");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Vaccine Administered Date");
    errorMsgs[i]=srca2str + " must be  >=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("VAC103"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
  function ruleEnDisDEM1276361()
{
 var foo = [];
$j('#DEM127 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('DEM128');
 } else { 
pgDisableElement('DEM128');
 }   
}
      
  function ruleRequireIfVAC1066362()
{
 var foo = [];
$j('#VAC106 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('D',foo) > -1) || ($j.inArray('H',foo) > -1) || ($j.inArray('N',foo) > -1) || ($j.inArray('M',foo) > -1) || ($j.inArray('UNK',foo) > -1) || ($j.inArray('W',foo) > -1) || ($j.inArray('Y',foo) > -1)){
pgRequireElement('VAC105');
 } else { 
pgRequireNotElement('VAC105');
 }   
}
      
     function pgCheckForDynamicRuleErrorsOnSubmit() {
     	var errorElts = new Array();
    	var errorLabels = new Array();
    	var retVal;
     	retVal = ruleDCompDEM1156358();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = ruleDCompDEM1156359();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = ruleDCompVAC1096360();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        
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
           
           	ruleEnDisDEM1276361();ruleRequireIfVAC1066362();
          
		  csMotherInfantRequired();
       return;
      }

	<!-- === batch subsection add edit check functions (if any) follow ===-->
     	
      
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
							}
			 			}
			<%} }%>
				} 
			<%} }%>
			var batchentry = { subsecNm:subSecNm, id:viewed,answerMap:map};  
			 if(emptyrow=="yes"){
					var errorrow= subSecNm+"errorMessages";
					displayErrors(errorrow, " At least one field must be entered when adding a repeating block.");
		            dwr.engine.endBatch();	
		            return false;
			 }  				

			JPageForm.setAnswer(batchentry,"<%=request.getSession()%>");
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
    }


    function editClicked(eleid,subSecNm) {
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
	  //Specific code for  country to state and state to county mapping
	var stateCode = answer.answerMap['INV503'];
	if(stateCode != null && stateCode != "" &&getElementByIdOrByName('INV505') != null){
		stateCode = stateCode.substring(0, stateCode.indexOf("$$"));
		JPageForm.getDwrCountiesForState(stateCode, function(data) {
		DWRUtil.removeAllOptions("INV505");
		DWRUtil.addOptions("INV505", data, "key", "value" );
		});
	}
	var countryCode = answer.answerMap['INV502'];
	if(countryCode != null && countryCode != "" &&getElementByIdOrByName('INV503') != null){
		countryCode = countryCode.substring(0, countryCode.indexOf("$$"));
		JPageForm.getfilteredStatesByCountry(countryCode, function(data) {
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
			JPageForm.updateAnswer(answer,function(answer) { 
			
			   for (var key in answer.answerMap) { 
	    			var uiComponent = "";
	    			<% for(int i=0;i<batchrecedit.length;i++){
	    		    	if(  null != batchrecedit[i][0]) { %> 
					if(key == "<%=batchrecedit[i][0]%>" )
						uiComponent = "<%=batchrecedit[i][5]%>";
				<%}}%>					
			    if(answer.answerMap[key] != null && answer.answerMap[key] != '' &&  (uiComponent == "1013" || uiComponent == "1017")){
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
	  	});
     		}
    	<%}}%>
     	dwr.engine.endBatch(); 
	}
	
	function fillTable(subSecNm,pattern,questionbody) {
	  JPageForm.getAllAnswer(subSecNm,function(answer) {
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
					if(!(key == null || key == 'null') && key == "<%=batchrecview[i][0]%>"){
					    var val = ans.answerMap[key];
						<%if( "1017".equalsIgnoreCase(batchrecview[i][5]) ){%>
					    val = ans.answerMap[key + "Disp"] ? ans.answerMap[key + "Disp"] : val ;
                        <%}%>
					    val = repeatingBlockFillValue(val);
					    dwr.util.setValue("table" + key + id, val);
					}
				}
		<%}}%>
		$(pattern + id).style.display = "";   
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
	  });
    }

function deleteClicked(eleid,subSecNm,pattern,questionbody) { 
                		
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
        if (confirm("You have indicated that you would like to delete this row. Would you like to continue with this action?")) {
             dwr.engine.beginBatch();
             JPageForm.deleteAnswer(answer);
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
		if(type == "1007" || type == "1013"){
			autocompTxtValuesForJSPByElement(key);
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
} 


function clearQuestion() {
    viewed = -1;
    dwr.util.setValues({subsecNm:"Others", id:viewed,answerMap:null });
}

function getDropDownValues(newValue)
{
    JPageForm.getDropDownValues(newValue, function(data) {
        dwr.util.removeAllOptions(newValue);  
        dwr.util.addOptions(newValue,data,"key","value"); 
    });
}
                  
function viewClicked(eleid,subSecNm) {	
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
	JPageForm.updateAnswer(answer,function(answer) {
	    for (var key in answer.answerMap) {
	    	var uiComponent = "";
	    		<% for(int i=0;i<batchrecedit1.length;i++){
	    		    if(  null != batchrecedit1[i][0]) { %> 
				if(key == "<%=batchrecedit1[i][0]%>" )
					uiComponent = "<%=batchrecedit1[i][5]%>";
			<%}}%>		
		if(answer.answerMap[key] != null && answer.answerMap[key] != '' && (uiComponent == "1013" || uiComponent == "1017")){
			<% for(int i=0;i<batchrecedit1.length;i++){
			     if(  null != batchrecedit1[i][0]) { %> 
			if(key == "<%=batchrecedit1[i][0]%>" && code == "<%=batchrecedit1[i][5]%>"){
				mulVal = answer.answerMap[key]; 
				repeatingBlockHandleMultiVal (mulVal, key);
	    		} else if (key == "<%=batchrecedit1[i][0]%>" && "1017" == "<%=batchrecedit1[i][5]%>"){
	    			partVal = answer.answerMap[key];
	    			repeatingBlockHandleViewParticipant (partVal, key, answer.answerMap[key + "Disp"]);
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
	    	if (component != "1019") 
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
        }      //viewClicked
        
   function clearClicked(subSecNm) {
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
    } //clearClicked       

   function clearRepeatingblk(subSecNm)    {
	JPageForm.clearRepeatingblk(subSecNm);           		
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
      String [] sectionNames  = {"Patient Information","Vaccination"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";

%> 

    <body class="popup" onunload="handlePageUnload(true, event); return false;" onload="startCountdown();autocompTxtValuesForJSP();pageCreateLoad('${PageForm.attributeMap.selectEltIdsArray}');
    populateBatchRecords();
    attachMoveFocusFunctionToTabKey();
    pgOnLoadCalcRptAge('DEM115','INV2001','INV2002','NBS096','NBS104');
    
    pgCheckOnloadOtherEntryAllowedFields();pgCheckDynamicRulesOnLoad();pgCheckForFieldsToHighlightOnEdit();pgPopulateCounties();addRolePresentationToTabsAndSections();">
        <div id="pageview"></div>
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
        <html:hidden name="PageForm" property="pageClientVO.answer(DEM165)" styleId="DEM165_Hidden"/>
		<html:hidden name="PageForm" property="pageClientVO.answer(DEM165_W)" styleId="DEM165_W_Hidden"/>
		
            <html:form action="/PageAction.do"> 
                <!-- Page title -->
	        <div class="popupTitle"> ${BaseForm.pageTitle} </div>
            <%String phcUID = (String) request.getAttribute("phcUID");%>
	        <!-- Top button bar -->
	      	<div class="popupButtonBar">
	            <input type="button" name="Submit" value="Submit" onclick="saveForm();"/>
	            <input type="button" name="Cancel" value="Cancel" onclick="cancelForm()" />
	        </div>            
            <!-- Body div -->
            <div id="bd">
                       <!-- Page Errors -->
                       <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
					   
                        <!-- Note: No Patient Summary at top of Generic Window  -->
                        <logic:equal name="PageForm" property="businessObjectType" value="VAC">
							<%@ include file="/pagemanagement/GenericEventSummary.jsp" %>
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
         		
         	
         	 
         	 
		 
      		 <layout:tab key="Patient">     
      		<jsp:include page="Patient.jsp"/>
             	</layout:tab>               	
             	
        		
         	
         	 
         	 
		 
      		 <layout:tab key="Vaccination">     
      		<jsp:include page="Vaccination.jsp"/>
             	</layout:tab>               	
             	
                
    
      </layout:tabs>  
	  
			 <!-- Bottom button bar -->
	  	    <div class="popupButtonBar">
		        <input type="button" name="Submit" value="Submit" onclick="saveForm()"/>
		        <input type="button" name="Cancel" value="Cancel" onclick="cancelForm()" />
		    </div>
	    	
               </html:form>
          </div> <!-- Container Div -->
    </body>
     
</html>
		

	