function printQueue() {
	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
}
function exportQueue() {
	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
}        	  

function cancelFilter(key) {				  	
	key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
	JProgramAreaForm.getAnswerArray(key1, function(data) {			  			
		revertOldSelections(key, data);
	});		  	
} 

function getInvestigatorCalDate(obj, anchor)
{
    var cal = new CalendarPopup();
    
    cal.showYearNavigation(); 
    var newObj = getElementByIdOrByName(obj);
    cal.select(newObj,anchor,'MM/dd/yyyy');
}
	
	function assignInvestigator(){
		$j("div#msgBlock").hide(); 
			resetDropdowns();
			if(isAnyCheckBoxSelected(getElementByIdOrByName('selectAllValue'))){
				if(validateHIVorSTD()){
					validateSelection();
					disabledDateAssignedToInvestigation();
				}
			}
			
	}
	
	
	function validateHIVorSTD(){
		
		var checkboxes = document.getElementsByName('selectCheckBox');
		var programAreaHIVOrSTD=false;
		
		var programAreaOther=false;
		var programArea="";
		var table = document.getElementsByClassName("dtTable")[0];
		var indexDocumentType = 5;
		var indexLocalId = 8;
		var indexProgramArea = 9;
	  for (var i = 1; i <= checkboxes.length; i++) {
				var row = table.getElementsByTagName("tr")[i];
	        	if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){
	           		   documentType = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].innerHTML;
					  var id = row.getElementsByTagName("td")[indexLocalId].innerHTML;
				var link = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].getAttribute("onclick");

						var publicHealthCaseUIDIndex = link.indexOf("publicHealthCaseUID");
						var invesFormCdIndex = link.indexOf("investigationFormCode");
						//var publicHealthCaseUID=link.substring(publicHealthCaseUIDIndex+20,link.length-37);
						var publicHealthCaseUID=link.substring(publicHealthCaseUIDIndex+20,invesFormCdIndex-1);
						var invFormcd = link.substring(invesFormCdIndex+22,link.length-2);
						if("PG_STD_Investigation" == invFormcd || "PG_HIV_Investigation"==invFormcd){
						   programAreaHIVOrSTD=true; 
					   }


					}
	    	}


	  
		
	     if(programAreaHIVOrSTD){
			$j("div#errorBlockSTD").show();
				document.getElementById("errorBlockSTD").textContent="Investigations in STD/HIV Program Area cannot be assigned an investigator in bulk. Please de-select the STD/HIV investigations and try again.";
				showHideMessage();
				return false;
				
	    	
	    }else{
			return true;
		}
	  }
	  function resetDropdowns(){
	    	if(document.getElementById("processingDecisionStyle")!=null && document.getElementById("processingDecisionStyle")!=undefined)
	    		document.getElementById("processingDecisionStyle").selectedIndex=-1;
	    	if(document.getElementById("processingDecisionStyleSTD")!=null && document.getElementById("processingDecisionStyleSTD")!=undefined)
	    		document.getElementById("processingDecisionStyleSTD").selectedIndex=-1;
	    }
		
		 function disableRemoveAllFilters(){
    	$j(".removefilerLink").removeAttr("href");
    }
		
		
