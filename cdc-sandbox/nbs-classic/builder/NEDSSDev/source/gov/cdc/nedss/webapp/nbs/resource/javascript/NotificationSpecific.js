function transferInvestigationOwnership() {
	var block = getElementByIdOrByName("blockparent");
	block.style.display = "block";	
	var o = new Object();
	o.opener = self;
	//var returnMessage = window.showModalDialog("/nbs/LoadTranOwnershipInv1.do?method=transferOwnershipLoad", o, GetDialogFeatures(850, 500, false));
	
	var URL = "/nbs/LoadTranOwnershipInv1.do?method=transferOwnershipLoad";
	var modWin = openWindow(URL, o,GetDialogFeatures(850, 500, false, true), block, "");
	
	if(modWin){
		var saveMsg = '<div class="submissionStatusMsg"> <div class="header"> Page Loading </div>' +  
        '<div class="body">Please wait...  The system is loading the requested page.</div> </div>';         
		$j.blockUI({  
		    message: saveMsg,  
		    css: {  
		        top:  ($j(window).height() - 500) /2 + 'px', 
		        left: ($j(window).width() - 500) /2 + 'px', 
		        width: '500px'
		    }  
		});
		
		modWin.onbeforeunload = function(){
			$j.unblockUI();
		}
	}
	
	
	
	return false;
}

function pamTOwnership(jurisd,exportFacility,comment ) {
	getPage("/nbs/LoadTranOwnershipInv1.do?method=transferOwnershipSubmit&Jurisdiction=" + jurisd + '&amp;exportFacility=' + exportFacility + '&amp;comment=' + comment);
}

function shareCase() {
	var block = getElementByIdOrByName("blockparent");
	block.style.display = "block";	
	var o = new Object();
	o.opener = self;
	//window.showModalDialog("/nbs/LoadTranOwnershipInv1.do?method=shareCaseLoad", o, GetDialogFeatures(700, 400, false));
	
	var URL = "/nbs/LoadTranOwnershipInv1.do?method=shareCaseLoad";
	var modWin = openWindow(URL, o,GetDialogFeatures(700, 400, false, true), block, "");
	return false;
}

function shareCaseSubmit(recipient,comment ) {
	getPage("/nbs/LoadTranOwnershipInv1.do?method=shareCaseSubmit&exportFacility=" + recipient + '&comment=' + comment);
}

function checkApproveComments(pFormAction, comments_id){

   var errorMsg = getElementByIdOrByName("errorMessage");
   if(errorMsg != null)
   errorMsg.innerText = "";
   document.forms['nedssForm'].action=pFormAction;
   document.forms['nedssForm'].submit();
}

function checkRejectComments(pFormAction, comments_id){


   var comments = getElementByIdOrByName(comments_id);
   var errorMsg = getElementByIdOrByName("errorMessage");
   if(errorMsg != null)
   errorMsg.innerText = "";

   if(comments.value == "")
   {
      var errorTD = getElementByIdOrByName("error1");
      if( errorTD )
      {
        var errorText=null;
        errorText = makeErrorMsg('ERR039');
        errorTD.innerText = errorText;
        errorTD.setAttribute("className", "error");
        comments.setAttribute("className", "error");
        document.location.href="#top";
      }
     return true;
   }
   document.forms['nedssForm'].action=pFormAction;
   document.forms['nedssForm'].submit();
}
function checkAll(someNode, field) {
	var tableNode = getParentTable( someNode );
	tableNode = getParentTable(tableNode);
	//alert(tableNode.innerHTML);
	if( tableNode == null ) {
		
		return( null );
	}
	var tFootNodeArr = tableNode.getElementsByTagName("TFOOT");
	//alert( "size = " + tFootNodeArr.length )
	if( (tFootNodeArr==null) || (tFootNodeArr[0]==null) ) {
		return( null );
	}
	var tFootNode = tFootNodeArr[0];
		
	var tHeadNodeArr = tableNode.getElementsByTagName("THEAD");
	if(tFootNode.currentPage==null){
		var currentPageAttribute = document.createAttribute("currentPage");
		currentPageAttribute.nodeValue = 1;
		tFootNode.setAttributeNode(currentPageAttribute);
	}
	var tRNodeArr = tableNode.getElementsByTagName("TR");
	var currentPage = tFootNode.currentPage;
	
	if(tRNodeArr[0]!=null){

		for (var i=0; i < tRNodeArr.length; i++) {	
			  	
			  	var childNodes = tRNodeArr[i].getElementsByTagName("INPUT");
			  	for(var j=0; j < childNodes.length; j++){
			  		if(childNodes[j].type=="checkbox"){
			  			childNodes[j].checked = true;
			  		}
			  }
		}
	}
}

function checkAll_ck()
{
	var cbs = document.forms[0].getElementsByTagName("INPUT");
	for(var i=0; i<cbs.length; i++){
		cbs[i].checked = true;
		}
}


