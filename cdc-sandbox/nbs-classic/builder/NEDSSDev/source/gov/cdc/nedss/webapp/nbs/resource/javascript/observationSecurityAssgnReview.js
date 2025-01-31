function printQueue() {
    window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
}
function exportQueue() {
    window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
}             

function cancelFilter(key) {                    
    key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));                     
    JObservationSecurityAssgnReviewForm.getAnswerArray(key1, function(data) {                        
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
    document.forms[0].action ='/nbs/LoadObsNeedingAssignment1.do?method=filterObservationsSubmit';
    document.forms[0].submit();         
}

function clearFilter()
{
    document.forms[0].action ='/nbs/LoadObsNeedingAssignment1.do?ContextAction=ObsAssign&initLoad=true&method=loadQueue';
    document.forms[0].submit();                                     
}







/**
 * resetDropdowns: unselect the selected option from any of the Program Area/Jurisdiction dropdowns
 */

function resetDropdowns(){
	if(document.getElementById("programAreaStyleP")!=null && document.getElementById("programAreaStyleP")!=undefined)
		document.getElementById("programAreaStyleP").selectedIndex=-1;
	if(document.getElementById("jurisdictionStyleJ")!=null && document.getElementById("jurisdictionStyleJ")!=undefined)
		document.getElementById("jurisdictionStyleJ").selectedIndex=-1;
	if(document.getElementById("jurisdictionStyle")!=null && document.getElementById("jurisdictionStyle")!=undefined)
		document.getElementById("jurisdictionStyle").selectedIndex=-1;
	if(document.getElementById("programAreaStyle")!=null && document.getElementById("programAreaStyle")!=undefined)
		document.getElementById("programAreaStyle").selectedIndex=1;
}

/**
 * resetMessageBlock: the success message is deleted and the blue section is hidden
 */

function resetMessageBlock(){
	document.getElementById("msgBlock").hide();
	document.getElementById("msgBlock").textContent="";
}



/**
* setSelectedDocumentIds(): set the lab/morb/case report ids into a hidden element for being read from Java
*/
	
function setSelectedDocumentIds(index){
    var sb = "";
	var documentIdsLabs = getElementByIdOrByName("chkboxIdsLabs"+index);
	var documentIdsMorbs = getElementByIdOrByName("chkboxIdsMorbs"+index);
	var documentIdsCases = getElementByIdOrByName("chkboxIdsCases"+index);
	
	var checkboxes = document.getElementsByName('selectCheckBox');
	var documentTypeMorbOrCase=false;
	var documentType="";
	var table = document.getElementsByClassName("dtTable")[0];
	var indexDocumentType = 1;
	var indexLocalId = 8;
	
	var sbLabs="";
	var sbMorbs="";
	var sbCases="";
	
	for (var i = 1; i < checkboxes.length+1; i++) {
		var row = table.getElementsByTagName("tr")[i];
		if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){
	       
		   documentType = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].innerHTML;
	
		   var id = row.getElementsByTagName("td")[indexLocalId].innerHTML;
		   var link = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].getAttribute("onclick")
		   var indexObservationUid=link.indexOf("observationUID");
		   var observationUid=link.substring(indexObservationUid+15,link.length-2);
		   
		   if(documentType.indexOf("Morbidity Report")!=-1)
			   sbMorbs = sbMorbs + observationUid + "|";
		   else 
			   if(documentType.indexOf("Case Report")!=-1)
			  	 sbCases = sbCases + observationUid + "|";
			   else //lab report uid
				   sbLabs = sbLabs + observationUid + "|";
	    }
	}
	
	documentIdsLabs.value=sbLabs;
	documentIdsMorbs.value=sbMorbs;
	documentIdsCases.value=sbCases;
}


/**
* validateSelection: returns true if:
* - user clicks on Transfer Program area and there's no Morb/Case report selected
* - user clicks on Transfer Ownership and there's no Morb/Case report selected
* - user clicks on Transfer Jurisdiction
*/
	

