function printQueue() {
	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
}
function exportQueue() {
	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
}        	  

function cancelFilter(key) {				  	
	key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
	JAssociateToInvestigationsForm.getAnswerArray(key1, function(data) {			  			
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
	document.forms[0].action ='/nbs/LoadInvestigationsToAssoc.do?method=filterInvestigatonSubmit';
	document.forms[0].submit();			
}

function clearFilter()
{
	//document.forms[0].action ='/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true';
	document.forms[0].action ='/nbs/LoadInvestigationsToAssoc.do?method=loadQueue&initLoad=true';
	//document.forms[0].action ='/nbs/ViewObservationLab2.do?ContextAction=AssociateToInvestigations&initLoad=true';
	document.forms[0].submit();										
}


function attachIcons() {	
    $j("#parent thead tr th a").each(function(i) {					      
			      if($j(this).html() == 'Start Date')
						$j(this).parent().append($j("#sdate"));
			      if($j(this).html() == 'Investigator')
						$j(this).parent().append($j("#inv"));
			      if($j(this).html() == 'Jurisdiction')
						$j(this).parent().append($j("#juris"));
			      if($j(this).html() == 'Condition')
						$j(this).parent().append($j("#cond"));
			      if($j(this).html() == 'CaseStatus')
						$j(this).parent().append($j("#stat"));
			      if($j(this).html() == 'Status')
						$j(this).parent().append($j("#invstat"));
			      if($j(this).html() == 'Patient')
						$j(this).parent().append($j("#patient"));			      
			}); 
    $j("#parent").before($j("#whitebar"));
    $j("#parent").before($j("#removeFilters"));
}

function displayTooltips() {		
	$j(".sortable a").each(function(i) {
	
		var headerNm = $j(this).html();
	      if(headerNm == 'Start Date') {
			_setAttributes(headerNm, $j(this), $j("#INV147"));
	      } 
	      else if(headerNm == 'Investigator') {
			_setAttributes(headerNm, $j(this), $j("#INV100"));
	      } else if(headerNm == 'Jurisdiction') {
			_setAttributes(headerNm, $j(this), $j("#INV107"));
	      } else if(headerNm == 'Condition') {
			_setAttributes(headerNm, $j(this), $j("#INV169"));
	      } else if(headerNm == 'CaseStatus') {
			_setAttributes(headerNm, $j(this), $j("#INV163"));
	      } else if(headerNm == 'Status') {
			_setAttributes(headerNm, $j(this), $j("#INV109"));			
	      } else if(headerNm == 'Patient') {
	    	  _setAttributes(headerNm, $j(this), $j("#PATIENT"));
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

function OpenProcessingDecisionPopup(event) {
	var block = getElementByIdOrByName("blockparent");
	block.style.display = "block";	
	var o = new Object();
	o.opener = self;
	//var returnMessage = window.showModalDialog("/nbs/LoadProcessingDecisionPopup.do?method=processingDecisionLoad&event="+event, o, GetDialogFeatures(460, 350, false));
	
	var URL = "/nbs/LoadProcessingDecisionPopup.do?method=processingDecisionLoad&event="+event;
	var modWin = openWindow(URL, o,GetDialogFeatures(460, 350, false, true), block, "");
	return false;
}




