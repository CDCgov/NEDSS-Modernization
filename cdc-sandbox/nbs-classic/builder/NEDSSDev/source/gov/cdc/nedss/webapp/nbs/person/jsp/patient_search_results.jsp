<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<html lang="en">
    <head>
        <title>NBS: Search Results</title>
        <%@ include file="/jsp/resources.jsp" %>
         <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPersonSearchForm.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="searchResultsSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT> 
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript">
         
		 
      
    	blockEnterKey();
      
       
        function showCount()
        {
        
             $j(".pagebanner b").each(function(i){ 
                 $j(this).append(" of ").append($j("#queueCnt").attr("value"));
             });
             $j(".singlepagebanner b").each(function(i){ 
                 var cnt = $j("#queueCnt").attr("value");
                
                 if(cnt > 0)
                     $j(this).append(" Results 1 to ").append(cnt).append(" of ").append(cnt);
             });             
        } 

        function getPage(target)
    	{  	
    	    document.forms[0].action =target;
    	 	document.forms[0].submit();
    	}

        function cancelFilter(key) {                    
        	if(getElementByIdOrByName("reportType").value == "N")
        	{
        		key1 = key.substring(key.indexOf("(")+1, key.indexOf(")")); 
                             
              revertOldSelections(key, null);
          }
        	else
        		{
        		key1 = key.substring(key.indexOf("(")+1, key.indexOf(")"));				  		
        		JPersonSearchForm.getAnswerArray(key1, function(data) {			  			
        			revertOldSelections(key, data);
        		});
        		}
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
        
        function onKeyUpValidate(){ 
        	if(getElementByIdOrByName("reportType").value == "N"){     	  
        	if(getElementByIdOrByName("SearchText1").value != ""){
          		getElementByIdOrByName("b1SearchText1").disabled=false;
          		getElementByIdOrByName("b2SearchText1").disabled=false;
          	   }else if(getElementByIdOrByName("SearchText1").value == ""){
          		getElementByIdOrByName("b1SearchText1").disabled=true;
          		getElementByIdOrByName("b2SearchText1").disabled=true;
          	   }
          	   if(getElementByIdOrByName("SearchText2").value != ""){
          		getElementByIdOrByName("b1SearchText2").disabled=false;
          		getElementByIdOrByName("b2SearchText2").disabled=false;
          	   }else if(getElementByIdOrByName("SearchText2").value == ""){
          		getElementByIdOrByName("b1SearchText2").disabled=true;
          		getElementByIdOrByName("b2SearchText2").disabled=true;
          	   }
          	 if(getElementByIdOrByName("SearchText3").value != ""){
           		getElementByIdOrByName("b1SearchText3").disabled=false;
           		getElementByIdOrByName("b2SearchText3").disabled=false;
           	   }else if(getElementByIdOrByName("SearchText3").value == ""){
           		getElementByIdOrByName("b1SearchText3").disabled=true;
           		getElementByIdOrByName("b2SearchText3").disabled=true;
           	   }
            if(getElementByIdOrByName("SearchText4").value != ""){
           		getElementByIdOrByName("b1SearchText4").disabled=false;
           		getElementByIdOrByName("b2SearchText4").disabled=false;
           	   }else if(getElementByIdOrByName("SearchText4").value == ""){
           		getElementByIdOrByName("b1SearchText4").disabled=true;
           		getElementByIdOrByName("b2SearchText4").disabled=true;
           	   }
            if(getElementByIdOrByName("SearchText5").value != ""){
           		getElementByIdOrByName("b1SearchText5").disabled=false;
           		getElementByIdOrByName("b2SearchText5").disabled=false;
           	   }else if(getElementByIdOrByName("SearchText5").value == ""){
           		getElementByIdOrByName("b1SearchText5").disabled=true;
           		getElementByIdOrByName("b2SearchText5").disabled=true;
           	   }
            if(getElementByIdOrByName("SearchText6").value != ""){
           		getElementByIdOrByName("b1SearchText6").disabled=false;
           		getElementByIdOrByName("b2SearchText6").disabled=false;
           	   }else if(getElementByIdOrByName("SearchText6").value == ""){
           		getElementByIdOrByName("b1SearchText6").disabled=true;
           		getElementByIdOrByName("b2SearchText6").disabled=true;
           	   }
            
 		}
 		else if(getElementByIdOrByName("reportType").value == "I"){   
 			if(getElementByIdOrByName("Patient").value != ""){
           		getElementByIdOrByName("b1Patient").disabled=false;
           		getElementByIdOrByName("b2Patient").disabled=false;
           	   }else if(getElementByIdOrByName("Patient").value == ""){
           		getElementByIdOrByName("b1Patient").disabled=true;
           		getElementByIdOrByName("b2Patient").disabled=true;
           	   }
 			
 			 if(getElementByIdOrByName("LocalIdSearchInv").value != ""){
          		getElementByIdOrByName("b1LocalIdSearchInv").disabled=false;
          		getElementByIdOrByName("b2LocalIdSearchInv").disabled=false;
          	   }else if(getElementByIdOrByName("LocalIdSearchInv").value == ""){
          		getElementByIdOrByName("b1LocalIdSearchInv").disabled=true;
          		getElementByIdOrByName("b2LocalIdSearchInv").disabled=true;
          	   }
 		}
 		else if(getElementByIdOrByName("reportType").value == "LMC" || getElementByIdOrByName("reportType").value == "LR" ){
 			if(getElementByIdOrByName("PatientSearchText").value != ""){
         		getElementByIdOrByName("b1PatientSearchText").disabled=false;
         		getElementByIdOrByName("b2PatientSearchText").disabled=false;
         	   }else if(getElementByIdOrByName("PatientSearchText").value == ""){
         		getElementByIdOrByName("b1PatientSearchText").disabled=true;
         		getElementByIdOrByName("b2PatientSearchText").disabled=true;
         	   }
        	if(getElementByIdOrByName("LocalIdSearchText").value != ""){
         		getElementByIdOrByName("b1LocalIdSearchText").disabled=false;
         		getElementByIdOrByName("b2LocalIdSearchText").disabled=false;
         	   }else if(getElementByIdOrByName("LocalIdSearchText").value == ""){
         		getElementByIdOrByName("b1LocalIdSearchText").disabled=true;
         		getElementByIdOrByName("b2LocalIdSearchText").disabled=true;
         	   } 
        	if(getElementByIdOrByName("Provider").value != ""){
         		getElementByIdOrByName("b1Provider").disabled=false;
         		getElementByIdOrByName("b2Provider").disabled=false;
         	   }else if(getElementByIdOrByName("Provider").value == ""){
         		getElementByIdOrByName("b1Provider").disabled=true;
         		getElementByIdOrByName("b2Provider").disabled=true;
         	   } 
 		}
		}

        function makeMSelects() {
            $j("#fullName").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#telephone").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#idSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#patientIdSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#ageSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#addressSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#patient").text({actionMode: '${fn:escapeXml(ActionMode)}'});	
            $j("#notif").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
            $j("#sdate").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#inv").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#juris").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#cond").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#stat").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			<%-- //$j("#sdate").multiSelect({actionMode: '<%= request.getAttribute("ActionMode") %>'});
			//$j("#juris").multiSelect({actionMode: '<%= request.getAttribute("ActionMode") %>'});
			//$j("#patient").text({actionMode: '<%= request.getAttribute("ActionMode") %>'}); --%>
			$j("#obsType").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#testCond").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#localId").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#provider").text({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#descrip").multiSelect({actionMode: '${fn:escapeXml(ActionMode)}'});
			$j("#localIdSearch").text({actionMode: '${fn:escapeXml(ActionMode)}'});
       }
        function attachIcons() {    
            $j("#searchResultsTable thead tr th a").each(function(i) {  
                if($j(this).html() == 'Name'){
                    $j(this).parent().append($j("#fullName"));                    
                } 
                if($j(this).html() == 'Phone/Email'){
                    $j(this).parent().append($j("#telephone"));                    
                } 
                if($j(this).html() == 'ID'){
                    $j(this).parent().append($j("#idSearch"));                    
                } 
                 if($j(this).html() == 'Patient ID'){
                    $j(this).parent().append($j("#patientIdSearch"));                    
                }  
                 if($j(this).html() == 'Age/DOB/Sex'){
                     $j(this).parent().append($j("#ageSearch"));                    
                 } 
                  if($j(this).html() == 'Address'){
                     $j(this).parent().append($j("#addressSearch"));                    
                 }  
                  if($j(this).html() == 'Patient'){
                      $j(this).parent().append($j("#patient"));                    
                  }  
                  if($j(this).html() == 'Notification'){
                      $j(this).parent().append($j("#notif"));                    
                  } 
                  if($j(this).html() == 'Start Date'){
						$j(this).parent().append($j("#sdate"));
                  }
                  if($j(this).html() == 'Investigator'){
						$j(this).parent().append($j("#inv"));
                  }
                  if($j(this).html() == 'Jurisdiction'){
						$j(this).parent().append($j("#juris"));
                  }
                  if($j(this).html() == 'Condition'){
						$j(this).parent().append($j("#cond"));
                  }
                  if($j(this).html() == 'Case Status'){
						$j(this).parent().append($j("#stat"));
                  }
                  if($j(this).html() == 'Date Received')
						$j(this).parent().append($j("#sdate"));
			      /* if($j(this).html() == 'Jurisdiction')
						$j(this).parent().append($j("#juris")); */
			      if($j(this).html() == 'Document Type')
						$j(this).parent().append($j("#obsType"));
			      if($j(this).html() == 'Associated With')
						$j(this).parent().append($j("#testCond"));	
			     /*  if($j(this).html() == 'Patient')
						$j(this).parent().append($j("#patient")); */
			      if($j(this).html() == 'Local ID')
						$j(this).parent().append($j("#localId"));
			      if($j(this).html() == 'Facility/Provider')
						$j(this).parent().append($j("#provider"));
			      if($j(this).html() == 'Description')
						$j(this).parent().append($j("#descrip"));
			      if($j(this).html() == 'Investigation ID'){
                      $j(this).parent().append($j("#localIdSearch"));                    
                  }
                }); 
            $j("#searchResultsTable").before($j("#whitebar"));
            $j("#searchResultsTable").before($j("#removeFilters"));
        }

        function displayTooltips() {        
            $j(".sortable a").each(function(i) {
            
                var headerNm = $j(this).html();
                var fullNameSearch = getElementByIdOrByName("SearchText1") == null ? "" : getElementByIdOrByName("SearchText1").value;
                var telePhoneSearch  = getElementByIdOrByName("SearchText2") == null ? "" : getElementByIdOrByName("SearchText2").value;
                var idSearch  = getElementByIdOrByName("SearchText3") == null ? "" : getElementByIdOrByName("SearchText3").value;
                var patientIdSearch  = getElementByIdOrByName("SearchText4") == null ? "" : getElementByIdOrByName("SearchText4").value;
                var ageSearch  = getElementByIdOrByName("SearchText5") == null ? "" : getElementByIdOrByName("SearchText5").value;
                var addressSearch  = getElementByIdOrByName("SearchText6") == null ? "" : getElementByIdOrByName("SearchText6").value;
                
                 if(headerNm =='Name') {                
                	 _setAttributes(headerNm, $j(this), fullNameSearch);
                  }
                else if(headerNm =='Phone/Email') {                
               	 _setAttributes(headerNm, $j(this), telePhoneSearch);
                 }
                else if(headerNm =='ID') {                
                  	 _setAttributes(headerNm, $j(this), idSearch);
                    }
                else if(headerNm =='Patient ID') {                
                  	 _setAttributes(headerNm, $j(this), patientIdSearch);
                    } 
                else if(headerNm =='Age/DOB/Sex') {                
                 	 _setAttributes(headerNm, $j(this), ageSearch);
                   }
                else if(headerNm =='Address') {                
                 	 _setAttributes(headerNm, $j(this), addressSearch);
                   }
               else if(headerNm == 'Start Date') {
       			_setAttributes(headerNm, $j(this), $j("#INV147"));
       	      } 
               else if(headerNm == 'Investigator') {
       			_setAttributes(headerNm, $j(this), $j("#INV100"));
       	      } else if(headerNm == 'Jurisdiction') {
       			_setAttributes(headerNm, $j(this), $j("#INV107"));
       	      }else  if(headerNm == 'Condition') {
       			_setAttributes(headerNm, $j(this), $j("#INV169"));
       	      }else  if(headerNm == 'Case Status') {
       			_setAttributes(headerNm, $j(this), $j("#INV163"));
       	      } else  if(headerNm == 'Notification') {
       			_setAttributes(headerNm, $j(this), $j("#NOT118"));			
       	      } else if(headerNm == 'Patient') {
       	    	  _setAttributes(headerNm, $j(this),  $j("#PATIENT"));
       	      }
       	      else if(headerNm == 'Date Received') {
   			_setAttributes(headerNm, $j(this), $j("#INV147"));
   	      } /* else if(headerNm == 'Jurisdiction') {
   			_setAttributes(headerNm, $j(this), $j("#INV107"));
   	      } */ else if(headerNm == 'Document Type') {
   			_setAttributes(headerNm, $j(this), $j("#OBS118"));			
   	      } /* else if(headerNm == 'Patient') {
   	    	  _setAttributes(headerNm, $j(this), $j("#PATIENT"));
   	      }	 */else if(headerNm == 'Associated With') {
   	    	_setAttributes(headerNm, $j(this), $j("#INV169"));
   	      }else if(headerNm == 'Local ID') {
   	    	  _setAttributes(headerNm, $j(this), $j("#LOCALID"));
   		  }else if(headerNm == 'Facility/Provider') {
   	    	  _setAttributes(headerNm, $j(this), $j("#PROVIDER"));//TODO: CHANGE
   		  }else if(headerNm == 'Description') {
   	    	  _setAttributes(headerNm, $j(this), $j("#DESCI"));//TODO: CHANGE
   		  }else if(headerNm == 'Investigation ID') {
   	    	  _setAttributes(headerNm, $j(this), $j("#LOCALID"));
   		  }
            });             
        } 

        function _showAtoZIcon(headerNm, link, colId) {            
            var htmlAsc = '<img class="multiSelect" src="GraySortAsc.gif" alt = "Sort Ascending" id="queueIcon" align="top" border="0"/>';
            var htmlDesc = '<img class="multiSelect" src="GraySortDesc.gif" alt = "Sort Descending" id="queueIcon" align="top" border="0"/>';
            var sortSt =  getElementByIdOrByName("sortSt") == null ? "" : getElementByIdOrByName("sortSt").value;            
            var sortFirstStr = sortSt.substring(0,(sortSt.indexOf("@")-1));
            var sortSecondStr = sortSt.substring(sortSt.indexOf("@")+1);
            
            if(sortFirstStr != null && sortFirstStr==headerNm) {
                if(sortSecondStr != null && sortSecondStr.indexOf("descending") != -1) {
                    link.after(htmlDesc);
                } else {
                    link.after(htmlAsc);
                }
            }

        }

        function _setAttributes(headerNm, link, colId) {
            
            var imgObj = link.parent().find("img");
           
            var toolTip = "";   
            var sortSt =  $j("#sortSt") == null ? "" : $j("#sortSt").html();
            
            var sortFirstStr = sortSt.substring(0,(sortSt.indexOf("@")-1));
            var sortSecondStr = sortSt.substring(sortSt.indexOf("@")+1);
            var orderCls = "SortAsc.gif";
            var altOrderCls = "Sort Ascending";
            var sortOrderCls = "FilterAndSortAsc.gif";
            var altSortOrderCls = "Filter Applied with Sort Ascending";
			
            
            if(sortSecondStr != null && sortSt.indexOf("descending") != -1) {
                orderCls = "SortDesc.gif";
                altOrderCls = "Sort Descending";
                sortOrderCls = "FilterAndSortDesc.gif";
                altSortOrderCls = "Filter Applied with Sort Descending";
            }   
            var filterCls = "Filter.gif";
            var altFilterCls = "Filter Applied";
            //toolTip = colId.html() == null ? "" : colId.html();
            if(colId != null && colId.length > 0) {
            
                link.attr("title", toolTip);
                imgObj.attr("src", filterCls);
                imgObj.attr("alt", altFilterCls);
                if(sortFirstStr != null && sortFirstStr == headerNm ){
                    imgObj.attr("src", sortOrderCls);           
                	imgObj.attr("alt", altSortOrderCls);	
           	 }
            } else {
            
                if(sortFirstStr != null && sortFirstStr==headerNm) {           
                    imgObj.attr("src", orderCls);    
                    imgObj.attr("alt", altOrderCls);
                }           
            }
        }
        
         function selectfilterCriteria()
	 {
	            document.forms[0].action ='/nbs/PatientSearchResults1.do?ContextAction=filterPatientSubmit';
	            document.forms[0].submit();
         } 
         
        function clearFilter()
	{
	             document.forms[0].action ='/nbs/PatientSearchResults1.do?ContextAction=removeFilter';
	             document.forms[0].submit();                                    
        }
        
        function printQueue() {
        	window.location.href = $j(".exportlinks a:last").attr("href") == null ? "#" :  $j(".exportlinks a:last").attr("href");
        }
        function exportQueue() {
        	window.location.href = $j(".exportlinks a:first").attr("href") == null ? "#" : $j(".exportlinks a:first").attr("href");
        }
        
		function createLink(element, url)
		{
			// call the JS function to block the UI while saving is on progress.
			blockUIDuringFormSubmissionNoGraphic();
            document.forms[0].action= url;
            //alert(url);
            document.forms[0].submit();  
		}
	
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
		
		function UncheckLabReportCheckBoxes(){ 
			 $j("div#stdHivDropdownBlock").hide();
			 $j("div#nonStdHivDropdownBlock").hide();
			var type=document.getElementById('reportType').value;
			var checkboxValue = null;
			var index=0;
			if(type != null && type == "LR"){     	  
		      var checkBoxes  = document.getElementsByName("selectCheckBox");
		      var selectAllCheck= document.getElementsByName("selectAllValue");
     
		      for(var i = 0; i < checkBoxes.length; i++){ 
		        if(checkBoxes[i].type=='checkbox'){
		        	checkboxValue=checkBoxes[i].value;
		        	if( checkboxValue !=null && checkboxValue !='undefined'&& checkboxValue.substr(0, checkboxValue.indexOf('-')) == 'UNPROCESSED') {
		        		checkBoxes[i].checked = false; 
		        		checkBoxes[i].disabled = false;
		        	} else {
		        		checkBoxes[i].disabled = true;
		        		index++;
		        	}
		        }
		      }
		      if(checkBoxes != null && index == checkBoxes.length ){
		    	  getElementByIdOrByName('selectAllValue').checked=false;
		    	  getElementByIdOrByName('selectAllValue').disabled=true;
		    	  document.getElementById("markAsReview").style.pointerEvents="none";
		      } else{
		    	  getElementByIdOrByName('selectAllValue').checked=false;
		    	  getElementByIdOrByName('selectAllValue').disabled=false; 
		    	  document.getElementById("markAsReview").style.pointerEvents = "auto";
		      }
		  } 
		}

		function markAsReviewedSectionForLRQ(){
			$j("div#msgBlock").hide();
 			$j("div#errorBlock").hide();
			if(isAnyCheckBoxSelected()){
				if(!isMixedStdHivAndNonStdHivRecords()){
				   showMarkAsReviewedBlock();  
				} else {
					document.getElementById("errorBlock").textContent="Documents in the STD/HIV Program Area must be processed in bulk separately. Please de-select the documents and try again.";
					showHideMessage();
					return false;
				}
			}
		}
		
		function isMixedStdHivAndNonStdHivRecords(){
			var programArea="";
			var programAreaHIVOrSTD=false;
			var programAreaOther=false;
			var checkBoxValue="";
			var elementName;
			var checkBoxes  = document.getElementsByName("selectCheckBox");
		    for(var i = 0; i < checkBoxes.length; i++){ 
		    	elementName=checkBoxes[i];
		    	 if(elementName.type=='checkbox' && elementName.checked==true){
					 checkboxValue=elementName.value;
					 if( checkboxValue !=null && checkboxValue !='undefined')  programArea= checkboxValue.substring(checkboxValue.indexOf('-')+1);
					 if(programArea == "true"){
						 programAreaHIVOrSTD=true;
					 } else {
						 programAreaOther=true; 
					 }
				 }
				 
			 }
		
			 
			 if(programAreaHIVOrSTD && programAreaOther){
				 return true;
			 } else {
				  if(programAreaHIVOrSTD){
					 $j("div#stdHivDropdownBlock").show();
					 $j("div#nonStdHivDropdownBlock").hide();
					 document.getElementById('markAsReviewdReason1').value = 'STD_UNKCOND_PROC_DECISION';
				 }
				 if(programAreaOther){
					 $j("div#stdHivDropdownBlock").hide();
					 $j("div#nonStdHivDropdownBlock").show();
					 document.getElementById('markAsReviewdReason1').value = 'NBS_NO_ACTION_RSN';
				 }
				 return false;
			 }
		}
		
		
		 function isAnyCheckBoxSelected() {
			var checkboxes = document.getElementsByTagName('input');
			var selected=false;
				 for (var i = 0; i < checkboxes.length; i++) {
					if((checkboxes[i].name == "selectCheckBox") && (checkboxes[i].checked)==true){
						 selected = true;
					}
				}
			return selected;
		 }
		 
	  
		 function showMarkAsReviewedBlock(){
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
		         $j("div#addMarkAsReviewedBlock").find(":input").attr("disabled","");            
		      
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
		        document.getElementById('markAsReviewdReason1').disabled=false;
		        window.scrollTo(0,0);
				   
			}
		 
		 function cancelAttachment() {
			   $j("td#fileNameLabel").css("color", "black");
		       $j("td#reasonLabel").css("color", "black");
		       $j("td#processingDecisionLabel").css("color", "black");
		       $j("div#errorBlock").hide();
		       $j("div#errorBlockP").hide();
		       $j("div#errorBlockSTD").hide();
		       cancelAttachmentBlock();
			   UncheckLabReportCheckBoxes();
			}
			
			function cancelAttachmentBlock() {   
		        $j("body").find(":input").attr("disabled","");
		        $j("div#addMarkAsReviewedBlock").hide();
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
		        $j('.removefilerLink').attr("disabled","")   
		    }
	        
			function markAsReviewedSubmit(){
				var errors = new Array();
	            var hasErrors = false;
	            var index=0;
	            var observationValues ="";
	            var processingDecision=""
	            if( document.getElementById('markAsReviewdReason1').value == 'STD_UNKCOND_PROC_DECISION'){
	               processingDecision = document.personSearchForm.processingDecisionStyleStd.value;
	            }else{
	               processingDecision = document.personSearchForm.processingDecisionStyle.value;
	            }
	            
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
	              }else {
	                $j("div#errorBlockP").hide();
	            	var checkboxes = document.getElementsByName("selectCheckBox");
	            	var documentIdsLabs = getElementByIdOrByName("chkboxIdsLabs1");    
	            	for (var i = 0; i < checkboxes.length; i++) {
	  			  		if( checkboxes[i].checked == true){ 
	  			  			observationValues=observationValues+checkboxes[i].id+ "|";
	  			  		}
	  				}
	  			  		  			     
	  			     documentIdsLabs.value=observationValues;
	   				 document.forms[0].action ="/nbs/MarkAsReviewedFromLabQueue.do";
		   			 document.forms[0].submit();  
	                 return true;
	            }
	             
			}
			
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
        
        </script>       
        <style type="text/css">
        .boxed {
          border: 1px solid #5F8DBF ;background:#E4F2FF
             }
            table.dtTable {margin-top:0em;}
            table.dtTable, table.privateDtTable {width:100%; border:1px solid #666666; padding:0.5em; margin:0m auto; margin-top:0em;}
			table.dtTable thead tr th,
			table.privateDtTable thead tr th {text-decoration:none; border:1px solid #666666; 
			        font-weight:bold; background:#EFEFEF; padding:0em; text-align:center;}
			table.dtTable tbody tr td, table.privateDtTable tbody tr td {vertical-align:top;} 
			table.dtTable thead tr th, table.privateDtTable thead tr th {text-decoration:none; border:1px solid #666666; 
			        font-weight:bold; background:#EFEFEF; padding:0.1em 0 0.1em 0.1em; text-align:center;}
			table.dtTable tbody tr.odd, table.privateDtTable tbody tr.odd, table.dtTable tbody tr.odd td table tr {background:#FFF;}
			table.dtTable tbody tr.even, table.privateDtTable tbody tr.even, table.dtTable tbody tr.even td table tr {background:#dce7f7;}
			table.dtTable tbody tr td table tr td {border-width:0;}
			table.dtTable tbody tr td, table.privateDtTable tbody tr td {padding:2px; border-width:0px 1px 1px 0px; border-style:solid; border-color:#C2D4EF;}
			table.dtTable tbody tr td.hoverDescLink a, table.privateDtTable tbody tr td.hoverDescLink a {text-decoration:none; color:#000; cursor:help;}
			table.dtTable tbody tr td.hoverDescLink a:hover, table.privateDtTable tbody tr td.hoverDescLink a:hover {background:#FFE2BF;}
			table.dtTable tbody tr td.dateField, table.dtTable tbody tr td.iconField {width:50px;}
			table.dtTable tbody tr td.nameField {width:150px;} 
			table.dtTable tbody tr td.iconField {text-align:center;}
	        div.messages { background:#E4F2FF; color:#000; padding:0.5em; border-width:1px 1px 1px 1px; border-color:#7AA6D5; border-style:solid; font-size:95%;}
	        div.popupButtonBar {text-align:right; width:100%; background:#EEE; border-bottom:1px solid #DDD;}
		      .removefilter{
					background-color:#003470; width:100%; height:25px;
					line-height:25px;float:right;text-align:right;
					}
					removefilerLink {vertical-align:bottom;  }
					.hyperLink
					{
					    font-size : 10pt;
					    font-family : Geneva, Arial, Helvetica, sans-serif;
					    color : #FFFFFF;
						text-decoration: none;
					}
			div#addMarkAsReviewedBlock {margin-top:10px; display:none; width:100%; text-align:center;}
            </style>
    </head>
    <body onload="startCountdown();attachIcons();makeMSelects();showCount();displayTooltips();addTabs();addRolePresentationToTabsAndSections();UncheckLabReportCheckBoxes();showHideMessage()">    
    <div id="blockparent"></div>
        <div id="doc3">
            <div id="bd">
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>
               
               <logic:notEqual name="personSearchForm" property="personSearch.custom" value="true"><!-- To avoid showing the Save button from already custom queues - to avoid being able to save the queue again -->
				  					
            	<div  style="text-align:right; margin-bottom:8px;">
                        <a  href='<%= request.getAttribute("newSearchHref")%>'>
                              New Search  
                     	</a>
                     	&nbsp;|&nbsp;
                     	<a href='<%= request.getAttribute("refineSearchHref")%>'>
                            Refine Search
                     	</a>
                </div>
                </logic:notEqual>
                <!-- Top button bar -->
                  <!-- Print and Export links -->
				  

			
					<logic:match name="personSearchForm" property="attributeMap.reportType" value="N">
                       <div class="grayButtonBar">
                         <table role="presentation" width="100%">
					   		 <tr>
					   		 	<td align="left" width="80%">
					   		 	</td>
					   		 
					   		 	<td align="right" >
					   		 	<logic:equal value="true" name="addButton" scope="request">
					   		 	<input type="button" name="Submit" style="width:70px" value="Add New" onclick="location.href='<%= request.getAttribute("addButtonHref")%>'"/>
					   		 	</logic:equal>
					   		 	</td>
					   		 </tr> 				   		
					   		
					   	</table>	 		
                         
                         </div>  
                 
	               </logic:match>  
				   <logic:notMatch name="personSearchForm" property="attributeMap.reportType" value="N">
				   <div class="printexport" id="printExport" align="right">
								<logic:match name="personSearchForm" property="personSearch.reportType" value="I">
									<logic:notEqual name="personSearchForm" property="personSearch.custom" value="true"><!-- To avoid showing the Save button from already custom queues - to avoid being able to save the queue again -->
				  					<img class="cursorHand" src="save_with_text.gif" align="left" style="width:65px" tabIndex="0" alt="Save as a custom queue" title="Save as a custom queue" onclick="showHideSaveBox(true);" onkeypress="if(isEnterKey(event)) saveQueue();"/>
 									</logic:notEqual>  
 								</logic:match>  
 								<logic:match name="personSearchForm" property="personSearch.reportType" value="LR">
									<logic:notEqual name="personSearchForm" property="personSearch.custom" value="true"><!-- To avoid showing the Save button from already custom queues - to avoid being able to save the queue again -->
				  					<img class="cursorHand" src="save_with_text.gif" align="left" style="width:65px" tabIndex="0" alt="Save as a custom queue" title="Save as a custom queue" onclick="showHideSaveBox(true);" onkeypress="if(isEnterKey(event)) saveQueue();"/>
 									</logic:notEqual>  
 								</logic:match> 
 								 <logic:equal name="personSearchForm" property="personSearch.custom" value="true"><!-- To avoid showing the Save button from already custom queues - to avoid being able to save the queue again -->
 									<img class="cursorHand" src="delete_with_text.gif" align="left" style="width:70px" tabIndex="0" alt="Delete custom queue" title="Delete custom queue" onclick="deleteCustomQueue(true);" onkeypress="if(isEnterKey(event)) saveQueue();"/>
								 </logic:equal>	
								<img tabIndex="0" class="cursorHand" src="print.gif" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
								<img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
					</div>
                  
                    </logic:notMatch>
 				<!-- <div  style="width:100%;">
		        	<table cellpadding="0" cellspacing="0" border="0" width="100%" background="task_button/tb_cel_bak.jpg">
						<tr >
							<td width="70%"> &nbsp;
							</td>
							
							<td valign="top" align="left">
							  <a href="<%= request.getAttribute("addButtonHref")%>" >
								<img src="task_button/fa_submit.jpg" alt="Add button" /> 
							  </a>
							</td>
						</tr>
						<tr>
						<td width="70%"> &nbsp;
							</td>
							<td class="boldEightBlack" align="left" valign="top">&nbsp;Add&nbsp;</td>
								
						</tr>
					</table>
                  </div>-->
              
	              <logic:equal name="personSearchForm" property="personSearch.custom" value="true"><!-- To avoid showing the Save button from already custom queues - to avoid being able to save the queue again -->				
	             
		              <div class="infoBox messages" style="text-align: center;">
								Your Search Criteria:  <%= request.getAttribute("searchCriteriaDesc")%>
				      </div>  
		        
	              </logic:equal>
                <logic:notEqual name="personSearchForm" property="personSearch.custom" value="true"><!-- To avoid showing the Save button from already custom queues - to avoid being able to save the queue again -->				
              
	
						<div class="infoBox messages" style="text-align: center;">
						Your Search Criteria:  <%= request.getAttribute("DSSearchCriteriaString")%> resulted in <%= request.getAttribute("queueCount")%> possible matches. 
						Would you like to <a href='<%= request.getAttribute("refineSearchHref")%>'> refine your search</a><logic:match name="personSearchForm" property="attributeMap.reportType" value="N">
						<logic:equal value="true" name="addButton" scope="request">
						or <a href='<%= request.getAttribute("addPatHref")%>'> add a new patient</a>
						</logic:equal>
						</logic:match>?
		        		<logic:match name="personSearchForm" property="attributeMap.totalRecords" value="error">
						<br>
						      The number of records that you can access has been exceeded. You will not be able to view all records that match your search parameters. Please refine your search to narrow your results.
						   
						</logic:match>
						
		        		</div>  
        	
				</logic:notEqual>
        	<%@ include file="../../jsp/saveCustomQueue.jsp" %>	
        	     <logic:equal name="personSearchForm" property="personSearch.custom" value="true">
					<logic:equal name="personSearchForm" property="attributeMap.reportType" value="LR">
						<logic:equal name="personSearchForm" property="attributeMap.permissionMarkAsReviewed" value="true">
			      	     	<div id="msgBlock" class="screenOnly infoBox success" style="width:99%;display:none;">${msgBlock}</div>
	 						<div id="errorBlock" class="screenOnly infoBox errors" style="width:99%;display:none;">${errorBlock}</div>
	 	                 </logic:equal>
	 	             </logic:equal>
	 	         </logic:equal>    
               <html:form action="/LoadPatientSearchResults1.do"> 
               
               <div id="addMarkAsReviewedBlock" class="boxed">

			
				                        <table role="presentation" class="formTable" style=" width:100%; margin:0px;">
				                        	<html:hidden name="personSearchForm" property="selectedcheckboxIdsLabs" styleId="chkboxIdsLabs1"/>
									        <tr>
									            <td colspan="3">
									                <div id="errorBlockP" class="screenOnly infoBox errors" style="display:none; width:99%"> </div>
									            </td>
									        </tr>
									        
									    
									        <tr>
									               <td colspan="3" style="text-align:left;"><span style="color:black; font-weight:bold;"> &nbsp;&nbsp;Please select a reason for taking no further action and enter any additional
									                comments that help to explain why no further action is required.
			 										This reason will be applied to all the records that have been selected below.
													 Documents that are marked as reviewed will remain on the patient's file, and if previously associated to an investigation will remain associated to
													 an investigation. Select Submit to continue or select Cancel to cancel this action.</td>
									        </tr>
									       
									        <tr>
									         
									            <td colspan ="2" class="fieldName" style="text-align:right; width:50%" id="reasonLabel"> <span style="color:#CC0000; font-weight:bold;">*</span> Reason For No Further Action:</td>
												<td class="InputField">	
												<div id="nonStdHivDropdownBlock">						
													<html:select title="Select Reason For No Further Action" name ="personSearchForm" property="processingDecisionSelected" styleId ="processingDecisionStyle">
														<html:optionsCollection property="nonSTDProcessingDecisionList" value="key" label="value"/>
													</html:select>
												</div>
												<div id="stdHivDropdownBlock">						
													<html:select title="Select Reason For No Further Action" name="personSearchForm" property="processingDecisionSelectedSTD" styleId ="processingDecisionStyleStd">
														<html:optionsCollection property="stdHivProcessingDecisionList" value="key" label="value"/>
													</html:select>
												</div>		
												</td>            									
											</tr>
											<tr>
												<td colspan ="2" id="reasonComments" class="fieldName">Comments:</td><td>
													<textarea title="Enter Comments" rows="6" cols="60" id="markAsReviewedComments" name ="markAsReviewedComments" onkeydown="checkMaxLength(this)" onkeyup="checkTextAreaLength(this,999)"></textarea>
												</td>
											</tr>
									        <tr>
									          
									            <td colspan="3" style="text-align:right;">
									             
									                <input type="button" value="Submit" name="submitButton" onclick="return markAsReviewedSubmit();"/>
									               
									                <input type="button" value="Cancel" name="cancelButton" onclick="javascript:cancelAttachment()"/>
									            </td>
									        </tr>      
									    </table>
									    <p><span align='left' class='status-text' id='updateStatusMsg'></span>
									    <div id="progressBar" style="display: none;">
									        <div id="theMeter">
									            <div id="progressBarText"></div>
									            <div id="progressBarBox">
									                <div id="progressBarBoxContent"></div>
									            </div>
									        </div>
									    </div>
									    </p>
						
					            </div>                 
                        <table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">   
                                
                                
								 
							
								
								
								
								  <logic:match name="personSearchForm" property="attributeMap.reportType" value="N">                                                                    
                                    <display:table name="personList" class="dtTable" pagesize="20"  id="searchResultsTable" style="border: 1px solid black;" requestURI="/nbs/PatientSearchResults1.do?ContextAction=sortingByColumn&existing=true&initLoad=true" 
                                        sort="external" export="true" excludedParams="*">
                                     	  
                                     	<display:column property="viewFile" style="width:9%;text-align:left;"  sortable="true"  sortName="getViewFile" title="Patient ID"/> 
                                     	   
                                        <display:column property="personFullName" sortable="true" sortName="getPersonFullName" style="width:20%;text-align:left;" defaultorder="ascending" title="Name"/>
                                        <display:column property="profile" sortable="true" sortName="getProfile" style="width:15%;text-align:left;" defaultorder="ascending" title="Age/DOB/Sex"/>
                                        <display:column property="personAddressProfile" sortable="true" sortName="getPersonAddress" style="width:20%;text-align:left;"  title="Address"/>  
                                        <display:column property="personPhoneprofile" sortable="true" style="width:20%;text-align:left;" sortName="getPersonPhoneprofile" title="Phone/Email"/>  
                                        <display:column property="personIds" style="width:20%;text-align:left;"  sortable="true" sortName="getPersonIds" title="ID"/>  
                                                                    
                                        <display:setProperty name="basic.empty.showtable" value="true" />
                                    </display:table>
										<html:select property="answerArray(SearchText1)" styleId = "fullName" onchange="selectfilterCriteria()" multiple="false" size="1">
    			<html:optionsCollection property="noDataArray" value="key" label="value"/>
			</html:select>
			<html:select property="answerArray(SearchText2)" styleId = "telephone" onchange="selectfilterCriteria()" multiple="false" size="1" >
    			<html:optionsCollection property="noDataArray" value="key" label="value"/>
			</html:select>
			<html:select property="answerArray(SearchText3)" styleId = "idSearch" onchange="selectfilterCriteria()" multiple="false" size="1" >
    			<html:optionsCollection property="noDataArray" value="key" label="value"/>
			</html:select>
			<html:select property="answerArray(SearchText4)" styleId = "patientIdSearch" onchange="selectfilterCriteria()" multiple="false" size="1" >
    			<html:optionsCollection property="noDataArray" value="key" label="value"/>
			</html:select>	
					<html:select property="answerArray(SearchText5)" styleId = "ageSearch" onchange="selectfilterCriteria()" multiple="false" size="1" >
    			<html:optionsCollection property="noDataArray" value="key" label="value"/>
			</html:select>
			<html:select property="answerArray(SearchText6)" styleId = "addressSearch" onchange="selectfilterCriteria()" multiple="false" size="1" >
    			<html:optionsCollection property="noDataArray" value="key" label="value"/>
			</html:select>			
			
				<html:hidden styleId="SearchText1" property="attributeMap.searchCriteria.fulNameSearch"/>
			    <html:hidden styleId="SearchText2" property="attributeMap.searchCriteria.telePhoneSearch"/>
			    <html:hidden styleId="SearchText3" property="attributeMap.searchCriteria.idSearch"/>
			    <html:hidden styleId="SearchText4" property="attributeMap.searchCriteria.patientIdSearch"/>
			    <html:hidden styleId="SearchText5" property="attributeMap.searchCriteria.ageSearch"/>
			    <html:hidden styleId="SearchText6" property="attributeMap.searchCriteria.addressSearch"/>
									</logic:match>
									
									
									<!--Lab Report or Morbidity Report or Case Report type selected-->
									<%-- <logic:match name="personSearchForm" property="attributeMap.reportType" value="LMC"> --%>
									<logic:match name="personSearchForm" property="selectDisplaySection" value="true">
								
								    <display:table name="personList" class="dtTable" pagesize="20"  id="searchResultsTable" style="border: 1px solid black;" requestURI="/nbs/PatientSearchResults1.do?ContextAction=sortingByColumn&existing=true&initLoad=true" 
                                        sort="external" export="true" excludedParams="*">
                                     	  
										<display:setProperty name="export.csv.filename" value="LabReportSearchResult.csv"/>
										<display:setProperty name="export.pdf.filename" value="LabReportSearchResult.pdf"/>
										
										 <logic:equal name="personSearchForm" property="personSearch.custom" value="true">
										  	<logic:equal name="personSearchForm" property="attributeMap.reportType" value="LR">
										 		<logic:equal name="personSearchForm" property="attributeMap.permissionMarkAsReviewed" value="true">
										 	 		<display:column style="width:1.5%;text-align:center" media="html" title="<p style='display:none'>Select/Deselect All</p><input title='Select/Deselect All' type='checkbox' style='margin-left:15%' name='selectAllValue' onchange='checkAll(this)' />" >
										 	 			<input title="Select/Deselect checkbox" type="checkbox" name="selectCheckBox" id="${searchResultsTable.observationUid}" value="${searchResultsTable.recordStatusCd}-${searchResultsTable.nonStdHivProgramAreaCode}"/></display:column>
												 </logic:equal>
											</logic:equal>
										 </logic:equal>
                                     	<display:column property="documentType" media="html" style="width:9%;text-align:left;"  sortable="true"  sortName="getDocumentTypeNoLnk" title="Document Type"/> 
                                        <display:column property="documentTypeNoLnk" media="csv pdf" style="width:9%;text-align:left;"  sortable="true"  sortName="getDocumentTypeNoLnk" title="Document Type"/> 
                                        <display:column property="startDate_s" media="html" sortable="true" sortName="getStartDate" style="width:10%;text-align:left;" defaultorder="ascending" format="{0,date,MM/dd/yyyy hh:mm a}" title="Date Received"/>
                                        <display:column property="dateReceived" media="csv pdf" sortable="true" sortName="getStartDate" style="width:10%;text-align:left;" defaultorder="ascending" format="{0,date,MM/dd/yyyy hh:mm a}" title="Date Received"/>
 										<display:column property="reportingFacilityProvider" media="html" sortable="true" sortName="getReportingFacilityProvider" style="width:15%;text-align:left;" defaultorder="ascending" title="Facility/Provider"/>
										<display:column property="reportingFacilityProviderPrint" media="csv pdf" sortable="true" sortName="getReportingFacilityProvider" style="width:15%;text-align:left;" defaultorder="ascending" title="Facility/Provider"/>
										<display:column property="personFullName" media="html" sortable="true" sortName="getPersonFullNameNoLink" style="width:15%;text-align:left;"  title="Patient"/>                                
           								<display:column property="personFullNameNoLink" media="csv pdf" sortable="true" sortName="getPersonFullNameNoLink" style="width:15%;text-align:left;"  title="Patient"/>                                
										<display:column property="description" media="html" sortable="true" style="width:20%;text-align:left;" sortName="getDescriptionPrint" title="Description"/>  
                                        <display:column property="descriptionPrint" media="csv pdf" defaultorder="ascending" sortable="true"  sortName= "getDescriptionPrint" title="Description" style="width:20%;"/>
		         						 <display:column property="jurisdiction" style="width:10%;text-align:left;"  sortable="true" sortName="getJurisdiction" title="Jurisdiction"/> 
                                        <display:column property="testsString" defaultorder="ascending" sortable="true" sortName= "getTestsStringNoLnk" media="html" title="Associated With" style="width:12%;"/>
		          						
		          						<display:column property="testsStringPrint" defaultorder="ascending" sortable="true" sortName= "getTestsStringPrint" media="csv pdf" title="Associated With" style="width:21%;"/>
		          
                                        <display:column property="localId" style="width:20%;text-align:left;"  sortable="true" sortName="getLocalId" title="Local ID"/> 
                                         
                                       <display:setProperty name="basic.empty.showtable" value="true" />
                                    </display:table>
                                    
									<html:select property="answerArray(STARTDATE)" styleId = "sdate" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="attributeMap.startDateDDList" value="key" label="value" style="width:180"/>
									</html:select>
									<html:select property="answerArray(OBSERVATIONTYPE)" styleId = "obsType" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="attributeMap.observationTypeDDList" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(JURISDICTION)" styleId = "juris" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="attributeMap.jurisdictionDDList" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(CONDITION)" styleId = "testCond" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="attributeMap.conditionDD" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(PatientSearchText)" styleId = "patient" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="noDataArray" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(LocalIdSearchText)" styleId = "localId" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="noDataArray" value="key" label="value"/>
									</html:select>
									<!-- TO DO  -->
									<html:select property="answerArray(Provider)" styleId = "provider" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="noDataArray" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(DESCRIPTION)" styleId = "descrip" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="attributeMap.descriptionDDList" value="key" label="value"/>
									</html:select>

								<%-- <html:hidden styleId="PatientSearchText" property="attributeMap.searchCriteria.patient" />
								<html:hidden styleId="LocalIdSearchText" property="attributeMap.searchCriteria.localId" />
								<html:hidden styleId="Provider" property="attributeMap.searchCriteria.provider" /> --%>
								
								<html:hidden styleId="markAsReviewdReason1" property="markAsReviewdReason"/>
								<input type='hidden' id='PatientSearchText'
										value="<%=request.getAttribute("PATIENT") != null ? request.getAttribute("PATIENT") : ""%>" />
									<input type='hidden' id='LocalIdSearchText'
										value="<%=request.getAttribute("LOCALID") != null ? request.getAttribute("LOCALID") : ""%>" />
									<input type='hidden' id='Provider'
										value="<%=request.getAttribute("PROVIDER") != null ? request.getAttribute("PROVIDER") : ""%>" />
										
									</logic:match>
								
								
								
									<!--Investigation type selected-->
									<logic:match name="personSearchForm" property="attributeMap.reportType" value="I">
									
									
									             
									<display:table name="personList" class="dtTable" pagesize="20"  id="searchResultsTable" style="border: 1px solid black;"
									requestURI="/nbs/PatientSearchResults1.do?ContextAction=sortingByColumn&existing=true&initLoad=true" 
                                        sort="external" export="true" excludedParams="*">		
                                     	<display:setProperty name="export.csv.filename" value="InvestigationSearchResult.csv"/>
										<display:setProperty name="export.pdf.filename" value="InvestigationSearchResult.pdf"/>
                                     	
                                        <display:column property="startDate" sortable="true" format="{0,date,MM/dd/yyyy}" sortName="getStartDate" style="width:10%;text-align:left;"  title="Start Date"/>  
                                        <display:column property="investigator" sortable="true" style="width:20%;text-align:left;" sortName="getInvestigator" title="Investigator"/>  
                                        <display:column property="jurisdiction" style="width:10%;text-align:left;"  sortable="true" sortName="getJurisdiction" title="Jurisdiction"/>  
                                        <display:column property="personFullName" sortable="true" media="html" sortName="getPersonFullNameNoLink" style="width:20%;text-align:left;" defaultorder="ascending" title="Patient"/>
										<display:column property="personFullNameNoLink" sortable="true" media="csv pdf" sortName="getPersonFullNameNoLink" style="width:20%;text-align:left;" defaultorder="ascending" title="Patient"/>
										<display:column property="conditionLink" style="width:20%;text-align:left;" media="html" sortable="true" sortName="getCondition" title="Condition"/>  
                                        <display:column property="condition" style="width:20%;text-align:left;" media="csv pdf" sortable="true" sortName="getCondition" title="Condition"/>  
										<display:column property="caseStatus" style="width:10%;text-align:left;"  sortable="true" sortName="getCaseStatus" title="Case Status"/>  
                                        <display:column property="notification" style="width:15%;text-align:left;"  sortable="true" sortName="getNotification" title="Notification"/>  
                                        <display:column property="localId" style="width:20%;text-align:left;"  sortable="true" sortName="getLocalId" title="Investigation ID"/>
                                        <display:setProperty name="basic.empty.showtable" value="true" />
                                    </display:table>
                                    <html:select property="answerArray(STARTDATE)" styleId = "sdate" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
									    <html:optionsCollection property="attributeMap.startDateDDList" value="key" label="value" style="width:180"/>
									</html:select>
									<html:select property="answerArray(INVESTIGATOR)" styleId = "inv" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
									    <html:optionsCollection property="attributeMap.investigatorDDList" value="key" label="value"/>
									</html:select>
									 <html:select property="answerArray(JURISDICTION)" styleId = "juris" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
									    <html:optionsCollection property="attributeMap.jurisdictionDDList" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(CONDITION)" styleId = "cond" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
									    <html:optionsCollection property="attributeMap.conditionDDList" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(CASESTATUS)" styleId = "stat" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
									    <html:optionsCollection property="attributeMap.CaseStatusDDList" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(NOTIFICATION)" styleId = "notif" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
									    <html:optionsCollection property="attributeMap.notificationDDList" value="key" label="value"/>
									</html:select>
									<html:select property="answerArray(Patient)" styleId = "patient" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
									    <html:optionsCollection property="noDataArray" value="key" label="value"/>
									</html:select> 
									<html:select property="answerArray(LocalIdSearchInv)" styleId = "localIdSearch" onchange="selectfilterCriteria()" multiple="true" size="4" style="width:180">
										<html:optionsCollection property="noDataArray" value="key" label="value"/>
									</html:select>
									 <html:hidden styleId="Patient" property="attributeMap.searchCriteria.patient"/>
									 <input type='hidden' id='LocalIdSearchInv'  value="<%=request.getAttribute("InvestigationID") != null ? request.getAttribute("InvestigationID") : ""%>" />
									
									
									 
									 
								</logic:match>
								
								
									
                                </td>
                            </tr>                           
                     </table>
                      <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
                          <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
                    	<div class="removefilter" id="removeFilters">
                    		<table role="presentation" style="width: 100%;">
						<tr>
							<td style="width: 50%; text-align: left; padding: 0.2em; padding-left:0.2%">
							 <logic:equal name="personSearchForm" property="personSearch.custom" value="true">
							 	<logic:equal name="personSearchForm" property="attributeMap.reportType" value="LR">
							 		<logic:equal name="personSearchForm" property="attributeMap.permissionMarkAsReviewed" value="true">
										<a class="reviewedLink" id="markAsReview" href="#" onclick="markAsReviewedSectionForLRQ()" onkeypress ="markAsReviewedSectionForLRQ()"><font class="hyperLink"> Mark As Reviewed </font></a> 
									</logic:equal>
								</logic:equal>
							  </logic:equal>
							</td>
							<td style="width: 50%; text-align: right; padding: 0.2em;">
							    <a class="removefilerLink" href="javascript:clearFilter()" style="text-decoration:none"><font class="hyperLink"> | Remove All Filters/Sorts&nbsp;</font></a>
							</td>
						</tr>
						</table>
		                     
                	</div>
					
					<logic:notMatch name="personSearchForm" property="attributeMap.reportType" value="N">
				   
				   <div class="printexport" id="printExport" align="right">
								<img class="cursorHand" src="print.gif" tabIndex="0" alt="Print Queue to PDF" title="Print Queue to PDF" onclick="printQueue();" onkeypress="if(isEnterKey(event)) printQueue();"/>
								<img class="cursorHand" src="export.gif" tabIndex="0" alt="Export Queue to CSV" title="Export Queue to CSV" onclick="exportQueue();" onkeypress="if(isEnterKey(event)) exportQueue();"/>
					</div>
                  
                    </logic:notMatch>
                   
               
            </div>
        </div>
        <logic:match name="personSearchForm" property="attributeMap.reportType" value="N">
         <div class="grayButtonBar">
                         <table role="presentation" width="100%">
					   		 <tr>
					   		 	<td align="left" width="80%">
					   		 	</td>
					   		 
					   		 	<td align="right" >
					   		 	<logic:equal value="true" name="addButton" scope="request">
					   		 	<input type="button" name="Submit" value="Add New" onclick="location.href='<%= request.getAttribute("addButtonHref")%>'"/>
					   		 	</logic:equal>
					   		 	</td>
					   		 </tr> 				   		
					   		
					   	</table>	 		
                         
                         </div>  

        <div  style="width:100%;">
		</logic:match>
	         <div style="display: none;visibility: none;" id="errorMessages">
				<b> <a name="errorMessagesHref"></a>Queue is sorted/filtered by :</b> <br/>
				<ul>
					<logic:iterate id="errors" name="personSearchForm" property="attributeMap.searchCriteria">
			   	      <li id="${fn:escapeXml(errors.key)}">${fn:escapeXml(errors.value)}</li>
					</logic:iterate>
				</ul>
			</div> 
	  
				<html:hidden styleId="reportType" property="attributeMap.reportType"/>
			    <html:hidden styleId="customId" property="attributeMap.custom"/>
			    <html:hidden styleId="queueName" property="attributeMap.queueName"/>
			    <html:hidden styleId="queueName" property="attributeMap.searchCriteriaDesc"/>
			    
			    <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
			     <%--    <html:hidden styleId="totalRecords" property="attributeMap.totalRecords"/>   --%>
          </div>
          
          
			    
         </html:form> 
    </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
		function startCountdown() {
			var sessionTimeout = <%= request.getSession().getMaxInactiveInterval()%>
			min = sessionTimeout / 60;
			sec = 0;				
			getTimerCountDown();
		}
</SCRIPT>  	