function clearAll_ck()
{
	var cbs = document.forms[0].getElementsByTagName("INPUT");
	for(var i=0; i<cbs.length; i++){
		cbs[i].checked = false;
		}
}
function clearAll(someNode, field) {
	var tableNode = getParentTable( someNode );
	tableNode = getParentTable(tableNode);
	
	if( tableNode == null ) {
		return( null );
	}
	var tFootNodeArr = tableNode.getElementsByTagName("TFOOT");
	//alert( "size = " + tFootNodeArr.length )
	if( (tFootNodeArr==null) || (tFootNodeArr[0]==null) ) {
		return( findCorrespondingCurrentPage( tableNode.parentNode ) );
	}
	var tFootNode = tFootNodeArr[0];
		
	var trNodeArr = tFootNode.getElementsByTagName("tr");
	var tHeadNodeArr = tableNode.getElementsByTagName("THEAD");
	if(tFootNode.currentPage==null){
		var currentPageAttribute = document.createAttribute("currentPage");
		currentPageAttribute.nodeValue = 1;
		tFootNode.setAttributeNode(currentPageAttribute);
	}
	var tRNodeArr = tableNode.getElementsByTagName("TR");
	var currentPage = tFootNode.currentPage;
	
	if(tRNodeArr[0]!=null){

		for (var i=0; i < tRNodeArr.length; i++) {	
			  	
			  	var childNodes = tRNodeArr[i].getElementsByTagName("INPUT");
			  	for(var j=0; j < childNodes.length; j++){
			  		if(childNodes[j].type=="checkbox"){
			  			childNodes[j].checked = false;
			  		}
			  		
			  		
			  	}
		}
	}
}

function removeNotifications()
{
	var z = 0;
	var errorTD = getElementByIdOrByName("errorRange");
	errorTD.setAttribute("className", "none");
	var errorText="";
	var inputs = document.getElementsByTagName("input");
	var frm = getElementByIdOrByName("nedssForm");
	var labelList = new Array();
	var errors = new Array();
    var index = 0;
    var isError = false;
    
	for(var i=0;i<inputs.length;i++)
	{
	  if(inputs.item(i).id=="isRemoved" && inputs.item(i).checked==true)
	  {
	     z++; 
	   }
	}
	if(z=="0")
	{
		
	//errorText = "Please select at least one Notification to be removed from the queue and try again.";
	//makeErrorMsg('ERR161',labelList.concat(""));
	//errorTD.innerText = errorText;
	//errorTD.setAttribute("className", "error");
	//document.location.href="#top";
	
	//This is my code
		errors[index++] = "Please select at least one Notification to be removed from the queue and try again.";
		isError = true;
		if (isError) {
            displayGlobalErrorMessage(errors);
            return false;
        }
	
	}
	else
	{
		      frm.action='/nbs/ReviewUpdatedNotifications1.do?ContextAction=Submit';
		     // submitForm();
		      document.forms[0].action =frm.action;
              document.forms[0].submit();
	}
}


/**
 * This function takes the parent class which identifies a row
 * and hides/displays all the child rows associated with it.
 * @param parentClass is of the form 'parent_blockId'
 */
function displayNotificationHistory(parentClass)
{
	var childClass = parentClass.replace("parent", "child");
	var tableId = "notificationHistoryTable";
	var tableElt = $j("#" + tableId);
	var parentRowElt = $j(tableElt).find("." + parentClass).get(0);
	
	if ($j($j(parentRowElt).find("img").get(0)).attr("src") == "minus_sign.gif") {
		$j($j(parentRowElt).find("img").get(0)).attr("src", "plus_sign.gif");
	}
	else if ($j($j(parentRowElt).find("img").get(0)).attr("src") == "plus_sign.gif") {
		$j($j(parentRowElt).find("img").get(0)).attr("src", "minus_sign.gif");
	}
	
	var childRowsElts = $j(tableElt).find("." + childClass);
		
	for (var i = 0; i < childRowsElts.length; i++) {
		var singleChildRow = $j(childRowsElts[i]);
		if ($j(singleChildRow).css("display") == "none") {
			$j(singleChildRow).removeClass("none");
		}
		else {
			$j(singleChildRow).addClass("none");
		}
	}
}

function displayApprovalQueueFull(uid, oSwitchImage){

	
	var trNodes = notificationApprovalQueueTable.getElementsByTagName("tr");
	
	//switch image
		if(oSwitchImage.src.search(/minus_sign.gif/)==-1){
			// flip the icon to minus
			oSwitchImage.src = "minus_sign.gif";
			oSwitchImage.alt = "Collapse";
			
	
		} else {
			
			// flip the icon to plus
			oSwitchImage.src = "plus_sign.gif";
			oSwitchImage.alt = "Expand";
			
		}
	
	for (var i=0; i < trNodes.length; i++) {
	
		if(trNodes[i].uid == uid) {

			if(trNodes[i].className=="none"){
				trNodes[i].setAttribute("className", "visible");


			}  else if(trNodes[i].className=="visible"){
				trNodes[i].setAttribute("className", "none");

			}	
		}
	}
	
}
//the following code for Queue
function attachIcons() {
    $j("#parent thead tr th a").each(function(i) {
			      if($j(this).html() == 'Submit Date')
						$j(this).parent().append($j("#sdate"));
			      if($j(this).html() == 'Submitted By')
						$j(this).parent().append($j("#inv"));
			      if($j(this).html() == 'Condition')
						$j(this).parent().append($j("#cond"));
			      if($j(this).html() == 'Status')
						$j(this).parent().append($j("#stat"));
			      if($j(this).html() == 'Type')
						$j(this).parent().append($j("#notif"));
			      if($j(this).html() == 'Recipient')
						$j(this).parent().append($j("#recipient"));
			      if($j(this).html() == 'Comments')
						$j(this).parent().append($j("#comment"));
			      if($j(this).html() == 'Patient')
						$j(this).parent().append($j("#patient"));
			});
    $j("#parent").before($j("#whitebar"));
    $j("#parent").before($j("#removeFilters"));
}

