function printQueue() {
	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
}
function exportQueue() {
	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
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

function cancelFilter(key) {				  	
	key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
	JObservationReviewForm.getAnswerArray(key1, function(data) {			  			
		revertOldSelections(key, data);
	});		  	
}


/**
	 * validateSelection(): show the alert message when HIV and/or STD program area selected
	 */
function validateSelection(){
		
		var checkboxes = document.getElementsByName('selectCheckBox');
		var programAreaHIVOrSTD=false;
		
		var programAreaOther=false;
		var programArea="";
		var table = document.getElementsByClassName("dtTable")[0];
		var indexDocumentType = 1;
		var indexLocalId = 8;
		var indexProgramArea = 9;
		//var documentIds= new Array();
		
	
	    for (var i = 1; i <= checkboxes.length; i++) {
			var row = table.getElementsByTagName("tr")[i];
	    	if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){//todo getCorrectAttribute
	           
	    		programArea = row.getElementsByTagName("td")[indexProgramArea].innerHTML;
			
			
			   if(programArea == "true"){
				   programAreaHIVOrSTD=true; 
			   }
			 
			   else
				   programAreaOther=true; 
	    	}
		}
		
	     if(programAreaHIVOrSTD){
	    	if(!programAreaOther)
	    		showMarkAsReviewedBlockSTD();
	    	else{
	    		
	    		var errorMessage="Documents in the STD/HIV Program Area must be processed in bulk separately. Please de-select the documents and try again.";//+documentIds.toString();
	    		alert(errorMessage);
	    	}
	    }
	    else 
	    	showMarkAsReviewedBlock();		
	     }

/**
* showMarkAsReviewedBlock(): show the mark as review blue section
*/

