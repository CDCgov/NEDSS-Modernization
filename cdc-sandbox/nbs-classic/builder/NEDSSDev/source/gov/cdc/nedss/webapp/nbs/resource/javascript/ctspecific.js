     	
     		function reloadInvs(filler){
	         	// alert(filler.value + ' from ctspecific !!!');      
	 	         JInvestigationForm.callChildForm(filler.value, function(data) { 	   
	 	         }); 	         	
	 	         setTimeout("reldPage()", 1000);
		  }