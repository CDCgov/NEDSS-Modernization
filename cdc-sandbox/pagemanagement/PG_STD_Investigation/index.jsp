<?xml version="1.0" encoding="UTF-8"?>

       <!-- ### DMB: BEGIN JSP PAGE GENERATE ###- - -->
        
        
    <html lang="en">
    <head>
    <title>NBS:Investigation: Generic</title>
    <%@ include file="/jsp/tags.jsp" %>
    <%@ include file="/jsp/resources.jsp" %> 
    <%@ page import="java.util.*" %>
    <%@ page import="gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj" %>
    <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageForm.js"></SCRIPT>  
     
        <script language="JavaScript"> 
             
        
        	
	    blockEnterKey();
		disableSubmitBeforePageLoadsFlag('${PageForm.disableSubmitBeforePageLoadsFlag}');
		
		document.onkeydown = function (e) {
			var attribute = getCorrectAttribute(document.activeElement, "type", document.activeElement.type);
			
			if(attribute==null){
			 if(e.which=='8')
				return false;
			}
			
			return preventF12(e);
		}
		
        function cancelForm()
        {
            var confirmMsg="If you continue with the Cancel action, you will lose any information you have entered. Select OK to continue, or Cancel to not continue.";
            if (confirm(confirmMsg)) {
                document.forms[0].action ="${PageForm.attributeMap.Cancel}";
            } else {
                return false;
            }
        }
        function saveForm() 
        {
          var method="${PageForm.attributeMap.method}";  

          if(method=="createSubmit"){
            if (pgCheckForErrorsOnSubmit() == true) {
              return false;
            } else {
              stdSpecialSubmitCheck(); //last min checks for STD
              var nbsSecurityJurisdictions = "${PageForm.attributeMap.NBSSecurityJurisdictions}";
              var valJuris =  validatePageJurisdiction("INV107",nbsSecurityJurisdictions);
              if (valJuris != null && valJuris == 'false')
              	return false; //user wants to edit jurisdiction
              if (valJuris != null && valJuris == 'true')
              	document.forms[0].action ="/nbs/PageAction.do?method=createSubmit&ContextAction=SubmitNoViewAccess";
              else              
                document.forms[0].action ="/nbs/PageAction.do?method=createSubmit&ContextAction=Submit";
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
          else   {       
            if (pgCheckForErrorsOnSubmit() == true) {
				 return false;
		    } else {
		      stdSpecialSubmitCheck(); //last min checks for STD
			  document.forms[0].action ="/nbs/PageAction.do?method=editSubmit&ContextAction=Submit";	
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
 
 
 
        
            
              
          
     <!-- =========Begin Javascript Functions for Dynamic Rules==========-->
     
  function ruleEnDisINV1287849()
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
      
  function ruleEnDisINV1457850()
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
      
  function ruleEnDisINV1507851()
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
      
  function ruleEnDisINV1527852()
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
      
  function ruleDCompINV1327853() {
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
      
  function ruleDCompINV1377854() {
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
      
  function ruleDCompDEM1157855() {
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
      
  function ruleDCompDEM1157856() {
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
      
  function ruleEnDisDEM1277857()
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
      
  function ruleDCompNBS1747858() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array(); 

 if ((getElementByIdOrByName("NBS174").value)==''){ 
 return {elements : errorElts, labels : errorMsgs}; }
 var sourceStr =getElementByIdOrByName("NBS174").value;
 var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
 var targetElt;
 var targetStr = ''; 
 var targetDate = '';
 targetStr =getElementByIdOrByName("NBS162") == null ? "" :getElementByIdOrByName("NBS162").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  >=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("NBS174");
 var targetDateEle=getElementByIdOrByName("NBS162");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Disposition Date");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date Assigned");
    errorMsgs[i]=srca2str + " must be  >=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("NBS162"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
  function ruleDCompNBS1477859() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array(); 

 if ((getElementByIdOrByName("NBS147").value)==''){ 
 return {elements : errorElts, labels : errorMsgs}; }
 var sourceStr =getElementByIdOrByName("NBS147").value;
 var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
 var targetElt;
 var targetStr = ''; 
 var targetDate = '';
 targetStr =getElementByIdOrByName("NBS146") == null ? "" :getElementByIdOrByName("NBS146").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  >=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("NBS147");
 var targetDateEle=getElementByIdOrByName("NBS146");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Date Closed");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date Assigned");
    errorMsgs[i]=srca2str + " must be  >=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("NBS146"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
  function ruleRequireIfNBS2097860()
{
 var foo = [];
$j('#NBS209 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('Y',foo) > -1)){
pgRequireElement('NBS210');
pgRequireElement('NBS211L');
 } else { 
pgRequireNotElement('NBS210');
pgRequireNotElement('NBS211L');
 }   
}
      
  function ruleEnDisNBS2167861()
{
 var foo = [];
$j('#NBS216 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS217');
 } else { 
pgDisableElement('NBS217');
 }   
}
      
  function ruleRequireIfNBS2167862()
{
 var foo = [];
$j('#NBS216 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('Y',foo) > -1)){
pgRequireElement('NBS217');
 } else { 
pgRequireNotElement('NBS217');
 }   
}
      
  function ruleEnDisNBS2187863()
{
 var foo = [];
$j('#NBS218 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS220');
pgEnableElement('NBS219');
 } else { 
pgDisableElement('NBS220');
pgDisableElement('NBS219');
 }   
}
      
  function ruleRequireIfNBS2187864()
{
 var foo = [];
$j('#NBS218 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('Y',foo) > -1)){
pgRequireElement('NBS220L');
pgRequireElement('NBS219');
 } else { 
pgRequireNotElement('NBS220L');
pgRequireNotElement('NBS219');
 }   
}
      
  function ruleEnDisNBS2217865()
{
 var foo = [];
$j('#NBS221 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS222');
 } else { 
pgDisableElement('NBS222');
 }   
}
      
  function ruleRequireIfNBS2217866()
{
 var foo = [];
$j('#NBS221 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('Y',foo) > -1)){
pgRequireElement('NBS222L');
 } else { 
pgRequireNotElement('NBS222L');
 }   
}
      
  function ruleEnDisNBS1117867()
{
 var foo = [];
$j('#NBS111 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length>0 && foo[0] != '') {
pgEnableElement('NBS113');
pgEnableElement('NBS114');
pgEnableElement('NBS112');
 } else { 
pgDisableElement('NBS113');
pgDisableElement('NBS114');
pgDisableElement('NBS112');
 }   
}
      
  function ruleDCompINV1477868() {
    var i = 0;
    var errorElts = new Array(); 
    var errorMsgs = new Array(); 

 if ((getElementByIdOrByName("INV147").value)==''){ 
 return {elements : errorElts, labels : errorMsgs}; }
 var sourceStr =getElementByIdOrByName("INV147").value;
 var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);
 var targetElt;
 var targetStr = ''; 
 var targetDate = '';
 targetStr =getElementByIdOrByName("NBS162") == null ? "" :getElementByIdOrByName("NBS162").value;
 if (targetStr!="") {
    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);
 if (!(srcDate  <=  targetDate)) {
 var srcDateEle=getElementByIdOrByName("INV147");
 var targetDateEle=getElementByIdOrByName("NBS162");
 var srca2str=buildErrorAnchorLink(srcDateEle,"Investigation Start Date");
 var targeta2str=buildErrorAnchorLink(targetDateEle,"Date Assigned");
    errorMsgs[i]=srca2str + " must be  <=  " + targeta2str; 
    colorElementLabelRed(srcDateEle); 
    colorElementLabelRed(targetDateEle); 
errorElts[i++]=getElementByIdOrByName("NBS162"); 
}
  }
 return {elements : errorElts, labels : errorMsgs}
}
      
  function ruleEnDisINV1787869()
{
 var foo = [];
$j('#INV178 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS128');
 } else { 
pgDisableElement('NBS128');
 }   
}
      
  function ruleRequireIfINV1787870()
{
 var foo = [];
$j('#INV178 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('Y',foo) > -1)){
pgRequireElement('NBS128');
 } else { 
pgRequireNotElement('NBS128');
 }   
}
      
  function ruleEnDisNBS1297871()
{
 var foo = [];
$j('#NBS129 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS130');
 } else { 
pgDisableElement('NBS130');
 }   
}
      
  function ruleEnDisNBS1317872()
{
 var foo = [];
$j('#NBS131 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS132');
 } else { 
pgDisableElement('NBS132');
 }   
}
      
  function ruleEnDisNBS1337873()
{
 var foo = [];
$j('#NBS133 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS134');
 } else { 
pgDisableElement('NBS134');
 }   
}
      
  function ruleEnDisNBS2237874()
{
 var foo = [];
$j('#NBS223 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS224');
 } else { 
pgDisableElement('NBS224');
 }   
}
      
  function ruleEnDisNBS2257875()
{
 var foo = [];
$j('#NBS225 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS226');
 } else { 
pgDisableElement('NBS226');
 }   
}
      
  function ruleEnDisNBS2277876()
{
 var foo = [];
$j('#NBS227 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS228');
 } else { 
pgDisableElement('NBS228');
 }   
}
      
  function ruleEnDisDEM1137877()
{
 var foo = [];
$j('#DEM113 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('U',foo) > -1) || ($j.inArray('Unknown'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS272');
 } else { 
pgDisableElement('NBS272');
 }   
}
      
  function ruleEnDisSTD1217878()
{
 var foo = [];
$j('#STD121 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('PHC1170',foo) > -1) || ($j.inArray('Other anatomic site not represented in other defined anatomic sites'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS248');
 } else { 
pgDisableElement('NBS248');
 }   
}
      
  function ruleEnDisDEM1557879()
{
 var foo = [];
$j('#DEM155 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('UNK',foo) > -1) || ($j.inArray('unknown'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS273');
 } else { 
pgDisableElement('NBS273');
 }   
}
      
  function ruleEnDisDEM1137880()
{
 var foo = [];
$j('#DEM113 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('F',foo) > -1) || ($j.inArray('Female'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('STD113');
pgEnableElement('INV178');
 } else { 
pgDisableElement('STD113');
pgDisableElement('INV178');
 }   
}
      
  function ruleEnDisINV5027881()
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
      
  function ruleHideUnhNBS2097882()
{
 var foo = [];
$j('#NBS209 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#NBS209').html()!=null){foo[0]=$j('#NBS209').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('NBS210');
pgUnhideElement('NBS211');
 } else { 
pgHideElement('NBS210');
pgHideElement('NBS211');
 }
 var foo_2 = [];