function validateSelection(){
	
	var checkboxes = document.getElementsByName('selectCheckBox');
	var documentTypeMorbOrCase=false;
	var documentType="";
	var table = document.getElementsByClassName("dtTable")[0];
	var indexDocumentType = 1;
	var indexLocalId = 8;
	var documentIds= new Array();
	
    for (var i = 1; i <= checkboxes.length; i++) {
		var row = table.getElementsByTagName("tr")[i];
    	if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){//todo getCorrectAttribute
           
		   documentType = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].innerHTML;
		
		   if(documentType.indexOf("Morbidity Report")!=-1 || documentType.indexOf("Case Report")!=-1){
			   documentTypeMorbOrCase=true;
			   
			   var id = row.getElementsByTagName("td")[indexLocalId].innerHTML;
			   
			   if(id.indexOf("<font color")!=-1){
				   var index = id.indexOf(">");
				   id=id.substring(index+1, index+16);
			   }
			   
			   documentIds.push(id);
		   }
    	}
	}
	
    if(documentTypeMorbOrCase){
    	var errorMessage="The following document(s) can only have Program Area assigned individually from the document: "+documentIds.toString();
		alert(errorMessage);
	}
	return !documentTypeMorbOrCase;
}

function validateSelectionProgramArea(){
	
	var checkboxes = document.getElementsByName('selectCheckBox');
	var documentTypeMorbOrCase=false;
	var documentType="";
	var table = document.getElementsByClassName("dtTable")[0];
	var indexDocumentType = 1;
	var indexLocalId = 8;
	var indexProgramArea = 7;
	//var documentIds= new Array();
	
    for (var i = 1; i <= checkboxes.length; i++) {
		var row = table.getElementsByTagName("tr")[i];
    	if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){//todo getCorrectAttribute
           
		   documentType = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].innerHTML;
		
		   if(documentType.indexOf("Morbidity Report")!=-1 || documentType.indexOf("Case Report")!=-1){
			   programArea = row.getElementsByTagName("td")[indexProgramArea].innerHTML;
			   if(programArea!="")
				   documentTypeMorbOrCase=true;
			   /*
			   var id = row.getElementsByTagName("td")[indexLocalId].innerHTML;
			   
			   if(id.indexOf("<font color")!=-1){
				   var index = id.indexOf(">");
				   id=id.substring(index+1, index+16);
			   }
			   
			   documentIds.push(id);*/
		   }
    	}
	}
	
    if(documentTypeMorbOrCase){
    	var errorMessage="The selected Case Reports or Morbidity Reports cannot have program area changed. Please update your selection and try again.";//+documentIds.toString();
		alert(errorMessage);
	}
	return !documentTypeMorbOrCase;
}

/**
* transferProgramArea: called after clicking on submit button for validating all the required fields has been entered. If it's validated, the form is submitted.
*/


function transferProgramArea(){
	
	//Validations (error messages)
	var errors = new Array();
	var index = 0;
	var hasErrors = false;
	var progArea = document.observationSecurityReviewForm.programAreaStyleP.value;

	if (jQuery.trim(progArea) == "") {
      errors[index++] = "Program Area is a required field.";
      hasErrors = true;
   
      $j("td#chooseFileLabelP").css("color", "#CC0000");
	}else {
      $j("td#chooseFileLabelP").css("color", "black");
	}        
 
	if (hasErrors) {
      displayErrors("errorBlockP", errors);

      return false;
	}else {
     //blockUIDuringFormSubmissionNoGraphic();
		$j("div#errorBlockP").hide();
        /* if ($j("div#msgBlockP").css("display") == "none") {
             $j("div#msgBlockP").css("display", "block");
  
        }*/
       	transferOwnershipBulk(1);
        return true;
    }
}

/**
* transferJurisdiction: called after clicking on submit button for validating all the required fields has been entered. If it's validated, the form is submitted.
*/

function transferJurisdiction(){

	//Validations (error messages)
	var errors = new Array();
	var index = 0;
	var hasErrors = false;
  
	if (jQuery.trim(document.observationSecurityReviewForm.jurisdictionStyleJ.value) == "") {
      errors[index++] = "Jurisdiction is a required field.";
      hasErrors = true;
      $j("td#chooseFileLabelJ").css("color", "#CC0000");
	}else {
      $j("td#chooseFileLabelJ").css("color", "black");
	} 
 
	if (hasErrors) {
      displayErrors("errorBlockJ", errors);
      
      return false;
	}else {
     //blockUIDuringFormSubmissionNoGraphic();
      $j("div#errorBlockJ").hide();
      /*  if ($j("div#msgBlockJ").css("display") == "none") {
      $j("div#msgBlockJ").css("display", "block");
 	  }*/
      transferOwnershipBulk(2);
      return true;
     }
}

