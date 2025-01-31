
function OrganizationSearchSubmit(){
	//return false if no problems
	var errorTD = getElementByIdOrByName("errorRange");
	errorTD.setAttribute("className", "none");
	var errorText="";
	
	var atLeastOneSearchCriteria = false;
	var atLeastOneStatusCode = false;
	
	
	
	if(getElementByIdOrByName("organizationSearch.nmTxt") !=null && isEmpty(getElementByIdOrByName("organizationSearch.nmTxt").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("organizationSearch.streetAddr1")!=null && isEmpty(getElementByIdOrByName("organizationSearch.streetAddr1").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("organizationSearch.cityDescTxt")!=null && isEmpty(getElementByIdOrByName("organizationSearch.cityDescTxt").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("organizationSearch.stateCd")!=null && isEmpty(getElementByIdOrByName("organizationSearch.stateCd").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("organizationSearch.zipCd")!=null && isEmpty(getElementByIdOrByName("organizationSearch.zipCd").value) == false) atLeastOneSearchCriteria = true;
        if(getElementByIdOrByName("organizationSearch.phoneNbrTxt")!=null && isEmpty(getElementByIdOrByName("organizationSearch.phoneNbrTxt").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("organizationSearch.rootExtensionTxt")!=null && isEmpty(getElementByIdOrByName("organizationSearch.rootExtensionTxt").value) == false) atLeastOneSearchCriteria = true;
	if(getElementByIdOrByName("organizationSearch.roleCd")!=null && isEmpty(getElementByIdOrByName("organizationSearch.roleCd").value) == false) atLeastOneSearchCriteria = true;

	var activeStatus = getElementByIdOrByName("organizationSearch.active");
	var inactiveStatus = getElementByIdOrByName("organizationSearch.inActive");
	
	if(activeStatus!=null && (activeStatus.checked) == true) atLeastOneStatusCode = true;
	if(inactiveStatus!=null && (inactiveStatus.checked) == true) atLeastOneStatusCode = true;

	// If it is for Entity Search
	if (inactiveStatus==null)
		atLeastOneStatusCode = true;
	
	/*	
	if(frm.organizationSearchactive!=null){
		
		if(frm.organizationSearchactive.checked     == true) atLeastOneStatusCode = true;
		if(frm.organizationSearchinActive.checked   == true) atLeastOneStatusCode = true;
		//if(frm.superceded.checked == true) atLeastOneStatusCode = true;
	} else{ //create frm active and set to true
		var statusNode = document.createElement("input");
		statusNode.setAttribute("type", "hidden");
		statusNode.setAttribute("value", "true");
		statusNode.setAttribute("name", "OrganizationSearch.active");
		var formNode =getElementByIdOrByName("nedssForm");
		formNode.appendChild(statusNode);
		atLeastOneStatusCode=true;
	}
	*/
	
	// do the appropriate error message
	if( atLeastOneSearchCriteria == false ) 
	{
		errorText = "Please enter at least one item to search on and try again.\n";
		errorTD.innerText = errorText;
		errorTD.textContent = errorText;
		errorTD.setAttribute("className", "error");
		errorTD.setAttribute("class", "error");
		showErrorStatement=false;
	} 
	
	if( atLeastOneStatusCode == false) 
	{
		errorText += "Please select at least one status code and try again.";
		errorTD.innerText = errorText;
		errorTD.textContent = errorText;
		errorTD.setAttribute("className", "error");
		errorTD.setAttribute("class", "error");
	}
		
	if(atLeastOneSearchCriteria==false || atLeastOneStatusCode==false)
		return true;
	else
		return false;
	
}

validationArray[0] = OrganizationSearchSubmit;