$j('#NBS209_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#NBS209_2').html()!=null){foo_2[0]=$j('#NBS209_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('NBS210_2');
pgUnhideElement('NBS211_2');
 } else { 
pgHideElement('NBS210_2');
pgHideElement('NBS211_2');
 }   
}
      
  function ruleHideUnhNOT1207883()
{
 var foo = [];
$j('#NOT120 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#NOT120').html()!=null){foo[0]=$j('#NOT120').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('NOT120SPEC');
 } else { 
pgHideElement('NOT120SPEC');
 }
 var foo_2 = [];
$j('#NOT120_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#NOT120_2').html()!=null){foo_2[0]=$j('#NOT120_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('NOT120SPEC_2');
 } else { 
pgHideElement('NOT120SPEC_2');
 }   
}
      
  function ruleHideUnhNBS1687884()
{
 var foo = [];
$j('#NBS168 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#NBS168').html()!=null){foo[0]=$j('#NBS168').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('NBS169');
 } else { 
pgHideElement('NBS169');
 }
 var foo_2 = [];
$j('#NBS168_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#NBS168_2').html()!=null){foo_2[0]=$j('#NBS168_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Yes')==true)){
pgUnhideElement('NBS169_2');
 } else { 
pgHideElement('NBS169_2');
 }   
}
      
  function ruleHideUnhNBS2427885()
{
 var foo = [];
$j('#NBS242 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#NBS242').html()!=null){foo[0]=$j('#NBS242').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Yes')==true)){
pgSubSectionShown('NBS_INV_STD_UI_49');
 } else { 
pgSubSectionHidden('NBS_INV_STD_UI_49');
 }
 var foo_2 = [];
