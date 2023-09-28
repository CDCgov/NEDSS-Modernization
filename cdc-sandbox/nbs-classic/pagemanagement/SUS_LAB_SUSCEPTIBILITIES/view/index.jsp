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
    <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPageSubForm.js"></SCRIPT>
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
     
     
     function hideRetainButtonsForNextEntry(){

		if(getElementByIdOrByName("NBS_UI_28")!=null && getElementByIdOrByName("NBS_UI_28")!=undefined)
			getElementByIdOrByName("NBS_UI_28").hide();//Retain Next Entry
		if(getElementByIdOrByName("NBS_UI_27")!=null && getElementByIdOrByName("NBS_UI_27")!=undefined)
			getElementByIdOrByName("NBS_UI_27").hide();//Patient Search	
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
	
	<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">

	  function deleteSubForm() {
	  var confirmMsg="You have indicated that you would like to delete this Isolate Tracking. Would you like to continue with this action?";
	  if (confirm(confirmMsg)) {
	  
	  JPageSubForm.deleteCurrentSubForm(function(data){
			  	//var msg = data[0];
			  //	if(msg !=null && msg !=""){
			  	//	alert(msg);
			  //	}else{		  
					window.returnValue = "windowClosed"; 
					window.close();    
			  //	}
			  	
			  });
	  } else return(false);
	}
	
	
	</logic:equal>
	
	
	
	<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
		  function deleteSubForm() {
	  var confirmMsg="You have indicated that you would like to delete this Susceptibility Test. Would you like to continue with this action?";
	  if (confirm(confirmMsg)) {
	  
	  JPageSubForm.deleteCurrentSubForm(function(data){
			    
					window.returnValue = "windowClosed"; 
					window.close();    
			  	
			  });
	  } else return(false);
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
	
	
     function cancelSubForm()
      {
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
	
		function showPrintFriendlySubFormPage()
	{
	
		var divElt =getElementByIdOrByName("pageview");
		divElt.style.display = "block";
		var o = new Object();
		o.opener = self;
	 
		var URL = "/nbs/PageSubFormAction.do?method=viewGenericLoad&mode=print";
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
		
		//Disable button.
		$j("input[type=button]").attr("disabled", "disabled");
	}

	function closePrinterFriendlyWindow() {
		self.close();

		var opener = getDialogArgument(); 
		var cview = opener.document.getElementById("pageview")
		if (cview != null)
			cview.style.display = "none";

		return false;
	}
	function printForm() {
		document.forms[0].target = "_blank";
		document.forms[0].action = "/nbs/PageAction.do?method=printLoad";
	}

	function transferLabOwnership()
	{ 
		document.forms[0].action = "${PageForm.attributeMap.transferOwnership}";
		document.forms[0].submit();				
	}
	function markAsReviewed(reason)
	{ 
		var url = "${PageForm.attributeMap.markAsReviewButtonHref}";
		if(reason!=null && reason!='')
	    	url = url+'&markAsReviewReason='+reason;
		document.forms[0].action = url;
		document.forms[0].submit();				
	}
	
	 // This function is getting called when marking STD lab or morbidity report(View Page) as reviewed
    function OpenMarkAsReviewed(event) {
    	var block = getElementByIdOrByName("pageview");
    	block.style.display = "block";
    	var o = new Object();
    	o.opener = self;
    	var PDLogic = "${PageForm.attributeMap.PDLogic}";
    	var URL = "/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event="+event+"&PDLogic="+PDLogic+"&context=loadMAR";
     	var modWin = openWindow(URL, o,GetDialogFeatures(600, 350, false, true), block, "");
    	return false;
    }
	

	function editContextForm(){
		var msg = "";
		var actUid = "<%= request.getAttribute("actUid")%>";
		var confirmMsg = "A notification message request exists against this event. If you continue with this action, you may change the content of the message. Select OK to continue or Cancel to not continue.";
		var businessObjType = '${PageForm.businessObjectType}';
		if(businessObjType=='TRMT'){
		document.forms[0].action = "${PageForm.attributeMap.editButtonHref}";
		document.forms[0].submit();	
		return;
		}
		  JPageForm.checkForExistingNotificationsByCdsAndUid(actUid, businessObjType, function(data){
		  	msg = data[0];
		  	if(msg !=null && msg !=""){
			    if (confirm(confirmMsg)) {
					editCalled = true;
					document.forms[0].action = "${PageForm.attributeMap.editButtonHref}";
					document.forms[0].submit();
			    } else {
					return(false);
			    }
			}else{
				editCalled = true;
				document.forms[0].action = "${PageForm.attributeMap.editButtonHref}";
				document.forms[0].submit();
		  	}
		  });
			
		return(false);
	}


  function deleteContextForm() {
     document.forms[0].target = "";
     var confirmMsg = "You have indicated that you would like to delete this Lab Report. By doing so, this record will no longer be available in the system. Would you like to continue with this action?";
     if (confirm(confirmMsg)) {
       document.forms[0].action = "${PageForm.attributeMap.deleteButtonHref}";
       document.forms[0].submit();
     } else {
       return false;
     }
    }
	
	function CreateInvestigationForm()
	{ 
		document.forms[0].action = "${PageForm.attributeMap.creatInvestigationButtonHref}";
		document.forms[0].submit();				
	}
	function AssociateInvestigationForm()
	{ 
		document.forms[0].action = "${PageForm.attributeMap.associateToInvestigationsButtonHref}";
		document.forms[0].submit();				
	}
	
	function clearMarkAsReviewed()
	{ 
		document.forms[0].action = "${PageForm.attributeMap.clearMarkAsReviewButtonHref}";
		document.forms[0].submit();				
	}
	function editForm() {
		editCalled = true;
		document.forms[0].action = "<%= request.getAttribute("genericViewEditUrl")%>";
		document.forms[0].submit();
	}
	
	function editSubForm() {
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
        
  
      
       function pgCheckDynamicRulesHideUnhideOnLoad() {
       	    var JCTContactForm = JBaseForm;
            var allfunctions = "ruleHideUnhNBS4556413();";
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
    String sb = "";
    Map map = new HashMap();
    if(request.getAttribute("SubSecStructureMap") != null){
		map =(Map)request.getAttribute("SubSecStructureMap");
    }
%>
                     
    
    <script language="JavaScript"> 
            
		var answerCache = { };
		var viewed = -1,count=0;   
                    
                    
                    
                    
/**
*setElectronicIndicatorOnLoad: gets the electronic indicator from the session and sets the value in the corresponding question identifier (NBS455)
*/
function setElectronicIndicatorOnLoad(){

	var questionId = "NBS455";
	var electronicInd = "<%= request.getSession().getAttribute("electronicIndicator")%>";
	if(getElementByIdOrByName(questionId)!=null && getElementByIdOrByName(questionId)!=undefined){
		$j("#"+questionId).val(electronicInd);

		var optionSelected = getElementByIdOrByName(questionId).selectedIndex;
		if(optionSelected>0 && electronicInd != null && getElementByIdOrByName(questionId)!=null && getElementByIdOrByName(questionId) != undefined){
			var value = getElementByIdOrByName(questionId).options[optionSelected].text;
			getElementByIdOrByName(questionId+"_textbox").value = value;
		}
	}
}


      function getCorrectForm (){
    
  	  var form =JPageForm;
      var bussinessObject = "<%=request.getAttribute("businessObjectType")%>";
        
		if(bussinessObject=="SUS")
			form = JPageSubForm;
		else
			form = JPageForm;
			
		return form;
		
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
	<% String[][] batchrecinsert  =  (String[][])pair.getValue();   
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
			
				//Empty the currentKey field
			
			if(document.getElementById("NBS459")!=null && document.getElementById("NBS459") !=undefined)
				document.getElementById("NBS459").setAttribute("value","");
		
		
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
                       		
function fillTable(subSecNm,pattern,questionbody) {


var form = getCorrectForm();

	form.getAllAnswer(subSecNm,function(answer) {
		// Delete all the rows except for the "pattern" row
	    dwr.util.removeAllRows(questionbody, { filter:function(tr) { return (tr.id != pattern); }});					
		dwr.util.setEscapeHtml(false);		
	    // Create a new set cloned from the pattern row -gt
	    var ans, id,rowclass="";
	<%  if(map !=  null){
		Iterator    itLab3 = map.entrySet().iterator(); 
		batchrecview  = null;			   	 
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
	

	<logic:equal name="PageSubForm" property="businessObjectType" value="LAB">
	if(subSecNm == 'RESULTED_TEST_CONTAINER'){
	 		generateRowKeyValues(subSecNm);
	 		addSusceptibilityToResultedTestBatchEntry(subSecNm,0,3);
	 		}
	</logic:equal>
		
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
     
          <logic:equal name="PageSubForm" property="businessObjectType" value="LAB">
	   		deleteKeyFromHashMapInSession(eleid);
	   	</logic:equal>

     	
} 		
function clearQuestion() {
     viewed = -1;
     dwr.util.setValues({subsecNm:"Others", id:viewed,answerMap:null });
}
                	        
function getDropDownValues(newValue) {
	//alert(newValue);
	var form = getCorrectForm();
    	form.getDropDownValues(newValue, function(data) {
        	dwr.util.removeAllOptions(newValue);  
        	dwr.util.addOptions(newValue,data,"key","value"); 		       
        });
}
                  
function viewClicked(eleid,subSecNm) {		               		    
	var key;
	var answer = answerCache[eleid.substring(4)];
	<% batchrecview  = null;
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
		
		setCurrentKeyfromSelectedRow(eleid, "View");
		enableActionButtons();
		
} //viewClicked

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
    
        String PatientRevision = (request.getAttribute("PatientRevision") == null) ? "" : ((String)request.getAttribute("PatientRevision"));
        String caseLocalId = (request.getAttribute("DSInvUid") == null) ? "" : ((String)request.getAttribute("DSInvUid"));
       	String perMprUid =  (request.getAttribute("DSPatientPersonUID") == null) ? "" : ((String)request.getAttribute("DSPatientPersonUID"));									
       
  %>       


  <% String printMode = (request.getAttribute("mode") == null) ? "" : ((String)request.getAttribute("mode"));
    // Note: include a call to close the child window and make the parent window's bg setting from gray to white.
    // since the same JSP is used for both regular display mode and printer friendly display mode, this kind of check
    // is required to prevent the window from closing itself in the regular display mode.
    if (printMode.equals("print")) { %>
        <body class="popup" onload="startCountdown();checkPageDelete();disablePrintLinks();populateBatchRecords();cleanupPatientRacesViewDisplay();addTabs();addRolePresentationToTabsAndSections();pgCheckDynamicRulesHideUnhideOnLoad();" onunload="return closePrinterFriendlyWindow();selectTabOnSubmit();">
    <% } else { %> 
    
    <logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
    <logic:notEqual name="PageSubForm"  property="businessObjectType" value="SUS">			
       <logic:notEqual name="PageSubForm" property="businessObjectType" value="LAB">			
        	<body class="popup" onload="startCountdown();checkPageDelete();selectTabOnSubmit();populateBatchRecords();cleanupPatientRacesViewDisplay();addTabs();addRolePresentationToTabsAndSections();setElectronicIndicatorOnLoad();pgCheckDynamicRulesHideUnhideOnLoad();" onunload="handleWindowUnload(); return false;">
	   </logic:notEqual>
	   <logic:equal name="PageSubForm" property="businessObjectType" value="LAB">			
        	<body class="popup" onload="startCountdown();checkPageDelete();selectTabOnSubmit();populateBatchRecords();cleanupPatientRacesViewDisplay();addTabs();addRolePresentationToTabsAndSections();pgCheckDynamicRulesHideUnhideOnLoad();hideSameAsReportingFacilityCheckBoxOnLoad();hideRetainButtonsForNextEntry();selectTabLab('<%=request.getAttribute("TabtoFocus")%>');" onunload="handleWindowUnload(); return false;">
	   </logic:equal>
	 </logic:notEqual>
	 </logic:notEqual>
 	<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
			<body class="popup" onload="startCountdown();checkPageDelete();selectTabOnSubmit();populateBatchRecords();cleanupPatientRacesViewDisplay();addTabs();addRolePresentationToTabsAndSections();pgCheckDynamicRulesHideUnhideOnLoad();onloadBlockParent();" onunload="unloadSubForms(true, event);">
	</logic:equal>
	<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
			<body class="popup" onload="startCountdown();checkPageDelete();selectTabOnSubmit();populateBatchRecords();cleanupPatientRacesViewDisplay();addTabs();addRolePresentationToTabsAndSections();setElectronicIndicatorOnLoad();pgCheckDynamicRulesHideUnhideOnLoad();onloadBlockParent();" onunload="unloadSubForms(true, event); return false;">
	</logic:equal>					
    <% } %>

        <div id="pageview"></div>        
        <!-- Container Div: To hold top nav bar, button bar, body and footer -->
        <div id="doc3">
            <html:form action="/PageAction.do">
            	<input type="hidden" name="deleteError" value="<%= request.getAttribute("deleteError") == null ? "" : request.getAttribute("deleteError")%>"/>
	    <logic:notEqual name="PageSubForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
	            <div class="popupTitle"> ${BaseForm.pageTitle} </div>
		</logic:notEqual>

         <!-- Top button bar -->
         
		<logic:equal name="PageSubForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
				<%@ include file="/jsp/topNavFullScreenWidth.jsp" %>     
				<%@ include file="/jsp/topbuttonbarFullScreenWidth.jsp" %>
       	</logic:equal>
		<logic:notEqual name="PageSubForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
			<div class="popupButtonBar">
				<logic:equal name="PageSubForm" property="securityMap(editGenericPermission)" value="true">
					<logic:equal name="PageSubForm" property="businessObjectType" value="VAC">
					   <input type="submit" name="Edit" value="Edit" onclick="return editVaccinationForm(${actUid})" />
					</logic:equal>
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="VAC">
						<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
					  	<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
					  		
					  		 <input type="submit" name="Edit" value="Edit" onclick="return editSubForm(${actUid})" />
						</logic:notEqual>
						</logic:equal>
						<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
					  	<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
					  		
					  		 <input type="submit" name="Edit" value="Edit" onclick="return editSubForm(${actUid})" />
						</logic:notEqual>
						
						</logic:equal>
						<logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
						<logic:notEqual name="PageSubForm" property="businessObjectType" value="SUS">
						
							<input type="submit" name="Edit" value="Edit" onclick="editForm()"/>
						</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
				</logic:equal>       
				
				<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
					<input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlySubFormPage();"/>
				</logic:equal>
	
				<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
					<input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlySubFormPage();"/>
				</logic:equal>
				
				<logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
				<logic:notEqual name="PageSubForm" property="businessObjectType" value="SUS">
					<input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlyPage();"/>
				</logic:notEqual>
				
				</logic:notEqual>
				 
				<logic:equal name="PageSubForm" property="securityMap(deleteGenericPermission)" value="true">
					 <logic:equal name="PageSubForm" property="businessObjectType" value="VAC">
							<input type="submit" name="Delete" value="Delete" onclick="return deleteVaccination(${actUid});" />
					 </logic:equal>
					 <logic:equal name="PageSubForm" property="businessObjectType" value="IXS">
							<input type="submit" name="Delete" value="Delete" onclick="return deleteInterview();" />
							<logic:equal name="PageSubForm" property="attributeMap.COINFECTION_INV_EXISTS" value="true">
								<input type="button" name="AssociateInvestigations" value="Associate Investigations" onclick="ManageInterviewInvestigationAssociations(${actUid})"/>
							</logic:equal>	
					 </logic:equal>
					 
					 <logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
						<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
					  	
							<input type="button" name="Delete" value="Delete" onclick="return deleteSubForm();" />
						 </logic:notEqual>
					 </logic:equal>
					 <logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
						<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
					  	
							<input type="button" name="Delete" value="Delete" onclick="return deleteSubForm();" />
						</logic:notEqual>
					 </logic:equal>
					 
					 
				</logic:equal>
				<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
					<input type="button" name="Cancel" value="Close" onclick="cancelSubForm()" />
				</logic:equal>
				<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
					<input type="button" name="Cancel" value="Close" onclick="cancelSubForm()" />
				</logic:equal>
				
				<logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
				<logic:notEqual name="PageSubForm" property="businessObjectType" value="SUS">
				
					<input type="button" name="Cancel" value="Close" onclick="cancelView(event)" />
				</logic:notEqual>
				</logic:notEqual>
				
			</div>
		</logic:notEqual>
        
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
                
                        
      
     <!-- ################### PAGE TAB ###################### - - -->
      <layout:tabs width="100%" styleClass="tabsContainer">
             
       		
        
	  	
         <layout:tab key="Susceptibility Test">     
        <jsp:include page="viewSusceptibilityTest.jsp"/>
        </layout:tab>  
        
           
          </layout:tabs>  
          
          
          <logic:equal name="PageSubForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
				<%@ include file="/jsp/bottombuttonbarFullScreenWidth.jsp" %>
       	  </logic:equal>
		  <logic:notEqual name="PageSubForm" property="genericType" value="GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE">
				<div class="popupButtonBar">
					<logic:equal name="PageSubForm" property="securityMap(editGenericPermission)" value="true">
						<logic:equal name="PageSubForm" property="businessObjectType" value="VAC">
						   <input type="submit" name="Edit" value="Edit" onclick="return editVaccinationForm(${actUid})" />
			            </logic:equal>
			            <logic:notEqual name="PageSubForm" property="businessObjectType" value="VAC">				
							<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
							<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
					  			 <input type="submit" name="Edit" value="Edit" onclick="return editSubForm(${actUid})" />
							</logic:notEqual>
							</logic:equal>
							<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
					  		<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
					  		
					  			 <input type="submit" name="Edit" value="Edit" onclick="return editSubForm(${actUid})" />
							
							</logic:notEqual>
							</logic:equal>
							<logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
							<logic:notEqual name="PageSubForm" property="businessObjectType" value="SUS">
								<input type="submit" name="Edit" value="Edit" onclick="editForm()"/>
							</logic:notEqual>
							</logic:notEqual>
						</logic:notEqual>
					</logic:equal>          
					
					<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
						<input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlySubFormPage();"/>
					</logic:equal>
		
					<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
						<input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlySubFormPage();"/>
					</logic:equal>
					
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="SUS">
					
						<input type="submit" name="Submit" value="Print" onclick="return showPrintFriendlyPage();"/>
					</logic:notEqual>
					</logic:notEqual>
				
					<logic:equal name="PageSubForm" property="securityMap(deleteGenericPermission)" value="true">
						<logic:equal name="PageSubForm" property="businessObjectType" value="VAC">
						   <input type="submit" name="Delete" value="Delete" onclick="return deleteVaccination(${actUid});" />
			            </logic:equal>
						<logic:equal name="PageSubForm" property="businessObjectType" value="IXS">
						  <input type="submit" name="Delete" value="Delete" onclick="return deleteInterview();" />
						</logic:equal>
						<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
						<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
					  	
							<input type="button" name="Delete" value="Delete" onclick="return deleteSubForm();" />
					 	</logic:notEqual>
					 	</logic:equal>
					 	<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
						<logic:notEqual name="PageSubForm" property="actionModeParent" value="View">
					  	
							<input type="button" name="Delete" value="Delete" onclick="return deleteSubForm();" />
					 	</logic:notEqual>
					 	</logic:equal>
						<logic:equal name="PageSubForm" property="attributeMap.COINFECTION_INV_EXISTS" value="true">
						   <input type="button" name="AssociateInvestigation" value="Associate Investigations" onclick="ManageInterviewInvestigationAssociations(${actUid})"/>
						</logic:equal>					
					</logic:equal> 
					
					<logic:equal name="PageSubForm" property="businessObjectType" value="ISO">
					<input type="button" name="Cancel" value="Close" onclick="cancelSubForm()" />
					</logic:equal>
					<logic:equal name="PageSubForm" property="businessObjectType" value="SUS">
					<input type="button" name="Cancel" value="Close" onclick="cancelSubForm()" />
					</logic:equal>
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="ISO">
					<logic:notEqual name="PageSubForm" property="businessObjectType" value="SUS">
					
						<input type="button" name="Cancel" value="Close" onclick="cancelView(event)" />
					</logic:notEqual>
					</logic:notEqual>
				
					
				</div>
			</logic:notEqual>      		
	</html:form>
      </div> <!-- Container Div -->
    </body>
     
</html>

