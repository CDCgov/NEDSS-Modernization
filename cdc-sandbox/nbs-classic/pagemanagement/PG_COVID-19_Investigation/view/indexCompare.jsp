<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN VIEW INDEX JSP PAGE GENERATE ###- - -->
    <html lang="en">
    <head>
     <%@ page import="java.util.*" %>
     <%@ include file="/jsp/tags.jsp" %>
    <title>NBS:Investigation: Generic</title>
    <%@ include file="/jsp/resources.jsp" %>        
 	 <link href="compare.css" type="text/css" rel="stylesheet">
     <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>
     <SCRIPT Language="JavaScript" Src="compareSpecific.js"></SCRIPT>
    
        <script language="JavaScript"> 
              
    
     function cancelForm(){
        	 document.forms[0].action = "${PageForm.attributeMap2.linkValueCancelCompare}";
        	document.forms[0].submit();
        }
    function mergeInvestigations(){
		//Do validations here
		
	
	
		if(!validateToMerge()){
			return false;
	 	}else{
	 	
	 		var survivor1 = document.getElementById("survivingRecord").checked;
			var survivor2 = document.getElementById("survivingRecord2").checked;

			var surv = "1";

			if(survivor2==true)
				surv="2";
			
			document.forms[0].action = "${PageForm.attributeMap.linkValueMergeInvestigations}"+"&survivor="+surv;
	        document.forms[0].submit(); 
		}
	}
    
    function checkSurvivorForPrint(){
		var survivor = '<%=request.getAttribute("survivor")%>';
		
		if(survivor.indexOf("1")!=-1)
			document.getElementById("survivingRecord").checked=true;
		if(survivor.indexOf("2")!=-1)
				document.getElementById("survivingRecord2").checked=true;
	}
    function deleteForm() {
      document.forms[0].target = "";
      var confirmMsg = "You have indicated that you would like to delete this Investigation. By doing so, this record will no longer be available in the system, and all Contact Records and Interview Records that were created within this investigation will be deleted. Would you like to continue with this action?";
      if (confirm(confirmMsg)) {
        document.forms[0].action = "${PageForm.attributeMap.deleteButtonHref}";
      } else {
        return false;
      }
    }
      
    function checkLoadNotif() {
      var reqForNotif = '<%=request.getAttribute("REQ_FOR_NOTIF")%>';
      if (reqForNotif != "null") {
        createPamNotification();
      } else {
        return
      }
    }

      /** Popup a child window and load the page that is currently being 
       *   viewed on the parent window. The call to load the page includes an additional 
       *   parameter called 'mode' that has a value of print. This value is used to load
       *   a seperate css file named 'print.css' when the page loads in the child window.
       */
       
	
	
	function showPrintFriendlyPage() {
	
	  var survivor1 = document.getElementById("survivingRecord").checked;
	  var survivor2 = document.getElementById("survivingRecord2").checked;

	  var surv = "";

	  if(survivor1==true)
		  surv="1";
	  if(survivor2==true)
		  surv+="2";
	  if(survivor1==false && survivor2==false)
		  survivor="0";
			  
	  var divElt =getElementByIdOrByName("pageview");
	  divElt.style.display = "block";
	  var o = new Object();
	  o.opener = self;
	  var URL = "/nbs/PageAction.do?method=viewLoad&mode=print&ContextAction=CompareInvestigations&survivor="+surv;
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

	function printForm() {
	  document.forms[0].target = "_blank";
	  document.forms[0].action = "/nbs/PageAction.do?method=printLoad";
	}

	function closePrinterFriendlyWindow() {
	  self.close();

	  var opener = getDialogArgument(); 
	  var pview = opener.document.getElementById("pageview")
	    pview.style.display = "none";

	  return false;
	}

	function editForm() {
	  var notificationCheck =getElementByIdOrByName("NotificationExists");
	  if (notificationCheck != null && notificationCheck.value == 'true' &&
		getElementByIdOrByName("notificationSection")!=null && getElementByIdOrByName("notificationSection").innerText.indexOf("NND")!=-1) {
		var confirmMsg = "A notification message request exists against this event. If you continue with this action, you may change the content of the message. Select OK to continue or Cancel to not continue.";
		if (confirm(confirmMsg)) {
		  document.forms[0].target = "";
		  document.forms[0].action = "${PageForm.attributeMap.Edit}";
		} else {
		  return false;
		}
	  } else {
		document.forms[0].target = "";
		document.forms[0].action = "${PageForm.attributeMap.Edit}";
	  }
	  return true;
	}
        
    function manageAssociations() {
      var notificationCheck =getElementByIdOrByName("NotificationExists");
      if (notificationCheck != null && notificationCheck.value == 'true') {
        var confirmMsg = "A notification message request exists against this event. If you continue with this action, you may change the content of the message. Select OK to continue or Cancel to not continue.";
        if (confirm(confirmMsg)) {
          document.forms[0].target = "";
          document.forms[0].action = "${PageForm.attributeMap.ManageEvents}";
        } else {
          return false;
        }
      } else {
        document.forms[0].target = "";
        document.forms[0].action = "${PageForm.attributeMap.ManageEvents}";
      } 
    }
	
	function appendPatientSearch(patientRevision,caseLocalId,perMprUid ){
	         
		$j("table#contactNamedByPatListID").append("<tr> <td colspan=\"6\" style=\"text-align:right;\"> <input type=\"button\" name=\"submitct\" value=\"Add New Contact Record\"  onclick=\"SearchPatientPopUp('" + patientRevision + "','" + caseLocalId + "','" + perMprUid + "');\"/> </td> </tr>");
	        		
	}   
	
	function appendManageAssociation(patientRevision,caseLocalId,perMprUid ){
	        
		$j("table#patNamedByContactsListID").append("<tr> <td colspan=\"6\" style=\"text-align:right;\"> <input type=\"button\" name=\"submitmanage\" value=\"Manage Contact Associations\"  onclick=\"ManageCtAssociationtPopUp('" + patientRevision + "','" + caseLocalId + "','" + perMprUid+ "');\"/> </td> </tr>");
	}  
	 	
	
    function reloadInvs(filler) {

      JPageForm.callChildForm(filler.value, function (data) {});
      setTimeout("reldPage()", 1000);
    }

    function selectTabOnSubmit() {     
		var contactTabtoFocus='<%=request.getAttribute("ContactTabtoFocus")%>';
		if(contactTabtoFocus != null && contactTabtoFocus == 'ContactTabtoFocus'){
			var tabCount =  $j('.ongletTextDis').length + 1; //only one enabled, rest disabled
			//alert("number of tabs -  = " + tabCount); //go to Contact Record tab
			selectTab(0,tabCount-1,tabCount-2,'ongletTextEna','ongletTextDis','ongletTextErr',null,null);
		}
	}	

    
    
    

      
     
     <!-- =========Begin Javascript Functions for Dynamic Rules==========-->
      
        function ruleEnDisINV1288832()
{
 var foo = [];
$j('#INV128 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV132');
pgEnableElement('INV133');
pgEnableParticipationElement('INV184');
pgEnableElement('54588_9');
pgEnableElement('INV134');
pgEnableElement('309904001');
 } else { 
pgDisableElement('INV132');
pgDisableElement('INV133');
pgDisableParticipationElement('INV184');
pgDisableElement('54588_9');
pgDisableElement('INV134');
pgDisableElement('309904001');
 }   
}
      
        function ruleEnDisINV1458833()
{
 var foo = [];
$j('#INV145 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV146');
pgEnableElement('NBS545');
 } else { 
pgDisableElement('INV146');
pgDisableElement('NBS545');
 }   
}
      
        function ruleEnDisDEM1278834()
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
      
        function ruleEnDisINV1508835()
{
 var foo = [];
$j('#INV150 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV151');
 } else { 
pgDisableElement('INV151');
 }   
}
      
        function ruleEnDisINV1528836()
{
 var foo = [];
$j('#INV152 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('OOC',foo) > -1) || ($j.inArray('Out of country'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('OOJ',foo) > -1) || ($j.inArray(' Out of jurisdiction'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('OOS',foo) > -1) || ($j.inArray(' from another jurisdiction'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV155');
pgEnableElement('INV153');
pgEnableElement('INV156');
pgEnableElement('INV154');
 } else { 
pgDisableElement('INV155');
pgDisableElement('INV153');
pgDisableElement('INV156');
pgDisableElement('INV154');
 }   
}
      
        function ruleEnDisDEM1138837()
{
 var foo = [];
$j('#DEM113 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('M',foo) > -1) || ($j.inArray('Male'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgDisableElement('INV178');
 } else { 
pgEnableElement('INV178');
 }   
}
      
        function ruleDCompINV1328838() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array(); 

 if ((getElementByIdOrByName("INV132").value)==''){ 
 return {elements : errorElts, labels : errorMsgs}; }
 var sourceStr =getElementByIdOrByName("INV132").value;
 var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
 var targetElt;
 var targetStr = ''; 
 var targetDate = '';
 targetStr =getElementByIdOrByName("INV133") == null ? "" :getElementByIdOrByName("INV133").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("INV132");
 var targetDateEle=getElementByIdOrByName("INV133");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Admission Date");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Discharge Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV133"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
        function ruleDCompINV1378839() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array(); 

 if ((getElementByIdOrByName("INV137").value)==''){ 
 return {elements : errorElts, labels : errorMsgs}; }
 var sourceStr =getElementByIdOrByName("INV137").value;
 var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
 var targetElt;
 var targetStr = ''; 
 var targetDate = '';
 targetStr =getElementByIdOrByName("INV138") == null ? "" :getElementByIdOrByName("INV138").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("INV137");
 var targetDateEle=getElementByIdOrByName("INV138");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Symptom Onset");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date of Symptom Resolution");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV138"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
        function ruleDCompDEM1158840() {
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
 targetStr =getElementByIdOrByName("INV132") == null ? "" :getElementByIdOrByName("INV132").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV132");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Admission Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV132"); 
}
  }
 targetStr =getElementByIdOrByName("INV162") == null ? "" :getElementByIdOrByName("INV162").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV162");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Confirmation Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV162"); 
}
  }
 targetStr =getElementByIdOrByName("INV110") == null ? "" :getElementByIdOrByName("INV110").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV110");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date Assigned to Investigation");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV110"); 
}
  }
 targetStr =getElementByIdOrByName("INV146") == null ? "" :getElementByIdOrByName("INV146").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV146");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date of Death");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV146"); 
}
  }
 targetStr =getElementByIdOrByName("INV111") == null ? "" :getElementByIdOrByName("INV111").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV111");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date of Report");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV111"); 
}
  }
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
 targetStr =getElementByIdOrByName("INV136") == null ? "" :getElementByIdOrByName("INV136").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV136");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Diagnosis Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV136"); 
}
  }
 targetStr =getElementByIdOrByName("INV133") == null ? "" :getElementByIdOrByName("INV133").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV133");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Discharge Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV133"); 
}
  }
 targetStr =getElementByIdOrByName("INV120") == null ? "" :getElementByIdOrByName("INV120").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV120");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Earliest Date Reported to County");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV120"); 
}
  }
 targetStr =getElementByIdOrByName("INV121") == null ? "" :getElementByIdOrByName("INV121").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV121");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Earliest Date Reported to State");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV121"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
        function ruleDCompDEM1158841() {
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
 targetStr =getElementByIdOrByName("INV138") == null ? "" :getElementByIdOrByName("INV138").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV138");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date of Symptom Resolution");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV138"); 
}
  }
 targetStr =getElementByIdOrByName("INV137") == null ? "" :getElementByIdOrByName("INV137").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV137");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date of Symptom Onset");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV137"); 
}
  }
 targetStr =getElementByIdOrByName("NBS056") == null ? "" :getElementByIdOrByName("NBS056").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("NBS056");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Infectious Period From");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("NBS056"); 
}
  }
 targetStr =getElementByIdOrByName("NBS057") == null ? "" :getElementByIdOrByName("NBS057").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("NBS057");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Infectious Period To");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("NBS057"); 
}
  }
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
 targetStr =getElementByIdOrByName("INV147") == null ? "" :getElementByIdOrByName("INV147").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("DEM115");
 var targetDateEle=getElementByIdOrByName("INV147");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date of Birth");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Investigation Start Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV147"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
        function ruleEnDisINV5028842()
{
 var foo = [];
$j('#INV502 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('124',foo) > -1) || ($j.inArray('CANADA'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('484',foo) > -1) || ($j.inArray(' MEXICO'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('840',foo) > -1) || ($j.inArray(' UNITED STATES'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV505');
pgEnableElement('INV503');
 } else { 
pgDisableElement('INV505');
pgDisableElement('INV503');
 }   
}
      
        function ruleHideUnhNOT1208843()
{
 var foo = [];
$j('#NOT120 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#NOT120').html()!=null){foo[0]=$j('#NOT120').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('INV176');
pgUnhideElement('NOT120SPEC');
 } else { 
pgHideElement('INV176');
pgHideElement('NOT120SPEC');
 }
 var foo_2 = [];