function showMarkAsReviewedBlock(){
		
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
       $j("div#addMarkAsReviewedBlock").show();
      // $j("div#addAttachmentBlock").hide();
     //  $j("div#addJurisdictionBlock").hide();
       $j("div#addMarkAsReviewedBlock").find(":input").attr("disabled","");            
      
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
function showMarkAsReviewedBlockSTD(){
		
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
    $j("div#addMarkAsReviewedBlockSTD").show();
   // $j("div#addAttachmentBlock").hide();
     //  $j("div#addJurisdictionBlock").hide();
    $j("div#addMarkAsReviewedBlockSTD").find(":input").attr("disabled","");            
   
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
	* cancelAttachment(): hide the blue section
	*/
	
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
	}
	
	/**
	* cancelAttachmentBlock: cancel the blue section
	*/
	
	function cancelAttachmentBlock() {
       // enable all input elts
     
       $j("body").find(":input").attr("disabled","");
 
       // hide attachments block
   
    	 //$j("div#addAttachmentBlock").hide();
       $j("div#addMarkAsReviewedBlock").hide();
       $j("div#addMarkAsReviewedBlockSTD").hide();
       //$j("div#addJurisdictionBlock").hide();
      
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
	document.forms[0].action ='/nbs/LoadNewLabReview1.do?method=filterObservationsSubmit';
	document.forms[0].submit();			
}

function clearFilter()
{

	document.forms[0].action ='/nbs/LoadNewLabReview1.do?ContextAction=Review&initLoad=true&method=loadQueue';
	document.forms[0].submit();										
}





	/**
	* markAsReviewedSection: method for showing the blue section
	*/

	function markAsReviewedSection(){
		
		
		//resetMessageBlock();
		
		
			resetDropdowns();
			//getCheckBoxes();
			if(isAnyCheckBoxSelected(getElementByIdOrByName('selectAllValue'))){
				//showAddAttachmentBlock();	
				
				validateSelection();
				
			//	prepopulateProgramArea("programAreaStyleP_textbox", "programAreaStyleP");				
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
	 * showMarkAsReviewedBlock(): show the mark as review blue section
	 */

	 function showMarkAsReviewedBlock(){
			
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
            $j("div#addMarkAsReviewedBlock").show();
           // $j("div#addAttachmentBlock").hide();
	      //  $j("div#addJurisdictionBlock").hide();
            $j("div#addMarkAsReviewedBlock").find(":input").attr("disabled","");            
           
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
	 function showMarkAsReviewedBlockSTD(){
			
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
         $j("div#addMarkAsReviewedBlockSTD").show();
        // $j("div#addAttachmentBlock").hide();
	      //  $j("div#addJurisdictionBlock").hide();
         $j("div#addMarkAsReviewedBlockSTD").find(":input").attr("disabled","");            
        
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
		* cancelAttachment(): hide the blue section
		*/
		
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
		}
		
		/**
		* cancelAttachmentBlock: cancel the blue section
		*/
		
		function cancelAttachmentBlock() {
	        // enable all input elts
	      
	        $j("body").find(":input").attr("disabled","");
	  
	        // hide attachments block
	    
	     	 //$j("div#addAttachmentBlock").hide();
	        $j("div#addMarkAsReviewedBlock").hide();
	        $j("div#addMarkAsReviewedBlockSTD").hide();
	        //$j("div#addJurisdictionBlock").hide();
	       
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
		*
		*/
		
        function markAsReviewedSubmitSTD(){
        	

			//Validations (error messages)
			var errors = new Array();
              var index = 0;
              var hasErrors = false;
              var processingDecision = document.observationReviewForm.processingDecisionStyleSTD.value;
              
			  
              // 
              if (jQuery.trim(processingDecision) == "") {
                  errors[index++] = "Processing Decision is a required field.";
                  hasErrors = true;
               
                  $j("td#processingDecisionLabel").css("color", "#CC0000");
              }
              else {
                  $j("td#processingDecisionLabel").css("color", "black");
              }        
             
              if (hasErrors) {
                  displayErrors("errorBlockSTD", errors);

                  return false;
              }
      	    
              else {
                 //blockUIDuringFormSubmissionNoGraphic();
               $j("div#errorBlockSTD").hide();
           
                /* if ($j("div#msgBlockP").css("display") == "none") {
                     $j("div#msgBlockP").css("display", "block");
          
                 }*/
			  
			 
	           	
	           	   markAsReviewedBulk(2);
                 
				 
               

                  return true;
                 }
             
		}
		function markAsReviewedSubmit(){
	

			//Validations (error messages)
			var errors = new Array();
              var index = 0;
              var hasErrors = false;
              var processingDecision = document.observationReviewForm.processingDecisionStyle.value;
              
			  
              // 
              if (jQuery.trim(processingDecision) == "") {
                  errors[index++] = "Reason For No Further Action is a required field.";
                  hasErrors = true;
               
                
                  $j("td#reasonLabel").css("color", "#CC0000");
              }
              else {
                 
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
			  
			 
	           	
	           	   markAsReviewedBulk(1);
                 
				 
               

                  return true;
                 }
             
		}
		function markAsReviewedBulk(i){
			
			setSelectedDocumentIds(i);
			document.forms[0].action ="/nbs/MarkAsReviewedBulk1.do?method=markAsReviewedBulk";
            //document.forms[0].action ="#";
			document.forms[0].submit();  
		}
             

      	   
		  
     
	    function setSelectedDocumentIds(index){
	        var sb = "";
			
	        var documentIdsLabs = getElementByIdOrByName("chkboxIdsLabs"+index);
	        var documentMprIdsLabs = getElementByIdOrByName("chkboxMprIdsLabs"+index);
	        var documentIdsMorbs = getElementByIdOrByName("chkboxIdsMorbs"+index);
	        var documentMprIdsMorbs = getElementByIdOrByName("chkboxMprIdsMorbs"+index);
	        var documentIdsCases = getElementByIdOrByName("chkboxIdsCases"+index);
	        var documentMprIdsCases = getElementByIdOrByName("chkboxMprIdsCases"+index);
		
			var checkboxes = document.getElementsByName('selectCheckBox');
			var documentTypeMorbOrCase=false;
			var documentType="";
			var table = document.getElementsByClassName("dtTable")[0];
			var indexDocumentType = 1;
			var indexLocalId = 8;
			
			var sbLabs="";
			var sbMprLabs="";
			var sbMorbs="";
			var sbMprMorbs="";
			var sbCases="";
			var sbMprCases="";
			
		

	         
	       
	        for (var i = 1; i <= checkboxes.length; i++) {
				var row = table.getElementsByTagName("tr")[i];
	        	if(row!=null && row!='undefined'&& getCorrectAttribute(row.getElementsByTagName("input")[0], "checked",row.getElementsByTagName("input")[0].checked)==true){
	               
				   documentType = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].innerHTML;

					   var id = row.getElementsByTagName("td")[indexLocalId].innerHTML;
					   
					   var link = row.getElementsByTagName("td")[indexDocumentType].getElementsByTagName("a")[0].getAttribute("onclick")
					   var indexObservationUid=link.indexOf("observationUID");
					   var indexMprUid=link.indexOf("MPRUid");
					   
					   var observationUid=link.substring(indexObservationUid+15,link.length-2);
					  
					   var mprUid=link.substring(indexMprUid+7,indexMprUid+7+8);
					   
					   if(documentType.indexOf("Morbidity Report")!=-1){
						   sbMorbs = sbMorbs + observationUid + "|";
					 
						   sbMprMorbs = sbMprMorbs + mprUid + "|";
					   }else 
					
						 
						   if(documentType.indexOf("Case Report")!=-1){
						  	 sbCases = sbCases + observationUid + "|";
						 
						  	 sbMprCases = sbMprCases + mprUid + "|";
						   }
						   else{ //lab report uid
							   sbLabs = sbLabs + observationUid + "|";

							   sbMprLabs = sbMprLabs + mprUid + "|";
								
						   }
					}
	    	}
	        
	         documentIdsLabs.value=sbLabs;
	         documentMprIdsLabs.value=sbMprLabs;
	         documentIdsMorbs.value=sbMorbs;
	         documentMprIdsMorbs.value=sbMprMorbs;
	         documentIdsCases.value=sbCases;
	         documentMprIdsCases.value=sbMprCases;
	         
	    }
	    
	    /**
	     * resetDropdowns: unselect the selected option from any of the Program Area/Jurisdiction dropdowns
	     */

	    function resetDropdowns(){
	    	if(document.getElementById("processingDecisionStyle")!=null && document.getElementById("processingDecisionStyle")!=undefined)
	    		document.getElementById("processingDecisionStyle").selectedIndex=-1;
	    	if(document.getElementById("processingDecisionStyleSTD")!=null && document.getElementById("processingDecisionStyleSTD")!=undefined)
	    		document.getElementById("processingDecisionStyleSTD").selectedIndex=-1;
	    }

	    /**
	     * showHideMessage: show the success/error message on the feedback bar if the message contains any text
	     */

	    function showHideMessage(){
	    	if(document.getElementById("msgBlock").textContent=="")
	    		document.getElementById("msgBlock").hide();
	    	else
	    		document.getElementById("msgBlock").show();
	    	
	    	if(document.getElementById("errorBlock").textContent=="")
	    		document.getElementById("errorBlock").hide();
	    	else
	    		document.getElementById("errorBlock").show();
	    	
	    	
	    }