/**
 * transferOwnerShip: called after clicking on submit button for validating all the required fields has been entered. If it's validated, the form is submitted.
 */

function transferOwnerShip(){
	
	//Validations (error messages)
	var errors = new Array();
	var index = 0;
	var hasErrors = false;
   
	if (jQuery.trim(document.observationSecurityReviewForm.programAreaStyle.value) == "") {
		errors[index++] = "Program Area is a required field.";
		hasErrors = true;
		$j("td#chooseFileLabel1").css("color", "#CC0000");
	}else {
      $j("td#chooseFileLabel1").css("color", "black");
    }        
   
    if (jQuery.trim(document.observationSecurityReviewForm.jurisdictionStyle.value) == "") {
	      errors[index++] = "Jurisdiction is a required field.";
	      hasErrors = true;
	      $j("td#chooseFileLabel2").css("color", "#CC0000");
    }else {
    	  $j("td#chooseFileLabel2").css("color", "black");
    } 
 
    if (hasErrors) {
	      displayErrors("errorBlock", errors);
	      
	      return true;
    }else {
    	 //blockUIDuringFormSubmissionNoGraphic();
         $j("div#errorBlock").hide();             
         /*    if ($j("div#msgBlock").css("display") == "none") {
         $j("div#msgBlock").css("display", "block");
   		}*/
		 transferOwnershipBulk(3);
		 return true;
    }
}

/**
 * transferOwnershipBulk: set the document uid selected into the corresponding hidden elements and submit the form.
 * parameter i:
 * - 1 is called from Program Area Section and it gets the value from the hidden elements with suffix 1
 * - 2 is called from Jurisdiction Section and it gets the value from the hidden elements with suffix 2
 * - 3 is called from Transfer Ownership Section and it gets the value from the hidden elements with suffix 3
 */

function transferOwnershipBulk(i){

	setSelectedDocumentIds(i);
	document.forms[0].action ="/nbs/TransferOwnershipBulk1.do?method=transferBulk";
    document.forms[0].submit();
    
}

/**
 * transferProgramAreaSection(): method called from Transfer Program area link to block the page and show the Program area section
 */

function transferProgramAreaSection() {					

	resetMessageBlock();
	//if(validateSelection()){
		if(validateSelectionProgramArea()){
		
		resetDropdowns();
		//getCheckBoxes();
		if(isAnyCheckBoxSelected(getElementByIdOrByName('selectAllValue'))){
			//showAddAttachmentBlock();	
			showAddProgramAreaBlock();
			prepopulateProgramArea("programAreaStyleP_textbox", "programAreaStyleP");				
		}
	}

}

/**
 * transferJurisdictionSection(): method called from Transfer Jurisdiction link to show the Jurisdiction section
 */

function transferJurisdictionSection(){
	
	resetMessageBlock();			
	if(isAnyCheckBoxSelected(getElementByIdOrByName('selectAllValue'))){
		resetDropdowns();
		showAddJurisdictionBlock();
		prepopulateJurisdiction("jurisdictionStyleJ_textbox", "jurisdictionStyleJ");
	}
}

/**
 * transferOwnershipSection: method called from Transfer Ownership link to show the Transfer Ownership section
 */

function transferOwnershipSection(){
	resetMessageBlock();
	
	//if(validateSelection()){
		if(validateSelectionProgramArea()){
		if(isAnyCheckBoxSelected(getElementByIdOrByName('selectAllValue'))){
			resetDropdowns();
			showAddAttachmentBlock();
			prepopulateJurisdiction("jurisdictionStyle_textbox", "jurisdictionStyle");
			prepopulateProgramArea("programAreaStyle_textbox", "programAreaStyle");	
		}			
	}
}