$j('#NBS242_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#NBS242_2').html()!=null){foo_2[0]=$j('#NBS242_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Yes')==true)){
pgSubSectionShown('NBS_INV_STD_UI_49_2');
 } else { 
pgSubSectionHidden('NBS_INV_STD_UI_49_2');
 }   
}
      
  function ruleHideUnhNBS2447886()
{
 var foo = [];
$j('#NBS244 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#NBS244').html()!=null){foo[0]=$j('#NBS244').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Yes')==true)){
pgSubSectionShown('NBS_INV_STD_UI_51');
 } else { 
pgSubSectionHidden('NBS_INV_STD_UI_51');
 }
 var foo_2 = [];
$j('#NBS244_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#NBS244_2').html()!=null){foo_2[0]=$j('#NBS244_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Yes')==true)){
pgSubSectionShown('NBS_INV_STD_UI_51_2');
 } else { 
pgSubSectionHidden('NBS_INV_STD_UI_51_2');
 }   
}
      
  function ruleHideUnhSTD1177887()
{
 var foo = [];
$j('#STD117 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo=='' && $j('#STD117').html()!=null){foo[0]=$j('#STD117').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1 || indexOfArray(foo,'Yes')==true)){
pgSubSectionShown('NBS_INV_STD_UI_64');
 } else { 
pgSubSectionHidden('NBS_INV_STD_UI_64');
 }
 var foo_2 = [];
$j('#STD117_2 :selected').each(function(i, selected){
 foo_2[i] = $j(selected).val();
 });
if(foo_2=='' && $j('#STD117_2').html()!=null){foo_2[0]=$j('#STD117_2').html().replace(/^\s+|\s+$/g,'');}
 if(($j.inArray('Y',foo_2) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo_2) > -1 || indexOfArray(foo,'Yes')==true)){
pgSubSectionShown('NBS_INV_STD_UI_64_2');
 } else { 
pgSubSectionHidden('NBS_INV_STD_UI_64_2');
 }   
}
      
  function ruleEnDisNBS1437888()
{
 var foo = [];
$j('#NBS143 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('06',foo) > -1) || ($j.inArray('6 - Yes'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('88',foo) > -1) || ($j.inArray(' 88 - Other'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS177');
pgEnableElement('NBS167');
 } else { 
pgDisableElement('NBS177');
pgDisableElement('NBS167');
 }   
}
      
  function ruleEnDisNBS2547889()
{
 var foo = [];
$j('#NBS254 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('STD106');
 } else { 
pgDisableElement('STD106');
 }   
}
      
  function ruleRequireIfNBS2547890()
{
 var foo = [];
$j('#NBS254 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('Y',foo) > -1)){
pgRequireElement('STD106L');
 } else { 
pgRequireNotElement('STD106L');
 }   
}
      
  function ruleEnDisSTD1067891()
{
 var foo = [];
$j('#STD106 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('4',foo) > -1) || ($j.inArray('Indeterminate-Documented'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('2',foo) > -1) || ($j.inArray(' Negative - Documented'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('5',foo) > -1) || ($j.inArray(' Negative-Self Report'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('6',foo) > -1) || ($j.inArray(' Positive-Self Report'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('1',foo) > -1) || ($j.inArray(' Positive/Reactive-Documented'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('3',foo) > -1) || ($j.inArray(' Prelim Positive-Documented'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS259');
 } else { 
pgDisableElement('NBS259');
 }   
}
      
  function ruleRequireIfSTD1067892()
{
 var foo = [];
$j('#STD106 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('4',foo) > -1) || ($j.inArray('2',foo) > -1) || ($j.inArray('1',foo) > -1) || ($j.inArray('3',foo) > -1)){
pgRequireElement('NBS259');
 } else { 
pgRequireNotElement('NBS259');
 }   
}
      
  function ruleEnDisNBS2607893()
{
 var foo = [];
$j('#NBS260 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS262');
pgEnableElement('NBS261');
 } else { 
pgDisableElement('NBS262');
pgDisableElement('NBS261');
 }   
}
      
  function ruleRequireIfNBS2607894()
{
 var foo = [];
$j('#NBS260 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('Y',foo) > -1)){
pgRequireElement('NBS261');
 } else { 
pgRequireNotElement('NBS261');
 }   
}
      
  function ruleEnDisNBS2627895()
{
 var foo = [];
$j('#NBS262 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS263');
pgEnableElement('NBS450');
pgEnableElement('NBS447');
pgEnableElement('NBS264');
pgEnableElement('NBS265');
 } else { 
pgDisableElement('NBS263');
pgDisableElement('NBS450');
pgDisableElement('NBS447');
pgDisableElement('NBS264');
pgDisableElement('NBS265');
 }   
}
      
  function ruleRequireIfNBS2627896()
{
 var foo = [];
$j('#NBS262 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });
if(foo.length==0) return;

 if(($j.inArray('Y',foo) > -1)){
pgRequireElement('NBS263L');
pgRequireElement('NBS450');
pgRequireElement('NBS447L');
 } else { 
pgRequireNotElement('NBS263L');
pgRequireNotElement('NBS450');
pgRequireNotElement('NBS447L');
 }   
}
      
  function ruleEnDisNBS2667897()
{
 var foo = [];
$j('#NBS266 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS267');
 } else { 
pgDisableElement('NBS267');
 }   
}
      
  function ruleEnDisNBS2337898()
{
 var foo = [];
$j('#NBS233 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('N',foo) > -1) || ($j.inArray('No'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS237');
pgEnableElement('NBS235');
pgEnableElement('NBS238');
pgEnableElement('NBS239');
pgEnableElement('NBS234');
pgEnableElement('NBS236');
pgEnableElement('NBS240');
 } else { 
pgDisableElement('NBS237');
pgDisableElement('NBS235');
pgDisableElement('NBS238');
pgDisableElement('NBS239');
pgDisableElement('NBS234');
pgDisableElement('NBS236');
pgDisableElement('NBS240');
 }   
}
      
  function ruleEnDisNBS2407899()
{
 var foo = [];
$j('#NBS240 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('STD300');
 } else { 
pgDisableElement('STD300');
 }   
}
      
  function ruleEnDisSTD1147900()
{
 var foo = [];
$j('#STD114 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS232');
 } else { 
pgDisableElement('NBS232');
 }   
}
      
  function ruleEnDisSTD1027901()
{
 var foo = [];
$j('#STD102 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('PHC1477',foo) > -1) || ($j.inArray(' S - Yes'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('C',foo) > -1) || ($j.inArray(' Possible'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('P',foo) > -1) || ($j.inArray(' C - Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('102957003');
 } else { 
pgDisableElement('102957003');
 }   
}
      
  function ruleEnDisNBS1367902()
{
 var foo = [];
$j('#NBS136 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('300',foo) > -1) || ($j.inArray('300 - Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('350',foo) > -1) || ($j.inArray(' 350 - Resistant Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgSubSectionEnabled('NBS_INV_STD_UI_72');
 } else { 
pgSubSectionDisabled('NBS_INV_STD_UI_72');
 }   
}
      
  function ruleEnDisINV2907903()
{
 var foo = [];
$j('#INV290 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('12',foo) > -1) || ($j.inArray('Rapid Plasma Reagin (RPR)'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('11',foo) > -1) || ($j.inArray(' RPR CSF Ql'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('20',foo) > -1) || ($j.inArray(' VDRL test of cerebrospinal fluid (CSF)'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('21',foo) > -1) || ($j.inArray(' Venereal Disease Research Laboratory test (VDRL) (serology)'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('STD123_1');
 } else { 
pgDisableElement('STD123_1');
 }   
}
      
  function ruleEnDisINV2907904()
{
 var foo = [];
$j('#INV290 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('12',foo) > -1) || ($j.inArray('Rapid Plasma Reagin (RPR)'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('11',foo) > -1) || ($j.inArray(' RPR CSF Ql'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('20',foo) > -1) || ($j.inArray(' VDRL test of cerebrospinal fluid (CSF)'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('21',foo) > -1) || ($j.inArray(' Venereal Disease Research Laboratory test (VDRL) (serology)'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgDisableElement('LAB628');
pgDisableElement('LAB115');
 } else { 
pgEnableElement('LAB628');
pgEnableElement('LAB115');
 }   
}
      
  function ruleEnDisNBS1367905()
{
 var foo = [];
$j('#NBS136 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('200',foo) > -1) || ($j.inArray('200 - Chlamydia'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('300',foo) > -1) || ($j.inArray(' 300 - Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('350',foo) > -1) || ($j.inArray(' 350 - Resistant Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('490',foo) > -1) || ($j.inArray(' 490 - PID Syndrome'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV179');
 } else { 
pgDisableElement('INV179');
 }   
}
      
  function ruleEnDisNBS1367906()
{
 var foo = [];
$j('#NBS136 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('350',foo) > -1) || ($j.inArray('350 - Resistant Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS138');
 } else { 
pgDisableElement('NBS138');
 }   
}
      
  function ruleEnDisNBS1367907()
{
 var foo = [];
$j('#NBS136 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('300',foo) > -1) || ($j.inArray('300 - Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('350',foo) > -1) || ($j.inArray(' 350 - Resistant Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS137');
 } else { 
pgDisableElement('NBS137');
 }   
}
      
  function ruleEnDisNBS1367908()
{
 var foo = [];
$j('#NBS136 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('200',foo) > -1) || ($j.inArray('200 - Chlamydia'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('300',foo) > -1) || ($j.inArray(' 300 - Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('350',foo) > -1) || ($j.inArray(' 350 - Resistant Gonorrhea'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('INV361');
 } else { 
pgDisableElement('INV361');
 }   
}
      
  function ruleEnDisNBS1367909()
{
 var foo = [];
$j('#NBS136 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('710',foo) > -1) || ($j.inArray('710 - Syphilis'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('720',foo) > -1) || ($j.inArray(' primary'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('730',foo) > -1) || ($j.inArray(' 720 - Syphilis'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('740',foo) > -1) || ($j.inArray(' secondary'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('745',foo) > -1) || ($j.inArray(' 730 - Syphilis'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('750',foo) > -1) || ($j.inArray(' early latent'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('72083004');
pgEnableElement('STD102');
pgEnableElement('410478005');
pgEnableElement('PHC1472');
 } else { 
pgDisableElement('72083004');
pgDisableElement('STD102');
pgDisableElement('410478005');
pgDisableElement('PHC1472');
 }   
}
      
  function ruleEnDisNBS2757910()
{
 var foo = [];
$j('#NBS275 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgSubSectionEnabled('NBS_INV_STD_UI_71');
 } else { 
pgSubSectionDisabled('NBS_INV_STD_UI_71');
 }   
}
      
  function ruleEnDisNBS4477911()
{
 var foo = [];
$j('#NBS447 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('Y',foo) > -1) || ($j.inArray('Yes'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS448');
 } else { 
pgDisableElement('NBS448');
 }   
}
      
  function ruleEnDisNBS2677912()
{
 var foo = [];
$j('#NBS267 :selected').each(function(i, selected){
 foo[i] = $j(selected).val();
 });

 if(($j.inArray('2',foo) > -1) || ($j.inArray('2-Confirmed - Accessed Service'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('6',foo) > -1) || ($j.inArray(' 2-Confirmed-Accessed Service Within 14 Days'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('7',foo) > -1) || ($j.inArray(' 3-Confirmed-Accessed Service Within 30 Days'.replace(/^\s+|\s+$/g,''),foo) > -1) || ($j.inArray('8',foo) > -1) || ($j.inArray(' 4-Confirmed-Accessed Service After 30 Days'.replace(/^\s+|\s+$/g,''),foo) > -1)){
pgEnableElement('NBS302');
 } else { 
pgDisableElement('NBS302');
 }   
}
      
      
   
      
     function pgCheckForDynamicRuleErrorsOnSubmit() {
     	var errorElts = new Array();
    	var errorLabels = new Array();
    	var retVal;
     	retVal = ruleDCompINV1327853();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = ruleDCompINV1377854();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = ruleDCompDEM1157855();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = ruleDCompDEM1157856();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = ruleDCompNBS1747858();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = ruleDCompNBS1477859();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = ruleDCompINV1477868();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = stdRuleCheckCaseStatusOnSubmit();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = sexPartnersValidationOnSubmit();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = checkIfContactInvsDispositioned();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = calculateTotalSexPartnersOnSubmit();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = stdRuleCheckReferralBasisOnSubmit();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = calculateSTD888();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = stdCheckPartnersPastYearOnSubmit();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = calculateSTD999();
     	   if (retVal != null && retVal.elements != undefined) { errorElts = errorElts.concat(retVal.elements); errorLabels = errorLabels.concat(retVal.labels); }  
        retVal = stdCheckPartnersInInterviewPeriodOnSubmit();
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
           
           	stdCheckDispositionValue();stdReferalBasisExposureFreqEnabReq();stdResetPageStateOnLoad();stdSetupDiagnosisOptions();stdBehavorialRiskAssessedEntry();ruleEnDisINV1287849();ruleEnDisINV1457850();ruleEnDisINV1507851();ruleEnDisINV1527852();ruleEnDisDEM1277857();ruleEnDisNBS2167861();ruleEnDisNBS2187863();ruleEnDisNBS2217865();ruleEnDisNBS1117867();ruleEnDisINV1787869();ruleEnDisNBS1297871();ruleEnDisNBS1317872();ruleEnDisNBS1337873();ruleEnDisNBS2237874();ruleEnDisNBS2257875();ruleEnDisNBS2277876();ruleEnDisDEM1137877();ruleEnDisSTD1217878();ruleEnDisDEM1557879();ruleEnDisDEM1137880();ruleEnDisINV5027881();ruleHideUnhNBS2097882();ruleHideUnhNOT1207883();ruleHideUnhNBS1687884();ruleHideUnhNBS2427885();ruleHideUnhNBS2447886();ruleHideUnhSTD1177887();ruleEnDisNBS1437888();ruleEnDisNBS2547889();ruleEnDisSTD1067891();ruleEnDisNBS2607893();ruleEnDisNBS2627895();ruleEnDisNBS2667897();ruleEnDisNBS2337898();ruleEnDisNBS2407899();ruleEnDisSTD1147900();ruleEnDisSTD1027901();ruleEnDisNBS1367902();ruleEnDisINV2907903();ruleEnDisINV2907904();ruleEnDisNBS1367905();ruleEnDisNBS1367906();ruleEnDisNBS1367907();ruleEnDisNBS1367908();ruleEnDisNBS1367909();ruleEnDisNBS2757910();ruleEnDisNBS4477911();ruleEnDisNBS2677912();ruleRequireIfNBS2097860();ruleRequireIfNBS2167862();ruleRequireIfNBS2187864();ruleRequireIfNBS2217866();ruleRequireIfINV1787870();ruleRequireIfNBS2547890();ruleRequireIfSTD1067892();ruleRequireIfNBS2607894();ruleRequireIfNBS2627896();
          
       return;
      }

	<!-- === batch subsection add edit check functions (if any) follow ===-->
     	function pgENTITYID100BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('ENTITYID100');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('ENTITYID100errorMessages', errorLabels); return false;}
		$j('#ENTITYID100errorMessages').css("display", "none");
		return true;
		}
        function pgNBS11002BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS11002');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS11002errorMessages', errorLabels); return false;}
		$j('#NBS11002errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_18BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_18');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_18errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_18errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_26BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_26');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_26errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_26errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_27BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_27');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_27errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_27errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_30BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_30');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_30errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_30errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_34BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_34');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_34errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_34errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_49BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_49');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_49errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_49errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_51BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_51');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_51errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_51errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_71BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_71');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_71errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_71errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_72BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_72');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_72errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_72errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_61BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_61');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_61errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_61errorMessages').css("display", "none");
		return true;
		}
        function pgNBS_INV_STD_UI_64BatchAddFunction()
     	    { 
    			var errorLabels = new Array();
    			var retVal;
    			var retRule;
    			 
     	        
        	
        	retVal=pgCheckForErrorsOnBatchSubsection('NBS_INV_STD_UI_64');
         	if (retVal != null && retVal != undefined) { errorLabels = errorLabels.concat(retVal); } 

        
        	if (errorLabels.length > 0) {
        	displayErrors('NBS_INV_STD_UI_64errorMessages', errorLabels); return false;}
		$j('#NBS_INV_STD_UI_64errorMessages').css("display", "none");
		return true;
		}
        
      
     </script>
     
      
 
    <% 
        Map map = new HashMap();
        if(request.getAttribute("SubSecStructureMap") != null){
                          map =(Map)request.getAttribute("SubSecStructureMap");
     }%>
   
               
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
			
			var calendars = $j( "img[src*='calendar.gif']");
    		if($j("#"+key).parent().parent().find(calendars)[0]!=undefined && $j("#"+key).parent().parent().find(calendars)[0]!=null)
				$j("#"+key).parent().parent().find(calendars)[0].attr("tabIndex", "0");
			$j("#"+key).parent().parent().find("input").attr("disabled", false);
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
        }      //viewClicked
        
        
        
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
		            
		            
	//Only if there's data in the textarea:
			var valueQuestion = $j("#"+elementId).val();
			if(valueQuestion!="" && valueQuestion!=null && valueQuestion!=undefined){
	
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
     
     
		     }else{//In case the text was deleted from the textarea
				dwr.util.setValue(elementId+"Date","");
				dwr.util.setValue(elementId+"User",""); 
			 }
     
      }
                                             
      
                    
      
               
          </script>  
        
     <style type="text/css">
            table.FORM {width:100%; margin-top:15em;}
     </style>
    </head>
      
      
     <% 
    int subSectionIndex = 0;

    String tabId = "";
      String [] sectionNames  = {"Patient Information","Address Information","Telephone and Email Contact Information","Race and Ethnicity Information","Other Identifying Information","Investigation Information","OOJ Initiating Agency Information","Reporting Information","Clinical","Epidemiologic","Comments","Case Numbers","Initial Follow-up","Surveillance","Notification of Exposure Information","Field Follow-up Information","Interview Case Assignment","Case Closure","Pregnant Information","900 Case Status","Risk Factors-Last 12 Months","Hangouts","Partner Information","Target Populations","STD Testing","Signs and Symptoms","STD History","900 Partner Services Information","Contact Investigation"};
     ;
  
    int sectionIndex = 0;
    String sectionId = "";

