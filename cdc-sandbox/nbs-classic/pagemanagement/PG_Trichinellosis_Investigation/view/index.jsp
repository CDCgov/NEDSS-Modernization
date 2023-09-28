<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN VIEW INDEX JSP PAGE GENERATE ###- - -->
            
        	
    <html lang="en">
    <head>
     <%@ page import="java.util.*" %>
     <%@ include file="/jsp/tags.jsp" %>
    <title>NBS:Investigation: Generic</title>
    <%@ include file="/jsp/resources.jsp" %>        
     <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>
        <script language="JavaScript"> 
              
     
    
    
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
	
	  var divElt =getElementByIdOrByName("pageview");
	  divElt.style.display = "block";
	  var o = new Object();
	  o.opener = self;
	  var URL = "/nbs/PageAction.do?method=viewLoad&mode=print";
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
      
        function ruleEnDisINV1288540()
{
 var foo = [];
$j('#INV128 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV132');
pgEnableElement('INV133');
pgEnableParticipationElement('INV184');
pgEnableElement('INV134');
 } else { 
pgDisableElement('INV132');
pgDisableElement('INV133');
pgDisableParticipationElement('INV184');
pgDisableElement('INV134');
 }   
}
      
        function ruleEnDisINV1458541()
{
 var foo = [];
$j('#INV145 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV146');
 } else { 
pgDisableElement('INV146');
 }   
}
      
        function ruleEnDisDEM1278542()
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
      
        function ruleEnDisINV1508543()
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
      
        function ruleEnDisINV1528544()
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
      
        function ruleEnDisDEM1138545()
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
      
        function ruleDCompINV1328546() {
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
      
        function ruleDCompINV1378547() {
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
 var srca2str=buildErrorAnchorLink(srcDateEle,"Illness Onset Date");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Illness End Date");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("INV138"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
        function ruleDCompDEM1158548() {
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
      
        function ruleDCompDEM1158549() {
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
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Illness End Date");
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
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Illness Onset Date");
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
      
        function ruleEnDisINV5028550()
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
      
        function ruleHideUnhNOT1208551()
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
      
        function ruleHideUnhINV1788552()
{
 var foo = [];
$j('#INV178 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#INV178').html()!=null){foo[0]=$j('#INV178').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('INV579');
 } else { 
pgHideElement('INV579');
 }
 var foo_2 = [];
$j('#INV178_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#INV178_2').html()!=null){foo_2[0]=$j('#INV178_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('INV579_2');
 } else { 
pgHideElement('INV579_2');
 }   
}
      
        function ruleEnDisNBS4068553()
{
 var foo = [];
$j('#NBS406 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgSubSectionEnabled('NBS_UI_38');
 } else { 
pgSubSectionDisabled('NBS_UI_38');
 }   
}
      
        function ruleEnDisINV7408554()
{
 var foo = [];
$j('#INV740 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgSubSectionEnabled('NBS_UI_43');
 } else { 
pgSubSectionDisabled('NBS_UI_43');
 }   
}
      
        function ruleEnDisLAB7118555()
{
 var foo = [];
$j('#LAB711 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('LAB665');
 } else { 
pgDisableElement('LAB665');
 }   
}
      
        function ruleEnDisNBS4548556()
{
 var foo = [];
$j('#NBS454 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('DOM',foo) > -1) || ($j.inArray('domestic'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgDisableElement('TRAVEL05');
 } else { 
pgEnableElement('TRAVEL05');
 }   
}
      
        function ruleEnDisNBS4548557()
{
 var foo = [];
$j('#NBS454 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('INTL',foo) > -1) || ($j.inArray('international'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgDisableElement('82753_5');
pgDisableElement('82754_3');
 } else { 
pgEnableElement('82753_5');
pgEnableElement('82754_3');
 }   
}
      
        function ruleEnDisTRAVEL028558()
{
 var foo = [];
$j('#TRAVEL02 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgSubSectionEnabled('NBS_UI_40');
 } else { 
pgSubSectionDisabled('NBS_UI_40');
 }   
}
      
        function ruleEnDisNBS3388559()
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
      
        function ruleEnDis3866610068560()
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
      
        function ruleEnDisINV9718561()
{
 var foo = [];
$j('#INV971 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('75134009',foo) > -1) || ($j.inArray('Not Examined'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgDisableElement('INV972');
 } else { 
pgEnableElement('INV972');
 }   
}
        
  
      
       function pgCheckDynamicRulesHideUnhideOnLoad() {
       	    var JCTContactForm = JBaseForm;
            var allfunctions = "ruleEnDisINV1288540();ruleEnDisINV1458541();ruleEnDisDEM1278542();ruleEnDisINV1508543();ruleEnDisINV1528544();ruleEnDisDEM1138545();ruleEnDisINV5028550();ruleHideUnhNOT1208551();ruleHideUnhINV1788552();ruleEnDisNBS4068553();ruleEnDisINV7408554();ruleEnDisLAB7118555();ruleEnDisNBS4548556();ruleEnDisNBS4548557();ruleEnDisTRAVEL028558();ruleEnDisNBS3388559();ruleEnDis3866610068560();ruleEnDisINV9718561();";
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
                        String[][] batchrecview  = null;
                        Map map = new HashMap();
                        if(request.getAttribute("SubSecStructureMap") != null){
                        
                      // String watemplateUid="1000879";
                      // map = util.getBatchMap(new Long(watemplateUid));
                          map =(Map)request.getAttribute("SubSecStructureMap");
                         
                      }%>
                     
               
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
	<% String[][]  batchrecinsert  =  (String[][])pair.getValue();   
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
                                
                                if(emptyrow=="yes"){
                                    qId=qId+"Description";
                                    
                                      if (getElementByIdOrByName(qId) != null) {
   										  var textval = getElementByIdOrByName(qId).textContent;
   										  if (!(textval == null || textval == '')) {
									      emptyrow = "no";
									 	 }
   									  } 
   									  
   									  }
    
                                <%}%>
                                
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

function viewClickedForEditableBatch(eleid,subSecNm) {	
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
	var stateCode = answer.answerMap['INV503'];
	if(stateCode != null && stateCode != "" && getElementByIdOrByName('INV505') != null){
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
		if(answer.answerMap[key] != null && answer.answerMap[key] != '' && (uiComponent == "1013" || uiComponent == "1017" || uiComponent == "1031")){
			<% for(int i=0;i<batchrecedit1.length;i++){
			     if(  null != batchrecedit1[i][0]) { %> 
			if(key == "<%=batchrecedit1[i][0]%>" && code == "<%=batchrecedit1[i][5]%>"){
				mulVal = answer.answerMap[key]; 
				repeatingBlockHandleMultiVal (mulVal, key);
	    		} else if (key == "<%=batchrecedit1[i][0]%>" && "1017" == "<%=batchrecedit1[i][5]%>"){
	    			partVal = answer.answerMap[key];
	    			repeatingBlockHandleViewParticipant (partVal, key, answer.answerMap[key + "Disp"]);
	    		}
	    		 else if (key == "<%=batchrecedit1[i][0]%>" && "1031" == "<%=batchrecedit1[i][5]%>"){
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

	<% 
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
        } 

                  
function viewClicked(eleid,subSecNm) {		               		    
	var key;
	var answer = answerCache[eleid.substring(4)];
	<%
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
		


<% batchrecclear = (String[][])pair.getValue();
for(int i=0;i<batchrecclear.length;i++){
if (batchrecclear[i][0] == null) continue;
String checknull1 = batchrecclear[i][0];%>
var key = "<%=batchrecclear[i][0]%>";
var componentType = "<%=batchrecclear[i][5]%>";
<%if(checknull1 != null && checknull1 != "" ){%>
if(key != null &&getElementByIdOrByName(key) != null && !subsectionDisabled){ //document.getElementById(key).onchange();
 $j("#"+key).trigger("change");
  }
<%}}%>



		viewed = -1;
	}
	<%}}%>
	
	
	changeColorRowsNotModified(subSecNm);
	
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
    
       function changeColorRowsNotModified(subSecNm){
   
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
		$j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
		$j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
	}
	
	
	for (var i = 0; i < len+1; i ++) {
		$j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");
	}

	$j($j(document.getElementsByClassName("nedssNavTable")).find("tbody > tr:odd").get(0)).css("background-color","#003470");
	$j($j(document.getElementsByClassName("nedssNavTable")).find("tbody > tr:even").get(0)).css("background-color","#003470");

   }
               
                    
                    
          
               
     	<!-- === batch subsection add edit check functions (if any) follow ===-->
     	function pgNBS_UI_32BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_UI_32');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_UI_32errorMessages', errorLabels); return false;}
		$j('#NBS_UI_32errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_UI_NBS_INV_GENV2_UI_4BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_UI_NBS_INV_GENV2_UI_4');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_UI_NBS_INV_GENV2_UI_4errorMessages', errorLabels); return false;}
		$j('#NBS_UI_NBS_INV_GENV2_UI_4errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_UI_43BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_UI_43');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_UI_43errorMessages', errorLabels); return false;}
		$j('#NBS_UI_43errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_UI_38BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_UI_38');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_UI_38errorMessages', errorLabels); return false;}
		$j('#NBS_UI_38errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_UI_40BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_UI_40');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_UI_40errorMessages', errorLabels); return false;}
		$j('#NBS_UI_40errorMessages').css("display", "none");
		return true;
		}
        
      
    
     </script>
     <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
     </style>
    </head>
         
     <% 
    int subSectionIndex = 0;

    String tabId = "";   


      String [] sectionNames  = {"Patient Information","Investigation Information","Reporting Information","Clinical","Epidemiologic","General Comments","Signs and Symptoms","Laboratory","Exposure","Contact Investigation","Retired Data Elements"};
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
        <body class="tabIndexClass" onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkLoadNotif();disablePrintLinks();populateBatchRecords();" onunload="return closePrinterFriendlyWindow();selectTabOnSubmit();pgCheckDynamicRulesHideUnhideOnLoad();addTabs();addRolePresentationToTabsAndSections();">
        <% } else if(addPermissionString.equals("false") && manageAssoPerm.equals("false") ){ %> 
        <body class="tabIndexClass" onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkLoadNotif();selectTabOnSubmit();populateBatchRecords();addTabs();addRolePresentationToTabsAndSections();">
    <% } else if(addPermissionString.equals("true") && manageAssoPerm.equals("false") ){ %> 
        <body class="tabIndexClass" onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkLoadNotif();appendPatientSearch('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');selectTabOnSubmit();populateBatchRecords();pgCheckDynamicRulesHideUnhideOnLoad();addTabs();addRolePresentationToTabsAndSections();">
      <% } else if(addPermissionString.equals("false") && manageAssoPerm.equals("true") ){ %> 
        <body class="tabIndexClass" onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkLoadNotif();appendManageAssociation('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');selectTabOnSubmit();populateBatchRecords();pgCheckDynamicRulesHideUnhideOnLoad();addTabs();addRolePresentationToTabsAndSections();">
         <% } else if(addPermissionString.equals("true") && manageAssoPerm.equals("true") ){ %> 
        <body class="tabIndexClass" onload="startCountdown();checkPageDelete();cleanupPatientRacesViewDisplay();checkLoadNotif();appendPatientSearch('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');appendManageAssociation('<%=PatientRevision%>','<%=caseLocalId%>','<%=perMprUid %>');selectTabOnSubmit();populateBatchRecords();pgCheckDynamicRulesHideUnhideOnLoad();addTabs();addRolePresentationToTabsAndSections();">
    <% } %>

        <div id="pageview"></div>        
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
            <html:form action="/PageAction.do">
            	<input type="hidden" name="deleteError" value="<%= request.getAttribute("deleteError") == null ? "" : request.getAttribute("deleteError")%>"/>
	        <html:hidden property="attributeMap.NotificationExists" styleId="NotificationExists"/>            

                <!-- Body div -->
                <div id="bd">
                    <!-- Top Nav Bar and top button bar -->
                    <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>                 
                    <!-- For create/edit mode only -->
                    <logic:notEqual name="BaseForm" property="actionMode" value="Preview">
                        <!-- top button bar -->
                        <%@ include file="/jsp/topbuttonbarFullScreenWidth.jsp" %>
            
                       <!-- Page Errors -->
                       <%@ include file="/jsp/feedbackMessagesBar.jsp" %>
                       
                        <!-- Patient summary -->
                        
                     
				    	 <%@ include file="/pagemanagement/patient/PatientSummary.jsp" %> 
					
		   
                      
                                             
                        <!-- Required Field Indicator -->
                        <div style="text-align:right; width:100%;"> 
                            <span class="boldTenRed"> * </span>
                            <span class="boldTenBlack"> Indicates a Required Field </span>  
                        </div>
                    </logic:notEqual>  
                
                        
      
     <!-- ################### PAGE TAB ###################### - - -->
      <layout:tabs width="100%" styleClass="tabsContainer">
             
       		
          	
        
	  	
         <layout:tab key="Patient">     
        <jsp:include page="viewPatient.jsp"/>
        </layout:tab>  
        
                
              
       		
          	
        
	  	
         <layout:tab key="Case Info">     
        <jsp:include page="viewCaseInfo.jsp"/>
        </layout:tab>  
        
                
              
       		
          	
        
	  	
         <layout:tab key="Trichinellosis">     
        <jsp:include page="viewTrichinellosis.jsp"/>
        </layout:tab>  
        
                
              
       		
          	
         	         <logic:equal name="PageForm" property="securityMap(ContactTracingEnableInd)" value="Y">
          		 <logic:equal name="PageForm" property="securityMap(checkToViewContactTracing)" value="true">
         	
        
	  	
         <layout:tab key="Contact Tracing">     
        <jsp:include page="viewContactTracing.jsp"/>
        </layout:tab>  
        
                
	  		</logic:equal>
	   		</logic:equal>
	        
           
           
                <logic:equal name="PageForm" property="securityMap(ContactTracingEnableInd)" value="Y">
          	<logic:equal name="PageForm" property="securityMap(checkToViewContactTracing)" value="true">
        	<layout:tab key="Contact Records">
               <jsp:include page="/pagemanagement/contactTracing/view/viewContactTracing.jsp"/> 
	 	</layout:tab> 
	       </logic:equal>
               </logic:equal>
	   
          
				<layout:tab key="Supplemental Info">
			       <jsp:include page="/pagemanagement/supplemental/SupplementalInformation.jsp"/> 
			 </layout:tab>    

		
          </layout:tabs>  
          
         <%@ include file="/jsp/bottombuttonbarFullScreenWidth.jsp" %>
	</html:form>
      </div> <!-- Container Div -->
    </body>
     
</html>