/**
 * getCheckBoxes:
 */
/*
function getCheckBoxes(){
	var checkboxes = document.getElementsByName('selectCheckBox');
	var table = document.getElementsByClassName("dtTable")[0];
	
	for (var i = 0; i < checkboxes.length; i++) {
        var row = table.getElementsByTagName("tr")[i];
        if(row!=null && row!='undefined'){
            if(getCorrectAttribute(row.getElementsByTagName("input")[0],"checked",row.getElementsByTagName("input")[0].checked)==true)
                    array2.push(true);
                else
                    array2.push(false);
        }
    }		
}*/

/**
 * setCheckBoxes:
 */
/*
function setCheckBoxes(){

    var checkboxes = document.getElementsByName('selectCheckBox');
    var table = document.getElementsByClassName("dtTable")[0];
    
	for (var i = 0; i < array2.length; i++) {
        var row = table.getElementsByTagName("tr")[i];
        if(row!=null && row!='undefined'){
            if(array2[i]==true)
                row.getElementsByTagName("input")[0].checked=true;
            else
                row.getElementsByTagName("input")[0].checked=false;
            }
    }
}*/

/**
 * disableAllLinks: disable all links on the table until the blue section is closed
 */

function disableAllLinks(){
	disableRemoveAllFilters();
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

	// disable links  
  
 
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

/**
 * showAddProgramAreaBlock: show the Program Area blue section
 */
function showAddProgramAreaBlock(){
		
		//disableRemoveAllFilters();
		disableAllLinks();

		$j("body").find(":input").attr("disabled","disabled");
   
    /* var imgstyles = $j('.multiSelect');
    
    
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
 */    
		$j("div#addProgramAreaBlock").show();
		$j("div#addAttachmentBlock").hide();
		$j("div#addJurisdictionBlock").hide();
		$j("div#addProgramAreaBlock").find(":input").attr("disabled","");            
   
    /* // disable links  
	  
 
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
	   
 */
}


/**
 * showAddJurisdictionBlock: show the Jurisdiction blue section
 */

function showAddJurisdictionBlock(){
	
	disableAllLinks();
	//disableRemoveAllFilters();
	$j("body").find(":input").attr("disabled","disabled");

 /* var imgstyles = $j('.multiSelect');
    
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
 */
	$j("div#addJurisdictionBlock").show();
	$j("div#addAttachmentBlock").hide();
	$j("div#addProgramAreaBlock").hide();
	$j("div#addJurisdictionBlock").find(":input").attr("disabled","");            

/* // disable links  
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
	 */   
}

/**
 * showAddAttachmentBlock(): show the Transfer ownership section
 */

function showAddAttachmentBlock(){
	disableAllLinks();
	//disableRemoveAllFilters();
	$j("body").find(":input").attr("disabled","disabled");
 

	$j("div#addAttachmentBlock").show();
	$j("div#addProgramAreaBlock").hide();
	$j("div#addJurisdictionBlock").hide();
	$j("div#addAttachmentBlock").find(":input").attr("disabled","");            
/* 	
	// disable links  
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
	   $j("div#error1").hide();
   $j("div#error2").hide();
   $j("div#error3").hide();
	   $j("div#success1").hide();
	   window.scrollTo(0,0);
 */	   
}

/**
 * cancelAttachment(): hide the blue section
 */
		
function cancelAttachment() {
       
	// display elts
	$j("td#fileNameLabel").css("color", "black");
	$j("td#chooseFileLabel1").css("color", "black");
	$j("td#chooseFileLabel2").css("color", "black");
	$j("td#chooseFileLabelP").css("color", "black");
	$j("td#chooseFileLabelJ").css("color", "black");
	$j("div#errorBlock").hide();
	//$j("div#msgBlock").hide();
	$j("div#errorBlockP").hide();
	// $j("div#msgBlockP").hide();
	$j("div#errorBlockJ").hide();
	// $j("div#msgBlockJ").hide();
	$j("div#error1").hide();
	$j("div#error2").hide();
	$j("div#error3").hide();
	$j("div#success1").hide();

	cancelAttachmentBlock();
	enableRemoveAllFilters();
}

/**
* cancelAttachmentBlock: cancel the blue section
*/

function cancelAttachmentBlock() {
    // enable all input elts
  
    $j("body").find(":input").attr("disabled","");
  
    // hide attachments block

    $j("div#addAttachmentBlock").hide();
	$j("div#addProgramAreaBlock").hide();
	$j("div#addJurisdictionBlock").hide();
   
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

/**
 * uncheckHeader(): uncheck all the checkboxes
 */

function uncheckHeader(){
	if(getElementByIdOrByName('selectAllValue')!=null){
		getElementByIdOrByName('selectAllValue').checked = false;
		checkAll(getElementByIdOrByName('selectAllValue'));
	}
}

/**
 * checkAll: select all the checkboxes
 */

function checkAll(ele) {
     var checkboxes = document.getElementsByTagName('input');
     if (getCorrectAttribute(ele,  "checked", ele.checked)) {
         for (var i = 0; i < checkboxes.length; i++) {
        	if(checkboxes[i].name == "selectCheckBox" && !checkboxes[i].disabled){
                 checkboxes[i].checked = true;
        	}
    	}
   	} else {
         for (var i = 0; i < checkboxes.length; i++) {

        

           if(checkboxes[i].name == "selectCheckBox"){
                 checkboxes[i].checked = false;
             }
        }
   	}
 }
 
/**
* isAnyCheckBoxSelected(): returns true is there's any checkbox selected 
*/

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

/**
* prepopulateJurisdiction(): prepopulates the jurisdiction if all the selected documents have the same jurisdiction
*/

function prepopulateJurisdiction(inputName, selectId){
	
	var checkboxes = document.getElementsByName('selectCheckBox');
	var jurisdiction="";
	var previousJurisdiction="";
	var table = document.getElementsByClassName("dtTable")[0];
	var indexJurisdiction = 6;
	var sameJurisdiction=true;

         for (var i = 1; i <= checkboxes.length && sameJurisdiction; i++) {
			var row = table.getElementsByTagName("tr")[i];
        	if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){//todo getCorrectAttribute
               
			   jurisdiction = row.getElementsByTagName("td")[indexJurisdiction].innerHTML;
			
				if(previousJurisdiction=="")
					previousJurisdiction = jurisdiction;
				else
					if(previousJurisdiction!=jurisdiction)
						sameJurisdiction=false;

			   
        	}
    	}
	
	if(sameJurisdiction){
		document.getElementsByName(inputName)[0].value = previousJurisdiction;

		var options = document.getElementById(selectId).options;
		var length=options.length;
		
		for(var i=0; i<length; i++){
			if(options[i].text==previousJurisdiction)
				document.getElementById(selectId).selectedIndex=i;
		
		}
	}

}


/**
* prepopulateProgramArea(): prepopulates the program area if all the selected documents have the same program area
*/

function prepopulateProgramArea(inputName, selectId){
	
	
	var checkboxes = document.getElementsByName('selectCheckBox');
	var programArea="";
	var previousProgramArea="";
	var table = document.getElementsByClassName("dtTable")[0];
	var indexProgramArea = 7;
	var sameProgramArea=true;

         for (var i = 1; i <= checkboxes.length && sameProgramArea; i++) {
			var row = table.getElementsByTagName("tr")[i];
        	if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){//todo getCorrectAttribute
               
			   programArea = row.getElementsByTagName("td")[indexProgramArea].innerHTML;
			
				if(previousProgramArea=="")
					previousProgramArea = programArea;
				else
					if(previousProgramArea!=programArea)
						sameProgramArea=false;

			   
        	}
    	}
	
	if(sameProgramArea){
		document.getElementsByName(inputName)[0].value = previousProgramArea;

		var options = document.getElementById(selectId).options;
		var length=options.length;
		
		for(var i=0; i<length; i++){
			if(options[i].text==previousProgramArea)
				document.getElementById(selectId).selectedIndex=i;
		
		}
	}

}

/**
 * showHideMessage: 
 */

function showHideMessage(){
	if (jQuery.trim(document.getElementById("msgBlock").textContent) == "") 
		document.getElementById("msgBlock").hide();
	else
		document.getElementById("msgBlock").show();
}

