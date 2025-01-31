
function OrganizationSearchSubmit(){
	//return false if no problems
		var errorTD = getElementByIdOrByName("errorRange");
		errorTD.setAttribute("className", "none");
		var errorText="";
	
		var atLeastOneSearchCriteria = false;
		var atLeastOneStatusCode = false;
	
	    var frm = getElementByIdOrByName("nedssForm");
	
		if(frm.personSearchlastName!=null && isEmpty(frm.personSearchlastName.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchfirstName!=null && isEmpty(frm.personSearchfirstName.value) == false) atLeastOneSearchCriteria = true;
		if(frm.personSearchbirthTime!=null && isEmpty(frm.personSearchbirthTime.value) == false) atLeastOneSearchCriteria = true;
	
	    if(frm.personSearchcurrentSex!=null && isEmpty(frm.personSearchcurrentSex.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchstreetAddr1!=null && isEmpty(frm.personSearchstreetAddr1.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchcityDescTxt!=null && isEmpty(frm.personSearchcityDescTxt.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchstate!=null && isEmpty(frm.personSearchstate.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchzipCd!=null && isEmpty(frm.personSearchzipCd.value) == false) atLeastOneSearchCriteria = true;
	
	    if(frm.personSearchlocalID!=null && isEmpty(frm.personSearchlocalID.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchtypeCd!=null && isEmpty(frm.personSearchtypeCd.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchphoneNbrTxt!=null && isEmpty(frm.personSearchphoneNbrTxt.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchageReported!=null && isEmpty(frm.personSearchageReported.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchraceCd!=null && isEmpty(frm.personSearchraceCd.value) == false) atLeastOneSearchCriteria = true;
	    if(frm.personSearchethnicGroupInd!=null && isEmpty(frm.personSearchethnicGroupInd.value) == false) atLeastOneSearchCriteria = true;
	
	
	
	
	
	   if(frm.personSearchactive!=null){
	   		
			if(frm.personSearchactive.checked     == true) atLeastOneStatusCode = true;
			if(frm.personSearchinActive.checked   == true) atLeastOneStatusCode = true;
			//if(frm.superceded.checked == true) atLeastOneStatusCode = true;
		} else{ //create frm active and set to true
			var statusNode = document.createElement("input");
			statusNode.setAttribute("type", "hidden");
			statusNode.setAttribute("value", "true");
			statusNode.setAttribute("name", "personSearch.active");
			var formNode = getElementByIdOrByName("nedssForm");
			formNode.appendChild(statusNode);
			atLeastOneStatusCode=true;
		}
	
		// do the appropriate error message
	
		if( atLeastOneSearchCriteria == false ) {
			
			errorText = "Please enter at least one item to search on and try again.\n";
			
			errorTD.innerText = errorText;
			errorTD.setAttribute("className", "error");
			
		} 


		if( atLeastOneStatusCode == false) {
			
			errorText += "Please select at least one status code and try again.";
			
			errorTD.innerText = errorText;
			errorTD.setAttribute("className", "error");
			
		}
		
		if(atLeastOneSearchCriteria==false || atLeastOneStatusCode==false)
			return true;
		else
			return false;
	
}

validationArray[0] = OrganizationSearchSubmit;
