function attachIcons() {
    // remove the hidden property of table search filters Div
    $j("#filterDropDowns").show();

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
			      if($j(this).html() == 'Rejected By')
						$j(this).parent().append($j("#rejBy"));
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
	      else if(headerNm == 'Rejected By') {
				_setAttributes(headerNm, $j(this), $j("#NOT120"));
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
	document.forms[0].action ='/nbs/LoadReviewRejectedNotifications1.do?method=filterRejectedNotificationSubmit';
	document.forms[0].submit();				
}

function clearFilter()
{

	document.forms[0].action ='/nbs/LoadReviewRejectedNotifications1.do?ContextAction=NNDRejectedNotifications&initLoad=true&method=loadQueue';
	document.forms[0].submit();								
}
function printQueue() {
	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
}
function exportQueue() {
	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
}   