function displayTooltips() {
	$j(".sortable a").each(function(i) {

		var headerNm = $j(this).html();
	      if(headerNm == 'Submit Date') {
			_setAttributes(headerNm, $j(this), $j("#INV147"));
	      }
	       else if(headerNm == 'Patient') {
	    	   _setAttributes(headerNm, $j(this), $j("#PATIENT"));
	  }
	      else if(headerNm == 'Submitted By') {
			_setAttributes(headerNm, $j(this), $j("#INV100"));
	      } else if(headerNm == 'Condition') {
			_setAttributes(headerNm, $j(this), $j("#INV169"));
	      } else if(headerNm == 'Status') {
			_setAttributes(headerNm, $j(this), $j("#INV163"));
	      } else if(headerNm == 'Type') {
			_setAttributes(headerNm, $j(this), $j("#NOT118"));
	      }
	      else if(headerNm == 'Recipient') {
				_setAttributes(headerNm, $j(this), $j("#NOT119"));
		      }
	      else if(headerNm == 'Comments') {
	    	  _setAttributes(headerNm, $j(this), $j("#COMMENT"));
		      }
	});
}

function _handlePatient(headerNm, link, colId) {
	var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
	var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';

	var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();
	if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
		if(sortSt != null && sortSt.indexOf("descending") != -1) {
			link.after(htmlDesc);
		} else {
			link.after(htmlAsc);
		}
	}

}


function _setAttributes(headerNm, link, colId) {

	var imgObj = link.parent().find("img");
	var toolTip = "";
	var sortSt = $j("#sortSt") == null ? "" : $j("#sortSt").html();
	var orderCls = "SortAsc.gif";
	var altOrderCls = "Sort Ascending";
	
	var sortOrderCls = "FilterAndSortAsc.gif";
	var altSortOrderCls = "Filter Applied with Sort Ascending";
	
	if(sortSt != null && sortSt.indexOf("descending") != -1) {
		orderCls = "SortDesc.gif";
		altOrderCls = "Sort Descending";
		sortOrderCls = "FilterAndSortDesc.gif";
		altSortOrderCls = "Filter Applied with Sort Descending";
		
	}
	var filterCls = "Filter.gif";
	var altFilterCls = "Filter Applied";
	
  	toolTip = colId.html() == null ? "" : colId.html();
  	if(toolTip.length > 0) {
		link.attr("title", toolTip);
		imgObj.attr("src", filterCls);
		imgObj.attr("alt", altFilterCls);
		if(sortSt != null && sortSt.indexOf(headerNm) != -1 ){
			imgObj.attr("src", sortOrderCls);
			imgObj.attr("alt", altSortOrderCls);
		}
  	} else {
		if(sortSt != null && sortSt.indexOf(headerNm) != -1 ) {
			imgObj.attr("src", orderCls);
			imgObj.attr("alt", altOrderCls);
		}
  	}
}
function printQueue() {
	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
}
function exportQueue() {
	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
}        	  

function cancelFilter(key) {				  	
	key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
	JInvestigationForm.getAnswerArray(key1, function(data) {			  			
		revertOldSelections(key, data);
	});		  	
}
function revertOldSelections(name, value)
{  
	if(value == null) {
		$j("input[@name="+name+"][type='checkbox']").attr('checked', true);
		$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);
		return;
	}

	//step1: clear all selections
	$j("input[@name="+name+"][type='checkbox']").attr('checked', false);
	$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', false);

	//step2: check previous selections from the form
   	for(var i=0; i<value.length; i++) {
		   $j(" INPUT[@value=" + value[i] + "][type='checkbox']").attr('checked', true);
	   }
	//step3: if all are checked, automatically check the 'select all' checkbox
	if(value.length == $j("input[@name="+name+"][type='checkbox']").parent().length)
		$j("input[@name="+name+"][type='checkbox']").parent().parent().find('INPUT.selectAll').attr('checked', true);

}
function selectfilterCriteria()
{	     
	document.forms[0].action ='/nbs/LoadReviewNotifications1.do?method=filterNotificationSubmit&ContextAction=NNDApproval';
	document.forms[0].submit();			
}

function clearFilter()
{

	document.forms[0].action ='/nbs/LoadReviewNotifications1.do?ContextAction=NNDApproval&initLoad=true&method=loadQueue';
	document.forms[0].submit();										
}
function printQueue() {
	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
}
function exportQueue() {
	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
}   