$j('#NOT120_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#NOT120_2').html()!=null){foo_2[0]=$j('#NOT120_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('INV176_2');
pgUnhideElement('NOT120SPEC_2');
 } else { 
pgHideElement('INV176_2');
pgHideElement('NOT120SPEC_2');
 }   
}
      
        function ruleEnDisNBS5578844()
{
 var foo = [];
$j('#NBS557 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS665');
pgEnableElement('NBS666');
pgEnableElement('NBS664');
pgEnableElement('NBS543');
pgEnableElement('INV603_6');
 } else { 
pgDisableElement('NBS665');
pgDisableElement('NBS666');
pgDisableElement('NBS664');
pgDisableElement('NBS543');
pgDisableElement('INV603_6');
 }   
}
      
        function ruleEnDisNBS5438845()
{
 var foo = [];
$j('#NBS543 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS350');
pgEnableElement('NBS350_2');
pgEnableElement('NBS350_3');
 } else { 
pgDisableElement('NBS350');
pgDisableElement('NBS350_2');
pgDisableElement('NBS350_3');
 }   
}
      
        function ruleEnDisNBS5608846()
{
 var foo = [];
$j('#NBS560 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS561');
 } else { 
pgDisableElement('NBS561');
 }   
}
      
        function ruleEnDisNBS5518847()
{
 var foo = [];
$j('#NBS551 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('EPIX',foo) > -1) || ($j.inArray('EpiX notification of travelers'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS552');
 } else { 
pgDisableElement('NBS552');
 }   
}
      
        function ruleEnDisINV5768848()
{
 var foo = [];
$j('#INV576 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV143');
pgEnableElement('INV144');
pgEnableElement('INV137');
pgEnableElement('INV138');
pgEnableElement('NBS555');
pgEnableElement('INV139');
pgEnableElement('INV140');
 } else { 
pgDisableElement('INV143');
pgDisableElement('INV144');
pgDisableElement('INV137');
pgDisableElement('INV138');
pgDisableElement('NBS555');
pgDisableElement('INV139');
pgDisableElement('INV140');
 }   
}
      
        function ruleEnDis3866610068849()
{
 var foo = [];
$j('#386661006 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV202');
 } else { 
pgDisableElement('INV202');
 }   
}
      
        function ruleEnDisNBS3388850()
{
 var foo = [];
$j('#NBS338 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS338_OTH');
 } else { 
pgDisableElement('NBS338_OTH');
 }   
}
      
        function ruleEnDis1024780088851()
{
 var foo = [];
$j('#102478008 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('128487001');
pgEnableElement('328383001');
pgEnableElement('413839001');
pgEnableElement('709044004');
pgEnableElement('73211009');
pgEnableElement('ARB017');
pgEnableElement('NBS662');
pgEnableElement('NBS677');
pgEnableElement('414916001');
 } else { 
pgDisableElement('128487001');
pgDisableElement('328383001');
pgDisableElement('413839001');
pgDisableElement('709044004');
pgDisableElement('73211009');
pgDisableElement('ARB017');
pgDisableElement('NBS662');
pgDisableElement('NBS677');
pgDisableElement('414916001');
 }   
}
      
        function ruleEnDisINV5768852()
{
 var foo = [];
$j('#INV576 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgSubSectionEnabled('NBS_UI_GA21002');
 } else { 
pgSubSectionDisabled('NBS_UI_GA21002');
 }   
}
      
        function ruleEnDisINV7408853()
{
 var foo = [];
$j('#INV740 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgSubSectionEnabled('NBS_UI_GA23001');
pgSubSectionEnabled('NBS_UI_GA21011');
 } else { 
pgSubSectionDisabled('NBS_UI_GA23001');
pgSubSectionDisabled('NBS_UI_GA21011');
 }   
}
      
        function ruleEnDis1103590098854()
{
 var foo = [];
$j('#110359009 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS671');
pgEnableElement('NBS671_2');
pgEnableElement('NBS671_3');
 } else { 
pgDisableElement('NBS671');
pgDisableElement('NBS671_2');
pgDisableElement('NBS671_3');
 }   
}
      
        function ruleEnDisNBS6628855()
{
 var foo = [];
$j('#NBS662 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS662_OTH');
 } else { 
pgDisableElement('NBS662_OTH');
 }   
}
      
        function ruleEnDisNBS6728856()
{
 var foo = [];
$j('#NBS672 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgSubSectionEnabled('NBS_UI_GA23000');
 } else { 
pgSubSectionDisabled('NBS_UI_GA23000');
 }   
}
      
        function ruleEnDisNBS6738857()
{
 var foo = [];
$j('#NBS673 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV547');
 } else { 
pgDisableElement('INV547');
 }   
}
      
        function ruleEnDisNBS6818858()
{
 var foo = [];
$j('#NBS681 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('95370_3');
pgEnableElement('NBS682');
 } else { 
pgDisableElement('95370_3');
pgDisableElement('NBS682');
 }   
}
      
        function ruleEnDisNBS5488859()
{
 var foo = [];
$j('#NBS548 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('CASE_PROB',foo) > -1) || ($j.inArray('Probable Case'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS678');
 } else { 
pgDisableElement('NBS678');
 }   
}
      
        function ruleEnDis3099040018860()
{
 var foo = [];
$j('#309904001 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS679');
pgEnableElement('NBS680');
 } else { 
pgDisableElement('NBS679');
pgDisableElement('NBS680');
 }   
}
      
        function ruleEnDisNBS5408861()
{
 var foo = [];
$j('#NBS540 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS683');
pgEnableElement('14679004');
 } else { 
pgDisableElement('NBS683');
pgDisableElement('14679004');
 }   
}
      
        function ruleEnDisINV6648862()
{
 var foo = [];
$j('#INV664 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('82754_3');
 } else { 
pgDisableElement('82754_3');
 }   
}
      
        function ruleEnDisTRAVEL388863()
{
 var foo = [];
$j('#TRAVEL38 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('TRAVEL05');
 } else { 
pgDisableElement('TRAVEL05');
 }   
}
      
        function ruleEnDis4730850028864()
{
 var foo = [];
$j('#473085002 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS690');
 } else { 
pgDisableElement('NBS690');
 }   
}
      
        function ruleEnDisNBS6848865()
{
 var foo = [];
$j('#NBS684 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS685');
 } else { 
pgDisableElement('NBS685');
 }   
}
      
        function ruleEnDisNBS5598866()
{
 var foo = [];
$j('#NBS559 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('FDD_Q_32');
 } else { 
pgDisableElement('FDD_Q_32');
 }   
}
      
        function ruleEnDis747320098867()
{
 var foo = [];
$j('#74732009 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS691');
pgEnableElement('NBS691_2');
pgEnableElement('NBS691_3');
 } else { 
pgDisableElement('NBS691');
pgDisableElement('NBS691_2');
pgDisableElement('NBS691_3');
 }   
}
      
        function ruleEnDisNBS6778868()
{
 var foo = [];
$j('#NBS677 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS677_OTH');
 } else { 
pgDisableElement('NBS677_OTH');
 }   
}
      
        function ruleEnDisNBS6828869()
{
 var foo = [];
$j('#NBS682 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS779');
 } else { 
pgDisableElement('NBS779');
 }   
}
      
        function ruleEnDisNBS6858870()
{
 var foo = [];
$j('#NBS685 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS686_CD');
 } else { 
pgDisableElement('NBS686_CD');
 }   
}
      
        function ruleEnDisINV603_68871()
{
 var foo = [];
$j('#INV603_6 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV897');
 } else { 
pgDisableElement('INV897');
 }   
}
      
        function ruleEnDisNBS5468872()
{
 var foo = [];
$j('#NBS546 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('81885_6');
pgEnableElement('81885_6_2');
pgEnableElement('81885_6_3');
 } else { 
pgDisableElement('81885_6');
pgDisableElement('81885_6_2');
pgDisableElement('81885_6_3');
 }   
}
      
        function ruleEnDisNBS7768873()
{
 var foo = [];
$j('#NBS776 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS776_OTH');
 } else { 
pgDisableElement('NBS776_OTH');
 }   
}
      
        function ruleEnDis2335730088874()
{
 var foo = [];
$j('#233573008 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS780');
 } else { 
pgDisableElement('NBS780');
 }   
}
      
        function ruleEnDisNBS7818875()
{
 var foo = [];
$j('#NBS781 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS389');
pgEnableElement('NBS782');
 } else { 
pgDisableElement('NBS389');
pgDisableElement('NBS782');
 }   
}
      
        function ruleEnDisINV1788876()
{
 var foo = [];
$j('#INV178 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV579');
pgEnableElement('VAR159');
pgEnableElement('VAR160');
 } else { 
pgDisableElement('INV579');
pgDisableElement('VAR159');
pgDisableElement('VAR160');
 }   
}
      
        function ruleEnDisVAC1268877()
{
 var foo = [];
$j('#VAC126 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('VAC140');
pgEnableElement('VAC142');
 } else { 
pgDisableElement('VAC140');
pgDisableElement('VAC142');
 }   
}
      
        function ruleEnDisVAC1488878()
{
 var foo = [];
$j('#VAC148 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('N',foo) > -1) || ($j.inArray('No'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('VAC149');
 } else { 
pgDisableElement('VAC149');
 }   
}
      
        function ruleEnDis1024780088879()
{
 var foo = [];
$j('#102478008 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('370388006');
pgEnableElement('85828009');
pgEnableElement('65568007');
pgEnableElement('8517006');
pgEnableElement('NBS676');
pgEnableElement('110359009');
pgEnableElement('74732009');
 } else { 
pgDisableElement('370388006');
pgDisableElement('85828009');
pgDisableElement('65568007');
pgDisableElement('8517006');
pgDisableElement('NBS676');
pgDisableElement('110359009');
pgDisableElement('74732009');
 }   
}
      
        function ruleEnDisINV7408880()
{
 var foo = [];
$j('#INV740 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV575');
 } else { 
pgDisableElement('INV575');
 }   
}
        
  
      
       function pgCheckDynamicRulesHideUnhideOnLoad() {
       	    var JCTContactForm = JBaseForm;
            var allfunctions = "ruleEnDisINV1288832();ruleEnDisINV1458833();ruleEnDisDEM1278834();ruleEnDisINV1508835();ruleEnDisINV1528836();ruleEnDisDEM1138837();ruleEnDisINV5028842();ruleHideUnhNOT1208843();ruleEnDisNBS5578844();ruleEnDisNBS5438845();ruleEnDisNBS5608846();ruleEnDisNBS5518847();ruleEnDisINV5768848();ruleEnDis3866610068849();ruleEnDisNBS3388850();ruleEnDis1024780088851();ruleEnDisINV5768852();ruleEnDisINV7408853();ruleEnDis1103590098854();ruleEnDisNBS6628855();ruleEnDisNBS6728856();ruleEnDisNBS6738857();ruleEnDisNBS6818858();ruleEnDisNBS5488859();ruleEnDis3099040018860();ruleEnDisNBS5408861();ruleEnDisINV6648862();ruleEnDisTRAVEL388863();ruleEnDis4730850028864();ruleEnDisNBS6848865();ruleEnDisNBS5598866();ruleEnDis747320098867();ruleEnDisNBS6778868();ruleEnDisNBS6828869();ruleEnDisNBS6858870();ruleEnDisINV603_68871();ruleEnDisNBS5468872();ruleEnDisNBS7768873();ruleEnDis2335730088874();ruleEnDisNBS7818875();ruleEnDisINV1788876();ruleEnDisVAC1268877();ruleEnDisVAC1488878();ruleEnDis1024780088879();ruleEnDisINV7408880();";
          var arrayFunctions = allfunctions.split(";");
          
          for(var i=0; i!=arrayFunctions.length;i++){
          	var enableFunction = arrayFunctions[i];
          	if(enableFunction.indexOf("ruleHideUnh")!=-1)
          	eval(enableFunction);
			
          
          }
          
       return;
      }  
     
          </script>  
     
       
                       <% 
                        Map map = new HashMap();
                        if(request.getAttribute("SubSecStructureMap") != null){
                        
                      // String watemplateUid="1000879";
                      // map = util.getBatchMap(new Long(watemplateUid));
                          map =(Map)request.getAttribute("SubSecStructureMap");
                      }
                      
                       Map map2 = new HashMap();
                        if(request.getAttribute("SubSecStructureMap2") != null){
                        
                      // String watemplateUid="1000879";
                      // map = util.getBatchMap(new Long(watemplateUid));
                          map2 =(Map)request.getAttribute("SubSecStructureMap2");
						  
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
               fillTable2(key+"_2","pattern"+key+"_2" ,"questionbody"+key+"_2" );			
        } 		
         highlightBatchEntrySection();	             		     
   }); 	
   dwr.engine.endBatch();
   window.setTimeout(alignSectionsAndSubsections, 1000);
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
	 highlightBatchEntrySection();
	}); 		 
} //fillTable	

        		
function fillTable2(subSecNm,pattern,questionbody) {
	JPageForm.getAllAnswer2(subSecNm,function(answer) {
		// Delete all the rows except for the "pattern" row
	    dwr.util.removeAllRows(questionbody, { filter:function(tr) { return (tr.id != pattern); }});					
		dwr.util.setEscapeHtml(false);		
	    // Create a new set cloned from the pattern row -gt
	    var ans, id,rowclass="";
	<%  if(map2 !=  null){
		Iterator    itLab3 = map2.entrySet().iterator(); 
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
	 highlightBatchEntrySection();
	}); 		 
} //fillTable2

           		
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
                    
                     
               
               
                          
function viewClicked2(eleid,subSecNm) {		               		    
	var key;
	var answer = answerCache[eleid.substring(4)];
	<% String[][] batchrecview2  = null;
	   if(map2 != null) {
		    Iterator  itLab2 = map2.entrySet().iterator(); 
		    String[][] batchrecedit  = null;
		    while(itLab2.hasNext()){  
		       Map.Entry pair = (Map.Entry)itLab2.next();%>

			if(subSecNm == "<%=pair.getKey().toString()%>"){  
			    <% batchrecview2 =  (String[][])pair.getValue();   
			    for(int i=0;i<batchrecview2.length;i++){
					if (batchrecview2[i][0] == null) continue;
					String strQuesId = batchrecview2[i][0];%>
					dwr.util.setValue( "<%=strQuesId%>_2", "");
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
					dwr.util.setValue(key+"_2",val);
                                        if(getElementByIdOrByName(key+"Oth") != null){
			                               dwr.util.setValue(key+"Oth",othVal); 			
			                }
				}  else if(val != null && val.indexOf(":") != -1 && val.indexOf("$$") != -1 && val.indexOf("OTH") != -1){
					dwr.util.setValue(key+"_2",val.substring(val.indexOf("$$")+2,val.indexOf(":")));
					dwr.util.setValue(key+"Oth", val.substring(val.indexOf(":")+1));
				} else if(val != null && val.indexOf("$$") != -1){
					val = val.substring(val.indexOf("$$")+2, val.length);
					dwr.util.setValue(key+"_2",val); 
					}
				else if(val != null && val.indexOf("$sn$") != -1){
					val = val.substring(0,val.indexOf("$sn$")) + ' ' +val.substring(val.indexOf("$val$")+5, val.length);
					dwr.util.setValue(key+"_2",val);
				}else if(val == null){
					val ="";  
					dwr.util.setValue(key+"_2",val);
				} else {
					dwr.util.setValue(key+"_2",val);
				}
			}

		}
		<%}}%>
} //viewClicked


     
                    
          
               
     

     
     </script>
     <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
     </style>
    </head>
         
     <% 
    int subSectionIndex = 0;

    String tabId = "";   


      String [] sectionNames  = {"Patient Information","Occupation and Industry","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Place of Residence","Healthcare Worker Information","Exposure Information","Information Source","Signs &amp; Symptoms","Medical History","Vaccination","Respiratory Diagnostic Testing","COVID-19 Laboratory Findings","Contact Investigation","Retired Questions"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";    
    
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
        <body class onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkSurvivorForPrint();cleanupPatientRacesViewDisplay2();checkLoadNotif();disablePrintLinks();populateBatchRecords();addTabs();addRolePresentationToTabsAndSections();" onunload="return closePrinterFriendlyWindow();selectTabOnSubmit();pgCheckDynamicRulesHideUnhideOnLoad();showAllShowDiffOnlyCSS();">
        <% } else if(addPermissionString.equals("false") && manageAssoPerm.equals("false") ){ %> 
        <body class onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkSurvivorForPrint();cleanupPatientRacesViewDisplay2();checkLoadNotif();selectTabOnSubmit();populateBatchRecords();showAllShowDiffOnlyCSS();addTabs();addRolePresentationToTabsAndSections();">
    <% } else if(addPermissionString.equals("true") && manageAssoPerm.equals("false") ){ %> 
        <body class onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkSurvivorForPrint();cleanupPatientRacesViewDisplay2();checkLoadNotif();appendPatientSearch('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');selectTabOnSubmit();populateBatchRecords();pgCheckDynamicRulesHideUnhideOnLoad();showAllShowDiffOnlyCSS();addTabs();addRolePresentationToTabsAndSections();">
      <% } else if(addPermissionString.equals("false") && manageAssoPerm.equals("true") ){ %> 
        <body class onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkSurvivorForPrint();cleanupPatientRacesViewDisplay2();checkLoadNotif();appendManageAssociation('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');selectTabOnSubmit();populateBatchRecords();pgCheckDynamicRulesHideUnhideOnLoad();showAllShowDiffOnlyCSS();addTabs();addRolePresentationToTabsAndSections();">
         <% } else if(addPermissionString.equals("true") && manageAssoPerm.equals("true") ){ %> 
        <body class onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkSurvivorForPrint();cleanupPatientRacesViewDisplay2();checkLoadNotif();appendPatientSearch('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');appendManageAssociation('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');selectTabOnSubmit();populateBatchRecords();pgCheckDynamicRulesHideUnhideOnLoad();showAllShowDiffOnlyCSS();addTabs();addRolePresentationToTabsAndSections();">
    <% } %>

        <div id="pageview"></div>        
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3" style="display: inline-block; min-width: 1300px">
            <html:form action="/PageAction.do">
            	<input type="hidden" name="deleteError" value="<%= request.getAttribute("deleteError") == null ? "" : request.getAttribute("deleteError")%>"/>
	        <html:hidden property="attributeMap.NotificationExists" styleId="NotificationExists"/>            

                <!-- Body div -->
                <div id="bd">
                    <!-- Top Nav Bar and top button bar -->
                    <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>                 
                    <!-- For create/edit mode only -->
                    
                    <logic:notEqual name="BaseForm" property="actionMode" value="Preview">
                    <logic:notEqual name="BaseForm" property="mode" value="print">
                        <!-- top button bar -->
                       <logic:equal name="BaseForm" property="securityMap(editInv)" value="true">
                          
                         
									<div  class="grayButtonBar" style="text-align: right;width:99%"><input
										type="submit" name="submitCrSub" id="submitCrSub"
										value="Merge" onClick="return mergeInvestigations();" /> <input
										type="submit" name="submitCrCan" id="submitCrCan"
										value="Print" onClick="return showPrintFriendlyPage();" /> <input
										type="submit" name="submitCrCan" id="submitCrCan"
										value="Cancel" onClick="return cancelForm();" /> &nbsp;</div>
								
                        </logic:equal>
                        
                        
                    
                       	<!-- Page Errors -->
                 		<%@ include file="/jsp/feedbackMessagesBar.jsp" %>
		                        
		                </logic:notEqual>
                    </logic:notEqual>  
                        <!-- Show differences Indicator -->
                        <div style="text-align:right; width:99%;"> 
                            <span class="boldTenBlack">  
	                            <input type="radio" name="fullrecordOrDifferences" value="fullrecord" onclick="showAllShowDiffOnly(true,false);alignSectionsAndSubsections();" checked title="Show Full Record"> Show Full Record  &nbsp;
	 							<input type="radio" name="fullrecordOrDifferences" value="differencesOnly" onclick="showAllShowDiffOnly(false, false);alignSectionsAndSubsections();" title="Show Differences Only"> Show Differences Only 
 							</span>  
                        </div>
                        
                    
                    <logic:notEqual name="BaseForm" property="mode" value="print">
                    <div style="text-align:left; width:99%;">            
						<ul class="horizontalList">
	                   		<li style="margin-right:5px;"><b>Go to: </b></li>                           
						<%  for(int i =0; i<sectionNames.length;i++){%>
							<li><a href="javascript:gotoSection('<%= sectionNames[sectionIndex].replaceAll(" ", "") %>')"><%= sectionNames[sectionIndex++] %></a></li>
						<%if(i!=sectionNames.length-1) {%>
							<li class="delimiter"> | </li>
						<%	}} %> </div>
						
						<BR />
				             <div style="text-align:left; width:99%;font-weight:bold;display:inline-block;">            
				               <div id="errorBlock" class="screenOnly infoBox errors" style="display:none;width:99%"></div>
					          </div>  
	
						 </logic:notEqual>
	                                   
                
            
                        
	
	<!--
		################### PAGE TAB ###################### - -
	-->

	
	 
	
	<logic:equal name="BaseForm" property="mode" value="print">
	
	 			<div class="printerIconBlock screenOnly">
					<table role="presentation" style="width:98%; margin:3px;">
					<tr>
						<td style="text-align:right; font-weight:bold;"> 
							<a href="#" onclick="return printPage();"> <img src="printer_icon.gif" alt="Print Page" title="Print Page"/> Print Page </a> 
						</td>
					</tr>
					</table>
				</div>
	</logic:equal>


    <div style="width:100%; float: left;"> 
    <table role="presentation" style="float:left;  width: 49%; position:relative;"> 

	<jsp:include page="compare.jsp"/>
	 
		</table> <table role="presentation" style="position:relative; width: 49%; margin-left: 50%;"> 
	
	<jsp:include page="compare2.jsp"/>

	 
    	</table></div>                        
    	
    	<logic:notEqual name="BaseForm" property="mode" value="print">
    	 <logic:equal name="BaseForm" property="securityMap(editInv)" value="true">
                          
                         
									<div  class="grayButtonBar" style="text-align: right; width:99%;display:inline-block"><input
										type="submit" name="submitCrSub" id="submitCrSub"
										value="Merge" onClick="return mergeInvestigations();" /> <input
										type="submit" name="submitCrCan" id="submitCrCan"
										value="Print" onClick="return showPrintFriendlyPage();" /> <input
										type="submit" name="submitCrCan" id="submitCrCan"
										value="Cancel" onClick="return cancelForm();" /> &nbsp;</div>
								
                        </logic:equal>
	</logic:notEqual>
      </html:form>
      </div> <!-- Container Div -->
    </body> 
     
    
     
</html>