function validateSelection(){
		disableRemoveAllFilters();
       $j("body").find(":input").attr("disabled","disabled");
       var imgstyles = $j('.multiSelect');
    	 for (var li = 0; li < imgstyles.length; li++) {
  			 var imgstyle = imgstyles.attr("style");
  				$j(imgstyles[li]).attr("style", imgstyle+";pointer-events:none");
  		 }
  		var hrs = $j('.reviewedLink');
  		 for (var li = 0; li < hrs.length; li++) {
			 var hr = hrs.attr("style");
				$j(hrs[li]).attr("style", "pointer-events:none");
				$j(hrs[li]).attr("disabled", "disabled");
		 }
		 $j('.reviewedLink').find('.hyperLink').attr("disabled","disabled");
		$j('.removefilerLink').attr("disabled","disabled");
       $j("div#assignInvestigator").show();
       $j("div#assignInvestigator").find(":input").attr("disabled","");            
    	  $j("div#errorBlockSTD").hide(); 
    
       var hrefs = $j("table.dtTable").find("a");
       for (var li = 0; li < hrefs.length; li++) {
           var href = $j(hrefs[li]).attr("href");
           var hiddenSpan = "<span style=\"display:none;\">" + href + "</span>";
           $j(hrefs[li]).append(hiddenSpan);
           $j(hrefs[li]).removeAttr("href");
           $j(hrefs[li]).attr("disabled", "disabled");
			var style = $j(hrefs[li]).attr("style");
			$j(hrefs[li]).attr("style", style+";pointer-events:none");//Firefox
           $j(hrefs[li]).css("cursor", "none");        
       }
        hrefs = $j("span.pagelinks").find("a");
       
               for (var li = 0; li < hrefs.length; li++) {
                   var href = $j(hrefs[li]).attr("href");
                   var hiddenSpan = "<span style=\"display:none;\">" + href + "</span>";
                   $j(hrefs[li]).append(hiddenSpan);
                   $j(hrefs[li]).removeAttr("href");
                   $j(hrefs[li]).attr("disabled", "disabled");
			var style = $j(hrefs[li]).attr("style");
			$j(hrefs[li]).attr("style", style+";pointer-events:none");//Firefox
                   $j(hrefs[li]).css("cursor", "none");        
       }
          $j("div#error1").hide();
      $j("div#error2").hide();
      $j("div#error3").hide();
          $j("div#success1").hide();
		  window.scrollTo(0,0);
		   
	}	
	
	
	 function isAnyCheckBoxSelected(ele) {
		var checkboxes = document.getElementsByTagName('input');
		var selected=false;
			 for (var i = 0; i < checkboxes.length; i++) {
				if((checkboxes[i].name == "selectCheckBox") && (checkboxes[i].checked)==true){
					 selected = true;
				}
			}
		return selected;
	 }
	
	
		

	function cancelAttachment() {
  
	// display elts
  $j("td#fileNameLabel").css("color", "black");


 
   $j("td#reasonLabel").css("color", "black");
   $j("td#processingDecisionLabel").css("color", "black");
  // $j("td#chooseFileLabelP").css("color", "black");
  // $j("td#chooseFileLabelJ").css("color", "black");
   $j("div#errorBlock").hide();
  
  // $j("div#msgBlock").hide();
   $j("div#errorBlockP").hide();

 //  $j("div#msgBlockP").hide();
   $j("div#errorBlockSTD").hide();
  // $j("div#msgBlockJ").hide();
   $j("div#error1").hide();
     $j("div#error2").hide();
       $j("div#error3").hide();
         $j("div#success1").hide();
  


  cancelAttachmentBlock();
  
  //setCheckBoxes();
	   enableRemoveAllFilters();
clearProvider('INV207');
	}
	
	function isValidDate(s) {
       var bits = s.split('/');
       var d = new Date(bits[2], bits[0] - 1, bits[1]);
       return d && (d.getMonth() + 1) == bits[0] && d.getDate() == Number(bits[1]);
    }
	
		function assignInvestigatorSubmit(){			
			//Validations (error messages)
			var errors = new Array();
              var index = 0;
              var hasErrors = false;
			  
			  var investigator = document.getElementById("INV207").innerText;
              var dateAssigned = document.programAreaForm.dateAssigned.value;
			  document.programAreaForm.investigatorSelected.value=investigator;
			  
			 
              if (jQuery.trim(investigator) == ""  ) {
                  errors[index++] = "Select an Investigator.";
                  hasErrors = true;
                  $j("td#reasonLabel").css("color", "#CC0000");
              }else if(jQuery.trim(dateAssigned) == ""){
				errors[index++] = "Select the date assigned to investigator.";
                  hasErrors = true;
                  $j("td#reasonLabel").css("color", "#CC0000");
	
			} else if(jQuery.trim(dateAssigned) != ""){
				
					  
	      			  var dateMMDDYYYRegex = "^[0-9]{2}/[0-9]{2}/[0-9]{4}$";
	        		 if(dateAssigned.match(dateMMDDYYYRegex) && isValidDate(dateAssigned)){
	           			// alert('is a valid date: '+daterequested);
	        		}else{
	            		errors[index++] = "Date Assigned to Investigation: must be in the format of mm/dd/yyyy.";
                 		hasErrors = true;
                		  $j("td#reasonLabel").css("color", "#CC0000");
	        		}
			}else {
                 
                  $j("td#reasonLabel").css("color", "black");
              }        
             
              if (hasErrors) {
                  displayErrors("errorBlockP", errors);

                  return false;
              }
      	    
              else {
                 //blockUIDuringFormSubmissionNoGraphic();
               $j("div#errorBlockP").hide();
           
                /* if ($j("div#msgBlockP").css("display") == "none") {
                     $j("div#msgBlockP").css("display", "block");
          
                 }*/
			  
	           	  assignInvestigatorBulk(1);
                   return true;
                 }
             
		}

		
		function assignInvestigatorBulk(i){
			setSelectedDocumentIds(i);
			document.forms[0].action ="/nbs/AssignInvestigatorBulk.do";
            //document.forms[0].action ="#";
			document.forms[0].submit();  
			
		}
		
	    function setSelectedDocumentIds(index){

	        var sb = "";
		    var documentIdsPublicCaseUIDAndFormCodeIDs = getElementByIdOrByName("chkboxIdsInfo");
	    	var checkboxes = document.getElementsByName('selectCheckBox');
			var documentTypeMorbOrCase=false;
			var documentType="";
			var table = document.getElementsByClassName("dtTable")[0];
			var indexDocumentType = 5;
			var indexLocalId = 8;
			
			var publicHealthCaseUIDs="";
		    for (var i = 1; i <= checkboxes.length; i++) {
				var row = table.getElementsByTagName("tr")[i];
	        	if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){
	           		   documentType = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].innerHTML;
					   var id = row.getElementsByTagName("td")[indexLocalId].innerHTML;
					   var link = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].getAttribute("onclick");

						var publicHealthCaseUIDIndex = link.indexOf("publicHealthCaseUID");
						var invesFormCdIndex = link.indexOf("investigationFormCode");
						//var publicHealthCaseUID=link.substring(publicHealthCaseUIDIndex+20,link.length-37);
						var publicHealthCaseUID=link.substring(publicHealthCaseUIDIndex+20,invesFormCdIndex-1);
						var invFormcd = link.substring(invesFormCdIndex+22,link.length-2);
						if(publicHealthCaseUIDs==""){
							publicHealthCaseUIDs=publicHealthCaseUID+"@"+invFormcd;
						}else{
							publicHealthCaseUIDs=publicHealthCaseUIDs+"|"+publicHealthCaseUID+"@"+invFormcd;;
						}

					}
	    	}
	        
	         documentIdsPublicCaseUIDAndFormCodeIDs.value=publicHealthCaseUIDs;
	    }
	    
	    
	
	function enableRemoveAllFilters(){
    	$j(".removefilerLink").attr("href", "javascript:clearFilter()");
    }
	
	/**
	* cancelAttachmentBlock: cancel the blue section
	*/
	
	function cancelAttachmentBlock() {
       // enable all input elts
     
       $j("body").find(":input").attr("disabled","");
 
       // hide attachments block
   $j("div#assignInvestigator").hide();
       
       $j('.multiSelect').attr("style", "pointer-events: all;");
       $j('.reviewedLink').attr("style", "pointer-events: all;");
       $j('.reviewedLink').attr("disabled", "");
       
       // enable links
    
      var hrefs = $j("table.dtTable").find("a");
  
     for (var li = 0; li < hrefs.length; li++) {
           var href = jQuery.trim($j($j(hrefs[li]).find("span").get(0)).html());
           if (href != "") {
               href = href.replace(/&amp;/g, "&");
               $j(hrefs[li]).attr("href", href);    
           }
           $j(hrefs[li]).find("span").remove();
           $j(hrefs[li]).attr("disabled", "");
			$j(hrefs[li]).attr("style", "pointer-events: all;");//Firefox
           $j(hrefs[li]).css("cursor", "hand");
       
      
    
       }   
         hrefs = $j("span.pagelinks").find("a");
               for (var li = 0; li < hrefs.length; li++) {
                   var href = jQuery.trim($j($j(hrefs[li]).find("span").get(0)).html());
                   if (href != "") {
                       href = href.replace(/&amp;/g, "&");
                       $j(hrefs[li]).attr("href", href);    
                   }
                   $j(hrefs[li]).find("span").remove();
                   $j(hrefs[li]).attr("disabled", "");
					$j(hrefs[li]).attr("style", "pointer-events: all;");//Firefox
                   $j(hrefs[li]).css("cursor", "hand");
     
       }  
       hrefs = $j('.reviewedLink');
       for (var li = 0; li < hrefs.length; li++) {
       	
       	$j(hrefs[li]).attr("style", "pointer-events: all;");//Firefox
           $j(hrefs[li]).attr("disabled", "");
           $j(hrefs[li]).css("cursor", "hand");
}  
       $j('.reviewedLink').find('.hyperLink').attr("disabled","");  
       $j('.removefilerLink').attr("disabled","");
     
       
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
	document.forms[0].action ='/nbs/LoadMyProgramAreaInvestigations1.do?method=filterInvestigatonSubmit';
	document.forms[0].submit();			
}

