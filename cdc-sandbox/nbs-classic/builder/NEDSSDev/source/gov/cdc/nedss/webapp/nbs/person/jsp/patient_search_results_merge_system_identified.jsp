<%@ include file="/jsp/tags.jsp" %>
<%@ page isELIgnored ="false" %>
<%@ page import="gov.cdc.nedss.util.HTMLEncoder"%>
<html lang="en">
    <head>
        <title>NBS: Merge Candidate List</title>
        <%@ include file="/jsp/resources.jsp" %>
         <SCRIPT Language="JavaScript" Src="/nbs/dwr/interface/JPersonSearchForm.js"></SCRIPT>
        <script Language="JavaScript" Src="searchResultsSpecific.js"></script>
        <SCRIPT Language="JavaScript" Src="MergePersonSpecific.js"></SCRIPT>
        <SCRIPT Language="JavaScript" Src="jqueryMultiSelect.js"></SCRIPT> 
        <link href="jqueryMultiSelect.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript">
      
        
        window.addEventListener('click', function(e){   
      	  if (!document.getElementById('systemIdentifiedPage').contains(e.target)){
				var type = e.target.tagName;
				
				if(type == "A" || type =="IMG"){//To avoid showing the message when the user clicks on any empty space outside of the System Identified page (instead of a link or button)
					showAlert();
					e.preventDefault();//It stops the action associated to the link or buttpn clicked
					return ;
				}
      	    // Clicked outside the System Identified page
      	  }
      	});
        /*
        * survivorSelection(): selects the merge checkbox if it is the first survivor id selected, otherwise, it shows a popup error message.
        */
        
        function survivorSelection(field){
     	   
     	   
	     	var selectedSurvivor = [];
	     	$j('.selectCheckBoxSurvivor:checked').each(function() {
	     		selectedSurvivor.push($j(this));
	     	});
	     	if(selectedSurvivor.length>1){
	     		alert("Only one Surviving PatientId can be selected as Survivor. Please uncheck the previously selected Surviving PatientId before selecting another patient.");
	     		field.checked=false;
	     	}
	     	else{
	     		var survivor = selectedSurvivor[0];
	     		
	     		var siblings = survivor.parent().siblings();
	     		var survivorSibling = siblings[0];
	     		var checkMergeSurvivor = $j(survivorSibling).find("input")[0];
	     		
	     		checkMergeSurvivor.checked=true;
	     	   
	     	}
        }
         
        /**
        * MergePatientNew: merge patient functionality. It validates there's at least 2 patients selected for merging and if there's a survivor selected
        the merge column needs to be selected as well. If all validations are correct, a double dare message will show before merging
        the patients.
        */
        function MergePatientNew(){


        	var errorMsgArray = new Array(); 
        	var confirmMsg = "If you continue with the Merge action, the selected patient records will be merged into a single patient record. Select OK to continue, or Cancel to not continue.";

        	//errorMsgArray.push(errorText);  



        	//At least 2 selected:

        		var selected = [];
        		$j('.selectCheckBoxMerge:checked').each(function() {
        			selected.push($j(this));
        		});
        		if(selected.length<=1){
        			
        			errorMsgArray.push("Please select a minimum of two patients in order to merge the patient data.");  
        			
        		}


        	//if survivor is selected, the corresponding patient merge checkbox needs to be selected

        		var selectedSurvivor = [];
        		$j('.selectCheckBoxSurvivor:checked').each(function() {
        			selectedSurvivor.push($j(this));
        		});
        		var survivorAndMergeSelected = false;
        		
        		if(selectedSurvivor.length==1){
        			var survivor = selectedSurvivor[0];
        			
        			var siblings = survivor.parent().siblings();
        			var survivorSibling = siblings[0];
        			var checkMergeSurvivor = $j(survivorSibling).find("input")[0];
        				
        				for(var i =0; i<selected.length; i++){
        					if(selected[i][0]==checkMergeSurvivor)
        						survivorAndMergeSelected=true;
        					
        				}
        			
        			if(!survivorAndMergeSelected)
        			errorMsgArray.push("You selected a Patient ID as the Survivor but you unchecked the associated Merge checkbox. Please recheck the merge checkbox or choose another Survivor.");  
        				
        		}
        		
        		
        		
        		if(errorMsgArray!=null && errorMsgArray.size()>0){
        			
        			 displayGlobalErrorMessage(errorMsgArray);
        		}
        		else
        		 if (confirm(confirmMsg)){
        		 		var merge="";
        		 		var survivor="";
        			     
        		 		
        		 		if (selectedSurvivor.length==1){
        		 			
        		 			var survivor = selectedSurvivor[0];
        		 			var siblings = survivor.parent().siblings();
                            var survivorSiblingId = siblings[1];
                            survivor=(survivorSiblingId.textContent).trim();
        		 		}
        		 		
        		 		

        		 		for(var i = 0 ; i< selected.length; i++){
        		 			
        		 			var mergeSelected = selected[i];
        		 			var siblings = mergeSelected.parent().siblings();
                            var mergeSiblingId = siblings[1];
                            var link = $j(mergeSiblingId).children("a")[0].href;
                            var uid = link.substring(link.lastIndexOf("uid=")+4);
                            uid = uid.substring(0,uid.indexOf(")"));
                            uid = uid.replace("'","");
                            if(uid!=null && uid.indexOf("%")!=-1){//ND-24519
                             	 uid = uid.substring(0, uid.indexOf("%"))
                              }

                            merge=merge+uid.trim();
                            if(i!=(selected.length-1))//if it is not the last one
                          	  merge+=",";
        		 		}
        		 		
        		 		
        		 		
        		 		
        		 		
        			    var action ='/nbs/MergeCandidateList2.do?ContextAction=Merge&survivor='+survivor+"&merge="+merge;
        			    
        			    getElementByIdOrByName("mergeLink").setAttribute("href",action);
        			 //   document.forms[0].action=action;
        			 //   document.forms[0].submit();         
        				//document.personSearchForm.action =action;
        			   // document.personSearchForm.submit();
        				
        				
        		 return true; 
        		 }else{
        		 return false; 
        		 }
        	   }
        
		
		
		
		   /**
        * MergePatientNew: merge patient functionality. It validates there's at least 2 patients selected for merging and if there's a survivor selected
        the merge column needs to be selected as well. If all validations are correct, a double dare message will show before merging
        the patients.
        */
        function MergePatientNewButton(){


        	var errorMsgArray = new Array(); 
        	var confirmMsg = "If you continue with the Merge action, the selected patient records will be merged into a single patient record. Select OK to continue, or Cancel to not continue.";

        	//errorMsgArray.push(errorText);  



        	//At least 2 selected:

        		var selected = [];
        		$j('.selectCheckBoxMerge:checked').each(function() {
        			selected.push($j(this));
        		});
        		if(selected.length<=1){
        			
        			errorMsgArray.push("Please select a minimum of two patients in order to merge the patient data.");  
        			
        		}


        	//if survivor is selected, the corresponding patient merge checkbox needs to be selected

        		var selectedSurvivor = [];
        		$j('.selectCheckBoxSurvivor:checked').each(function() {
        			selectedSurvivor.push($j(this));
        		});
        		var survivorAndMergeSelected = false;
        		
        		if(selectedSurvivor.length==1){
        			var survivor = selectedSurvivor[0];
        			
        			var siblings = survivor.parent().siblings();
        			var survivorSibling = siblings[0];
        			var checkMergeSurvivor = $j(survivorSibling).find("input")[0];
        				
        				for(var i =0; i<selected.length; i++){
        					if(selected[i][0]==checkMergeSurvivor)
        						survivorAndMergeSelected=true;
        					
        				}
        			
        			if(!survivorAndMergeSelected)
        			errorMsgArray.push("You selected a Patient ID as the Survivor but you unchecked the associated Merge checkbox. Please recheck the merge checkbox or choose another Survivor.");  
        				
        		}
        		
        		
        		
        		if(errorMsgArray!=null && errorMsgArray.size()>0){
        			
        			 displayGlobalErrorMessage(errorMsgArray);
        		}
        		else
        		 if (confirm(confirmMsg)){
        		 		var merge="";
        		 		var survivor="";
        			     
        		 		
        		 		if (selectedSurvivor.length==1){
        		 			
        		 			var survivor = selectedSurvivor[0];
        		 			var siblings = survivor.parent().siblings();
                            var survivorSiblingId = siblings[1];
                            survivor=(survivorSiblingId.textContent).trim();
        		 		}
        		 		
        		 		

        		 		for(var i = 0 ; i< selected.length; i++){
        		 			
        		 			var mergeSelected = selected[i];
        		 			var siblings = mergeSelected.parent().siblings();
                            var mergeSiblingId = siblings[1];
                            var link = $j(mergeSiblingId).children("a")[0].href;
                            var uid = link.substring(link.lastIndexOf("uid=")+4);
                            uid = uid.substring(0,uid.indexOf(")"));
                            uid = uid.replace("'","");
                            if(uid!=null && uid.indexOf("%")!=-1){//ND-24519
                             	 uid = uid.substring(0, uid.indexOf("%"))
                              }

                            merge=merge+uid.trim();
                            if(i!=(selected.length-1))//if it is not the last one
                          	  merge+=",";
        		 		}
        		 		
        		 		
        		 		
        		 		
        		 		
        			    var action ='/nbs/MergeCandidateList2.do?ContextAction=Merge&survivor='+survivor+"&merge="+merge;
        			    
        			  //  getElementByIdOrByName("mergeLink").setAttribute("href",action);
        			 //   document.forms[0].action=action;
        			 //   document.forms[0].submit();         
        				//document.personSearchForm.action =action;
        			   // document.personSearchForm.submit();
        				
        				
						document.forms[0].action = action;

					document.forms[0].submit();
	
	
        		 }/*else{
        		 return false; 
        		 }*/
        	   }
        
		

        
        function removeFromMerge(){


        	var errorMsgArray = new Array(); 
        	var confirmMsg = "If you continue with the Remove from Merge action, the selected patient record(s) won't be available to merge in the future. Select OK to continue, or Cancel to not continue.";

			//At least 1 selected:

             		var selected = [];
             		$j('.selectCheckBoxMerge:checked').each(function() {
             			selected.push($j(this));
             		});
             		if(selected.length<1){
             			
             			errorMsgArray.push("Please select a minimum of one patient in order to remove from merge.");  
             			
             		}
             		
             		if(errorMsgArray!=null && errorMsgArray.size()>0){
             			
             			 displayGlobalErrorMessage(errorMsgArray);
             		}
				else
        		 if (confirm(confirmMsg)){
        		 		var removeMerge="";
        		 		var survivor="";

        		 		for(var i = 0 ; i< selected.length; i++){
        		 			
        		 			var removeSelected = selected[i];
        		 			var siblings = removeSelected.parent().siblings();
                            var mergeSiblingId = siblings[1];
                            var link = $j(mergeSiblingId).children("a")[0].href;
                            var uid = link.substring(link.lastIndexOf("uid=")+4);
                            uid = uid.substring(0,uid.indexOf(")"));
                            uid = uid.replace("'","");
                            if(uid!=null && uid.indexOf("%")!=-1){//ND-24519
                            	 uid = uid.substring(0, uid.indexOf("%"))
                             }
							removeMerge=removeMerge+uid.trim();
							if(i!=(selected.length-1))//if it is not the last one
                            	removeMerge+=",";
        		 		}
        		 		
        			    var action ='/nbs/MergeCandidateList2.do?ContextAction=RemoveFromMerge&removeFromMerge='+removeMerge;
        			    
        			    getElementByIdOrByName("removeMergeLink").setAttribute("href",action);
        			 //   document.forms[0].action=action;
        			 //   document.forms[0].submit();         
        				//document.personSearchForm.action =action;
        			   // document.personSearchForm.submit();
        				
        				
        		 return true; 
        		 }else{
        		 return false; 
        		 }
        		 	   
        }



        function removeFromMergeButton(){


        	var errorMsgArray = new Array(); 
        	var confirmMsg = "If you continue with the Remove from Merge action, the selected patient record(s) won't be available to merge in the future. Select OK to continue, or Cancel to not continue.";

			//At least 1 selected:

             		var selected = [];
             		$j('.selectCheckBoxMerge:checked').each(function() {
             			selected.push($j(this));
             		});
             		if(selected.length<1){
             			
             			errorMsgArray.push("Please select a minimum of one patient in order to remove from merge.");  
             			
             		}
             		
             		if(errorMsgArray!=null && errorMsgArray.size()>0){
             			
             			 displayGlobalErrorMessage(errorMsgArray);
             		}
				else
        		 if (confirm(confirmMsg)){
        		 		var removeMerge="";
        		 		var survivor="";

        		 		for(var i = 0 ; i< selected.length; i++){
        		 			
        		 			var removeSelected = selected[i];
        		 			var siblings = removeSelected.parent().siblings();
                            var mergeSiblingId = siblings[1];
                            var link = $j(mergeSiblingId).children("a")[0].href;
                            var uid = link.substring(link.lastIndexOf("uid=")+4);
                            uid = uid.substring(0,uid.indexOf(")"));
                            uid = uid.replace("'","");
                            if(uid!=null && uid.indexOf("%")!=-1){//ND-24519
                            	 uid = uid.substring(0, uid.indexOf("%"))
                             }
							removeMerge=removeMerge+uid.trim();
							if(i!=(selected.length-1))//if it is not the last one
                            	removeMerge+=",";
        		 		}
        		 		
        			    var action ='/nbs/MergeCandidateList2.do?ContextAction=RemoveFromMerge&removeFromMerge='+removeMerge;
        			    
        			  //  getElementByIdOrByName("removeMergeLink").setAttribute("href",action);
        			 //   document.forms[0].action=action;
        			 //   document.forms[0].submit();         
        				//document.personSearchForm.action =action;
        			   // document.personSearchForm.submit();
        				document.forms[0].action = action;

						document.forms[0].submit();
        				
        		// return true; 
        		 }/*else{
        		 return false; 
        		 }*/
        		 	   
        }
		
        
        
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
        
        function onKeyUpValidate()
 		{ if(getElementByIdOrByName("reportType").value == "N"){     	  
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
 		}
 		else if(getElementByIdOrByName("reportType").value == "LMC"){
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
	            document.forms[0].action ='/nbs/MergeCandidateList2.do?ContextAction=filterPatientSubmit';
	            document.forms[0].submit();
         } 
         
        function clearFilter()
	{
	             document.forms[0].action ='/nbs/MergeCandidateList2.do?ContextAction=removeFilter';
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
            document.forms[0].submit();  
		}
        
        </script>       
        <style type="text/css">
        

			
		
            table.dtTable thead tr th.selectAll {text-align:center;}
			
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
            </style>
    </head>
    <body onload="startCountdown();attachIcons();makeMSelects();showCount();displayTooltips();addTabs();addRolePresentationToTabsAndSections();">    
    <div id="blockparent"></div>
        <div id="doc3">
            <div id="bd">
                <%@ include file="../../jsp/topNavFullScreenWidth.jsp" %>

			<div id = "systemIdentifiedPage">
                <!-- Top button bar -->
                  <!-- Print and Export links -->
				  

			
					<logic:match name="personSearchForm" property="attributeMap.reportType" value="N">
                       <div class="grayButtonBar">
                         <table role="presentation" width="100%">
					   		 <tr style="white-space: nowrap">
					   		 	<td align="left" width="80%">
								<input type="button" name="Submit" style="width:80px" title="To merge records, select two or more candidates in the Merge column. You may also select a surviving record ID; if none is selected, then the oldest (first) patient ID will be the surviving record ID." value="Merge" onclick="MergePatientNewButton()"/>
					   		 	<input type="button" name="Submit" style="width:140px" title="To remove a patient from future merges, select a patient in the Merge column. This will flag the patient to NOT be included in future merge logic until/unless the patient record is updated in the future." value="Remove from Merge" onclick="removeFromMergeButton()"/>
					   		 	
					   		 	</td>
					   		 
					   		 	<td align="right" >
					   		 	<input type="button" name="Submit" style="width:80px" title="Skip allows you to skip the current merge candidate list to get to the next set of merge candidates. Any skipped merge candidate groups are moved to the end of the merge candidate group queue for later review." value="Skip" onclick="callSkip( '<%=request.getAttribute("groupsAvailableToMerge")%>')"/>
					   		 	<input type="button" name="Submit" style="width:80px" title="No Merge allows you to indicate that none of the candidates in the merge group should be merged and to move to the *next *set of merge candidates."  value="No Merge" onclick="callNoMergePersonNew()"/>
					   		 	<input type="button" name="Submit" style="width:80px" title="Exist Merge allows you to exit the Merge Patient functionality."  value="Exit Merge" onclick="cancel('/nbs/MergeCandidateList2.do?ContextAction=Cancel');"/>
					   		 	<input type="button" name="Submit" style="width:80px" title="Print allows you to print a table of the current Merge Candidate List."  value="Print" onclick="printQueue();"/>
					   		 	<input type="button" name="Submit" style="width:80px" title="Download allows you to download a table of the current Merge Candidate List."  value="Download" onclick="exportQueue();"/>
					   		 	</td>
					   		 </tr> 				   		
					   		
					   	</table>	 		
                         
                         </div>  
                 
	               </logic:match>  
			
			  <% if (request.getAttribute("confirmationMergeMessage") != null) { %>
					  <div class="infoBox success">
					  	<%=(String)request.getAttribute("confirmationMergeMessage")%>
					  </div>    
	           <% } %>
						           
				<div id="MergeCandidatesBox" class="boxed" style="margin-bottom:5px">
			
			
			
			
			
				                        <table role="presentation" class="formTable" style=" width:100%; margin:0px;">
 											<tr>
									               <td colspan="3" style="text-align:left;"><span style="color:black; font-weight:bold;"> &nbsp;&nbsp;Candidate group batch process last run: <%=request.getAttribute("candidateQueueCreationDate")%></td>
									      	  </tr>
									      	  <tr>
									      	  	   <td colspan="3" style="text-align:left;"><span style="color:black; font-weight:bold;"> &nbsp;&nbsp;Number of groups remaining to view for merge: <%=request.getAttribute("groupsAvailableToMerge")%></td>
									     
									        </tr>
									        
									</table>
			</div>
									        
									        
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
              
              
                 <!-- Error Messages using Action Messages-->
				<div id="globalFeedbackMessagesBar" class="screenOnly"> </div>
				
               <html:form action="/LoadMergeCandidateList2.do">                  
                        <table role="presentation" width="100%">                        
                            <tr>
                                <td align="center">   
                                
                                
								 
							
								
								
								
								  <logic:match name="personSearchForm" property="attributeMap.reportType" value="N">                                                                    
                                    <display:table name="personList" class="dtTable" pagesize="20"  id="searchResultsTable" style="border: 1px solid black;" requestURI="/nbs/MergeCandidateList2.do?ContextAction=sortingByColumn&existing=true&initLoad=true" 
                                        sort="external" export="true" excludedParams="*">
                                     	  
                                     	  
                                     	<display:column style="width:3%;text-align:center" media="html" title="<p style='display:none'>Select/Deselect All</p> Merge" ><input title="Select/Deselect checkbox" type="checkbox" name="selectCheckBoxMerge" class="selectCheckBoxMerge"/> </display:column>                
					 					<display:column style="width:6%;text-align:center" media="html" title="<p style='display:none'>Select/Deselect All</p> Surviving ID" ><input onclick="survivorSelection(this)" title="Select/Deselect checkbox" type="checkbox" name="selectCheckBoxSurvivor" class="selectCheckBoxSurvivor"/> </display:column>                
					
					
                                     	<display:column property="viewFile" media = "html" style="width:6%;text-align:left;"  sortable="true"  sortName="getViewFile" title="Patient ID"/> 
                                     	<display:column property="viewFileWithoutLink"  media = "pdf csv" style="width:9%;text-align:left;"  sortable="true"  sortName="getViewFile" title="Patient ID"/> 
                                     	   
                                        <display:column property="personFullName" sortable="true" media = "html" sortName="getPersonFullName" style="width:20%;text-align:left;" defaultorder="ascending" title="Name"/>
                                        <display:column property="personFullNameNoLink" sortable="true" media = "pdf csv" sortName="getPersonFullName" style="width:20%;text-align:left;" defaultorder="ascending" title="Name"/>
                                        
                                        <display:column property="profile" sortable="true" sortName="getProfile"  media = "html"  style="width:8%;text-align:left;" defaultorder="ascending" title="Age/DOB/Sex"/>
                                        <display:column property="profileNoLink" sortable="true" sortName="getProfile" media = "pdf csv" style="width:15%;text-align:left;" defaultorder="ascending" title="Age/DOB/Sex"/>
                                        
                                        <display:column property="personAddressProfile"  media = "html"  sortable="true" sortName="getPersonAddress" style="width:19%;text-align:left;"  title="Address"/>  
                                        <display:column property="personAddressProfileNoLink" media = "pdf csv" sortable="true" sortName="getPersonAddress" style="width:20%;text-align:left;"  title="Address"/>  
                                        
                                        <display:column property="personPhoneprofile"  media = "html"  sortable="true" style="width:19%;text-align:left;" sortName="getPersonPhoneprofile" title="Phone/Email"/>  
                                        <display:column property="personPhoneprofileNoLink" media = "pdf csv" sortable="true" style="width:20%;text-align:left;" sortName="getPersonPhoneprofile" title="Phone/Email"/>  
                                        
                                        <display:column property="personIds" media = "html" style="width:19%;text-align:left;"  sortable="true" sortName="getPersonIds" title="ID"/>  
                                        <display:column property="personIdsNoLink" media = "pdf csv" style="width:20%;text-align:left;"  sortable="true" sortName="getPersonIds" title="ID"/>  
                                                                    
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
			
			<logic:notEmpty name="personSearchForm" property="attributeMap.searchCriteria">
				<html:hidden styleId="SearchText1" property="attributeMap.searchCriteria.fulNameSearch"/>
			    <html:hidden styleId="SearchText2" property="attributeMap.searchCriteria.telePhoneSearch"/>
			    <html:hidden styleId="SearchText3" property="attributeMap.searchCriteria.idSearch"/>
			    <html:hidden styleId="SearchText4" property="attributeMap.searchCriteria.patientIdSearch"/>
			    <html:hidden styleId="SearchText5" property="attributeMap.searchCriteria.ageSearch"/>
			    <html:hidden styleId="SearchText6" property="attributeMap.searchCriteria.addressSearch"/>
			</logic:notEmpty>
									</logic:match>
									
													
                                </td>
                            </tr>                           
                     </table>
                     
                  <input type="hidden" id="candidateQueueCreationDate" name="candidateQueueCreationDate" value="<%=request.getAttribute("candidateQueueCreationDate")%>"/> 
                  <input type="hidden" id="groupsAvailableToMerge" name="groupsAvailableToMerge" value="<%=request.getAttribute("groupsAvailableToMerge")%>"/>   
				    
				    
                      <!-- html:hidden styleId="queueCnt" property="attributeMap.queueCount"/-->
                          <div id="whitebar" style="background-color:#FFFFFF; width: 100%; height:1px;" align="right"></div>
                    	<div class="removefilter" id="removeFilters">
                    	
                    			<a class="mergeLink" id="mergeLink" href="#" onclick="MergePatientNew()" style="text-decoration:none; float:left;" title = "To merge records, select two or more candidates in the Merge column. You may also select a surviving record ID; if none is selected, then the oldest (first) patient ID will be the surviving record ID." ><font class="hyperLink" style="text-decoration:underline;padding-left:5px;padding-right:5px;">Merge</font></a>
                    			<div><font style="text-decoration:none; float:left; color:white">|</font><a class="removeMergeLink" id="removeMergeLink" href="#" onclick="removeFromMerge()" title = "To remove a patient from future merges, select a patient in the Merge column. This will flag the patient to NOT be included in future merge logic until/unless the patient record is updated in the future." style="text-decoration:none; float:left;"><font class="hyperLink" style="text-decoration:underline;padding-left:5px;">Remove from Merge</font></a></div>
                    	
		                        <div><font style="text-decoration:none; color:white">|</font><a class="removefilerLink" href="javascript:clearFilter()" style="text-decoration:none"><font class="hyperLink" style="text-decoration:underline; padding-left: 5px; padding-right: 5px" >Remove All Filters/Sorts</font></a></div>
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
					   		 <tr style="white-space: nowrap">
					   		 	<td align="left" width="80%">
								<input type="button" name="Submit" style="width:80px" title="To merge records, select two or more candidates in the Merge column. You may also select a surviving record ID; if none is selected, then the oldest (first) patient ID will be the surviving record ID." value="Merge" onclick="MergePatientNewButton()"/>
					   		 	<input type="button" name="Submit" style="width:140px" title="To remove a patient from future merges, select a patient in the Merge column. This will flag the patient to NOT be included in future merge logic until/unless the patient record is updated in the future." value="Remove from Merge" onclick="removeFromMergeButton()"/>
					   		 	</td>
					   		 
					   		 	<td align="right" >
								<input type="button" name="Submit" style="width:80px" title="Skip allows you to skip the current merge candidate list to get to the next set of merge candidates. Any skipped merge candidate groups are moved to the end of the merge candidate group queue for later review." value="Skip" onclick="callSkip( '<%=request.getAttribute("groupsAvailableToMerge")%>')"/>
					   		 	<input type="button" name="Submit" style="width:80px" title="No Merge allows you to indicate that none of the candidates in the merge group should be merged and to move to the *next *set of merge candidates."  value="No Merge" onclick="callNoMergePersonNew()"/>
					   		 	<input type="button" name="Submit" style="width:80px" title="Exist Merge allows you to exit the Merge Patient functionality."  value="Exit Merge" onclick="cancel('/nbs/MergeCandidateList2.do?ContextAction=Cancel');"/>
					   		 	<input type="button" name="Submit" style="width:80px" title="Print allows you to print a table of the current Merge Candidate List."  value="Print" onclick="printQueue();"/>
					   		 	<input type="button" name="Submit" style="width:80px" title="Download allows you to download a table of the current Merge Candidate List."  value="Download" onclick="exportQueue();"/>
					   		 	
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
					
					
			       <html:hidden styleId="queueCnt" property="attributeMap.queueCount"/>
			     <%--    <html:hidden styleId="totalRecords" property="attributeMap.totalRecords"/>   --%>
         
         </div>
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