%> 

    <body class onload="startCountdown();autocompTxtValuesForJSP();pageCreateLoad('${PageForm.attributeMap.selectEltIdsArray}');
    populateBatchRecords();pgPopulateMMWR('INV165','INV166');pgPopulateInfoAsOf('NBS104');
    attachMoveFocusFunctionToTabKey();
    pgOnLoadCalcRptAge('DEM115', 'INV2001', 'INV2002', 'NBS096', 'NBS104');
    pgCheckOnloadOtherEntryAllowedFields();pgCheckDynamicRulesOnLoad();pgCheckForFieldsToHighlightOnEdit();pgPopulateCounties();addTabs();">
        <div id="pageview"></div>
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
        <html:hidden name="PageForm" property="pageClientVO.answer(DEM165)" styleId="DEM165_Hidden"/>
		<html:hidden name="PageForm" property="pageClientVO.answer(DEM165_W)" styleId="DEM165_W_Hidden"/>
		<html:hidden name="PageForm" property="pageClientVO.answer(MTH168)" styleId="MTH168_Hidden"/>
		<html:hidden name="PageForm" property="pageClientVO.answer(INV505)" styleId="INV505_Hidden"/>
            <html:form action="/PageAction.do">
                <!-- Body div -->
                <div id="bd">
   					<!-- Top Nav Bar and top button bar -->
					 <%@ include file="/jsp/topNavFullScreenWidth.jsp" %>                 
				 <!-- For create/edit mode only -->
                    <logic:notEqual name="PageForm" property="actionMode" value="Preview">
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
             	
             	
        		
         	
         	
         	 
         	 
		 
      		 <layout:tab key="Case Info">     
      		<jsp:include page="CaseInfo.jsp"/>
             	</layout:tab>               	
             	
             	
        		
         	
         	
         	 
         	 
		 
      		 <layout:tab key="Case Management">     
      		<jsp:include page="CaseManagement.jsp"/>
             	</layout:tab>               	
             	
             	
        		
         	
         	
         	 
         	 
		 
      		 <layout:tab key="Core Info">     
      		<jsp:include page="CoreInfo.jsp"/>
             	</layout:tab>               	
             	
             	
        		
         	
         	
         	         <logic:equal name="PageForm" property="securityMap(ContactTracingEnableInd)" value="Y">
          		 <logic:equal name="PageForm" property="securityMap(checkToViewContactTracing)" value="true">
         	
         	 
         	 
		 
      		 <layout:tab key="Contact Tracing">     
      		<jsp:include page="ContactTracing.jsp"/>
             	</layout:tab>               	
             	
             	
  			</logic:equal>
   			</logic:equal>
           	
                
      
           
          <logic:equal name="PageForm" property="securityMap(ContactTracingEnableInd)" value="Y">
          <logic:equal name="PageForm" property="securityMap(checkToViewContactTracing)" value="true">
                            <layout:tab key="Contact Records">
                                <jsp:include page="/pagemanagement/contactTracing/ContactTracing.jsp"/>
                            </layout:tab> 
           </logic:equal>
           </logic:equal> 
                        
                       
          
          <logic:equal name="PageForm" property="securityMap(SupplementalInfoEnableInd)" value="Y">
          <logic:equal name="PageForm" property="securityMap(checkToViewSupplementalInfo)" value="true">
                            <layout:tab key="Supplemental Info">
                                <jsp:include page="/pagemanagement/supplemental/SupplementalInformation.jsp"/>
                            </layout:tab> 
           </logic:equal>
           </logic:equal> 
                          
        
    	  </layout:tabs>  
	  
	   <%@ include file="/jsp/bottombuttonbarFullScreenWidth.jsp" %>
               </html:form>
          </div> <!-- Container Div -->
    </body>
     
</html>
		

	