function clearFilter()
{

	document.forms[0].action ='/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true';
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
			      if($j(this).html() == 'Notification')
						$j(this).parent().append($j("#notif"));
			      if($j(this).html() == 'Patient')
						$j(this).parent().append($j("#patient"));			 
				 if($j(this).html() == 'Investigation ID')
						$j(this).parent().append($j("#investigationid"));			      
     
			}); 
    $j("#parent").before($j("#whitebar"));
    $j("#parent").before($j("#removeFilters"));
}

		function uncheckHeader(){
			if(getElementByIdOrByName('selectAllValue')!=null){
				getElementByIdOrByName('selectAllValue').checked = false;
			}
			
			var checkboxes = document.getElementsByName("selectCheckBox");
			
			for(var i=0; i<checkboxes.length;i++){
				checkboxes[i].checked=false;
			}
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
	      } else if(headerNm == 'Notification') {
			_setAttributes(headerNm, $j(this), $j("#NOT118"));			
	      } else if(headerNm == 'Patient') {
	    	  _setAttributes(headerNm, $j(this), $j("#PATIENT"));
	      }	 else if(headerNm == 'Investigation ID') {
		     _setAttributes(headerNm, $j(this), $j("#INVESTIGATIONID"));
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


function showInvestigatorCalendarEnterKey(id, id2, e)
{
	if(document.activeElement!=null && document.activeElement.src.indexOf("calendar")!=-1){
			if (e!=null && e.which=='13'){
				getInvestigatorCalDate(id,id2);
				return false;
			}
		
	}
}
	
function getInvestigatorCalDate(obj, anchor)
{
    var cal = new CalendarPopup();
    
    
    cal.showYearNavigation(); // << and >> arrows to navigate year & month
    var newObj = getElementByIdOrByName(obj);
    cal.select(newObj,anchor,'MM/dd/yyyy');
}